

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 *
 * Support for lazy array messages:
 *
 * <header>
 *   <transaction rpc_usr="" rpc_pwd="" rpc_name="">
 *      <lazymessage name="MemberData" startindex="10" endindex="100"/>
 *   </transaction>
 * </header>
 */
package com.dexels.navajo.document.jaxpimpl;

import java.beans.*;
import java.util.*;
import java.util.regex.*;

import javax.xml.transform.stream.*;

import org.w3c.dom.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.jaxpimpl.xml.*;


/**
 * The message object is used to store properties (see @Property.class).
 */
public final class MessageImpl implements Message, Comparable<Message> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 993688423553100266L;
	private int totalElements;
    private Navajo myRootDoc = null;

    public final transient Element ref;

    public final String toString() {
        return ref.getAttribute(Message.MSG_NAME);
    }

    public final String getName() {
        return ref.getAttribute(Message.MSG_NAME);
    }

    /**
     * Return the parent message if there is one.
     * @return
     */

    public int getCurrentTotal(){
      return -1;
    }

    public void setCurrentTotal(int aap){
      // whoops
    }

    public void setParent(Message m) {
      System.err.println("setParent in message is not used");
    }

    public Property getPropertyAtRow(int row, String name) {
      if (!(MSG_TYPE_TABLE.equals(getType())||MSG_TYPE_ARRAY.equals(getType()))) {
        throw new IllegalArgumentException("Can only do getPropertyAtRow for table or array messages");
      }
      Message m = getMessage(row);
      if (m==null) {
        return null;
      }
      return m.getProperty(name);
    }

    public final Message getParentMessage() {
        Node n = ref.getParentNode();
        if (n instanceof Element) {
          Element e = (Element) n;
          if (e.getTagName().equals("message")) {
            Message candidate = new MessageImpl(e);
            if (candidate.isArrayMessage()) {
              return candidate.getParentMessage();
            } else
              return candidate;
          }
          else
            return null;
        } else
          return null;
    }


    public final Message getArrayParentMessage() {
        Node n = ref.getParentNode();
        if (n instanceof Element) {
          Element e = (Element) n;
          if (e.getTagName().equals("message")) {
            Message candidate = new MessageImpl(e);
            return candidate;
          }
          else
            return null;
        } else
          return null;
    }


   public final void setMessageMap(MessageMappable m){
     // yeah sure..
     System.err.println("WARNING: setMessageMap not (yet) implemented in JAXP Implementation!");
   }

    /**
     * Return the fully qualified Navajo message name.
     */
    public final String getFullMessageName() {

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
        // Added leading slash. (Changed by Frank)
        return "/"+result;
    }

    /**
     * Set the type of a message.
     * Default "simple".
     *
     * @param s
     */
    public final void setType(String s) {
      ref.setAttribute(Message.MSG_TYPE, s);
    }

    public final String getType() {
      return ref.getAttribute(Message.MSG_TYPE);
    }

    public final boolean isArrayMessage() {
      return (this.getType().equals(Message.MSG_TYPE_ARRAY));
    }

    public final int getArraySize(){
        if (!this.isArrayMessage())
          throw new IllegalArgumentException("getArraySize() is only supported by array type messages");
        return ref.getChildNodes().getLength();
    }

    public final int getIndex() {
      if ((ref.getAttribute(Message.MSG_INDEX) != null) && !ref.getAttribute(Message.MSG_INDEX).equals(""))
        return Integer.parseInt(ref.getAttribute(Message.MSG_INDEX));
      else
        return -1;
    }

    /**
     * Set the index of the message.
     * @param name
     */
    public final void setIndex(int i) {
      ref.setAttribute(Message.MSG_INDEX, i+"");
    }

    public final void setCondition(String condition) {
      ref.setAttribute(Message.MSG_CONDITION, condition);
    }

    /**
     * Set the name of the message.
     */
    public final void setName(String name) {
        ref.setAttribute(Message.MSG_NAME, name);
    }

    public final String getMode() {
      return ref.getAttribute(Message.MSG_MODE);
    }

    /**
     * Set the mode of the message.
     * Current modes are: default, lazy.
     *
     * @param mode
     */
    public final void setMode(String mode) {
      ref.setAttribute(Message.MSG_MODE, mode);
    }

    /**
     * Set the total number of lazy array element sub messages.
     * @param c
     */
    public final void setLazyTotal(int c) {
      ref.setAttribute(Message.MSG_LAZY_COUNT, c+"");
    }

    /**
     * Set the total number of remaining lazy array element sub messages.
     *
     * @param c
     */
    public final void setLazyRemaining(int c) {
      ref.setAttribute(Message.MSG_LAZY_REMAINING, c+"");
    }

    /**
     * Set the total number of array element sub messages.
     *
     * @param c
     */
    public final void setArraySize(int c) {
      ref.setAttribute(Message.MSG_ARRAY_SIZE, c+"");
    }

    /**
     * Create a message.
     */

    public static final Message create(Navajo tb, String name) {
      return MessageImpl.create(tb, name, "");
    }

    public static final Message create(Navajo tb, String name, String type) {

        Message p = null;

        Document d = (Document) tb.getMessageBuffer();
        Element n = d.createElement(Message.MSG_DEFINITION);

        p = new MessageImpl(n);
        p.setName(name);
        p.setRootDoc(tb);
        if (!type.equals(""))
          p.setType(type);

        return p;
    }

    /**
     * Add a property to a message. If a property with the specified name already exists,
     * replace it with the new one.
     */
    public final void addProperty(Property p) {
        // First check if property is already present. If it is overwrite with new version.
        if (p == null)
            throw new NullPointerException("Can not add null properties");

        Property dummy = this.getProperty(p.getName());

        if (dummy != null) {
            ref.removeChild((Node) dummy.getRef());
        }
        ref.appendChild((Node) p.getRef());
    }

    /**
     * Use this method to add an element to an array type message.
     *
     * @param m
     * @return
     */
    public final Message addElement(Message m) {
      return addMessage(m, false);
    }

    public final Message addMessage(Message m) {

    	if (m == null) {
            return null;
          }
    	
        // Do not add messages with mode "ignore".
        if (m.getMode().endsWith(Message.MSG_MODE_IGNORE)) {
            //System.out.println("IGNORING ADDMESSAGE(), MODE = IGNORE!!!!!!!");
            return null;
        }

        if (this.getType().equals(Message.MSG_TYPE_ARRAY))
          return addMessage(m, false);
        else
          return addMessage(m, true);
    }

    /**
     * Add a message to a message. If a message with the specified name already exists
     * withing the parent message, replace it with the new one.
     */
    public final Message addMessage(Message m, boolean overwrite) {

        if (m == null)
            return null;

        Message dummy = this.getMessage(m.getName());
        if ((dummy != null) && overwrite && !this.getType().equals(Message.MSG_TYPE_ARRAY))
            return dummy;

        if (dummy != null && overwrite) {
            ref.removeChild((Node) dummy.getRef());
        }
        ref.appendChild((Node) m.getRef());

        /**
         * If message is array type, insert new message as "element".
         */

        if (this.getType().equals(Message.MSG_TYPE_ARRAY)) {
          // Increase element counter.
          if (!m.getType().equals(MSG_TYPE_DEFINITION)) {
            m.setIndex(totalElements++);
            // Element message MUST have same name as parent array message.
            m.setName(getName());

          }
        }

        return m;
    }


    public final void addMessage(Message m, int index) throws NavajoException {
      throw new UnsupportedOperationException("addMessage with index not implemented in JaxpImpl");
    }

    /**
     * Remove a property from a message. If a null value is given as input do nothing.
     */
    public final void removeProperty(Property p) {
        if (p != null) {
            ref.removeChild((Node) p.getRef());
        }
    }

    /**
     * Remove a message from a message. If a null value is given as input do nothing.
     */
    public final void removeMessage(Message m) {
        if (m != null) {
          ref.removeChild( (Node) m.getRef());
        }
    }

    /**
     * Return all properties that match a given regular expression. Regular expression may include sub-messages and even
     * absolute message references starting at the root level.
     */
    public final ArrayList<Property> getProperties(String regularExpression) throws NavajoException {

        if (regularExpression.startsWith(Navajo.PARENT_MESSAGE+Navajo.MESSAGE_SEPARATOR)) {
          regularExpression = regularExpression.substring((Navajo.PARENT_MESSAGE+Navajo.MESSAGE_SEPARATOR).length());
          return getParentMessage().getProperties(regularExpression);
        } else
        if (regularExpression.startsWith(Navajo.MESSAGE_SEPARATOR)) { // We have an absolute offset

            Navajo d = getRootDoc();
            return d.getProperties(regularExpression);
        } else {
            ArrayList<Property> props = new ArrayList<Property>();
//            Property prop = null;
            ArrayList<Message> messages = null;
//            ArrayList<Message> sub = null;
//            ArrayList<Message> sub2 = null;
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


            if (!messageList.equals("")) {
              messages = this.getMessages(messageList);
            } else {
              messages = new ArrayList<Message>();
              messages.add(this);
            }

            Pattern pattern = Pattern.compile(realProperty);
            for (int i = 0; i < messages.size(); i++) {
                message = messages.get(i);
                ArrayList<Property> allProps = message.getAllProperties();
                try {
                  for (int j = 0; j < allProps.size(); j++) {
                    String name = allProps.get(j).getName();
                    if (pattern.matcher(name).matches())
                        props.add(allProps.get(j));
                  }
                } catch (Exception re) {
                  throw new NavajoExceptionImpl(re.getMessage());
                }
            }
            return props;
        }
    }

    /**
     * Return all messages that match a given regular expression. Regular expression may include sub-messages and even
     * absolute message references starting at the root level.
     */
    public final ArrayList<Message> getMessages(String regularExpression) throws NavajoException {

        ArrayList<Message> messages = null;
        ArrayList<Message> sub = null;
        ArrayList<Message> sub2 = null;

        if (regularExpression.startsWith(Navajo.PARENT_MESSAGE+Navajo.MESSAGE_SEPARATOR)) {
          regularExpression = regularExpression.substring((Navajo.PARENT_MESSAGE+Navajo.MESSAGE_SEPARATOR).length());
          return getParentMessage().getMessages(regularExpression);
        } else
        if (regularExpression.startsWith(Navajo.MESSAGE_SEPARATOR)) { // We have an absolute offset

            Navajo d = getRootDoc();

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
                    messages = new ArrayList<Message>();
                    for (int i = 0; i < sub.size(); i++) {
                        m = sub.get(i);
                        sub2 = m.getMessages(msgName);
                        messages.addAll(sub2);
                    }
                    sub = messages;
                }
            }
            return sub;
        }  else {
            ArrayList<Message> msgList = getAllMessages();
            ArrayList<Message> result = new ArrayList<Message>();
            try {
                Pattern re = Pattern.compile(regularExpression);
                for (int i = 0; i < msgList.size(); i++) {
                    Message m = msgList.get(i);
                    String name = m.getName();
                    if (m.getType().equals(Message.MSG_TYPE_ARRAY) && re.matcher(name).matches() ) { // If message is array type add all children.
                      result.addAll(m.getAllMessages());
                    } else {
                      if (re.matcher(name).matches())
                          result.add(msgList.get(i));
                    }
                }
            } catch (Exception re) {
                throw new NavajoExceptionImpl(re.getMessage());
            }
            return result;
        }
    }

    public final Message getMessage(int index) {
      Message m = null;
      NodeList list = ref.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals(Message.MSG_DEFINITION)) {
                Element e = (Element) list.item(i);
                m = new MessageImpl(e);
                if (m.getIndex() == index)
                  return m;
            }
        }

        return null;
    }

    /**
     * Return a message with a specific name if it exists. If it does not exist return null.
     */
    public final Message getMessage(String name) {

        if (name.startsWith(Navajo.MESSAGE_SEPARATOR)) { // We have an absolute offset
            Navajo d = getRootDoc();
            return d.getMessage(name);
        } if (name.startsWith(Navajo.PARENT_MESSAGE+Navajo.MESSAGE_SEPARATOR)) {
           name = name.substring((Navajo.PARENT_MESSAGE+Navajo.MESSAGE_SEPARATOR).length());
           return getParentMessage().getMessage(name);
        } else {
            NodeList list = ref.getChildNodes();
            for (int i = 0; i < list.getLength(); i++) {
                if (list.item(i).getNodeName().equals(Message.MSG_DEFINITION)) {
                    Element e = (Element) list.item(i);
                    String type = e.getAttribute("type");
                    String msgName = e.getAttribute(Message.MSG_NAME);
                    StringTokenizer arEl = new StringTokenizer(name, "()");
                    String realName = arEl.nextToken();
                    if ((type != null) && (type.equals(Message.MSG_TYPE_ARRAY)) && msgName.equals(realName)) {
                      if (arEl.hasMoreTokens()) {
                        String index = arEl.nextToken();
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
                      if (msgName.equals(realName))
                        return new MessageImpl(e);
                    }
                }
            }
        }
        return null;
    }

    public final Property getPathProperty(String property) {

        if (property.startsWith(Navajo.MESSAGE_SEPARATOR)) { // We have an absolute offset

            Navajo d = getRootDoc();

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
                        
                    if (message == null)
                        return null;
                    else 
                    	message = message.getMessage(property);
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
    public final Property getProperty(String name) {

        if (name.indexOf(Navajo.MESSAGE_SEPARATOR) != -1)
            return getPathProperty(name);

//        if (name.startsWith(Navajo.PARENT_MESSAGE+Navajo.MESSAGE_SEPARATOR)) {
//
//        }

        NodeList list = ref.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals(PropertyImpl.PROPERTY_DEFINITION)) {
                Element e = (Element) list.item(i);

                if (e.getAttribute(PropertyImpl.PROPERTY_NAME).equals(name))
                    return new PropertyImpl(e);
            }
        }
        return null;
    }

    /**
     * Return all properties in this message. Properties in submessages are not included(!).
     */
    public final ArrayList<Property> getAllProperties() {

        ArrayList<Property> h = new ArrayList<Property>();
        Property p = null;
        NodeList list = ref.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals(PropertyImpl.PROPERTY_DEFINITION)) {
                Element e = (Element) list.item(i);

                p = new PropertyImpl(e);
                h.add(p);
            }
        }

        return h;
    }

    /**
     * Return all messages in this message. Only first level sub-messages are returned(!).
     */
    public final ArrayList<Message> getAllMessages() {

        ArrayList<Message> h = new ArrayList<Message>();
        Message m = null;
        NodeList list = ref.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals(Message.MSG_DEFINITION)) {
                Element e = (Element) list.item(i);

                m = new MessageImpl(e);
                h.add(m);
            }
        }

        return h;
    }

    /**
     * Check if this message contains a property with a specific name. Property name may include references
     * to sub-messages.
     */
    public final boolean contains(String name) {
        if (getProperty(name) != null)
            return true;
        else
            return false;
    }

    public  MessageImpl(Element e) {
        this.ref = e;
        String type = e.getAttribute("type");
        if ((type != null) && (type.equals("array")))
          this.totalElements = ref.getChildNodes().getLength();
    }

    public final Object getRef() {
      return this.ref;
    }

    public final Navajo getRootDoc() {
      if (myRootDoc == null) {
         Document d = ref.getOwnerDocument();
         myRootDoc = new NavajoImpl(d, new NavajoFactoryImpl());
      }
      return myRootDoc;
    }

    public final void setRootDoc(Navajo n) {
      myRootDoc = n;
    }

    public final void write(java.io.Writer writer) {
      try {
        XMLDocumentUtils.toXML(this.ref, "", "", "", new StreamResult(writer));
      }
      catch (NavajoException ex) {
        ex.printStackTrace(System.err);
      }
    }

    public final void write(java.io.OutputStream stream) {
      try {
        XMLDocumentUtils.toXML(this.ref, "", "", "", new StreamResult(stream));
      }
      catch (NavajoException ex) {
        ex.printStackTrace(System.err);
      }
    }

    public static void main (String [] args) throws Exception {
        Navajo n = NavajoFactory.getInstance().createNavajo();
        Message ar = NavajoFactory.getInstance().createMessage(n, "array");
        n.addMessage(ar);
        ar.setType(Message.MSG_TYPE_ARRAY);
        Message el1 = NavajoFactory.getInstance().createMessage(n, "el");
        Message el2 = NavajoFactory.getInstance().createMessage(n, "el");
        ar.addMessage(el1);
        ar.addMessage(el2);
        Property p = NavajoFactory.getInstance().createProperty(n, "Aap", Property.STRING_PROPERTY, "iets", 10, "", Property.DIR_OUT);
        el1.addProperty(p);
        n.write(System.err);

        ArrayList<Property> l = n.getProperties("/.*/Aap");
        Property s = l.get(0);
        System.err.println("name =" + s.getName() + ", value = " + s.getValue());
        //System.out.println("size = " + ar.getArraySize());
    }

    public final Message copy() throws NavajoException {
      Navajo empty = NavajoFactory.getInstance().createNavajo();
      Message result = copy(empty);
      empty.addMessage(result);
      return result;
    }

    public final Message copy(Navajo n) {
       Node nn = ((Document) n.getMessageBuffer()).importNode(this.ref, true);
       return new MessageImpl((Element) nn);
    }


    public boolean isEqual(Message o) {
     return isEqual(o, "");
   }


    public boolean isEqual(Message o, String skipProperties) {

     //System.err.println("in Message.isEqual(), my name is " + getName() + ", other is " + getName() + ", skipProperties = " + skipProperties);
     Message other = o;
     if (!other.getName().equals(this.getName()))
       return false;
     // Check sub message structure.
     ArrayList<Message> allOther = other.getAllMessages();
     ArrayList<Message> allMe = this.getAllMessages();
     //System.err.println("my msg size is " + allMe.size() + ", other msg size is " + allOther.size());
     if (allOther.size() != allMe.size())
       return false;
     for (int i = 0; i < allOther.size(); i++) {
       Message otherMsg = allOther.get(i);
       boolean match = false;
       for (int j = 0; j < allMe.size(); j++) {
         Message myMsg = allMe.get(j);
         if (myMsg.isEqual(otherMsg, skipProperties)) {
           match = true;
           j = allMe.size() + 1;
         }
       }
       if (!match)
         return false;
     }
     // Check property structure.
     ArrayList<Property> allOtherProps = other.getAllProperties();
     ArrayList<Property> allMyProps = this.getAllProperties();
     if (allOtherProps.size() != allMyProps.size())
       return false;
     for (int i = 0; i < allOtherProps.size(); i++) {
       Property otherProp = allOtherProps.get(i);
       boolean match = false;
       //System.err.println("About to check property: " + otherProp.getName());
       // Check whether property name exists in skipProperties list.
       if (skipProperties.indexOf(otherProp.getName()) != -1) {
         match = true;
       } else {
         for (int j = 0; j < allMyProps.size(); j++) {
           Property myProp = allMyProps.get(j);
           if (myProp.isEqual(otherProp)) {
             match = true;
             j = allMyProps.size() + 1;
           }
         }
       }
       if (!match)
         return false;
     }
     return true;
   }

   public void refreshExpression() throws NavajoException {
     throw new UnsupportedOperationException("Can not refresh expressions in JAXPIMPL");
   }

   public Message addMessage(int index) {
     throw new UnsupportedOperationException("Can not add messages in JAXPIMPL");
   }


   /**
    * Add empty message at the end
    */
   public Message addMessage() {
     throw new UnsupportedOperationException("Can not add messages in JAXPIMPL");
   }
   
   public void setDefinitionMessage(Message m) {
	   throw new UnsupportedOperationException("Can not set definition message in JAXPIMPL");
   }
   
   public Message getDefinitionMessage() {
     Message m = getArrayParentMessage();
     if (m==null) {
       System.err.println("No parent, so no definition");
       return null;
     }
     ArrayList<Message> al = m.getAllMessages();
     for (int i = 0; i < al.size(); i++) {
       Message current = al.get(i);
       if (current.getType().equals(Message.MSG_TYPE_DEFINITION)) {
         return current;
       }
     }
     return null;
//     throw new UnsupportedOperationException("Can not get definition message in JAXPIMPL");
   }

  public String getOrderBy() {
	// TODO Auto-generated method stub
	return null;
  }

  public void setOrderBy(String s) {
	// TODO Auto-generated method stub
	
  }

  public int compareTo(Message o) {
	// TODO Auto-generated method stub
	return 0;
  }



public void firePropertyDataChanged(Property p, Object oldValue, Object newValue) {
	// TODO Auto-generated method stub
	
}

public void addPropertyChangeListener(PropertyChangeListener p) {
	// TODO Auto-generated method stub
	
}

public void removePropertyChangeListener(PropertyChangeListener p) {
	// TODO Auto-generated method stub
	
}

public Message replaceMessage(Message m) {
	if (m == null) {
		return null;

	}

	Message dummy = this.getMessage(m.getName());
	if ( dummy != null ) {
		ref.replaceChild((Node) m.getRef(), (Node) dummy.getRef());
		return m;
	} else {
		return null;
	}	
}


public Map<String,Property> getProperties() {
	throw new IllegalStateException("JAXP IMPL DOES NOT SUPPORT THIS BEAN OPERATOR!");
}

public Map<String,Message> getMessages() {
	throw new IllegalStateException("JAXP IMPL DOES NOT SUPPORT THIS BEAN OPERATOR!");
}

public List<Message> getElements() {
	throw new IllegalStateException("JAXP IMPL DOES NOT SUPPORT THIS BEAN OPERATOR!");
}


}
