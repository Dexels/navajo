/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

package com.dexels.navajo.document;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import org.w3c.dom.*;

import com.dexels.navajo.xml.XMLutils;
import com.dexels.navajo.xml.XMLDocumentUtils;
import com.dexels.navajo.util.*;
import javax.servlet.http.HttpServletRequest;
import gnu.regexp.*;

/**
 * This class implements the XML message buffer and its
 * manipulation methods.
 *
 * @author Arjen Schoneveld (Dexels/Brentfield)
 * @version 0.1 (11/7/2000)
 * TODO: throw new Exceptions when neccessary.
 * ALSO PUT THE getAction() related messages in this class.
 */

public class Navajo implements java.io.Serializable {

    /**
     * Public constants.
     */

    public static final String METHODS_DEFINITION = "methods";
    public static final String BODY_DEFINITION = "tml";
    public static final String MESSAGE_SEPARATOR = "/";

    /**
     * The XML message buffer. The message buffer contains all the
     * XML messages that have been received from the server.
     */
    private Document docBuffer;

    private String documentName = "ANONYMOUS";

    private ArrayList currentMessages = null;
    private ArrayList currentActions = null;

    private Node messagePointer;
    private Node propertyPointer;
    private String errorDescription;
    private int errorNumber;

    private void initDocument() throws NavajoException {
      docBuffer = XMLDocumentUtils.createDocument();
      Element body = (Element) docBuffer.createElement(Navajo.BODY_DEFINITION);
      docBuffer.appendChild(body);
    }

    /**
     * Initialize the XML message buffer
     */
    public Navajo() throws NavajoException {
      initDocument();
    }

    /**
     * Initialize the XML message buffer with a predefined document
     */
    public Navajo(Document d) {
      docBuffer = (Document) d;
      currentMessages = this.getAllMessages(docBuffer);
      currentActions = this.getAllMethods(docBuffer);
    }

    public Navajo(Document d, String name) {
      docBuffer = d;
      currentMessages = this.getAllMessages(d);
      currentActions = this.getAllMethods(d);
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

    public ArrayList getCurrentActions() {
      return currentActions;
    }

    public ArrayList getCurrentMessages() {
      return currentMessages;
    }

     /**
     * Create a TML header.
     */
    public static Element createHeader(Document d, String rpcName,
                                           String rpcUser, String rpcPwd,
                                           RequestHeader request) {

	Element header = (Element) d.createElement("header");
	Element client = (Element) d.createElement("client");
	Element server = (Element) d.createElement("server");
	Element agent = (Element) d.createElement("agent");
	Element transaction =
	    (Element) d.createElement("transaction");

	header.appendChild(client);
	header.appendChild(server);
	header.appendChild(agent);
	header.appendChild(transaction);

	// Creeer "client" tag
        if (request != null) {

          String address = request.getHeader(RequestHeader.HTTP_REMOTE_ADDR);
          String host = "UNKNOWN";
          try {
            host = java.net.InetAddress.getByName(address).getHostName();
          } catch (Exception e) {

          }

	  client.setAttribute("address",
                             request.getHeader(RequestHeader.HTTP_REMOTE_ADDR));
          client.setAttribute("host", host);
          client.setAttribute("user",
                             request.getHeader(RequestHeader.HTTP_REMOTE_USER));

	Element xmlhttp =
	    (Element) d.createElement("http");
	  xmlhttp.setAttribute("accept",
                               request.getHeader("accept"));
	  xmlhttp.setAttribute("accept_language",
                               request.getHeader("accept-language"));
	  xmlhttp.setAttribute("accept_encoding",
                               request.getHeader("accept-encoding"));
	  xmlhttp.setAttribute("cookie",
                               request.getHeader("cookie"));
	  xmlhttp.setAttribute("referer", "");
	  xmlhttp.setAttribute("user_agent",
                               request.getHeader("user-agent"));
	  xmlhttp.setAttribute("https", "");

	  client.appendChild(xmlhttp);
        }

	// Creeer "transaction" tag
	transaction.setAttribute("type", "");
	transaction.setAttribute("rpc_name", rpcName);
	transaction.setAttribute("rpc_usr", rpcUser);
	transaction.setAttribute("rpc_pwd", rpcPwd);

    return header;
    }

    /**
     * Return all the Method objects in the Navajo document.
     */
    public ArrayList getAllMethods(Document d) {

      NodeList list = null;
      Node n = XMLutils.findNode(d, Navajo.METHODS_DEFINITION);
      ArrayList h = new ArrayList();

      if (n != null) {
        list = n.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
          if (list.item(i).getNodeName().equals(Method.METHOD_DEFINITION)) {
            Method m = new Method((Element) list.item(i));
            h.add(m);
          }
        }
      }

      return h;
    }


    /**
     * Return all the method names in the current XML action buffer.
     */
    public ArrayList getAllMethods() {

      Node n;

      ArrayList props = new ArrayList();

      if (docBuffer != null) {

    	  n = XMLutils.findNode(docBuffer, Navajo.METHODS_DEFINITION);

    	  if (n != null) {
    	      NodeList list = n.getChildNodes();

    	      int index = 0;
     	      for (int i = 0; i < list.getLength(); i++) {
    		  if (list.item(i).getNodeName().equals(Method.METHOD_DEFINITION)) {
    		      Element f = (Element) list.item(i);
    		      props.add(index++, f.getAttribute(Method.METHOD_NAME));
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
	    out = new FileWriter("/tmp/docBuffer"+filename);
        XMLDocumentUtils.toXML(docBuffer,null,null,new StreamResult(out));
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

	Element body = (Element) d.createElement(Navajo.BODY_DEFINITION);
	Node n = getMessage(name, false).cloneNode(true);
	d.appendChild(n);
	d.appendChild(n);

	try {
	    FileWriter out = new FileWriter("/tmp/"+filename);
        XMLDocumentUtils.toXML(d,null,null,new StreamResult(out));
	} catch (Exception e) {}
    }

    /**
     * Return the names of the required messages of a specific method (Given the method name).
     */
    protected Vector getRequiredMessages(String method) {

       Vector req = null;

       Element g = (Element)
            XMLutils.findNodeWithAttributeValue(docBuffer, Method.METHOD_DEFINITION,
                                       Method.METHOD_NAME, method);

       if (g != null) {
	   req = new Vector();
	   NodeList list = g.getChildNodes();

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

    /**
     * Return the Message objects in a specific Navajo XML document.
     */
    public ArrayList getAllMessages(Document d) {

      ArrayList h = new ArrayList();

      Element body = (Element) XMLutils.findNode(d, Navajo.BODY_DEFINITION);

      NodeList list = body.getChildNodes();

      for (int i = 0; i < list.getLength(); i++) {
        if (list.item(i).getNodeName().equals(Message.MSG_DEFINITION)) {

          Message m = new Message((Element) list.item(i));
          h.add(m);

        }
      }

      return h;
    }

    /**
     * Return all the Message object of this Navajo document.
     */
    public ArrayList getAllMessages() {
      return getAllMessages(docBuffer);
    }


    /**
     * Return a method object given a method name.
     */
    public Method getMethod(String name) {
      Element e = (Element) XMLutils.findNodeWithAttributeValue(docBuffer, Method.METHOD_DEFINITION,
                                                                        Method.METHOD_NAME, name);
      if (e != null) {
        Method m = new Method(e);
        return m;
      }
      else
        return null;
    }

     /**
     * Return a arraylist of message objects given a regular expression name.
     */
    public ArrayList getMessages(String name) throws NavajoException {

      System.out.println("in getMessages(), name = " + name);
      ArrayList messages = new ArrayList();
      ArrayList sub = null;
      ArrayList sub2 = null;

      Node body = XMLutils.findNode(docBuffer, Navajo.BODY_DEFINITION);

      // Strip absolute path indicator, because we are already at the top level.
      if (name.startsWith(Navajo.MESSAGE_SEPARATOR))
        name = name.substring(1, name.length());

      if (name.indexOf(Navajo.MESSAGE_SEPARATOR) != -1) // contains a path, descent it first
      {
        StringTokenizer tok = new StringTokenizer(name, Navajo.MESSAGE_SEPARATOR);
        Message m = null;
        while (tok.hasMoreElements()) {
          String msgName = tok.nextToken();
          System.out.println("--> msgName = " + msgName);
          if (sub == null) { // First message in path.
            sub = getMessages(msgName);
          }
          else  {// Subsequent submessages in path.
            messages = new ArrayList();
            for (int i = 0; i < sub.size(); i++) {
              m = (Message) sub.get(i);
              System.out.println("for loop, parent message m = " + m.getName());
              sub2 = m.getMessages(msgName);
              messages.addAll(sub2);
            }
            sub = messages;
          }
        }
        return sub;
        //return messages;
      } else {
        try {
          // Only find first level messages.
          RE re = new RE(name);
          NodeList list = body.getChildNodes();
          for (int i = 0; i < list.getLength(); i++) {
             if (list.item(i).getNodeName().equals(Message.MSG_DEFINITION)) {
                Element e = (Element) list.item(i);
                if (re.isMatch(e.getAttribute(Message.MSG_NAME))) {
                  Message m = new Message(e);
                  messages.add(m);
                }
              }
          }
          return messages;
        } catch (REException ree) {
          throw new NavajoException(ree.getMessage());
        }
      }
    }

    /**
     * Return a message object given a message name.
     */
    public Message getMessage(String name) {


      Node body = XMLutils.findNode(docBuffer, Navajo.BODY_DEFINITION);

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
              if (e.getAttribute(Message.MSG_NAME).equals(name)) {
                Message m = new Message(e);
                return m;
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
          if (e.getAttribute(Message.MSG_NAME).equals(name)){
            return new Message(e);
          }
        }
      }

      return null;
    }

  public ArrayList getProperties(String regularExpression) throws NavajoException {
    ArrayList props = new ArrayList();
    Property prop = null;
    ArrayList messages = null;
    ArrayList sub = null;
    ArrayList sub2 = null;
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

    Util.debugLog("messageList = " + messageList + "(" + messageList.length() + ")");
    Util.debugLog("realProperty = " + realProperty);

    messages = this.getMessages(messageList);
    for (int i = 0; i < messages.size(); i++) {
      message = (Message) messages.get(i);
      Util.debugLog("checking message: " + message.getName());
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
          message = message.getMessage(property);
        if (message == null)
          return null;
      }
      index++;
    }

    return prop;
  }

  /**
   * Get the selection option, given the property path: <message>.[...].<property>:<option>.
   * Return null if the property does not exist.
   */
  public Selection getSelection(String property) throws NavajoException
  {

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
          message = message.getMessage(property);
        if (message == null)
          return null;
      }
      index++;
    }

    return sel;
  }


    /**
     * Return true if a message with a specific name exists in the Navajo document, else false.
     */
    public boolean contains(String name) {
      if (this.getMessage(XMLutils.findNode(docBuffer, Navajo.BODY_DEFINITION),name) != null)
        return true;
      else
        return false;
    }


    /**
     * Strip off the "description" tags and delete all not selected options.
     */
    private void stripMessage(Node n) {

       Node node = null, e = null, l = null;
       Element m = null, f = null;
       Attr at;
       NodeList optionList = null;

       NodeList propertyList = n.getChildNodes();

       for (int i = 0; i < propertyList.getLength(); i++) {
         node = propertyList.item(i);
         if (node.getNodeName().equals(Property.PROPERTY_DEFINITION)) {
            m = (Element) node;
            try {
              if ((at = m.getAttributeNode(Property.PROPERTY_DESCRIPTION)) != null)
                m.removeAttributeNode(at);
              if ((at = m.getAttributeNode(Property.PROPERTY_LENGTH)) != null)
                m.removeAttributeNode(at);
              if ((at = m.getAttributeNode(Property.PROPERTY_CARDINALITY)) != null)
                m.removeAttributeNode(at);
            } catch (Exception ex1) {

            }
            try {
              if (m.getAttribute(Property.PROPERTY_TYPE).equals(Property.SELECTION_PROPERTY)) {
                 // remove option nodes that are not selected

                 optionList = m.getChildNodes();
                 for (int j = 0; j < optionList.getLength(); j++) {
                    e = optionList.item(j);
                    if (e.getNodeName().equals(Selection.SELECTION_DEFINITION)) {

                      f = (Element) e;
                      if (f.getAttribute(Selection.SELECTION_SELECTED).equals(Selection.SELECTION_OFF)) {

                         f.getParentNode().removeChild(f);
                      }
                    }
                 }
              }

            } catch (Exception ex2) {

            }
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

    private void clearAllSelections(Message m) throws NavajoException {

      ArrayList list = m.getAllMessages();

      for (int i = 0; i < list.size(); i++)
        clearAllSelections((Message) list.get(i));
      ArrayList props = m.getAllProperties();

      for (int i = 0; i < props.size(); i++) {
        Property prop = (Property) props.get(i);

        if (prop.getType().equals(Property.SELECTION_PROPERTY) && prop.getCardinality().equals("+")) {
           Util.debugLog("Clearing property: " + prop.getName());
          ArrayList all = prop.getAllSelections();
          for (int j = 0; j < all.size(); j++) {
            Selection sel = (Selection) all.get(j);
            sel.setSelected(false);
          }
        }
      }
    }

    public void clearAllSelections() throws NavajoException {
         Element body = (Element) XMLutils.findNode(this.docBuffer, Navajo.BODY_DEFINITION);

         ArrayList list = getAllMessages();

        for (int i = 0; i < list.size(); i++) {
          clearAllSelections((Message) list.get(i));
        }
    }

    public Message copyMessage(Message message, Navajo newDocument) {


      Node n = message.ref;

      Node nn = newDocument.getMessageBuffer().importNode(n, true);

      return new Message((Element) nn);
    }

    public Message copyMessage(String name, Navajo newDocument) {

      Message message = this.getMessage(name);
      Node n = message.ref;

      Node nn = newDocument.getMessageBuffer().importNode(n, true);

      return new Message((Element) nn);
    }


    public Method copyMethod(String name, Navajo newDocument) {

      Method method = this.getMethod(name);
      Node n = method.ref;
      Node nn = newDocument.getMessageBuffer().importNode(n, true);

      return new Method((Element) nn);
    }

    public Method copyMethod(Method method, Navajo newDocument) {


      Node n = method.ref;
      Node nn = newDocument.getMessageBuffer().importNode(n, true);

      return new Method((Element) nn);
    }

    /**
     * Append the docBuffer with a new Navajo Document.
     */
    public void appendDocBuffer(Document d) throws NavajoException, IOException {

	    String name;
        int i;


        if (d == null)
          throw new NavajoException("appendDocBuffer(): empty Document cannot be appended");

        if (docBuffer == null)
          throw new NavajoException("appendDocBuffer(): empty docBuffer");

    	Node body = XMLutils.findNode(docBuffer, Navajo.BODY_DEFINITION);
        if (body == null)
          throw new NavajoException("appendDocBuffer(): no body element found in docBuffer");

        Node receivedBody = XMLutils.findNode(d, Navajo.BODY_DEFINITION);
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
                Node n = docBuffer.importNode(list.item(i),true  );
                body.appendChild(n);
            } else if (list.item(i).getNodeName().equals(AntiMessage.MSG_DEFINITION)) {
                // Anti-message encountered.
                Element e = (Element) list.item(i);
                name = e.getAttribute(AntiMessage.MSG_NAME);
                Util.debugLog("PROCESSING ANTIMESSAGE: " + name);
                Node m = getMessage(body, name, false);
                // If message with the same name exists in current document, remove it.
                if (m != null) {
                Util.debugLog("REMOVING MESSAGE DUE TO ANTIMESSAGE");
                body.removeChild(m);
                }
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

            list = d.getElementsByTagName(Method.METHOD_DEFINITION);
            for (i = 0; i < list.getLength(); i++) {
                if (list.item(i).getNodeName().equals(Method.METHOD_DEFINITION)) {
                    Element e = (Element) list.item(i);
                    name = e.getAttribute(Method.METHOD_NAME);
                    Node f =
                    XMLutils.findNodeWithAttributeValue(docBuffer,
                                        Method.METHOD_DEFINITION,
                                        Method.METHOD_NAME,
                                        name);
                    if (f != null) {
                    methodElement.removeChild(f);
                    }
                    Node m = docBuffer.importNode(list.item(i),true  );
                    methodElement.appendChild(m);
                }

	        }
	    }

        currentActions = this.getAllMethods(d);
        currentMessages = this.getAllMessages(d);
    }

    /**
     * Return the current XML message buffer.
     * If the NavajoTransaction style flag is set, return a NavajoTransaction style
     * XML document.
     */
    public Document getMessageBuffer() {
        return this.docBuffer;
    }

    private Node getMethodByName(String aMethodName) {
      if (docBuffer != null) {
        Node myMethodsNode = XMLutils.findNode(getMessageBuffer(), Navajo.METHODS_DEFINITION);
        if (myMethodsNode == null) return null;
        NodeList myMethodNodeList  = myMethodsNode.getChildNodes();
        for (int i = 0; i < myMethodNodeList.getLength() ; i++ ) {
          Node myNode = myMethodNodeList.item(i);
          if (myNode.getNodeType() == Node.ELEMENT_NODE) {
            Element myElement = (Element)myNode;
            String myName = myElement.getAttribute(Method.METHOD_NAME);
            if (myName.equals(aMethodName)) return myElement;
          }
        }
      }
      return null;
    }

    public String toString() {


       return XMLDocumentUtils.toString(this.getMessageBuffer());

       /**
      java.io.StringWriter w = new java.io.StringWriter();
      try {
        XMLDocumentUtils.toXML(getMessageBuffer(),null,null,new StreamResult( w ));
      } catch (Exception e) {
        return XMLDocumentUtils.toString(this.getMessageBuffer());
      }
      return w.toString();
      */

    }

    /**
     * Add a method to the Navajo document. If the method name already exists, replace the old one.
     */
    public void addMethod(Method m) {

      Element body = (Element) XMLutils.findNode(docBuffer, Navajo.BODY_DEFINITION);
      Element methods = (Element) XMLutils.findNode(docBuffer, Navajo.METHODS_DEFINITION);
      if (methods == null) {
        methods = (Element) docBuffer.createElement(Navajo.METHODS_DEFINITION);
        body.appendChild(methods);
      }

      Method dummy = this.getMethod(m.getName());
      if (dummy != null)
        methods.removeChild(dummy.ref);

      methods.appendChild(m.ref);
    }

    public Message addMessage(Message message) throws NavajoException {
      return addMessage(message, true);
    }

    /**
     * Add a message to the Navajo document. If the message name already exists, replace the old one.
     */
    public Message addMessage(Message message, boolean overwrite) throws NavajoException {

      Node body = XMLutils.findNode(docBuffer, Navajo.BODY_DEFINITION);
      Message dummy = this.getMessage(body, message.getName());

      if ((dummy != null) && !overwrite)
        return dummy;

      if (dummy != null)
        body.removeChild(dummy.ref);

      body.appendChild(message.ref);

      return message;
    }

    public void removeMessage(Message message) {
      if (message != null) {
        Node body = XMLutils.findNode(docBuffer, Navajo.BODY_DEFINITION);
        body.removeChild(message.ref);
      }
    }

    /**
     * Delete a message from a specified message.
     */
    public void removeMessage(String message) {

      Message msg = this.getMessage(XMLutils.findNode(docBuffer, Navajo.BODY_DEFINITION),message);
      if (msg != null)
        removeMessage(msg);
    }

    public static void main(String args[]) throws Exception {
        Navajo doc = new Navajo();
        Message msg0 = Message.create(doc, "aap");
        Message msg1 = Message.create(doc, "noot");
        Message msg1b = Message.create(doc, "noot1");
        Message msg2 = Message.create(doc, "mies");
        Message msg2b = Message.create(doc, "mies");
        Message msg3 = Message.create(doc, "wim");
        Message msg3b = Message.create(doc, "wim");

        doc.addMessage(msg0);
        msg0.addMessage(msg1);
        msg0.addMessage(msg1b);

        msg1.addMessage(msg2);
        msg1b.addMessage(msg2b);

        msg2.addMessage(msg3);
        msg2b.addMessage(msg3b);

        System.out.println(doc);

        ArrayList ref = doc.getMessages("/aap/noot.*/mies/wim");
        System.out.println(ref.toString());


    }

}
