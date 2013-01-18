package com.dexels.navajo.document.base;
import java.beans.*;
import java.io.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static final long serialVersionUID = 7765245678970907997L;
	protected final BaseMessageImpl rootMessage;
  protected BaseHeaderImpl myHeader = null;
  protected final BaseMethodsImpl myMethods = new BaseMethodsImpl(this);
  protected int expiration = -1;
  protected String myLazyMessagePath = "";
  protected int myErrorNumber;
  protected String myErrorDescription;
  private List<PropertyChangeListener> myPropertyDataListeners;
  private final NavajoFactory myFactory;
  private HashMap<String,Navajo> navajoMap = new HashMap<String,Navajo>();
  
private final static Logger logger = LoggerFactory
		.getLogger(BaseNavajoImpl.class);

  public BaseNavajoImpl(NavajoFactory nf) {
	  myFactory = nf;
	  rootMessage = (BaseMessageImpl)nf.createMessage(this,"");
//	  new BaseMessageImpl(this);
  }

  public String getImplementationName() {
      return "SAXP";
  }
  
  public void addHeader(Header h) {
//    if (myHeader == null) {
      myHeader = (BaseHeaderImpl)h;
//    }
//    myHeader.merge((BaseHeaderImpl)h);
  }

  public void removeHeader() {
    myHeader = null;
    /** @todo Don't really know what I should do here */
  }

  public void setExpiration(int i) {
    expiration = i;
/** @todo Verify this */
  }


  public Navajo copy() {
    Navajo ni = NavajoFactory.getInstance().createNavajo();
    BaseNavajoImpl n = (BaseNavajoImpl)ni;
//    n.setRootMessage(cop getRootMessage().copy(n));
    ArrayList<Message> al = getAllMessages();
    for (int i = 0; i < al.size(); i++) {
      Message m = al.get(i);
      Message m2 = copyMessage(m,n);
      n.addMessage(m2);
    }
    List<Method> mm = myMethods.getAllMethods();
    for (int i = 0; i < mm.size(); i++) {
      Method m = mm.get(i);
      Method m2 = m.copy(n);
      n.addMethod(m2);
    }
    if(getHeader()!=null) {
        ni.addHeader(getHeader().copy(ni));
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
	 if(m==null) {
		 throw new NullPointerException("Can not add null message to Navajo object");
	 }
//      logger.info("ADDING MESSAGE");
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


  public ArrayList<Message> getAllMessages() {
    return rootMessage.getAllMessages();
  }

  public ArrayList<Message> getMessages(String regexp) throws NavajoException {
    if (regexp.startsWith(MESSAGE_SEPARATOR)) {
      return rootMessage.getMessages(regexp.substring(1));
    }
//    logger.info("Getmessages, in Navajo. looking for messagE: "+regexp);
    return rootMessage.getMessages(regexp);
  }

//  public Message getByPath(String path) {
//    return rootMessage.getByPath(path);
//  }

//<method name="navajo_logon_send"> <required message="identification"/> <required message="services"/> </method>


  public Header getHeader() {
    return myHeader;
  }


  public ArrayList<Method> getAllMethods() {
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


  public void clearAllSelections() {
    try {
      rootMessage.clearAllSelections();
    }
    catch (NavajoException ex) {
    	logger.error("Error: ", ex);
    }
  }


  public String toString() {
	  StringWriter sw = new StringWriter();
	  try {
		this.write(sw);
	} catch (NavajoException e) {
		logger.error("Error: ", e);
	}
	  return sw.toString();
  }
  
  public String persistenceKey() {
      Navajo copy = this.copy();
      copy.removeHeader();
      StringWriter sw = new StringWriter();
      try {
    	  copy.write(sw);
      } catch (NavajoException e) {
    	  logger.error("Error: ", e);
      }
      return sw.toString().hashCode() + "";
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
  
  public Message mergeMessage(Message m) {
	  return rootMessage.mergeMessage(m);
  }
  
  private Message mergeMessage(Message m, boolean preferThisNavajo) {
	  return rootMessage.mergeMessage(m, preferThisNavajo);
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
	                if (message == null)
	                    return null;
	                else
	                	message = message.getMessage(property);
            }
            index++;
        }

        return sel;
  }
  public Property getProperty(String s) {
    return rootMessage.getProperty(s);
  }

  public ArrayList<Property> getProperties(String s) throws NavajoException {
    ArrayList<Property> props = new ArrayList<Property>();
        Property prop = null;
        ArrayList<Message> messages = null;
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
            message = messages.get(i);

            prop = message.getProperty(realProperty);

            if (prop != null)
                props.add(prop);
        }
        return props;

  }

  public Method getMethod(String s) {
      return myMethods.getMethod(s);
  }

  public Vector<String> getRequiredMessages(String s) {

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
      Navajo other = o;
      ArrayList<Message> otherMsgs = other.getAllMessages();
      ArrayList<Message> myMsgs = this.getAllMessages();

//      logger.info("-----------------");
//      this.write(System.err);
//      logger.info("-----------------");
//      o.write(System.err);
//      logger.info("-----------------");


      if (otherMsgs.size() != myMsgs.size()){
        return false;
      }

      for (int i = 0; i < otherMsgs.size(); i++) {
        Message otherMsg = otherMsgs.get(i);
        boolean match = false;
        for (int j = 0; j < myMsgs.size(); j++) {
          Message myMsg = myMsgs.get(j);
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
    	logger.error("Error: ", e);
    	return false;
    }
    return true;
  }



  public synchronized List<Property> refreshExpression() throws NavajoException{
    try {

    	Map<Property,List<Property>> depSet = NavajoFactory.getInstance().getExpressionEvaluator().createDependencyMap(this);
        return NavajoFactory.getInstance().getExpressionEvaluator().processRefreshQueue(depSet);

        
      }
    catch (NavajoException ex) {
    	logger.error("Error refreshing navajo: ", ex);
    	return null;
    }
 
  }

///**
// * @deprecated
// */
//  @Deprecated
//public void read(java.io.InputStream stream) throws NavajoException {
//    InputStreamReader isr = new InputStreamReader(stream);
//    read(isr);
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
@Deprecated
public Method copyMethod(String name, Navajo newDocument) {
    return null;
}

/**
 * @deprecated
 */
@Deprecated
public Method copyMethod(Method method, Navajo newDocument) {
     return null;
}

/**
 * @deprecated
 */
@Deprecated
public Object getMessageBuffer() {
     return this;
}

/**
 * @deprecated
 */
@Deprecated
public void appendDocBuffer(Object d) throws NavajoException {
    Navajo n = (Navajo)d;
    ArrayList<Message>msgs = n.getAllMessages();
    for (int i = 0; i < msgs.size(); i++) {
        Message m = msgs.get(i);
        addMessage(m.copy(this));
    }
}

public void write(Writer writer, boolean condense, String method) throws NavajoException {
    write(writer);
}

public void write(OutputStream stream, boolean condense, String method) throws NavajoException {
    super.write(stream);
}


/**
 * @param r  
 */
public void disposeReader(Reader r) {
    
}

public Map<String,String> getAttributes() {
    Map<String,String> m = new HashMap<String,String>();
    m.put("documentImplementation",getImplementationName());
    return m;
}

public List<BaseNode> getChildren() {
    ArrayList<BaseNode> al = new ArrayList<BaseNode>();
    if (myHeader!=null) {
        al.add(myHeader);
    }
    for (Message m : getAllMessages()) {
		al.add((BaseNode) m);
	}
    al.add(myMethods);
    return al;
}

public String getTagName() {
    return "tml";
}


public void firePropertyDataChanged(Property p,Object oldValue, Object newValue) {
//	logger.info("Navajo changed. ");
	if (myPropertyDataListeners != null) {
		for (int i = 0; i < myPropertyDataListeners.size(); i++) {
			PropertyChangeListener c = myPropertyDataListeners.get(i);
			c.propertyChange(new PropertyChangeEvent(p,"value",oldValue, newValue));

//			logger.info("Alpha: PROPERTY DATA CHANGE Fired: " + oldValue + " - " + newValue);
			// Thread.dumpStack();
		}
	}
}

public void addPropertyChangeListener(PropertyChangeListener p) {
	if (myPropertyDataListeners == null) {
		myPropertyDataListeners = new ArrayList<PropertyChangeListener>();
	}
	myPropertyDataListeners.add(p);
	if(myPropertyDataListeners.size()>1) {
//		logger.info("Multiple property listeners detected!" + myPropertyDataListeners.size());
	}
}

public void removePropertyChangeListener(PropertyChangeListener p) {
	if (myPropertyDataListeners == null) {
		return;
	}
	myPropertyDataListeners.remove(p);
}

public static void main(String [] args) throws Exception {
	Navajo bitch = NavajoFactory.getInstance().createNavajo(new FileInputStream("/home/arjen/projecten/Navajo/inheritance/JarnoBitch.xml"));
	Navajo master = NavajoFactory.getInstance().createNavajo(new FileInputStream("/home/arjen/projecten/Navajo/inheritance/JarnoMaster.xml"));
	
	Navajo merge = bitch.merge(master);
	
	merge.write(System.err);
}

public NavajoFactory getNavajoFactory() {
	return myFactory;
}

public void writeJSON(Writer w) throws NavajoException{
	try{
		this.printElementJSON(w, false);
	}catch(Exception e){
		throw new NavajoExceptionImpl(e); 
	}
}

public void writeJSONTypeless(Writer w) throws NavajoException{
	try{
		this.printElementJSONTypeless(w);
	}catch(Exception e){
		throw new NavajoExceptionImpl(e); 
	}
}

public final void printElementJSONTypeless(final Writer sw) throws IOException {
	ArrayList<Message> list = getAllMessages();
	
	writeElement(sw, "{");
	int cnt = 0;
	for(Message m : list){
		if(cnt > 0){
			writeElement(sw, ", ");
		}
		((BaseNode)m).printElementJSONTypeless(sw);
		cnt++;
	}
	writeElement(sw, "}");

}

public Navajo merge(Navajo with) throws NavajoException {
	return merge(with, false);
}

public Navajo merge(Navajo with, boolean preferThisNavajo) throws NavajoException {

	// Find duplicate messages.
	ArrayList<Message> superMessages = this.getAllMessages();
	ArrayList<Message> subMessages = with.getAllMessages();

	for (int i = 0; i < superMessages.size(); i++) {
		Message superMsg = superMessages.get(i);
		for (int j = 0; j < subMessages.size(); j++) {
			Message subMsg = subMessages.get(j);
			if ( superMsg.getName().equals(subMsg.getName()) ) {
				// Found duplicate!
				Message newMsg = subMsg.copy(this);
				this.mergeMessage(newMsg, preferThisNavajo);
			}
		}
	}

	// Find new messages.
	for (int i = 0; i < subMessages.size(); i++) {
		Message subMsg = subMessages.get(i);
		boolean newMsg = true;
		for (int j = 0; j < superMessages.size(); j++) {
			Message superMsg = superMessages.get(j);
			if ( superMsg.getName().equals(subMsg.getName()) ) {
				newMsg = false;
				j = superMessages.size() + 1;
			}
		}
		if ( newMsg ) {
			this.addMessage(subMsg.copy(this));
		}
	}

	return this;

}

public Map<String,Message> getMessages() {
	return getRootMessage().getMessages();
}

@Override
public void addNavajo(String key, Navajo value) {
	navajoMap.put(key, value);
}

@Override
public Navajo getNavajo(String key) {
	return navajoMap.get(key);
}

@Override
public void removeNavajo(String key) {
	navajoMap.remove(key);
}


}
