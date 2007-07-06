package com.dexels.navajo.document.base;

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

import com.dexels.navajo.document.*;
import java.io.*;

public class BaseHeaderImpl
    extends BaseNode
    implements Header {

//  protected String myName;
//  protected String myPassword;
//  protected String myService;
    
  protected final BaseClientImpl myClientImpl;
  protected String myIp;
  
  private Set piggyBackData = null;
  
  
  /**
   * @deprecated
   */
  protected String myLazyMessage = null;
  protected long expiration = -1;
  /**
   * @deprecated
   */
 protected TreeMap lazyMessageList = new TreeMap();

  //protected boolean isFinished = false;
///**
//  * @deprecated
//  */
// //protected String myCallbackName = null;
// /**
//  * @deprecated
//  */
// //protected String myCallbackPointer = null;
// //protected int percReady = -1;
  

  protected Map attributeMap = null;

  
  protected final BaseCallbackImpl myCallback;
  protected  BaseTransactionImpl myTransaction;
  
  
  public BaseHeaderImpl(com.dexels.navajo.document.Navajo n, String user,
                    String password, String service) {
    super(n);
    myClientImpl =  new BaseClientImpl(n);
    myTransaction = new BaseTransactionImpl(n);
    myTransaction.setRpc_name(service);
    myTransaction.setRpc_usr(user);
    myTransaction.setRpc_pwd(password);
    setIdentification(user, password, service);
    myCallback  = new BaseCallbackImpl(n);
  }

  public Header copy(Navajo n) {
	  Header h = NavajoFactory.getInstance().createHeader(n, getRPCName(), getRPCUser(), getRPCPassword(), expiration);
	  for (Iterator iter = getAttributes().keySet().iterator(); iter.hasNext();) {
		String element = (String) iter.next();
		h.setHeaderAttribute(element, getHeaderAttribute(element));
	  }
	  return h;
  }
  
  public BaseHeaderImpl(com.dexels.navajo.document.Navajo n) {
    super(n);
    myClientImpl =  new BaseClientImpl(n);
    myTransaction = new BaseTransactionImpl(n);
    myCallback  = new BaseCallbackImpl(n);
  }

  public final void setSchedule(String s) {
	  myTransaction.setRpc_schedule(s);
  }
  
  public final String getSchedule() {
	  return myTransaction.getRpc_schedule();
  }
  
  public final void setExpiration(long i) {
    expiration = i;
  }

  public void setHeaderAttribute(String key, String value) {
      if (attributeMap==null) {
        attributeMap = new HashMap();
    }
      attributeMap.put(key, value);
  }

  public String getHeaderAttribute(String key) {
      if (attributeMap==null) {
        return null;
    }
    return (String)attributeMap.get(key);
  }

  /**
   * @deprecated
   */

  public final void addLazyMessagePath(String path, int startIndex,
                                       int endIndex, int total) {
    LazyMessagePath lmp = NavajoFactory.getInstance().createLazyMessagePath(getRootDoc(), path, startIndex, endIndex, total);
    lazyMessageList.put(path, lmp);
    /** @todo repair this function*/
//    throw new UnsupportedOperationException();
  }

//  public ArrayList getAllLazyMessages() {
//    return lazyMessageList;
//  }


  /**
   * @deprecated
   */
  public final void setIdentification(String user, String password,
                                      String service) {
//    myName = user;
//    myPassword = password;
//    myService = service;
      myTransaction.setRpc_name(service);
      myTransaction.setRpc_pwd(password);
      myTransaction.setRpc_usr(user);
  }

  /**
   * @deprecated
   */
  public final void setService(String service) {
      myTransaction.setRpc_name(service);
        }
  
  /**
   * @deprecated
   */
  public final LazyMessagePath getLazyMessagePath(String path) {
    /** @todo repair this function*/
//    throw new UnsupportedOperationException();
//
    return (LazyMessagePath) lazyMessageList.get(path);
  }

 

  public final void setRPCName(String s) {
      myTransaction.setRpc_name(s);
       }
  
  /**
   * @deprecated
   */
  public final String getIPAddress() {
      return myClientImpl.getAddress();
  }
  
  public final String getCallBackInterupt(String object) {
    /**@todo Implement this com.dexels.navajo.document.Header abstract method*/
//    throw new java.lang.UnsupportedOperationException(
//        "Method getCallBackInterupt() not yet implemented.");
	
	  if ( getCallback().getRef(object) == null ) {
		  System.err.println("UNKNOWN CALLBACK OBJECT: " + object);
		  return "";
	  }
	  return getCallback().getRef(object).getInterrupt();
	  
  }

  /**
   * @deprecated
   */
  public final void setRequestData(String ipAddress, String host) {
      myClientImpl.setAddress(ipAddress);
      myClientImpl.setHost(host);
  }

  public final String getRPCName() {
    return myTransaction.getRpc_name();
  }

  public final String getRPCPassword() {
      return myTransaction.getRpc_pwd();
  }

  public final void setRPCUser(String s) {
      myTransaction.setRpc_usr(s);
  }

  public final long getExpirationInterval() {
    return expiration;
  }

  public void setExpirationInterval(long l) {
    expiration = l;
  }

  public java.util.Map getLazyMessageMap() {
    return lazyMessageList;
  }


 
  public void removeCallBackPointers() {
	  if ( myCallback != null && myCallback.getObjects() != null ) {
		  myCallback.getObjects().clear();
	  }
  }

  public final String getCallBackPointer(String object) {
	  if ( myCallback != null && myCallback.getObjects() != null ) {
		  for (int i = 0; i < myCallback.getObjects().size(); i++ ) {
			  BaseObjectImpl boi = (BaseObjectImpl) myCallback.getObjects().get(i);
			  if ( boi.getName() != null && boi.getName().equals(object ) ) {
				  return boi.getRef();
			  }
		  }
	  }
	  return null;
  }

  public final void setRPCPassword(String s) {
    myTransaction.setRpc_pwd(s);
  }


  public final String getRPCUser() {
    return myTransaction.getRpc_usr();
  }

  /**
   * @deprecated
   */
  public final void setCallBack(String name, String pointer, int percReady,
                                boolean isFinished, String interrupt) {
    //this.isFinished = isFinished;
//    this.myInterrupt = interrupt;
//    this.myCallbackName = name;
//    this.myCallbackPointer = pointer;
    //this.percReady = percReady;
    BaseObjectImpl boi = new BaseObjectImpl(getRootDoc());
    boi.setName(name);
    boi.setRef(pointer);
    boi.setPercReady((double) percReady);
    boi.setFinished(isFinished);
    boi.setInterrupt(interrupt);
    myCallback.addObject(boi);
  }
  
  public void addTransaction(BaseTransactionImpl bci) {
      // TODO Auto-generated method stub
      myTransaction = bci;
  }

//  public void addCallBack(BaseCallbackImpl bci) {
//      // TODO Auto-generated method stub
//      
//  }  
  /**
   * @deprecated
   */

  public final void setCallBackInterrupt(String interrupt) {
	  ArrayList objects = getCallback().getObjects();
	  if(objects!=null && objects.size()>0) {
		  BaseObjectImpl boi = (BaseObjectImpl)objects.get(0);
		  boi.setInterrupt(interrupt);
	  }
	  if (objects!=null && objects.size()>1) {
		System.err.println("Warning: Multible references found. Ambiguous kill detected");
  	}
	  if (objects==null) {
		System.err.println("Problem setting setCallBackInterrupt, no object found.");
	}
  }
  /**
   * @deprecated
   */

  public final String getUserAgent() {
    return "MoZiLLa";
  }
  /**
   * @deprecated
   */

  public final String getHostName() {
//    try {
//      InetAddress.getLocalHost().getHostName();
//    }
//    catch (UnknownHostException ex) {
//      ex.printStackTrace();
//    }
    return "localhost";

  }

  public com.dexels.navajo.document.LazyMessageImpl getLazyMessages() {
    return null;
  }
  
  /**
   * @deprecated
   * TODO: Add object ref for supporting multiple async objects!
   */

  public int getCallBackProgress() {
	  if ( myCallback != null && myCallback.getObjects() != null && !myCallback.getObjects().isEmpty() ) {
		  String pr = ( (BaseObjectImpl) myCallback.getObjects().get(0) ).getPercReady();
		  if ( pr != null ) {
			  return (int) Double.parseDouble(pr);
		  } else {
			  return 0;
		  }
	  }
	  return 0;
  }

  /**
   * Returns whether the asynchronous server process has completed
   * TODO: Add object ref for supporting multiple async objects!
   */
  public boolean isCallBackFinished() {
	  if ( myCallback != null && myCallback.getObjects() != null && !myCallback.getObjects().isEmpty() ) {
		  BaseObjectImpl boi = (BaseObjectImpl) myCallback.getObjects().get(0);
		  return boi.isFinished();
	  }
	  return false;
  }

public Map getHeaderAttributes() {
    return attributeMap;
}

public List getChildren() {
    // TODO Auto-generated method stub
    ArrayList al = new ArrayList();
    if (myTransaction!=null) {
        al.add(myTransaction);
    }
    al.add(myCallback);
    al.add(myClientImpl);
    if (piggyBackData!=null) {
		for (Iterator iter = piggyBackData.iterator(); iter.hasNext();) {
			Map element = (Map) iter.next();
			BasePiggybackImpl bpi  = new BasePiggybackImpl(element);
			al.add(bpi);
		}
	}
    return al;
}

public String getTagName() {
    // TODO Auto-generated method stub
    return "header";
}

public Object getRef() {
    // TODO Auto-generated method stub
    return null;
}


public String getRequestId() {
    if (myTransaction!=null) {
        return myTransaction.getRequestId();
    }
    return null;
}

public static void main (String [] args) throws Exception {
        System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
        Navajo n = NavajoFactory.getInstance().createNavajo();
        Header h = NavajoFactory.getInstance().createHeader(n , "aap", "noot", "mies", -1 );
        n.addHeader(h);
    }

public void setRequestId(String id) {
    if (myTransaction!=null) {
    	myTransaction.setRequestId(id);
    }
}

public BaseCallbackImpl getCallback() {
    return myCallback;
}

public void addPiggyBackData(Map element) {
	if (piggyBackData==null) {
		piggyBackData = new HashSet();
	}
	piggyBackData.add(element);
}

/**
 * Returns a set of maps
 * @return
 */
public Set getPiggybackData() {
	return  piggyBackData;
}

public void clearPiggybackData() {
	if ( piggyBackData != null ) {
		piggyBackData.clear();
	}
}


public Map getAttributes() {
	if(attributeMap==null) {
		return null;
	}
	return new HashMap(attributeMap);
}

}
