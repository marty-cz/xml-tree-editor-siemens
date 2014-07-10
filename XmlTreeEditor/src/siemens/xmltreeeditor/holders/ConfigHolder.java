/*
 * Copyright 2014 Martin Brazdil <martin.brazdil at gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package siemens.xmltreeeditor.holders;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import siemens.xmltreeeditor.config.Config;
import siemens.xmltreeeditor.config.Operation;
import siemens.xmltreeeditor.config.operations.XmlOperation;
import siemens.xmltreeeditor.config.operations.XmlOperationEnum;
import siemens.xmltreeeditor.utils.FileUtils;

/**
 * This class provides an access to the input config structure.
 * Config is loaded from given path of xml file.
 * JAXB (marshalling/unmarshalling) is used to the access of xml file.
 * @author Martin Brazdil <martin.brazdil at gmail.com>
 */
public class ConfigHolder {
    
    /**
     * JAXB context (defines classes to marshalling process).
     */
    private final JAXBContext jaxbContext;
    
    /**
     * Path of xml config file.
     */
    private final Path configFile; 
    
    /**
     * Config object created from file.
     */
    private Config config;

    /**
     * Creates holder and JAXB context.
     * @param configFile the config xml file path
     * @throws JAXBException if JAXB context cannot be created
     * @throws IOException if config file is invalid
     */
    public ConfigHolder(Path configFile) 
            throws JAXBException, IOException {
        
        try {
            jaxbContext = JAXBContext.newInstance(Config.class);
        } catch (JAXBException ex) {
            throw new JAXBException("Can't create JAXB context.", ex);
        }
        FileUtils.verifyFile(configFile);
        this.configFile = configFile;
    }

    /**
     * Gets the config object loaded from xml config file.
     * @return the config object or <code>null</code> if is not loaded
     */
    public Config getConfig() {
        return config;
    }
    
    /**
     * Executes all operation from config over input xml file.
     * @param rootElement the element of document root
     * @throws IllegalStateException if operation executon fails
     */
    public void execAllOperations(Element rootElement) 
            throws IllegalStateException {
        
        if (config.getOperations() != null) {
            for (Operation op : config.getOperations()) {        
                try {
                    execOperation(op, rootElement);            
                } catch (Exception ex) {
                    throw new IllegalStateException(op.getOperType()+"(): "+ex.getMessage(), ex);
                }
            }   
        }
    } 
    
    /**
     * Executes given operation over input xml file.
     * @param op the operation to be processed
     * @param rootElement the element of document root
     * @throws IllegalArgumentException if operation arguments are wrong (count or type)
     */
    private void execOperation(Operation op, Element rootElement) 
            throws IllegalArgumentException {
        
        XmlOperationEnum enumOp = op.getXmlOperation();        
        XmlOperation xmlOp = enumOp.getOperation();
        switch (enumOp) {
            case OP_DELETE_NODE: {   // recursive delete nodes by name
                xmlOp.execOperation(rootElement, op.getNodeName());
                break;
            }
            case OP_REPLACE_VALUE: { // replace value in all nodes (recursively)
                xmlOp.execOperation(rootElement, op.getOldValue(), op.getNewValue());
                break;
            }
            default: break;  // unknown operations is not processed
        }
    }
    
    /**
     * Gets a schema object of JAXB object.
     * @return the schema or <code>null</code> in case of error occurs
     * Source: http://stackoverflow.com/questions/2603778/how-can-i-unmarshall-in-jaxb-and-enjoy-the-schema-validation-without-using-an-ex
     */
    private Schema getSchemaOfConfigXml() {
        try {
            final List<ByteArrayOutputStream> outs = new ArrayList<>();
              // create a schema generator
            jaxbContext.generateSchema(new SchemaOutputResolver(){
                @Override
                public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    outs.add(out);
                    StreamResult streamResult = new StreamResult(out);
                    streamResult.setSystemId("");  // because of Stream usage
                    return streamResult;
                }});
              // save content of generator
            StreamSource[] sources = new StreamSource[outs.size()];
            for (int i=0; i<outs.size(); i++) {
                ByteArrayOutputStream out = outs.get(i);
                // to examine schema:  System.out.append(new String(out.toByteArray()));
                sources[i] = new StreamSource(new ByteArrayInputStream(out.toByteArray()),"");
            }
              // create schema
            SchemaFactory sf = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
            return sf.newSchema(sources);
        } catch (IOException | SAXException ex) {
            return null;
        }
    }
    
    /**
     * Loads (unmarshalling) config object from config file.
     * @throws JAXBException if unmarshalling error occurs
     */
    public void loadConfig() 
            throws JAXBException {
        
        config = null;
        try {
            Unmarshaller jaxbUnmarsh = jaxbContext.createUnmarshaller();
              // get schema (xsd) of JAXB config object
            jaxbUnmarsh.setSchema(getSchemaOfConfigXml());
            config = (Config) jaxbUnmarsh.unmarshal(configFile.toFile());
        } catch (Exception ex) {
            throw new JAXBException(configFile.getFileName() + ": Invalid XML structure" 
                    + ((ex.getCause() != null) ? " ("+ex.getCause().getMessage() + ") " : ""), 
                    ex);
        }
    }

    /**
     * Saves (marshalling) config object to the given file.
     * @param targetConfigFile the file path of target
     * @throws JAXBException if marshalling error occurs
     * @throws IOException if given target file is <code>null</code>
     */
    public void saveConfig(Path targetConfigFile) 
            throws JAXBException, IOException {
        
        if (targetConfigFile == null) {
            throw new IOException("Target XML file is unknown");
        }
        Marshaller jaxbMarsh = jaxbContext.createMarshaller();

        jaxbMarsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // printing to the console stdout
        //jaxbMarsh.marshal(config, System.out);

        // save to the file
        jaxbMarsh.marshal(config, targetConfigFile.toFile());
    }

}
