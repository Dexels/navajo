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
 * A message contains data, it does not represent data.
 */

public interface Message
    extends java.io.Serializable {

  /**
   * Message attributes/constants.
   */
  public static final String MSG_DEFINITION = "message";
  public static final String MSG_NAME = "name";
  public static final String MSG_INDEX = "index";
  public static final String MSG_TYPE = "type";
  public static final String MSG_CONDITION = "condition";
  public static final String MSG_PARAMETERS_BLOCK = "__parms__";

  public static final String MSG_TYPE_SIMPLE = "simple";
  public static final String MSG_TYPE_ARRAY = "array";
  public static final String MSG_TYPE_TABLE = "table";

  public static final String MSG_MODE = "mode";
  public static final String MSG_MODE_LAZY = "lazy";
  public static final String MSG_MODE_IGNORE = "ignore";

  public static final String MSG_LAZY_COUNT = "lazy_total";
  public static final String MSG_LAZY_REMAINING = "lazy_remaining";
  public static final String MSG_ARRAY_SIZE = "array_size";
  public static final String MSG_TYPE_ARRAY_ELEMENT = "array_element";
  public static final String MSG_TYPE_DEFINITION = "definition";

  /**
   * Get the name of the message. A message name must be unique at each message level.
   *
   * @return String, the name of this Message
   */
  public String getName();

  /**
   * Return the parent message if there is one.
   *
   * @return Message, the parent of this Message, null if no parent is present
   */
  public Message getParentMessage();

  /**
   * Returns the parent of the message, even if it is an array message.
   * in nano it is exactly the same as getParentMessage.
   * @return Message,  the parent of this Message, null if no parent is present
   */
  public Message getArrayParentMessage();

  /**
   * Return the fully qualified Navajo message name.
   * @return String, the full messagename
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
   * Get the type of a message.
   *
   * @return Type of the message, empty String if type is not specified.
   * Types are:
   * MSG_TYPE_SIMPLE, MSG_TYPE_ARRAY, MSG_TYPE_ARRAY_ELEMENT, MSG_TYPE_DEFINITION, MSG_TYPE_TABLE
   */
  public String getType();

  /**
   * Check if message is of type Array
   *
   * @return true if type is MSG_TYPE_ARRAY
   */
  public boolean isArrayMessage();

  /**
   * Get the largest index of the array message elements.
   *
   * @return
   * @throws NavajoException
   */
  public int getArraySize();

  /**
   * Get the index value of an array message element.
   *
   * @return ArrayMessage index
   */
  public int getIndex();

  /**
   * Set the index of the message. If message is not array element return -1.
   *
   * @param name
   */
  public void setIndex(int i);

  public void setCondition(String condition);

  /**
   * Sets the name of the message.
   */
  public void setName(String name);

  /**
   * Get the mode of the message
   * @return String mode
   */
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
   * @return Message
   */
  public Message addElement(Message m);

  /**
   * Add a sub message. Overwrite message with the same name.
   *
   * @param m
   * @return Message
   */
  public Message addMessage(Message m);

  /**
   * Add a message to a message. If a message with the specified name already exists
   * withing the parent message, replace it with the new one.
   * @return Message
   */
  public Message addMessage(Message m, boolean overwrite);

  /**
   * Adds a message to an array message at a specific location in the array
   */
  public void addMessage(Message m, int index) throws NavajoException;

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
   * @return ArrayList of Property objects that match the given regular expression
   */
  public ArrayList getProperties(String regularExpression) throws
      NavajoException;

  /**
   * Return all messages that match a given regular expression. Regular expression may include sub-messages and even
   * absolute message references starting at the root level.
   * @return ArrayList with Message objects that match the given regular expression
   */
  public ArrayList getMessages(String regularExpression) throws NavajoException;

  /**
   * Get an array element message with a given index.
   *
   * @param index
   * @return Message at given index
   */
  public Message getMessage(int index);

  /**
   * Return a message with a specific name if it exists. If it does not exist return null.
   * @return Message with the given name, null when not found
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
   * @param name
   * @return Property with the given name, null if no Property was found
   */
  public Property getProperty(String name);

  /**
   * Return all properties in this message. Properties in submessages are not included(!).
   * @return ArrayList containing all this Message's Property objects
   */
  public ArrayList getAllProperties();

  /**
   * Return all messages in this message. Only first level sub-messages are returned(!).
   * @return ArrayList containting all this Message's submessages
   */
  public ArrayList getAllMessages();

  /**
   * Check if this message contains a property with a specific name. Property name may include references
   * to sub-messages.
   * @param Name
   * @return true if a Property with the given name is found.
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

  /**
   * Copy the message to another Navajo object n. Actually a new message instance is created that is a copy
   * of the original with the only difference of having a different "owner" Navajo object.
   * NOTE that the message should be explicitly appended using the addMessage() method of the Navajo object(!)
   *
   * @param n
   * @return
   */
  public Message copy(Navajo n);

  /**
   * Copies a message to a new (empty) Navajo document.
   * NOTE that the copied message is added to the newly created Navajo object(!)
   *
   * @return
   */
  public Message copy() throws NavajoException;

  /**
   * Compare the contect of a message with another Message o.
   *
   * @param o
   * @return true if content is the same, false otherwise.
   */
  public boolean isEqual(Message o);

  /**
   * As isEqual(Message). A ";" seperated string of property names can be supplied, that need to be excluded from the comparison.
   *
   * @param o
   * @param skipProperties
   * @return
   */
  public boolean isEqual(Message o, String skipProperties);

  /**
   * refreshes recursively all the properties in this message.
   * i.e.: Recalculates all expression-type properties
   */
  public void refreshExpression() throws NavajoException;

  /**
   * Not in use
   */
  public int getCurrentTotal();

  public void setCurrentTotal(int aap);

  /**
   * Sets the parent message. Right now, only implemented in nanoimpl
   * @param m
   */
  public void setParent(Message m);

  /**
   * Add empty message at index
   * @return Inserted message
   */
  public Message addMessage(int index);

  /**
   * Add empty message at the end
   * @return Appended message
   */
  public Message addMessage();

  /**
   * Get the definition message. Definition messages are used in array messages to define the arraymessages' properties for each 'column'
   * @return Message definition
   */

  public Message getDefinitionMessage();

}
