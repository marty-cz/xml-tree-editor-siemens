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

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import siemens.xmltreeeditor.XmlTreeEditorSetting;

/**
 * This class is used to represent a configuration object (file) 
 * with JAXB annotation. 
 * @author Martin Brazdil <martin.brazdil at gmail.com>
 */
@XmlRootElement(name = XmlTreeEditorSetting.XML_NODE_CONFIG)
// Order is important becase of XSD validation!
@XmlType(propOrder = { XmlTreeEditorSetting.XML_NODE_INPUT, 
                       XmlTreeEditorSetting.XML_NODE_SCHEMA,
                       XmlTreeEditorSetting.XML_NODE_OPERS })
@XmlAccessorType(XmlAccessType.NONE)
public class Config {
    
    /**
     * File path of input xml file.
     */
    private String input;
    
    /**
     * File path of xsd file of input xml.
     */
    private String schema;
    
    /**
     * List of supported xml operations.
     * @see siemens.xmltreeeditor.config.operations.XmlOperationEnum
     */
    private List<Operation> operations;

    /**
     * Gets a path of input xml file.
     * The path is relative to placement of config file.
     * @return the path
     */
    public String getInput() {
        return (input == null) ? "" : input;
    }

    /**
     * Sets a path of input xml file.
     * The path is relative to placement of config file.
     * @param input the path
     */
    @XmlElement(name = XmlTreeEditorSetting.XML_NODE_INPUT, required = true)
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * Gets a path of xsd file of input xml.
     * The path is relative to placement of config file.
     * @return the path
     */
    public String getSchema() {
        return (schema == null) ? "" : schema;
    }

    /**
     * Sets a path of xsd file of input xml.
     * The path is relative to placement of config file.
     * @param schema the path
     */
    @XmlElement(name = XmlTreeEditorSetting.XML_NODE_SCHEMA)
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * Gets a list of valid operations to be used on input xml file.
     * @return the list of operations
     */
    public List<Operation> getOperations() {
        return operations;
    }

    /**
     * Sets a list of valid operations to be used on input xml file.
     * @param operations the list of operations
     */
    @XmlElementWrapper(name = XmlTreeEditorSetting.XML_NODE_OPERS)
    @XmlElement(name=XmlTreeEditorSetting.XML_NODE_OPER)
    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
    
}
