package com.dexels.navajo.document.base;
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


public class BaseNavajoImpl extends BaseNode implements Navajo {

  protected final BaseMessageImpl rootMessage;
  protected BaseHeaderImpl myHeader = null;
  protected final BaseMethodsImpl myMethods = new BaseMethodsImpl(this);
  protected int expiration = -1;
  protected String myLazyMessagePath = "";
  protected int myErrorNumber;
  protected String myErrorDescription;


  public BaseNavajoImpl() {
    rootMessage = (BaseMessageImpl)NavajoFactory.getInstance().createMessage(this,"");
//    new BaseMessageImpl(this);
  }

  public String getImplementationName() {
      return "SAXP";
  }
  
  public void addHeader(Header h) {
    if (myHeader == null) {
      myHeader = (BaseHeaderImpl)h;
    }
//    myHeader.merge((BaseHeaderImpl)h);
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
    BaseNavajoImpl n = (BaseNavajoImpl)ni;
//    n.setRootMessage(cop getRootMessage().copy(n));
    ArrayList al = getAllMessages();
    for (int i = 0; i < al.size(); i++) {
      Message m = (Message)al.get(i);
      Message m2 = copyMessage(m,n);
      n.addMessage(m2);
    }
    ArrayList mm = myMethods.getAllMethods();
    for (int i = 0; i < mm.size(); i++) {
      Method m = (Method)mm.get(i);
      Method m2 = m.copy(n);
      n.addMethod(m2);
    }
    return n;
  }
  public void addMethod(Method m) {
    myMethods.addMethod(m);
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
//      System.err.println("ADDING MESSAGE");
    rootMessage.addMessage(m);
    return m;
  }

  public void setIdentification(String username, String password, String service) {
      if (myHeader==null) {
          myHeader = (BaseHeaderImpl)NavajoFactory.getInstance().createHeader(this,service,username,password,-1);

    }
      myHeader.setRPCUser(username);
      myHeader.setRPCPassword(password);
      myHeader.setRPCName(service);
  }
    //    myHeader.setIdentification(username,password,service);
  

//  public void setService(String service) {
//    myService = service;
//  }

  public void setMethod() {
//<method name="navajo_logon_send"> <required message="identification"/> <required message="services"/> </method>
//    Message m = createMessage(this,"methods");
//
//    addMessage(m);
  }

  /**
   * @deprecated
   * @see com.dexels.navajo.document.Navajo#addLazyMessagePath(java.lang.String, int, int, int)
   */
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


  public Header getHeader() {
    return myHeader;
  }


  public ArrayList getAllMethods() {
    return myMethods.getAllMethods();
  }

  public void prune() {
    ((BaseMessageImpl)getRootMessage()).prune();
  }

/** @deprecated. This is SO JAXP
*/
  public void importMessage(Message m) {
    BaseMessageImpl mi = (BaseMessageImpl) m;
    Message n = mi.copy(this);
    rootMessage.addMessage(n);
  }

  /**
   * @deprecated
   */
  public LazyMessagePath getLazyMessagePath(String path) {
    return myHeader.getLazyMessagePath(path);
  }

  public void clearAllSelections() {
    try {
      ( (BaseMessageImpl) rootMessage).clearAllSelections();
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }
  }


  public String toString() {
	  StringWriter sw = new StringWriter();
	  try {
		this.write(sw);
	} catch (NavajoException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return sw.toString();
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

  public Message copyMessage(String s, Navajo n) {
    Message m = getMessage(s);
    return copyMessage(m,n);
  }

  public Message copyMessage(Message m, Navajo n) {
    BaseMessageImpl mc = (BaseMessageImpl)(((BaseMessageImpl)m).copy(n));
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
      return myMethods.getMethod(s);
  }

  public Vector getRequiredMessages(String s) {

    /** @todo Implement */
    throw new UnsupportedOperationException();
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

//      System.err.println("-----------------");
//      this.write(System.err);
//      System.err.println("-----------------");
//      o.write(System.err);
//      System.err.println("-----------------");


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

/**
 * @deprecated
 */
  public void read(java.io.InputStream stream) throws NavajoException {
    InputStreamReader isr = new InputStreamReader(stream);
    read(isr);
  }


//
//  public boolean includeMessage(Message m, String method) {
//  	Method methObj = (Method) getMethod(method);
//  	if (methObj != null) {
//  		return methObj.includeMessage(m);
//  	} else {
//  		return true;
//  	}
//  }
  public Object getRef() {
      throw new UnsupportedOperationException("getRef not possible on base type. Override it if you need it");
  }

public String writeDocument(String filename) {
    throw new UnsupportedOperationException("Oh please. writeDocument is SO five years ago");
}

public void writeMessage(String name, String filename) throws NavajoException {
    throw new UnsupportedOperationException("Oh please. writeMessage is SO five years ago");
}

/**
 * @deprecated
 */
public Method copyMethod(String name, Navajo newDocument) {
    return null;
}

/**
 * @deprecated
 */
public Method copyMethod(Method method, Navajo newDocument) {
     return null;
}

/**
 * @deprecated
 */
public Object getMessageBuffer() {
     return this;
}

/**
 * @deprecated
 */
public void appendDocBuffer(Object d) throws NavajoException {
    Navajo n = (Navajo)d;
    ArrayList msgs = n.getAllMessages();
    for (int i = 0; i < msgs.size(); i++) {
        Message m = (Message)msgs.get(i);
        addMessage(m.copy(this));
    }
}

public void write(Writer writer, boolean condense, String method) throws NavajoException {
    // TODO implement condensation, and method filtering
    write(writer);
}

public void write(OutputStream stream, boolean condense, String method) throws NavajoException {
    // TODO implement condensation, and method filtering
    super.write(stream);
}

public void printElement(Writer sw, int indent) throws IOException {
//    sw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    super.printElement(sw, indent);
}
/** @deprecated
 * Create a new navajo using a stream in the NavajoFactory
 * @see com.dexels.navajo.document.Navajo#read(java.io.Reader)
 */
public void read(Reader stream) throws NavajoException {
     
}

/*
 * Creates a reader for this Navajo. 
 * It uses files in the background.
 * Don't forget to close the reader!
 */

public Reader createReader() throws NavajoException, IOException{
    final File f = File.createTempFile("navajoHTML", ".tml");
    //f.deleteOnExit();
    FileWriter fw = new FileWriter(f);
    write(fw);
    fw.flush();
    fw.close();
    FileReader fr = new FileReader(f) {
        public void close() throws IOException {
            super.close();
            f.delete();
        };
    }; 
    return fr;
}

public void disposeReader(Reader r) {
    
}

public Map getAttributes() {
    Map m = new HashMap();
    m.put("documentImplementation",getImplementationName());
    return m;
}

public List getChildren() {
    // TODO Auto-generated method stub
    ArrayList al = new ArrayList();
    if (myHeader!=null) {
        al.add(myHeader);
    }
    al.addAll(getAllMessages());
    al.add(myMethods);
    return al;
}

public String getTagName() {
    return "tml";
}

}
