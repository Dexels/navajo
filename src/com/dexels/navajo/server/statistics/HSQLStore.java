package com.dexels.navajo.server.statistics;

import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import java.sql.*;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class HSQLStore implements StoreInterface {

  private static boolean ready = false;

  public HSQLStore(final String path) {
    try {
      Class.forName("org.hsqldb.jdbcDriver");
      new Thread(
        new Runnable() {
          public void run() {
            org.hsqldb.Server hsqlServer = new org.hsqldb.Server();
            System.err.println("HSQL URL = " + path);
            String [] arguments = {"-database", path};
            HSQLStore.ready = true;
            hsqlServer.main(arguments);
          }
        }
      ).start();
    }
    catch (ClassNotFoundException ex) {
      ex.printStackTrace(System.err);
    }
  }

  /**
   *
ACCESS_ID
WEBSERVICE
USERNAME
TOTALTIME
PARSETIME
AUTHORISATIONTIME
REQUESTENCODING
COMPRESSEDRECV
COMPRESSEDSND
IP_ADDRESS
HOSTNAME
CREATED

   */
  private static final String insertAccessSQL = "insert into access " +
      "(access_id, webservice, username, totaltime, parsetime, authorisationtime, requestsize, requestencoding, compressedrecv, compressedsnd, ip_address, hostname, created) " +
      "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  private final Connection createConnection(String db) {

    while (!ready) {
      try {
        System.err.println("Waiting for store to become ready.....");
        Thread.sleep(5000);
      }
      catch (InterruptedException ex1) {
      }
    }

    Connection myConnection = null;
    try {
      if (myConnection == null || !myConnection.isClosed()) {
        System.err.println("Connection to Navajo DB: " + "jdbc:hsqldb:" + db);
        myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "sa", "");
      }
    }
     catch (Exception ex) {
       ex.printStackTrace(System.err);
     }
     return myConnection;
   }

  private final void addAccess(Access a) {
    if (Dispatcher.getNavajoConfig().dbPath != null) {
      Connection con = createConnection(Dispatcher.getNavajoConfig().dbPath);
      if (con != null) {
        try {
          PreparedStatement ps = con.prepareStatement(insertAccessSQL);
          ps.setString(1, a.accessID);
          ps.setString(2, a.rpcName);
          ps.setString(3, a.rpcUser);
          ps.setInt(4, a.getTotaltime());
          ps.setInt(5, a.parseTime);
          ps.setInt(6, a.authorisationTime);
          ps.setInt(7, a.contentLength);
          ps.setString(8, a.requestEncoding);
          ps.setBoolean(9, a.compressedReceive);
          ps.setBoolean(10, a.compressedSend);
          ps.setString(11, a.ipAddress);
          ps.setString(12, a.hostName);
          ps.setTimestamp(13, new java.sql.Timestamp(a.created.getTime()));
          ps.executeUpdate();
          ps.close();
          con.close();
        }
        catch (SQLException ex) {
          ex.printStackTrace(System.err);
        }
      }
    }
  }

  public void storeAccess(Access a) {
    addAccess(a);
  }

}