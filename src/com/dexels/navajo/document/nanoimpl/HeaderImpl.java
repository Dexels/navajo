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
import java.io.*;

public final class HeaderImpl
    extends BaseNode
    implements Header {

  private String myName;
  private String myPassword;
  private String myService;
  private String myIp;
  private String myLazyMessage = null;
  private long expiration = -1;
  private TreeMap lazyMessageList = new TreeMap();

  private boolean isFinished = false;
  private String myInterrupt = null;
  private String myCallbackName = null;
  private String myCallbackPointer = null;
  private int percReady = -1;

  public HeaderImpl(com.dexels.navajo.document.Navajo n, String user,
                    String password, String service) {
    super(n);
    setIdentification(user, password, service);
  }

  public HeaderImpl(com.dexels.navajo.document.Navajo n) {
    super(n);
  }

  public final void setExpiration(long i) {
    expiration = i;
  }

  public final void addLazyMessagePath(String path, int startIndex,
                                       int endIndex) {
    LazyMessagePath lmp = NavajoFactory.getInstance().createLazyMessagePath(
        getRootDoc(), path, startIndex, endIndex);
    lazyMessageList.put(path, lmp);
    /** @todo repair this function*/
//    throw new UnsupportedOperationException();
  }

//  public ArrayList getAllLazyMessages() {
//    return lazyMessageList;
//  }

  public final void setIdentification(String user, String password,
                                      String service) {
    myName = user;
    myPassword = password;
    myService = service;
  }

  public final void setService(String service) {
    myService = service;
  }

  public final LazyMessagePath getLazyMessagePath(String path) {
    /** @todo repair this function*/
//    throw new UnsupportedOperationException();
//
    return (LazyMessagePath) lazyMessageList.get(path);
  }

  public final void fromXml(XMLElement e) {
    Enumeration enum = e.enumerateChildren();
//    System.err.println("\n\nPARSING TO HEADER: ");
//    System.err.println(e.toString());
//      System.err.println("\n\nEND OF PARSING HEADER\n");
    while (enum.hasMoreElements()) {
      XMLElement child = (XMLElement) enum.nextElement();
      if (child.getName().equals("transaction")) {
        setIdentification(child.getStringAttribute("rpc_usr"),
                          child.getStringAttribute("rpc_pwd"),
                          child.getStringAttribute("rpc_name"));
        if (child.getStringAttribute("expiration_interval") != null &&
            !child.getStringAttribute("expiration_interval").equals("")) {
          setExpiration(Long.parseLong(child.getStringAttribute(
              "expiration_interval")));
        }
      }
      if (child.getName().equals("callback")) {
//        System.err.println("Parsing callback");
        Enumeration enum2 = child.enumerateChildren();
        while (enum2.hasMoreElements()) {
          XMLElement child2 = (XMLElement) enum2.nextElement();
          if (child2.getName().equals("object")) {
//            System.err.println("Parsing object");
            setCallBack(child2.getStringAttribute("name"),
                        child2.getStringAttribute("ref"),
                        child2.getIntAttribute("perc_ready"),
                        child2.getBooleanAttribute("finished", "true", "false", false),
                        child2.getStringAttribute("interrupt"));
          }
        }
      }
    }
  }

  public final XMLElement toXml(XMLElement parent) {

//    System.err.println("Finished: "+isFinished);
//    System.err.println("PercReady: "+percReady);
//    System.err.println("Name: "+myCallbackName);
//    System.err.println("pointer: "+myCallbackPointer);
    try {
      XMLElement header = new CaseSensitiveXMLElement();
//      System.err.println("MY USERNAME: "+getRPCUser());
      header.setName("header");

      XMLElement transaction = new CaseSensitiveXMLElement();
      transaction.setName("transaction");
      if (myService != null) {
        transaction.setAttribute("rpc_name", myService);
      }
      if (myName != null) {
        transaction.setAttribute("rpc_usr", myName);
      }
      if (myPassword != null) {
        transaction.setAttribute("rpc_pwd", myPassword);
      }
      transaction.setAttribute("expiration_interval", this.expiration + "");
      Iterator it = lazyMessageList.values().iterator();
      while (it.hasNext()) {
        LazyMessagePath path = (LazyMessagePath) it.next();
        transaction.addChild( ( (LazyMessagePathImpl) path).toXml(transaction));
      }

      header.addChild(transaction);
//      if (myCallbackPointer!=null) {
        XMLElement callback = new CaseSensitiveXMLElement();
        callback.setName("callback");
//      header.addChild(callback);
        XMLElement obj = new CaseSensitiveXMLElement();
        obj.setName("object");
        transaction.addChild(callback);
        if (myCallbackPointer!=null) {
          obj.setAttribute("finished", "" + isFinished);
        }
        if (myCallbackName!=null) {
          obj.setAttribute("name",myCallbackName);
        }
        if (myCallbackPointer!=null) {
          obj.setAttribute("ref",myCallbackPointer);
        }
        if (myCallbackPointer!=null) {
          obj.setIntAttribute("perc_ready", percReady);
        }
        callback.addChild(obj);
//      }

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
    throw new java.lang.UnsupportedOperationException(
        "Method getCallBackInterupt() not yet implemented.");
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

  public void setExpirationInterval(long l) {
    expiration = l;
  }

  public Map getLazyMessageMap() {
    return lazyMessageList;
  }

  public final void merge(HeaderImpl n) {
    setExpiration(n.getExpirationInterval());
    lazyMessageList.putAll(n.getLazyMessageMap());
    setRPCUser(n.getRPCUser());
    setService(n.getRPCName());
    setRPCPassword(n.getRPCPassword());
  }

  public final String getCallBackPointer(String object) {
    return myCallbackPointer;
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

  public final void setCallBack(String name, String pointer, int percReady,
                                boolean isFinished, String interrupt) {
    this.isFinished = isFinished;
    this.myInterrupt = interrupt;
    this.myCallbackName = name;
    this.myCallbackPointer = pointer;
    this.percReady = percReady;
//    System.err.println("Finished: "+isFinished);
//    System.err.println("PercReady: "+percReady);
//    System.err.println("Name: "+name);
//    System.err.println("pointer: "+pointer);
  }

  public final void setCallBackInterrupt(String interrupt) {
    this.myInterrupt = interrupt;
  }

  public final String getUserAgent() {
    return "MoZiLLa";
  }

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

  public int getCallBackProgress() {
    return percReady;
  }

  /**
   * Returns whether the asynchronous server process has completed
   */
  public boolean isCallBackFinished() {
    return isFinished;
  }

}
