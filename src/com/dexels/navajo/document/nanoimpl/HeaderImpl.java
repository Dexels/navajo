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
import java.net.*;

public final class HeaderImpl extends BaseNode implements Header {

  private String myName;
  private String myPassword;
  private String myService;
  private String myIp;
  private String myLazyMessage = null;
  private long expiration = -1;
  private TreeMap lazyMessageList = new TreeMap();

  public HeaderImpl(com.dexels.navajo.document.Navajo n, String user, String password, String service) {
    super(n);
    setIdentification(user,password,service);
  }

  public HeaderImpl(com.dexels.navajo.document.Navajo n) {
    super(n);
  }

  public final void setExpiration(long i) {
    expiration = i;
  }

  public final void addLazyMessagePath(String path, int startIndex, int endIndex) {
    LazyMessagePath lmp = NavajoFactory.getInstance().createLazyMessagePath(getRootDoc(), path,startIndex,endIndex);
    lazyMessageList.put(path,lmp);
    /** @todo repair this function*/
//    throw new UnsupportedOperationException();
  }

//  public ArrayList getAllLazyMessages() {
//    return lazyMessageList;
//  }

  public final void setIdentification(String user, String password, String service) {
    myName = user;
    myPassword = password;
    myService = service;
  }

  public final void setService(String service) {
    myService = service;
  }

  public final LazyMessagePath getLazyMessagePath(String path) {
    /** @todo repair this function*/
    throw new UnsupportedOperationException();

//    return (LazyMessagePath)lazyMessageList.get(path);
  }

  public final void fromXml(XMLElement e) {
    Enumeration enum = e.enumerateChildren();
    while (enum.hasMoreElements()) {
      XMLElement child = (XMLElement) enum.nextElement();
      if (child.getName().equals("transaction")) {
        setIdentification(child.getStringAttribute("rpc_usr"), child.getStringAttribute("rpc_pwd"), child.getStringAttribute("rpc_name"));
        if (child.getStringAttribute("expiration_interval") != null && !child.getStringAttribute("expiration_interval").equals(""))
          setExpiration(Long.parseLong(child.getStringAttribute("expiration_interval")));
      }
    }
  }

  public final XMLElement toXml(XMLElement parent) {
    try {
      XMLElement header = new CaseSensitiveXMLElement();
//      System.err.println("MY USERNAME: "+getRPCUser());
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
        transaction.addChild(((LazyMessagePathImpl)path).toXml(transaction));
      }


      header.addChild(transaction);
      return header;
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }

  }
  public final void setRPCName(String s) {
    myService = s;
  }
  public final String getIPAddress() {
    return myIp;
  }
  public final String getCallBackInterupt(String object) {
    /**@todo Implement this com.dexels.navajo.document.Header abstract method*/
    throw new java.lang.UnsupportedOperationException("Method getCallBackInterupt() not yet implemented.");
  }
  public final void setRequestData(String ipAddress, String host) {
    /**@todo Implement this com.dexels.navajo.document.Header abstract method*/
  }
  public final String getRPCName() {
    return myService;
  }
  public final String getRPCPassword() {
    return myPassword;
  }
  public final void setRPCUser(String s) {
    myName = s;
  }
  public final long getExpirationInterval() {
    return expiration;
  }

  public Map getLazyMessageMap() {
    return lazyMessageList;
  }

  public final void merge(HeaderImpl n) {
    setExpiration(n.getExpirationInterval());
    lazyMessageList.putAll(n.getLazyMessageMap());
    setRPCName(n.getRPCUser());
    setService(n.getRPCName());
    setRPCPassword(n.getRPCPassword());
  }

  public final String getCallBackPointer(String object) {
    /**@todo Implement this com.dexels.navajo.document.Header abstract method*/
    throw new java.lang.UnsupportedOperationException("Method getCallBackPointer() not yet implemented.");
  }
  public final void setRPCPassword(String s) {
    myPassword = s;
  }
  public final Object getRef() {
    return toXml(null);
  }
  public final String getRPCUser() {
    return myName;
  }
  public final void setCallBack(String name, String pointer, int percReady, boolean isFinished, String interrupt) {
    /**@todo Implement this com.dexels.navajo.document.Header abstract method*/
  }
  public final String getUserAgent() {
    return "MoZiLLa";
  }
  public final String getHostName() {
    try {
      InetAddress.getLocalHost().getHostName();
    }
    catch (UnknownHostException ex) {
      ex.printStackTrace();
    }
    return "localhost";

  }
  public com.dexels.navajo.document.LazyMessageImpl getLazyMessages() {
    return null;
  }
}
