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
 *
 * @author Martin Brazdil <martin.brazdil at gmail.com>
 */
public class ConfigHolder {
    
    /**
     * 
     */
    private final JAXBContext jaxbContext;
    
    /**
     * 
     */
    private final Path configFile; 
    
    /**
     * 
     */
    private Config config;

    /**
     *
     * @param configFile
     * @throws JAXBException
     * @throws IOException
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
     *
     * @return
     */
    public Config getConfig() {
        return config;
    }
    
    /**
     *
     * @param op
     * @param rootElement
     * @throws IllegalArgumentException
     */
    public void execOperation(Operation op, Element rootElement) 
            throws IllegalArgumentException {
        
        XmlOperationEnum enumOp = op.getXmlOperation();        
        XmlOperation xmlOp = enumOp.getOperation();
        switch (enumOp) {
            case OP_DELETE_NODE: {
                xmlOp.execOperation(rootElement, op.getNodeName());
                break;
            }
            case OP_REPLACE_VALUE: {
                xmlOp.execOperation(rootElement, op.getOldValue(), op.getNewValue());
                break;
            }
            default: break;
        }
    }
    
    /**
     *
     * @throws JAXBException
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
     *
     * @param targetConfigFile
     * @throws JAXBException
     * @throws IOException
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
