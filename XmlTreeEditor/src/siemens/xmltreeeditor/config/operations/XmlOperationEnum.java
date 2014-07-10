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

package siemens.xmltreeeditor.config.operations;

import java.util.HashMap;
import java.util.Map;
import siemens.xmltreeeditor.XmlTreeEditorSetting;

/**
 *
 * @author Martin Brazdil <martin.brazdil at gmail.com>
 */
public enum XmlOperationEnum {
    
    /**
     *
     */
    OP_UNKNOWN(""),

    /**
     *
     */
    OP_DELETE_NODE(XmlTreeEditorSetting.XML_OP_REMOVE_NODE),

    /**
     *
     */
    OP_REPLACE_VALUE(XmlTreeEditorSetting.XML_OP_REPLACE_VALUE);
        
    /**
     * 
     */
    private static final Map<XmlOperationEnum, XmlOperation> OPERATIONS = new HashMap<>();
    static {
        OPERATIONS.put(OP_UNKNOWN, null);
        OPERATIONS.put(OP_DELETE_NODE, new XmlOperationRemoveNode());
        OPERATIONS.put(OP_REPLACE_VALUE, new XmlOperationReplaceValue());
    }
   
    /**
     * 
     */
    private final String name;

    private XmlOperationEnum(String name) {
        this.name = (name != null) ? name : "";
    }
    
    /**
     *
     * @return
     */
    public XmlOperation getOperation() {
        return OPERATIONS.get(this);
    }

    /**
     *
     * @return
     */
    public String getName() {
        return this.name;
    }
    
    /**
     *
     * @param name
     * @return
     */
    public static XmlOperationEnum findByName(String name){
        for(XmlOperationEnum op : values()) {
            if (op.getName().equals(name)) {
                return op;
            }
        }
        return OP_UNKNOWN; // item of enum is not found
    }
    
}
