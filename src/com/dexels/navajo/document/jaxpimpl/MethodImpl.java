

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.document.jaxpimpl;

import com.dexels.navajo.document.*;
import org.w3c.dom.*;
import java.util.*;


public final class MethodImpl implements Method {

    /**
     * Public constants.
     */
    public static final String METHOD_DEFINITION = "method";
    public static final String METHOD_REQUIRED = "required";
    public static final String METHOD_NAME = "name";
    public static final String METHOD_DESCRIPTION = "description";
    public static final String METHOD_SERVER = "server";

    public Element ref;

    /**
     * Create a new method (give name and server) for a Navajo document tb.
     */
    public static Method create(Navajo tb, String name, String server) {
        Method p = null;

        Document d = (Document) tb.getMessageBuffer();
        Element n = (Element) d.createElement(MethodImpl.METHOD_DEFINITION);

        p = new MethodImpl(n);
        p.setName(name);
        //p.setServer(server);

        return p;
    }

    /**
     * Instantiate a new method using an existing Element e.
     */
    public MethodImpl(Element e) {
        this.ref = e;
    }

    /**
     * Add a required message to a method using a Message object.
     */
    public void addRequired(Message message) {
        Element e = (Element) ref.getOwnerDocument().createElement(MethodImpl.METHOD_REQUIRED);
        e.setAttribute(MessageImpl.MSG_DEFINITION, message.getName());
        ref.appendChild(e);
    }

    /**
     * Add a required message to a method using a message name.
     */
    public void addRequired(String message) {
        Element e = (Element) ref.getOwnerDocument().createElement(MethodImpl.METHOD_REQUIRED);

        e.setAttribute(MessageImpl.MSG_DEFINITION, message);
        ref.appendChild(e);
    }

    /**
     * Return the name of the method.
     */
    public String getName() {
        return ref.getAttribute(MethodImpl.METHOD_NAME);
    }

    /**
     * Set the name of the method.
     */
    public void setName(String name) {
        ref.setAttribute(MethodImpl.METHOD_NAME, name);
    }

    /**
     * Set the description of the method.
     * @param d
     */
    public void setDescription(String d) {
        ref.setAttribute(MethodImpl.METHOD_DESCRIPTION, d);
    }

    /**
     * Get the description of the method.
     * @param d
     */
    public String getDescription() {
      return ref.getAttribute(MethodImpl.METHOD_DESCRIPTION);
    }

    /**
     * Return the server of the method (URI).
     */
    public String getServer() {
        return ref.getAttribute(MethodImpl.METHOD_SERVER);
    }

    /**
     * Set the server (URI) of the method.
     */
    public void setServer(String server) {
        ref.setAttribute(MethodImpl.METHOD_SERVER, server);
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
                if (list.item(i).getNodeName().equals(MethodImpl.METHOD_REQUIRED)) {
                    Element f = (Element) list.item(i);

                    req.add(index++, f.getAttribute(MessageImpl.MSG_DEFINITION));
                }
            }
        }

        return req;
    }

    public Object getRef() {
      return this.ref;
    }
}
