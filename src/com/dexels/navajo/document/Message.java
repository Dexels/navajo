package com.dexels.navajo.document;

import java.util.*;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public interface Message {

    /**
     * Message attributes/constants.
     */
    public static String MSG_DEFINITION = "message";
    public static final String MSG_NAME = "name";
    public static final String MSG_INDEX = "index";
    public static final String MSG_TYPE = "type";
    public static final String MSG_PARAMETERS_BLOCK = "__parms__";

    public static final String MSG_TYPE_SIMPLE = "simple";
    public static final String MSG_TYPE_ARRAY = "array";

    public static final String MSG_MODE = "mode";
    public static final String MSG_MODE_LAZY = "lazy";

    public static final String MSG_LAZY_COUNT = "lazy_total";
    public static final String MSG_LAZY_REMAINING = "lazy_remaining";
    public static final String MSG_ARRAY_SIZE = "array_size";

    public String getName();

    /**
      * Return the parent message if there is one.
      * @return
      */
    public Message getParentMessage();

    /**
     * Return the fully qualified Navajo message name.
     */
    public String getFullMessageName();

    /**
     * Set the type of a message.
     * Default "simple".
     *
     * @param s
     */
    public void setType(String s);

    public String getType();

    public boolean isArrayMessage();

    public int getIndex();

      /**
      * Set the index of the message.
      * @param name
      */
    public void setIndex(int i);

    /**
     * Set the name of the message.
     */
    public void setName(String name);

    /**
     * Set the mode of the message.
     * Current modes are: default, lazy.
     *
     * @param mode
     */
    public void setMode(String mode);

    /**
     * Set the total number of lazy array element sub messages.
     * @param c
     */
    public void setLazyTotal(int c);

    /**
     * Set the total number of remaining lazy array element sub messages.
     *
     * @param c
     */
    public void setLazyRemaining(int c);

    /**
     * Set the total number of array element sub messages.
     *
     * @param c
     */
    public void setArraySize(int c);

    /**
     * Add a property to a message. If a property with the specified name already exists,
     * replace it with the new one.
     */
    public void addProperty(Property p);

    /**
     * Use this method to add an element to an array type message.
     *
     * @param m
     * @return
     */
    public Message addElement(Message m);

    public Message addMessage(Message m);

    /**
     * Add a message to a message. If a message with the specified name already exists
     * withing the parent message, replace it with the new one.
     */
    public Message addMessage(Message m, boolean overwrite);

    /**
     * Remove a property from a message. If a null value is given as input do nothing.
     */
    public void removeProperty(Property p);

    /**
     * Remove a message from a message. If a null value is given as input do nothing.
     */
    public void removeMessage(Message m);

    /**
     * Return all properties that match a given regular expression. Regular expression may include sub-messages and even
     * absolute message references starting at the root level.
     */

    public ArrayList getProperties(String regularExpression)  throws NavajoException;

    /**
     * Return all messages that match a given regular expression. Regular expression may include sub-messages and even
     * absolute message references starting at the root level.
     */
    public ArrayList getMessages(String regularExpression) throws NavajoException;

    public Message getMessage(int index);

    /**
     * Return a message with a specific name if it exists. If it does not exist return null.
     */
    public Message getMessage(String name);

    public Property getPathProperty(String property);

    /**
     * Return a property with a specific name if it exists. Property name may include references to sub-messages.
     * Example: getProperty("mymessage/sub1/subsub/propy").
     */
    public Property getProperty(String name);

    /**
     * Return all properties in this message. Properties in submessages are not included(!).
     */
    public ArrayList getAllProperties();

    /**
     * Return all messages in this message. Only first level sub-messages are returned(!).
     */
    public ArrayList getAllMessages();

    /**
     * Check if this message contains a property with a specific name. Property name may include references
     * to sub-messages.
     */
    public boolean contains(String name);


    /**
     * Return the internal implementation specific representation of the Message.
     *
     * @return
     */
    public Object getRef();

}