package com.dexels.navajo.xml;


import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.Locale;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;


public class XMLDocumentUtils {

    public static String DEFAULT_ENCODING = "UTF-8";

    private static javax.xml.parsers.DocumentBuilderFactory builderFactory = null;
    private static javax.xml.transform.TransformerFactory transformerFactory = null;

    private static synchronized void createDocumentBuilderFactory() {

        if (builderFactory == null) {
            try {
                System.out.println("Trying to use Xerces DocumentBuilderFactory instance");
                builderFactory = DocumentBuilderFactory.newInstance();
                System.out.println("factory instance: " + builderFactory);
            } catch (Exception e) {
                System.out.println("Could not find XML parser, using system default");
                // builderFactory = DocumentBuilderFactory.newInstance();
            }
        }

    }

    /**
     * transforms an XML-file and XSL-file into a String
     */
    public static String transform(Document xmlIn, File xslFile)throws IOException,
            ParserConfigurationException,
            TransformerConfigurationException,
            TransformerException, com.dexels.navajo.document.NavajoException {
        createDocumentBuilderFactory();
        if (transformerFactory == null) {
            try {
                System.out.println("Trying to use Xalan TransformerFactory instance");
                // transformerFactory = new org.apache.xalan.processor.TransformerFactoryImpl();
                transformerFactory = TransformerFactory.newInstance();
                System.out.println("factory instance: " + transformerFactory);
            } catch (java.lang.NoClassDefFoundError e) {
                System.out.println("Could not find XSLT factory, using system default");
                throw new com.dexels.navajo.document.NavajoException("Could not instantiate XSLT");
            }

        }

        StringWriter sw = new StringWriter();
        Transformer  transformer = transformerFactory.newTransformer(new StreamSource(xslFile));

        transformer.setOutputProperty(OutputKeys.ENCODING, DEFAULT_ENCODING);
        transformer.transform(new DOMSource(xmlIn), new StreamResult(sw));

        return sw.toString();
    }

    /**
     * transforms Document into an XML-stream
     * sets 'encoding' to DEFAULT (= 'UTF-16')
     * in case of no DTD-checking dtdPublicId and dtdSystemId may be declared as 'null'
     */
    public static void toXML(Document document, String dtdPublicId, String dtdSystemId, StreamResult result)
            throws com.dexels.navajo.document.NavajoException {
        toXML(document, dtdPublicId, dtdSystemId, DEFAULT_ENCODING, result);
        return;
    }

    /**
     * transforms given Document into an XML-stream
     * encoding can be set;
     * output method = 'xml' (indented)
     */
    public static void toXML(Document document, String dtdPublicId, String dtdSystemId, String encoding,
                              StreamResult result)
            throws com.dexels.navajo.document.NavajoException {

        createDocumentBuilderFactory();
        if (transformerFactory == null) {
            try {
                System.out.println("Trying to use Xalan TransformerFactory instance");
                // transformerFactory = new org.apache.xalan.processor.TransformerFactoryImpl();
                transformerFactory = TransformerFactory.newInstance();
                System.out.println("factory instance: " + transformerFactory);
            } catch (java.lang.NoClassDefFoundError e) {
                System.out.println("Could not find XSLT factory, using system default");
                throw new com.dexels.navajo.document.NavajoException("Could not instantiate XSLT");
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
            exception.printStackTrace(System.err);
            throw new com.dexels.navajo.document.NavajoException(exception.getMessage());
        }
        return;
    }

    /**
     * an empty Document is created
     */
    public static Document createDocument() throws com.dexels.navajo.document.NavajoException {

        createDocumentBuilderFactory();

        try {
            javax.xml.parsers.DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.newDocument();

            return document;
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
            throw new com.dexels.navajo.document.NavajoException(exception.getMessage());
        }
    }

    /**
     * an XML-file is read into a Document
     */
    public static Document createDocument(String source) throws com.dexels.navajo.document.NavajoException {
        try {
            return createDocument(new FileInputStream(new File(source)), false);
        } catch (FileNotFoundException fnfex) {
            fnfex.printStackTrace(System.err);
            throw new com.dexels.navajo.document.NavajoException(fnfex.getMessage());
        }
    }

    /**
     * XML-information is read via an inputstream into a Document (DTD validation can be set)
     */
    public static Document createDocument(InputStream source, boolean validation) throws com.dexels.navajo.document.NavajoException {

        createDocumentBuilderFactory();

        try {
            javax.xml.parsers.DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(source);

            return document;
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
            throw new com.dexels.navajo.document.NavajoException(exception.getMessage());
        }
    }

    public static boolean checkDocumentType(Document document, String dtdPublicId) {
        DocumentType documentType = document.getDoctype();

        if (documentType != null) {
            String publicId = documentType.getPublicId();

            return publicId != null && publicId.equals(dtdPublicId);
        }
        return true; // Workaround until DTDs are published
        // return false;
    }

    private static String printElement(Node n) {

        if (n == null)
            return "";

        if (n instanceof Element) {

            StringBuffer result = new StringBuffer();
            String tagName = n.getNodeName();

            result.append("<" + tagName);
            NamedNodeMap map = n.getAttributes();

            if (map != null) {
                for (int i = 0; i < map.getLength(); i++) {
                    result.append(" ");
                    Attr attr = (Attr) map.item(i);
                    String name = attr.getNodeName();
                    String value = attr.getNodeValue();

                    result.append(name + "=\"" + value + "\"");
                }
            }
            NodeList list = n.getChildNodes();

            if (list.getLength() > 0)
                result.append(">\n");
            else
                result.append("/>\n");

            for (int i = 0; i < list.getLength(); i++) {
                Node child = list.item(i);

                result.append(printElement(child));
            }
            if (list.getLength() > 0)
                result.append("</" + tagName + ">\n");
            return result.toString();
        } else {
            return "";
        }
    }

    public static String toString(Node n) {
        StringBuffer result = new StringBuffer();

        result.append(printElement(n));
        return result.toString();
    }

    public static String toString(Document d) {
      return toString(d, false);
    }

    public static String toString(Document d, boolean skipHeader) {
        StringBuffer result = new StringBuffer();

        if (!skipHeader)
          result.append("<?xml version=\"1.0\" encoding=\"" + DEFAULT_ENCODING + "\"?>\n");
        Node n = d.getFirstChild();

        result.append(printElement(n));
        return result.toString();
    }

    public static void main(String args[]) throws Exception {
        com.dexels.navajo.document.Navajo doc = new com.dexels.navajo.document.Navajo();
        com.dexels.navajo.document.Message msg = com.dexels.navajo.document.Message.create(doc, "dexels");

        doc.addMessage(msg);
        com.dexels.navajo.document.Property prop = com.dexels.navajo.document.Property.create(doc, "aap",
                com.dexels.navajo.document.Property.INTEGER_PROPERTY, "", 0, "",
                com.dexels.navajo.document.Property.DIR_IN);

        msg.addProperty(prop);
        System.out.println(doc.toString());
    }
}
