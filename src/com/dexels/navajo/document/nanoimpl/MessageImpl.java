package com.dexels.navajo.document.nanoimpl;
/**
 * <p>Title: ShellApplet</p>
 * <p>Description: </p>
 * <p>Part of the Navajo mini client, based on the NanoXML parser</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

import java.util.*;
//import nanoxml.*;
//import com.dexels.sportlink.client.swing.*;
//import com.dexels.navajo.nanoclient.*;
import com.dexels.navajo.document.*;

public class MessageImpl extends BaseNode implements Message {
  private String myName = "";
  private String myType = "";
  private String myMode = "";
  private int myIndex = -1;
  protected Map propertyMap = new TreeMap();
  protected Map messageMap = new TreeMap();
  protected List messageList = new ArrayList();
  private MessageImpl myParent = null;
  private MessageMappable myStringMap = null;
  private int startIndex = -1;
  private int endIndex = -1;

  public MessageImpl(Navajo n) {
    super(n);
    myType = Message.MSG_TYPE_SIMPLE;
  }

  public MessageImpl(Navajo n, String name) {
    super(n);
    myName = name;
    myType = Message.MSG_TYPE_SIMPLE;
  }

  public String getType() {
    return myType;
  }

  public void setType(String type) {
    myType = type;
  }

  public String getName() {
    return myName;
  }

  public void setName(String name){
    myName = name;
  }

  public void setMode(String mode) {
    myMode = mode;
  }

  public String getMode() {
    return myMode;
  }

  public Message addMessage(Message m) {
    if (m==null) {
      System.err.println("Ignoring null message. Not adding message");
      return null;
    }

    messageMap.put(m.getName(),m);
    if (getType().equals(MSG_TYPE_ARRAY)) {
      m.setIndex(messageList.size());
    }
    messageList.add(m);
    ((MessageImpl)m).setParent(this);
    return m;
  }
  public ArrayList getAllMessages() {
//    return new ArrayList(messageMap.values());
    return new ArrayList(messageList);
  }

  public void addProperty(Property q) {
    PropertyImpl p = (PropertyImpl)q;
    propertyMap.put(p.getName(),p);
    p.setMessageName(this.getName());
    p.setParent(this);
  }

  public ArrayList getAllProperties() {
    return getProperties("");
  }

  public ArrayList getProperties(String regexp) {
    Iterator it = propertyMap.values().iterator();
    ArrayList selected = new ArrayList();
    while (it.hasNext()) {
      Property p = (Property)it.next();
      if(compliesWith(p,regexp)) {
        selected.add(p);
      }
    }
    return selected;
  }

  public Message getMessage(String name) {
    return (Message)messageMap.get(name);
  }

  public ArrayList getMessages(String regexp) {
    Iterator it = messageMap.values().iterator();
    ArrayList selected = new ArrayList();
    while (it.hasNext()) {
      Message m = (Message)it.next();
      if(compliesWith(m,regexp)) {
        selected.add(m);
      }
    }
     return selected;
  }
  protected boolean compliesWith(Message m, String expression) {
     return m.getName().startsWith(expression);
  }
  protected boolean compliesWith(Property p, String expression) {
     return p.getName().startsWith(expression);
  }

  public Property getProperty(String s) {
    return (Property)propertyMap.get(s);
  }
  public int getIndex() {
    return myIndex;
  }

  public void setIndex(int index) {
    myIndex = index;
  }

  public XMLElement toXml(XMLElement parent) {
    XMLElement m = new CaseSensitiveXMLElement();
    m.setName("message");
    m.setAttribute("name",myName);
    if ((getType()!=null) && (!"".equals(getType())) && (!Message.MSG_TYPE_SIMPLE.equals(getType())) ) {
      m.setAttribute(MSG_TYPE,getType());
    }
    if (getType().equals(MSG_TYPE_ARRAY_ELEMENT)) {
      m.setAttribute("index",""+getIndex());
    }
    if ((getMode()!=null) && (!"".equals(getMode()))) {
      m.setAttribute("mode",""+getMode());
    }

    if (getStartIndex()>=0) {
      m.setAttribute("startindex",""+getStartIndex());
    }
    if (getEndIndex()>=0) {
      m.setAttribute("endindex",""+getEndIndex());
    }

    for (int i = 0; i < getChildMessageCount(); i++) {
//      Message msg = (Message)messageList.get(i);
      MessageImpl msg = (MessageImpl)getMessage(i);
      if (msg!=null) {
        m.addChild(msg.toXml(m));
      }
    }


    Iterator props = propertyMap.values().iterator();
    while (props.hasNext()) {
      PropertyImpl p = (PropertyImpl)props.next();
      m.addChild(p.toXml(m));
    }
    return m;
  }

  public Message getMessage(int i) {
    if (i>=getChildMessageCount()) {
      System.err.println("Message index out of range");
    }

    return (Message)getAllMessages().get(i);
  }

  // Returns an array element
  public Message getMessage(String name, int index) {
    Message m = getMessage(name);
    if (m==null) {
      System.err.println("No such message: "+name);
      Thread.dumpStack();
      return null;
    }
    if (!m.getType().equals(Message.MSG_TYPE_ARRAY)) {
      System.err.println("Found a non array message, when querying for an array element");
      return null;
    }
    return m.getMessage(index);
  }

  public void removeChildMessage(Message msg){
    messageList.remove(msg);
    messageMap.remove(msg);
  }

  public void fromXml(XMLElement e) {
    for (int i = 0; i < e.countChildren(); i++) {
      XMLElement child = (XMLElement)e.getChildren().elementAt(i);
      String name = child.getName();
      if (name.equals("property")) {
        /** @todo Beware: Will things be affected? */
        PropertyImpl p = null;
        try {
          p = (PropertyImpl)NavajoFactory.getInstance().createProperty(myDocRoot, (String) child.getAttribute("name"), "", "", 0, "", "");
        }
        catch (NavajoException ex) {
          ex.printStackTrace();
        }
        this.addProperty(p);
        p.fromXml(child);
      }
      if (name.equals("message")) {
        String childName = (String)child.getAttribute("name");
        String type = (String) child.getAttribute(MSG_TYPE);
        String index = (String) child.getAttribute(MSG_INDEX);
        String mode = (String) child.getAttribute(MSG_MODE);
        if ((index != null) && !index.equals("")) {

        }

        // Ok, now a simple implentation of the laziness check.
        MessageImpl msg = null;
        if (MSG_MODE_LAZY.equals(mode)) {
          System.err.println("YES! A lazy message!");
          /** @todo Fix again */
//          msg = NavajoFactory.getInstance().createLazyMessage(myDocRoot,childName);
        } else {
          msg = (MessageImpl)NavajoFactory.getInstance().createMessage(myDocRoot,childName);
        }
        if (type != null)
            msg.setType(type);
        if ((index != null) && !index.equals("")) {
          msg.setIndex(Integer.parseInt(index));
          msg.setType(MSG_TYPE_ARRAY_ELEMENT);
        }
        msg.fromXml(child);
        this.addMessage(msg);
       }
    }
  }

  public int getChildMessageCount() {
    return getAllMessages().size();
  }

  public void addArrayMessage(Message m) {
    if (!MSG_TYPE_ARRAY.equals(getType())) {
      throw new RuntimeException("Adding array element to non-array message");
    }
    m.setName(getName());
    addMessage(m);
  }

  public Message copy(Navajo n) {
    MessageImpl cp = (MessageImpl)NavajoFactory.getInstance().createMessage(n,getName());
    cp.setRootDoc(n);
    ArrayList myMsg = getAllMessages();
    cp.setEndIndex(getEndIndex());
    cp.setStartIndex(getStartIndex());
    cp.setIndex(getIndex());
    cp.setMode(getMode());
    cp.setType(getType());

    for (int i = 0; i < myMsg.size(); i++) {
      MessageImpl current = (MessageImpl)myMsg.get(i);
      Message cc = current.copy(n);
      cp.addMessage(cc);
    }
    ArrayList myProp = getAllProperties();
    for (int i = 0; i < myProp.size(); i++) {
      PropertyImpl current = (PropertyImpl)myProp.get(i);
      Property cc = current.copy(n);
      cp.addProperty(cc);
    }
    return cp;
  }

  public void prune() {
    ArrayList myMsg = getAllMessages();
    for (int i = 0; i < myMsg.size(); i++) {
      MessageImpl current = (MessageImpl)myMsg.get(i);
      current.prune();
    }
    ArrayList myProp = getAllProperties();
    for (int i = 0; i < myProp.size(); i++) {
      PropertyImpl current = (PropertyImpl)myProp.get(i);
      current.prune();
    }
  }

  public void setMessageMap(MessageMappable m) {
    myStringMap = m;
    ArrayList myMsg = getAllMessages();
    for (int i = 0; i < myMsg.size(); i++) {
      MessageImpl current = (MessageImpl)myMsg.get(i);
      if (current!=null) {
        current.setMessageMap(m);
      }
    }
  }
  public MessageMappable getMessageMap() {
    return myStringMap;
  }
  public String toString() {
    if (myStringMap!=null) {
      return myStringMap.getMessageLabel(this);
    }

    return getName();
  }

  public void setParent(Message m ) {
    myParent = (MessageImpl)m;
  }

  public Message getParent() {
    return myParent;
  }

  public Message getByPath(String path) {
    /** @todo ARRAY SUPPORT */
    System.err.println("PARSING PATH: "+path);
    if (path.startsWith("/")) {
      System.err.println("PATH STARTED WITH SLASH IN MESSAGE");
      path = path.substring(1);
    }

    int slash = path.indexOf("/");
    if (slash<0) {
      return getMessage(path);
    } else {
      System.err.println("Index: "+slash+" of: "+path);
      String messagename = path.substring(0,slash);
      System.err.println("Sumbessage: "+messagename);
      Message m = getMessage(messagename);
      if (m!=null) {
        return m.getMessage(path.substring(slash+1));
      } else {
        return null;
      }
    }
  }
 public Property getPropertyByPath(String pth) {
    /** @todo ARRAY SUPPORT */
//    System.err.println("PARSING PATH: "+pth);
    String path = null;
    if (pth.startsWith("/")) {
//      System.err.println("PATH STARTED WITH SLASH. STRIPPING /");
      path = pth.substring(1);
    } else {
      path = pth;
    }
    int slash = path.indexOf("/");
    if (slash<0) {
//      System.err.println("No slashes left. Getting property: "+path);
      return getProperty(path);
    } else {
      String msgname = path.substring(0,slash);
      String propname = path.substring(slash,path.length());
//      System.err.println("Getting message: "+msgname);
      MessageImpl ms = (MessageImpl)getMessage(msgname);
      if (ms!=null) {
        return ms.getPropertyByPath(propname);
      } else {
        System.err.println("No such message: "+msgname);
//        Thread.dumpStack();
      }

//      System.err.println("SubProperty: "+propname);
      return null;
    }
  }



  public String getPath() {
    if (myParent!=null) {
      return myParent.getPath()+"/"+getName();
    } else {
      return getName();
    }
  }

  public int getStartIndex() {
    return startIndex;
  }
  public int getEndIndex() {
    return endIndex;
  }
  public void setStartIndex(int i) {
    startIndex = i;
  }
  public void setEndIndex(int i) {
    endIndex = i;
  }
  public XMLElement generateTml(Header h) {
    XMLElement m = new CaseSensitiveXMLElement();
    m.setName("tml");
    m.addChild((XMLElement)h.getRef());
    for (int i = 0; i < getChildMessageCount(); i++) {
      MessageImpl current = (MessageImpl)getMessage(i);
      m.addChild(current.toXml(m));
    }
//
//    Iterator msgs = messageMap.values().iterator();
//    while (msgs.hasNext()) {
//      Message msg = (Message)msgs.next();
//    }
    return m;
  }


  public Message getParentMessage() {
    return getParent();
  }
  public Message addElement(Message m) {
    if (!getType().equals(Message.MSG_TYPE_ARRAY)) {
      throw new IllegalArgumentException("Can not add element to non-array type message!");
    }
    m.setIndex(getArraySize());
    addMessage(m);
    return m;
  }
  public Message addMessage(Message m, boolean overwrite) {
    if (getMessage(m.getName())!=null && overwrite) {
      removeChildMessage(m);
    }
    addMessage(m);
    return m;
  }

  public int getArraySize() {
    return messageList.size();
  }
  public void setArraySize(int i) {
    throw new UnsupportedOperationException("Dont know what this method should do.");
  }
  public boolean isArrayMessage() {
    return MSG_TYPE_ARRAY.equals(getType());
  }

  public String getFullMessageName() {
    return getPath();
  }
  public Property getPathProperty(String path) {
    return getPropertyByPath(path);
  }

  public Object getRef() {
    return toXml(null);
  }
  public void removeMessage(Message msg) {
    removeChildMessage(msg);
  }
  public void removeMessage(String msg) {
    removeChildMessage(getMessage(msg));
  }
  public void removeProperty(Property p) {
    /**@todo Implement this com.dexels.navajo.document.Message abstract method*/
  }
  public void setLazyRemaining(int c) {
    /**@todo Implement this com.dexels.navajo.document.Message abstract method*/
  }
  public void setLazyTotal(int c) {
    /**@todo Implement this com.dexels.navajo.document.Message abstract method*/
  }
  public boolean contains(String name) {
    /**@todo Implement this com.dexels.navajo.document.Message abstract method*/
    throw new java.lang.UnsupportedOperationException("Method contains() not yet implemented.");
  }

}