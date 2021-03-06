/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

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
    extends java.io.Serializable, java.lang.Comparable<Message> {

  /**
   * Message attributes/constants.
   */
  public static final String MSG_DEFINITION = "message";
  public static final String MSG_FILTER = "filter";
  public static final String MSG_NAME = "name";
  public static final String MSG_INDEX = "index";
  public static final String MSG_TYPE = "type";
  public static final String MSG_EXTENDS = "extends";
  public static final String MSG_SCOPE = "scope";
  public static final String MSG_METHOD = "method";
  public static final String MSG_CONDITION = "condition";
  public static final String MSG_ETAG = "etag";
  // internal messages when found on root level
  public static final String MSG_GLOBALS_BLOCK = "__globals__";
  public static final String MSG_PARAMETERS_BLOCK = "__parms__";
  public static final String MSG_AAA_BLOCK = "__aaa__";
  public static final String MSG_ENTITY_BLOCK = "__entity__";
  public static final String MSG_TOKEN_BLOCK = "__token__";

  public static final String MSG_TYPE_SIMPLE = "simple";
  public static final String MSG_TYPE_ARRAY = "array";
  public static final String MSG_TYPE_TABLE = "table";

  public static final String MSG_SUBTYPE = "subtype";

  public static final String MSG_SCOPE_LOCAL = "local";
  public static final String MSG_SCOPE_GLOBAL = "global";
  
  public static final String MSG_MODE = "mode";
  public static final String MSG_MODE_LAZY = "lazy";
  public static final String MSG_MODE_IGNORE = "ignore";
  public static final String MSG_MODE_OVERWRITE = "overwrite";

  public static final String MSG_LAZY_COUNT = "lazy_total";
  public static final String MSG_LAZY_REMAINING = "lazy_remaining";
  public static final String MSG_ARRAY_SIZE = "array_size";
  public static final String MSG_TYPE_ARRAY_ELEMENT = "array_element";
  public static final String MSG_TYPE_DEFINITION = "definition";
  public static final String MSG_ORDERBY = "orderby";

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
   * UPDATE:
   * I changed the behaviour of getParentMessage in SAX/NANO to coincide with JAXP.
   * I replaced all occurrences of getParentMessage with getArrayParentMessagein client 
   * code.
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
   * Set the orderby of a message.
   * Default "" other value: "[PropertyName1], [PropertyName2]".
   * @param s
   * 
   */
  public void setOrderBy(String s);

  
  /**
   * Sets the complete subtype attribute of a message.
   *
   * @param subType the subtype key.
   */
  public void setSubType(String subType);

  /**
   * Get the value of subtype key/value pair.
   *
   * @param key the subtype key
   * @return the subtype value
   */
  public String getSubType(String key);
  
  /**
   * Get the value of the complete subtype attribute of a message.
   *
   * @return the subtypes value
   */
  public String getSubType();
  
  /**
   * Get all subtypes.
   * 
   */
  public Map<String, String> getSubTypes();

  /**
   * Get the type of a message.
   *
   * @return Orderby of the message, empty String if type is not specified.
   */
  public String getOrderBy();

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

 /**
  * @deprecated
  * @param condition
  */
  @Deprecated
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
   * Sets the scope of the message. Scope can be used by e.g. NavajoMap adapter to determine whether this message should
   * be passed by default (global) or never (local).
   * 
   * @param scope
   */
  public void setScope(String scope);
  
  /**
   * Gets the message scope.
   * 
   * @return
   */
  public String getScope();
  
  /**
   * Return the value of the extends attribute. 
   * Extends refers to an entity message(s) that is(are) extended by this message (entity message inheritance)
   * 
   * @return
   */
  public String getExtends();
  
  /**
   * Sets the extends attribute.
   * 
   * @param s
   */
  public void setExtends(String s);
  
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

  public void addParam(Param p);
  
  /**
   * Add a property to a message. If a property with the specified name already exists,
   * replace it with the new one.
   */
  public void addProperty(Property p);

  /**
   * Add a property to a message. If a property with the specified name already exists,
   * replace it with the new one unless preferMyProperties is set to true.
   */
  public void addProperty(Property property, boolean preferMyProperties);
  
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
   * Add a <map ref=> tag to map an adapter on a message.
   */
  public void addMapRef(MapAdapter m);
  
  public void addMap(MapAdapter m);
  
  
  /**
   * Replaces a message with messsage m. The location of the message in the XML will NOT change.
   * 
   * @return Message
   */
  public Message mergeMessage(Message m);
  
  /**
   * Replaces a message with messsage m. The location of the message in the XML will NOT change.
   * 
   * @param m
   * @param preferThisMessage, if set to true existing property values/types in this message are preferred over merge Message m
   * 
   * @return Message
   */
  public Message mergeMessage(Message m, boolean preferThisMessage);

  /**
   * Mask message instance with a message 'mask'
   * (For array messages, the mask message should use definition messages)
   * 
   * @param template, the message 'mask'
   * 
   */
  public void maskMessage(Message mask);
  
  /**
   * Mask message instance with a message 'mask'
   * (For array messages, the mask message should use definition messages)
   * 
   * @param template, the message 'mask'
   * @param method, remove messages/properties with a wrong method
   */
   void maskMessage(Message mask, String method);

  
  /**
   * Adds a message to an array message at a specific location in the array
   */
  public void addMessage(Message m, int index);

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
  public List<Property> getProperties(String regularExpression);

  /**
   * Return all messages that match a given regular expression. Regular expression may include sub-messages and even
   * absolute message references starting at the root level.
   * @return ArrayList with Message objects that match the given regular expression
   */
  public List<Message> getMessages(String regularExpression);

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

  public String getPath();

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
  public List<Property> getAllProperties();

  /**
   * Return all messages in this message. Only first level sub-messages are returned(!).
   * @return ArrayList containting all this Message's submessages
   */
  public List<Message> getAllMessages();

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

  public void writeJSON(Writer writer) throws IOException;
  
  public void writeSimpleJSON(Writer writer) throws IOException;

  public void writeSimpleJSON(Writer writer,String[] properties) throws IOException;

  public void writeSimpleJSON(String name, Writer writer, String[] properties)
			throws IOException;
  
  public void writeAsCSV(Writer writer, String delimiter) throws IOException;

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
   * Create and add a new element, based on the definition message
   * @return
   */
  public Message instantiateFromDefinition();
  
  /**
   * Copies a message to a new (empty) Navajo document.
   * NOTE that the copied message is added to the newly created Navajo object(!)
   *
   * @return
   */
  public Message copy();

  /**
   * Compare the content of a message with another Message o.
   *
   * @param o
   * @return true if content is the same, false otherwise.
   */
  public boolean isEqual(Message o);

  /**
   * As isEqual(Message). A ";" separated string of property names can be supplied, that need to be excluded from the comparison.
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
  public void refreshExpression() throws ExpressionChangedException;

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
   * Merge message with incoming message. All properties and submessages of incoming message are merged
   * with the current message. The name of the incoming message is not relevant.
   * 
   * @param incoming
   */
  public void merge(Message incoming);
  
  /**
   * Merge message with incoming message.  All properties and submessages of incoming message are merged
   * with the current message. The name of the incoming message is not relevant.
   * 
   * @param incoming
   * @param preferThis in case of a conflict use the current message
   */
  public void merge(Message incoming, boolean preferThis);
  
  /**
   * Merge message with incoming message.  All properties and submessages of incoming message are merged
   * with the current message. The name of the incoming message is not relevant.
   * 
   * @param incoming
   * @param preferThis in case of a conflict use the current message
   * @param applySubType apply the subtype constraints (for instance nullable=true)
   */
  public void merge(Message incoming, boolean preferThis, boolean applySubType);

  /**
   * Get the definition message. Definition messages are used in array messages to define the arraymessages' properties for each 'column'
   * @return Message definition
   */
  public Message getDefinitionMessage();
  
  public void setDefinitionMessage(Message m);

  /**
   * Generates, sets and returns an etag attribute for a message.
   * 
   * @return
   */
  public String generateEtag();
  
  /**
   * Clears the etag attribute
   */
  public void clearEtag();
  
  /**
   * Set etag to specified value.
   */
  public void setEtag(String value);
  
  /**
   * Get current value of etag attribute.
   */
  public String getEtag();
  
  /**
   * Set method to specified value.
   */
  public void setMethod(String value);
  
  /**
   * Get current value of method attribute.
   */
  public String getMethod();
  
  public void addPropertyChangeListener(PropertyChangeListener p);
  public void removePropertyChangeListener(PropertyChangeListener p);
  public void firePropertyDataChanged(Property p,Object oldValue, Object newValue);
	public Map<String, Property> getProperties();

	public Map<String, Message> getMessages();

	public List<Message> getElements();
	
	public List<Message> getSortedElements();

	/**
	 * Returns a map of names->property values.
	 * 
	 * Nested messages will be ignored
	 * @return
	 */
	public Map<String, Object> getValueMap();

	/**
	 * Write the whole object to a Writer
	 * @param sw
	 * @param indent
	 * @throws IOException
	 */
	public void printElement(final Writer sw, int indent) throws IOException;

	public boolean printStartTag(final Writer sw, int indent,boolean forceDualTags) throws IOException ;
	public void printBody(final Writer sw, int indent) throws IOException;
	public void printCloseTag(final Writer sw, int indent) throws IOException;

	public void setValue(String propertyName, Object value);
	
	/**
	 * Whether this message represents an internal message
	 * @return
	 */
	public boolean isInternal();
	

	public boolean isAntiMessage();

}
