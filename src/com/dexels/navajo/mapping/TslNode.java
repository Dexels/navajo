package com.dexels.navajo.mapping;

import java.util.*;
import com.dexels.navajo.xml.*;
import org.w3c.dom.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Albert Lo
 * @version $Id$ 
 */

public class TslNode {
    private Vector nodes = new Vector();    // the childnodes
    private Map attributes = new HashMap(); // the attributes
    private String tag = "";                     // the tag name ie. <tsl>, <map>, <field>
    private Hashtable attrib = new Hashtable();
    private Node node;

    private static final String COMMENT_NODE = "comment";

    public TslNode(Document doc) {
        NodeList tmpNodeList = doc.getElementsByTagName("tsl");

        node = tmpNodeList.item(0);

        // set tag
        tag = node.getNodeName();
        // set childnodes
        if (node.hasChildNodes()) {
            NodeList childNodes = node.getChildNodes();

            for (int i = 0; i < childNodes.getLength(); i++) {
                Node childnode = childNodes.item(i);

                if (childnode.getNodeType() == childnode.ELEMENT_NODE) {
                    TslNode newNode = new TslNode(childnode);

                    nodes.add(newNode);
                    // System.err.println("add newNode: " + newNode.getTagName());
                }
            }
        }

        // set attributes
        NamedNodeMap nnm = node.getAttributes();

        for (int i = 0; i < nnm.getLength(); i++) {
            Node no = nnm.item(i);

            // System.err.println("!! debug!!! setting: " + no.getNodeName() + " -> "+no.getNodeValue());
            setAttribute(no.getNodeName(), no.getNodeValue());
        }
        // System.err.println("setting attributes completed for node " + tag);
    }

    public Node getNode() {
      return node;
    }

    public TslNode(Node node) {
        // set tag
        tag = node.getNodeName();
        this.node = node;
        // set childnodes
        if (node.hasChildNodes()) {
            NodeList childNodes = node.getChildNodes();

            for (int i = 0; i < childNodes.getLength(); i++) {
                Node childnode = childNodes.item(i);

                if (childnode.getNodeType() == childnode.ELEMENT_NODE) {
                    if (!childnode.getNodeName().equals(COMMENT_NODE)) { // Skip comment tags
                        TslNode newNode = new TslNode(childnode);

                        nodes.add(newNode);
                        // System.err.println("add newNode: " + newNode.getTagName());
                    }
                }
            }
        }

        // set attributes
        NamedNodeMap nnm = node.getAttributes();

        for (int i = 0; i < nnm.getLength(); i++) {
            Node no = nnm.item(i);

            // System.err.println("!! debug!!! setting: " + no.getNodeName() + " -> "+no.getNodeValue());
            setAttribute(no.getNodeName(), no.getNodeValue());
        }
        // System.err.println("setting attributes completed for node " + tag);
    }

    public String getTagName() {
        return tag;
    }

    public String getAttribute(String name) {
        String result = (String) attributes.get(name);
        if (result == null)
            return "";
        return XMLutils.XMLUnescape(result);
    }

    public void setAttribute(String name, String value) {
        attributes.put(name, value);
        attributes.put(value, name);
    }

    public int getNodesSize() {
        return nodes.size();
    }

    public String toString() {
        String result = "<" + this.getTagName();
        NamedNodeMap nnm = node.getAttributes();

        for (int i = 0; i < nnm.getLength(); i++) {
            Node no = nnm.item(i);

            result += " " + no.getNodeName() + "=\"" + no.getNodeValue() + "\"";
        }
        result += ">";
        return result;
    }

    public TslNode getNode(int i) {
        TslNode result = (TslNode) nodes.elementAt(i);

        return result;
    }

    public TslNode getFirstNode() {
        return (TslNode) nodes.firstElement();
    }

    public Vector getAllNodes() {
        return nodes;
    }

    public TslNode getNodeByType(String tag) {
        for (int i = 0; i < nodes.size(); i++) {
            TslNode tmp = (TslNode) nodes.elementAt(i);

            if (tmp.getTagName().equals(tag)) {
                return tmp;
            }
        }
        return null;
    }

}

