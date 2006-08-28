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

  protected boolean isFinished = false;
  /**
   * @deprecated
   */
 protected String myInterrupt = null;
 /**
  * @deprecated
  */
 protected String myCallbackName = null;
 /**
  * @deprecated
  */
 protected String myCallbackPointer = null;
  protected int percReady = -1;
  

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

  public BaseHeaderImpl(com.dexels.navajo.document.Navajo n) {
    super(n);
    myClientImpl =  new BaseClientImpl(n);
    myTransaction = new BaseTransactionImpl(n);
    myCallback  = new BaseCallbackImpl(n);
  }

  public final void setExpiration(long i) {
    expiration = i;
  }

  public void setAttribute(String key, String value) {
      if (attributeMap==null) {
        attributeMap = new HashMap();
    }
      attributeMap.put(key, value);
  }

  public String getAttribute(String key) {
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
  
  /**
   * @deprecated
   */
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
    myCallbackPointer = null;
  }

  public final String getCallBackPointer(String object) {
    return myCallbackPointer;
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
    this.isFinished = isFinished;
    this.myInterrupt = interrupt;
    this.myCallbackName = name;
    this.myCallbackPointer = pointer;
    this.percReady = percReady;
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
    this.myInterrupt = interrupt;
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
   */

  public int getCallBackProgress() {
    return percReady;
  }

  /**
   * Returns whether the asynchronous server process has completed
   */
  public boolean isCallBackFinished() {
    return isFinished;
  }

public Map getAttributes() {
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
    System.err.println("Serializing header.");
    if (piggyBackData!=null) {
//    	System.err.println(":::::::::: ADDING PIGGYBACKDATA ::::::: count:  "+piggyBackData.size());
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
        System.err.println("n = " + n.getClass().getName());
        Header h = NavajoFactory.getInstance().createHeader(n , "aap", "noot", "mies", -1 );
        n.addHeader(h);
        n.write(System.err);
    }

public void setRequestId(String id) {
    if (myTransaction!=null) {
    	//System.err.println("in setRequestId(" + id + ")");
        myTransaction.setRequestId(id);
    }
}

public BaseCallbackImpl getCallback() {
    return myCallback;
}

public void addPiggyBackData(Map element) {
	if (piggyBackData==null) {
//		System.err.println("Lazy create of piggyback data");
		piggyBackData = new HashSet();
	}
	piggyBackData.add(element);
//	System.err.println("piggyback size: "+piggyBackData.size());
}

/**
 * Returns a set of maps
 * @return
 */
public Set getPiggybackData() {
	return  piggyBackData;
}

public void clearPiggybackData() {
	piggyBackData.clear();
}

}
