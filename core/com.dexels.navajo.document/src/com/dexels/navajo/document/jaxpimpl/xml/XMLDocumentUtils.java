package com.dexels.navajo.document.jaxpimpl.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.dexels.navajo.document.NavajoFactory;


public class XMLDocumentUtils {

    public static final String DEFAULT_ENCODING = "UTF-8";

    private static javax.xml.parsers.DocumentBuilderFactory builderFactory = null;
    private static javax.xml.transform.TransformerFactory transformerFactory = null;
    
	private static final Logger logger = LoggerFactory
			.getLogger(XMLDocumentUtils.class);
    private static synchronized void createDocumentBuilderFactory() {

        if (builderFactory == null) {
            try {
                builderFactory = DocumentBuilderFactory.newInstance();
            } catch (Exception e) {
            	logger.error("Trouble initializing documentbuilder factory: ",e);
            }
        }
    }

    /**
     * transforms an XML-file and XSL-file into a String
     */
    public static synchronized String transform(Document xmlIn, File xslFile)throws TransformerException {
        createDocumentBuilderFactory();
        if (transformerFactory == null) {
            try {
                 transformerFactory = TransformerFactory.newInstance();
            } catch (java.lang.NoClassDefFoundError e) {
                logger.warn("Could not find XSLT factory, using system default");

                throw NavajoFactory.getInstance().createNavajoException("Could not instantiate XSLT");
            }

        }

        StringWriter sw = new StringWriter();
        Transformer  transformer = null;

        if (xslFile==null) {
              transformer = transformerFactory.newTransformer();
		} else {
              transformer = transformerFactory.newTransformer(new StreamSource(xslFile));
		}

        transformer.setOutputProperty(OutputKeys.ENCODING, DEFAULT_ENCODING);
        transformer.transform(new DOMSource(xmlIn), new StreamResult(sw));

        return sw.toString();
    }

    /**
     * transforms Document into an XML-stream
     * sets 'encoding' to DEFAULT (= 'UTF-16')
     * in case of no DTD-checking dtdPublicId and dtdSystemId may be declared as 'null'
     */
    public static void toXML(Document document, String dtdPublicId, String dtdSystemId, StreamResult result) {
        toXML(document, dtdPublicId, dtdSystemId, DEFAULT_ENCODING, result);
    }

    /**
     * transforms given Document into an XML-stream
     * encoding can be set;
     * output method = 'xml' (indented)
     */
    public static synchronized void toXML(Node document, String dtdPublicId, String dtdSystemId, String encoding, StreamResult result) {
        createDocumentBuilderFactory();
        if (transformerFactory == null) {
            try {
                transformerFactory = TransformerFactory.newInstance();
            } catch (java.lang.NoClassDefFoundError e) {
                logger.warn("Could not find XSLT factory, using system default");
                throw NavajoFactory.getInstance().createNavajoException("Could not instantiate XSLT");
            }

        }

        try {

            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            if (dtdSystemId != null) {
                transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dtdSystemId);
            }
            if (dtdPublicId != null) {
                transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, dtdPublicId);
            }
            transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), result);
        } catch (Exception exception) {
            throw NavajoFactory.getInstance().createNavajoException(exception.getMessage());
        }
    }

    /**
     * an empty Document is created
     */
    public static Document createDocument() {

        createDocumentBuilderFactory();

        try {
            javax.xml.parsers.DocumentBuilder builder = builderFactory.newDocumentBuilder();
            return builder.newDocument();
        } catch (Exception exception) {
        	logger.error("Error: ", exception);
            return null;
        }
    }

    /**
     * an XML-file is read into a Document
     */
    public static Document createDocument(String source) {
        try {
            return createDocument(new FileInputStream(new File(source)), false);
        } catch (FileNotFoundException fnfex) {
            throw NavajoFactory.getInstance().createNavajoException(fnfex.getMessage());
        }
    }

    /**
     * XML-information is read via an inputstream into a Document (DTD validation can be set)
     * @param validation use validation? Actually unused variable
     */
    public static Document createDocument(InputStream source, boolean validation) {

        createDocumentBuilderFactory();

        try {
            javax.xml.parsers.DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(source);
            document.normalize();
            return document;
        } catch (Exception exception) {
            throw NavajoFactory.getInstance().createNavajoException(exception.getMessage());
        } finally {
            // ALWAYS CLOSE STREAM!!
            if (source != null) {
                try {
                    source.close();
                } catch (Exception ex) {
                }
            }

        }
    }

    public static boolean checkDocumentType(Document document, String dtdPublicId) {
        DocumentType documentType = document.getDoctype();

        if (documentType != null) {
            String publicId = documentType.getPublicId();

            return publicId != null && publicId.equals(dtdPublicId);
        }
        return true; // Workaround until DTDs are published
    }

    private static final void writeElement( Writer sw, String value ) throws IOException {
    	sw.write(value);
    }

    private static void printElement(Node n, Writer sw) throws IOException {
        if (n == null) {
            return;
        }
 
        if (n instanceof Element) {
         
            String tagName = n.getNodeName();

            writeElement( sw, "<" + tagName);
            NamedNodeMap map = n.getAttributes();

            if (map != null) {
                for (int i = 0; i < map.getLength(); i++) {
                	writeElement( sw, " ");
                    Attr attr = (Attr) map.item(i);
                    String name = attr.getNodeName();
                    String value = attr.getNodeValue();
                    String sss = XMLutils.XMLEscape(value);

                    writeElement( sw, name + "=\"" + sss+ "\"");
                }
            }
            NodeList list = n.getChildNodes();

            if (list.getLength() > 0) {
            	writeElement( sw, ">");
            }
            else {
            	writeElement( sw, "/>");
            }

            for (int i = 0; i < list.getLength(); i++) {
                Node child = list.item(i);

                printElement(child, sw);
            }
            if (list.getLength() > 0) {
            	writeElement( sw, "</" + tagName + ">\n");
            }

        } else {
            if (n instanceof Text) {
                Text t = (Text)n;
                String s = XMLutils.XMLEscape(t.getData());
                // Should this be escaped?! &amp; etc.
                sw.write(s);
            } else if (n  instanceof Comment) {
            	Comment c = (Comment)n;
                sw.write("<!--"+c.getData()+"-->");
            }
        }
    }

    public static void write( Document d, Writer w, boolean showImplementationAttribute ) {

    	try {
    		if ( showImplementationAttribute ) {
    			d.getDocumentElement().setAttribute("documentImplementation", "JAXP");
    		}
    		printElement ( d.getDocumentElement(), w );
    		w.flush();
    	} catch (IOException e) {
    		logger.error("Error: ", e);
    	}
    }

    public static String toString(Node n) {
        StringWriter sw = new StringWriter();

        try {
			printElement(n, sw);
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
        return sw.toString();
    }

    public static String toString(Document d) {
      return toString(d, false);
    }

    public static String toString(Document d, boolean skipHeader) {
    	StringWriter sw = new StringWriter();

        if (!skipHeader)
          sw.write("<?xml version=\"1.0\" encoding=\"" + DEFAULT_ENCODING + "\"?>\n");
        Node n = d.getFirstChild();

        try {
			printElement(n, sw);
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
        return sw.toString();
    }
}