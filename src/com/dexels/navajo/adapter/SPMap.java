package com.dexels.navajo.adapter;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version 1.0
 *
 * $Id$
 *
 *
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.*;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.logger.*;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.Memo;

public class SPMap extends SQLMap {

  public String outputParameter;
  public String outputParameterType;

  protected final static int INPUT_PARAM = 0;
  protected final static int OUTPUT_PARAM = 1;
  protected final static int INOUT_PARAM = 2;

  protected ArrayList parameterTypes = new ArrayList();

  protected static HashMap lookupTable = null;

  protected CallableStatement callStatement = null;

  public void load(Parameters parms, Navajo inMessage, Access access,
                   NavajoConfig config) throws com.dexels.navajo.server.
      UserException, com.dexels.navajo.mapping.MappableException {

    super.load(parms, inMessage, access, config);
    if (lookupTable == null) {
      lookupTable = new HashMap();
      lookupTable.put("VARCHAR", new Integer(java.sql.Types.VARCHAR));
      lookupTable.put("DOUBLE", new Integer(java.sql.Types.DOUBLE));
      lookupTable.put("BIT", new Integer(java.sql.Types.BIT));
      lookupTable.put("INTEGER", new Integer(java.sql.Types.INTEGER));
      lookupTable.put("TINYINT", new Integer(java.sql.Types.TINYINT));
      lookupTable.put("DATE", new Integer(java.sql.Types.TIMESTAMP));
      lookupTable.put("SMALLINT", new Integer(java.sql.Types.SMALLINT));
      lookupTable.put("NUMBER", new Integer(java.sql.Types.NUMERIC));
    }
    // System.out.println("lookupTable = " + lookupTable);
  }

  /**
   * Determine SP parameter type from metadata (HIGHLY EXPERIMENTAL BUT NECCESSARY TO SUPPORT NULL VALUES IN SP PARAMS!
   *
   * @param index
   * @return
   */
  private final int getSpParameterType(String spName, int parameterIndex) {

    // ONLY INCREMENT THIS FOR SP's WITH OUTPUT PARAMETER, TODO: PARAMETERIZE THIS!!!!!!!!!!!!!!!!!!!!
    parameterIndex++;
    return Types.VARCHAR;

//    try {
//
//      if (spName.equals("")) {
//        return Types.VARCHAR;
//      }
//
//      int type = Types.VARCHAR;
//
//      DatabaseMetaData md = con.getMetaData();
//      // Sybase expects a ;1 after the procudure name.....
//      ResultSet rs = md.getProcedureColumns(null, null, spName + ";1", null);
//      int index = 1;
//      // String sType = "";
//      boolean found = false;
//
//      while (rs.next() && !found) {
//        type = rs.getInt("DATA_TYPE");
//        if (index == parameterIndex) {
//          found = true;
//          break;
//        }
//        else {
//          index++;
//        }
//      }
//      rs.close();
//
//      return type;
//    }
//    catch (SQLException sqle) {
//      return Types.VARCHAR;
//    }
  }

  protected ResultSetMap[] getResultSet(boolean updateOnly) throws com.dexels.navajo.server.UserException {

    if (debug) {
      System.out.print("TIMING SPMAP, start query... : " + update);

    }
    long start = System.currentTimeMillis();

    requestCount++;
    ResultSet rs = null;

    try {

      createConnection();

      if (con == null) {
        throw new UserException( -1,
            "in SQLMap. Could not open database connection [driver = " + driver +
                                ", url = " + url + ", username = '" + username +
                                "', password = '" + password + "']");
      }

      if (resultSet == null) {
        String spName = "";

        if (query != null) {
          callStatement = con.prepareCall(query);
          if (query.indexOf("Call") != -1 && query.indexOf("(") != -1) {
            spName = query.substring(query.indexOf("Call") + 5,
                                     query.indexOf("("));
          }
        }
        else {
          callStatement = con.prepareCall(update);
          if (update.indexOf("Call") != -1
              && update.indexOf("(") != -1) {
            spName = update.substring(update.indexOf("Call") + 5,
                                      update.indexOf("("));
          }
        }

        if (debug) {
          System.out.println("callStatement = " + callStatement.toString());
        }
        if (debug) {
          System.out.println("parameters = " + parameters);

        }
        if (parameters != null) {

          int spIndex = 0;

          for (int i = 0; i < parameters.size(); i++) {
            Object param = parameters.get(i);
            int type = ( (Integer) parameterTypes.get(i)).intValue();

            if (debug) {
              System.out.println("Setting parameter: " + param + "(" +
                                 (param != null ? param.getClass().toString() :
                                  "") + "), type = " + type);

            }
            if (type == INPUT_PARAM) {
              spIndex++;
              if (param == null) {
                callStatement.setNull(i + 1, Types.VARCHAR );//getSpParameterType(spName, spIndex));
              }
              else
              if (param instanceof String) {
                callStatement.setString(i + 1, (String) param);
              }
              else if (param instanceof Integer) {
                callStatement.setInt(i + 1, ( (Integer) param).intValue());
              }
              else if (param instanceof Double) {
                // System.out.println(i + " : param instanceof Double");
                callStatement.setDouble(i + 1, ( (Double) param).doubleValue());
              }
              else if (param instanceof java.util.Date) {
                java.sql.Timestamp timeStamp = new java.sql.Timestamp( ( (java.
                    util.Date) param).getTime());
                callStatement.setTimestamp(i + 1, timeStamp);
              }
              else if (param instanceof Boolean) {
                callStatement.setBoolean(i + 1, ( (Boolean) param).booleanValue());
              }
              else if (param instanceof ClockTime) {
                java.util.Date dValue = ((ClockTime) param).dateValue();
                java.sql.Timestamp timeStamp = new java.sql.Timestamp(dValue.getTime());
                callStatement.setTimestamp(i + 1, timeStamp);
              }
              else if (param instanceof Money) {
                callStatement.setDouble(i + 1, ((Money) param).doubleValue());
              }
              else if (param instanceof Memo) {
                callStatement.setString(i + 1, ( (Memo) param).toString());
              }
              else if (param instanceof Binary) {
                // Following code is copied from SQLMap
                if (debug) {
                  System.err.println("TRYING TO INSERT A BLOB....");
                }
                byte[] data = ( (Binary) param).getData();

                if ( data != null ) {
                  // NOTE: THIS IS ORACLE SPECIFIC!!!!!!!!!!!!!!!!!!
                  oracle.sql.BLOB blob = oracle.sql.BLOB.createTemporary(this.
                      con, false, oracle.sql.BLOB.DURATION_SESSION);
                  blob.open(oracle.sql.BLOB.MODE_READWRITE);
                  blob.putBytes(1, data);
                  blob.close();
                  callStatement.setBlob(i + 1, blob);
                  //statement.setBytes(i+1, data);
                  //java.io.ByteArrayInputStream bis = new java.io.ByteArrayInputStream(data);
                  //statement.setBinaryStream(i + 1, bis, data.length);
                  if (debug) {
                    System.err.println("ADDED BLOB");
                  }
                }
                else {
                  callStatement.setNull(i + 1, Types.BLOB );
                }
              }
            }
            else {
              int sqlType = ( (Integer) lookupTable.get( (String) param)).
                  intValue();
              callStatement.registerOutParameter(i + 1, sqlType);
              // System.out.println("\nregistered output parameter");
            }
          }
        }

        if (query != null) {
          // System.out.println("\nCalling query - callStatement.query()");
          rs = callStatement.executeQuery();
          // System.out.println("\nCalled query");
        }
        else {
          // System.out.println("\nCalling update - callStatement.execute()");
          callStatement.execute();
          // System.out.println("\nCalled update");
        }
      }

      if (rs != null) {
        ResultSetMetaData meta = rs.getMetaData();
        int columns = meta.getColumnCount();
        ArrayList dummy = new ArrayList();
        int index = 1;

        remainCount = 0;
        while (rs.next()) {
          if ( (index >= startIndex)
              && ( (endIndex == INFINITE) || (index <= endIndex))) {
            ResultSetMap rm = new ResultSetMap();

            for (int i = 1; i < (columns + 1); i++) {
              String param = meta.getColumnName(i);
              int type = meta.getColumnType(i);
              // System.out.println(param + " has type " + getType(type));
              Object value = null;
              java.util.Calendar c = java.util.Calendar.getInstance();

              if (rs.getString(i) != null) {
                switch (type) {
                  case Types.INTEGER:
                  case Types.SMALLINT:
                  case Types.TINYINT:
                  case Types.NUMERIC:
                    value = new Integer(rs.getInt(i));
                    break;

                  case Types.VARCHAR:
                  case Types.CHAR:
                    if (rs.getString(i) != null) {
                      value = new String(rs.getString(i));
                    }
                    break;

                  case Types.FLOAT:
                  case Types.DOUBLE:
                  case Types.DECIMAL:
                    value = new Double(rs.getString(i));
                    break;

                  case Types.DATE:
                    if (rs.getDate(i) != null) {
                      Date d = rs.getDate(i, c);
                      long l = d.getTime();

                      value = new java.util.Date(l);
                    }
                    break;

                  case -101: // For Oracle; timestamp with timezone, treat this as clocktime.
                     if (rs.getTimestamp(i) != null) {
                       Timestamp ts = rs.getTimestamp(i, c);
                       long l = ts.getTime();
                       value = new ClockTime(new java.util.Date(l));
                     }
                     break;

                  case Types.TIMESTAMP:
                    if (rs.getTimestamp(i) != null) {
                      Timestamp ts = rs.getTimestamp(i, c);
                      long l = ts.getTime();
                      value = new java.util.Date(l);
                    }
                    break;

                  case Types.BIT:
                    value = new Boolean(rs.getBoolean(i));
                    break;

                  default:
                    if (rs.getString(i) != null) {
                      value = new String(rs.getString(i));
                    }
                    break;
                }
              }
              rm.addValue(param.toUpperCase(), value);
            }
            dummy.add(rm);
            viewCount++;
          }
          else if (index >= startIndex) {
            remainCount++;
          }
          rowCount++;
          index++;
        }
        resultSet = new ResultSetMap[dummy.size()];
        resultSet = (ResultSetMap[]) dummy.toArray(resultSet);
      }
    }
    catch (SQLException sqle) {
      sqle.printStackTrace();
      logger.log(NavajoPriority.ERROR, sqle.getLocalizedMessage() + "/" + sqle.getSQLState(), sqle);
      throw new UserException( -1,  sqle.getLocalizedMessage() + "/" + sqle.getSQLState());
    }
    finally {
      resetAll(rs);
      rs = null;
    }
    long end = System.currentTimeMillis();
    double total = (end - start) / 1000.0;

    totaltiming += total;
    // System.out.println("finished " + total + " seconds. Average query time: " + (totaltiming/requestCount) + " (" + requestCount + ")");
    return resultSet;
  }

  public void setQuery(String newQuery) throws UserException {
//    if ( (this.query != null) || (this.update != null)) {
//      throw new UserException( -1,
//          "SPMap does not allow for multiple queries/updates, use a new SPMap");
//    }
    update = null;
    super.setQuery(newQuery);
    parameterTypes = new ArrayList();
  }

  public void setUpdate(String newUpdate) throws com.dexels.navajo.server.
      UserException {
    if ( (this.update != null) || (this.query != null)) {
      throw new UserException( -1,
          "SPMap does not allow for multiple queries/updates, use a new SPMap");
    }
    // System.out.println("in setUpdate(), newUpdate = " + newUpdate);
    // Close previous callStatement if it exists.
    try {
      	if (callStatement != null) {
      		callStatement.close();
      	}
      } catch (Exception e) { e.printStackTrace(System.err); }
    super.setUpdate(newUpdate);
    parameterTypes = new ArrayList();
  }

  public void setParameter(Object param) {
    // System.out.println("in setParameter(),");
    super.setParameter(param);
    parameterTypes.add(new Integer(INPUT_PARAM));
    // System.out.println("Leaving setParameter() in SPMap");
  }

  public Object getOutputParameter() {
    return "";
  }

  public void setOutputParameterType(String type) {
    //System.out.println("in setOutputParameter(), type = " + type);
    super.setParameter( (String) type);
    parameterTypes.add(new Integer(OUTPUT_PARAM));
    //System.out.println("Added output parameter f" + (String) type);
  }

  public Object getOutputParameter(Integer i) throws com.dexels.navajo.server.UserException {

    int index = i.intValue();
    // System.out.println("in getOutputParameter("+index+")");
    Object value = null;

    if (callStatement != null) {
      try {
        // System.out.println("parameters = " + parameters);
        if ( index  > parameters.size() ) {
          throw new UserException(-1, "Outputparameter index out of range: " + i.intValue() );
        }
        String type = (String) parameters.get(index - 1);
        //System.err.println("type = " + type);
        if (lookupTable.get(type) == null) {
          throw new UserException(-1, "Outputparameter index out of range, trying to read a normal parameter as an output parameter: " + i.intValue() );
        }
        int sqlType = ( (Integer) lookupTable.get(type)).intValue();

        //System.out.println("sqlType = " + sqlType);
        java.util.Calendar c = java.util.Calendar.getInstance();

        // System.out.println("VALUE OF OUTPUT PARAMETER: " + callStatement.getString(index));
        switch (sqlType) {
          case Types.VARCHAR:
          case Types.CHAR:
            value = callStatement.getString(index);
            break;

          case Types.BIT:
            value = new Boolean(callStatement.getBoolean(index));
            break;

          case Types.DATE:
            if (callStatement.getDate(index) != null) {
              Date d = callStatement.getDate(index, c);
              long l = d.getTime();

              value = new java.util.Date(l);
            }
            break;

          case -101: // For Oracle; timestamp with timezone, treat this as clocktime.
                    if (callStatement.getTimestamp(index) != null) {
                      Timestamp ts = callStatement.getTimestamp(index, c);
                      long l = ts.getTime();
                      value = new ClockTime(new java.util.Date(l));
                    }
                    break;

          case Types.TIMESTAMP:
            if (callStatement.getTimestamp(index) != null) {
              Timestamp ts = callStatement.getTimestamp(index, c);
              long l = ts.getTime();

              value = new java.util.Date(l);
            }
            break;

          case Types.INTEGER:
            value = new Integer(callStatement.getInt(index));
            break;

          case Types.NUMERIC:

            ResultSetMetaData meta = callStatement.getMetaData();
            int prec = meta.getPrecision(index);
            int scale = meta.getScale(index);

            if (scale == 0) {
              value = new Integer(callStatement.getInt(index));
            }
            else {
              value = new Double(callStatement.getString(index));
            }
            break;

          case Types.SMALLINT:
          case Types.TINYINT:
            value = new Integer(callStatement.getInt(index));
            break;

          case Types.DOUBLE:
          case Types.FLOAT:
            value = new Double(callStatement.getDouble(index));
            break;

          default:
            value = callStatement.getString(index);
            break;
        }
      }
      catch (SQLException sqle) {
        logger.log(NavajoPriority.ERROR, sqle.getMessage(), sqle);
        throw new com.dexels.navajo.server.UserException( -1, sqle.getMessage());
      }
      return value;
    }
    else {
      return "";
    }
  }

  public void store() throws com.dexels.navajo.server.UserException,
      com.dexels.navajo.mapping.MappableException {
    super.store();
    try {
      if (callStatement != null) {
        callStatement.close();
      }
    }
    catch (SQLException sqle) {
      logger.log(NavajoPriority.ERROR, sqle.getMessage(), sqle);
      sqle.printStackTrace();
    }
  }

  public void kill() {
    super.kill();
    try {
      if (callStatement != null) {
        callStatement.close();
      }
    }
    catch (SQLException sqle) {
      logger.log(NavajoPriority.ERROR, sqle.getMessage(), sqle);
      sqle.printStackTrace();
    }
  }
}
