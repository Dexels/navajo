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

public class MessageImpl
    extends BaseNode
    implements Message, TreeNode {
  private String myName = "";
  private String myType = "";
  private String myMode = "";
  private String myCondition = "";
  private int myIndex = -1;
  protected TreeMap propertyMap = new TreeMap();
  private ArrayList propertyList = new ArrayList();
  protected TreeMap messageMap = new TreeMap();
  protected List messageList = new ArrayList();
  private MessageImpl myParent = null;
  private MessageMappable myStringMap = null;
  private int startIndex = -1;
  private int endIndex = -1;
  private boolean isRootMessage = false;

//  private List myDefinitionList = null;
//  private Map myDefinitionMap = null;

  private MessageImpl definitionMessage = null;

  public MessageImpl(Navajo n) {
    super(n);
    myType = Message.MSG_TYPE_SIMPLE;
  }

  public MessageImpl(Navajo n, String name) {
    super(n);
    myName = name;
    myType = Message.MSG_TYPE_SIMPLE;
  }

//  public Property getPropertyAtRow(int row,String propertyName) {
//    if (myDefinitionList==null) {
//      return getMessage(row).getProperty(propertyName);
//    }
//    Message m = getValueMessage(row);
//    if (m==null) {
//      System.err.println("OH dear, no such row");
//      return null;
//    }
//    Property p = m.getProperty(propertyName);
//    Property defProp = (Property)myDefinitionMap.get(propertyName);
//
//    if (defProp==null && p==null) {
//      System.err.println("This is strange: No definitionprop and also no value prop.");
//      return null;
//    }
//
//    if (p==null) {
//      System.err.println("No value property");
//      p = defProp.copy(getRootDoc());
//      return p;
//    }
//    if (defProp!=null) {
//      Property pp = defProp.copy(getRootDoc());
//      pp.setValue(p.getValue());
//      return pp;
//    }
//    return null;
//  }
//

//  public Message buildMessageForRow(int index) {
//    System.err.println("Unefficient way of using messages of type table");
//    Message m = NavajoFactory.getInstance().createMessage(getRootDoc(),getName(),Message.MSG_TYPE_ARRAY_ELEMENT);
//
//    for (Iterator iter = myDefinitionList.iterator(); iter.hasNext(); ) {
//      Property current = (Property)iter.next();
//      m.addProperty(getPropertyAtRow(index,current.getName()));
//    }
//    return m;
//  }
//


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

  public final void setCondition(String condition) {
    myCondition = condition;
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

  public Message addMessage(Message m) {

    if (m == null) {
      //System.err.println("Ignoring null message. Not adding message");
      return null;
    }

    // Do not add messages with mode "ignore".
    if (m.getMode().endsWith(Message.MSG_MODE_IGNORE)) {
      //System.out.println("IGNORING ADDMESSAGE(), MODE = IGNORE!!!!!!!");
      return null;
    }
//    System.err.println("SETTING PARENT OF MESSAGE: "+m.getName()+" type: "+m.getType()+" I am: "+getName()+" my type: "+getType());
    m.setParent(this);
    if (this.getType().equals(Message.MSG_TYPE_ARRAY)) {
      return addMessage(m, false);
    }
    else {
      return addMessage(m, true);
    }
//    return addMessage(m, false);

  }

  public final Message addMessage(Message m, boolean overwrite) {
    String name = m.getName();

    if (getMessage(name) != null && !overwrite &&
        !this.getType().equals(Message.MSG_TYPE_ARRAY)) {
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

    return m;
  }

  public final void addMessage(Message m, int index) throws NavajoException {
   if (!getType().equals(Message.MSG_TYPE_ARRAY)) {
     throw new NavajoExceptionImpl("Can not add to with index to messages, if it is not an array message. Is that clear?");
   }
   messageList.add(index,m);
   messageMap.put(m.getName(), m);
   m.setIndex(index);
   m.setName(getName());
   m.setParent(this);
  }


  public ArrayList getAllMessages() {
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
      this.removeProperty( (Property) propertyMap.get(p.getName()));
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

      StringTokenizer tok = new StringTokenizer(regularExpression, Navajo.MESSAGE_SEPARATOR);
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
            if (pattern.matcher(name).matches()) {
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
  public void refreshExpression() throws NavajoException{
    ArrayList aa = getAllMessages();
    for (int i = 0; i < aa.size(); i++) {
      Message current = (Message)aa.get(i);
      current.refreshExpression();
    }
    ArrayList bb = getAllProperties();
    for (int j = 0; j < bb.size(); j++) {
      Property current = (Property)bb.get(j);
      current.refreshExpression();
    }

  }

  public Message getMessage(String name) {

    //System.err.println("in getMessage("+name+")");
    if (name.startsWith("../")) {
      return getParentMessage().getMessage(name.substring(3));
    }

    if (name.indexOf("/") >= 0) {
      return getByPath(name);
    }

    if (name.indexOf("@") >= 0) {
//      System.err.println("Found reference to Array element message: "+name);
      StringTokenizer arEl = new StringTokenizer(name, "@");
      String realName = arEl.nextToken();
      Message array = getMessage(realName);
      if (array != null) {
        if ( (array.getType() != null) &&
            (array.getType().equals(Message.MSG_TYPE_ARRAY))) {
          if (arEl.hasMoreTokens()) {
            String index = arEl.nextToken();
            int i = 0;
            try {
              i = Integer.parseInt(index);
            }
            catch (NumberFormatException ex) {
              ex.printStackTrace();
            }
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
  public ArrayList getMessages(String regularExpression) throws NavajoException {

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
      StringTokenizer tok = new StringTokenizer(regularExpression, Navajo.MESSAGE_SEPARATOR);
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
        String index = null;

        if (regularExpression.indexOf("@") != -1) {
            StringTokenizer arEl = new StringTokenizer(regularExpression, "@");
            regularExpression = arEl.nextToken();
            index = arEl.nextToken();

        }
        Pattern pattern = Pattern.compile(regularExpression);
        for (int i = 0; i < msgList.size(); i++) {
          Message m = (Message) msgList.get(i);
          String name = m.getName();
          if (m.getType().equals(Message.MSG_TYPE_ARRAY) && pattern.matcher(name).matches()) { // If message is array type add all children.
             if (index == null) {
               result.addAll(m.getAllMessages());
             } else {
               try {
                 result.add(m.getMessage(Integer.parseInt(index)));
               } catch (Exception pe) {
                 throw new NavajoExceptionImpl("Could not parse array index: " + index);
               }
             }
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
//    System.err.println("Getting property: "+s+" my path: "+getFullMessageName());

    if (s.startsWith("/")) {
      return getRootDoc().getProperty(s.substring(1));
    }

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

    //System.err.println("================= SERIALIZED MESSAGE: ======\n"+m.toString()+"\n==================================================");
    return m;
  }

  final void toXmlElement(XMLElement m) {
//    if (myDefinitionList!=null) {
//      XMLElement xx = new CaseSensitiveXMLElement();
//      xx.setName("definitions");
//      for (int i = 0; i < myDefinitionList.size(); i++) {
//        PropertyImpl current = (PropertyImpl)myDefinitionList.get(i);
//        XMLElement xelt = current.toXml(xx);
//        xx.addChild(xelt);
//      }
//      m.addChild(xx);
//    }

    if (definitionMessage!=null) {
      m.addChild(definitionMessage.toXml(m));
    }
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

    // pr
    // the 'normal' way
//    if (myDefinitionList==null) {
      Iterator props = propertyList.iterator();
      while (props.hasNext()) {
        PropertyImpl p = (PropertyImpl) props.next();
        m.addChild(p.toXml(m));
      }
//    } else {
//
//    }
  }

  private Message getValueMessage(int i) {
    return (Message) getAllMessages().get(i);

  }

  public Message getMessage(int i) {

    if (i >= getChildMessageCount()) {
//      System.err.println("Message index out of range");
    }
//    if (myDefinitionList!=null) {
//      return buildMessageForRow(i);
//    }
    if (getAllMessages().size() == 0) {
      return this;
    }
    return (Message) getAllMessages().get(i);
  }

  // Returns an array element
  public Message getMessage(String name, int index) {
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

  private int currentTotal = -1;
  public int getCurrentTotal() {
    return currentTotal;
  }

  public void setCurrentTotal(int aap) {
    currentTotal= aap;
  }


//  private void loadDefinitions(XMLElement xe) {
//    if (myDefinitionList==null) {
//      myDefinitionList = new ArrayList();
//      myDefinitionMap = new HashMap();
//    }
//    Vector v = xe.getChildren();
//    for (int i = 0; i < v.size(); i++) {
//      XMLElement current = (XMLElement)v.get(i);
//      PropertyImpl p = new PropertyImpl(getRootDoc(),"");
//      p.fromXml(current);
//      System.err.println("Adding definition... "+p.getName());
//      myDefinitionList.add(p);
//      myDefinitionMap.put(p.getName(),p);
//    }
//  }
  public void fromXml(XMLElement e) {
    fromXml(e,null);
  }

  public Property getPropertyDefinition(String name) {
    return definitionMessage.getProperty(name);
  }

  public void fromXml(XMLElement e, MessageImpl defParent) {
    for (int i = 0; i < e.countChildren(); i++) {
      XMLElement child = (XMLElement) e.getChildren().elementAt(i);
      String name = child.getName();
//      System.err.println("Array message found. children: "+e.countChildren());
//      if (name.equals("definitions")) {
//        loadDefinitions(child);
//      }
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
         if (defParent!=null) {
//           System.err.println("Defparent present");
          p.fromXml(child,defParent);
        } else {
//          if (myDefinitionList!=null) {
//            p.fromXml(child,this);
//          } else {
            p.fromXml(child);
//          }
        }
        this.addProperty(p);

      }
      if (name.equals("message")) {
        String childName = (String) child.getAttribute("name");
        String type = (String) child.getAttribute(MSG_TYPE);
        String index = (String) child.getAttribute(MSG_INDEX);
        String mode = (String) child.getAttribute(MSG_MODE);

        // Ok, now a simple implentation of the laziness check.
        MessageImpl msg = null;
//        if (false) {
        if (MSG_MODE_LAZY.equals(mode)) {
//          System.err.println("YES! A lazy message!");
//          System.err.println("CONSTRUCTING LAZY MESSAGE: \n");
//           System.err.println("\n\n");
          int lazyRemaining = Integer.parseInt( (String) child.getAttribute(
              Message.MSG_LAZY_REMAINING));
           int currentTotal = Integer.parseInt( (String) child.getAttribute(
              Message.MSG_ARRAY_SIZE));
          System.err.println("lazyRemaining = " + lazyRemaining +
                             ", current total = " + currentTotal + ", total = " +
                             child.getAttribute(Message.MSG_LAZY_COUNT));
          int windowSize = 100;
          if (lazyRemaining == 0) {
            windowSize = 0;
          }
          else if (lazyRemaining < currentTotal) {
            windowSize = lazyRemaining;
          }
          else {
            windowSize = currentTotal;
          }
          if (windowSize < 0) {
            windowSize = 0;
          }
          System.err.println("windowSize = " + windowSize);

          msg = (LazyMessageImpl) NavajoFactory.getInstance().createLazyMessage(
              myDocRoot, childName, windowSize);
          if (type != null) {
            msg.setType(type);
          }
          if (definitionMessage!=null) {
            msg.fromXml(child,this);
          } else {
            msg.fromXml(child);
          }
          msg.setCurrentTotal(currentTotal);
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
          if ( (index != null) && !index.equals("") && MSG_TYPE_DEFINITION.equals(getType())) {
            msg.setIndex(Integer.parseInt(index));
            msg.setType(MSG_TYPE_ARRAY_ELEMENT);
          }
          if (definitionMessage!=null) {
            msg.fromXml(child,this);
          } else {
            msg.fromXml(child);
          }

        }

        if (defParent!=null && defParent.getDefinitionMessage()!=null) {
          ArrayList myDefinitionList = defParent.getDefinitionMessage().getAllMessages();
          for (int j = 0; j < myDefinitionList.size(); j++) {
            PropertyImpl pq = (PropertyImpl)myDefinitionList.get(j);
            String pname = pq.getName();
            if (getProperty(pname)==null) {
              //System.err.println("\n\nCreating prop: "+pname+" ::: "+getIndex());
              PropertyImpl pi = (PropertyImpl)pq.copy(getRootDoc());
              addProperty(pi);
              //System.err.println("pi::::::::::: "+pi.toXml(null).toString());
            }
          }
        }

//            System.err.println("Defparent not present, definitionlist present");
        if (msg.getType().equals(MSG_TYPE_DEFINITION)) {
          definitionMessage = msg;
        } else {
        this.addMessage(msg);
        }
//        System.err.println("CONSTRUCTED THE FOLLOWING:");
      }
    }
  }

  public int getChildMessageCount() {
    return getAllMessages().size();
  }

  public final void addArrayMessage(Message m) {
    if (!MSG_TYPE_ARRAY.equals(getType())) {
      throw new RuntimeException("Adding array element to non-array message");
    }
    m.setName(getName());
    addMessage(m);
  }

  public final Message copy() throws NavajoException {
    Navajo empty = NavajoFactory.getInstance().createNavajo();
    Message result = copy(empty);
    empty.addMessage(result);
    return result;
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
      if (current == this) {
        throw new RuntimeException("CYCLIC Message copy found!");
      }

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
    if (m==null) {
      //System.err.println("==========================\nDeleting parent.... Bad idea\n\n\n");
      return;
    }
//    System.err.println("Setting parent to "+m.getName());
//    System.err.println("Full: "+m.getFullMessageName()+" type: "+m.getType());
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
      if (getParentMessage()==null) {
//        System.err.println("Looking for a parent that is not there!!");
//        System.err.println("My name: "+getName()+" my type: "+getType() );
//        System.err.println("Expression: "+pth);
        write(System.err);
      }
      return getParentMessage().getProperty(path.substring(3));
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
      if (myParent.getType().equals(Message.MSG_TYPE_ARRAY)) {
        return myParent.getPath()+"@"+getIndex();
      }
      return myParent.getPath() + "/" + getName();
    }
    else {
      return getName();
    }
  }

  public int getStartIndex() {
    return startIndex;
  }

  public int getEndIndex() {
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
    return getAllProperties().size() + getAllMessages().size();
  }

  public TreeNode getChildAt(int childIndex) {
    if (childIndex >= getAllProperties().size()) {
      return (TreeNode) getAllMessages().get(childIndex -
                                             getAllProperties().size());
    }
    else {
      return (TreeNode) getAllProperties().get(childIndex);
    }

  }

  public Enumeration children() {
    Vector v = new Vector(getAllProperties());
    v.addAll(getAllMessages());
    return v.elements();
  }

  public int getIndex(TreeNode t) {
    for (int i = 0; i < getAllProperties().size(); i++) {
      if (getAllProperties().get(i) == t) {
        return i;
      }
    }
    for (int i = 0; i < getAllMessages().size(); i++) {
      if (getAllMessages().get(i) == t) {
        return i;
      }
    }
    return 0;
  }

  public boolean isLeaf() {
    return messageList.size() == 0;
  }

  public boolean getAllowsChildren() {
    return true;
  }

  public TreeNode getParent() {
    return (TreeNode) getParentMessage();
  }

  public static void main(String[] args) throws Exception {
    System.setProperty("com.dexels.navajo.DocumentImplementation",
                       "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");

    Navajo n = NavajoFactory.getInstance().createNavajo();
    Message array = NavajoFactory.getInstance().createMessage(n, "Array", Message.MSG_TYPE_ARRAY);
    for (int i = 0; i < 5; i++) {
      Message sub = NavajoFactory.getInstance().createMessage(n, "Array");
      array.addMessage(sub);
      Property p = NavajoFactory.getInstance().createProperty(n, "Apenoot", "string", "i="+i, 10, "", "in");
      sub.addProperty(p);
    }
    n.addMessage(array);
    //n.write(System.err);
//    System.err.println("BROEP..");
    ArrayList p = n.getProperties("/Arr[aA][yY]@0/Apenoot");
    //aap.write(System.err);
    System.err.println("p = " + ((Property) p.get(0)).getValue());
  }

  public boolean isEqual(Message o) {
    return isEqual(o, "");
  }

  public boolean isEqual(Message o, String skipProperties) {

    //System.err.println("in Message.isEqual(), my name is " + getName() + ", other is " + getName() + ", skipProperties = " + skipProperties);
    Message other = (Message) o;
    if (!other.getName().equals(this.getName())) {
      return false;
    }
    // Check sub message structure.
    ArrayList allOther = other.getAllMessages();
    ArrayList allMe = this.getAllMessages();
    //System.err.println("my msg size is " + allMe.size() + ", other msg size is " + allOther.size());
    if (allOther.size() != allMe.size()) {
      return false;
    }
    for (int i = 0; i < allOther.size(); i++) {
      Message otherMsg = (Message) allOther.get(i);
      boolean match = false;
      for (int j = 0; j < allMe.size(); j++) {
        Message myMsg = (Message) allMe.get(j);
        if (myMsg.isEqual(otherMsg, skipProperties)) {
          match = true;
          j = allMe.size() + 1;
        }
      }
      if (!match) {
        return false;
      }
    }
    // Check property structure.
    ArrayList allOtherProps = other.getAllProperties();
    ArrayList allMyProps = this.getAllProperties();
    if (allOtherProps.size() != allMyProps.size()) {
      return false;
    }
    for (int i = 0; i < allOtherProps.size(); i++) {
      Property otherProp = (Property) allOtherProps.get(i);
      boolean match = false;
      // Check whether property name exists in skipProperties list.
      if (skipProperties.indexOf(otherProp.getName()) != -1) {
        match = true;
      }
      else {
        for (int j = 0; j < allMyProps.size(); j++) {
          Property myProp = (Property) allMyProps.get(j);
          if (myProp.isEqual(otherProp)) {
            match = true;
            j = allMyProps.size() + 1;
          }
        }
      }
      //System.err.println("Isequal property " + otherProp.getName() + ": " + match);
      if (!match) {
        return false;
      }
    }
    return true;
  }

  private Message createEmptyMessage() {
    return null;
  }

  public void addMessage(int index) {
    if (getType().equals(Message.MSG_TYPE_ARRAY)) {
      throw new IllegalStateException("Can only add empty messages to arraymessages.");
    }
    if (definitionMessage==null) {
      throw new IllegalStateException("Can only add empty messages when definitions present.");
    }
    Message newChild = createEmptyMessage();
    try {
      addMessage(newChild, index);
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }
   }


  /**
   * Add empty message at the end
   */
  public void addMessage() {
    addMessage(getArraySize());
  }
  public Message getDefinitionMessage() {
    return definitionMessage;
  }

}
