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

package siemens.xmltreeeditor.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Martin Brazdil <martin.brazdil at gmail.com>
 */
public class ConfigHelper {
    
    public static Config loadConfigFromXmlFile(Path configFile) 
            throws IOException, JAXBException {
        
        if (Files.isReadable(configFile) == false) {
          throw new IOException("File is not readable");
        }
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(OperationDeleteNode.class, OperationReplaceValue.class, Config.class);
            Unmarshaller jaxbUnmarsh = jaxbContext.createUnmarshaller();
            return (Config) jaxbUnmarsh.unmarshal(configFile.toFile());
        } catch (JAXBException ex) {
            ex.printStackTrace(System.out);
            throw new JAXBException("Invalid XML structure " + ex.getMessage(), ex);
        }
    }
    
    public static void saveConfigToXmlFile(Path configFile, Config config) throws JAXBException {
        
        JAXBContext jaxbContext = JAXBContext.newInstance(OperationDeleteNode.class, OperationReplaceValue.class, Config.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        //Marshal the employees list in console
        jaxbMarshaller.marshal(config, System.out);

        //Marshal the employees list in file
       // jaxbMarshaller.marshal(employees, new File("c:/temp/employees.xml"));
    }
    
}
