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

import javax.xml.bind.ValidationException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import siemens.xmltreeeditor.XmlTreeEditorSetting;

/**
 *
 * @author Martin Brazdil <martin.brazdil at gmail.com>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Operation {
    
    private String operType;
    
    private String nodeName;
    
    private String oldValue;
    
    private String newValue;
    

    public String getOperType() {
        return operType;
    }

    @XmlAttribute(name = XmlTreeEditorSetting.XML_ATTR_OPER_TYPE)
    public void setOperType(String operType) throws ValidationException {
        for (String oper: XmlTreeEditorSetting.XML_OPS) {
            if (oper.equals(operType)) {
               this.operType = operType; 
               return ;
            }
        }
        throw new ValidationException("Invalid type of operation \"" + operType + "\"");
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getOldValue() {
        return oldValue;
    }

    @XmlAttribute(name = XmlTreeEditorSetting.XML_ATTR_OLD_VAL)
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    @XmlAttribute(name = XmlTreeEditorSetting.XML_ATTR_NEW_VAL)
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
    
    public void doOperation() {
        switch (operType) {
            case XmlTreeEditorSetting.XML_OP_DELETE_NODE: {
                break;   
            }
            case XmlTreeEditorSetting.XML_OP_REPLACE_VAL: {
                break;
            }
        }  
    }
    
}
