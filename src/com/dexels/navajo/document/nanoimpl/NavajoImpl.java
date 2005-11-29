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


public final class NavajoImpl implements Navajo {

  private MessageImpl rootMessage = null;
  private HeaderImpl myHeader = null;
//  private String myName="";
//  private String myPassword="";
//  private String myService="";
  private ArrayList myMethods = new ArrayList();
  //private boolean doAppendMethods = true;
  private int expiration = -1;
  private String myLazyMessagePath = "";
  private int myErrorNumber;
  private String myErrorDescription;

//  private Map myDepSet = null;

  public NavajoImpl() {
    rootMessage = (MessageImpl)NavajoFactory.getInstance().createMessage(this,"");
//    new MessageImpl(this);
    /** @todo Check.. */
    myHeader = (HeaderImpl)NavajoFactory.getInstance().createHeader(this,"","","",-1);
  }

  public void setRootMessage(Message r) {
    rootMessage = (MessageImpl)r;
  }

  public void addHeader(Header h) {
    if (myHeader == null) {
      myHeader = (HeaderImpl)h;
    }
    myHeader.merge((HeaderImpl)h);
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

  public Message getRootMessage() {
    return rootMessage;
  }

  public void addMap(com.dexels.navajo.document.MapTag map) throws NavajoException {
    throw new java.lang.UnsupportedOperationException(
        "Method addMap() not yet implemented.");
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

  public void addLazyMessagePath(String path, int startIndex, int endIndex, int total) {
///** @todo Fix this one */
    myHeader.addLazyMessagePath(path, startIndex, endIndex, total);
  }
//

  public ArrayList getAllMessages() {
    return rootMessage.getAllMessages();
  }

  public ArrayList getMessages(String regexp) throws NavajoException {
    if (regexp.startsWith(MESSAGE_SEPARATOR)) {
      return rootMessage.getMessages(regexp.substring(1));
    }
//    System.err.println("Getmessages, in Navajo. looking for messagE: "+regexp);
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
  	return toXml(false, null);
  }

  public XMLElement toXml(boolean condense, String method) {
    XMLElement x = new CaseSensitiveXMLElement();
    toXmlElement(x, condense, method);
    return x;
//    return rootMessage.generateTml(myHeader);

  }

  private final void toXmlElement(XMLElement x, boolean condense, String method) {
//    XMLElement x=  ((MessageImpl)rootMessage).toXml(null);
    x.setName("tml");
//    System.err.println("\n\nMY HEADER: "+x);
    if (myHeader != null) {
      x.addChild( ( (HeaderImpl) myHeader).toXml(null));
      ( (MessageImpl) rootMessage).generateTml(this, myHeader, x, condense, method);
    }
//    System.err.println("MY HEADERAGAIN: "+x+"\n\n");
//    rootMessage.generateTml(myHeader,x);
    if (!condense) {
        addMethods(x);
    }
  }

  private final void addMethods(XMLElement x) {
    XMLElement methods = new CaseSensitiveXMLElement();
    methods.setName("methods");
    x.addChild(methods);
    for (int i = 0; i < myMethods.size(); i++) {
      MethodImpl m = (MethodImpl) myMethods.get(i);
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
      } else if (name.equals("header")) {
         ((HeaderImpl) myHeader).fromXml(x);
      }
    }
  }

  public ArrayList getAllMethods() {
    return (ArrayList)myMethods.clone();
  }

  private final void loadMethods(XMLElement e) {
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
    ((MessageImpl)getRootMessage()).prune();
  }


  public void importMessage(Message m) {
    MessageImpl mi = (MessageImpl) m;
    Message n = mi.copy(this);
    rootMessage.addMessage(n);
  }

  public LazyMessagePath getLazyMessagePath(String path) {
    return myHeader.getLazyMessagePath(path);
  }

  public void write(java.io.Writer writer) throws NavajoException {
  	write(writer, false, null);
  }

  public void write(OutputStream o) throws NavajoException {
  	write(o, false, null);
  }

  public void write(OutputStream o, boolean condense, String method) {
    try {
      OutputStreamWriter w = new OutputStreamWriter(o);
      toXml(condense, method).write(w);
      w.flush();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public String toString() {
    return toXml().toString();
  }

  public void write(Writer w, boolean condense, String method) {
    try {
      toXml(condense, method).write(w);
      w.flush();
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
    return rootMessage.addMessage(m,b);
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
                    message = message.getMessage(property);
                if (message == null)
                    return null;
            }
            index++;
        }

        return sel;
  }
  public Property getProperty(String s) {
    return rootMessage.getProperty(s);
  }

  public ArrayList getProperties(String s) throws NavajoException {
    ArrayList props = new ArrayList();
        Property prop = null;
        ArrayList messages = null;
        ArrayList sub = null;
        ArrayList sub2 = null;
        String property = null;
        Message message = null;

        StringTokenizer tok = new StringTokenizer(s, Navajo.MESSAGE_SEPARATOR);
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
            message = (Message) messages.get(i);

            prop = message.getProperty(realProperty);

            if (prop != null)
                props.add(prop);
        }
        return props;

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

  public boolean isEqual(Navajo o) {
     try {
      Navajo other = (Navajo) o;
      ArrayList otherMsgs = other.getAllMessages();
      ArrayList myMsgs = this.getAllMessages();

      System.err.println("-----------------");
      this.write(System.err);
      System.err.println("-----------------");
      o.write(System.err);
      System.err.println("-----------------");


      if (otherMsgs.size() != myMsgs.size()){
        return false;
      }

      for (int i = 0; i < otherMsgs.size(); i++) {
        Message otherMsg = (Message) otherMsgs.get(i);
        boolean match = false;
        for (int j = 0; j < myMsgs.size(); j++) {
          Message myMsg = (Message) myMsgs.get(j);
          if (myMsg.isEqual(otherMsg, "")) {
            match = true;
            j = myMsgs.size() + 1;
          }
        }
        if (!match){
          return false;
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }



  public synchronized List refreshExpression() throws NavajoException{
//    ArrayList aa = getAllMessages();
//    for (int i = 0; i < aa.size(); i++) {
//      Message current = (Message)aa.get(i);
//      current.refreshExpression();
//    }
    try {
//      if (myDepSet == null) {
//        updateDependencySet();
//      } else {
//        System.err.println("Reusing depset");
//      }
//
//        System.err.println("REFRESHING EXPRESSION FROM: ");
//        Thread.dumpStack();
        Map depSet = NavajoFactory.getInstance().getExpressionEvaluator().createDependencyMap(this);
        return NavajoFactory.getInstance().getExpressionEvaluator().processRefreshQueue(depSet);

        
      }
    catch (NavajoException ex) {
      ex.printStackTrace();
      System.err.println("Error refreshing navajo");
      return null;
    }
 
  }

  public void read(java.io.Reader stream) throws NavajoException {
    XMLElement xe = new CaseSensitiveXMLElement();
    try {
      xe.parseFromReader(stream);
    }
    catch (XMLParseException ex) {
      throw new NavajoExceptionImpl(ex);
    }
    catch (IOException ex) {
      throw new NavajoExceptionImpl(ex);
    }
    fromXml(xe);
  }

  public void read(java.io.InputStream stream) throws NavajoException {
    InputStreamReader isr = new InputStreamReader(stream);
    read(isr);
  }

//  public void updateDependencySet() throws NavajoException {
//    myDepSet = NavajoFactory.getInstance().getExpressionEvaluator().createDependencyMap(this);
//  }

  public boolean includeMessage(Message m, String method) {

  	//System.err.println("in NavajoImpl includeMessage(), #methods: " + myMethods.size() + ", method = " + method);
  	MethodImpl methObj = (MethodImpl) getMethod(method);
  	if (methObj != null) {
  		return methObj.includeMessage(m);
  	} else {
  		return true;
  	}

  }
}
