package com.dexels.navajo.document;
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
import nanoxml.*;
import java.util.*;

public class Header extends BaseNode {

  private String myName;
  private String myPassword;
  private String myService;
  private String myLazyMessage = null;
  private int expiration = -1;
  private TreeMap lazyMessageList = new TreeMap();

  public Header(Navajo n, String user, String password, String service) {
    super(n);
    setIdentification(user,password,service);
  }

  public Header(Navajo n) {
    super(n);
  }

  public void setExpiration(int i) {
    expiration = i;
  }

  public void addLazyMessage(String path, int startIndex, int endIndex) {
    LazyMessagePath lmp = new LazyMessagePath(getRootDoc(), path,startIndex,endIndex);
    lazyMessageList.put(path,lmp);
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
    return (LazyMessagePath)lazyMessageList.get(path);
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
//      if (myLazyMessage!=null) {
//        transaction.setAttribute("lazymessage",myLazyMessage);
//      }
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
}
