package com.dexels.navajo.adapter;

import javax.naming.Context;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.HashMap;
import java.sql.*;
import org.dexels.grus.DbConnectionBroker;
import com.dexels.navajo.util.*;
import com.dexels.navajo.xml.XMLutils;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 *
 * $Id$
 *
 */

/**
 * This built in mappable class should be used as a general class for using arbitrary SQL queries and processing the ResultSet
 *
 * SQLMap can use multiple datasource which are defined in the sqlmap.xml configuration file.
 * The configuration file can contain a "default" datasource in which case datasources need to be defined in the script
 * (using the datasource field!)>
 * An example configuration file:
 *
 * <tml>
 * <message name="datasources">
 *  <message name="default">
 *     <property name="driver" value="com.sybase.jdbc2.jdbc.SybDriver"/>
 *     <property name="url" value="jdbc:sybase:Tds:cerberus.knvb.nl:5001/knvb"/>
 *     <property name="username" value="dexels"/>
 *     <property name="password" value="dexels"/>
 *     <property name="logfile" value="default.sqlmap"/>
 *     <property name="refresh" type="float" value="0.1"/>
 *     <property name="min_connections" type="integer" value="10"/>
 *     <property name="max_connections" type="integer" value="50"/>
 *  </message>
 * </message>
 * </tml>
 *
 * Other datasource are defined as "default" except with a unique message name identifying the datasource.
 *
 * A single SQLMap instance can be used to run multiple queries. If a single transaction context is required multiple SQLMap instances
 * can be used if the transactionContext is the same.
 *
 * TODO
 *
 * Use property "timeout" to solve busy waiting bug (see below)
 *
 * BUGS
 *
 * Change DbConnectionBroker such that it throws an exception if after some specified timeout a connection cannot be made.
 * Currently the connectionbroker keeps waiting until a connection can be created. This leads to deadlocks if nested SQLMap
 * instances are used!!
 *
 */

public class SQLMap implements Mappable {

  protected final static int INFINITE = -1;

  public String driver;
  public String url;
  public String username;
  public String password;
  public String update;
  public String query;
  public boolean doUpdate;
  public boolean autoCommit = true;
  public int transactionIsolation = -1;
  public int rowCount = 0;
  public int viewCount = 0;
  public int remainCount = 0;
  public ResultSetMap [] resultSet = null;
  public int startIndex = 1;
  public int endIndex = INFINITE;
  public Object parameter;
  public Object columnValue;
  public int resultSetIndex = 0;
  public int transactionContext = -1;

  protected DbConnectionBroker broker = null;
  protected Connection con = null;
  protected PreparedStatement statement = null;
  protected ArrayList parameters = null;

  protected static HashMap fixedBroker = null;
  public String datasource = "default";

  protected static double totaltiming = 0.0;
  protected static int requestCount = 0;

  private static Navajo configFile = null;

  private static HashMap transactionContextMap = null;

  private int connectionId = -1;

  class FinalizeThread extends Thread {
    private DbConnectionBroker broker = null;

    public FinalizeThread(DbConnectionBroker broker) {
      this.broker = broker;
    }

    public void run() {
      broker.destroy();
    }
  }

  private void createDataSource(Message body, NavajoConfig config) throws UserException, NavajoException {

     String dataSourceName = body.getName();
     driver = NavajoUtils.getPropertyValue(body, "driver", true);
     url = NavajoUtils.getPropertyValue(body, "url", true);
     username = NavajoUtils.getPropertyValue(body, "username", true);
     password = NavajoUtils.getPropertyValue(body, "password", true);
     String logFile = config.getRootPath() + "/log/" + NavajoUtils.getPropertyValue(body, "logfile", true);
     double refresh = Double.parseDouble(NavajoUtils.getPropertyValue(body, "refresh", true));
     String min = NavajoUtils.getPropertyValue(body, "min_connections", false);
     int minConnections = (min.equals("")) ? 5 : Integer.parseInt(min);
     String max = NavajoUtils.getPropertyValue(body, "max_connections", false);
     int maxConnections = (max.equals("")) ? 20 : Integer.parseInt(max);

     DbConnectionBroker myBroker = null;

     myBroker = createConnectionBroker(driver, url, username, password, minConnections, maxConnections, logFile,refresh);
     fixedBroker.put(dataSourceName, myBroker);
  }

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
    // Check whether property file sqlmap.properties exists.

    //System.out.println("in SQLMap(), load(), config = " + config);
    //System.out.println("path = " + config.getConfigPath());
    try {

      if (transactionContextMap == null)
        transactionContextMap = new HashMap();

      if (configFile == null) {
          configFile = XMLutils.createNavajoInstance(config.getConfigPath()+"sqlmap.xml");
          //System.out.println("configFile = " + configFile);

          // If propery file exists create a static connectionbroker that can be accessed by multiple instances of
          // SQLMap!!!
          if (fixedBroker == null) {
            fixedBroker = new HashMap();
          }
          // Get other data sources.
          ArrayList all = configFile.getMessages("/datasources/.*");
          for (int i = 0; i < all.size(); i++) {
            Message body = (Message) all.get(i);
            createDataSource(body, config);
          }

           if (fixedBroker.get("default") == null)
            throw new UserException(-1, "in SQLMap. Could not create default broker [driver = " +
                                    driver + ", url = " + url + ", username = '" + username + "', password = '" + password + "']");
      }
      rowCount = 0;
    } catch (NavajoException ne) {
      ne.printStackTrace();
      throw new MappableException(ne.getMessage());
    } catch (java.io.FileNotFoundException fnfe) {
      fnfe.printStackTrace();
      throw new MappableException("Could not load configuration file for SQLMap object: " + fnfe.getMessage());
    }
  }

  public void setDatasource(String s) {
    datasource = s;
  }

  public void kill() {
    //System.out.println("SQLMap kill() called");
    try {
        if (!autoCommit) {
            if (con != null)
              con.rollback();
        }
        if (transactionContext == -1) {
          if (con != null) {
            transactionContextMap.remove(connectionId+"");
            ((DbConnectionBroker) fixedBroker.get(datasource)).freeConnection(con);
          }
        }
      } catch (SQLException sqle) {
        sqle.printStackTrace();
      }
  }

  public void store() throws MappableException, UserException {
    //System.out.println("SQLMap store() called");
    // Kill temporary broker.
    // If part of transaction context, do not free connection or commit changes yet.
    if (transactionContext == -1) {
        if (con != null) {
          try {
            if (!autoCommit)
                con.commit();
          } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw new UserException(-1, sqle.getMessage());
          }
          transactionContextMap.remove(connectionId+"");
          ((DbConnectionBroker) fixedBroker.get(datasource)).freeConnection(con);
          //System.out.println("TOTAL OPEN CONNECTIONS = " + fixedBroker.getUseCount());
      }
    }

    if (broker != null) {
      FinalizeThread t = new FinalizeThread(broker);
      t.start();
    }
  }

  public void setTransactionIsolationLevel(int j) {
    transactionIsolation = j;
  }

  public void setAutoCommit(boolean b) {
    this.autoCommit = b;
  }

  public void setTransactionContext(int i) throws UserException {
    this.transactionContext = i;
    // Get a shared connection from the transactionContextMap.
    //System.out.println("in setTransactionContex(), id = " + i);
    con = (Connection) this.transactionContextMap.get(i+"");
    if (con == null)
      throw new UserException(-1, "Invalid transaction context set");
  }

  public int getRowCount() {
    return this.rowCount;
  }

  public int getViewCount() {
    return this.viewCount;
  }

  public int getRemainCount() {
    return this.remainCount;
  }

  public void setRowCount(int i) {
    this.rowCount = i;
  }

  public void setUpdate(String newUpdate) throws UserException {
    update = newUpdate;
    //System.out.println("update = " + update);
    this.resultSet = null;
    parameters = new ArrayList();
  }

  public void setDoUpdate(boolean doit) throws UserException {
    this.getResultSet();
  }

  public void setResultSetIndex(int index) {
    this.resultSetIndex = index;
  }

  public Object getColumnValue() throws UserException {
    throw new UserException(-1, "Use $columnValue('[name of the column]')");
  }

  public Object getColumnValue(String columnName) throws UserException {
    if (resultSet == null)
      getResultSet();
    ResultSetMap rm = resultSet[resultSetIndex];
    return rm.getColumnValue(columnName);
  }

  /**
   * Use this method to define a new query.
   * All parameters used by a previous query are removed.
   * replace " characters with ' characters.
   */
  public void setQuery(String newQuery) {
    query = newQuery.replace('"', '\'');
    //System.out.println("query =tp " + query);
    this.resultSet = null;
    parameters = new ArrayList();
  }

  public void setParameter(Object param) {
    //System.out.println("in setParameter(), param = " + param);
    if (parameters == null)
      parameters = new ArrayList();
    //System.out.println("adding parameter: " + param);
    if ((param != null) && (param instanceof String) && (((String) param).indexOf(";") != -1)) {
      java.util.StringTokenizer tokens = new java.util.StringTokenizer((String) param, ";");
      while (tokens.hasMoreTokens()) {
        parameters.add(tokens.nextToken());
      }
    } else {
      parameters.add(param);
      //System.out.println("added parameter");
    }
  }

  protected static synchronized DbConnectionBroker createConnectionBroker(String driver, String url, String username, String password,
                                                                          int min, int max, String logFile, double refreshRate) throws UserException {
    DbConnectionBroker db = null;
    try {
      db = new DbConnectionBroker(driver, url, username, password, min, max, logFile, refreshRate);
    } catch (Exception e) {
      e.printStackTrace();
      throw new UserException(-1, "Could not create connectiobroker: " + "[driver = " +
                                  driver + ", url = " + url + ", username = '" + username + "', password = '" + password + "']:" + e.getMessage());
    }
    //System.out.println("Created connection broker for url: " + url);
    return db;
  }

  protected String getType(int i) {
    switch (i) {
      case java.sql.Types.DOUBLE: return "DOUBLE";
      case Types.FLOAT: return "FLOAT";
      case Types.INTEGER: return "INTEGER";
      case Types.DATE: return "DATE";
      case Types.VARCHAR: return "VARCHAR";
      case Types.BIT: return "BOOLEAN";
      case Types.TIME: return "TIME";
      case Types.TIMESTAMP: return "TIMESTAMP";
      case Types.BIGINT: return "BIGINT";
      case Types.DECIMAL: return "DECIMAL";
      case Types.NULL: return "NULL";
      case Types.NUMERIC: return "NUMERIC";
      case Types.OTHER: return "OTHER";
      case Types.REAL: return "REAL";
      case Types.SMALLINT: return "SMALLINT";
      case Types.BLOB: return "BLOB";
      case Types.DISTINCT: return "DISTINCT";
      case Types.STRUCT: return "STRUCT";
      case Types.JAVA_OBJECT: return "JAVA_OBJECT";
      case Types.TINYINT: return "TINYINT";
      default: return "UNSUPPORTED: " + i;
    }
  }

  private void createConnection() throws SQLException {
     if (con == null) { // Create connection if it does not yet exist.
        con = ((DbConnectionBroker) fixedBroker.get(datasource)).getConnection();
        connectionId = con.hashCode();
        System.out.println("id for connection: " + connectionId);
        transactionContextMap.put(connectionId+"", con);
        if (con != null) {
            con.setAutoCommit(autoCommit);
            if (transactionIsolation != -1)
              con.setTransactionIsolation(transactionIsolation);
        }
      }
  }

  public int getTransactionContext() throws UserException {
    try {
      createConnection();
    } catch (SQLException sqle) {
      sqle.printStackTrace();
      throw new UserException(-1, sqle.getMessage());
    }
    return connectionId;
  }

  /**
   * NOTE: DO NOT USE THIS METHOD ON LARGE RESULTSETS WITHOUT SETTING ENDINDEX.
   *
   */

  public ResultSetMap [] getResultSet() throws UserException {

    //System.out.print("TIMING SQLMAP, start query...");
    //long start = System.currentTimeMillis();
    requestCount++;
    ResultSet rs = null;


    try {

      createConnection();

    if (con == null)
        throw new UserException(-1, "in SQLMap. Could not open database connection [driver = " +
                                driver + ", url = " + url + ", username = '" + username + "', password = '" + password + "']");

    if (resultSet == null) {
        if (query != null)
          statement = con.prepareStatement(query);
        else
          statement = con.prepareStatement(update);

        if (parameters != null) {
          //System.out.println("parameters = " + parameters);
          for (int i = 0; i < parameters.size(); i++) {
            Object param = parameters.get(i);
            //System.out.println("parameter " + i + " = " + param);
            if (param == null)
                statement.setNull(i+1, Types.OTHER);
            if (param instanceof String)
                statement.setString(i+1, (String) param);
            else if (param instanceof Integer)
                statement.setInt(i+1, ((Integer) param).intValue());
            else if (param instanceof Double)
                statement.setDouble(i+1, ((Double) param).doubleValue());
            else if (param instanceof java.util.Date) {
                java.sql.Date sqlDate = new java.sql.Date(((java.util.Date) param).getTime());
                statement.setDate(i+1, sqlDate);
            } else if (param instanceof Boolean) {
                statement.setBoolean(i+1, ((Boolean) param).booleanValue());
            }
          }
        }
        if (query != null)
          rs = statement.executeQuery();
        else
          statement.executeUpdate();
      }

      if (rs != null) {
        ResultSetMetaData meta = rs.getMetaData();
        int columns = meta.getColumnCount();
        ArrayList dummy = new ArrayList();
        int index = 1;
        remainCount = 0;
        while (rs.next()) {
         if ((index >= startIndex) && ((endIndex == INFINITE) || (index <= endIndex))) {
           ResultSetMap rm = new ResultSetMap();
           for (int i = 1; i < (columns + 1); i++) {
              String param = meta.getColumnName(i);
              int type = meta.getColumnType(i);
              //System.out.println(param + " has type " + getType(type));
              Object value = null;
              java.util.Calendar c = java.util.Calendar.getInstance();
              if (rs.getString(i) != null) {
                switch (type) {
                  case Types.INTEGER: case Types.SMALLINT: case Types.TINYINT: value = new Integer(rs.getInt(i)); break;
                  case Types.VARCHAR: if (rs.getString(i) != null) value = new String(rs.getString(i)); break;
                  case Types.FLOAT: case Types.DOUBLE: value = new Double(rs.getString(i)); break;
                  case Types.DATE:
                                   if (rs.getDate(i) != null) {
                                      Date d = rs.getDate(i, c);
                                      long l = d.getTime();
                                      value = new java.util.Date(l);
                                 }
                                 //System.out.println("DATE value : " + value);
                                   break;
                  case Types.TIMESTAMP:
                                  if (rs.getTimestamp(i) != null) {
                                     Timestamp ts = rs.getTimestamp(i, c);
                                     long l = ts.getTime();
                                     value = new java.util.Date(l);
                                  }
                                  //System.out.println("TIMESTAMP value : " + value);
                                  break;
                  case Types.BIT: value = new Boolean(rs.getBoolean(i));break;
                  default: if (rs.getString(i) != null) value = new String(rs.getString(i)); break;
                }
              } else {
                //System.out.println(param + "=" + value);
              }
              //if (value == null)
              //  value = new String("");
              rm.values.put(param, value);
           }
           dummy.add(rm);
           viewCount++;
         } else if (index >= startIndex) {
           remainCount++;
         }
         rowCount++;
         index++;
        }
        resultSet = new ResultSetMap[dummy.size()];
        resultSet = (ResultSetMap []) dummy.toArray(resultSet);
      }
    } catch (SQLException sqle) {
      sqle.printStackTrace();
      throw new UserException(-1, sqle.getMessage());
    } finally {
      parameters = new ArrayList();
      query = update = null;
      try {
        if (rs != null) {
          rs.close();
          rs = null;
        }
        if (statement != null) {
          statement.close();
          statement = null;
        }
      } catch (Exception e) {
        e.printStackTrace();
        throw new UserException(-1, e.getMessage());
      }
    }
    //long end = System.currentTimeMillis();
    //double total = (end - start) / 1000.0;
    //totaltiming += total;
    //System.out.println("finished " + total + " seconds. Average query time: " + (totaltiming/requestCount) + " (" + requestCount + ")");
    return resultSet;
  }

  public void setStartIndex(int newStartIndex) {
    startIndex = newStartIndex;
  }

  public int getStartIndex() {
    return startIndex;
  }
  public void setEndIndex(int newEndIndex) {
    endIndex = newEndIndex;
  }
  public int getEndIndex() {
    return endIndex;
  }
}
