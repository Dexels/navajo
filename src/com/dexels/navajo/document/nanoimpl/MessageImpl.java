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
//import com.dexels.navajo.tipi.tipixml.*;
//import com.dexels.sportlink.client.swing.*;
//import com.dexels.navajo.nanoclient.*;
import com.dexels.navajo.document.*;
import java.util.regex.*;
import java.io.*;
import javax.swing.tree.*;

public  class MessageImpl
    extends BaseNode
    implements Message,TreeNode {
  private String myName = "";
  private String myType = "";
  private String myMode = "";
  private int myIndex = -1;
  protected Map propertyMap = new TreeMap();
  private ArrayList propertyList = new ArrayList();
  protected Map messageMap = new TreeMap();
  protected List messageList = new ArrayList();
  private MessageImpl myParent = null;
  private MessageMappable myStringMap = null;
  private int startIndex = -1;
  private int endIndex = -1;
  private boolean isRootMessage = false;

  public MessageImpl(Navajo n) {
    super(n);
    myType = Message.MSG_TYPE_SIMPLE;
  }

  public MessageImpl(Navajo n, String name) {
    super(n);
    myName = name;
    myType = Message.MSG_TYPE_SIMPLE;
  }

  public final void setRootMessage(boolean b) {
    isRootMessage = b;
  }

  public final String getType() {
    return myType;
  }

  public final void setType(String type) {
    myType = type;
  }

  public final String getName() {
    return myName;
  }

  public final void setName(String name) {
    myName = name;
  }

  public final void setMode(String mode) {
    myMode = mode;
  }

  public final String getMode() {
    return myMode;
  }

  public final void clearAllSelections() throws NavajoException {
    for (int i = 0; i < propertyList.size(); i++) {
      Property p = (Property) propertyList.get(i);
      p.clearSelections();
    }
    for (int i = 0; i < messageList.size(); i++) {
      MessageImpl p = (MessageImpl) messageList.get(i);
      p.clearAllSelections();
    }

  }

  public  Message addMessage(Message m) {
    if (m == null) {
      //System.err.println("Ignoring null message. Not adding message");
      return null;
    }
    // Do not add messages with mode "ignore".
    if (m.getMode().endsWith(Message.MSG_MODE_IGNORE)) {
      //System.out.println("IGNORING ADDMESSAGE(), MODE = IGNORE!!!!!!!");
      return null;
    }

    if (m == null) {
      return null;
    }

    if (this.getType().equals(Message.MSG_TYPE_ARRAY)) {
      return addMessage(m, false);
    }
    else {
      return addMessage(m, true);
    }

  }

  public  ArrayList getAllMessages() {
    return new ArrayList(messageList);
  }

  public final void addProperty(Property q) {

    PropertyImpl p = (PropertyImpl) q;
    if (propertyMap.get(p.getName()) == null) {
      propertyList.add(q);
      propertyMap.put(p.getName(), p);
      p.setParent(this);
    }
    else {
      this.removeProperty((Property) propertyMap.get(p.getName()));
      addProperty(q);
    }
  }

  public final ArrayList getAllProperties() {
    return propertyList;
  }

 public final ArrayList getProperties(String regularExpression) throws
     NavajoException {

   if (regularExpression.startsWith(Navajo.PARENT_MESSAGE +
                                    Navajo.MESSAGE_SEPARATOR)) {
     regularExpression = regularExpression.substring( (Navajo.PARENT_MESSAGE +
         Navajo.MESSAGE_SEPARATOR).length());
     return getParentMessage().getProperties(regularExpression);
   }
   else
   if (regularExpression.startsWith(Navajo.MESSAGE_SEPARATOR)) { // We have an absolute offset
//         Navajo d = new NavajoImpl(this.ref.getOwnerDocument());
     Navajo d = getRootDoc();
     return d.getProperties(regularExpression.substring(1));
   }
   else {
     ArrayList props = new ArrayList();
     Property prop = null;
     ArrayList messages = null;
     ArrayList sub = null;
     ArrayList sub2 = null;
     String property = null;
     Message message = null;

     StringTokenizer tok = new StringTokenizer(regularExpression,
                                               Navajo.MESSAGE_SEPARATOR);
     String messageList = "";

     int count = tok.countTokens();

     for (int i = 0; i < count - 1; i++) {
       property = tok.nextToken();
       messageList += property;
       if ( (i + 1) < count - 1) {
         messageList += Navajo.MESSAGE_SEPARATOR;
       }
     }
     String realProperty = tok.nextToken();

     if (!messageList.equals("")) {
       messages = this.getMessages(messageList);
     }
     else {
       messages = new ArrayList();
       messages.add(this);
     }

     Pattern pattern = Pattern.compile(realProperty);
     for (int i = 0; i < messages.size(); i++) {
       message = (Message) messages.get(i);
       ArrayList allProps = message.getAllProperties();
       try {
         for (int j = 0; j < allProps.size(); j++) {
           String name = ( (Property) allProps.get(j)).getName();
           if ( pattern.matcher(name).matches() ) {
             props.add(allProps.get(j));
           }
         }
       }
       catch (Exception re) {
         throw new NavajoExceptionImpl(re.getMessage());
       }
     }
     return props;
   }
 }

  public  Message getMessage(String name) {

    //System.err.println("in getMessage("+name+")");
    if (name.startsWith("../")) {
      return getParentMessage().getMessage(name.substring(3));
    }

    if (name.indexOf("/") >= 0) {
      return getByPath(name);
    }

    if (name.indexOf("@") >= 0) {
      StringTokenizer arEl = new StringTokenizer(name, "@");
      String realName = arEl.nextToken();
      Message array = getMessage(realName);
      if (array != null) {
        if ( (array.getType() != null) && (array.getType().equals(Message.MSG_TYPE_ARRAY))) {
          if (arEl.hasMoreTokens()) {
            String index = arEl.nextToken();
            //System.err.println("index = " + index);
            int i = 0;
            try {
              i = Integer.parseInt(index);
            }
            catch (NumberFormatException ex) {
              ex.printStackTrace();
            }
            //System.err.println("i = " + i);
            return array.getMessage(i);
          }
        }
      }
    }

    return (Message) messageMap.get(name);
  }

//  public ArrayList getMessages(String regexp) {
//    Iterator it = messageMap.values().iterator();
//    ArrayList selected = new ArrayList();
//    while (it.hasNext()) {
//      Message m = (Message) it.next();
//      if (compliesWith(m, regexp)) {
//        selected.add(m);
//      }
//    }
//    return selected;
//  }
//
//  private ArrayList getMessagesByPath(String regExpPath) {
//
//  }
  /**
   * Return all messages that match a given regular expression. Regular expression may include sub-messages and even
   * absolute message references starting at the root level.
   */
  public  ArrayList getMessages(String regularExpression) throws NavajoException {

    ArrayList messages = new ArrayList();
    ArrayList sub = null;
    ArrayList sub2 = null;

    if (regularExpression.startsWith(Navajo.PARENT_MESSAGE +
                                     Navajo.MESSAGE_SEPARATOR)) {
      regularExpression = regularExpression.substring( (Navajo.PARENT_MESSAGE +
          Navajo.MESSAGE_SEPARATOR).length());
      return getParentMessage().getMessages(regularExpression);
    }
    else
    if (regularExpression.startsWith(Navajo.MESSAGE_SEPARATOR)) { // We have an absolute offset

      return myDocRoot.getMessages(regularExpression);
    }
    else // Contains submessages.
    if (regularExpression.indexOf(Navajo.MESSAGE_SEPARATOR) != -1) { // contains a path, descent it first
      StringTokenizer tok = new StringTokenizer(regularExpression,
                                                Navajo.MESSAGE_SEPARATOR);
      Message m = null;

      while (tok.hasMoreElements()) {
        String msgName = tok.nextToken();

        if (sub == null) { // First message in path.
          sub = getMessages(msgName);
        }
        else { // Subsequent submessages in path.
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
    }
    else {
      ArrayList msgList = getAllMessages();
      ArrayList result = new ArrayList();
      try {
        Pattern pattern = Pattern.compile(regularExpression);
        for (int i = 0; i < msgList.size(); i++) {
          Message m = (Message) msgList.get(i);
          String name = m.getName();
          if (m.getType().equals(Message.MSG_TYPE_ARRAY) && pattern.matcher(name).matches()) { // If message is array type add all children.
            result.addAll(m.getAllMessages());
          }
          else {
            if (pattern.matcher(name).matches()) {
              result.add(msgList.get(i));
            }
          }
        }
      }
      catch (Exception re) {
        throw new NavajoExceptionImpl(re.getMessage());
      }
      return result;
    }
  }

  protected final boolean compliesWith(Message m, String expression) {
    return m.getName().startsWith(expression);
  }

  protected final boolean compliesWith(Property p, String expression) {
    return p.getName().startsWith(expression);
  }

  public final Property getProperty(String s) {
//    return (Property) propertyMap.get(s);
    if (s.startsWith("/"))
      return getRootDoc().getProperty(s.substring(1));

    return getPropertyByPath(s);
  }

  public final int getIndex() {
    return myIndex;
  }

  public final void setIndex(int index) {
    myType = Message.MSG_TYPE_ARRAY_ELEMENT;
    myIndex = index;
  }

  public final XMLElement generateTml(Header h, XMLElement m) {

//    for (int i = 0; i < getChildMessageCount(); i++) {
//      MessageImpl current = (MessageImpl) getMessage(i);
//      m.addChild(current.toXml(m));
//    }
    for (int i = 0; i < getChildMessageCount(); i++) {
      MessageImpl msg = (MessageImpl) getMessage(i);
      if (msg != null) {
        m.addChild(msg.toXml(m));
      }
//      System.err.println("CREATED DOC: "+m.toString());
    }
//    toXmlElement(m);
//    m.setName("hooka");
    return m;
  }

  public XMLElement toXml(XMLElement parent) {
    XMLElement m = new CaseSensitiveXMLElement();
    m.setAttribute("name", myName);
    toXmlElement(m);
    m.setName("message");
    return m;
  }

  final void toXmlElement(XMLElement m) {
    if ( (getType() != null) && (!"".equals(getType())) &&
        (!Message.MSG_TYPE_SIMPLE.equals(getType()))) {
      m.setAttribute(MSG_TYPE, getType());
    }
    if (getType().equals(MSG_TYPE_ARRAY_ELEMENT)) {
      m.setAttribute("index", "" + getIndex());
    }
    if ( (getMode() != null) && (!"".equals(getMode()))) {
      m.setAttribute("mode", "" + getMode());
    }

    if (getStartIndex() >= 0) {
      m.setAttribute("startindex", "" + getStartIndex());
    }
    if (getEndIndex() >= 0) {
      m.setAttribute("endindex", "" + getEndIndex());
    }

    for (int i = 0; i < getChildMessageCount(); i++) {
      MessageImpl msg = (MessageImpl) getMessage(i);
      if (msg != null) {
        m.addChild(msg.toXml(m));
      }
//      System.err.println("CREATED DOC: "+m.toString());
    }

    Iterator props = propertyMap.values().iterator();
    while (props.hasNext()) {
      PropertyImpl p = (PropertyImpl) props.next();
      m.addChild(p.toXml(m));
    }
  }

  public  Message getMessage(int i) {
    if (i >= getChildMessageCount()) {
      System.err.println("Message index out of range");
    }

    return (Message) getAllMessages().get(i);
  }

  // Returns an array element
  public  Message getMessage(String name, int index) {
    Message m = getMessage(name);
    if (m == null) {
//      System.err.println("No such message: " + name);
//      Thread.dumpStack();
      return null;
    }
    if (!m.getType().equals(Message.MSG_TYPE_ARRAY)) {
      System.err.println(
          "Found a non array message, when querying for an array element");
      return null;
    }
    return m.getMessage(index);
  }

  public final void removeChildMessage(Message msg) {
    messageList.remove(msg);
    messageMap.remove(msg.getName());
  }

  public  void fromXml(XMLElement e) {
    for (int i = 0; i < e.countChildren(); i++) {
      XMLElement child = (XMLElement) e.getChildren().elementAt(i);
      String name = child.getName();
//      System.err.println("Array message found. children: "+e.countChildren());
      if (name.equals("property")) {
        /** @todo Beware: Will things be affected? */
        PropertyImpl p = null;
        try {
          p = (PropertyImpl) NavajoFactory.getInstance().createProperty(
              myDocRoot, (String) child.getAttribute("name"), "", "", 0, "", "");
        }
        catch (NavajoException ex) {
          ex.printStackTrace();
        }
        this.addProperty(p);
        p.fromXml(child);
      }
      if (name.equals("message")) {
        String childName = (String) child.getAttribute("name");
        String type = (String) child.getAttribute(MSG_TYPE);
        String index = (String) child.getAttribute(MSG_INDEX);
        String mode = (String) child.getAttribute(MSG_MODE);
        if ( (index != null) && !index.equals("")) {

        }

        // Ok, now a simple implentation of the laziness check.
        MessageImpl msg = null;
//        if (false) {
         if (MSG_MODE_LAZY.equals(mode)) {
//          System.err.println("YES! A lazy message!");
//          System.err.println("CONSTRUCTING LAZY MESSAGE: \n");
//           System.err.println("\n\n");
          msg = (LazyMessageImpl)NavajoFactory.getInstance().createLazyMessage(myDocRoot,childName);
          if (type != null) {
            msg.setType(type);
          }
          msg.fromXml(child);
          if ( (index != null) && !index.equals("")) {
            msg.setIndex(Integer.parseInt(index));
            msg.setType(MSG_TYPE_ARRAY_ELEMENT);
          }
        }
        else {
          msg = (MessageImpl) NavajoFactory.getInstance().createMessage(
              myDocRoot, childName);
          if (type != null) {
            msg.setType(type);
          }
          if ( (index != null) && !index.equals("")) {
            msg.setIndex(Integer.parseInt(index));
            msg.setType(MSG_TYPE_ARRAY_ELEMENT);
          }
          msg.fromXml(child);
        }
        this.addMessage(msg);
//        System.err.println("CONSTRUCTED THE FOLLOWING:");
      }
    }
  }

  public  int getChildMessageCount() {
    return getAllMessages().size();
  }

  public final void addArrayMessage(Message m) {
    if (!MSG_TYPE_ARRAY.equals(getType())) {
      throw new RuntimeException("Adding array element to non-array message");
    }
    m.setName(getName());
    addMessage(m);
  }

  public final Message copy(Navajo n) {
    MessageImpl cp = (MessageImpl) NavajoFactory.getInstance().createMessage(n,
        getName());
    cp.setRootDoc(n);
    ArrayList myMsg = getAllMessages();
    cp.setEndIndex(getEndIndex());
    cp.setStartIndex(getStartIndex());
    cp.setIndex(getIndex());
    cp.setMode(getMode());
    cp.setType(getType());

    for (int i = 0; i < myMsg.size(); i++) {
      MessageImpl current = (MessageImpl) myMsg.get(i);
      Message cc = current.copy(n);
      cp.addMessage(cc);
    }
    ArrayList myProp = getAllProperties();
    for (int i = 0; i < myProp.size(); i++) {
      PropertyImpl current = (PropertyImpl) myProp.get(i);
      Property cc = current.copy(n);
      cp.addProperty(cc);
    }
    return cp;
  }

  public final void prune() {
    ArrayList myMsg = getAllMessages();
    for (int i = 0; i < myMsg.size(); i++) {
      MessageImpl current = (MessageImpl) myMsg.get(i);
      current.prune();
    }
    ArrayList myProp = getAllProperties();
    for (int i = 0; i < myProp.size(); i++) {
      PropertyImpl current = (PropertyImpl) myProp.get(i);
      current.prune();
    }
  }

  public final void setMessageMap(MessageMappable m) {
    myStringMap = m;
    ArrayList myMsg = getAllMessages();
    for (int i = 0; i < myMsg.size(); i++) {
      MessageImpl current = (MessageImpl) myMsg.get(i);
      if (current != null) {
        current.setMessageMap(m);
      }
    }
  }

  public final MessageMappable getMessageMap() {
    return myStringMap;
  }

  public final String toString() {
    //return super.toString();
    if (myStringMap != null) {
      return myStringMap.getMessageLabel(this);
    }
    return getName();
  }

  public final void setParent(Message m) {
    myParent = (MessageImpl) m;
  }

//  public final Message getParent() {
//    return myParent;
//  }

  public Message getByPath(String path) {
    /** @todo ARRAY SUPPORT */
    if (path.startsWith("../")) {
      Message m = getParentMessage().getMessage(path.substring(3));
    }

    if (path.startsWith("/")) {
      path = path.substring(1);
    }

    int slash = path.indexOf("/");
    if (slash < 0) {
      return getMessage(path);
    }
    else {
//      System.err.println("Index: " + slash + " of: " + path);
      String messagename = path.substring(0, slash);
//      System.err.println("Sumbessage: " + messagename);
      Message m = getMessage(messagename);
      if (m != null) {
        return m.getMessage(path.substring(slash + 1));
      }
      else {
        return null;
      }
    }
  }

  public final Property getPropertyByPath(String pth) {
    /** @todo ARRAY SUPPORT */
//    System.err.println("PARSING PATH: "+pth);
    String path = null;
    if (pth.startsWith("/")) {
//      System.err.println("PATH STARTED WITH SLASH. STRIPPING /");
      path = pth.substring(1);
    }
    else {
      path = pth;
    }
    if (path.startsWith("..")) {
      return getParentMessage().getProperty(path.substring(2));
    }

    int slash = path.indexOf("/");
    if (slash < 0) {
//      System.err.println("No slashes left. Getting property: "+path);
      return (Property) propertyMap.get(path);
//      return getProperty(path);
    }
    else {
      String msgname = path.substring(0, slash);
      String propname = path.substring(slash, path.length());
//      System.err.println("Getting message: "+msgname);
      MessageImpl ms = (MessageImpl) getMessage(msgname);
      if (ms != null) {
        return ms.getPropertyByPath(propname);
      }
      else {
//        Thread.dumpStack();
      }

//      System.err.println("SubProperty: "+propname);
      return null;
    }
  }

  public final String getPath() {
    if (myParent != null) {
      return myParent.getPath() + "/" + getName();
    }
    else {
      return getName();
    }
  }

  public  int getStartIndex() {
    return startIndex;
  }

  public  int getEndIndex() {
    return endIndex;
  }

  public final void setStartIndex(int i) {
    startIndex = i;
  }

  public void setEndIndex(int i) {
    endIndex = i;
  }

  public final Message getParentMessage() {
    return myParent;
  }

  public final Message addElement(Message m) {
    if (!getType().equals(Message.MSG_TYPE_ARRAY)) {
      throw new IllegalArgumentException(
          "Can not add element to non-array type message!");
    }
    m.setIndex(getArraySize());
    addMessage(m);
    return m;
  }

  public final Message addMessage(Message m, boolean overwrite) {
    String name = m.getName();

    if (getMessage(name) != null && !overwrite && !this.getType().equals(Message.MSG_TYPE_ARRAY)) {
        return getMessage(name);
    }

    if (getMessage(name) != null && overwrite) {
        removeChildMessage(getMessage(m.getName()));
    }
      /**
      * If message is array type, insert new message as "element".
      */
      messageMap.put(m.getName(), m);
      if (getType().equals(MSG_TYPE_ARRAY)) {
        m.setIndex(messageList.size());
        m.setName(getName());
      }
      messageList.add(m);
      ( (MessageImpl) m).setParent(this);


    return m;
  }

  public int getArraySize() {
    return messageList.size();
  }

  public void setArraySize(int i) {
    throw new UnsupportedOperationException(
        "Dont know what this method should do.");
  }

  public final boolean isArrayMessage() {
    return MSG_TYPE_ARRAY.equals(getType());
  }

  public final String getFullMessageName() {
    return getPath();
  }

  public final Property getPathProperty(String path) {
    return getPropertyByPath(path);
  }

  public final Object getRef() {
    return toXml(null);
  }

  public final void removeMessage(Message msg) {
    removeChildMessage(msg);
  }

  public final void removeMessage(String msg) {
    removeChildMessage(getMessage(msg));
  }

  public final void removeProperty(Property p) {
    propertyList.remove(p);
    propertyMap.remove(p.getName());
        /**@todo Implement this com.dexels.navajo.document.Message abstract method*/
  }

  public final void setLazyRemaining(int c) {
        /**@todo Implement this com.dexels.navajo.document.Message abstract method*/
  }

  public final void setLazyTotal(int c) {
        /**@todo Implement this com.dexels.navajo.document.Message abstract method*/
  }

  public final boolean contains(String name) {
    boolean b = getMessage(name) != null;
    if (!b) {
      return getProperty(name) != null;
    }
    return b;
  }

  public final void write(java.io.Writer writer) {
    try {
      toXml(null).write(writer);
      writer.flush();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }

  }

  public final void write(java.io.OutputStream o) {
     try {
      OutputStreamWriter w = new OutputStreamWriter(o);
      toXml(null).write(w);
      w.flush();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }
  public int getChildCount() {
    return getAllProperties().size()+getAllMessages().size();
  }
  public TreeNode getChildAt(int childIndex) {
    if (childIndex>=getAllProperties().size()) {
      return (TreeNode)getAllMessages().get(childIndex-getAllProperties().size());
    } else {
      return (TreeNode)getAllProperties().get(childIndex);
    }

  }
  public Enumeration children() {
    Vector v = new Vector(getAllProperties());
    v.addAll(getAllMessages());
    return v.elements();
  }
  public int getIndex(TreeNode t) {
    for (int i = 0; i < getAllProperties().size(); i++) {
      if (getAllProperties().get(i)==t) {
        return i;
      }
    }
    for (int i = 0; i < getAllMessages().size(); i++) {
      if (getAllMessages().get(i)==t) {
        return i;
      }
    }
    return 0;
  }

  public boolean isLeaf() {
    return messageList.size()==0;
  }

  public boolean getAllowsChildren() {
    return true;
  }

  public TreeNode getParent() {
    return (TreeNode)getParentMessage();
   }

   public static void main (String [] args) throws Exception {
     System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");

     Navajo n = NavajoFactory.getInstance().createNavajo();
     Message array = NavajoFactory.getInstance().createMessage(n, "Array", Message.MSG_TYPE_ARRAY);
     for (int i = 0; i < 5; i++) {
       Message sub = NavajoFactory.getInstance().createMessage(n, "Array");
       array.addMessage(sub);
       Property p = NavajoFactory.getInstance().createProperty(n, "Apenoot", "string", "5465Aa", 10, "", "in");
       sub.addProperty(p);
     }
     n.addMessage(array);
     //n.write(System.err);
     System.err.println("BROEP..");
     ArrayList p = n.getProperties("/ArrayF/Apenoot");
     //aap.write(System.err);
     System.err.println("p = " + p);
   }
}
