

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

public class Access implements java.io.Serializable {

    public int accessID;
    public int userID;
    public int serviceID;
    public String rpcName;
    public String rpcPwd;
    public String rpcUser;
    public String userAgent;
    public String ipAddress;
    public String hostName;
    public boolean betaUser = false;
    private Dispatcher myDispatcher;

    private Navajo outputDoc;
    private LazyMessageImpl lazyMap;
    private Message currentOutMessage;

    public Navajo getOutputDoc() {
      return outputDoc;
    }

    public void setOutputDoc(Navajo n) {
        outputDoc = n;
    }

    public Access(int accessID, int userID, int serviceID, String rpcUser,
            String rpcName, String userAgent, String ipAddress, String hostName, boolean betaUser) {

        this.accessID = accessID;
        this.userID = userID;
        this.serviceID = serviceID;
        this.rpcName = rpcName;
        this.rpcUser = rpcUser;
        this.userAgent = userAgent;
        this.hostName = hostName;
        this.ipAddress = ipAddress;
        this.betaUser = betaUser;
    }

    public Access(int accessID, int userID, int serviceID, String rpcUser,
            String rpcName, String userAgent, String ipAddress, String hostName) {
        this.accessID = accessID;
        this.userID = userID;
        this.serviceID = serviceID;
        this.rpcName = rpcName;
        this.rpcUser = rpcUser;
        this.userAgent = userAgent;
        this.hostName = hostName;
        this.ipAddress = ipAddress;
        this.betaUser = false;

    }

    protected void setMyDispatcher(Dispatcher d) {
      this.myDispatcher = d;
    }

    public Dispatcher getDispatcher() {
      return this.myDispatcher;
    }

    public void setLazyMessages(LazyMessageImpl h) {
      this.lazyMap = h;
    }

    public LazyMessageImpl getLazyMessages() {
      return this.lazyMap;
    }

    public Message getCurrentOutMessage() {
     return currentOutMessage;
    }

    public void setCurrentOutMessage(Message currentOutMessage) {
     this.currentOutMessage = currentOutMessage;
    }
}
