package com.dexels.navajo.install;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public class Tools {

    public static String DEFAULT_ENCODING = "UTF-8";

    private static javax.xml.parsers.DocumentBuilderFactory builderFactory = null;
    private static javax.xml.parsers.DocumentBuilder builder = null;
    private static javax.xml.transform.TransformerFactory transformerFactory = null;

    static {

        if (builderFactory == null) {
            try {
                builderFactory = new org.apache.crimson.jaxp.DocumentBuilderFactoryImpl();
                System.out.println("factory instance: " + builderFactory);
                builder = builderFactory.newDocumentBuilder();
                System.out.println("builder instance: " + builder);
            } catch (Exception e) {
                System.out.println("Could not find XML parser, using system default");
                // builderFactory = DocumentBuilderFactory.newInstance();
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
            Node n = (Node) list.item(i);

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

    private static String searchAndReplace(String text, String tag, String replace) {

        StringBuffer result;
        String before = "", last = "";
        int start, end;

        result = new StringBuffer();

        start = 0;
        end = -1;

        start = text.indexOf(tag, end);

        if (start == -1)
            return text;

        while (start != -1) {

            before = text.substring(end + 1, start);
            end = text.indexOf("}", start);

            result.append(before);
            result.append(replace);

            start = text.indexOf(tag, end);
            if (start == -1)
                last = text.substring(end + 1, text.length());
            result.append(last);
        }

        return result.toString();

    }

    /**
     * Create an XML document from a file.
     *
     * @param source
     * @return
     * @throws NavajoException
     */
    public static Document createDocument(String source) throws Exception {
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
    private static Document createDocument(InputStream source, boolean validation) throws Exception {

        Document document = builder.parse(source);

        document.normalize();

        return document;

    }

    /**
     * This method copies fileIn to fileOut while replacing tokens with a value.
     * Tokens are named: ${<TOKEN_NAME>} and are stored in a hashmap tokens as key-value pairs.
     *
     * @param fileIn
     * @param fileOut
     * @param tokens
     */
    public static void copyAndReplaceTokens(String fileIn, String fileOut, HashMap tokens) throws IOException, FileNotFoundException {

        BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(new FileInputStream(new File(fileIn))));
        FileWriter fos = new FileWriter(new File(fileOut));

        String line = "";

        while ((line = reader.readLine()) != null) {
            if (tokens != null) {
                Iterator allTokens = tokens.keySet().iterator();

                while (allTokens.hasNext()) {
                    String token = allTokens.next().toString();
                    String value = tokens.get(token).toString();

                    line = searchAndReplace(line, "{" + token + "}", value);
                }
            }
            fos.write(line + "\n");
        }
        fos.close();

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
        java.util.zip.ZipInputStream zipIn = new java.util.zip.ZipInputStream(new java.io.FileInputStream(jarFile));

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
        zipIn.close();
    }

    public static void mkDir(String dir) {
        File f = new File(dir);

        f.mkdir();
    }

    public static void main(String args[]) throws Exception {
        Document d = builder.parse(new File("/usr/local/orion/config/server.xml"));

        System.out.println(d);
        System.out.println(xmlToString(d));
    }
}
