package com.dexels.navajo.nanodocument;
import nanoxml.*;
import java.util.*;
/**
 * <p>Title: ShellApplet</p>
 * <p>Description: </p>
 * <p>Part of the Navajo mini client, based on the NanoXML parser</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels </p>
 * @author Frank Lyaruu
 * @version 1.0
 */


public class Navajo {

  private Message rootMessage = null;
  private Header myHeader = null;
//  private String myName="";
//  private String myPassword="";
//  private String myService="";
  private ArrayList myMethods = new ArrayList();
  private boolean doAppendMethods = false;
  private int expiration = -1;
  private String myLazyMessagePath = "";

  public Navajo() {
    rootMessage = new RootMessage(this);
    myHeader = createHeader(this);
  }

  public void setRootMessage(Message r) {
    rootMessage = r;
  }

  public void setExpiration(int i) {
    expiration = i;
    myHeader.setExpiration(i);
  }

  public Navajo copy() {
    Navajo n = new Navajo();
    n.setRootMessage(getRootMessage().copy(n));
//    System.err.println("COPIED NAVAJO: "+n.toXml());
    for (int i = 0; i < myMethods.size(); i++) {
      Method m = (Method)myMethods.get(i);
      n.addMethod(m.copy(this));
    }

    return n;
  }
  public void addMethod(Method m) {
    myMethods.add(m);
  }
 public Message getMessage(String name)  {
    return rootMessage.getMessage(name);
  }

  public Message getMessage(String name, int index) {
    return rootMessage.getMessage(name,index);
  }

  public Message getRootMessage() {
    return rootMessage;
  }

  public void addMessage(Message m) {
    rootMessage.addMessage(m);
  }

  public void setIdentification(String username, String password, String service) {
    myHeader.setIdentification(username,password,service);
  }

//  public void setService(String service) {
//    myService = service;
//  }

  public void setMethod() {
//<method name="navajo_logon_send"> <required message="identification"/> <required message="services"/> </method>
//    Message m = createMessage(this,"methods");
//
//    addMessage(m);
  }

  public void addLazyMessage(String path, int startIndex, int endIndex) {
    myHeader.addLazyMessage(path, startIndex, endIndex);
  }

  public LazyMessagePath getLazyMessagePath(String path) {
    return myHeader.getLazyMessagePath(path);
  }

  public ArrayList getAllMessages() {
    return rootMessage.getAllMessages();
  }

  public ArrayList getMessages(String regexp) {
    return rootMessage.getMessages(regexp);
  }

  public Message getByPath(String path) {
    return rootMessage.getByPath(path);
  }

  public Message create(Navajo n, String name) {
    return new MessageImpl(n,name);
  }

  public static Property createProperty(Navajo n,String name, String type, Object value, int i, String desc, String direction) {
    return new PropertyImpl(n,name,type,value,i,desc,direction);
  }

  public static Property createProperty(Navajo n, String name, String cardinality, String desc, String direction) {
    return new PropertyImpl(n,name,cardinality,desc,direction);
  }

  public static Property createProperty(Navajo n, String name) {
    return new PropertyImpl(n,name);
  }

  public static Message createMessage(Navajo n, String name) {
    return new MessageImpl(n,name);
  }

  public static Message createArrayMessage(Navajo n, String name) {
    Message m = new MessageImpl(n,name);
    m.setType(Message.MSG_TYPE_ARRAY);
    return m;
  }

  public static Message createArrayElementMessage(Navajo n, String name) {
    Message m = new MessageImpl(n,name);
    m.setType(Message.MSG_TYPE_ARRAY_ELEMENT);
    return m;
  }

  public static LazyMessage createLazyMessage(Navajo n, String msgName) {
    return ((LazyMessage)(new LazyMessageImpl(n,msgName)));
  }

  public static Selection createSelection(Navajo n, String name, String value, boolean isSelected) {
    return new SelectionImpl(n,name, value, isSelected);
  }
  public static Selection createSelection(Navajo n) {
    return new SelectionImpl(n);
  }

  public static Header createHeader(Navajo n, String username, String password, String service) {
    return new Header(n, username,password, service);
  }

  public static Header createHeader(Navajo n) {
    return new Header(n);
  }

//<method name="navajo_logon_send"> <required message="identification"/> <required message="services"/> </method>

  public static Method createMethod(Navajo n, String name) {
    return new MethodImpl(n,name);
  }

  public Header getHeader() {
    return myHeader;
  }

  public XMLElement toXml() {
//    Header h = createHeader(this,myName,myPassword,myService);
//    h.setExpiration(expiration);
//    h.setLazyMessage(myLazyMessagePath);
    XMLElement x=  rootMessage.generateTml(myHeader);
    if (doAppendMethods) {
      addMethods(x);
    }

    return x;
  }

  private void addMethods(XMLElement x) {
    XMLElement methods = new CaseSensitiveXMLElement();
    methods.setName("methods");
    x.addChild(methods);
    for (int i = 0; i < myMethods.size(); i++) {
      Method m = (Method)myMethods.get(i);
      XMLElement mx = m.toXml(x);
      methods.addChild(mx);
    }
  }

  public void fromXml(XMLElement e) {
    Vector v = e.getChildren();

    rootMessage.fromXml(e);
    for (int i = 0; i < v.size(); i++) {
      XMLElement x = (XMLElement)v.get(i);
      String name = x.getName();
      if (name.equals("methods")) {
        loadMethods(x);
      }
    }
  }

  public ArrayList getAllMethods() {
    return (ArrayList)myMethods.clone();
  }

  private void loadMethods(XMLElement e) {
    myMethods.clear();
    Vector v = e.getChildren();
    for (int i = 0; i < v.size(); i++) {
      XMLElement x = (XMLElement)v.get(i);
      String name = (String)x.getAttribute("name");
      Method m = createMethod(this,name);
      m.fromXml(x);
      myMethods.add(m);
    }
  }

  public void prune() {
    getRootMessage().prune();
  }

}
