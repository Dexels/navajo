package com.dexels.navajo.studio;


import javax.swing.tree.*;
// import com.dexels.navajo.xmlobject.*;
import org.w3c.dom.*;

import java.util.*;
import com.dexels.navajo.xml.XMLutils;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Albert Lo
 * @version 1.0
 */

public class NavajoTreeNode extends DefaultMutableTreeNode {

    // Object navajoXmlObject; // this is 1 object class from the package com.dexels.navajo.xmlobject.*;
    protected String tag = "";
    protected String value = "";
    protected HashMap attributes = new HashMap();
    // NamedNodeMap attributes;

    private String parentName = "";

    public NavajoTreeNode() {
        super();
    }

    public NavajoTreeNode(NavajoTreeNode parent, String tag1) {
        super("<" + tag1 + ">");
        // attributes
        tag = tag1;
        if (parent != null)
            parentName = parent.getTag();
        else
            parentName = "";
    }

    public NavajoTreeNode(String tag1) {
        super("<" + tag1 + ">");
        // attributes
        tag = tag1;
    }

    /**
     * @todo Fix the tab and breaks while assigning the values to the node
     */
    public NavajoTreeNode(Node node) {

        super("<" + node.getNodeName() + ">");

        System.out.println("In NavajoTreeNode(): node = " + node.getNodeName());
        Node parent = node.getParentNode();

        if (parent != null)
            parentName = parent.getNodeName();
        else
            parentName = "";

        tag = node.getNodeName();
        // attributes
        NamedNodeMap attributesMap = node.getAttributes();
        for (int i = 0; i < attributesMap.getLength(); i++) {
            Node attribNameNode = attributesMap.item(i);
            putAttributes(attribNameNode.getNodeName(), XMLutils.XMLUnescape(attribNameNode.getNodeValue()));
            System.out.println("attribNameNode: " + attribNameNode.getNodeValue());
        }

        // value - Disabled until a solution found to trim tabs and breaks
        NodeList childs = node.getChildNodes();

        for (int i = 0; i < childs.getLength(); i++) {
            if (childs.item(i).getNodeType() == Node.TEXT_NODE) {
                value = value + childs.item(i).getNodeValue().trim();
            }
        }
        System.err.println("child node value : " + value);
    }

    // public NamedNodeMap getAttributes(){
    // return attributes;
    // }

    public HashMap getAttributes() {
        return attributes;
    }

    public String getAttribute(String name) {
        return (String) attributes.get(name);
    }

    public void putAttributes(String name, String value) {
        if (name != null && value != null) {
            if (!name.equals("")) {
                attributes.put(name, value);
                if (tag.equals("required")) {
                    if (name.equals("message")) {
                        setUserObject("<" + tag + "> " + value);
                    }
                } else if (tag.equals("map")) {
                    if (name.equals("object")) {
                        setUserObject("<" + tag + "> " + value);
                    }
                    if (name.equals("ref")) {
                        // Is ref object of TML?
                        // NavajoTreeNode parentNode = (NavajoTreeNode) this.getParent();
                        if (parentName.equals("field"))
                            setUserObject("<" + tag + "> tml ref:" + "[" + value + "]");
                        else
                            setUserObject("<" + tag + "> object ref: " + "$" + value);
                    }
                } else {
                    if (name.equals("name")) {
                        setUserObject("<" + tag + "> " + value);
                    }
                }
            } else {
                System.err.println("WARNING in NavajoTreeNode : name == empty");
            }
        } else {
            if (name == null) {
                System.err.println("ERROR in NavajoTreeNode : value:" + value + " assigned to attribute: NULL");

            } else {
                System.err.println("ERROR in NavajoTreeNode : value == NULL for atrribute:" + name);
            }
        }
    }

    void setAttributes(HashMap map) {
        attributes = map;
    }

    void setTag(String newTag) {
        tag = newTag;
    }

    public String getTag() {
        return tag;
    }

    public void setValue(String value1) {
        value = value1;
    }

    public Object clone() {
        NavajoTreeNode clone = new NavajoTreeNode(this.getTag());
        HashMap hash = this.getAttributes();
        Iterator iter = hash.keySet().iterator();

        while (iter.hasNext()) {
            String attribute = (String) iter.next();
            String value = (String) hash.get(attribute);

            clone.putAttributes(attribute, value);
        }
        return clone;
    }

    public String getValue() {
        return value;
    }

    public NavajoTreeNode[] getAllChilderen() {
        int childs = getChildCount();
        NavajoTreeNode[] results = new NavajoTreeNode[childs];

        for (int i = 0; i < childs; i++) {
            results[i] = (NavajoTreeNode) getChildAt(i);
        }
        return results;
    }

    /**
     * This function returns the index of the first child with the given search parameter.
     */
    public NavajoTreeNode getFirstChildByTag(String toFind) throws NullPointerException {
        NavajoTreeNode[] childs = getAllChilderen();

        for (int i = 0; i < childs.length; i++) {
            if (childs[i].getTag().equals(toFind)) {
                return childs[i];
            }
        }
        return null;
    }

    public String getParentName() {
        return parentName;
    }

}
