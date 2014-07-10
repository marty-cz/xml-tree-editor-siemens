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
import siemens.xmltreeeditor.config.operations.XmlOperationEnum;

/**
 * This class is used to represent a operation defined at 
 * object (file) with JAXB annotation. 
 * @author Martin Brazdil <martin.brazdil at gmail.com>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Operation {
    
    /**
     * Operation type.
     * @see siemens.xmltreeeditor.XmlTreeEditorSetting 
     */
    private String operType;
    
    /**
     * Name of node.
     */
    private String nodeName;
    
    /**
     * Old value to be replaced.
     */
    private String oldValue;
    
    /**
     * New value of the replacement.
     */
    private String newValue;
    
    /**
     * Item of supported operation in enumeration.
     * This attribute is used only for internal needs.
     */
    private XmlOperationEnum operation = XmlOperationEnum.OP_UNKNOWN;
    
    /**
     * Gets a type/name of supported xml operation.
     * @return the name of operation
     */
    public String getOperType() {
        return (operType == null) ? "" : operType;
    }

    /**
     * Sets a type/name of operation.
     * @param operType the type/name of operation
     * @throws ValidationException if given operation is not supported
     */
    @XmlAttribute(name = XmlTreeEditorSetting.XML_ATTR_OPER_TYPE)
    public void setOperType(String operType) 
            throws ValidationException {
        
        
        this.operation = XmlOperationEnum.findByName(operType);
        if (this.operation == XmlOperationEnum.OP_UNKNOWN) {
            throw new ValidationException("Invalid type of operation \"" + operType + "\"");
        }
        this.operType = operType; 
    }
    
    /**
     * Gets linked object of xml operation.
     * @return null if operation is not supported, otherwise the xml operation object
     */
    public XmlOperationEnum getXmlOperation() {
        return operation;
    }

    /**
     * Gets a name of nodes to be removed.
     * @return the name of nodes
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * Sets a name of nodes to be removed.
     * @param nodeName the name of nodes
     */
    @XmlAttribute(name = XmlTreeEditorSetting.XML_ATTR_NODE_NAME)
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * Gets an old value of node to be replaced.
     * @return the value of node
     */
    public String getOldValue() {
        return oldValue;
    }

    /**
     * Sets an old value of node to be replaced.
     * @param oldValue the value of node
     */
    @XmlAttribute(name = XmlTreeEditorSetting.XML_ATTR_OLD_VAL)
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    /**
     * Gets a new value of node of the replacement.
     * @return the value of node
     */
    public String getNewValue() {
        return newValue;
    }

    /**
     * Sets a new value of node of the replacement.
     * @param newValue the value of node
     */
    @XmlAttribute(name = XmlTreeEditorSetting.XML_ATTR_NEW_VAL)
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
    
}
