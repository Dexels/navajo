package com.dexels.navajo.adapter;

import com.dexels.navajo.document.*;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.HashMap;
import java.sql.*;
import javax.naming.Context;

import org.dexels.grus.DbConnectionBroker;

import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.util.*;
import com.dexels.navajo.logger.*;

/**
 * Title:        Navajopa
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
 * Other datasource are defined as "default" except with a unique message name identifying the datasource. *
 * A single SQLMap instance can be used to run multiple queries. If a single transaction context is required multiple SQLMap instances
 * can be used if the transactionContext is the same.
 *
 * TODO
 *
 * Use property "timeout" to solve busy waiting bug (see below)
 * Introduce option to set autocommit mode
 *
 * BUGS
 *
 * Change DbConnectionBroker such that it throws an exception if after some specified timeout a connection cannot be made.
 * Currently the connectionbroker keeps waiting until a connection can be created. This leads to deadlocks if nested SQLMap
 * instances are used!!
 *
 * Already closed connection appeared. Were does this come from: see following stack trace:
 *
 * 2002-12-03 21:40:41,174 DEBUG [ApplicationServerThread] mapping.XmlMapperInterpreter (XmlMapperInterpreter.java:123) - in XMlMapperInterpreter(), XMLfile:/home/dexels/projecten/SportLink/sportlink-serv/navajo-tester/auxilary/scripts//ProcessInsertClubMembership.xsl :
2002-12-03 21:40:59,701 ERROR [ApplicationServerThread] adapter.SPMap (SPMap.java:273) - JZ0C0: Connection is already closed.
java.sql.SQLException: JZ0C0: Connection is already closed.
        at com.sybase.jdbc2.jdbc.ErrorMessage.raiseError(ErrorMessage.java:500)
        at com.sybase.jdbc2.jdbc.SybConnection.checkConnection(SybConnection.java:1731)
        at com.sybase.jdbc2.jdbc.SybStatement.checkDead(SybStatement.java:1792)
        at com.sybase.jdbc2.jdbc.SybPreparedStatement.setNull(SybPreparedStatement.java:107)
        at com.dexels.navajo.adapter.SPMap.getResultSet(SPMap.java:151)
        at com.dexels.navajo.adapter.SQLMap.setDoUpdate(SQLMap.java:303)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.dexels.navajo.mapping.XmlMapperInterpreter.setAttribute(XmlMapperInterpreter.java:1198)
        at com.dexels.navajo.mapping.XmlMapperInterpreter.setSimpleAttribute(XmlMapperInterpreter.java:1245)
        at com.dexels.navajo.mapping.XmlMapperInterpreter.executeSimpleMap(XmlMapperInterpreter.java:1340)
        at com.dexels.navajo.mapping.XmlMapperInterpreter.createMapping(XmlMapperInterpreter.java:685)
        at com.dexels.navajo.mapping.XmlMapperInterpreter.doMapping(XmlMapperInterpreter.java:1422)
        at com.dexels.navajo.mapping.XmlMapperInterpreter.createMapping(XmlMapperInterpreter.java:466)
        at com.dexels.navajo.mapping.XmlMapperInterpreter.doMapping(XmlMapperInterpreter.java:1422)
        at com.dexels.navajo.mapping.XmlMapperInterpreter.interpret(XmlMapperInterpreter.java:1635)
        at com.dexels.navajo.server.GenericHandler.doService(GenericHandler.java:62)
        at com.dexels.navajo.server.ServiceHandler.construct(ServiceHandler.java:39)
        at com.dexels.navajo.persistence.impl.PersistenceManagerImpl.get(PersistenceManagerImpl.java:168)
        at com.dexels.navajo.server.Dispatcher.dispatch(Dispatcher.java:232)
        at com.dexels.navajo.server.Dispatcher.handle(Dispatcher.java:672)
        at com.dexels.navajo.server.TmlHttpServlet.doPost(TmlHttpServlet.java:187)
        at javax.servlet.http.HttpServlet.service(HttpServlet.java:211)
        at javax.servlet.http.HttpServlet.service(HttpServlet.java:309)
        at javax.servlet.http.HttpServlet.service(HttpServlet.java:336)
        at com.evermind._deb._lnc(.:514)
        at com.evermind._deb._wmb(.:170)
        at com.evermind._co._wbb(.:581)
        at com.evermind._co._fs(.:189)
        at com.evermind._bt.run(.:62)
 *
 */

public class SQLMap implements Mappable, LazyArray {

    protected final static int INFINITE = -1;

    public boolean debug = false;
    public String driver;
    public String url;
    public String username;
    public String password;
    public String update;
    public String query;
    public boolean doUpdate;
    // Set autoCommit to true to overide default settings from sqlmap.xml configuration file!
    public boolean autoCommit = true;
    private boolean overideAutoCommit = false;
    public int transactionIsolation = -1;
    public int rowCount = 0;
    public int viewCount = 0;
    public int updateCount = 0;
    public int remainCount = 0;
    public ResultSetMap[] resultSet = null;
    public int startIndex = 1;
    public int endIndex = INFINITE;
    public Object parameter;
    public Object columnValue;
    public int resultSetIndex = 0;
    public int transactionContext = -1;
    public String reload;

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

    private static HashMap autoCommitMap = null;

    protected static NavajoLogger logger = NavajoConfig.getNavajoLogger(SQLMap.class); //Logger.getLogger( SQLMap.class );

    private NavajoConfig navajoConfig = null;

    private void createDataSource(Message body, NavajoConfig config) throws UserException, NavajoException {

        String dataSourceName = body.getName();

        if (debug) System.err.println("Creating new datasource: " + dataSourceName);

        driver = NavajoUtils.getPropertyValue(body, "driver", true);
        url = NavajoUtils.getPropertyValue(body, "url", true);
        username = NavajoUtils.getPropertyValue(body, "username", true);
        password = NavajoUtils.getPropertyValue(body, "password", true);
        String logFile = config.getRootPath() + "/log/"
                + NavajoUtils.getPropertyValue(body, "logfile", true);
        double refresh = Double.parseDouble(NavajoUtils.getPropertyValue(body, "refresh", true));
        String min = NavajoUtils.getPropertyValue(body, "min_connections", false);
        int minConnections = (min.equals("")) ? 5 : Integer.parseInt(min);
        String max = NavajoUtils.getPropertyValue(body, "max_connections", false);
        int maxConnections = (max.equals("")) ? 20 : Integer.parseInt(max);
        String autoCommitStr = NavajoUtils.getPropertyValue(body, "autocommit", false);
        boolean ac = (autoCommitStr.equals("") || autoCommitStr.equalsIgnoreCase("true"));
        DbConnectionBroker myBroker = null;
        autoCommitMap.put(dataSourceName, new Boolean(ac));

        myBroker = createConnectionBroker(driver, url, username, password,
                                          minConnections, maxConnections, logFile, refresh);

        if (fixedBroker.get(dataSourceName) != null) {
            DbConnectionBroker brkr = (DbConnectionBroker) fixedBroker.get(dataSourceName);
            transactionContextMap = new HashMap();
            transactionContext = -1;
            con = null;
            if (debug) System.err.println("Killing previous version of broker (" + dataSourceName + ")...");
              brkr.destroy();
            if (debug) System.err.println("Done!");
        }
        fixedBroker.put(dataSourceName, myBroker);

        String logOutput = "Created datasource: " + dataSourceName + "\n" +
                           "Driver = " + driver + "\n" +
                           "Url = " + url + "\n" +
                           "Username = " + username + "\n" +
                           "Password = " + password + "\n" +
                           "Minimum connections = " + min + "\n" +
                           "Maximum connections = " + max + "\n" +
                           "Autocommit = " + ac + "\n";

        logger.log(NavajoPriority.DEBUG, logOutput);
    }

    public synchronized void setDeleteDatasource(String datasourceName) throws MappableException, UserException {
      logger.log(NavajoPriority.INFO, "SQLMap setDeleteDatasource(" + datasourceName + ") called");
      if (fixedBroker != null) {
        DbConnectionBroker brkr = (DbConnectionBroker) fixedBroker.get(datasourceName);
        if (brkr != null) {
            brkr.destroy();
          logger.log(NavajoPriority.INFO, "Destroyed broker for datasource: " + datasourceName);
        }
      }
    }

    /**
     *
     * @param reload
     */
    public synchronized void setReload(String datasourceName) throws MappableException, UserException {

        if (debug) System.out.println("SQLMAP setReload("+datasourceName+") called!");
        //logger.log(NavajoPriority.INFO, "SQLMap SetReload() called");
        this.reload = reload;
        try {

            if (transactionContextMap == null || !datasourceName.equals(""))
                transactionContextMap = new HashMap();

            if (autoCommitMap == null || !datasourceName.equals(""))
                autoCommitMap = new HashMap();

            if (configFile == null || !datasourceName.equals("")) {
//                configFile = XMLutils.createNavajoInstance(navajoConfig.getConfigPath() + "sqlmap.xml");

                configFile = navajoConfig.readConfig("sqlmap.xml");

                // If propery file exists create a static connectionbroker that can be accessed by multiple instances of
                // SQLMap!!!
                if (fixedBroker == null && datasourceName.equals("")) { // Only re-create entire HashMap at initialization!
                    fixedBroker = new HashMap();
                }

                if (datasourceName.equals("")) {
                  // Get other data sources.
                  ArrayList all = configFile.getMessages("/datasources/.*");
                  for (int i = 0; i < all.size(); i++) {
                      Message body = (Message) all.get(i);
                      createDataSource(body, navajoConfig);
                  }
                } else {
                  createDataSource(configFile.getMessage("/datasources/"+datasourceName), navajoConfig);
                }

                if (fixedBroker.get("default") == null) {
                    logger.log(NavajoPriority.WARN, "Could not create default broker [driver = " + driver + ", url = " + url + ", username = '" + username + "', password = '" + password + "']");
                    //throw new UserException(-1, "in SQLMap. Could not create default broker [driver = " + driver + ", url = " + url + ", username = '" + username + "', password = '" + password + "']");
                }
            }
            rowCount = 0;
        } catch (NavajoException ne) {
            logger.log(NavajoPriority.ERROR, ne.getMessage(), ne);
            throw new MappableException(ne.getMessage());
        } catch (java.io.IOException fnfe) {
            logger.log(NavajoPriority.ERROR, fnfe.getMessage(), fnfe);
            throw new MappableException("Could not load configuration file for SQLMap object: " + fnfe.getMessage());
        }
    }

    public void setDebug(boolean b) {
      this.debug = b;
    }

    public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
        // Check whether property file sqlmap.properties exists.
        this.navajoConfig = config;
        setReload("");
        if (debug) System.err.println("LEVAING SQLMAP load()...");
    }

    public void setDatasource(String s) {
        datasource = s;
    }

    public void kill() {
        try {
            // Determine autocommit value
            boolean ac = (this.overideAutoCommit) ? autoCommit : ((Boolean) autoCommitMap.get(datasource)).booleanValue();
            if (!ac) {
                if (con != null)
                    con.rollback();
            }
            // Set autoCommit mode to default value.
            if (con != null && autoCommitMap.get(datasource) != null)
              con.setAutoCommit(((Boolean) autoCommitMap.get(datasource)).booleanValue());
            if (transactionContext == -1) {
                if (con != null) {
                    transactionContextMap.remove(connectionId + "");
                    ((DbConnectionBroker) fixedBroker.get(datasource)).freeConnection(con);
                }
            }
        } catch (SQLException sqle) {
            logger.log(NavajoPriority.ERROR, sqle.getMessage(), sqle);
            sqle.printStackTrace();
        }
    }

    public void store() throws MappableException, UserException {
        // Kill temporary broker.
        // If part of transaction context, do not free connection or commit changes yet.
        if (transactionContext == -1) {
            if (con != null) {
                try {
                    // Determine autocommit value
                    boolean ac = (this.overideAutoCommit) ? autoCommit : ((Boolean) autoCommitMap.get(datasource)).booleanValue();
                    if (!ac)
                        con.commit();
                    // Set autoCommit mode to default value.
                    con.setAutoCommit(((Boolean) autoCommitMap.get(datasource)).booleanValue());
                } catch (SQLException sqle) {
                    logger.log(NavajoPriority.ERROR, sqle.getMessage(), sqle);
                    throw new UserException(-1, sqle.getMessage(), sqle);
                }
                if (transactionContextMap != null)
                    transactionContextMap.remove(connectionId + "");
                if (fixedBroker != null) {
                    ((DbConnectionBroker) fixedBroker.get(datasource)).freeConnection(con);
                }
            }
        }
    }

    public void setTransactionIsolationLevel(int j) {
        transactionIsolation = j;
    }

    public void setAutoCommit(boolean b) throws UserException {
        this.autoCommit = b;
        try {
        if (con != null)
          con.commit(); // Commit previous actions.
          con.setAutoCommit(b);
        } catch (SQLException sqle) {
          logger.log(NavajoPriority.DEBUG, sqle.getMessage(), sqle);
          throw new UserException(-1, sqle.getMessage(), sqle);
        }
        overideAutoCommit = true;
    }

    public void setTransactionContext(int i) throws UserException {

      if (debug) System.err.println("IN SETTRANSACTIONCONTEX(), I = " + i);
        this.transactionContext = i;
        // Get a shared connection from the transactionContextMap.
        // System.err.println("in setTransactionContex(), id = " + i);
        con = (Connection) this.transactionContextMap.get(i + "");

        if (debug) System.err.println("CON = " + con);

        if (con == null) {
            logger.log(NavajoPriority.ERROR, "Invalid transaction context: " + i);
            throw new UserException(-1, "Invalid transaction context set");
        }
    }

    public int getTotalElements() throws UserException {
      return getTotalElements("");
    }

    public int getTotalElements(String s) throws UserException {
        if (resultSet == null)
            getResultSet();
        return this.rowCount;
    }

    public int getCurrentElements(String s) {
        return this.viewCount;
    }

    public int getRemainingElements(String s) {
        return this.remainCount;
    }

    public void setRowCount(int i) {
        this.rowCount = i;
    }

    public int getRowCount() throws UserException {
      return getTotalElements("");
    }

    public void setUpdateCount( int i ) {
      this.updateCount = 0;
    }

    public int getUpdateCount() throws UserException {
      this.getTotalElements( "" );
      return ( this.updateCount );
    }

    public void setUpdate(String newUpdate) throws UserException {
        update = newUpdate;
        this.resultSet = null;
        parameters = new ArrayList();
    }

    public void setDoUpdate(boolean doit) throws UserException {
        this.getResultSet(true);
    }

    public void setResultSetIndex(int index) {
        this.resultSetIndex = index;
    }

    public Object getColumnValue() throws UserException {
        throw new UserException(-1, "Use $columnValue('[name of the column]')");
    }

     public Object getColumnName(Integer index) throws UserException {

      if (resultSet == null)
            getResultSet();
      if ((resultSet == null) || (resultSet.length == 0))
          throw new UserException(-1, "No records found");

      ResultSetMap rm = resultSet[resultSetIndex];
      return rm.getColumnName(index);

    }

     public Object getColumnValue(Integer index) throws UserException {

      if (resultSet == null)
            getResultSet();
      if ((resultSet == null) || (resultSet.length == 0))
          throw new UserException(-1, "No records found");

      ResultSetMap rm = resultSet[resultSetIndex];
      return rm.getColumnValue(index);

    }

    public Object getColumnValue(String columnName) throws UserException {
        if (resultSet == null)
            getResultSet();

        if ((resultSet == null) || (resultSet.length == 0))
          throw new UserException(-1, "No records found");

        ResultSetMap rm = resultSet[resultSetIndex];

        return rm.getColumnValue(columnName);
    }

    /**
     * Use this method to define a new query.
     * All parameters used by a previous query are removed.
     * replace " characters with ' characters.
     */
    public void setQuery(String newQuery) throws UserException {
        query = newQuery.replace('"', '\'');
        if (debug)
            System.err.println("SQLMap(): query = " + query);
        this.resultSet = null;
        parameters = new ArrayList();
    }

    public void setParameter(Object param) {
        if (debug) System.err.println("in setParameter(), param = " + param);
        if (parameters == null)
            parameters = new ArrayList();
        if ((param != null) && (param instanceof String)
                && (((String) param).indexOf(";") != -1)) {
            java.util.StringTokenizer tokens = new java.util.StringTokenizer((String) param, ";");

            while (tokens.hasMoreTokens()) {
                parameters.add(tokens.nextToken());
            }
        } else {
            parameters.add(param);
        }
    }

    protected static synchronized DbConnectionBroker createConnectionBroker(String driver, String url, String username, String password,
            int min, int max, String logFile, double refreshRate) throws UserException {
        DbConnectionBroker db = null;

        try {
            db = new DbConnectionBroker(driver, url, username, password, min, max, logFile, refreshRate);
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(NavajoPriority.ERROR, e.getMessage(), e);
            throw new UserException(-1, "Could not create connectiobroker: " + "[driver = " + driver + ", url = " + url + ", username = '" + username + "', password = '" + password + "']:" + e.getMessage());
        }
        return db;
    }

    protected String getType(int i) {
        switch (i) {
        case java.sql.Types.DOUBLE:
            return "DOUBLE";

        case Types.FLOAT:
            return "FLOAT";

        case Types.INTEGER:
            return "INTEGER";

        case Types.DATE:
            return "DATE";

        case Types.VARCHAR:
            return "VARCHAR";

        case Types.BIT:
            return "BOOLEAN";

        case Types.TIME:
            return "TIME";

        case Types.TIMESTAMP:
            return "TIMESTAMP";

        case Types.BIGINT:
            return "BIGINT";

        case Types.DECIMAL:
            return "DECIMAL";

        case Types.NULL:
            return "NULL";

        case Types.NUMERIC:
            return "NUMERIC";

        case Types.OTHER:
            return "OTHER";

        case Types.REAL:
            return "REAL";

        case Types.SMALLINT:
            return "SMALLINT";

        case Types.BLOB:
            return "BLOB";

        case Types.DISTINCT:
            return "DISTINCT";

        case Types.STRUCT:
            return "STRUCT";

        case Types.JAVA_OBJECT:
            return "JAVA_OBJECT";

        case Types.TINYINT:
            return "TINYINT";

        default:
            return "UNSUPPORTED: " + i;
        }
    }

    protected void createConnection() throws SQLException, UserException {

        if (con == null) { // Create connection if it does not yet exist.
            con = ((DbConnectionBroker) fixedBroker.get(datasource)).getConnection();
            if (con == null) {
              logger.log(NavajoPriority.WARN, "Could not connect to database: " + datasource + ", one more try with fresh broker....");
              Message msg = configFile.getMessage("/datasources/"+datasource);
              try {
                createDataSource(msg, navajoConfig);
              } catch (NavajoException ne) {
                 logger.log(NavajoPriority.ERROR, ne.getMessage(), ne);
                throw new UserException(-1, ne.getMessage());
              }
              con = ((DbConnectionBroker) fixedBroker.get(datasource)).getConnection();
              if (con == null) {
                logger.log(NavajoPriority.ERROR, "Could (still) not connect to database: " + datasource + ", check your connection");
                throw new UserException(-1, "Could not connect to database: " + datasource + ", check your connection");
              }
            }
            connectionId = con.hashCode();
            transactionContextMap.put(connectionId + "", con);
            if (con != null) {
                boolean ac = (this.overideAutoCommit) ? autoCommit : ((Boolean) autoCommitMap.get(datasource)).booleanValue();
                con.commit();
                con.setAutoCommit(ac);
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
        if (debug) System.err.println("IN GETTRANSACTIONCONTEXT(), CONNECTIONID = " + connectionId);
        return connectionId;
    }

    /**
     * NOTE: DO NOT USE THIS METHOD ON LARGE RESULTSETS WITHOUT SETTING ENDINDEX.
     *
     */

    private ResultSet getDBResultSet(boolean updateOnly) throws SQLException {
                if (query != null)
                    statement = con.prepareStatement(query);
                else
                    statement = con.prepareStatement(update);

                if (parameters != null) {
                    // System.err.println("parameters = " + parameters);
                    for (int i = 0; i < parameters.size(); i++) {
                        Object param = parameters.get(i);

                        // System.err.println("parameter " + i + " = " + param);
                        if (param == null)
                            statement.setNull(i + 1, Types.VARCHAR);
                        if (param instanceof String)
                            statement.setString(i + 1, (String) param);
                        else if (param instanceof Integer)
                            statement.setInt(i + 1, ((Integer) param).intValue());
                        else if (param instanceof Double)
                            statement.setDouble(i + 1, ((Double) param).doubleValue());
                        else if (param instanceof java.util.Date) {
                            java.sql.Date sqlDate = new java.sql.Date(((java.util.Date) param).getTime());
                            statement.setDate(i + 1, sqlDate);
                        } else if (param instanceof Boolean) {
                            statement.setBoolean(i + 1, ((Boolean) param).booleanValue());
                        }
                    }
                }

                ResultSet rs = null;

                if (updateOnly) {
                  statement.executeUpdate();
                }
                else {
                  try {
                    rs = statement.executeQuery();
                  }
                  catch (SQLException e) {
                    rs = null;
                    // For Sybase compatibility: sybase does not like to be called using executeQuery() if query does not return a resultset.
                    if (e.getMessage().indexOf("JZ0R2") == -1) {
                      e.printStackTrace();
                      throw e;
                    }
                  }
                }
                this.updateCount = statement.getUpdateCount();

                // dump any SQL warnings
                if (debug) {
                  SQLWarning warning = statement.getWarnings();
                  while ( warning != null ) {
                    this.logger.log(NavajoPriority.DEBUG, "SQL warning: " +
                                    warning.getMessage());
                    warning = warning.getNextWarning();
                  }
                  this.logger.log( NavajoPriority.DEBUG, "updated record count is " +
                    this.updateCount );
                }
                return rs;
    }

    public Connection getConnection() throws java.sql.SQLException {
        try {
          createConnection();
          return this.con;
        } catch (com.dexels.navajo.server.UserException ue) {
          return null;
        }
    }

    public ResultSetMap [] getResultSet() throws UserException {
      return getResultSet(false);
    }

    private ResultSetMap [] getResultSet(boolean updateOnly) throws UserException {

        requestCount++;
        ResultSet rs = null;

        long start = 0;
        if (debug)
          start = System.currentTimeMillis();


        try {

            createConnection();

            if (con == null) {
                logger.log(NavajoPriority.ERROR, "Could not connect to database: " + datasource + ", check your connection");
                throw new UserException(-1, "in SQLMap. Could not open database connection [driver = " + driver + ", url = " + url + ", username = '" + username + "', password = '" + password + "']");
            }

            if (debug) System.err.println("SQLMAP, GOT CONNECTION, STARTING QUERY");
            if (resultSet == null) {
              rs = getDBResultSet(updateOnly);
            }

            if (debug) System.err.println("SQLMAP, QUERY HAS BEEN EXECUTED, RETRIEVING RESULTSET");

            if (rs != null) {

                int columns = 0;
                ResultSetMetaData meta = null;
                try {
                  meta = rs.getMetaData();
                  columns = meta.getColumnCount();
                } catch (Exception e) {

                }
                ArrayList dummy = new ArrayList();
                int index = 1;
                remainCount = 0;
                rowCount = 0;

                while (rs.next()) {

                    if ((index >= startIndex)
                            && ((endIndex == INFINITE) || (index <= endIndex))) {
                        ResultSetMap rm = new ResultSetMap();

                        for (int i = 1; i < (columns + 1); i++) {
                            String param = meta.getColumnLabel(i);
                            int type = meta.getColumnType(i);

                            Object value = null;

                            if (rs.getString(i) != null) {
                                switch (type) {
                                    // If number of decimal places equals zero treat NUMERIC as integer else as a float.
                                    //case Types.NUMERIC:
                                         //System.err.println("param: " + param + " IN NUMERIC, getScale() = " + meta.getScale(i));
                                         //System.err.println("param: " + param + " IN NUMERIC, getPrecision() = " + meta.getPrecision(i));

                                         //if (meta.getScale(i) == 0)
                                         //  value = new Integer(rs.getInt(i));
                                         //else
                                         //  value = new Double(rs.getString(i));
                                         //break;
                                    case Types.INTEGER:
                                    case Types.SMALLINT:
                                    case Types.TINYINT:
                                        value = new Integer(rs.getInt(i));
                                        break;

                                    case Types.CHAR:
                                    case Types.VARCHAR:
                                        if (rs.getString(i) != null) value = new String(rs.getString(i));
                                        break;

                                    case Types.DECIMAL:
                                    case Types.FLOAT:
                                    case Types.DOUBLE:
                                    case Types.NUMERIC:
                                        value = new Double(rs.getString(i));
                                        break;

                                    case Types.DATE:
                                        if (rs.getDate(i) != null) {

                                            java.util.Calendar c = java.util.Calendar.getInstance();
                                            long l = -1;
                                            try {
                                              Date d = rs.getDate(i, c);
                                              l = d.getTime();
                                            } catch (Exception e) {
                                              Date d = rs.getDate(i);
                                              l = d.getTime();
                                            }


                                            value = new java.util.Date(l);
                                        }

                                        break;

                                    case Types.TIMESTAMP:
                                        if (rs.getTimestamp(i) != null) {

                                            java.util.Calendar c = java.util.Calendar.getInstance();

                                            long l = -1;
                                            try {
                                              Timestamp ts = rs.getTimestamp(i, c);
                                              l = ts.getTime();
                                            } catch (Exception e) {
                                              Date d = rs.getDate(i);
                                              l = d.getTime();
                                            }

                                            value = new java.util.Date(l);
                                        }

                                        break;

                                    case Types.BIT:
                                        value = new Boolean(rs.getBoolean(i));
                                        break;

                                    default:
                                        if (rs.getString(i) != null) value = new String(rs.getString(i));
                                        break;
                                }
                            } else {
                            }
                            rm.addValue(param.toUpperCase(), value);
                        }
                        dummy.add(rm);
                        viewCount++;
                    } else if (index >= startIndex) {
                        remainCount++;
                    }
                    rowCount++;
                    index++;
                }
                if (debug) System.err.println("GOT RESULTSET");
                resultSet = new ResultSetMap[dummy.size()];
                resultSet = (ResultSetMap[]) dummy.toArray(resultSet);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            logger.log(NavajoPriority.ERROR, sqle.getMessage(), sqle);
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
                logger.log(NavajoPriority.ERROR, e.getMessage(), e);
                throw new UserException(-1, e.getMessage());
            }
        }

        if (debug) {
             long end = System.currentTimeMillis();
             double total = (end - start) / 1000.0;
            // totaltiming += total;
        }
        return resultSet;
    }

    public void setStartIndex(int newStartIndex) {
      startIndex = newStartIndex;
    }

    public void setStartIndex(String s, int newStartIndex) {
        startIndex = newStartIndex;
    }

    public String getDatabaseProductName() throws UserException {
      if (con != null) {
        try {
          return con.getMetaData().getDatabaseProductName();
        } catch (SQLException sqle) {
          throw new UserException(-1, sqle.getMessage());
        }
      } else
        return "Not Connected.";
    }

    public int getStartIndex(String s) {
        return startIndex;
    }

    public void setEndIndex(int i) {
      endIndex = i;
    }

    public void setEndIndex(String s, int newEndIndex) {
        endIndex = newEndIndex;
    }

    public int getEndIndex(String s) {
        return endIndex;
    }
}
