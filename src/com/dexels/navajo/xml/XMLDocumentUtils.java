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
    private static javax.xml.parsers.DocumentBuilder builder = null;
    private static javax.xml.transform.TransformerFactory transformerFactory = null;

    static {

        String encoding = System.getProperty("encoding");
        if (encoding == null)
          encoding = "UTF-8";
        DEFAULT_ENCODING = encoding;

        System.out.println("encoding = " + encoding);

        try {
              System.out.println("Trying to use Xerces DocumentBuilderFactory instance");
              //builderFactory =  new org.apache.xerces.jaxp.DocumentBuilderFactoryImpl();
              builderFactory = DocumentBuilderFactory.newInstance();
              System.out.println("factory instance: " + builderFactory);
              builder = builderFactory.newDocumentBuilder();
              System.out.println("builder instance: " + builder);
        } catch (Exception e) {
              System.out.println("Could not find XML parser, using system default");
              builderFactory = DocumentBuilderFactory.newInstance();
        }

        try {
            System.out.println("Trying to use Xalan TransformerFactory instance");
            //transformerFactory = new org.apache.xalan.processor.TransformerFactoryImpl();
            transformerFactory = TransformerFactory.newInstance();
            System.out.println("factory instance: " + transformerFactory);
        } catch (java.lang.NoClassDefFoundError e) {
            System.out.println("Could not find XSLT factory, using system default");
            transformerFactory = TransformerFactory.newInstance();
        }
    }

    /**
     * transforms an XML-file and XSL-file into a String
     */
    public static String transform(Document xmlIn, File xslFile)throws IOException,
                                                                     ParserConfigurationException,
                                                                     TransformerConfigurationException,
                                                                     TransformerException {

        StringWriter sw          = new StringWriter();
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
    public static void toXML(Document document, String dtdPublicId, String dtdSystemId, String encoding, StreamResult result)
    throws com.dexels.navajo.document.NavajoException {
        try {

            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            if (dtdSystemId != null) {
                transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dtdSystemId);
            }
            if (dtdPublicId != null){
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

        try {

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
            return createDocument( new FileInputStream( new File(source) ), false );
        }
        catch (FileNotFoundException fnfex) {
            fnfex.printStackTrace(System.err);
            throw new com.dexels.navajo.document.NavajoException(fnfex.getMessage());
        }
    }

    /**
     * XML-information is read via an inputstream into a Document (DTD validation can be set)
     */
    public static Document createDocument(InputStream source, boolean validation) throws com.dexels.navajo.document.NavajoException {


        try {
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
        //return false;
    }

}