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

package siemens.xmltreeeditor;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.xml.sax.SAXException;
import siemens.xmltreeeditor.config.Config;
import siemens.xmltreeeditor.holders.ConfigHolder;
import siemens.xmltreeeditor.holders.DomXmlHolder;

/**
 *
 * @author Martin Brazdil <martin.brazdil at gmail.com>
 */
public class XmlTreeEditor {
    
    /**
     * Prints an error message and exit the program (if errCode &gt; 0).
     * @param errMsg the message to be printed
     * @param errCode the exit code
     */
    private static void printError(String errMsg, int errCode) {
        System.err.println("Error: " + errMsg);
        if (errCode > XmlTreeEditorSetting.OK_ERR_CODE) {
            System.exit(errCode);
        }
    }

    /**
     * Perform the XML editting by config file.
     * Path of config file is signle mandatory argument.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
          // verify input arguments
        if (args.length != XmlTreeEditorSetting.CONFIG_ARG_IDX) {
            printError("Invalid arguments count (must have " + XmlTreeEditorSetting.CONFIG_ARG_IDX + " arguments)",
                    XmlTreeEditorSetting.COMMON_ERR_CODE);
        }
          // xml editing
        try {
              // load config file and parse it
            Path configPath = Paths.get(args[XmlTreeEditorSetting.CONFIG_ARG_IDX-1]);
            ConfigHolder configHolder = new ConfigHolder(configPath);
            configHolder.loadConfig();
            Config config = configHolder.getConfig();      
              // absolute path to the config file
            String configParentPath = configPath.getParent().toString() + System.getProperty("file.separator");
            
              // create a xml holder for input xml file
            DomXmlHolder domXmlHolder = new DomXmlHolder(Paths.get(configParentPath + config.getInput()));
              // parsing input xml file
            domXmlHolder.parseXml();
              // validating input xml file accordig to the given xsd schema file  
            if (config.getSchema().isEmpty() == false) {
                try {
                    domXmlHolder.validateXml(Paths.get(configParentPath + config.getSchema()));
                    System.err.println(String.format("Input xml file \"%s\" is valid according to schema \"%s\"",
                            config.getInput(), config.getSchema()));
                } catch (IOException | SAXException ex) {
                    printError(ex.getMessage(), XmlTreeEditorSetting.OK_ERR_CODE);  // do not exit !
                }     
            }
              // execution of xml modification operations (sets at config file)
            configHolder.execAllOperations(domXmlHolder.getXmlRootElem());
              // save the result to the new file
            domXmlHolder.saveToXmlFile(Paths.get(configParentPath 
                    + XmlTreeEditorSetting.getXmlResultPath(config.getInput())));
    
        } catch (Exception ex) {
            // ex.printStackTrace();
            // print error end exit
            printError(ex.getMessage(), XmlTreeEditorSetting.COMMON_ERR_CODE);
        } 
    }
    
}
