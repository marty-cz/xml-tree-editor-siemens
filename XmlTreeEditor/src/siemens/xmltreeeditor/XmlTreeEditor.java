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
import java.nio.file.Paths;
import javax.xml.bind.JAXBException;
import siemens.xmltreeeditor.config.Config;
import siemens.xmltreeeditor.config.ConfigHelper;
import siemens.xmltreeeditor.config.Operation;

/**
 *
 * @author Martin Brazdil <martin.brazdil at gmail.com>
 */
public class XmlTreeEditor {
    
    private static void printError(String errMsg, int errCode) {
        System.err.println("Error: " + errMsg);
        if (errCode > 0) {
            System.exit(errCode);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != XmlTreeEditorSetting.CONFIG_ARG_IDX) {
            printError("Invalid arguments count (must have " + XmlTreeEditorSetting.CONFIG_ARG_IDX + " argument)",
                    XmlTreeEditorSetting.COMMON_ERR_CODE);
        }
        try {
            Config conf = ConfigHelper.loadConfigFromXmlFile(Paths.get(args[XmlTreeEditorSetting.CONFIG_ARG_IDX-1]));
            System.out.println("Successfully loaded");
            ConfigHelper.saveConfigToXmlFile(null, conf);
            System.out.println("Successfully saved");
        } catch (IOException | JAXBException ex) {
            printError(args[XmlTreeEditorSetting.CONFIG_ARG_IDX-1] +": " + ex.getMessage(),
                    XmlTreeEditorSetting.COMMON_ERR_CODE);
        }
    }
    
}
