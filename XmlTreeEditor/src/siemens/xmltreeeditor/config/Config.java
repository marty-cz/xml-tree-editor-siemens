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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import siemens.xmltreeeditor.XmlTreeEditorSetting;

/**
 *
 * @author Martin Brazdil <martin.brazdil at gmail.com>
 */
@XmlRootElement(name = XmlTreeEditorSetting.XML_NODE_CONFIG)
@XmlAccessorType(XmlAccessType.NONE)
public class Config {
    
    /**
     * 
     */
    private String input = "";
    
    /**
     * 
     */
    private String schema = "";
    
    /**
     * 
     */
    private List<Operation> operations = new ArrayList<>();

    /**
     *
     * @return
     */
    public String getInput() {
        return input;
    }

    /**
     *
     * @param input
     */
    @XmlElement(name = XmlTreeEditorSetting.XML_NODE_INPUT)
    public void setInput(String input) {
        this.input = input;
    }

    /**
     *
     * @return
     */
    public String getSchema() {
        return schema;
    }

    /**
     *
     * @param schema
     */
    @XmlElement(name = XmlTreeEditorSetting.XML_NODE_SCHEMA)
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     *
     * @return
     */
    public List<Operation> getOperations() {
        return operations;
    }

    /**
     *
     * @param operations
     */
    @XmlElementWrapper(name = XmlTreeEditorSetting.XML_NODE_OPERS)
    @XmlElement(name=XmlTreeEditorSetting.XML_NODE_OPER)
    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
    
}
