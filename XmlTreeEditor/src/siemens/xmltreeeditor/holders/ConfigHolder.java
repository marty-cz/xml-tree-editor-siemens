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

import java.io.IOException;
import java.nio.file.Path;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.w3c.dom.Element;
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
     * Loads (unmarshalling) config object from config file.
     * @throws JAXBException if unmarshalling error occurs
     */
    public void loadConfig() 
            throws JAXBException {
        
        config = null;
        try {
            Unmarshaller jaxbUnmarsh = jaxbContext.createUnmarshaller();
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
