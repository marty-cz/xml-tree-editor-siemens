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
package siemens.xmltreeeditor.holders;

import java.io.IOException;
import java.nio.file.Path;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import siemens.xmltreeeditor.utils.FileUtils;

/**
 * This class provides an access to the input xml file.
 * Xml file is loaded from given path.
 * Classical DOM is used because of XML modification is needed.
 * @author Martin Brazdil <martin.brazdil at gmail.com>
 */
public class DomXmlHolder {

    /**
     * Loaded xml document. 
     */
    private Document xmlDocument;
    
    /**
     * Path of input xml file.
     */
    private final Path xmlFile;

    /**
     * Creates holder.
     * @param xmlFile the input xml file path
     * @throws IOException if input file is invalid
     */
    public DomXmlHolder(Path xmlFile)
            throws IOException {

        FileUtils.verifyFile(xmlFile);
        this.xmlFile = xmlFile;
    }

    /**
     * Do the validation of input xml file according to a defined xsd schema.
     * @param xsdFile the xsd file path
     * @throws IOException if xsd file is invalid
     * @throws SAXException if validation fails
     */
    public void validateXml(Path xsdFile)
            throws IOException, SAXException {

        FileUtils.verifyFile(xsdFile);
          // xsd validation
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsdFile.toFile());

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile.toFile()));
        } catch (SAXException | IOException ex) {
            throw new SAXException(xmlFile.getFileName() + ": Does not correspond with schema \""
                    + xsdFile.getFileName() + "\": " + ex.getMessage(), ex);
        }
    }

    /**
     * Do the parsing of input xml file by DOM specification.
     * @throws SAXException if validation fails
     */
    public void parseXml()
            throws SAXException {

        xmlDocument = null;
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();

              // dont print the exception message !!!
            builder.setErrorHandler(new ErrorHandler() {

                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    throw exception;
                }
                @Override
                public void error(SAXParseException exception) throws SAXException {
                    throw exception;
                }
                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    throw exception;
                }
            });

              // xml parsing
            Document doc = builder.parse(xmlFile.toFile());
              // removes white characters (replaced by space character) 
              // in values and attributes in xml
            doc.getDocumentElement().normalize();
            
            xmlDocument = doc;
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            throw new SAXException(xmlFile.getFileName() + ": Invalid XML structure: "
                    + ex.getMessage(), ex);
        }
    }

    /**
     * Saves XML document object to the file.
     * @param targetXmlFile the file path of target
     * @throws IOException if target path is null
     * @throws TransformerException if DOM to XML transformation fails
     */
    public void saveToXmlFile(Path targetXmlFile)
            throws IOException, TransformerException {

        if (targetXmlFile == null) {
            throw new IOException("Target XML file is unknown");
        }
          // transformation to XML from DOM object
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
              // sets XML options an normalize content of DOM object
            transformer.setOutputProperty(OutputKeys.ENCODING, xmlDocument.getXmlEncoding());
            transformer.setOutputProperty(OutputKeys.VERSION, xmlDocument.getXmlVersion());
            getXmlRootElem().normalize();
              // do the transformation and save it to the file
            DOMSource source = new DOMSource(getXmlRootElem());
            transformer.transform(source, new StreamResult(targetXmlFile.toFile()));
        } catch (TransformerException ex) {
            throw new TransformerException("Can't save DOM XML to file \""
                    + targetXmlFile.getFileName() + "\": " + ex.getMessage(), ex);
        }
    }

    /**
     * Gets a element of document root.
     * @return the element of document root or <code>null</code> if document 
     *         is not loaded
     */
    public Element getXmlRootElem() {
        return (xmlDocument != null) ? xmlDocument.getDocumentElement() : null;
    }

}
