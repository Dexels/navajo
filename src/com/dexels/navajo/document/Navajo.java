package com.dexels.navajo.document;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

import java.util.*;

public interface Navajo extends com.dexels.navajo.persistence.Persistable {

     /**
     * Public constants.
     */

    public static final String METHODS_DEFINITION = "methods";
    public static final String BODY_DEFINITION = "tml";
    public static final String SCRIPT_BODY_DEFINITION = "tsl";
    public static final String MESSAGE_SEPARATOR = "/";
    public static final String PARENT_MESSAGE = "..";

   /**
     * Set the errorDescription class property.
     */
    public void setErrorDescription(String a);

    /**
     * Set the errorNumber class property.
     */
    public void setErrorNumber(int i);

    /**
     * Get the errorDescription class property.
     */

    public String getErrorDescription();

    /**
     * Get the errorNumber class property.
     */
    public int getErrorNumber();

    public ArrayList getCurrentActions();

    public ArrayList getCurrentMessages();

    /**
     * Return all the Method objects in the Navajo document.
     */
    public ArrayList getAllMethods();

    /**
     * DEBUGGING: write the current message and action buffers to
     * a file with a specified postfix filename.
     */
    public String writeDocument(String filename);

    /**
     * DEBUGGING: write a specific message (name) to a file (filename).
     */
    public void writeMessage(String name, String filename) throws NavajoException;

    /**
     * Return the names of the required messages of a specific method (Given the method name).
     */
    public Vector getRequiredMessages(String method);

    /**
     * Return all the Message object of this Navajo document.
     */
    public ArrayList getAllMessages()  throws NavajoException;

    /**
     * Return a method object given a method name.
     */
    public Method getMethod(String name)  throws NavajoException;

    /**
     * Return a arraylist of message objects given a regular expression name.
     */
    public ArrayList getMessages(String name) throws NavajoException;

    /**
     * Return a message object given a message name.
     */
    public Message getMessage(String name);

    public ArrayList getProperties(String regularExpression)  throws NavajoException;

    /**
     * Get the property, give the property path: <message>.[...].<property>.
     * Return null if the property does not exist.
     */
    public Property getProperty(String property);

    /**
     * Get the selection option, given the property path: <message>.[...].<property>:<option>.
     * Return null if the property does not exist.
     */
    public Selection getSelection(String property) throws NavajoException;

    /**
     * Return true if a message with a specific name exists in the Navajo document, else false.
     */
    public boolean contains(String name);

    public Message copyMessage(Message message, Navajo newDocument);

    public Message copyMessage(String name, Navajo newDocument);

    public Method copyMethod(String name, Navajo newDocument);

    public Method copyMethod(Method method, Navajo newDocument);

    public String toString();

    /**
     * Add a method to the Navajo document. If the method name already exists, replace the old one.
     */
    public void addMethod(Method m)  throws NavajoException;

    public Message addMessage(Message message)  throws NavajoException;
    /**
     * Add a message to the Navajo document. If the message name already exists, replace the old one.
     */
    public Message addMessage(Message message, boolean overwrite) throws NavajoException ;

    public void removeMessage(Message message) throws NavajoException ;

    /**
     * Delete a message from a specified message.
     */
    public void removeMessage(String message);

    /**
     * Persistence interface stuff.
     */

    public String persistenceKey();

    public Object getMessageBuffer();

     public void appendDocBuffer(Object d) throws NavajoException;

     public void clearAllSelections() throws NavajoException;

    public void write(java.io.Writer writer)  throws NavajoException;

     public void write(java.io.OutputStream stream)  throws NavajoException;

    public void removeHeader();

    public void addHeader(Header h);

    public Header getHeader();
}