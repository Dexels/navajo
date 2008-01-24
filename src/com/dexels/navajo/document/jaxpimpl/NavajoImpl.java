/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

package com.dexels.navajo.document.jaxpimpl;

import java.beans.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

import javax.xml.transform.stream.*;

import org.w3c.dom.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.jaxpimpl.xml.*;



/**
 * This class implements the XML message buffer and its
 * manipulation methods.
 *
 * @author Arjen Schoneveld (Dexels/Brentfield)
 * @version 0.1 (11/7/2000)
 * TODO: throw new Exceptions when neccessary.
 * ALSO PUT THE getAction() related messages in this class.
 */

public final class NavajoImpl implements Navajo, java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8175746322818786056L;

	/**
     * The XML message buffer. The message buffer contains all the
     * XML messages that have been received from the server.
     */
    private  transient Document docBuffer;

    private String documentName = "ANONYMOUS";
    private String myBodyDefinition = BODY_DEFINITION;

    private transient Node messagePointer;
    private transient Node propertyPointer;
    private String errorDescription;
    private int errorNumber;

    private final void initDocument(String bodyDefinition) {
        myBodyDefinition = bodyDefinition;
        docBuffer = XMLDocumentUtils.createDocument();
        Element body = docBuffer.createElement(bodyDefinition);
        docBuffer.appendChild(body);
    }

    /**
     * Initialize the XML message buffer
     */
    public NavajoImpl() {
        initDocument(Navajo.BODY_DEFINITION);
    }

    public NavajoImpl(String bodyDefinition)  {
      initDocument(bodyDefinition);
    }

    /**
     * Initialize the XML message buffer with a predefined document
     */
    public NavajoImpl(Document d) {
        docBuffer = d;
    }

    public NavajoImpl(Document d, String name) {
        docBuffer = d;
        documentName = name;
    }

    /**
     * Set the errorDescription class property.
     */
    public void setErrorDescription(String a) {
        this.errorDescription = a;
    }

    /**
     * Set the errorNumber class property.
     */
    public void setErrorNumber(int i) {
        this.errorNumber = i;
    }

    /**
     * Get the errorDescription class property.
     */

    public String getErrorDescription() {
        return this.errorDescription;
    }

    /**
     * Get the errorNumber class property.
     */
    public int getErrorNumber() {
        return this.errorNumber;
    }

    /**
     * Create a TML header.
     */
    public static Element createHeader(Document d, String rpcName,
            String rpcUser, String rpcPwd, long expirationInterval,
            com.dexels.navajo.document.RequestHeader request) {

        Element header = d.createElement("header");
        Element client = d.createElement("client");
        Element server = d.createElement("server");
        Element agent = d.createElement("agent");
        Element transaction =
                d.createElement("transaction");

        header.appendChild(client);
        header.appendChild(server);
        header.appendChild(agent);
        header.appendChild(transaction);

        // Creeer "client" tag
        if (request != null) {

            String address = request.getRequestHeader("remoteAddress");
            String host = "UNKNOWN";

            try {
                host = java.net.InetAddress.getByName(address).getHostName();
            } catch (Exception e) {}

            client.setAttribute("address",
                    request.getRequestHeader("remoteAddress"));
            client.setAttribute("host", host);
            client.setAttribute("user",
                    request.getRequestHeader("remoteUser"));

            Element xmlhttp =
                    d.createElement("http");

            xmlhttp.setAttribute("accept",
                    request.getRequestHeader("accept"));
            xmlhttp.setAttribute("accept_language",
                    request.getRequestHeader("acceptLanguage"));
            xmlhttp.setAttribute("accept_encoding",
                    request.getRequestHeader("acceptEncoding"));
            xmlhttp.setAttribute("cookie",
                    request.getRequestHeader("cookie"));
            xmlhttp.setAttribute("referer", "");
            xmlhttp.setAttribute("user_agent",
                    request.getRequestHeader("userAgent"));
            xmlhttp.setAttribute("https", "");

            client.appendChild(xmlhttp);
        }

        // Creeer "transaction" tag
        transaction.setAttribute("type", "");
        transaction.setAttribute("rpc_name", rpcName);
        transaction.setAttribute("rpc_usr", rpcUser);
        transaction.setAttribute("rpc_pwd", rpcPwd);
 
        // Expiration interval is used to indicate when persisted responses should expire.
        transaction.setAttribute("expiration_interval", expirationInterval + "");

        return header;
    }

    /**
     * Return all the Method objects in the Navajo document.
     */
    public ArrayList<Method> getAllMethods(Object o) {

        Document d = (Document) o;
        NodeList list = null;
        Node n = XMLutils.findNode(d, Navajo.METHODS_DEFINITION);
        ArrayList<Method> h = new ArrayList<Method>();

        if (n != null) {
            list = n.getChildNodes();

            for (int i = 0; i < list.getLength(); i++) {
                if (list.item(i).getNodeName().equals(MethodImpl.METHOD_DEFINITION)) {
                    Method m = new MethodImpl((Element) list.item(i));
                    h.add(m);
                }
            }
        }

        return h;
    }

    /**
     * Return all the method names in the current XML action buffer.
     */
    public ArrayList<Method> getAllMethods() {

        Node n;

        ArrayList<Method> props = new ArrayList<Method>();

        if (docBuffer != null) {

            n = XMLutils.findNode(docBuffer, Navajo.METHODS_DEFINITION);

            if (n != null) {
                NodeList list = n.getChildNodes();

                int index = 0;

                for (int i = 0; i < list.getLength(); i++) {
                    if (list.item(i).getNodeName().equals(MethodImpl.METHOD_DEFINITION)) {
                        Element f = (Element) list.item(i);
                        MethodImpl m = new MethodImpl(f);
                        props.add(index++, m);
                    }
                }
            }
        }

        return props;
    }

    /**
     * DEBUGGING: write the current message and action buffers to
     * a file with a specified postfix filename.
     */
    public String writeDocument(String filename) {

        FileWriter out = null;

        try {
            out = new FileWriter("/tmp/docBuffer" + filename);
            XMLDocumentUtils.toXML(docBuffer, null, null, new StreamResult(out));
            return "succes";
        } catch (Exception e) {
            return "failure";
        }

    }

    /**
     * DEBUGGING: write a specific message (name) to a file (filename).
     */
    public void writeMessage(String name, String filename) throws NavajoException {

        Document d = XMLDocumentUtils.createDocument();

       // Element body = (Element) d.createElement(myBodyDefinition);
        Node n = getMessage(name, false).cloneNode(true);

        d.appendChild(n);
        d.appendChild(n);

        try {
            FileWriter out = new FileWriter("/tmp/" + filename);

            XMLDocumentUtils.toXML(d, null, null, new StreamResult(out));
        } catch (Exception e) {}
    }

    /**
     * Return the names of the required messages of a specific method (Given the method name).
     */
    public Vector<String> getRequiredMessages(String method) {

        Vector<String> req = null;

        Element g = (Element)
                XMLutils.findNodeWithAttributeValue(docBuffer, MethodImpl.METHOD_DEFINITION,
                MethodImpl.METHOD_NAME, method);

        if (g != null) {
            req = new Vector<String>();
            NodeList list = g.getChildNodes();

            int index = 0;

            for (int i = 0; i < list.getLength(); i++) {
                if (list.item(i).getNodeName().equals(MethodImpl.METHOD_REQUIRED)) {
                    Element f = (Element) list.item(i);
                    req.add(index++, f.getAttribute(Message.MSG_DEFINITION));
                }
            }
        }

        return req;
    }

    /**
     * Return the Message objects in a specific Navajo XML document.
     */
    public ArrayList<Message> getAllMessages(Document d) {

        ArrayList<Message> h = new ArrayList<Message>();

        Element body = (Element) XMLutils.findNode(d, myBodyDefinition);

        NodeList list = body.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals(Message.MSG_DEFINITION)) {
                Message m = new MessageImpl((Element) list.item(i));
                h.add(m);
            }
        }

        return h;
    }

    /**
     * Return all the Message object of this Navajo document.
     */
    public ArrayList<Message> getAllMessages() {
        return getAllMessages(docBuffer);
    }

    /**
     * Return a method object given a method name.
     */
    public Method getMethod(String name) {
        Element e = (Element) XMLutils.findNodeWithAttributeValue(docBuffer, MethodImpl.METHOD_DEFINITION,
                MethodImpl.METHOD_NAME, name);

        if (e != null) {
            Method m = new MethodImpl(e);

            return m;
        } else
            return null;
    }

    /**
     * Return a arraylist of message objects given a regular expression name.
     */
    public ArrayList<Message> getMessages(String name) throws NavajoException {

        // System.out.println("in getMessages(), name = " + name);
        ArrayList<Message> messages = new ArrayList<Message>();
        ArrayList<Message> sub = null;
        ArrayList<Message> sub2 = null;

        Node body = XMLutils.findNode(docBuffer, myBodyDefinition);

        // Strip absolute path indicator, because we are already at the top level.
        if (name.startsWith(Navajo.MESSAGE_SEPARATOR))
            name = name.substring(1, name.length());

        if (name.indexOf(Navajo.MESSAGE_SEPARATOR) != -1) // contains a path, descent it first
        {
            StringTokenizer tok = new StringTokenizer(name, Navajo.MESSAGE_SEPARATOR);
            Message m = null;

            while (tok.hasMoreElements()) {
                String msgName = tok.nextToken();

                // System.out.println("--> msgName = " + msgName);
                if (sub == null) { // First message in path.
                    sub = getMessages(msgName);
                } else {// Subsequent submessages in path.
                    messages = new ArrayList<Message>();
                    for (int i = 0; i < sub.size(); i++) {
                        m = sub.get(i);
                        // System.out.println("for loop, parent message m = " + m.getName());
                        sub2 = m.getMessages(msgName);
                        messages.addAll(sub2);
                    }
                    sub = messages;
                }
            }
            return sub;
            // return messages;
        } else {
            try {
                // Only find first level messages.
                Pattern pattern = Pattern.compile(name);
                NodeList list = body.getChildNodes();

                for (int i = 0; i < list.getLength(); i++) {
                    if (list.item(i).getNodeName().equals(Message.MSG_DEFINITION)) {
                        Element e = (Element) list.item(i);
                        String type = e.getAttribute("type");

                        if ((type != null) && (type.equals(Message.MSG_TYPE_ARRAY)) &&
                             pattern.matcher(e.getAttribute(Message.MSG_NAME)).matches() ) {
                          Message msg = new MessageImpl(e);
                          messages.addAll(msg.getAllMessages());
                        } else {
                          if (pattern.matcher(e.getAttribute(Message.MSG_NAME)).matches()) {
                              Message m = new MessageImpl(e);
                              messages.add(m);
                          }
                        }
                    }
                }
                return messages;
            } catch (Exception ree) {
                throw new NavajoExceptionImpl(ree.getMessage());
            }
        }
    }

    /**
     * Return a message object given a message name.
     */
    public Message getMessage(String name) {

        if (name == null || name.equals(""))
            return null;

        Node body = XMLutils.findNode(docBuffer, myBodyDefinition);

        if (name.indexOf(Navajo.MESSAGE_SEPARATOR) != -1) // contains a path, descent it first
        {
            StringTokenizer tok = new StringTokenizer(name, Navajo.MESSAGE_SEPARATOR);
            Message m = null;

            while (tok.hasMoreElements()) {
                String msgName = tok.nextToken();
                if (m == null) // First message in path.
                    m = getMessage(msgName);
                else  // Subsequent submessages in path.
                    m = m.getMessage(msgName);
                if (m == null)
                    return null;
            }
            return m;
        } else {
            // Only find first level messages.
            NodeList list = body.getChildNodes();
            for (int i = 0; i < list.getLength(); i++) {
                if (list.item(i).getNodeName().equals(Message.MSG_DEFINITION)) {
                    Element e = (Element) list.item(i);
                    String type = e.getAttribute("type");
                    String msgName = e.getAttribute(Message.MSG_NAME);
                    StringTokenizer arEl = new StringTokenizer(name, "()");
                    String realName = "";
                    try {
                      realName = arEl.nextToken();

                    } catch (Exception ee) {

                      ee.printStackTrace();
                      //System.out.println("NAME = " + name + ", MSGNAME = " + msgName);
                    }
                    if ((type != null) && (type.equals(Message.MSG_TYPE_ARRAY)) && msgName.equals(realName)) {
                      if (arEl.hasMoreTokens()) {
                        String index = arEl.nextToken();
                        //System.out.println("index = " + index);
                        Message mp = new MessageImpl(e);
                        ArrayList<Message> elements = mp.getAllMessages();
                        for (int j = 0; j < elements.size(); j++) {
                          Message m = elements.get(j);
                          if ((m.getIndex()+"").equals(index))
                            return m;

                        }
                      } else {
                         return new MessageImpl(e);
                      }
                    } else {
                      if (msgName.equals(realName)) {
                          Message m = new MessageImpl(e);
                          return m;
                      }
                    }
                }
            }

            return null;
        }
    }

    /**
     * Return a message object from the DIRECT CHILDS of a Node n.
     */
    public Message getMessage(Node n, String name) {
        NodeList list = n.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals(Message.MSG_DEFINITION)) {
                Element e = (Element) list.item(i);

                if (e.getAttribute(Message.MSG_NAME).equals(name)) {
                    return new MessageImpl(e);
                }
            }
        }

        return null;
    }

    public ArrayList<Property> getProperties(String regularExpression) throws NavajoException {
        ArrayList<Property> props = new ArrayList<Property>();
        Property prop = null;
        ArrayList<Message> messages = null;
    
        String property = null;
        Message message = null;

        StringTokenizer tok = new StringTokenizer(regularExpression, Navajo.MESSAGE_SEPARATOR);
        String messageList = "";

        int count = tok.countTokens();

        for (int i = 0; i < count - 1; i++) {
            property = tok.nextToken();
            messageList += property;
            if ((i + 1) < count - 1)
                messageList += Navajo.MESSAGE_SEPARATOR;
        }
        String realProperty = tok.nextToken();


        messages = this.getMessages(messageList);
        for (int i = 0; i < messages.size(); i++) {
            message = messages.get(i);

            prop = message.getProperty(realProperty);

            if (prop != null)
                props.add(prop);
        }
        return props;
    }

    /**
     * Get the property, give the property path: <message>.[...].<property>.
     * Return null if the property does not exist.
     */
    public Property getProperty(String property) {

        Property prop = null;
        StringTokenizer tok = new StringTokenizer(property, Navajo.MESSAGE_SEPARATOR);
        Message message = null;

        int count = tok.countTokens();
        int index = 0;

        while (tok.hasMoreElements()) {
            property = tok.nextToken();
            // Check if last message/property reached.
            if (index == (count - 1)) { // Reached property field.
                if (message != null) {
                    // Check if name contains ":", which denotes a selection.
                    if (property.indexOf(":") != -1) {
                        StringTokenizer tok2 = new StringTokenizer(property, ":");
                        String propName = tok2.nextToken();
                        prop = message.getProperty(propName);
                    } else {
                        prop = message.getProperty(property);
                    }
                }
            } else { // Descent message tree.
                if (index == 0) // First message.
                    message = this.getMessage(property);
                else // Subsequent messages.
                if (message == null)
                    return null;
                else
                    message = message.getMessage(property);
                
            }
            index++;
        }

        return prop;
    }

    /**
     * Get the selection option, given the property path: <message>.[...].<property>:<option>.
     * Return null if the property does not exist.
     */
    public Selection getSelection(String property) throws NavajoException {

        Selection sel = null;
        Property prop = null;
        StringTokenizer tok = new StringTokenizer(property, Navajo.MESSAGE_SEPARATOR);
        Message message = null;

        int count = tok.countTokens();
        int index = 0;

        while (tok.hasMoreElements()) {
            property = tok.nextToken();
            // Check if last message/property reached.
            if (index == (count - 1)) { // Reached property field.
                if (message != null) {
                    // Check if name contains ":", which denotes a selection.
                    if (property.indexOf(":") != -1) {
                        StringTokenizer tok2 = new StringTokenizer(property, ":");
                        String propName = tok2.nextToken();
                        String selName = tok2.nextToken();

                        prop = message.getProperty(propName);
                        sel = prop.getSelection(selName);
                    } else {
                        // Does not contain a selection option.
                        return null;
                    }
                }
            } else { // Descent message tree.
                if (index == 0) // First message.
                    message = this.getMessage(property);
                else // Subsequent messages.
                 if (message == null)
                    return null;
                 else
                     message = message.getMessage(property);
                
            }
            index++;
        }

        return sel;
    }

    /**
     * Return true if a message with a specific name exists in the Navajo document, else false.
     */
    public boolean contains(String name) {
        if (this.getMessage(XMLutils.findNode(docBuffer, myBodyDefinition), name)
                != null)
            return true;
        else
            return false;
    }

    /**
     * Strip off the "description" tags and delete all not selected options.
     */
    private final void stripMessage(Node n) {

        Node node = null, e = null;
        Element m = null, f = null;
        Attr at;
        NodeList optionList = null;

        NodeList propertyList = n.getChildNodes();

        for (int i = 0; i < propertyList.getLength(); i++) {
            node = propertyList.item(i);
            if (node.getNodeName().equals(PropertyImpl.PROPERTY_DEFINITION)) {
                m = (Element) node;
                try {
                    if ((at = m.getAttributeNode(PropertyImpl.PROPERTY_DESCRIPTION))
                            != null)
                        m.removeAttributeNode(at);
                    if ((at = m.getAttributeNode(PropertyImpl.PROPERTY_LENGTH))
                            != null)
                        m.removeAttributeNode(at);
                    if ((at = m.getAttributeNode(PropertyImpl.PROPERTY_CARDINALITY))
                            != null)
                        m.removeAttributeNode(at);
                } catch (Exception ex1) {}
                try {
                    if (m.getAttribute(PropertyImpl.PROPERTY_TYPE).equals(PropertyImpl.SELECTION_PROPERTY)) {
                        // remove option nodes that are not selected

                        optionList = m.getChildNodes();
                        for (int j = 0; j < optionList.getLength(); j++) {
                            e = optionList.item(j);
                            if (e.getNodeName().equals(SelectionImpl.SELECTION_DEFINITION)) {

                                f = (Element) e;
                                if (f.getAttribute(SelectionImpl.SELECTION_SELECTED).equals(SelectionImpl.SELECTION_OFF)) {

                                    f.getParentNode().removeChild(f);
                                }
                            }
                        }
                    }

                } catch (Exception ex2) {}
            }
        }
    }

    /**
     * Return an XML node of the specified message in the XML message buffer.
     * If stripped is true: strip description and length tags from properties
     * delete unselected options.
     */
    public Node getMessage(String name, boolean stripped) {

        Node n;
        NodeList MessageList = docBuffer.getElementsByTagName(Message.MSG_DEFINITION);

        for (int i = 0; i < MessageList.getLength(); i++) {
            n = MessageList.item(i);
            if (n.getNodeName().equals(Message.MSG_DEFINITION)) {
                Element m = (Element) n;

                if (m.getAttribute(Message.MSG_NAME).equals(name)) {
                    if (stripped) stripMessage(n);
                    return n;
                }
            }
        }

        return null;
    }

    /**
     * As above, except that only the DIRECT CHILDS of a Node n are searched.
     */
    public Node getMessage(Node begin, String name, boolean stripped) {
        Node n;
        NodeList MessageList = begin.getChildNodes();

        for (int i = 0; i < MessageList.getLength(); i++) {
            n = MessageList.item(i);
            if (n.getNodeName().equals(Message.MSG_DEFINITION)) {
                Element m = (Element) n;

                if (m.getAttribute(Message.MSG_NAME).equals(name)) {
                    if (stripped) stripMessage(n);
                    return n;
                }
            }
        }
        return null;
    }

    /**
     * Create the initial XML message buffer using an XML document as input.
     */
    public void createDocBuffer(Document d) {
        docBuffer = d;
    }

    private final void clearAllSelections(Message m) throws NavajoException {

        ArrayList<Message> list = m.getAllMessages();

        for (int i = 0; i < list.size(); i++)
            clearAllSelections(list.get(i));
        ArrayList<Property> props = m.getAllProperties();

        for (int i = 0; i < props.size(); i++) {
            Property prop = props.get(i);

            if (prop.getType().equals(PropertyImpl.SELECTION_PROPERTY)
                    && prop.getCardinality().equals("+")) {

                ArrayList<Selection> all = prop.getAllSelections();

                for (int j = 0; j < all.size(); j++) {
                    Selection sel = all.get(j);

                    sel.setSelected(false);
                }
            } else if (prop.getType().equals(PropertyImpl.BOOLEAN_PROPERTY)) {
                prop.setValue(PropertyImpl.FALSE);
            }
        }
    }

    public Navajo copy() {
      throw new UnsupportedOperationException("Navajo.copy() not implemented in jaxpimpl.");
    }

    public void clearAllSelections() throws NavajoException {
        //Element body = (Element) XMLutils.findNode(this.docBuffer, myBodyDefinition);
        ArrayList<Message> list = getAllMessages();
        for (int i = 0; i < list.size(); i++) {
            clearAllSelections(list.get(i));
        }
    }

    public Message copyMessage(Message message, Navajo newDocument) {

        Node n = (Node) message.getRef();

        Node nn = ((Document) newDocument.getMessageBuffer()).importNode(n, true);

        return new MessageImpl((Element) nn);
    }

    public Message copyMessage(String name, Navajo newDocument) {

        Message message = this.getMessage(name);
        Node n = (Node) message.getRef();

        Node nn = ((Document) newDocument.getMessageBuffer()).importNode(n, true);

        return new MessageImpl((Element) nn);
    }

    public Method copyMethod(String name, Navajo newDocument) {

        Method method = this.getMethod(name);
        Node n = (Node) method.getRef();
        Node nn = ((Document) newDocument.getMessageBuffer()).importNode(n, true);

        return new MethodImpl((Element) nn);
    }

    public Method copyMethod(Method method, Navajo newDocument) {

        Node n = (Node) method.getRef();
        Node nn = ((Document) newDocument.getMessageBuffer()).importNode(n, true);

        return new MethodImpl((Element) nn);
    }

    /**
     * Append the docBuffer with a new Navajo Document.
     */
    public void appendDocBuffer(Object o) throws NavajoException {

        String name;
        int i;
        Document d = (Document) o;

        if (d == null)
            throw new NavajoExceptionImpl("appendDocBuffer(): empty Document cannot be appended");

        if (docBuffer == null)
            throw new NavajoExceptionImpl("appendDocBuffer(): empty docBuffer");

        Node body = XMLutils.findNode(docBuffer, myBodyDefinition);

        if (body == null)
            throw new NavajoExceptionImpl("appendDocBuffer(): no body element found in docBuffer");

        Node receivedBody = XMLutils.findNode(d, myBodyDefinition);
        NodeList list = receivedBody.getChildNodes();

        for (i = 0; i < list.getLength(); i++) {

            if (list.item(i).getNodeName().equals(Message.MSG_DEFINITION)) {
                Element e = (Element) list.item(i);

                name = e.getAttribute(Message.MSG_NAME);

                // if message already exists, remove old message
                // NOTE: SOME MESSAGE TYPE CAN BE PRESENT MORE THAN ONCE
                // AN ATTRIBUTE WILL HAVE TO BE DEFINED IF THIS IS THE CASE!!!
                // SEE ALSO addMessage() method.
                // USE volgnummers OR SOMETHING LIKE THAT?
                Node m = getMessage(body, name, false);

                if (m != null) {
                    body.removeChild(m);
                }
                Node n = docBuffer.importNode(list.item(i), true);

                body.appendChild(n);
            }
        }

        // Append "methods". Find "methods" tag incoming document
        Node first = XMLutils.findNode(d, Navajo.METHODS_DEFINITION);

        if (first != null) {
            // Check to see if "methods" node exists in current docBuffer
            // If not create one, else use it.
            Node methodElement = XMLutils.findNode(docBuffer, Navajo.METHODS_DEFINITION);

            if (methodElement == null) {
                methodElement = docBuffer.createElement(Navajo.METHODS_DEFINITION);
                body.appendChild(methodElement);
            }

            list = d.getElementsByTagName(MethodImpl.METHOD_DEFINITION);
            for (i = 0; i < list.getLength(); i++) {
                if (list.item(i).getNodeName().equals(MethodImpl.METHOD_DEFINITION)) {
                    Element e = (Element) list.item(i);

                    name = e.getAttribute(MethodImpl.METHOD_NAME);
                    Node f =
                            XMLutils.findNodeWithAttributeValue(docBuffer,
                            MethodImpl.METHOD_DEFINITION,
                            MethodImpl.METHOD_NAME,
                            name);

                    if (f != null) {
                        methodElement.removeChild(f);
                    }
                    Node m = docBuffer.importNode(list.item(i), true);

                    methodElement.appendChild(m);
                }

            }
        }
    }

    /**
     * Return the current XML message buffer.
     * If the NavajoTransaction style flag is set, return a NavajoTransaction style
     * XML document.
     */
    public Object getMessageBuffer() {
        return this.docBuffer;
    }

    private Node getMethodByName(String aMethodName) {
        if (docBuffer != null) {
            Node myMethodsNode = XMLutils.findNode((Document) getMessageBuffer(), Navajo.METHODS_DEFINITION);

            if (myMethodsNode == null) return null;
            NodeList myMethodNodeList = myMethodsNode.getChildNodes();

            for (int i = 0; i < myMethodNodeList.getLength(); i++) {
                Node myNode = myMethodNodeList.item(i);

                if (myNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element myElement = (Element) myNode;
                    String myName = myElement.getAttribute(MethodImpl.METHOD_NAME);

                    if (myName.equals(aMethodName)) return myElement;
                }
            }
        }
        return null;
    }

    public String toString() {
    	StringWriter writer = new StringWriter();
    	
    	//XMLDocumentUtils.toXML(docBuffer,null,null, new StreamResult(writer));
    	XMLDocumentUtils.write( docBuffer, writer, true );
    	
    	return writer.toString();
    	//Node body = XMLutils.findNode(docBuffer, myBodyDefinition);
    	//return XMLDocumentUtils.toString(body);
    }

    /**
     * Add a method to the Navajo document. If the method name already exists, replace the old one.
     */
    public void addMethod(Method m) {

        Element body = (Element) XMLutils.findNode(docBuffer, myBodyDefinition);
        Element methods = (Element) XMLutils.findNode(docBuffer, Navajo.METHODS_DEFINITION);

        if (methods == null) {
            methods = docBuffer.createElement(Navajo.METHODS_DEFINITION);
            body.appendChild(methods);
        }

        Method dummy = this.getMethod(m.getName());

        if (dummy != null)
            methods.removeChild((Node) dummy.getRef());

        methods.appendChild((Node) m.getRef());
    }

    public void addMap(com.dexels.navajo.document.MapTag map) throws NavajoException {
       Node body = XMLutils.findNode(docBuffer, myBodyDefinition);
       body.appendChild((Node) map.getRef());
    }

    public Message addMessage(Message message) throws NavajoException {
        return addMessage(message, true);
    }

    /**
     * Add a message to the Navajo document. If the message name already exists, replace the old one.
     */
    public Message addMessage(Message message, boolean overwrite) throws NavajoException {

         // Do not add messages with mode "ignore".
        if (message.getMode().endsWith(Message.MSG_MODE_IGNORE)) {
           //System.out.println("IGNORING ADDMESSAGE(), MODE = IGNORE!!!!!!!");
           return null;
        }

        Node body = XMLutils.findNode(docBuffer, myBodyDefinition);
        Message dummy = this.getMessage(body, message.getName());

        if ((dummy != null) && !overwrite)
            return dummy;

        if (dummy != null)
            body.removeChild((Node) dummy.getRef());

        body.appendChild((Node) message.getRef());

        return message;
    }

    public void removeMessage(Message message) {
        if (message != null) {
            Node body = XMLutils.findNode(docBuffer, myBodyDefinition);

            body.removeChild((Node) message.getRef());
        }
    }

    /**
     * Delete a message from a specified message.
     */
    public void removeMessage(String message) {

        Message msg = this.getMessage(XMLutils.findNode(docBuffer, myBodyDefinition), message);

        if (msg != null)
            removeMessage(msg);
    }

    /**
     * Persistence interface stuff.
     */

    public String persistenceKey() {
    	String result = this.toString();
    	return result.hashCode() + "";
    }

    public void write(java.io.Writer writer) throws NavajoException {
    	write(writer, false, null);
    }
    
    public void write(OutputStream o) throws NavajoException {
    	write(o, false, null);
    }
      
    public void write(java.io.Writer writer, boolean condense, String method) throws NavajoException {
        //XMLDocumentUtils.toXML(docBuffer, null, null, new StreamResult(writer));
    	
    	XMLDocumentUtils.write( docBuffer, writer, true );
    	
    }

    public void write(java.io.OutputStream stream, boolean condense, String method) throws NavajoException {
    	//XMLDocumentUtils.toXML(docBuffer, null, null, new StreamResult(stream));
    	
    	OutputStreamWriter osw;
		try {
			osw = new OutputStreamWriter( stream, "UTF-8" );
			XMLDocumentUtils.write( docBuffer, osw, true );
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
    	
    	
    }

    public void removeHeader() {
      Element body = (Element) XMLutils.findNode(docBuffer, Navajo.BODY_DEFINITION);
      removeHeader(body);

    }

    private final void removeHeader(Node body) {
      Element header = (Element) XMLutils.findNode(docBuffer, "header");
      if (header != null)
        body.removeChild(header);
    }

    public void addHeader(Header h) {
        Element body = (Element) XMLutils.findNode(docBuffer, Navajo.BODY_DEFINITION);
        removeHeader(body);
        body.appendChild((Element) h.getRef());
    }

    public Header getHeader() {
        Element body = (Element) XMLutils.findNode(docBuffer, "header");
        if (body == null)
          return null;
        else
          return new HeaderImpl(body);
    }

    public void importMessage(Message m) {
        Element body = (Element) XMLutils.findNode(docBuffer, Navajo.BODY_DEFINITION);
        Node n = docBuffer.importNode((Node) m.getRef(), true);
        body.appendChild(n);
    }

    public Message getRootMessage() {
      throw new UnsupportedOperationException("The getRootMessage method should not be used anymore. It is only implemented in the nanoimpl!");
    }

    public LazyMessagePath getLazyMessagePath(String path) {
      throw new UnsupportedOperationException("The getLazyMessagePath is not implemented in JaxpImpl. Sorry.");
    }
    public void addLazyMessagePath(String message,int startIndex, int endIndex, int total) {
      throw new UnsupportedOperationException("The addLazyMessagePath is not implemented in JaxpImpl. Sorry.");
    }

    public boolean isEqual(Navajo o) {

      try {
        Navajo other = o;
        ArrayList<Message> otherMsgs = other.getAllMessages();
        ArrayList<Message> myMsgs = this.getAllMessages();
        if (otherMsgs.size() != myMsgs.size())
          return false;
        for (int i = 0; i < otherMsgs.size(); i++) {
          Message otherMsg = otherMsgs.get(i);
          boolean match = false;
          for (int j = 0; j < myMsgs.size(); j++) {
            Message myMsg = myMsgs.get(j);
            if (myMsg.isEqual(otherMsg, "")) {
              match = true;
              j = myMsgs.size() + 1;
            }
          }
          if (!match)
            return false;
        }
      }
      catch (Exception e) {
        return false;
      }
      return true;
    }

    public void read(java.io.Reader stream) throws NavajoException {

      throw new UnsupportedOperationException("Not implemented in jaxpimpl");
//      XMLElement xe = new CaseSensitiveXMLElement();
//      try {
//        xe.parseFromReader(stream);
//      }
//      catch (XMLParseException ex) {
//        throw new NavajoExceptionImpl(ex);
//      }
//      catch (IOException ex) {
//        throw new NavajoExceptionImpl(ex);
//      }
//      fromXml(xe);
    }

    public void read(java.io.InputStream stream) throws NavajoException {
      this.docBuffer = XMLDocumentUtils.createDocument(stream, false);
    }
    public List refreshExpression() throws NavajoException {
      throw new UnsupportedOperationException("Can not refresh expressions in JAXPIMPL");
    }


	public void firePropertyDataChanged(Property p, Object oldValue, Object newValue) {
	     throw new UnsupportedOperationException("Can not firePropertyDataChanged expressions in JAXPIMPL");
		
	}


	public void addPropertyChangeListener(PropertyChangeListener p) {
	     throw new UnsupportedOperationException("Can not addPropertyChangeListener expressions in JAXPIMPL");
	}

	public void removePropertyChangeListener(PropertyChangeListener p) {
	     throw new UnsupportedOperationException("Can not removePropertyChangeListener expressions in JAXPIMPL");
		
	}

}
