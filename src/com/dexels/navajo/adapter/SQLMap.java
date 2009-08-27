package com.dexels.navajo.adapter;

import com.dexels.navajo.adapter.sqlmap.DatabaseInfo;
import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.adapter.sqlmap.SQLBatchUpdateHelper;
import com.dexels.navajo.adapter.sqlmap.ConnectionBrokerManager;
import com.dexels.navajo.adapter.sqlmap.SessionIdentification;

import com.dexels.navajo.document.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.*;
import java.util.StringTokenizer;

import org.dexels.grus.DbConnectionBroker;

import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import com.dexels.navajo.mapping.compiler.meta.SQLFieldDependency;
import com.dexels.navajo.server.*;
import com.dexels.navajo.util.*;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.Percentage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.Map;
import java.util.Collections;
import java.util.logging.Level;

import com.dexels.navajo.document.types.Memo;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.types.AuditLogEvent;

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
 * TODO Use property "timeout" to solve busy waiting bug (see below)
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

public class SQLMap implements Mappable, LazyArray, HasDependentResources {

  protected final static int INFINITE = -1;
  protected final String USERPWDDELIMITER = "/";
  protected final String DEFAULTSRCNAME = "default";

  public boolean debug = false;
  public boolean kill = false;
  public int timeAlert = -1;
  
  public String driver;
  public String url;
  public String username;
  public String password;
  public String update;
  public String query;
  public String savedQuery;
  public boolean doUpdate;
  // Set autoCommit to true to overide default settings from sqlmap.xml configuration file!
  public boolean autoCommit = true;
  public boolean replaceQueryDoubleQuotes = true;

  private boolean overideAutoCommit = false;
  public int transactionIsolation = -1;
  public int rowCount = 0;
  public int lazyTotal = 0;
  public int totalRows = 0;
  public int viewCount = 0;
  public int updateCount = 0;
  public int remainCount = 0;
  public ResultSetMap[] resultSet = null;
  public Binary records;
  public int startIndex = 1;
  public int endIndex = INFINITE;
  public Object parameter;
  public Object multipleParameters;
  public Object columnValue;
  public int resultSetIndex = 0;
  public int transactionContext = -1;
  public String reload;
  public String separator = ";";
  public boolean showHeader = true;

  protected Connection con = null;
  protected PreparedStatement statement = null;
  protected ArrayList parameters = null;
  
  protected final ArrayList binaryStreamList = new ArrayList();
  

  protected static ConnectionBrokerManager fixedBroker = null;
  protected DbConnectionBroker myConnectionBroker = null;
  public String datasource = this.DEFAULTSRCNAME;
  public String databaseProductName;
  public String databaseVersion;
  public DatabaseInfo databaseInfo;

  protected static double totaltiming = 0.0;
  protected static int requestCount = 0;

  private static Navajo configFile = null;
  private static Map transactionContextMap = null;
  private static Map autoCommitMap = null;

  private int connectionId = -1;

  protected NavajoConfigInterface navajoConfig = null;

  // handling batch mode, multiple SQL statements
  private boolean batchMode = false;
  private SQLBatchUpdateHelper helper = null;

  private static int openResultSets = 0;
  protected Access myAccess;
  
  public int instances;
  
  private static Object semaphore = new Object();
  
  private void createDataSource(Message body, NavajoConfigInterface config) throws Throwable {

    String dataSourceName = body.getName();

    if (debug) {
      Access.writeToConsole(myAccess, "Creating new datasource: " + dataSourceName + "\n");
    }
    
    if (autoCommitMap==null) {
		autoCommitMap = Collections.synchronizedMap(new HashMap());
	}

    driver = body.getProperty("driver").getValue(); //NavajoUtils.getPropertyValue(body, "driver", true);
    url =  body.getProperty("url").getValue(); // NavajoUtils.getPropertyValue(body, "url", true);

    final String username = (this.username != null) ? this.username :
    	body.getProperty("username").getValue();
    final String password = (this.password != null) ? this.password :
    	body.getProperty("password").getValue();

    String logFile = config.getRootPath() + "/log/"
        + body.getProperty("logfile").getValue();
    double refresh = Double.parseDouble(body.getProperty("refresh").getValue());
    
    String min = ( body.getProperty("min_connections") != null ? body.getProperty("min_connections").getValue() : "" ); // NavajoUtils.getPropertyValue(body, "min_connections", false);
    int minConnections = (min.equals("")) ? 5 : Integer.parseInt(min);
    String max = ( body.getProperty("max_connections") != null ? body.getProperty("max_connections").getValue() : "" ); 
    int maxConnections = (max.equals("")) ? 20 : Integer.parseInt(max);
    String autoCommitStr = ( body.getProperty("autocommit") != null ? body.getProperty("autocommit").getValue() : "" ); 
    boolean ac = (autoCommitStr.equals("") ||
                  autoCommitStr.equalsIgnoreCase("true"));
    DbConnectionBroker myBroker = null;
    autoCommitMap.put(dataSourceName, new Boolean(ac));

    if (fixedBroker.get(dataSourceName, username, password) != null) {
      transactionContextMap = Collections.synchronizedMap(new HashMap());
      transactionContext = -1;
      con = null;
      if (debug) {
    	  Access.writeToConsole(myAccess, "Killing previous version of broker (" + dataSourceName + ":" + username + ")...\n");
      }
      fixedBroker.destroy(dataSourceName, username);
      if (debug) {
    	  Access.writeToConsole(myAccess, "Done!\n");
      }
    }
    try {
    	fixedBroker.put(dataSourceName, driver, url, username, password,
    			minConnections, maxConnections, logFile,
    			refresh, new Boolean(ac), false);
    }
    catch (ClassNotFoundException e) {
      throw new UserException( -1, e.toString());
    }

    String logOutput = "Created datasource: " + dataSourceName + "\n" +
        "Driver = " + driver + "\n" +
        "Url = " + url + "\n" +
        "Username = " + username + "\n" +
        "Password = " + password + "\n" +
        "Minimum connections = " + min + "\n" +
        "Maximum connections = " + max + "\n" +
        "Autocommit = " + ac + "\n";

  }

  public int getInstances() {
	  return ConnectionBrokerManager.getInstances();
  }
  
  public synchronized void setDeleteDatasource(String datasourceName) throws
      MappableException, UserException {
   
    if (fixedBroker != null) {
      fixedBroker.destroy(datasourceName, this.username);
    }
  }

  /**
   *
   * @param reload
   */
  public void setReload(String datasourceName) throws MappableException, UserException {
	  
	  //synchronized ( semaphore ) {
		  if (debug) {
			  Access.writeToConsole(myAccess, "SQLMAP setReload(" + datasourceName + ") called!\n");
		  }
		  
		  try {
			  
			  if (transactionContextMap == null || !datasourceName.equals("")) {
				  synchronized ( semaphore ) {
					  if ( transactionContextMap == null ) {
						  transactionContextMap = Collections.synchronizedMap(new HashMap());
					  }
				  }
			  }
			  
			  if (autoCommitMap == null || !datasourceName.equals("")) {
				  synchronized ( semaphore ) {
					  if ( transactionContextMap == null ) {
						  autoCommitMap = Collections.synchronizedMap(new HashMap());
					  }
				  }
			  }
			  
			  if (configFile == null || !datasourceName.equals("")) {

				  synchronized ( semaphore ) {

					  if ( configFile == null ) {
						  configFile = navajoConfig.readConfig("sqlmap.xml");

						  // If propery file exists create a static connectionbroker that can be accessed by multiple instances of
						  // SQLMap!!!
						  if (fixedBroker == null && datasourceName.equals("")) { // Only re-create entire HashMap at initialization!
							  fixedBroker = new ConnectionBrokerManager(this.debug);
						  }

						  if (datasourceName.equals("")) {
							  // Get other data sources.
							  ArrayList all = configFile.getMessages("/datasources/.*");
							  for (int i = 0; i < all.size(); i++) {
								  Message body = (Message) all.get(i);
								  createDataSource(body, navajoConfig);
							  }
						  }
						  else {
							  createDataSource(configFile.getMessage("/datasources/" +
									  datasourceName), navajoConfig);
						  }
						  this.checkDefaultDatasource();
					  }
				  }
			  }
			  rowCount = 0;
		  }
		  catch (NavajoException ne) {
			  ne.printStackTrace(Access.getConsoleWriter(myAccess));
			  AuditLog.log("SQLMap", ne.getMessage(), Level.SEVERE, myAccess.accessID);
			  throw new MappableException(ne.getMessage());
		  }
		  catch (java.io.IOException fnfe) {
			  fnfe.printStackTrace(Access.getConsoleWriter(myAccess));
			  AuditLog.log("SQLMap", fnfe.getMessage(), Level.SEVERE, myAccess.accessID);
			  throw new MappableException(
					  "Could not load configuration file for SQLMap object: " +
					  fnfe.getMessage());
		  } catch (Throwable t) {
			  t.printStackTrace(Access.getConsoleWriter(myAccess));
			  AuditLog.log("SQLMap", t.getMessage(), Level.SEVERE, myAccess.accessID);
			  throw new MappableException(t.getMessage());
		  }
	  //}
  }

  public void setDebug(boolean b) {
    this.debug = b;
  }
  
  public void cleanupBinaryStreams() {
	  for (int i = 0; i < binaryStreamList.size(); i++) {
		InputStream is = (InputStream)binaryStreamList.get(i);
		try {
			is.close();
		} catch (Throwable e) {
			e.printStackTrace(Access.getConsoleWriter(myAccess));
		}
	}
	  binaryStreamList.clear();
  }

  public void load(Access access) throws MappableException, UserException {
    // Check whether property file sqlmap.properties exists.
    navajoConfig = DispatcherFactory.getInstance().getNavajoConfig();
    myAccess = access;
    setReload("");
    if (debug) {
    	Access.writeToConsole(myAccess, "LEAVING SQLMAP load()...");
    }
  }

  public void setDatasource(String s) {
    datasource = s;
  }

  /**
   * Possibility to explictly rollback transactions, by calling kill setKill.
   * 
   * @param b
   */
  public void setKill(boolean b) {
	  if ( b ) {
		  kill();
	  }
  }
  
  public void kill() {
	  
	cleanupBinaryStreams();
	  
    if (autoCommitMap.get(this.datasource) == null) {
      if ( debug ) {
    	  Access.writeToConsole(myAccess, "Did not find autoCommitMap entry for: " + datasource + "\n");
      }
      return;
    }

    try {
      boolean ac = (this.overideAutoCommit) ? autoCommit :
          ( (Boolean) autoCommitMap.get(datasource)).booleanValue();
      if ( debug ) {
    	  Access.writeToConsole(myAccess, "Autocommit flag for " + datasource + " = " + ac + "\n");
      }
      if (!ac) {
        if (con != null) {
          try {
        	  Access.writeToConsole(myAccess, "ROLLBACK OF TRANSACTION " + getTransactionContext() + " DUE TO KILL.....\n");
		  } catch (UserException e) {	
			  e.printStackTrace(Access.getConsoleWriter(myAccess));
		  }
		  if ( debug ) {
			  Access.writeToConsole(myAccess, "CALLING TRANSACTION ROLLBACK...\n");
	      }
          con.rollback();
          if ( debug ) {
        	  Access.writeToConsole(myAccess, "DONE!\n");
	      }
        }
      }
      // Set autoCommit mode to default value.
      if ( con != null ) {
    	  if (transactionContext == -1) {
    		  if (con != null) {
    			  transactionContextMap.remove(connectionId + "");
    			  try {
    				  SessionIdentification.clearSessionId( (getMetaData() != null ? getMetaData().getVendor() : "" ), con);
    			  }
    			  catch (UserException ex) {
    			  }
    			  // Free connection.
    			  if ( myConnectionBroker.supportsAutocommit ) {
    				  con.setAutoCommit(true);
    			  }
    			  myConnectionBroker.freeConnection(con);
    			  con = null;
    		  }
    	  }
      }
    }
    catch (SQLException sqle) {
    	AuditLog.log("SQLMap", sqle.getMessage(), Level.SEVERE, myAccess.accessID);
    	sqle.printStackTrace(Access.getConsoleWriter(myAccess));
    }
  }

  public void store() throws MappableException, UserException {
	  
	  cleanupBinaryStreams();
	  // Kill temporary broker.
	  // If part of transaction context, do not free connection or commit changes yet.
	  boolean isClosed = false;
	  try {
		  isClosed = (con != null) ? con.isClosed() : true;
	  }
	  catch (SQLException sqle2) {
	  }
	  if (transactionContext == -1) {
		  if (con != null && !isClosed ) {
			  try {
				  // Determine autocommit value
				  if ( myConnectionBroker == null || myConnectionBroker.supportsAutocommit ) {
					  boolean ac = (this.overideAutoCommit) ? autoCommit :  ( (Boolean) autoCommitMap.get(datasource)).booleanValue();
					  if (!ac) {
						  con.commit();
						  //System.err.println("SQLMAP, CALLING COMMIT() FOR AUTOCOMMIT = FALSE CONNECTION");
						  // Set autoCommit mode to default value.
					  }
					  con.setAutoCommit(true);
				  }
				  //System.err.println("SQLMAP, SETTING AUTOCOMMIT TO TRUE AGAIN");
			  }
			  catch (SQLException sqle) {
				  AuditLog.log("SQLMap", sqle.getMessage(), Level.SEVERE, myAccess.accessID);
				  throw new UserException( -1, sqle.getMessage(), sqle);
			  }
			  if (transactionContextMap != null) {
				  transactionContextMap.remove(connectionId + "");
			  }
			  if (fixedBroker != null) {
				  SessionIdentification.clearSessionId(getMetaData() != null ? getMetaData().getVendor() : "", con);
			  }
		  }
		  if ( fixedBroker != null && con != null && myConnectionBroker != null ) {
			  // Free connection.
			  myConnectionBroker.freeConnection(con);
			  con = null;
		  }
	  }
	  con = null;
  }

  public void setTransactionIsolationLevel(int j) {
    transactionIsolation = j;
  }

  public void setAutoCommit(boolean b) throws UserException {
	  try {
		  createConnection();
		  if ( con != null && ( myConnectionBroker == null || myConnectionBroker.supportsAutocommit ) ) {
			  if ( !autoCommit ) {
				  con.commit(); // Commit previous actions.
			  }
			  this.autoCommit = b;
			  con.setAutoCommit(b);
		  }
	  }
	  catch (SQLException sqle) {
		  AuditLog.log("SQLMap", sqle.getMessage(), Level.SEVERE, myAccess.accessID);
		  throw new UserException( -1, sqle.getMessage(), sqle);
	  }
	  overideAutoCommit = true;
  }

  public void setTransactionContext(int i) throws UserException {

    if (debug) {
    	Access.writeToConsole(myAccess, "IN SETTRANSACTIONCONTEX(), I = " + i + "\n");
    }
    this.transactionContext = i;
    // Get a shared connection from the transactionContextMap.
    // System.err.println("in setTransactionContex(), id = " + i);
    con = (Connection)transactionContextMap.get(i + "");

    if (debug) {
    	Access.writeToConsole(myAccess, "CON = " + con + "\n");
    }
    
    if (con == null) {
    	AuditLog.log("SQLMap", "Invalid transaction context: " + i, Level.SEVERE,myAccess.accessID);
      throw new UserException( -1, "Invalid transaction context set");
    }
  }

  /**
   * Set the total elements in a lazy array (as a result from a previous operation), to prevent recalculation.
   * @throws UserException
   */
  public void setTotalElements(String name, int t) throws UserException {
    this.lazyTotal = t;
  }

  public int getTotalElements() throws UserException {
    return getTotalElements("");
  }

  public int getTotalElements(String s) throws UserException {
    //System.err.println("in getTotalElements(" + s+ ")");
    if (resultSet == null) {
      getResultSet();
    }
    // If endIndex is set, determine row count first.
    //System.err.println("CALCULATE ROWCOUNT...........................................................................");
    if (lazyTotal == 0) { // lazyTotal has not been set from outside.
      if (viewCount <= (getEndIndex(s) - getStartIndex(s))) {
        lazyTotal = viewCount;
      }
      else {
        lazyTotal = getTotalRows();
      }
    }
    return this.lazyTotal;
  }

  public int getCurrentElements(String s) {
    return this.viewCount;
  }

  public int getRemainingElements(String s) throws UserException {
    if (debug) {
    	Access.writeToConsole(myAccess, "in getRemainingElements(" + s + ")\n");
    }
    getTotalElements(s);
    if (debug) {
    	Access.writeToConsole(myAccess, "in getRemainingElements()\n");
    	Access.writeToConsole(myAccess, "startIndex = " + startIndex + "\n");
    	Access.writeToConsole(myAccess, "endIndex = " + endIndex + "\n");
    	Access.writeToConsole(myAccess, "shownElements = " + viewCount + "\n");
    	Access.writeToConsole(myAccess, "totalElements = " + lazyTotal + "\n");
    	Access.writeToConsole(myAccess, "remainingElements = " + (lazyTotal - endIndex) + "\n");
    }
    int remaining = (lazyTotal - endIndex);
    return (remaining > 0 ? remaining : 0);
  }

  public void setRowCount(int i) {
    this.rowCount = i;
  }

  public int getRowCount() throws UserException {
    if (resultSet == null) {
      getResultSet();
    }
    return this.rowCount;
  }

  public void setUpdateCount(int i) {
    this.updateCount = 0;
  }

  public int getUpdateCount() throws UserException {
    return (this.updateCount);
  }

  public void setUpdate(final String newUpdate) throws UserException {
    update = newUpdate;
    
    if (debug) {
    	Access.writeToConsole(myAccess, "SQLMap(): update = " + update + "\n");
    }
    
    this.savedQuery = newUpdate;
    this.resultSet = null;
    this.query = null;
    parameters = new ArrayList();
  }

  public final void setDoUpdate(final boolean doit) throws UserException {
    this.getResultSet(true);
  }

  public final void setResultSetIndex(int index) {
    this.resultSetIndex = index;
  }

  public final Object getColumnValue() throws UserException {
    throw new UserException( -1, "Use $columnValue('[name of the column]')");
  }

  public final Object getColumnName(final Integer index) throws UserException {

    if (resultSet == null) {
      getResultSet();
    }
    if ( (resultSet == null) || (resultSet.length == 0)) {
      throw new UserException( -1, "No records found");
    }

    ResultSetMap rm = resultSet[resultSetIndex];
    return rm.getColumnName(index);

  }

  public Object getColumnValue(final Integer index) throws UserException {

    if (resultSet == null) {
      getResultSet();
    }
    if ( (resultSet == null) || (resultSet.length == 0)) {
      throw new UserException( -1, "No records found");
    }

    ResultSetMap rm = resultSet[resultSetIndex];
    return rm.getColumnValue(index);

  }

  public Object getColumnValue(final String columnName) throws UserException {
    if (resultSet == null) {
      getResultSet();

    }
    if ( (resultSet == null) || (resultSet.length == 0)) {
      throw new UserException( -1, "No records found");
    }

    ResultSetMap rm = resultSet[resultSetIndex];

    return rm.getColumnValue(columnName);
  }

  /**
   * Use this method to define a new query.
   * All parameters used by a previous query are removed.
   * replace " characters with ' characters.
   */
  public void setQuery(final String newQuery) throws UserException {
	  
	if ( newQuery.indexOf(";") != -1 ) {
		throw new UserException(-1, "Use of semicolon in query fields is not allowed, maybe you meant to use an update field?");
	}
	
    query = newQuery.replace('"', ( this.replaceQueryDoubleQuotes ) ? '\'' : '\"');
    if (debug) {
    	Access.writeToConsole(myAccess, "SQLMap(): query = " + query + "\n");
    }
    this.savedQuery = query;
    this.resultSet = null;
    this.update = null;
    parameters = new ArrayList();
  }

  /**
   * Set multiple parameter using a single string. Parameters MUST be seperated by semicolons (;).
   *
   * @param param contains the parameter(s). Multiple parameters are support for string types.
   */
  public final void setMultipleParameters(final Object param) {
    if (debug) {
    	Access.writeToConsole(myAccess, "in setParameters(), param = " + param + " (" +
                         ( (param != null) ? param.getClass().getName() : "") +
                         ")\n");
    }
    if (parameters == null) {
      parameters = new ArrayList();
    }
    if ( (param != null) && (param instanceof String)
        && ( ( (String) param).indexOf(";") != -1)) {
      java.util.StringTokenizer tokens = new java.util.StringTokenizer( (String)
          param, ";");

      while (tokens.hasMoreTokens()) {
        parameters.add(tokens.nextToken());
      }
    }
    else {
      parameters.add(param);
    }
  }

  /**
   * Setting (a single) parameter of a SQL query.
   *
   * @param param the parameter.
   */
  public void setParameter(final Object param) {
    if (debug) {
    	Access.writeToConsole(myAccess, "in setParameter(), param = " + param + " (" +
                         ( (param != null) ? param.getClass().getName() : "") +
                         ")\n");
    }
    if (parameters == null) {
      parameters = new ArrayList();
    }
    /**
         if ( (param != null) && (param instanceof String)
        && ( ( (String) param).indexOf(";") != -1)) {
         java.util.StringTokenizer tokens = new java.util.StringTokenizer( (String)
          param, ";");
      while (tokens.hasMoreTokens()) {
        parameters.add(tokens.nextToken());
      }
         }
         else {**/
    parameters.add(param);
    /*}**/
  }

  protected final static synchronized DbConnectionBroker createConnectionBroker(
      String driver, String url, String username, String password,
      int min, int max, String logFile, double refreshRate) throws
      UserException {
    DbConnectionBroker db = null;

    try {
      db = new DbConnectionBroker(driver, url, username, password, min, max,
                                  logFile, refreshRate);
    }
    catch (Exception e) {
      e.printStackTrace();
      AuditLog.log("SQLMap", e.getMessage(), Level.SEVERE);
      throw new UserException( -1,
                              "Could not create connectiobroker: " +
                              "[driver = " +
                              driver + ", url = " + url + ", username = '" +
                              username + "', password = '" + password + "']:" +
                              e.getMessage());
    }
    return db;
  }

  protected final String getType(int i) {
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

  public void setKillConnection() {
    if (con != null) {
      try {
        System.err.println("Trying to close connection .... (NOT YET IMPLEMENTED!)");
        //con.close();
        //System.err.println("... Done!");
      }
      catch (Throwable ex) {
        ex.printStackTrace(Access.getConsoleWriter(myAccess));
      }
    }
  }

  protected final void createConnection() throws SQLException, UserException {

    if (this.debug) {
    	Access.writeToConsole(myAccess, this.getClass() + ": in createConnection()\n");
    }

    if (con == null) { // Create connection if it does not yet exist.

      if (this.debug) {
    	  Access.writeToConsole(myAccess, "in createConnection() for datasource " + datasource +" and username " + username + "\n");
      }

      if (fixedBroker == null || fixedBroker.get(this.datasource, this.username, this.password) == null) {
        throw new UserException( -1,
                                "Could not create connection to datasource " +
                                this.datasource + ", using username " +
                                this.username);
      }

      myConnectionBroker = fixedBroker.get(this.datasource, this.username, this.password);
      con = myConnectionBroker.getConnection();

      if (con == null) {
    	  AuditLog.log("SQLMap", "Could not connect to database: " + datasource +  ", one more try with fresh broker....", Level.WARNING, myAccess.accessID);
        
        Message msg = configFile.getMessage("/datasources/" + datasource);
        try {
          createDataSource(msg, navajoConfig);
        }
        catch (NavajoException ne) {
        	 AuditLog.log("SQLMap", ne.getMessage(), Level.SEVERE);
          throw new UserException( -1, ne.getMessage());
        } catch (Throwable t) {
        	AuditLog.log("SQLMap", t.getMessage(), Level.SEVERE);
          throw new UserException( -1, t.getMessage());
        }
        myConnectionBroker = fixedBroker.get(this.datasource, this.username, this.password);
        con = myConnectionBroker.getConnection();
        if (con == null) {
        	AuditLog.log("SQLMap",  "Could (still) not connect to database: " + datasource + " (" + this.username + ")" +
                    ", check your connection", Level.SEVERE);
         
          throw new UserException( -1,
                                  "Could not connect to database: " +
                                  datasource + " (" + this.username + ")" +
                                  ", check your connection");
        }
      }
      else {
        if (this.debug) {
        	Access.writeToConsole(myAccess, this.getClass() +
              ": returned a good connection from the broker manager\n");
        }
      }
      if (con != null && ( myConnectionBroker == null || myConnectionBroker.supportsAutocommit ) ) {
        boolean ac = (this.overideAutoCommit) ? autoCommit : ( (Boolean) autoCommitMap.get(datasource)).booleanValue();
        if ( !ac ) {
        	con.commit();
        }
        con.setAutoCommit(ac);
        //con.setHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT);
        if (transactionIsolation != -1) {
          con.setTransactionIsolation(transactionIsolation);
        }
        // Set session identification.
        SessionIdentification.setSessionId(this.getMetaData() != null ? this.getMetaData().getVendor() : "Unknown", con, this.myAccess);
      }
    }
    if ( (this.con != null) && (this.connectionId == -1)) {
      this.connectionId = con.hashCode();
      transactionContextMap.put(connectionId + "", con);
      if (this.debug) {
    	  Access.writeToConsole(myAccess, this.getClass() + ": put connection no. " +
                           this.connectionId + " into the connection map\n");
      }
    }
  }

  public final int getTransactionContext() throws UserException {
    try {
      createConnection();
    }
    catch (SQLException sqle) {
      sqle.printStackTrace(Access.getConsoleWriter(myAccess));
      throw new UserException( -1, sqle.getMessage());
    }
    if (debug) {
    	Access.writeToConsole(myAccess, "IN GETTRANSACTIONCONTEXT(), CONNECTIONID = " +connectionId + "\n");
    }
    return (this.connectionId);
  }

  private final void setStatementParameters(PreparedStatement statement) throws
      java.sql.SQLException {
    if (parameters != null) {
      // System.err.println("parameters = " + parameters);
      for (int i = 0; i < parameters.size(); i++) {
        Object param = parameters.get(i);

        // System.err.println("parameter " + i + " = " + param);
        if (param == null) {
          statement.setNull(i + 1, Types.VARCHAR);
        }
        else if (param instanceof String) {
          statement.setString(i + 1, (String) param);
        }
        else if (param instanceof Integer) {
          statement.setInt(i + 1, ( (Integer) param).intValue());
        }
        else if (param instanceof Double) {
          statement.setDouble(i + 1, ( (Double) param).doubleValue());
        }
        else if (param instanceof Percentage ) {
        	 statement.setDouble(i + 1, ( (Percentage) param).doubleValue());
        }
        else if (param instanceof java.util.Date) {
          java.sql.Date sqlDate = new java.sql.Date( ( (java.util.Date) param).
              getTime());
          statement.setDate(i + 1, sqlDate);
        }
        else if (param instanceof Boolean) {
          statement.setBoolean(i + 1, ( (Boolean) param).booleanValue());
        }
        else if (param instanceof ClockTime) {
          java.sql.Timestamp sqlDate = new java.sql.Timestamp( ( (ClockTime) param).dateValue().getTime());
          statement.setTimestamp(i + 1, sqlDate);
        }
        else if (param instanceof Money) {
          statement.setDouble(i + 1, ( (Money) param).doubleValue());
        }
        else if (param instanceof Memo) {
          String memoString = ( (Memo) param).toString();
		statement.setCharacterStream(i + 1, new StringReader(memoString),memoString.length());
        }
        else if (param instanceof Binary) {
          Binary b = (Binary)param;
          setBlob(statement, i, b);
          if (debug) {
        	  Access.writeToConsole(myAccess, "ADDED BLOB\n");
          }
        } else {
        	throw new SQLException("Unknown type encountered in SQLMap.setStatementParameters(): " + param);
        }
      }
    }

  }
 
/**
 * BEWARE! Possible resource leak!!! Should the stream be closed?
 * @param statement
 * @param i
 * @param b
 * @throws SQLException
 */
  
protected void setBlob(PreparedStatement statement, int i, Binary b) throws SQLException {
	  InputStream os = b.getDataAsStream();
	  statement.setBinaryStream(i+1,os,(int)b.getLength());
	  
	  // All streams in this list will be closed on kill() or store()
	  binaryStreamList.add(os);
}

  /**
   * NOTE: DO NOT USE THIS METHOD ON LARGE RESULTSETS WITHOUT SETTING ENDINDEX.
   *
   */
  public final ResultSet getDBResultSet(boolean updateOnly) throws SQLException,
      UserException {

    createConnection();

    if (con == null) {
    	AuditLog.log("SQLMap",   "Could not connect to database: " + datasource +
                ", check your connection", Level.SEVERE, myAccess.accessID);
    
      throw new UserException( -1,
          "in SQLMap. Could not open database connection [driver = " + driver +
          ", url = " + url + ", username = '" + username +
          "', password = '" + password + "']");
    }

    if (debug) {
    	Access.writeToConsole(myAccess, "SQLMAP, GOT CONNECTION, STARTING QUERY\n");
    }

    // batch mode?
    this.batchMode = updateOnly &&
        ( (this.query == null) || (this.query.length() == 0)) && (this.update != null) &&
        (this.update.indexOf(SQLBatchUpdateHelper.DELIMITER) > 0);
    if (this.batchMode) {
      if (this.debug) {
    	  Access.writeToConsole(myAccess, this.getClass() +
                           ": detected batch mode, trying a batch update\n");
      }
      this.helper = new SQLBatchUpdateHelper(this.update,
                                             this.con, this.parameters,
                                             this.debug);
      this.updateCount = this.helper.getUpdateCount();
//      this.batchMode = false;
      return (this.helper.getResultSet());
    }

    if (debug) { Access.writeToConsole(myAccess, "BEFORE PREPARESTATEMENT()\n"); }
    
    // Check for open statement.
    if (this.statement != null) {
    	try {
    		this.statement.close();
    	} catch (Exception e) {}
    	this.statement = null;
    }

    if (query != null) {
      this.statement = con.prepareStatement(query);
    }
    else {
      this.statement = con.prepareStatement(update);
    }
    openResultSets++;
    if (debug) { Access.writeToConsole(myAccess, "AFTER PREPARESTATEMENT(), SETTING MAXROWS...\n"); }

    if (endIndex != INFINITE) {
      this.statement.setMaxRows(this.endIndex);
      //this.statement.setFetchSize(endIndex);
    }

    if (debug) { Access.writeToConsole(myAccess, "SET MAXROWS DONE..SETTING STATEMENT PARAMETERS\n"); }
    setStatementParameters(statement);

    ResultSet rs = null;

    if (updateOnly) {
      this.statement.executeUpdate();
    }
    else {
      try {
        if (debug) { Access.writeToConsole(myAccess, "CALLING EXECUTEQUERY()\n"); }
        rs = this.statement.executeQuery();

        if (debug) { Access.writeToConsole(myAccess, "GOT RESULTSET!!!!!\n"); }
      }
      catch (SQLException e) {
    	  if (rs != null) {
    	        rs.close();
    	        rs = null;
    	      }
        if (rs != null) {
          resetAll();
        }
       
        // For Sybase compatibility: sybase does not like to be called using executeQuery() if query does not return a resultset.
        if (e.getMessage().indexOf("JZ0R2") == -1) {
          e.printStackTrace(Access.getConsoleWriter(myAccess));
          throw e;
        }
      }

    }
    this.updateCount = this.statement.getUpdateCount();

    // dump any SQL warnings
    if (debug) {
      SQLWarning warning = this.statement.getWarnings();
      while (warning != null) {
    	  Access.writeToConsole(myAccess, "SQL warning: " + warning.getMessage() + "\n");
        warning = warning.getNextWarning();
      }
    }

    // Set row to startIndex value.
    //rs.setFetchDirection(ResultSet.TYPE_SCROLL_INSENSITIVE);
    //rs.absolute(startIndex);

    return rs;
  }

  public Connection getConnection() throws java.sql.SQLException {
    try {
      createConnection();
      return this.con;
    }
    catch (com.dexels.navajo.server.UserException ue) {
    	ue.printStackTrace(Access.getConsoleWriter(myAccess));
      return null;
    }
  }

  public ResultSetMap[] getResultSet() throws UserException {

    if (resultSet == null) {
      return getResultSet(false);
    }
    else {
      return resultSet;
    }
  }

  protected ResultSetMap[] getResultSet(boolean updateOnly) throws
      UserException {

    requestCount++;
    ResultSet rs = null;

    long start = 0;
    if ( debug || timeAlert > 0) {
      start = System.currentTimeMillis();
    }

    try {

      if (resultSet == null) {
        rs = getDBResultSet(updateOnly);
      }

      if (debug) {
    	  Access.writeToConsole(myAccess, "SQLMAP, QUERY HAS BEEN EXECUTED, RETRIEVING RESULTSET\n");
      }

      if (rs != null) {

        int columns = 0;
        ResultSetMetaData meta = null;
        try {
          meta = rs.getMetaData();
          columns = meta.getColumnCount();
        }
        catch (Exception e) {

        }
        ArrayList dummy = new ArrayList();
        int index = 1;
        remainCount = 0;
        rowCount = 0;

        try {

          while (rs.next()) {

            if ( (index >= startIndex) &&
                ( (endIndex == INFINITE) || (index <= endIndex))) {
              ResultSetMap rm = new ResultSetMap();

              for (int i = 1; i < (columns + 1); i++) {
                String param = meta.getColumnLabel(i);
                int type = meta.getColumnType(i);


                
                Object value = null;
                final String strVal = rs.getString(i);

                if ( (strVal != null && !rs.wasNull()) || type == Types.BLOB) {
                  switch (type) {

                    case Types.BINARY:
                    case Types.BLOB:
                    case -4:
                      InputStream is = rs.getBinaryStream(i);
                      if (is != null) {
                        value = new Binary(is);
                      } else {
                        value = null;
                      }
                      break;

                    case Types.INTEGER:
                    case Types.SMALLINT:
                    case Types.TINYINT:
                      value = new Integer(rs.getInt(i));
                      break;

                    case Types.CHAR:
                    case Types.VARCHAR:
                      if (rs.getString(i) != null) {
                        value = new String(rs.getString(i));
                      }
                      break;

                    case Types.NUMERIC:

                      int prec = meta.getPrecision(i);
                      int scale = meta.getScale(i);

                      //if (debug) System.err.println(i + ", prec = " + prec + ", scale =  " + scale);
                      if (scale <= 0) {
                        value = new Integer(rs.getInt(i));
                      }
                      else {
                        value = new Double(rs.getString(i));
                      }
                      break;

                    case Types.DECIMAL:
                    case Types.FLOAT:
                    case Types.DOUBLE:

                      value = new Double(rs.getString(i));
                      break;

                    case Types.DATE:
                      if (rs.getDate(i) != null) {

                        long l = -1;
                        try {
                          Date d = rs.getDate(i);
                          l = d.getTime();
                        }
                        catch (Exception e) {
                          Date d = rs.getDate(i);
                          l = d.getTime();
                        }

                        value = new java.util.Date(l);
                      }

                      break;

                    case -101: // For Oracle; timestamp with timezone, treat this as clocktime.
                      if (rs.getTimestamp(i) != null) {

                        long l = -1;
                        try {
                          Timestamp ts = rs.getTimestamp(i);
                          l = ts.getTime();
                        }
                        catch (Exception e) {
                          Date d = rs.getDate(i);
                          l = d.getTime();
                        }

                        value = new ClockTime(new java.util.Date(l));
                      }

                      break;

                    case Types.TIMESTAMP:
                      if (rs.getTimestamp(i) != null) {

                        long l = -1;
                        try {
                          Timestamp ts = rs.getTimestamp(i);
                          l = ts.getTime();
                        }
                        catch (Exception e) {
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
                      if (rs.getString(i) != null) {
                        value = new String(rs.getString(i));
                      }
                      break;
                  }
                }
                else {
                }
                rm.addValue(param.toUpperCase(), value);
              }
              dummy.add(rm);
              viewCount++;
            }
            //else if (index >= startIndex) {
            //  remainCount++;
            //}
            rowCount++;
            index++;
          }

        }
        catch (Exception e) {
          /*************************************************
           this is a bit of a kludge,
           for batch mode, we'll poke ahead to see if we
           really do have a result set, otherwise, just
           set it to null.
           *************************************************/

          if (debug) {
        	  Access.writeToConsole(myAccess, 
                "batch mode did not provide a fully baked result set, sorry.\n");
        	  Access.writeToConsole(myAccess, "SQL exception is '" + e.toString() + "'\n");
          }
          if (rs != null) {
        	  rs.close();
        	  rs = null;
          }
          resetAll();

        }
        if (debug) {
        	Access.writeToConsole(myAccess, "GOT RESULTSET\n");
        }
        resultSet = new ResultSetMap[dummy.size()];
        resultSet = (ResultSetMap[]) dummy.toArray(resultSet);
      }
    }
    catch (SQLException sqle) {
      sqle.printStackTrace(Access.getConsoleWriter(myAccess));
      AuditLog.log("SQLMap", sqle.getMessage(), Level.SEVERE, myAccess.accessID);
      throw new UserException( -1, sqle.getMessage());
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

    if ( debug || timeAlert > 0 ) {
      long end = System.currentTimeMillis();
      double total = (end - start) / 1000.0;
      if ( timeAlert > 0 && (int) (end - start) > timeAlert ) {
    	  AuditLogEvent ale =  new AuditLogEvent("SQLMAPTIMEALERT", "Query took " + (end-start) + " millis:\n" +
    			                                 (query != null ? query : update ), Level.WARNING);
    	  ale.setAccessId(myAccess.accessID);
    	  NavajoEventRegistry.getInstance().publishEvent(ale);
      }
      // Log total if needed....
      // totaltiming += total;
    }
    return resultSet;
  }

  protected void resetAll() throws UserException {
    this.query = this.update = null;

    try {
     
      if (this.statement != null) {
        this.statement.close();
        this.statement = null;
        openResultSets--;
      }
      if (this.helper != null) {
        this.helper.closeLast();
        this.helper = null;
      }
      if (this.batchMode) {
        this.batchMode = false;
      }

    }
    catch (Exception e) {
    	AuditLog.log("SQLMap", e.getMessage(), Level.SEVERE, myAccess.accessID);
      throw new UserException( -1, e.getMessage());
    }
    //System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> OPEN RESULT SETS: " + openResultSets);
  }

  /**
   * sets the (absolute) start row number for the resultset to support lazy messaging.
   *
   * @param newStartIndex
   */
  public void setStartIndex(int newStartIndex) {
    startIndex = newStartIndex;
  }

  public void setStartIndex(String s, int newStartIndex) {
    startIndex = newStartIndex;
  }

  private final DatabaseInfo getMetaData() throws UserException {
    if (fixedBroker == null || myConnectionBroker == null) {
      throw new UserException( -1,
                              "Could not create connection to datasource " +
                              this.datasource + ", using username " +
                              this.username);
    }
    return fixedBroker.getMetaData(this.datasource, this.username, this.password);
  }

  public DatabaseInfo getDatabaseInfo() throws UserException {
    DatabaseInfo dmd = getMetaData();
    return dmd;
  }

  public String getDatabaseVersion() throws UserException {

    if (transactionContext != -1) {
      return "See parent map";
    }

    DatabaseInfo dmd = getMetaData();

    if (dmd != null) {
      return dmd.getVersion();
    }
    else {
      return "Not Connected.";
    }

  }

  public String getDatabaseSessionId() throws UserException {
    if (con != null) {
      return SessionIdentification.getSessionIdentification(getMetaData().getVendor(), this.datasource, this.myAccess);
    } else {
      return null;
    }
  }

  public String getDatabaseProductName() throws UserException {

    if (transactionContext != -1) {
      return "See parent map";
    }

    DatabaseInfo dmd = getMetaData();

    if (dmd != null) {
      return dmd.getVendor();
    }
    else {
      return "Not Connected.";
    }

  }

  public void setReplaceQueryDoubleQuotes( boolean b ) {
    this.replaceQueryDoubleQuotes = b;
  }

  public int getStartIndex(String s) {
    return startIndex;
  }

  /**
       * Set the (absolute) end row number for the resultset to support lazy messaging.
   * @param i
   */
  public void setEndIndex(int i) {
    endIndex = i;
  }

  public void setEndIndex(String s, int newEndIndex) {
    endIndex = newEndIndex;
  }

  public int getEndIndex(String s) {
    return endIndex;
  }

  /**
   * Set the database password, should be done before a createconnection() is
   * called.  The password may also be passed at the same time using the
   * designated delimiter
       * @param String containing database user name plus optional password information
   * @throws UserException if we pass an empty string, shouldn't really happen
   * unless you're stupid
   */
  public void setUsername(final String s) throws MappableException,
      UserException {
    final StringTokenizer tokenizer = new StringTokenizer(s,
        this.USERPWDDELIMITER);
    if (!tokenizer.hasMoreTokens()) {
      throw new UserException( -1, "tried to set an empty database user name");
    }
    this.username = tokenizer.nextToken().trim();
    if (this.debug) {
    	Access.writeToConsole(myAccess, this.getClass() + ": set database user name to '" +
                         this.username + "'\n");
    }
    if (tokenizer.hasMoreTokens()) {
      this.password = tokenizer.nextToken().trim();
      if (this.debug) {
    	  Access.writeToConsole(myAccess, this.getClass() +
                           ": set database user password to '" + this.password +
                           "'\n");
      }
    }

    try {
      fixedBroker.put(this.datasource, this.username, this.password);
    }
    catch (ClassNotFoundException e) {
      throw new UserException( -1, e.toString());
    }
    myConnectionBroker = fixedBroker.get(this.datasource, this.username, password);
    this.con = myConnectionBroker.getConnection();
  }

  public String getUsername() {
    return (this.username);
  }

  public String getPassword() {
    return (this.password);
  }

  private void checkDefaultDatasource() {
    if (!fixedBroker.haveSimilarBroker(this.DEFAULTSRCNAME)) {
      final String msg = "Could not create default broker [driver = " +
          driver +
          ", url = " + url + ", username = '" + username +
          "', password = '" + password + "']";

      AuditLog.log("SQLMap", msg, Level.WARNING, myAccess.accessID);
     
      if (debug) {
    	  Access.writeToConsole(myAccess, this.getClass() + ": " + msg + "\n");
      }
    }
  }

  /**
   * Get the total number of rows for the defined query.
   *
   * @return
   */
  public final int getTotalRows() {

    //savedQuery = savedQuery.toUpperCase();
    if (debug) { Access.writeToConsole(myAccess, "savedQuery is " + savedQuery + "\n"); }

    savedQuery = savedQuery.replaceAll("[fF][rR][oO][Mm]", "FROM");
    savedQuery = savedQuery.replaceAll("[Oo][rR][dD][eE][rR]", "ORDER");

    String countQuery = "SELECT count(*) " +
        savedQuery.substring(savedQuery.lastIndexOf("FROM"),
                             (savedQuery.indexOf("ORDER") != -1 ?
                              savedQuery.lastIndexOf("ORDER") :
                              savedQuery.length()));

    PreparedStatement count = null;
    ResultSet rs = null;
    int total = 0;

    try {
      createConnection();

      if (debug) { Access.writeToConsole(myAccess, "Executing count query: " + countQuery + "......\n"); }
      count = con.prepareStatement(countQuery);
      this.setStatementParameters(count);
      rs = count.executeQuery();

      total = 0;
      if (rs.next()) {
        total = rs.getInt(1);
      }
      if (debug) { Access.writeToConsole(myAccess, "Result = " + total + "\n"); }

    }
    catch (Exception e) {
      e.printStackTrace(Access.getConsoleWriter(myAccess));
    }
    finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (count != null) {
          count.close();
        }
      }
      catch (SQLException sqle) {
        sqle.printStackTrace(Access.getConsoleWriter(myAccess));
      }
    }

    return total;
  }

  public static void main(String[] args) throws Exception {

    String query =
        " SELECT " +
        "       lid.eigenaar RegionOwner, lid.roep_nm FirstName, lid.voorletters FirstInitials, lid.tussenvoegsel Infix," +
        "       lid.achter_nm LastName, lid.meisjes_nm MaidenName, vereniging.ver_nm ClubName, lid.relatie_cd MemberIdentifier," +
        "       lid.geb_dt BirthDate, lid.overlijdens_dt DateOfPassing, lid.geb_pl BirthPlace, lid.geslacht Gender," +
        "       lid.burgelijke_staat MaritalStatus, lid.lnd_cd BirthCountryCode,lid.aanmeld_dt MemberRegistrationDate," +
        "       lid.afmeld_dt MemberDeregistrationDate, geb_land.lnd_nm BirthCountryName, relatie.adres StreetName,     " +
        "       relatie.huis_nr AddressNumber, relatie.huisnr_toev AddressNumberAppendix, relatie.postcode ZipCode,    " +
        "       relatie.woonplaats City, relatie.lnd_cd AddressCountryCode, woon_land.lnd_nm AddressCountryName,     " +
        "       verenigings_lid.aanmeld_dt ClubRegistrationDate, verenigings_lid.afmeld_dt ClubDeregistrationDate,    " +
        "       lid.tel_werk FaxNumber, lid.tel_werk_2 EmailAddress, relatie.rel_tel_2 MobilePhone, relatie.rel_tel HomePhone," +
        "       lid.tel_cd TelephoneType," +
        "       lid.contrib_bet isContributionPaid, verenigings_lid.contrib_cd ContributionCode," +
        "       verenigings_lid.bet_wijze_cd PaymentMethod, verenigings_lid.bet_per_cd PaymentPeriod," +
        "       relatie.bankgiro BankAccountNumber, relatie.tenaamstelling Ascription," +
        "       relatie.pl_tenaamstelling AscriptionPlace, verenigings_lid.geroyeerd_j_n isExpelled," +
        "       verenigings_lid.royement_reden ExpelledReason" +
        "       FROM" +
        "       vereniging, verenigings_lid, relatie," +
        "       land geb_land, land woon_land, lid" +
        "       WHERE " +
        "       verenigings_lid.relatie_cd = vereniging.relatie_cd AND " +
        "       verenigings_lid.rel_cd = lid.relatie_cd AND lid.relatie_cd = relatie.relatie_cd AND " +
        "       lid.lnd_cd = geb_land.lnd_cd AND relatie.lnd_cd = woon_land.lnd_cd AND vereniging.relatie_cd = ?";
    query = query.replaceAll("[fF][rR][oO][Mm]", "FROM");
    query = query.replaceAll("[Oo][rR][dD][eE][rR]", "ORDER");
    query = "SELECT count(*) " + query.substring(query.lastIndexOf("FROM"),
                                                 (query.indexOf("ORDER") != -1 ?
                                                  query.lastIndexOf("ORDER") :
                                                  query.length()));

    System.err.println(query);
  }

  public String getQuery() {
    // replace parameters.
    String dbQuery = savedQuery;
    if (this.parameters != null) {
      StringBuffer queryWithParameters = new StringBuffer(dbQuery.length());
      int index = 0;
      for (int i = 0; i < dbQuery.length(); i++) {
        if (dbQuery.charAt(i) != '?') {
          queryWithParameters.append(dbQuery.charAt(i));
        }
        else {
          Object o = parameters.get(index++);
          if (o instanceof String && o != null) {
            queryWithParameters.append("'" + o.toString() + "'");
          }
          else {
            if (o != null) {
              queryWithParameters.append(o.toString());
            } else {
              queryWithParameters.append("null");
            }
          }
        }
      }
      return queryWithParameters.toString();
    }
    else {
      return query;
    }
  }

  public String getDatasource() {
    if (transactionContext != -1) {
      return "See parent map";
    }

    return datasource;
  }

  public int getConnectionId() {
    return connectionId;
  }

  public boolean isAutoCommit() {
    return autoCommit;
  }
  
  /**
   * Get all records from resultset as Binary object (x-separated file)
   * @return
   */
  public Binary getRecords() throws UserException {

	  java.io.File tempFile = null;
	  ResultSet rs = null;
	  try {
		  Binary b = null;
		  rs = getDBResultSet(false);

		  tempFile = File.createTempFile("sqlmap_records", "navajo");
		  FileOutputStream fos = new FileOutputStream( tempFile );
		  OutputStreamWriter fw = new OutputStreamWriter( fos, "UTF-8" );

		  int columns = 0;
		  ResultSetMetaData meta = null;

		  try {
			  meta = rs.getMetaData();
			  columns = meta.getColumnCount();

			  if ( this.showHeader ) {
				  for (int j = 0; j < columns; j++) {
					  String column = meta.getColumnLabel(j+1);
					  if ( j == 0 ) {
						  fw.write(column);
					  } else {
						  fw.write(this.separator + column);
					  }
				  }
				  fw.write("\n");
			  }
		  }
		  catch (Exception e) {
			  e.printStackTrace(Access.getConsoleWriter(myAccess));
		  }

		  while ( rs.next() ) {
			  for (int j = 1; j <= columns; j++) {
				  String value = ( rs.getObject(j)  != null ?  rs.getString(j) +"" : "");
				  if ( j == 1) {
					  fw.write(value);
				  } else {
					  fw.write(this.separator + value);
				  }
			  }
			  fw.write("\n");
		  }
		  fw.flush();
		  fw.close();

		  b = new Binary( tempFile, false );

		  if ( fos != null ) {
			  fos.close();
		  }

		  return b;
	  } catch ( Exception ioe ) {
		  throw new UserException( -1, ioe.getMessage(), ioe );
	  } finally {
		  if ( rs != null ) {
			  try {
				  rs.close();
				  rs = null;
				  resetAll();
			  } catch (SQLException e) {
				  // TODO Auto-generated catch block
				  e.printStackTrace(Access.getConsoleWriter(myAccess));
			  }
		  }
		  if ( tempFile != null ) {
			  try {
				  tempFile.delete();
			  } catch (Exception ioe2 ) {
				  ioe2.printStackTrace(Access.getConsoleWriter(myAccess));
			  }
		  }
	  }
  }

  /**
   * Sets the separator for the Binary CSV (see getRecords())
   * 
   * @param s
   */
  public void setSeparator(String s) {
	this.separator = s;  
  }
  
  /**
   * controls the inclusion of a header row in the Binary CSV (see getRecords())
   * @param b
   */
  public void setShowHeader(boolean b) {
	  this.showHeader = b;
  }

  /**
   * METADATA INFORMATION.
   */
  
  public DependentResource [] getDependentResourceFields() {
	  return new DependentResource[]{new GenericDependentResource("database", "datasource", AdapterFieldDependency.class), 
			                        new GenericMultipleDependentResource("sql", "update", SQLFieldDependency.class), 
			                        new GenericMultipleDependentResource("sql", "query", SQLFieldDependency.class)};
  }

public int getTimeAlert() {
	return timeAlert;
}

public void setTimeAlert(int timeAlert) {
	this.timeAlert = timeAlert;
}
  
}
