package com.dexels.navajo.adapter;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version 1.0
 */

import javax.naming.Context;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.sql.*;
import org.dexels.grus.DbConnectionBroker;
import com.dexels.navajo.util.*;

public class SPMap extends SQLMap {

  public String outputParameter;
  public String outputParameterType;

  protected final static int INPUT_PARAM = 0;
  protected final static int OUTPUT_PARAM = 1;
  protected final static int INOUT_PARAM = 2;

  protected ArrayList parameterTypes = new ArrayList();

  protected static HashMap lookupTable = null;

  protected CallableStatement callStatement = null;

  public void load(Context context, Parameters parms, Navajo inMessage, Access access, ArrayList keyList) throws com.dexels.navajo.server.UserException, com.dexels.navajo.mapping.MappableException {
    super.load( context,  parms,  inMessage,  access,  keyList);
     if (lookupTable == null) {
        lookupTable = new HashMap();
        lookupTable.put("VARCHAR", new Integer(java.sql.Types.VARCHAR));
        lookupTable.put("DOUBLE", new Integer(java.sql.Types.DOUBLE));
        lookupTable.put("BIT", new Integer(java.sql.Types.BIT));
        lookupTable.put("INTEGER", new Integer(java.sql.Types.INTEGER));
        lookupTable.put("DATE", new Integer(java.sql.Types.TIMESTAMP));
    }
    //System.out.println("lookupTable = " + lookupTable);
  }

  public ResultSetMap[] getResultSet() throws com.dexels.navajo.server.UserException {

    //System.out.print("TIMING SQLMAP, start query...");

    //long start = System.currentTimeMillis();
    requestCount++;
    ResultSet rs = null;

    try {
    if (!useFixedBroker) {
      if (broker == null) // Create temporary broker if it does not exist.
        broker = createConnectionBroker(driver, url, username, password);
      if (broker == null)
        throw new UserException(-1, "in SQLMap. Could not open database connection [driver = " +
                                  driver + ", url = " + url + ", username = '" + username + "', password = '" + password + "']");
      if (con == null)  { // Create connection if it does not yet exist.
        con = broker.getConnection();
      }
    }
    else {
      if (con == null) { // Create connection if it does not yet exist.
        con = fixedBroker.getConnection();
        if (con != null)
            con.setAutoCommit(autoCommit);
      }
    }

    if (con == null)
        throw new UserException(-1, "in SQLMap. Could not open database connection [driver = " +
                                driver + ", url = " + url + ", username = '" + username + "', password = '" + password + "']");

    //System.out.println("resultSet = " + resultSet);

    if (resultSet == null) {
        if (query != null)
          callStatement = con.prepareCall(query);
        else
          callStatement = con.prepareCall(update);

        //System.out.println("callStatement = " + callStatement.toString());

        //System.out.println("parameters = " + parameters);

        if (parameters != null) {
          for (int i = 0; i < parameters.size(); i++) {
            Object param = parameters.get(i);
            int type = ((Integer) parameterTypes.get(i)).intValue();
            //System.out.println("Setting parameter " + param + ", type = " + type);
            if (type == INPUT_PARAM) {
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
            else {
              int sqlType = ((Integer) lookupTable.get((String) param)).intValue();
              callStatement.registerOutParameter(i+1, sqlType);
              //System.out.println("registered output parameter");
            }
          }
        }
        if (query != null)
          rs = callStatement.executeQuery();
        else
          callStatement.execute();
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
                                 break;
                case Types.TIMESTAMP:
                                if (rs.getTimestamp(i) != null) {
                                   Timestamp ts = rs.getTimestamp(i, c);
                                   long l = ts.getTime();
                                   value = new java.util.Date(l);
                                }
                                break;
                case Types.BIT: value = new Boolean(rs.getBoolean(i));break;
                default: if (rs.getString(i) != null) value = new String(rs.getString(i)); break;
              }
              if (value == null)
                value = new String("null");
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
      //parameters = new ArrayList();
      query = update = null;
      try {
        if (rs != null)
          rs.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    //long end = System.currentTimeMillis();
    //double total = (end - start) / 1000.0;
    //totaltiming += total;
    //System.out.println("finished " + total + " seconds. Average query time: " + (totaltiming/requestCount) + " (" + requestCount + ")");
    return resultSet;
  }

  public  void setQuery(String newQuery) {
    super.setQuery( newQuery);
    parameterTypes = new ArrayList();
  }

  public void setUpdate(String newUpdate) throws com.dexels.navajo.server.UserException {
    super.setUpdate( newUpdate);
    parameterTypes = new ArrayList();
  }

  public void setParameter(Object param) {
    super.setParameter( param);
    parameterTypes.add(new Integer(INPUT_PARAM));
  }

  public Object getOutputParameter() {
    return "";
  }

  public void setOutputParameterType(String type) {
    //System.out.println("in setOutputParameter(), type = " + type);
    super.setParameter( (String) type );
    parameterTypes.add(new Integer(OUTPUT_PARAM));
    //System.out.println("Added output parameter " + (String) type);
  }

  public Object getOutputParameter(Integer i) throws com.dexels.navajo.server.UserException {


    int index = i.intValue();
    //System.out.println("in getOutputParameter("+index+")");
    Object value = null;

    if (callStatement != null) {
       try {
           //System.out.println("parameters = " + parameters);
           String type = (String) parameters.get(index - 1);
           //System.out.println("type = " + type);
           int sqlType = ((Integer) lookupTable.get(type)).intValue();
           //System.out.println("sqlType = " + sqlType);
           java.util.Calendar c = java.util.Calendar.getInstance();
           switch (sqlType) {
              case Types.VARCHAR: value = callStatement.getString(index);break;
              case Types.BIT: value = new Boolean(callStatement.getBoolean(index));break;
              case Types.DATE:
                              if (callStatement.getDate(index) != null) {
                                  Date d = callStatement.getDate(index, c);
                                  long l = d.getTime();
                                  value = new java.util.Date(l);
                                }
                                break;
              case Types.TIMESTAMP:
                              if (callStatement.getTimestamp(index) != null) {
                                  Timestamp ts = callStatement.getTimestamp(index, c);
                                  long l = ts.getTime();
                                  value = new java.util.Date(l);
                              }
                              break;
              case Types.INTEGER: value = new Integer(callStatement.getInt(index));break;
              case Types.DOUBLE: case Types.FLOAT: value = new Double(callStatement.getDouble(index));break;
              default: value = callStatement.getString(index);break;
           }
       } catch (SQLException sqle) {
          sqle.printStackTrace();
          throw new com.dexels.navajo.server.UserException(-1, sqle.getMessage());
       }
       return value;
    } else
      return "";
  }

  public void store() throws com.dexels.navajo.server.UserException, com.dexels.navajo.mapping.MappableException {
    super.store();
    try {
      if (callStatement != null)
        callStatement.close();
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
  }

  public void kill() {
    super.kill();
    try {
      if (callStatement != null)
        callStatement.close();
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
  }



}