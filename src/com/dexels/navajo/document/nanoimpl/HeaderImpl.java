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

import java.net.*;
import java.util.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.lazy.LazyMessage;

public class HeaderImpl extends BaseNode implements Header {

  private String myName;
  private String myPassword;
  private String myService;
  private String myLazyMessage = null;
  private int expiration = -1;
  private TreeMap lazyMessageList = new TreeMap();

  public HeaderImpl(com.dexels.navajo.document.Navajo n, String user, String password, String service) {
    super(n);
    setIdentification(user,password,service);
  }

  public HeaderImpl(com.dexels.navajo.document.Navajo n) {
    super(n);
  }

  public void setExpiration(int i) {
    expiration = i;
  }

  public void addLazyMessage(String path, int startIndex, int endIndex) {
//    LazyMessagePath lmp = new LazyMessagePath(getRootDoc(), path,startIndex,endIndex);
//    lazyMessageList.put(path,lmp);
    System.err.println("FIX IT!");
    /** @todo repair this function*/
    throw new UnsupportedOperationException();
  }

//  public ArrayList getAllLazyMessages() {
//    return lazyMessageList;
//  }

  public void setIdentification(String user, String password, String service) {
    myName = user;
    myPassword = password;
    myService = service;
  }

  public void setService(String service) {
    myService = service;
  }

  public LazyMessagePath getLazyMessagePath(String path) {
    /** @todo repair this function*/
    throw new UnsupportedOperationException();

//    return (LazyMessagePath)lazyMessageList.get(path);
  }

  public XMLElement toXml(XMLElement parent) {
    try {
      XMLElement header = new CaseSensitiveXMLElement();
      header.setName("header");
      XMLElement transaction = new CaseSensitiveXMLElement();
      transaction.setName("transaction");
      if (myService!=null) {
        transaction.setAttribute("rpc_name",myService);
      }
      if (myName!=null) {
        transaction.setAttribute("rpc_usr",myName);
      }
      if (myPassword!=null) {
        transaction.setAttribute("rpc_pwd",myPassword);
      }
      transaction.setAttribute("expiration_interval",this.expiration+"");
      Iterator it = lazyMessageList.values().iterator();
      while(it.hasNext()) {
        LazyMessagePath path = (LazyMessagePath)it.next();
        transaction.addChild(path.toXml(transaction));
      }


      header.addChild(transaction);
      return header;
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }

  }
  public void setRPCName(String s) {
    /**@todo Implement this com.dexels.navajo.document.Header abstract method*/
  }
  public String getIPAddress() {
    /**@todo Implement this com.dexels.navajo.document.Header abstract method*/
    throw new java.lang.UnsupportedOperationException("Method getIPAddress() not yet implemented.");
  }
  public String getCallBackInterupt(String object) {
    /**@todo Implement this com.dexels.navajo.document.Header abstract method*/
    throw new java.lang.UnsupportedOperationException("Method getCallBackInterupt() not yet implemented.");
  }
  public void setRequestData(String ipAddress, String host) {
    /**@todo Implement this com.dexels.navajo.document.Header abstract method*/
  }
  public String getRPCName() {
    /**@todo Implement this com.dexels.navajo.document.Header abstract method*/
    throw new java.lang.UnsupportedOperationException("Method getRPCName() not yet implemented.");
  }
  public String getRPCPassword() {
    /**@todo Implement this com.dexels.navajo.document.Header abstract method*/
    throw new java.lang.UnsupportedOperationException("Method getRPCPassword() not yet implemented.");
  }
  public void setRPCUser(String s) {
    /**@todo Implement this com.dexels.navajo.document.Header abstract method*/
  }
  public long getExpirationInterval() {
    /**@todo Implement this com.dexels.navajo.document.Header abstract method*/
    throw new java.lang.UnsupportedOperationException("Method getExpirationInterval() not yet implemented.");
  }
  public String getCallBackPointer(String object) {
    /**@todo Implement this com.dexels.navajo.document.Header abstract method*/
    throw new java.lang.UnsupportedOperationException("Method getCallBackPointer() not yet implemented.");
  }
  public void setRPCPassword(String s) {
    /**@todo Implement this com.dexels.navajo.document.Header abstract method*/
  }
  public Object getRef() {
    return toXml(null);
  }
  public String getRPCUser() {
    /**@todo Implement this com.dexels.navajo.document.Header abstract method*/
    throw new java.lang.UnsupportedOperationException("Method getRPCUser() not yet implemented.");
  }
  public void setCallBack(String name, String pointer, int percReady, boolean isFinished, String interrupt) {
    /**@todo Implement this com.dexels.navajo.document.Header abstract method*/
  }
  public String getUserAgent() {
    /**@todo Implement this com.dexels.navajo.document.Header abstract method*/
    throw new java.lang.UnsupportedOperationException("Method getUserAgent() not yet implemented.");
  }
  public String getHostName() {
    /**@todo Implement this com.dexels.navajo.document.Header abstract method*/
    throw new java.lang.UnsupportedOperationException("Method getHostName() not yet implemented.");
  }
  public LazyMessage getLazyMessages() {
    /**@todo Implement this com.dexels.navajo.document.Header abstract method*/
    throw new java.lang.UnsupportedOperationException("Method getLazyMessages() not yet implemented.");
  }
}
