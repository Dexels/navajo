

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
import com.dexels.navajo.mapping.CompiledScript;
import java.sql.*;
import java.sql.DriverManager;

public final class Access implements java.io.Serializable {

    public java.util.Date created = new java.util.Date();
    public static int accessCount = 0;
    public String accessID = "";
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
    private CompiledScript myScript = null;

    private Navajo outputDoc;
    private LazyMessageImpl lazyMap;
    private Message currentOutMessage;
    private Object userCertificate;

    private static Connection myConnection = null;

    /**
     * Some queries
     * @return
     */

    private static final String insertAccessSQL = "insert into access " +
        "(access_id, webservice, username, totaltime, ip_address, hostname, created) " +
        "values (?, ?, ?, ?, ?, ?, ?)";

    public Navajo getOutputDoc() {
      return outputDoc;
    }

    public void setOutputDoc(Navajo n) {
        outputDoc = n;
    }

    public void setCompiledScript(CompiledScript cs) {
      this.myScript = cs;
    }

    public CompiledScript getCompiledScript() {
      return this.myScript;
    }

    private final Connection createConnection(String db) {
      try {
      if (myConnection == null || !myConnection.isClosed()) {
          Class.forName("org.hsqldb.jdbcDriver");
          System.err.println("Connection to Navajo DB: " + "jdbc:hsqldb:" + db);
          myConnection = DriverManager.getConnection("jdbc:hsqldb:" + db, "sa", "");
        }
      }
      catch (Exception ex) {
         ex.printStackTrace(System.err);
       }

      return myConnection;
    }

    private final void addAccess() {
      if (Dispatcher.getNavajoConfig().dbPath != null) {
        Connection con = createConnection(Dispatcher.getNavajoConfig().dbPath);
        if (con != null) {
          try {
            PreparedStatement ps = con.prepareStatement(insertAccessSQL);
            ps.setString(1, this.accessID);
            ps.setString(2, this.rpcName);
            ps.setString(3, this.rpcUser);
            ps.setInt(4, -1);
            ps.setString(5, this.ipAddress);
            ps.setString(6, this.hostName);
            ps.setDate(7, new java.sql.Date(this.created.getTime()));
            ps.executeUpdate();
          }
          catch (SQLException ex) {
            ex.printStackTrace(System.err);
          }
        }
      }
    }

    public Access(int accessID, int userID, int serviceID, String rpcUser,
            String rpcName, String userAgent, String ipAddress, String hostName,
            boolean betaUser, Object certificate) {

        accessCount++;
        this.accessID = System.currentTimeMillis() + "-" + accessCount;
        this.userID = userID;
        this.serviceID = serviceID;
        this.rpcName = rpcName;
        this.rpcUser = rpcUser;
        this.userAgent = userAgent;
        this.hostName = hostName;
        this.ipAddress = ipAddress;
        this.betaUser = betaUser;
        this.userCertificate = certificate;
        addAccess();
    }

    public Access(int accessID, int userID, int serviceID, String rpcUser,
            String rpcName, String userAgent, String ipAddress, String hostName, Object certificate) {
        accessCount++;
        this.accessID = System.currentTimeMillis() + "-" + accessCount;
        this.userID = userID;
        this.serviceID = serviceID;
        this.rpcName = rpcName;
        this.rpcUser = rpcUser;
        this.userAgent = userAgent;
        this.hostName = hostName;
        this.ipAddress = ipAddress;
        this.betaUser = false;
        this.userCertificate = certificate;
        addAccess();
    }

    protected final void setUserCertificate(Object cert) {
      userCertificate = cert;
    }

    public final Object getUserCertificate() {
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
