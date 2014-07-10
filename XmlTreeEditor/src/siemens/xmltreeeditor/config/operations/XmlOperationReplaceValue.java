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

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class is used to implement <i>node value replacement</i> operation.
 * Basically replaces old value (string) with new one of all nodes in document.
 * @author Martin Brazdil <martin.brazdil at gmail.com>
 */
public class XmlOperationReplaceValue implements XmlOperation {

    /**
     * Replaces value of all nodes.
     * Recursive searching is used.
     * @param nodes the nodes to be searched
     * @param oldValue the value to be replaced
     * @param newValue the replacement value
     */
    private void replaceValueInNodes(NodeList nodes, String oldValue, String newValue) {
        
        if (oldValue == null) {
            oldValue = "";
        }
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if ( (n.getNodeValue() != null && n.getNodeValue().equals(oldValue))
                 || (n.getNodeValue() == null && oldValue.isEmpty()) ) {
                n.setNodeValue(newValue);
            }
            if (n.hasChildNodes()) {
                replaceValueInNodes(n.getChildNodes(), oldValue, newValue);
            }
        }
    }


    /**
     * Replaces value of all nodes of the DOM structure object.
     * @param rootElement the element of document root
     * @param args the variable arguments. Valid input: 
     * <ul>
     *   <li>oldValue ... the old string to be replaced</li>   
     *   <li>newValue ... the new string of replacement</li>    
     * </ul>
     * @throws IllegalArgumentException if arguments are wrong (count or type)
     */
    @Override
    public void execOperation(Element rootElement, Object... args) 
            throws IllegalArgumentException {
        
        if ((rootElement != null && args.length == 2 && (args[0] instanceof String) 
                && (args[1] instanceof String)) == false) {
            throw new IllegalArgumentException("Invalid count or type of arguments");
        }    
        replaceValueInNodes(rootElement.getChildNodes(), (String)args[0], (String)args[1]); 
    }
    
}
