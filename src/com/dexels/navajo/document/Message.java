package com.dexels.navajo.document;

import java.util.*;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 *
 * A Message object represents the "data holding" structure of a Navajo document.
 * A message contains data is does not represent data.
 */

public interface Message extends java.io.Serializable {

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
    public static final String MSG_MODE_IGNORE = "ignore";

    public static final String MSG_LAZY_COUNT = "lazy_total";
    public static final String MSG_LAZY_REMAINING = "lazy_remaining";
    public static final String MSG_ARRAY_SIZE = "array_size";
    public static final String MSG_TYPE_ARRAY_ELEMENT = "array_element";

    /**
     * Get the name of the message. A message name must be unique at each message level.
     *
     * @return
     */
    public String getName();

    /**
      * Return the parent message if there is one. If message has no parent return null.
      *
      * @return
      */
    public Message getParentMessage();

    /**
     * Return the fully qualified Navajo message name.
     */
    public String getFullMessageName();

    /**
     * Set the type of a message.
     * Default "simple" other value: "array".
     * An "array" message contains other messages as elements. Each message element has the same
     * structure. A message element has an additional attribute "index" (see getIndex() ).
     *
     * @param s
     */
    public void setType(String s);

    /**
     * Get the type of a message. If type is not defined return "".
     *
     * @return
     */
    public String getType();

    /**
     * Returns true of message is of "array" type.
     *
     * @return
     */
    public boolean isArrayMessage();

    /**
     * Get the largest index of the array message elements.
     *
     * @return
     * @throws NavajoException
     */
    public int getArraySize() throws NavajoException;

    /**
     * Get the index value of an array message element.
     *
     * @return
     */
    public int getIndex();

      /**
      * Set the index of the message. If message is not array element return -1.
      *
      * @param name
      */
    public void setIndex(int i);

    /**
     * Sets the name of the message.
     */
    public void setName(String name);

    public String getMode();

    /**
     * Set the mode of the message.
     * Current modes are: "default", "lazy". "lazy" is only supported by "array" type messages.
     * A "lazy" array message supports lazy retrieval of array message elements.
     *
     * @param mode
     */
    public void setMode(String mode);

    /**
     * Sets the total number of lazy array element sub messages.
     * Lazy array messages represent all the array messages that are physically present.
     *
     * @param c
     */
    public void setLazyTotal(int c);

    /**
     * Set the total number of remaining lazy array element sub messages. The number of remaining lazy
     * messages is equal to the total array size - the highest (absolute) index of the lazy total.
     *
     * @param c
     */
    public void setLazyRemaining(int c);

    /**
     * Set the total number of array element messages. The total number may be larger than the lazy
     * total, but it can never be smaller.
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
     * Use this method to add an element message to an array type message. The index
     * of the message element is automatically set.
     *
     * @param m
     * @return
     */
    public Message addElement(Message m);

    /**
     * Add a sub message. Overwrite message with the same name.
     *
     * @param m
     * @return
     */
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

    /**
     * Get an array element message with a given index.
     *
     * @param index
     * @return
     */
    public Message getMessage(int index);

    /**
     * Return a message with a specific name if it exists. If it does not exist return null.
     */
    public Message getMessage(String name);

    /**
     * Get a property addressed by a full (absolute or relative) Navajo path.
     *
     * @param property
     * @return
     */
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

    /**
     * Return the Navajo doc this object is part of.
     */
    public Navajo getRootDoc();

    /**
     * Replace the Navajo doc this object is part of with this one.
     */
    public void setRootDoc(Navajo n);

    /**
     * Serialize a message as a string to a particular writer.
     *
     * @param writer
     */
    public void write(java.io.Writer writer);

    /**
      * Serialize a message as a string to a particular output stream.
      *
      * @param stream
      */
    public void write(java.io.OutputStream stream);
    /**
      * Set the message map for retreiving the right values for 'toString()'
      *
      * @param stream
      */
    public void setMessageMap(MessageMappable m);

}