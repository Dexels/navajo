

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.document;


import org.w3c.dom.*;
import java.util.*;


public class Method {

    /**
     * Public constants.
     */
    public static final String METHOD_DEFINITION = "method";
    public static final String METHOD_REQUIRED = "required";
    public static final String METHOD_NAME = "name";
    public static final String METHOD_SERVER = "server";

    public Element ref;

    /**
     * Create a new method (give name and server) for a Navajo document tb.
     */
    public static Method create(Navajo tb, String name, String server) {
        Method p = null;

        Document d = tb.getMessageBuffer();
        Element n = (Element) d.createElement(Method.METHOD_DEFINITION);

        p = new Method(n);
        p.setName(name);
        p.setServer(server);

        return p;
    }

    /**
     * Instantiate a new method using an existing Element e.
     */
    public Method(Element e) {
        this.ref = e;
    }

    /**
     * Add a required message to a method using a Message object.
     */
    public void addRequired(Message message) {
        Element e = (Element) ref.getOwnerDocument().createElement(Method.METHOD_REQUIRED);

        e.setAttribute(Message.MSG_DEFINITION, message.getName());
        ref.appendChild(e);
    }

    /**
     * Add a required message to a method using a message name.
     */
    public void addRequired(String message) {
        Element e = (Element) ref.getOwnerDocument().createElement(Method.METHOD_REQUIRED);

        e.setAttribute(Message.MSG_DEFINITION, message);
        ref.appendChild(e);
    }

    /**
     * Return the name of the method.
     */
    public String getName() {
        return ref.getAttribute(Method.METHOD_NAME);
    }

    /**
     * Set the name of the method.
     */
    public void setName(String name) {
        ref.setAttribute(Method.METHOD_NAME, name);
    }

    /**
     * Return the server of the method (URI).
     */
    public String getServer() {
        return ref.getAttribute(Method.METHOD_SERVER);
    }

    /**
     * Set the server (URI) of the method.
     */
    public void setServer(String server) {
        ref.setAttribute(Method.METHOD_SERVER, server);
    }

    /**
     * Return a list of required message names.
     */
    public ArrayList getRequiredMessages() {

        ArrayList req = null;

        if (this.ref != null) {

            NodeList list = this.ref.getChildNodes();

            if (list.getLength() > 0)
                req = new ArrayList();

            int index = 0;

            for (int i = 0; i < list.getLength(); i++) {
                if (list.item(i).getNodeName().equals(Method.METHOD_REQUIRED)) {
                    Element f = (Element) list.item(i);

                    req.add(index++, f.getAttribute(Message.MSG_DEFINITION));
                }
            }
        }

        return req;
    }
}
