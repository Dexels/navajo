

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.server;

import java.sql.Connection;
import org.dexels.grus.DbConnectionBroker;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.LazyMessageImpl;

public final class Access implements java.io.Serializable {

    public java.util.Date created = new java.util.Date();
    public int accessID;
    public int userID;
    public int serviceID;
    public String rpcName = "";
    public String rpcPwd = "";
    public String rpcUser = "";
    public String userAgent;
    public String ipAddress;
    public String hostName;
    public boolean betaUser = false;
    private Dispatcher myDispatcher;

    private Navajo outputDoc;
    private LazyMessageImpl lazyMap;
    private Message currentOutMessage;
    private Object userCertificate;

    public Navajo getOutputDoc() {
      return outputDoc;
    }

    public void setOutputDoc(Navajo n) {
        outputDoc = n;
    }

    public Access(int accessID, int userID, int serviceID, String rpcUser,
            String rpcName, String userAgent, String ipAddress, String hostName, boolean betaUser, Object certificate) {

        this.accessID = accessID;
        this.userID = userID;
        this.serviceID = serviceID;
        this.rpcName = rpcName;
        this.rpcUser = rpcUser;
        this.userAgent = userAgent;
        this.hostName = hostName;
        this.ipAddress = ipAddress;
        this.betaUser = betaUser;
        this.userCertificate = certificate;
    }

    public Access(int accessID, int userID, int serviceID, String rpcUser,
            String rpcName, String userAgent, String ipAddress, String hostName, Object certificate) {
        this.accessID = accessID;
        this.userID = userID;
        this.serviceID = serviceID;
        this.rpcName = rpcName;
        this.rpcUser = rpcUser;
        this.userAgent = userAgent;
        this.hostName = hostName;
        this.ipAddress = ipAddress;
        this.betaUser = false;
        this.userCertificate = certificate;
    }

    protected void setUserCertificate(Object cert) {
      userCertificate = cert;
    }

    public Object getUserCertificate() {
      return userCertificate;
    }

    protected final void setMyDispatcher(Dispatcher d) {
      this.myDispatcher = d;
    }

    public final Dispatcher getDispatcher() {
      return this.myDispatcher;
    }

    public final void setLazyMessages(LazyMessageImpl h) {
      this.lazyMap = h;
    }

    public final LazyMessageImpl getLazyMessages() {
      return this.lazyMap;
    }

    public final Message getCurrentOutMessage() {
     return currentOutMessage;
    }

    public final void setCurrentOutMessage(Message currentOutMessage) {
     this.currentOutMessage = currentOutMessage;
    }
}
