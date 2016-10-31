package com.dexels.navajo.document;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */
import java.beans.PropertyChangeListener;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.persistence.Persistable;

public interface Navajo extends Persistable {

    /**
     * Public constants.
     */

    public static final String METHODS_DEFINITION = "methods";
    public static final String OPERATIONS_DEFINITION = "operations";
    public static final String BODY_DEFINITION = "tml";
    public static final String SCRIPT_BODY_DEFINITION = "tsl";
    public static final String MESSAGE_SEPARATOR = "/";
    public static final String PARENT_MESSAGE = "..";

    /**
     * Set the errorDescription class property.
     * 
     * @param String
     *            description
     */
    public void setErrorDescription(String description);

    /**
     * Set the errorNumber class property.
     * 
     * @param int errornumber
     */
    public void setErrorNumber(int i);

    /**
     * Get the errorDescription class property.
     * 
     * @return String errorderscription
     */

    public String getErrorDescription();

    /**
     * Get the errorNumber class property.
     * 
     * @return int errornumber
     */
    public int getErrorNumber();

    /**
     * Return all the Method objects in the Navajo document.
     * 
     * @return ArrayList containing all Methods
     */
    public List<Method> getAllMethods();

    public List<Operation> getAllOperations();

    /**
     * DEBUGGING: write the current message and action buffers to a file with a
     * specified postfix filename.
     * 
     * @param String
     *            filename
     */
    public String writeDocument(String filename);

    /**
     * DEBUGGING: write a specific message (name) to a file (filename).
     * 
     * @param String
     *            name, String filename
     */
    public void writeMessage(String name, String filename) throws NavajoException;

    /**
     * Return the names of the required messages of a specific method (Given the
     * method name).
     * 
     * @return Vector containing the names of the required Messages for the
     *         given method
     */
    public List<String> getRequiredMessages(String method);

    /**
     * Return all the Message object of this Navajo document.
     * 
     * @return ArrayList of allMessage objects in this Navajo
     */
    public List<Message> getAllMessages() throws NavajoException;

    /**
     * Return a method object given a method name.
     * 
     * @return Method with the given name, null if not found
     */
    public Method getMethod(String name) throws NavajoException;

    /**
     * Return an arraylist of message objects given a regular expression name.
     * 
     * @return ArrayList of all Message objects that match the given regular
     *         expression
     */
    public List<Message> getMessages(String name) throws NavajoException;

    /**
     * Return a message object given a message name.
     * 
     * @return Message object that matches the given name
     */
    public Message getMessage(String name);

    /**
     * Return an arraylist of Property objects given a regular expression name.
     * 
     * @return ArrayList of all Property objects that match the given regular
     *         expression
     */

    public List<Property> getProperties(String regularExpression) throws NavajoException;

    /**
     * Get the property, give the property path: <message>.[...].<property>.
     * Return null if the property does not exist.
     * 
     * @return Property that matches the given path, null if path does not exist
     */
    public Property getProperty(String property);

    /**
     * Get the selection option, given the property path:
     * <message>.[...].<property>:<option>. Return null if the property does not
     * exist.
     * 
     * @return Selected selection of the given property
     */
    public Selection getSelection(String property) throws NavajoException;

    /**
     * Return true if a message with a specific name exists in the Navajo
     * document, else false.
     * 
     * @return true if the current Navajo object contains a Message with the
     *         given name
     */
    public boolean contains(String name);

    /**
     * Copies the given Message into the given Navajo
     * 
     * @param message
     *            Message
     * @param newDocument
     *            Navajo
     * @return Message the copy
     */
    public Message copyMessage(Message message, Navajo newDocument);

    /**
     * Copies the given Message (referred by name) into the given Navajo
     * 
     * @param message
     *            Message
     * @param newDocument
     *            Navajo
     * @return Message the copy
     */
    public Message copyMessage(String name, Navajo newDocument);

    /**
     * Copies the given Method (referred by name) into the given Navajo
     * 
     * @param method
     *            Method
     * @param newDocument
     *            Navajo
     * @return Method the copy
     */
    public Method copyMethod(String name, Navajo newDocument);

    /**
     * Copies the given Method into the given Navajo
     * 
     * @param method
     *            Method
     * @param newDocument
     *            Navajo
     * @return Method the copy
     */
    public Method copyMethod(Method method, Navajo newDocument);

    /**
     * Recursively copies a Navajo document. For now, only implemented in
     * nanoimpl. It does have some side effects to the original, and using
     * definition messages may cause some performance problems.
     * 
     */

    public Navajo copy();

    /**
     * GIves the String representation of the current Navajo
     * 
     * @return String
     */
    @Override
    public String toString();

    /**
     * Add a method to the Navajo document. If the method name already exists,
     * replace the old one.
     * 
     * @param m
     *            Method
     * @throws NavajoException
     */
    public void addMethod(Method m) throws NavajoException;

    public void addOperation(Operation o) throws NavajoException;

    /**
     * Add a Message object tot the current Navajo
     * 
     * @param message
     *            Message
     * @throws NavajoException
     * @return Message
     */
    public Message addMessage(Message message) throws NavajoException;

    public void addMap(MapTag map) throws NavajoException;

    /**
     * Add a message to the Navajo document. If the message name already exists,
     * replace the old one.
     */
    public Message addMessage(Message message, boolean overwrite) throws NavajoException;

    /**
     * Replaces a message to the Navajo document. If original does not exist, do
     * nothing and return null.
     * 
     */
    public Message mergeMessage(Message newMsg) throws NavajoException;

    /**
     * Removes the given Message from the current Navajo
     * 
     * @param message
     *            Message
     * @throws NavajoException
     */
    public void removeMessage(Message message) throws NavajoException;

    /**
     * Delete a message from a specified message.
     */
    public void removeMessage(String message);

    /**
     * Return the persisetence key
     * 
     * @return String persistence key
     */

    @Override
    public String persistenceKey();

    /**
     * Retun the Message buffer object
     * 
     * @return Object
     */
    public Object getMessageBuffer();

    /**
     * Append Document buffer
     * 
     * @param d
     *            Object
     * @throws NavajoException
     */
    public void appendDocBuffer(Object d) throws NavajoException;

    /**
     * Clear all selections
     * 
     * @throws NavajoException
     */
    public void clearAllSelections() throws NavajoException;

    /**
     * Write the current Navajo object to the given writer
     * 
     * @param writer
     *            Writer
     * @param condense
     *            if set to true it will optimize the serialized Navajo, e.g.
     *            unselected selections will not be included, not "required"
     *            messages will not be included.
     * @param method
     *            optionally the method to optimize for can be specified.
     * @throws NavajoException
     */
    public void write(java.io.Writer writer, boolean condense, String method) throws NavajoException;

    /**
     * Write the current Navajo object to the given writer
     * 
     * @param stream
     *            OutputStream
     * @param condense
     *            if set to true it will optimize the serialized Navajo, e.g.
     *            unselected selections will not be included, not "required"
     *            messages will not be included.
     * @param method
     *            optionally the method to optimize for can be specified.
     * @throws NavajoException
     */
    public void write(OutputStream stream, boolean condense, String method) throws NavajoException;

    /**
     * Write the current Navajo object to the given writer
     * 
     * @param writer
     *            Writer
     * @throws NavajoException
     */
    public void write(java.io.Writer writer) throws NavajoException;

    /**
     * Write the current Navajo object to the given writer
     * 
     * @param writer
     *            Writer
     * @throws NavajoException
     */
    public void writeJSON(java.io.Writer writer) throws NavajoException;

    /**
     * Write the current Navajo object to the given writer
     * 
     * @param writer
     *            Writer
     * @throws NavajoException
     */
    public void writeJSONTypeless(java.io.Writer writer) throws NavajoException;

    /**
     * Write the current Navajo object to the given outputstream
     * 
     * @param stream
     *            OutputStream
     * @throws NavajoException
     */
    public void write(java.io.OutputStream stream) throws NavajoException;

    /**
     * Remove this Navajo objects' header.
     */
    public void removeHeader();

    /**
     * Add a new header to a Navajo object. NOTE: If a header is already
     * present, the previous header is replaced.
     *
     * @param h
     */
    public void addHeader(Header h);

    /**
     * Return the header instance for this Navajo object.
     *
     * @return
     */
    public Header getHeader();

    /**
     * Import a message from another Navajo object.
     *
     * @param m
     */
    public void importMessage(Message m);

    /**
     * Returns this Navajo object's rootmessage
     * 
     * @return Message
     */
    public Message getRootMessage();

    /**
     * Compares two Navajo objects and their content
     * 
     * @param o
     *            Navajo
     * @return true if given Navajo objet is equal to the current Navajo object
     */
    public boolean isEqual(Navajo o);

    /**
     * Refresh all expressions in the current Navajo object
     * 
     * @throws NavajoException
     * @return List expression properties
     */
    public List<Property> refreshExpression() throws NavajoException;

    public void addPropertyChangeListener(PropertyChangeListener p);

    public void removePropertyChangeListener(PropertyChangeListener p);

    public void firePropertyDataChanged(Property p, Object oldValue, Object newValue);

    public NavajoFactory getNavajoFactory();

    /**
     * Merge Navajo with other Navajo. Navajo is merged by merging Messages.
     * Messages in the current Navajo are replaced with messages in Navajo with.
     * Basically the Navajo with is "laid over" the current Navajo, i.e. it is a
     * kind of Message based inheritance.
     * 
     * @param with
     * @return
     * @throws NavajoException
     */
    public Navajo merge(Navajo with) throws NavajoException;

    /**
     * Mask Navajo with other Navajo.
     * 
     * @param with
     * @return
     * @throws NavajoException
     */
    public Navajo mask(Navajo with, String method) throws NavajoException;

    /**
     * Merge Navajo with other Navajo. Navajo is merged by merging Messages.
     * Messages in the current Navajo are replaced with messages in Navajo with.
     * Basically the Navajo with is "laid over" the current Navajo, i.e. it is a
     * kind of Message based inheritance.
     * 
     * @param with
     * @param preferThis
     *            , if this parameter is set to true, the property values/types
     *            of this Navajo is prefered.
     * @return
     * @throws NavajoException
     */
    public Navajo merge(Navajo with, boolean preferThis) throws NavajoException;

    public Map<String, Message> getMessages();

    /**
     * Add another Navajo object using a unique key for later reference.
     * 
     * @param key
     * @param value
     */
    public void addNavajo(String key, Navajo value);

    /**
     * Get a previously added Navajo object using unique key.
     * 
     * @param key
     * @return
     */
    public Navajo getNavajo(String key);

    /**
     * Remove a previously added Navajo object.
     * 
     * @param key
     */
    public void removeNavajo(String key);

}
