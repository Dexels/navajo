package com.dexels.navajo.document;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

import java.util.ArrayList;

public interface Method extends java.io.Serializable {
   /**
     * Add a required message to a method using a Message object.
     */
    public void addRequired(Message message);

    /**
     * Add a required message to a method using a message name.
     */
    public void addRequired(String message);

    /**
     * Return the name of the method.
     */
    public String getName();

    /**
     * Set the name of the method.
     */
    public void setName(String name);

    /**
     * Set the description of the method.
     * @param d
     */
    public void setDescription(String d);

    /**
     * Get the description of the method.
     * @param d
     */
    public String getDescription();

    /**
     * Return the server of the method (URI).
     */
    public String getServer();

    /**
     * Set the server (URI) of the method.
     */
    public void setServer(String server);

    /**
     * Return a list of required message names.
     */
    public ArrayList getRequiredMessages();

      /**
     * Return the internal implementation specific representation of the Message.
     *
     * @return
     */
    public Object getRef();

}