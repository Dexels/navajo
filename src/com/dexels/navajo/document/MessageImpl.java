package com.dexels.navajo.document;
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
import nanoxml.*;
//import com.dexels.sportlink.client.swing.*;
import com.dexels.navajo.nanoclient.*;

public class MessageImpl extends BaseNode implements Message {
  private String myName = "";
  private String myType = "";
  private String myMode = "";
  private int myIndex = -1;
  protected Map propertyMap = new TreeMap();
  protected Map messageMap = new TreeMap();
  protected List messageList = new ArrayList();
  private Message myParent = null;
  private MessageMappable myStringMap = null;
  private int startIndex = -1;
  private int endIndex = -1;

  public MessageImpl(Navajo n) {
    super(n);
    myType = Message.MSG_TYPE_NORMAL;
  }

  public MessageImpl(Navajo n, String name) {
    super(n);
    myName = name;
    myType = Message.MSG_TYPE_NORMAL;
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

  public void addMessage(Message m) {
    if (m==null) {
      System.err.println("Ignoring null message. Not adding message");
      return;
    }

    messageMap.put(m.getName(),m);
    if (getType().equals(MSG_TYPE_ARRAY)) {
      m.setIndex(messageList.size());
    }
    messageList.add(m);
    m.setParent(this);
  }
  public ArrayList getAllMessages() {
//    return new ArrayList(messageMap.values());
    return new ArrayList(messageList);
  }

  public void addProperty(Property p) {
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
    if ((getType()!=null) && (!"".equals(getType())) && (!Message.MSG_TYPE_NORMAL.equals(getType())) ) {
      m.setAttribute("type",getType());
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
      Message msg = getMessage(i);
      if (msg!=null) {
        m.addChild(msg.toXml(m));
      }
    }


    Iterator props = propertyMap.values().iterator();
    while (props.hasNext()) {
      Property p = (Property)props.next();
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
        Property p = Navajo.createProperty(myDocRoot,(String)child.getAttribute("name"));
        this.addProperty(p);
        p.fromXml(child);
      }
      if (name.equals("message")) {
        String childName = (String)child.getAttribute("name");
        String type = (String) child.getAttribute("type");
        String index = (String) child.getAttribute("index");
        String mode = (String) child.getAttribute("mode");
        if ((index != null) && !index.equals("")) {

        /**
         * TEMPORARY SOLUTION: APPEND INDEX ATTRIBUTE TO MESSAGE NAME TO CREATE UNIQUE MESSAGE!!!
         */
//          childName += "("+ index + ")";
        /**
         * END TEMPORARY SOLUTION
         */
        }

        // Ok, now a simple implentation of the laziness check.
        Message msg;
        if (MSG_MODE_LAZY.equals(mode)) {
          System.err.println("YES! A lazy message!");
          msg = Navajo.createLazyMessage(myDocRoot,childName);
        } else {
          msg = Navajo.createMessage(myDocRoot,childName);
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
    Message cp = Navajo.createMessage(n,getName());
    cp.setRootDoc(n);
    ArrayList myMsg = getAllMessages();
    cp.setEndIndex(getEndIndex());
    cp.setStartIndex(getStartIndex());
    cp.setIndex(getIndex());
    cp.setMode(getMode());
    cp.setType(getType());

    for (int i = 0; i < myMsg.size(); i++) {
      Message current = (Message)myMsg.get(i);
      Message cc = current.copy(n);
      cp.addMessage(cc);
    }
    ArrayList myProp = getAllProperties();
    for (int i = 0; i < myProp.size(); i++) {
      Property current = (Property)myProp.get(i);
      Property cc = current.copy(n);
      cp.addProperty(cc);
    }
    return cp;
  }

  public void prune() {
    ArrayList myMsg = getAllMessages();
    for (int i = 0; i < myMsg.size(); i++) {
      Message current = (Message)myMsg.get(i);
      current.prune();
    }
    ArrayList myProp = getAllProperties();
    for (int i = 0; i < myProp.size(); i++) {
      Property current = (Property)myProp.get(i);
      current.prune();
    }
  }

  public void setMessageMap(MessageMappable m) {
    myStringMap = m;
    ArrayList myMsg = getAllMessages();
    for (int i = 0; i < myMsg.size(); i++) {
      Message current = (Message)myMsg.get(i);
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
    myParent = m;
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
        return m.getByPath(path.substring(slash+1));
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
      Message ms = getMessage(msgname);
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
    m.addChild(h.toXml(null));
    for (int i = 0; i < getChildMessageCount(); i++) {
      Message current = getMessage(i);
      m.addChild(current.toXml(m));
    }
//
//    Iterator msgs = messageMap.values().iterator();
//    while (msgs.hasNext()) {
//      Message msg = (Message)msgs.next();
//    }
    return m;
  }
}