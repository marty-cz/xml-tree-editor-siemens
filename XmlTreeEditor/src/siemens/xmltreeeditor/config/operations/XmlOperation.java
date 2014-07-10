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

/**
 * This class is an interface of common XML modifying operations. 
 * Operations are using a DOM structured object.
 * @author Martin Brazdil <martin.brazdil at gmail.com>
 */
public interface XmlOperation {
    
    /**
     * Executes implemented operation over the DOM structure object.
     * @param rootElement the element of document root
     * @param args the variable arguments. To the specific explanation see 
     *             implementation classes.
     * @throws IllegalArgumentException if arguments are wrong (count or type)
     */
    void execOperation(Element rootElement, Object... args)
            throws IllegalArgumentException;
    
}
