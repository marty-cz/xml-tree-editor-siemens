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

/**
 * This class containes properties of editor (i.e. constants).
 * @author Martin Brazdil <martin.brazdil at gmail.com>
 */
public class XmlTreeEditorSetting {
    
    /**
     * Index of argument field <code>args</code> of <code>main()</code>
     * function.
     */
    public static final int CONFIG_ARG_IDX = 1;
    
    /**
     * OK exit status in case of everything is ok.
     */
    public static final int OK_ERR_CODE = 0;
    
    /**
     * Common exit status in case of error occurs.
     */
    public static final int COMMON_ERR_CODE = 1;
    
    /**
     * Name of root node of config file.
     */
    public static final String XML_NODE_CONFIG = "config";

    /**
     * Name of node which specifies an input xml file path.
     */
    public static final String XML_NODE_INPUT  = "input";

    /**
     * Name of node which specifies a xsd schema file path 
     * of input xml file.
     */
    public static final String XML_NODE_SCHEMA = "schema";

    /**
     * Name of node which specifies a list of xml operations.
     */
    public static final String XML_NODE_OPERS  = "operations";

    /**
     * Name of node which specifies an operation record.
     */
    public static final String XML_NODE_OPER   = "operation";
    
    /**
     * Name of attribute of <code>operation</code> node 
     * which specifies a type of operation.
     * @see siemens.xmltreeeditor.config.operations.XmlOperationEnum
     */
    public static final String XML_ATTR_OPER_TYPE = "operType";

    /**
     * Name of attribute of <code>operation</code> node 
     * which specifies a node name (delete operation).
     */
    public static final String XML_ATTR_NODE_NAME = "nodeName";

    /**
     * Name of attribute of <code>operation</code> node 
     * which specifies an <i>old</i> value of node (replace operation).
     */
    public static final String XML_ATTR_OLD_VAL   = "oldValue";

    /**
     * Name of attribute of <code>operation</code> node 
     * which specifies a <i>new</i> value of node (replace operation).
     */
    public static final String XML_ATTR_NEW_VAL   = "newValue";
    
   
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  
//            Names of types of supported xml operations    
// * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    /**
     * Name of type of recursive remove node operation.
     * @see siemens.xmltreeeditor.config.operations.XmlOperationRemoveNode
     */
    public static final String XML_OP_REMOVE_NODE = "removeNode";

    /**
     * Name of type of value replace operation.
     * @see siemens.xmltreeeditor.config.operations.XmlOperationReplaceValue
     */
    public static final String XML_OP_REPLACE_VALUE = "replaceValue";
    
    /**
     * Gets the result xml output pat.h
     * @param inputPath the path of input xml file
     * @return the path or <code>null</code> if <code>inputPath</code> is <code>null</code>
     */
    public static final String getXmlResultPath(String inputPath) {
        return (inputPath != null) ? inputPath+"2" : null;
    }
    
}
