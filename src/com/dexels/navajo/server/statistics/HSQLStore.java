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

  public HSQLStore() {
  }

  private static Connection myConnection = null;

  private static final String insertAccessSQL = "insert into access " +
      "(access_id, webservice, username, totaltime, ip_address, hostname, created) " +
      "values (?, ?, ?, ?, ?, ?, ?)";

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

  private final void addAccess(Access a) {
    if (Dispatcher.getNavajoConfig().dbPath != null) {
      Connection con = createConnection(Dispatcher.getNavajoConfig().dbPath);
      if (con != null) {
        try {
          PreparedStatement ps = con.prepareStatement(insertAccessSQL);
          ps.setString(1, a.accessID);
          ps.setString(2, a.rpcName);
          ps.setString(3, a.rpcUser);
          ps.setInt(4, -1);
          ps.setString(5, a.ipAddress);
          ps.setString(6, a.hostName);
          ps.setDate(7, new java.sql.Date(a.created.getTime()));
          ps.executeUpdate();
        }
        catch (SQLException ex) {
          ex.printStackTrace(System.err);
        }
      }
    }
  }

  public void storeAccess(Access a) {
    /**@todo Implement this com.dexels.navajo.server.statistics.StoreInterface method*/
    throw new java.lang.UnsupportedOperationException("Method storeAccess() not yet implemented.");
  }

  public void finalize() {
    try {
      if (myConnection != null && !myConnection.isClosed()) {
        myConnection.close();
      }
    }
    catch (SQLException ex) {
      ex.printStackTrace(System.err);
    }
  }

}