package com.dexels.navajo.document.nanoimpl;
import java.util.*;
import java.io.*;
import com.dexels.navajo.document.*;
/**
 * <p>Title: ShellApplet</p>
 * <p>Description: </p>
 * <p>Part of the Navajo mini client, based on the NanoXML parser</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels </p>
 * @author Frank Lyaruu
 * @version 1.0
 */


public class NavajoImpl implements Navajo {

  private MessageImpl rootMessage = null;
  private Header myHeader = null;
//  private String myName="";
//  private String myPassword="";
//  private String myService="";
  private ArrayList myMethods = new ArrayList();
  private boolean doAppendMethods = false;
  private int expiration = -1;
  private String myLazyMessagePath = "";
  private int myErrorNumber;
  private String myErrorDescription;

  public NavajoImpl() {
    rootMessage = (MessageImpl)NavajoFactory.getInstance().createMessage(this,"");
//    new MessageImpl(this);
    /** @todo Check.. */
    myHeader = NavajoFactory.getInstance().createHeader(this,"","","",-1);
  }

  public void setRootMessage(Message r) {
    rootMessage = (MessageImpl)r;
  }

  public void addHeader(Header h) {
    myHeader = h;
  }

  public void removeHeader() {
    myHeader = null;
    /** @todo Don't really know what I should do here */
  }

  public void setExpiration(int i) {
    expiration = i;
/** @todo Verify this */
//    myHeader.setExpirationInterval(i);
  }

  public Navajo copy() {
    Navajo ni = NavajoFactory.getInstance().createNavajo();
    NavajoImpl n = (NavajoImpl)ni;
//    n.setRootMessage(cop getRootMessage().copy(n));
    ArrayList al = getAllMessages();
    for (int i = 0; i < al.size(); i++) {
      Message m = (Message)al.get(i);
      Message m2 = copyMessage(m,n);
      n.addMessage(m2);
    }

    for (int i = 0; i < myMethods.size(); i++) {
      Method m = (Method)myMethods.get(i);
      Method m2 = n.copyMethod(m,n);
      n.addMethod(m2);
    }
    return n;
  }
  public void addMethod(Method m) {
    myMethods.add(m);
  }
 public Message getMessage(String name)  {
    return rootMessage.getMessage(name);
  }

//  public Message getMessage(String name, int index) {
//    return rootMessage.getMessage(name,index);
//  }

  public MessageImpl getRootMessage() {
    return rootMessage;
  }

  public Message addMessage(Message m) {
    rootMessage.addMessage(m);
    return m;
  }

  public void setIdentification(String username, String password, String service) {
    myHeader.setRPCUser(username);
    myHeader.setRPCPassword(password);
    myHeader.setRPCName(service);
//    myHeader.setIdentification(username,password,service);
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

//  public void addLazyMessage(String path, int startIndex, int endIndex) {
/** @todo Fix this one */
//    myHeader.addLazyMessage(path, startIndex, endIndex);
//  }

//  public LazyMessagePath getLazyMessagePath(String path) {
    /** @todo Fix this one */
//    return myHeader.getLazyMessagePath(path);
//    return null;
//  }

  public ArrayList getAllMessages() {
    return rootMessage.getAllMessages();
  }

  public ArrayList getMessages(String regexp) throws NavajoException {
    if (regexp.startsWith(MESSAGE_SEPARATOR)) {
      return rootMessage.getMessages(regexp.substring(1));
    }

    return rootMessage.getMessages(regexp);
  }

//  public Message getByPath(String path) {
//    return rootMessage.getByPath(path);
//  }

//<method name="navajo_logon_send"> <required message="identification"/> <required message="services"/> </method>

  public static Method createMethod(NavajoImpl n, String name) {
    return new MethodImpl(n,name);
  }

  public Header getHeader() {
    return myHeader;
  }

  public XMLElement toXml() {
    XMLElement x = new CaseSensitiveXMLElement();
    toXmlElement(x);
    return x;
//    return rootMessage.generateTml(myHeader);

  }

  private void toXmlElement(XMLElement x) {
//    XMLElement x=  ((MessageImpl)rootMessage).toXml(null);
    x.setName("tml");
//    System.err.println("\n\nMY HEADER: "+x);
    x.addChild(((HeaderImpl)myHeader).toXml(null));
    ((MessageImpl)rootMessage).generateTml(myHeader,x);
//    System.err.println("MY HEADERAGAIN: "+x+"\n\n");
//    rootMessage.generateTml(myHeader,x);
    if (doAppendMethods) {
      addMethods(x);
    }
  }

  private void addMethods(XMLElement x) {
    XMLElement methods = new CaseSensitiveXMLElement();
    methods.setName("methods");
    x.addChild(methods);
    for (int i = 0; i < myMethods.size(); i++) {
      MethodImpl m = (MethodImpl)myMethods.get(i);
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
    System.err.println("\nLOADED:\n"+toXml()+"\n\n");
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
      MethodImpl m = (MethodImpl)createMethod(this,name);
      m.fromXml(x);
      myMethods.add(m);
    }
  }

  public void prune() {
    getRootMessage().prune();
  }


  public void importMessage(Message m) {
    throw new UnsupportedOperationException();

  }

  public void write(OutputStream o) {
    try {
      toXml().write(new OutputStreamWriter(o));
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void write(Writer w) {
    try {
      toXml().write(w);
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void clearAllSelections() {
    try {
      ( (MessageImpl) rootMessage).clearAllSelections();
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }
  }

  public void appendDocBuffer(Object o) {
    XMLElement xe = (XMLElement)o;
    fromXml(xe);
  }

  public Object getMessageBuffer() {
    return toXml();
  }
  public String persistenceKey() {
      String result = this.toString();

      return result.hashCode() + "";
  }
  public void removeMessage(String s) {
    rootMessage.removeMessage(s);
  }
  public void removeMessage(Message m) {
    rootMessage.removeMessage(m);
  }

  public Message addMessage(Message m, boolean b) {
    rootMessage.addMessage(m,b);
    return m;
  }

  public Method copyMethod(Method m, Navajo n) {
    MethodImpl mi = (MethodImpl)m;
    Method m2 = mi.copy(n);
    return m2;
  }
  public Method copyMethod(String s, Navajo n) {
    MethodImpl mi = (MethodImpl)getMethod(s);
    Method m2 = mi.copy(n);
    return m2;
  }

  public Message copyMessage(String s, Navajo n) {
    Message m = getMessage(s);
    return copyMessage(m,n);
  }

  public Message copyMessage(Message m, Navajo n) {
    MessageImpl mc = (MessageImpl)(((MessageImpl)m).copy(n));
    return mc;
  }

  public boolean contains(String s) {
    /** @todo Implement */
    throw new UnsupportedOperationException();
  }

  public Selection getSelection(String s) {
//    return rootMessage.getS
    /** @todo Implement */
    throw new UnsupportedOperationException();
//    return null;
  }
  public Property getProperty(String s) {
    return rootMessage.getProperty(s);
  }

  public ArrayList getProperties(String s) throws NavajoException {
    return rootMessage.getProperties(s);
  }

  public Method getMethod(String s) {
    for (int i = 0; i < myMethods.size(); i++) {
      Method m = (Method)myMethods.get(i);
      if (m.getName().equals(s)) {
        return m;
      }
    }
    return null;
  }

  public Vector getRequiredMessages(String s) {

    /** @todo Implement */
    throw new UnsupportedOperationException();
  }

  /**
   * DEBUGGING: write a specific message (name) to a file (filename).
   */
  public void writeMessage(String name, String filename) throws NavajoException {
     try {
          FileWriter out = new FileWriter("/tmp/" + filename);
          ((MessageImpl)getMessage(name)).toXml(null).write(out);
      } catch (Exception e) {
        e.printStackTrace();
      }
  }

  public String writeDocument(String filename) {
    try {
         FileWriter out = new FileWriter("/tmp/" + filename);
         toXml().write(out);
         return "succes";
     } catch (Exception e) {
         return "failure";
     }
  }

  public int getErrorNumber() {
    return myErrorNumber;
  }
  public void setErrorNumber(int i) {
    myErrorNumber = i;
  }

  /**
   * Get the errorDescription class property.
   */

  public String getErrorDescription() {
    return myErrorDescription;
  }
  public void setErrorDescription(String s) {
    myErrorDescription = s;
  }

}
