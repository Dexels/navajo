package com.dexels.navajo.adapter;

import java.sql.Connection;
import com.dexels.navajo.server.Access;
import java.sql.CallableStatement;
import java.sql.*;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class SessionIdentification {

  private static final String oracleSid = "SELECT sid, serial# FROM v$session WHERE client_info = ?";
  private static final String killOracleSession = "ALTER SYSTEM KILL SESSION '?, ?'";

  public static final void killSession(String dbIdentifier, Connection con, Access access) {

  }

  public static final String getSessionIdentification(String dbIdentifier, String datasource, Access access) {

    String sid = null;

    if (dbIdentifier.equals("Oracle") && access != null) {
      SQLMap sql = new SQLMap();
      try {
        sql.load(null, null, access, Dispatcher.getNavajoConfig());
        sql.setDatasource(datasource);
        sql.setQuery(oracleSid);
        sql.setParameter(access.accessID);
        if (sql.getRowCount() > 0) {
          sid = sql.getColumnValue("sid") + "/" + sql.getColumnValue("serial#");
        }
        sql.store();
      }
      catch (Exception ex) {
        ex.printStackTrace(System.err);
        sql.kill();
      }
    }

    return sid;

  }

  public static final void setSessionId(String dbIdentifier, Connection con,
                                        Access access) {

    if (dbIdentifier.equals("Oracle") && access != null && con != null) {
      try {
        CallableStatement cstmt = con.prepareCall(
            "Call DBMS_APPLICATION_INFO.SET_MODULE(?, ?)");
        cstmt.setString(1, Dispatcher.serverId);
        cstmt.setString(2, access.rpcUser + "@" + access.rpcName);
        cstmt.executeUpdate();
        cstmt.close();
        cstmt = con.prepareCall("Call DBMS_APPLICATION_INFO.SET_CLIENT_INFO(?)");
        cstmt.setString(1, access.accessID);
        cstmt.executeUpdate();
        cstmt.close();
      }
      catch (SQLException ex) {
        ex.printStackTrace(System.err);
      }
    }
  }

  public static final void clearSessionId(String dbIdentifier, Connection con) {
    if (dbIdentifier.equals("Oracle") && con != null) {
      try {
        CallableStatement cstmt = con.prepareCall(
            "Call DBMS_APPLICATION_INFO.SET_MODULE(?, ?)");
        cstmt.setString(1, null);
        cstmt.setString(2, null);
        cstmt.executeUpdate();
        cstmt.close();
        cstmt = con.prepareCall("Call DBMS_APPLICATION_INFO.SET_CLIENT_INFO(?)");
        cstmt.setString(1, null);
        cstmt.executeUpdate();
        cstmt.close();
      }
      catch (SQLException ex) {
        ex.printStackTrace(System.err);
      }
    }

  }

}