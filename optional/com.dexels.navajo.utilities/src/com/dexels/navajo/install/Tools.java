package com.dexels.navajo.install;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexels.navajo.document.NavajoException;

public class Tools {

    public static final String DEFAULT_ENCODING = "UTF-8";

    private static javax.xml.parsers.DocumentBuilderFactory builderFactory = null;
    private static javax.xml.parsers.DocumentBuilder builder = null;
   // private static javax.xml.transform.TransformerFactory transformerFactory = null;
    
	private final static Logger logger = LoggerFactory.getLogger(Tools.class);
	
    static {

        if (builderFactory == null) {
            try {
                builderFactory = DocumentBuilderFactory.newInstance(); //new org.apache.crimson.jaxp.DocumentBuilderFactoryImpl();
                builder = builderFactory.newDocumentBuilder();
            } catch (Exception e) {
                logger.warn("Could not find XML parser, using system default",e);
            }
        }

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

    public static String xmlToString(Document d) {
        StringBuffer result = new StringBuffer();

        result.append("<?xml version=\"1.0\" encoding=\"" + DEFAULT_ENCODING + "\"?>\n");
        // Node n = d.getC
        NodeList list = d.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            Node n = list.item(i);

            result.append(printElement(n));
        }
        return result.toString();
    }

    public static Node findNode(Document d, String name) {

        Node body = d.getDocumentElement();

        return actualFindNode(body, name);
    }

    private static Node actualFindNode(Node node, String name) {

        if (node.getNodeName().equals(name)) {
            return node;
        }
        if (node.hasChildNodes()) {
            NodeList list = node.getChildNodes();
            int size = list.getLength();

            for (int i = 0; i < size; i++) {
                Node found = actualFindNode(list.item(i), name);

                if (found != null) return found;
            }
        }
        return null;
    }

    /**
     * Create an XML document from a file.
     *
     * @param source
     * @return
     * @throws NavajoException
     * @deprecated
     */
    public static Document createDocument(String source) throws FileNotFoundException, java.io.IOException,
                                      org.xml.sax.SAXException {
            return createDocument(new FileInputStream(new File(source)), false);
    }

    /**
     * XML-information is read via an inputstream into a Document (DTD validation can be set)
     * @deprecated
     */
    private static Document createDocument(InputStream source, boolean validation) throws java.io.IOException,
                                      org.xml.sax.SAXException
    {

        Document document = builder.parse(source);
        document.normalize();
        source.close();

        return document;

    }



    /**
     * This method extract a jar file to a specified directory.
     *
     * @param jarFile
     * @param extractPath
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void extractJar(String jarFile, String extractPath) throws FileNotFoundException, IOException {
        FileInputStream fis = new java.io.FileInputStream(jarFile);
        
        java.util.zip.ZipInputStream zipIn = new java.util.zip.ZipInputStream(fis);
try {
        byte[] buf = new byte[1024];
        int len;
        java.util.zip.ZipEntry entry = null;

        while ((entry = zipIn.getNextEntry()) != null) {
            if (entry.isDirectory()) {
                java.io.File zipDir = new java.io.File(extractPath + "/" + entry.getName());

                zipDir.mkdir();
            } else {
                java.io.BufferedOutputStream outFile = new java.io.BufferedOutputStream(new java.io.FileOutputStream(extractPath + "/" + entry.getName()));

                while ((len = zipIn.read(buf)) != -1) {
                    outFile.write(buf, 0, len);
                }
                outFile.close();
            }
        }
        fis.close();
} finally {
	zipIn.close();
}
    }

    public static void mkDir(String dir) {
        File f = new File(dir);

        f.mkdir();
    }

    public static void main(String args[]) throws Exception {
//        Document d = builder.parse(new File("/usr/local/orion/config/server.xml"));
//
//        System.out.println(d);
//        System.out.println(xmlToString(d));
    }
}
