

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.document;


import org.w3c.dom.*;
import com.dexels.navajo.util.Util;
import java.util.ArrayList;
import java.util.*;
import gnu.regexp.*;


/**
 * The message object is used to store properties (see @Property.class).
 */
public class Message {

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

    public Element ref;

    public String toString() {
        return ref.getAttribute(Message.MSG_NAME);
    }

    public String getName() {
        return ref.getAttribute(Message.MSG_NAME);
    }

    /**
     * Return the parent message if there is one.
     * @return
     */

    public Message getParentMessage() {
        Node n = ref.getParentNode();
        if (n instanceof Element) {
          Element e = (Element) n;
          if (e.getTagName().equals("message"))
            return new Message(e);
          else
            return null;
        } else
          return null;
    }

    /**
     * Return the fully qualified Navajo message name.
     */
    public String getFullMessageName() {

        String result = this.getName();
        Node n = ref.getParentNode();

        while (n != null) {
            if (n.getNodeName().equals(Message.MSG_DEFINITION)) {
                Element parent = (Element) n;
                String name = parent.getAttribute(Message.MSG_NAME);

                result = name + Navajo.MESSAGE_SEPARATOR + result;
            }
            n = n.getParentNode();
        }
        return result;
    }

    /**
     * Set the type of a message.
     * Default "simple".
     *
     * @param s
     */
    public void setType(String s) {
      ref.setAttribute(Message.MSG_TYPE, s);
    }

    public String getType() {
      return ref.getAttribute(Message.MSG_TYPE);
    }

    public boolean isArrayMessage() {
      return (this.getType().equals(Message.MSG_TYPE_ARRAY));
    }

    public int getIndex() {
      if (ref.getAttribute(Message.MSG_INDEX) != null)
        return Integer.parseInt(ref.getAttribute(Message.MSG_INDEX));
      else
        return -1;
    }

    /**
     * Set the index of the message.
     * @param name
     */
    public void setIndex(int i) {
      ref.setAttribute(Message.MSG_INDEX, i+"");
    }


    /**
     * Set the name of the message.
     */
    public void setName(String name) {
        ref.setAttribute(Message.MSG_NAME, name);
    }

    /**
     * Create a message.
     */
    public static Message create(Navajo tb, String name) {

        Message p = null;

        Document d = tb.getMessageBuffer();
        Element n = (Element) d.createElement(Message.MSG_DEFINITION);

        p = new Message(n);
        p.setName(name);

        return p;
    }

    /**
     * Add a property to a message. If a property with the specified name already exists,
     * replace it with the new one.
     */
    public void addProperty(Property p) {
        // First check if property is already present. If it is overwrite with new version.
        if (p == null)
            return;

        Property dummy = this.getProperty(p.getName());

        if (dummy != null) {
            ref.removeChild(dummy.ref);
        }
        ref.appendChild(p.ref);
    }

    public Message addMessage(Message m) {
        return addMessage(m, true);
    }

    /**
     * Add a message to a message. If a message with the specified name already exists
     * withing the parent message, replace it with the new one.
     */
    public Message addMessage(Message m, boolean overwrite) {

        if (m == null)
            return null;

        Message dummy = this.getMessage(m.getName());

        if ((dummy != null) && !overwrite)
            return dummy;

        if (dummy != null) {
            ref.removeChild(dummy.ref);
        }
        ref.appendChild(m.ref);
        return m;
    }

    /**
     * Remove a property from a message. If a null value is given as input do nothing.
     */
    public void removeProperty(Property p) {
        if (p != null) {
            ref.removeChild(p.ref);
        }
    }

    /**
     * Remove a message from a message. If a null value is given as input do nothing.
     */
    public void removeMessage(Message m) {
        if (m != null)
            ref.removeChild(m.ref);
    }

    /**
     * Return all properties that match a given regular expression. Regular expression may include sub-messages and even
     * absolute message references starting at the root level.
     */
    public ArrayList getProperties(String regularExpression) throws NavajoException {

        if (regularExpression.startsWith(Navajo.PARENT_MESSAGE+Navajo.MESSAGE_SEPARATOR)) {
          regularExpression = regularExpression.substring((Navajo.PARENT_MESSAGE+Navajo.MESSAGE_SEPARATOR).length());
          return getParentMessage().getProperties(regularExpression);
        } else
        if (regularExpression.startsWith(Navajo.MESSAGE_SEPARATOR)) { // We have an absolute offset
            Util.debugLog("in Message: getProperties(): " + regularExpression);
            Navajo d = new Navajo(this.ref.getOwnerDocument());

            return d.getProperties(regularExpression);
        } else {
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

            //System.out.println("messageList = " + messageList);
            //System.out.println("realProperty = " + realProperty);

            if (!messageList.equals("")) {
              messages = this.getMessages(messageList);
            } else {
              messages = new ArrayList();
              messages.add(this);
            }

            for (int i = 0; i < messages.size(); i++) {
                message = (Message) messages.get(i);
                ArrayList allProps = message.getAllProperties();
                try {
                  RE re = new RE(realProperty);
                  for (int j = 0; j < allProps.size(); j++) {
                    String name = ((Property) allProps.get(j)).getName();
                    if (re.isMatch(name))
                        props.add(allProps.get(j));
                  }
                } catch (REException re) {
                  throw new NavajoException(re.getMessage());
                }
            }
            return props;
        }
    }

    /**
     * Return all messages that match a given regular expression. Regular expression may include sub-messages and even
     * absolute message references starting at the root level.
     */
    public ArrayList getMessages(String regularExpression) throws NavajoException {

        ArrayList messages = new ArrayList();
        ArrayList sub = null;
        ArrayList sub2 = null;

        if (regularExpression.startsWith(Navajo.PARENT_MESSAGE+Navajo.MESSAGE_SEPARATOR)) {
          regularExpression = regularExpression.substring((Navajo.PARENT_MESSAGE+Navajo.MESSAGE_SEPARATOR).length());
          return getParentMessage().getMessages(regularExpression);
        } else
        if (regularExpression.startsWith(Navajo.MESSAGE_SEPARATOR)) { // We have an absolute offset
            Util.debugLog("in Message: getMessages(): " + regularExpression);
            Navajo d = new Navajo(this.ref.getOwnerDocument());

            return d.getMessages(regularExpression);
        } else // Contains submessages.
          if (regularExpression.indexOf(Navajo.MESSAGE_SEPARATOR) != -1) // contains a path, descent it first
        {
            StringTokenizer tok = new StringTokenizer(regularExpression, Navajo.MESSAGE_SEPARATOR);
            Message m = null;

            while (tok.hasMoreElements()) {
                String msgName = tok.nextToken();

                if (sub == null) { // First message in path.
                    sub = getMessages(msgName);
                } else {// Subsequent submessages in path.
                    messages = new ArrayList();
                    for (int i = 0; i < sub.size(); i++) {
                        m = (Message) sub.get(i);
                        sub2 = m.getMessages(msgName);
                        messages.addAll(sub2);
                    }
                    sub = messages;
                }
            }
            return sub;
        }  else {
            ArrayList msgList = getAllMessages();
            ArrayList result = new ArrayList();

            try {
                RE re = new RE(regularExpression);

                for (int i = 0; i < msgList.size(); i++) {
                    String name = ((Message) msgList.get(i)).getName();

                    if (re.isMatch(name))
                        result.add(msgList.get(i));
                }
            } catch (REException re) {
                throw new NavajoException(re.getMessage());
            }
            return result;
        }
    }

    /**
     * Return a message with a specific name if it exists. If it does not exist return null.
     */
    public Message getMessage(String name) {

        if (name.startsWith(Navajo.MESSAGE_SEPARATOR)) { // We have an absolute offset
            Navajo d = new Navajo(this.ref.getOwnerDocument());

            return d.getMessage(name);
        } if (name.startsWith(Navajo.PARENT_MESSAGE+Navajo.MESSAGE_SEPARATOR)) {
           name = name.substring((Navajo.PARENT_MESSAGE+Navajo.MESSAGE_SEPARATOR).length());
           return getParentMessage().getMessage(name);
        } else {
            NodeList list = ref.getChildNodes();

            for (int i = 0; i < list.getLength(); i++) {
                if (list.item(i).getNodeName().equals(Message.MSG_DEFINITION)) {
                    Element e = (Element) list.item(i);

                    if (e.getAttribute(Message.MSG_NAME).equals(name))
                        return new Message(e);
                }
            }
        }
        return null;
    }

    private Property getPathProperty(String property) {

        if (property.startsWith(Navajo.MESSAGE_SEPARATOR)) { // We have an absolute offset
            Util.debugLog("in Message: getProperties(): " + property);
            Navajo d = new Navajo(this.ref.getOwnerDocument());

            return d.getProperty(property);
        } else {
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
    }

    /**
     * Return a property with a specific name if it exists. Property name may include references to sub-messages.
     * Example: getProperty("mymessage/sub1/subsub/propy").
     */
    public Property getProperty(String name) {

        if (name.indexOf(Navajo.MESSAGE_SEPARATOR) != -1)
            return getPathProperty(name);

        if (name.startsWith(Navajo.PARENT_MESSAGE+Navajo.MESSAGE_SEPARATOR)) {

        }

        NodeList list = ref.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals(Property.PROPERTY_DEFINITION)) {
                Element e = (Element) list.item(i);

                if (e.getAttribute(Property.PROPERTY_NAME).equals(name))
                    return new Property(e);
            }
        }
        return null;
    }

    /**
     * Return all properties in this message. Properties in submessages are not included(!).
     */
    public ArrayList getAllProperties() {

        ArrayList h = new ArrayList();
        Property p = null;
        NodeList list = ref.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals(Property.PROPERTY_DEFINITION)) {
                Element e = (Element) list.item(i);

                p = new Property(e);
                h.add(p);
            }
        }

        return h;
    }

    /**
     * Return all messages in this message. Only first level sub-messages are returned(!).
     */
    public ArrayList getAllMessages() {

        ArrayList h = new ArrayList();
        Message m = null;
        NodeList list = ref.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals(Message.MSG_DEFINITION)) {
                Element e = (Element) list.item(i);

                m = new Message(e);
                h.add(m);
            }
        }

        return h;
    }

    /**
     * Check if this message contains a property with a specific name. Property name may include references
     * to sub-messages.
     */
    public boolean contains(String name) {
        if (getProperty(name) != null)
            return true;
        else
            return false;
    }

    public Message(Element e) {
        this.ref = e;
    }

}
