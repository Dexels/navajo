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

import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.adapter.sqlmap.SQLMapConstants;
import com.dexels.navajo.adapter.sqlmap.SQLMapHelper;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.Repository;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.util.AuditLog;

@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class SPMap extends SQLMap {

	
  private final static Logger logger = LoggerFactory.getLogger(SPMap.class);
  public String outputParameter;
  public String outputParameterType;

  protected final static int INPUT_PARAM = 0;
  protected final static int OUTPUT_PARAM = 1;
  protected final static int INOUT_PARAM = 2;
  private static int openCallStatements = 0;
  private boolean isLegacyMode;

  protected ArrayList parameterTypes = new ArrayList();

  protected static HashMap lookupTable = null;

  protected CallableStatement callStatement = null;

  private static Object semaphore = new Object();
  
  public SPMap() {
	  this.isLegacyMode = SQLMapConstants.isLegacyMode();
  }
  
  public void load(Access access) throws UserException, MappableException {
	  
	  super.load(access);
	  synchronized ( semaphore  ) {
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
	  }
	  // System.out.println("lookupTable = " + lookupTable);
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

        // Close previously open call statements:
        if (callStatement != null) {

			try {
				callStatement.close();
			} catch (Exception e) {
				logger.warn("Non fatal error closing statement", e);
			}
     	callStatement = null;
        	openCallStatements--;
        }
        
        if (query != null) {
        
          callStatement = con.prepareCall(query);
          openCallStatements++;
          //System.err.println(">>>>>>>>>>>>>>>>>>> OPEN CALL STATEMENTS: " + openCallStatements);
          if (query.indexOf("Call") != -1 && query.indexOf("(") != -1) {
            spName = query.substring(query.indexOf("Call") + 5,
                                     query.indexOf("("));
          }
        }
        else {
          callStatement = con.prepareCall(update);
          openCallStatements++;
          //System.err.println(">>>>>>>>>>>>>>>>>>> OPEN CALL STATEMENTS: " + openCallStatements);
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
	              System.out.println("Setting parameter: " + param + "(" + (param != null ? param.getClass().toString() : "") + "), type = " + type);
	            }
	            if (type == INPUT_PARAM) {
	                spIndex++;
					SQLMapHelper.setParameter(callStatement,
    										  param, 
    										  i, 
    										  this.getClass(),
    										  myConnectionBroker.getDbIdentifier(), 
    										  this.isLegacyMode,
    										  this.debug, 
    										  this.myAccess);
	            } else {
	              int sqlType = ( (Integer) lookupTable.get( param)).intValue();
	              callStatement.registerOutParameter(i + 1, sqlType);
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
          if ( (index >= startIndex) && ( (endIndex == INFINITE) || (index <= endIndex))) {
            ResultSetMap rm = new ResultSetMap();

            for (int i = 1; i < (columns + 1); i++) {
              String param = meta.getColumnName(i);
              int type = meta.getColumnType(i);
              // System.out.println(param + " has type " + getType(type));
              Object value = null;
              java.util.Calendar c = java.util.Calendar.getInstance();

              if (rs.getString(i) != null) {
            	  value = SQLMapHelper.getColumnValue(rs, type, i);
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
      AuditLog.log("SPMap", sqle.getLocalizedMessage() + "/" + sqle.getSQLState(), Level.SEVERE, myAccess.accessID);
      
      throw new UserException( -1,  sqle.getLocalizedMessage() + "/" + sqle.getSQLState());
    }
    finally {
    	 if (rs != null) {
       	  try {
       	   rs.close();
       	  } catch (Exception e) { e.printStackTrace(Access.getConsoleWriter(myAccess)); }
       	  rs = null;
         }
         this.resetAll();
    }
    long end = System.currentTimeMillis();
    double total = (end - start) / 1000.0;

    totaltiming += total;
    // System.out.println("finished " + total + " seconds. Average query time: " + (totaltiming/requestCount) + " (" + requestCount + ")");
    return resultSet;
  }

  
    private boolean isLegacyMode() {
		Repository r = DispatcherFactory.getInstance().getNavajoConfig().getRepository();
		return r.useLegacyDateMode();
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
      		callStatement = null;
      		openCallStatements--;
      	}
      } catch (Exception e) { e.printStackTrace(Access.getConsoleWriter(myAccess)); }
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
    super.setParameter( type);
    parameterTypes.add(new Integer(OUTPUT_PARAM));
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
    	  AuditLog.log("SPMap", sqle.getLocalizedMessage() + "/" + sqle.getSQLState(), Level.SEVERE, myAccess.accessID);
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
    try {
      if (callStatement != null) {
    	//System.err.println("CLOSING callStatement IN SPMAP.store()");
        callStatement.close();
        callStatement = null;
        openCallStatements--;
      }
    }
    catch (SQLException sqle) {
    	AuditLog.log("SPMap", sqle.getLocalizedMessage() + "/" + sqle.getSQLState(), Level.SEVERE, myAccess.accessID);
      sqle.printStackTrace();
    }
    super.store();
  }

  public void kill() {
    try {
      if (callStatement != null) {
        callStatement.close();
        callStatement = null;
        openCallStatements--;
      }
    }
    catch (SQLException sqle) {
    	AuditLog.log("SPMap", sqle.getLocalizedMessage() + "/" + sqle.getSQLState(), Level.SEVERE, myAccess.accessID);
      sqle.printStackTrace();
    }
    super.kill();
  }
  
  /**
   * This method can not be deleted because it's used in SQLMapHelper - setBlob
   */
  public void addToBinaryStreamList(InputStream binaryStream) {
      super.addToBinaryStreamList(binaryStream);
  }
}
