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
 *
 * @author Martin Brazdil <martin.brazdil at gmail.com>
 */
public class XmlOperationRemoveNode implements XmlOperation {
       
    /**
     *
     * @param nodes
     * @param name
     */
    private void removeNodes(NodeList nodes, String name) {
        
        for (int i = 0; i < nodes.getLength(); i++) {
            Node n = nodes.item(i);
            if (name != null && n.getNodeName().equals(name)) {
                n.getParentNode().removeChild(n);
            } else if (n.hasChildNodes()) {
                removeNodes(n.getChildNodes(), name);
            }
        }
    }

    /**
     *
     * @param rootElement
     * @param args
     * @throws IllegalArgumentException
     */
    @Override
    public void execOperation(Element rootElement, Object... args) 
            throws IllegalArgumentException {
        
        if ( (rootElement != null && args.length == 1 
              && (args[0] instanceof String) ) == false) {
            throw new IllegalArgumentException("Invalid count or type of arguments");
        }
        removeNodes(rootElement.getChildNodes(), (String) args[0]);
    }

}
