// package com.dexels.navajo.studio;
package com.dexels.navajo.studio;


import org.w3c.dom.*;
import org.xml.sax.*;

import javax.xml.transform.stream.StreamResult;
import javax.swing.*;
import javax.swing.tree.*;

import java.io.*;
import java.util.*;
import java.net.*;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.xml.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Albert Lo
 * @version 1.0
 */

public class NavajoIOUtil {

    // Data Members

    // xml file location - from user's input
    public static String fileLocation;

    // TABLE META DATA
    public static final String ROOT_TML_TAG = "tml";
    public static final String ROOT_TSL_TAG = "tsl";

    //
    // Methods
    //
    public NavajoIOUtil() {}

    public static Document readXml(String filename) throws NavajoException {
        Document tmldoc = null;// new XmlDocument();

        try {
            FileInputStream input = new FileInputStream(new File(filename));

            tmldoc = XMLDocumentUtils.createDocument(input, false);
            // XMLDocumentUtils.toXML(tmldoc, null, null, new StreamResult(System.out));
        } catch (Exception e) {
            System.err.println("a problem with IO");
            e.printStackTrace(System.out);

        }
        System.err.println("read complete");
        return tmldoc;
    }

    public static void saveXml(JTree jtree, String fileName) {
        // the xml output will be printed in system.out
        // to do: change output to a file with PrintWriter

        DefaultTreeModel model1 = (DefaultTreeModel) jtree.getModel();
        NavajoTreeNode root = (NavajoTreeNode) model1.getRoot();

        try {
            FileOutputStream fo = new FileOutputStream(fileName);
            PrintWriter pw = new PrintWriter(fo);

            printElement(root, pw);

            pw.flush();
            pw.close();

        } catch (Exception e) {
            System.err.println("problem occurd while trying to read destination file");
            e.printStackTrace(System.out);
        }
    }

    private static void printElement(NavajoTreeNode root, PrintWriter pw) {
        try {
            // print the element
            String tagName = root.getTag();
            TreeNode[] nodes = root.getPath();
            String tab = "";

            for (int i = 0; i < nodes.length - 1; i++) {
                System.err.print("  ");
                pw.print("  ");
                tab = tab + "  ";
            }

            String element = "<" + tagName;
            HashMap attributes = root.getAttributes();
            Iterator keys = attributes.keySet().iterator();
            String attributeName = "";
            String attributeValue = "";

            // print the attributes
            while (keys.hasNext()) {
                attributeName = (String) keys.next();
                attributeValue = (String) attributes.get(attributeName);
                attributeValue = XMLutils.XMLEscape(attributeValue);
                element = element + " " + attributeName + "=\"" + attributeValue
                        + "\"";
            }

            // check and print childNodes
            int size = root.getChildCount();
            String value = root.getValue();

            if (size == 0 && value.equals("")) {
                element = element + "/>";
                System.err.println(element);
                pw.println(element);
            } else {
                element = element + ">";
                if (!value.equals("")) {
                    element = element + value;
                }
                System.err.println(element);
                pw.println(element);

                // get the child node and recurse
                for (int i = 0; i < size; i++) {
                    NavajoTreeNode child = (NavajoTreeNode) root.getChildAt(i);

                    printElement(child, pw);

                }

                System.err.print(tab);
                pw.print(tab);

                System.err.println("</" + tagName + ">");
                pw.println("</" + tagName + ">");
            }

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace(System.out);
        }

    }

}
