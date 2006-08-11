package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class AsyncProxyMap extends AsyncMappable {

  public String method;
  public String username;
  public String password;

  private Navajo outDoc;
  private Navajo inDoc;
  private Access access;

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws com.dexels.navajo.server.UserException, com.dexels.navajo.mapping.MappableException {
    username = access.rpcUser;
    password = access.rpcPwd;
    this.access = access;
    outDoc = inMessage;
  }

  public void setMethod(String name) {
    System.out.println("AsyncProxyMap: in setMethod(), name = " + name);
    this.method = name;
  }

  public void kill() {
  }

  public void afterResponse() {
    System.out.println("AsyncProxyMap: in afterResponse()");
    access.setOutputDoc(inDoc);
  }

  public int getPercReady() {
    System.out.println("AsyncProxyMap: in getPercReady()");
    return 0;
  }

  public void beforeResponse(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) {
    access.setOutputDoc(inDoc);
    System.out.println("AsyncProxyMap: in beforeResponse()");
    System.out.println("INDOC = " + access.getOutputDoc());
  }

  public void store() throws com.dexels.navajo.server.UserException, com.dexels.navajo.mapping.MappableException {
    System.out.println("AsyncProxyMap: in store()");
  }

  public void run() throws com.dexels.navajo.server.UserException {
     Header h = outDoc.getHeader();
     if (h == null) {
          h = NavajoFactory.getInstance().createHeader(outDoc, method, username, password, -1);
          outDoc.addHeader(h);
     } else {
          h.setRPCName(method);
          h.setRPCPassword(password);
          h.setRPCUser(username);
    }

    try {
      System.err.println("Calling method: " + method);
      outDoc.write(System.err);
      inDoc = access.getDispatcher().handle(outDoc);
    } catch (Exception e) {
      e.printStackTrace();
      throw new UserException(-1, e.getMessage());
    }
    setIsFinished();
  }

  public void afterRequest() throws UserException {
    System.out.println("AsyncProxyMap: in afterRequest()");
    if (method == null)
      throw new UserException(-1, "AsyncProxyMap: specify a method");
  }
}