package com.dexels.navajo.adapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;

import org.dexels.grus.DbConnectionBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.sqlmap.ConnectionBrokerManager;
import com.dexels.navajo.adapter.sqlmap.DatabaseInfo;
import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.adapter.sqlmap.SQLBatchUpdateHelper;
import com.dexels.navajo.adapter.sqlmap.SQLMapConstants;
import com.dexels.navajo.adapter.sqlmap.SQLMapHelper;
import com.dexels.navajo.adapter.sqlmap.SessionIdentification;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Memo;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.NavajoType;
import com.dexels.navajo.document.types.Percentage;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.types.AuditLogEvent;
import com.dexels.navajo.jdbc.JDBCMappable;
import com.dexels.navajo.mapping.Debugable;
import com.dexels.navajo.mapping.DependentResource;
import com.dexels.navajo.mapping.GenericDependentResource;
import com.dexels.navajo.mapping.GenericMultipleDependentResource;
import com.dexels.navajo.mapping.HasDependentResources;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import com.dexels.navajo.mapping.compiler.meta.SQLFieldDependency;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.server.Repository;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.resource.ResourceManager;
import com.dexels.navajo.util.AuditLog;

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

@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class SQLMap implements JDBCMappable, Mappable, HasDependentResources, Debugable {

	protected final static int INFINITE = -1;
	protected final String USERPWDDELIMITER = "/";
	protected final String DEFAULTSRCNAME = "default";

	public boolean debug = false;
	public boolean kill = false;
	public int timeAlert = -1;

	public String driver;
	public String url;

	public String alternativeUsername;
	public String alternativePassword;

	public String username;
	public String password;

	public String update;
	public String query;
	public Binary binaryUpdate;
	public String savedQuery;
	public boolean doUpdate;
	// Set autoCommit to true to overide default settings from sqlmap.xml
	// configuration file!
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
	protected static final Map autoCommitMap = Collections.synchronizedMap(new HashMap());

	private int connectionId = -1;

	protected NavajoConfigInterface navajoConfig = null;

	// handling batch mode, multiple SQL statements
	private boolean batchMode = false;
	private SQLBatchUpdateHelper helper = null;

	private static int openResultSets = 0;
	protected Access myAccess;

	public int instances;
	private boolean updateOnly;
	private boolean isLegacyMode;
	private String dbIdentifier = null;

	private static Object semaphore = new Object();
	private static boolean initialized = false;
  
	
	private final static Logger logger = LoggerFactory.getLogger(SQLMap.class);
	
	public SQLMap() {
		this.isLegacyMode = SQLMapConstants.isLegacyMode();
	}
  
	/**
	 * Try to get the database identifier
	 * @return String
	 */
	public String getDbIdentifier() {
		
		if ( this.myConnectionBroker != null ) {
			return this.myConnectionBroker.getDbIdentifier();
		} else {
			return null;
		}
	}
	
	private void createDataSource(Message body, NavajoConfigInterface config) throws Throwable {
		String dataSourceName = body.getName();

		if (debug) {
			Access.writeToConsole(myAccess, "Creating new datasource: " + dataSourceName + "\n");
		}

		System.err.println("Creating new datasource: " + dataSourceName);

		driver = body.getProperty("driver").getValue(); 
		// NavajoUtils.getPropertyValue(body, "driver", true);
		url = body.getProperty("url").getValue(); 
		// NavajoUtils.getPropertyValue(body, "url", true);

		username = body.getProperty("username").getValue();
		password = body.getProperty("password").getValue();

		String logFile = config.getRootPath() + "/log/" + body.getProperty("logfile").getValue();
		double refresh = Double.parseDouble(body.getProperty("refresh").getValue());

		String min = (body.getProperty("min_connections") != null ? body.getProperty("min_connections").getValue() : ""); 
		// NavajoUtils.getPropertyValue(body, "min_connections", false);
		int minConnections = (min.equals("")) ? 5 : Integer.parseInt(min);
		String max = (body.getProperty("max_connections") != null ? body.getProperty("max_connections").getValue() : "");
		int maxConnections = (max.equals("")) ? 20 : Integer.parseInt(max);
		String autoCommitStr = (body.getProperty("autocommit") != null ? body.getProperty("autocommit").getValue() : "");
		boolean ac = (autoCommitStr.equals("") || autoCommitStr.equalsIgnoreCase("true"));
		String type = (body.getProperty("type") != null ? body.getProperty("type").getValue() : "");

		// DbConnectionBroker myBroker = null;
		autoCommitMap.put(dataSourceName, new Boolean(ac));

		if (fixedBroker.get(dataSourceName, username, password) != null) {
			// transactionContextMap = Collections.synchronizedMap(new
			// HashMap());
			transactionContext = -1;
			con = null;
			if (debug) {
				Access.writeToConsole(myAccess,
						"Killing previous version of broker (" + dataSourceName
								+ ":" + username + ")...\n");
			}
			fixedBroker.destroy(dataSourceName);
			if (debug) {
				Access.writeToConsole(myAccess, "Done!\n");
			}
		}
		try {
			fixedBroker.put(dataSourceName, driver, url, username, password,
					minConnections, maxConnections, logFile, refresh,
					new Boolean(ac), false, type);
		} catch (ClassNotFoundException e) {
			throw new UserException(-1, e.toString(), e);
		}

		String logOutput = "Created datasource: " + dataSourceName + "\n"
				+ "Driver = " + driver + "\n" + "Url = " + url + "\n"
				+ "Username = " + username + "\n" + "Password = " + password
				+ "\n" + "Minimum connections = " + min + "\n"
				+ "Maximum connections = " + max + "\n" + "Autocommit = " + ac
				+ "\n";

	}

	public Object getParameter(int index) {
		if (parameters == null) {
			return null;
		}
		return parameters.get(index);
	}

	public int getInstances() {
		return ConnectionBrokerManager.getInstances();
	}

	public synchronized void setDeleteDatasource(String datasourceName)
			throws MappableException, UserException {

		if (fixedBroker != null) {
			fixedBroker.destroy(datasourceName);
		}
		fixedBroker = null;
	}

	public static void terminateFixedBroker() {
		if (fixedBroker != null) {
			fixedBroker.terminate();
		}
		fixedBroker = null;

	}
  
	/**
	 * 
	 * @param reload
	 */
	public void setReload(String datasourceName) throws MappableException, UserException {

		// synchronized ( semaphore ) {
		if (debug) {
			Access.writeToConsole(myAccess, "SQLMAP setReload(" + datasourceName + ") called!\n");
		}

		try {
			synchronized (semaphore) {
				if (!initialized || !datasourceName.equals("")) {

					if (configFile == null) {
						configFile = navajoConfig.readConfig("sqlmap.xml");

						// If property file exists create a static
						// connectionbroker that can be accessed by multiple
						// instances of
						// SQLMap!!!
						if (fixedBroker == null && datasourceName.equals("")) { // Only
																				// re-create
																				// entire
																				// HashMap
																				// at
																				// initialization!
							fixedBroker = new ConnectionBrokerManager();
						}

						if (datasourceName.equals("")) {
							// Get other data sources.
							ArrayList all = configFile.getMessages("/datasources/.*");
							for (int i = 0; i < all.size(); i++) {
								Message body = (Message) all.get(i);
								createDataSource(body, navajoConfig);
							}
						} else {
							createDataSource(configFile.getMessage("/datasources/" + datasourceName), navajoConfig);
						}
						this.checkDefaultDatasource();
					}
					initialized = true;
				}

			}

			rowCount = 0;
		} catch (NavajoException ne) {
			ne.printStackTrace(Access.getConsoleWriter(myAccess));
			AuditLog.log("SQLMap", ne.getMessage(), Level.SEVERE,
					(myAccess != null ? myAccess.accessID : "unknown access"));
			throw new MappableException(ne.getMessage(), ne);
		} catch (java.io.IOException fnfe) {
			fnfe.printStackTrace(Access.getConsoleWriter(myAccess));
			AuditLog.log("SQLMap", fnfe.getMessage(), Level.SEVERE,
					(myAccess != null ? myAccess.accessID : "unknown access"));
			throw new MappableException(
					"Could not load configuration file for SQLMap object: "
							+ fnfe.getMessage(), fnfe);
		} catch (Throwable t) {
			t.printStackTrace(Access.getConsoleWriter(myAccess));
			AuditLog.log("SQLMap", t.getMessage(), Level.SEVERE,
					(myAccess != null ? myAccess.accessID : ""));
			throw new MappableException(t.getMessage(), t);
		}
		// }
	}

	public void setDebug(boolean b) {
		this.debug = b;
	}

	public void cleanupBinaryStreams() {
		for (int i = 0; i < binaryStreamList.size(); i++) {
			InputStream is = (InputStream) binaryStreamList.get(i);
			try {
				is.close();
			} catch (Throwable e) {
				e.printStackTrace(Access.getConsoleWriter(myAccess));
			}
		}
		binaryStreamList.clear();
	}

	/**
	 * I use this bugger for the batch updates.
	 * 
	 * @return
	 */
	public boolean isUpdateOnly() {
		return updateOnly;
	}

	public void setUpdateOnly(boolean updateOnly) {
		this.updateOnly = updateOnly;
	}

	public void load(Access access) throws MappableException, UserException {
		// Check whether property file sqlmap.properties exists.
		navajoConfig = DispatcherFactory.getInstance().getNavajoConfig();
		myAccess = access;
		setReload("");
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
		if (b) {
			kill();
		}
	}

	public void kill() {

		try {
			if (autoCommitMap.get(this.datasource) == null) {
				return;
			}
			boolean ac = (this.overideAutoCommit) ? autoCommit
					: ((Boolean) autoCommitMap.get(datasource)).booleanValue();
			if (!ac) {
				if (con != null) {
					kill = true;
					con.rollback();
				}
			}
		} catch (SQLException sqle) {
			AuditLog.log("SQLMap", sqle.getMessage(), Level.SEVERE,
					(myAccess != null ? myAccess.accessID : "unknown access"));
			sqle.printStackTrace(Access.getConsoleWriter(myAccess));
		} finally {
			try {
				store();
			} catch (MappableException e) {
			} catch (UserException e) {
			}
		}
	}

	public void store() throws MappableException, UserException {

		cleanupBinaryStreams();

		if (transactionContext == -1) {

			String resetSession = null;
			if (myConnectionBroker != null && this.alternativeUsername != null) {
				if (SQLMapConstants.POSTGRESDB.equals(this.getDbIdentifier())) {
					resetSession = "SET SEARCH_PATH TO " + myConnectionBroker.getUsername();
				} else {
					resetSession = "ALTER SESSION SET CURRENT_SCHEMA = " + myConnectionBroker.getUsername();
				}
			}
			try {
				if (con != null && !con.isClosed()) {
					// if defaultUsername was set, set it back.
					if (resetSession != null) {
						PreparedStatement stmt = con.prepareStatement(resetSession);
						stmt.executeUpdate();
						stmt.close();
					}
					// Determine autocommit value
					if (myConnectionBroker == null || myConnectionBroker.supportsAutocommit) {
						boolean ac = (this.overideAutoCommit) ? autoCommit : ((Boolean) autoCommitMap.get(datasource)).booleanValue();
						if (!ac && !kill) { // Only commit if kill (rollback)
											// was not called.
							con.commit();
						}
						con.setAutoCommit(true);
					}
				}
			} catch (SQLException sqle) {
				System.err.println("COULD NOT RESET SCHEMA");
				System.err.println(resetSession);
				AuditLog.log("SQLMap", sqle.getMessage(), Level.SEVERE, (myAccess != null ? myAccess.accessID : "unknown access"));
				throw new UserException(-1, sqle.getMessage(), sqle);
			} finally {
				if (fixedBroker != null && con != null && myConnectionBroker != null) {
					// Free connection.
					myConnectionBroker.freeConnection(con);
				}
			}
		}

	}

	public void setTransactionIsolationLevel(int j) {
		transactionIsolation = j;
	}

	public void setAutoCommit(boolean b) throws UserException {
		try {
			createConnection();
			if (con != null
					&& (myConnectionBroker == null || myConnectionBroker.supportsAutocommit)) {
				if (!autoCommit) {
					con.commit(); // Commit previous actions.
				}
				this.autoCommit = b;
				con.setAutoCommit(b);
			}
		} catch (SQLException sqle) {
			AuditLog.log("SQLMap", sqle.getMessage(), Level.SEVERE,
					(myAccess != null ? myAccess.accessID : "unknown access"));
			throw new UserException(-1, sqle.getMessage(), sqle);
		}
		overideAutoCommit = true;
	}

	public void setTransactionContext(int i) throws UserException {

		if (debug) {
			Access.writeToConsole(myAccess, "IN SETTRANSACTIONCONTEX(), I = "
					+ i + "\n");
		}
		this.transactionContext = i;

	}

	/**
	 * Set the total elements in a lazy array (as a result from a previous
	 * operation), to prevent recalculation.
	 * 
	 * @throws UserException
	 */
	public void setTotalElements(String name, int t) throws UserException {
		this.lazyTotal = t;
	}

	public int getTotalElements() throws UserException {
		return getTotalElements("");
	}

	public int getTotalElements(String s) throws UserException {
		// System.err.println("in getTotalElements(" + s+ ")");
		if (resultSet == null) {
			getResultSet();
		}
		// If endIndex is set, determine row count first.
		// System.err.println("CALCULATE ROWCOUNT...........................................................................");
		if (lazyTotal == 0) { // lazyTotal has not been set from outside.
			if (viewCount <= (getEndIndex(s) - getStartIndex(s))) {
				lazyTotal = viewCount;
			} else {
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
			Access.writeToConsole(myAccess, "in getRemainingElements(" + s
					+ ")\n");
		}
		getTotalElements(s);
		if (debug) {
			Access.writeToConsole(myAccess, "in getRemainingElements()\n");
			Access.writeToConsole(myAccess, "startIndex = " + startIndex + "\n");
			Access.writeToConsole(myAccess, "endIndex = " + endIndex + "\n");
			Access.writeToConsole(myAccess, "shownElements = " + viewCount
					+ "\n");
			Access.writeToConsole(myAccess, "totalElements = " + lazyTotal
					+ "\n");
			Access.writeToConsole(myAccess, "remainingElements = "
					+ (lazyTotal - endIndex) + "\n");
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

	public void setBinaryUpdate(Binary b) throws UserException {
		String query = new String(b.getData());
		setUpdate(query);
	}

	public void setUpdate(final String newUpdate) throws UserException {
		update = newUpdate;

		if (debug) {
			Access.writeToConsole(myAccess, "SQLMap(): update = " + update
					+ "\n");
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
		throw new UserException(-1, "Use $columnValue('[name of the column]')");
	}

	public final Object getColumnName(final Integer index) throws UserException {

		if (resultSet == null) {
			getResultSet();
		}
		if ((resultSet == null) || (resultSet.length == 0)) {
			throw new UserException(-1, "No records found");
		}

		ResultSetMap rm = resultSet[resultSetIndex];
		return rm.getColumnName(index);

	}

	public Object getColumnValue(final Integer index) throws UserException {

		if (resultSet == null) {
			getResultSet();
		}
		if ((resultSet == null) || (resultSet.length == 0)) {
			throw new UserException(-1, "No records found");
		}

		ResultSetMap rm = resultSet[resultSetIndex];
		return rm.getColumnValue(index);

	}

	public Object getColumnValue(final String columnName) throws UserException {
		if (resultSet == null) {
			getResultSet();

		}
		if ((resultSet == null) || (resultSet.length == 0)) {
			throw new UserException(-1, "No records found");
		}

		ResultSetMap rm = resultSet[resultSetIndex];
		return rm.getColumnValue(columnName);
	}

	public void setBinaryQuery(Binary b) throws UserException {
		String query = new String(b.getData());
		setQuery(query);
	}

	/**
	 * Use this method to define a new query. All parameters used by a previous
	 * query are removed. replace " characters with ' characters.
	 */
	public void setQuery(final String newQuery) throws UserException {

		if (newQuery.indexOf(";") != -1) {
			throw new UserException(
					-1,
					"Use of semicolon in query fields is not allowed, maybe you meant to use an update field?");
		}

		query = newQuery.replace('"', (this.replaceQueryDoubleQuotes) ? '\''
				: '\"');
		if (debug) {
			Access.writeToConsole(myAccess, "SQLMap(): query = " + query + "\n");
		}
		this.savedQuery = query;
		this.resultSet = null;
		this.update = null;
		parameters = new ArrayList();
	}

	/**
	 * Set multiple parameter using a single string. Parameters MUST be
	 * seperated by semicolons (;).
	 * 
	 * @param param
	 *            contains the parameter(s). Multiple parameters are support for
	 *            string types.
	 */
	public final void setMultipleParameters(final Object param) {
		if (debug) {
			Access.writeToConsole(myAccess, "in setParameters(), param = " + param + " (" + ((param != null) ? param.getClass().getName() : "") + ")\n");
		}
		if (parameters == null) {
			parameters = new ArrayList();
		}
		if ((param != null) && (param instanceof String) && (((String) param).indexOf(";") != -1)) {
			java.util.StringTokenizer tokens = new java.util.StringTokenizer((String) param, ";");
			while (tokens.hasMoreTokens()) {
				parameters.add(tokens.nextToken());
			}
		} else {
			parameters.add(param);
		}
	}

	/**
	 * Setting (a single) parameter of a SQL query.
	 * 
	 * @param param
	 *            the parameter.
	 */
	public void setParameter(final Object param) {
		if (debug) {
			Access.writeToConsole(myAccess, "in setParameter(), param = " + param + " (" + ((param != null) ? param.getClass().getName() : "") + ")\n");
		}
		if (parameters == null) {
			parameters = new ArrayList();
		}
		parameters.add(param);
	}

	public void setKillConnection() {
		if (con != null) {
			try {
				if (myConnectionBroker != null) {
					myConnectionBroker.setCloseAll();
				}
			} catch (Throwable ex) {
				ex.printStackTrace(Access.getConsoleWriter(myAccess));
			}
		}
	}

	protected final void createConnection() throws SQLException, UserException {

		if (this.debug) {
			Access.writeToConsole(myAccess, this.getClass() + ": in createConnection()\n");
		}

		if (transactionContext != -1) {

			con = DbConnectionBroker.getConnection(transactionContext);
			if (con == null) {
				throw new UserException(-1, "Invalid transaction context set: " + transactionContext);
			}
			// Set myConnectionBroker.
			myConnectionBroker = DbConnectionBroker.getConnectionBroker(transactionContext);
			// Make sure to set connection id.
			this.connectionId = transactionContext;
		}

		if (con == null) { // Create connection if it does not yet exist.

			if (this.debug) {
				Access.writeToConsole(myAccess, "in createConnection() for datasource " + datasource + " and username " + username + "\n");
			}

			myConnectionBroker = null;
			if (fixedBroker != null) {
				myConnectionBroker = fixedBroker.get(this.datasource, null, null);
			}

			if (myConnectionBroker == null) {
				throw new UserException(-1,
						"Could not create connection to datasource "
								+ this.datasource + ", using username "
								+ this.username + ", fixedBroker = "
								+ fixedBroker);
			}

			con = myConnectionBroker.getConnection();

			if (con == null) {
				// AuditLog.log("SQLMap", "Could not connect to database: " +
				// datasource + ", one more try with fresh broker....",
				// Level.WARNING, (myAccess != null ? myAccess.accessID :
				// "unknown access"));
				//
				// Message msg = configFile.getMessage("/datasources/" +
				// datasource);
				// try {
				// createDataSource(msg, navajoConfig);
				// }
				// catch (NavajoException ne) {
				// AuditLog.log("SQLMap", ne.getMessage(), Level.SEVERE);
				// throw new UserException( -1, ne.getMessage());
				// } catch (Throwable t) {
				// AuditLog.log("SQLMap", t.getMessage(), Level.SEVERE);
				// throw new UserException( -1, t.getMessage());
				// }
				// myConnectionBroker = fixedBroker.get(this.datasource, null,
				// null);
				// con = myConnectionBroker.getConnection();
				// if (con == null) {
				AuditLog.log("SQLMap",
						"Could (still) not connect to database: " + datasource
								+ " (" + this.username + ")"
								+ ", check your connection", Level.SEVERE);

				throw new UserException(-1, "Could not connect to database: "
						+ datasource + " (" + this.username + ")"
						+ ", check your connection");
				// }
			} else {
				if (this.debug) {
					Access.writeToConsole(myAccess, this.getClass() + ": returned a good connection from the broker manager\n");
				}
			}

			// Set current schema if username was specified...
			if (this.alternativeUsername != null) { // Only works for Oracle...
				try {
					// Now set current_schema...
					PreparedStatement stmt = null;
					if (SQLMapConstants.POSTGRESDB.equals(this.getDbIdentifier())) {
						stmt = con.prepareStatement("SET SEARCH_PATH TO " + this.alternativeUsername);
					} else {
						stmt = con.prepareStatement("ALTER SESSION SET CURRENT_SCHEMA = " + this.alternativeUsername);
					}
					stmt.executeUpdate();
					stmt.close();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}

			if (con != null && (myConnectionBroker == null || myConnectionBroker.supportsAutocommit)) {
				boolean ac = (this.overideAutoCommit) ? autoCommit : ((Boolean) autoCommitMap.get(datasource)).booleanValue();
				if (!ac) {
					con.commit();
				}
				con.setAutoCommit(ac);
				// con.setHoldability(ResultSet.CLOSE_CURSORS_AT_COMMIT);
				if (transactionIsolation != -1) {
					con.setTransactionIsolation(transactionIsolation);
				}
				// Set session identification.
				SessionIdentification.setSessionId(this.getMetaData() != null ? this.getMetaData().getVendor() : "Unknown", con, this.myAccess);
			}

			if (this.con != null) {
				this.connectionId = con.hashCode();
				if (this.debug) {
					Access.writeToConsole(myAccess, this.getClass()
							+ ": put connection no. " + this.connectionId
							+ " into the connection map\n");
				}
			}
		}

	}

	public final int getTransactionContext() throws UserException {
		try {
			createConnection();
		} catch (SQLException sqle) {
			sqle.printStackTrace(Access.getConsoleWriter(myAccess));
			throw new UserException(-1, sqle.getMessage());
		}
		if (debug) {
			Access.writeToConsole(myAccess, "IN GETTRANSACTIONCONTEXT(), CONNECTIONID = " + connectionId + "\n");
		}
		return (this.connectionId);
	}

	private final void setStatementParameters(PreparedStatement statement) throws java.sql.SQLException {
		if (parameters != null) {
			// System.err.println("parameters = " + parameters);
			for (int i = 0; i < parameters.size(); i++) {
				Object param = parameters.get(i);
				SQLMapHelper.setParameter(statement, 
										  param, 
										  i,
										  this.getClass(),
										  this.getDbIdentifier(),
										  this.isLegacyMode, 
										  this.debug, 
										  this.myAccess);
			}
		}
	}

	/**
	 * NOTE: DO NOT USE THIS METHOD ON LARGE RESULTSETS WITHOUT SETTING
	 * ENDINDEX.
	 * 
	 */
	public final ResultSet getDBResultSet(boolean updateOnly) throws SQLException, UserException {

		createConnection();

		if (con == null) {
			AuditLog.log("SQLMap", "Could not connect to database: "
					+ datasource + ", check your connection", Level.SEVERE,
					(myAccess != null ? myAccess.accessID : "unknown access"));

			throw new UserException(-1,
					"in SQLMap. Could not open database connection [driver = "
							+ driver + ", url = " + url + ", username = '"
							+ username + "', password = '" + password + "']");
		}

		if (debug) {
			Access.writeToConsole(myAccess, "SQLMAP, GOT CONNECTION, STARTING QUERY\n");
		}

		// batch mode?
		this.batchMode = updateOnly
				&& ((this.query == null) || (this.query.length() == 0))
				&& (this.update != null)
				&& (this.update.indexOf(SQLBatchUpdateHelper.DELIMITER) > 0);
		if (this.batchMode) {
			if (this.debug) {
				Access.writeToConsole(myAccess, this.getClass() + ": detected batch mode, trying a batch update\n");
			}
			this.helper = new SQLBatchUpdateHelper(this.update, 
												   this.con,
												   this.parameters, 
												   this.myAccess,
												   this.getDbIdentifier(),
												   this.getClass(), 
												   this.isLegacyMode, 
												   this.debug,
												   updateOnly);
			this.updateCount = this.helper.getUpdateCount();
			// this.batchMode = false;
			return (this.helper.getResultSet());
		}

		if (debug) {
			Access.writeToConsole(myAccess, "BEFORE PREPARESTATEMENT()\n");
		}

		// Check for open statement.
		if (this.statement != null) {
			try {
				this.statement.close();
			} catch (Exception e) {
			}
			this.statement = null;
		}

		if (query != null) {
			this.statement = con.prepareStatement(query);
		} else {
			this.statement = con.prepareStatement(update);
		}
		openResultSets++;
		if (debug) {
			Access.writeToConsole(myAccess, "AFTER PREPARESTATEMENT(), SETTING MAXROWS...\n");
		}

		if (endIndex != INFINITE) {
			this.statement.setMaxRows(this.endIndex);
			// this.statement.setFetchSize(endIndex);
		}

		if (debug) {
			Access.writeToConsole(myAccess, "SET MAXROWS DONE..SETTING STATEMENT PARAMETERS\n");
		}
		setStatementParameters(statement);

		ResultSet rs = null;

		if (updateOnly) {
			this.statement.executeUpdate();
		} else {
			try {
				if (debug) {
					Access.writeToConsole(myAccess, "CALLING EXECUTEQUERY()\n");
				}
				rs = this.statement.executeQuery();

				if (debug) {
					Access.writeToConsole(myAccess, "GOT RESULTSET!!!!!\n");
				}
			} catch (SQLException e) {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (rs != null) {
					resetAll();
				}

				// For Sybase compatibility: sybase does not like to be called
				// using executeQuery() if query does not return a resultset.
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
		// rs.setFetchDirection(ResultSet.TYPE_SCROLL_INSENSITIVE);
		// rs.absolute(startIndex);

		return rs;
	}

	public Connection getConnection() throws java.sql.SQLException {
		try {
			createConnection();
			return this.con;
		} catch (com.dexels.navajo.server.UserException ue) {
			ue.printStackTrace(Access.getConsoleWriter(myAccess));
			return null;
		}
	}

	public ResultSetMap[] getResultSet() throws UserException {

		if (resultSet == null) {
			return getResultSet(false);
		} else {
			return resultSet;
		}
	}

	protected ResultSetMap[] getResultSet(boolean updateOnly) throws UserException {
		requestCount++;
		ResultSet rs = null;

		long start = 0;
		if (debug || timeAlert > 0) {
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
				} catch (Exception e) {

				}
				ArrayList dummy = new ArrayList();
				int index = 1;
				remainCount = 0;
				rowCount = 0;

				try {

					while (rs.next()) {

						if ((index >= startIndex) && ((endIndex == INFINITE) || (index <= endIndex))) {
							ResultSetMap rm = new ResultSetMap();

							for (int i = 1; i < (columns + 1); i++) {
								String param = meta.getColumnLabel(i);
								int type = meta.getColumnType(i);

								Object value = null;
//								final String strVal = rs.getString(i);
								
//								if ((strVal != null && !rs.wasNull()) || type == Types.BLOB) {
									value = SQLMapHelper.getColumnValue(rs, type, i);
//								}
								rm.addValue(param.toUpperCase(), value);
							}
							dummy.add(rm);
							viewCount++;
						}
						// else if (index >= startIndex) {
						// remainCount++;
						// }
						rowCount++;
						index++;
					}

				} catch (Exception e) {
					/*************************************************
					 * this is a bit of a kludge, for batch mode, we'll poke
					 * ahead to see if we really do have a result set,
					 * otherwise, just set it to null.
					 *************************************************/

					if (debug) {
						Access.writeToConsole(myAccess, "batch mode did not provide a fully baked result set, sorry.\n");
						Access.writeToConsole(myAccess, "SQL exception is '" + e.toString() + "'\n");
						logger.info("Sql problem that might or might not be a an actual problem: ",e);
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
		} catch (SQLException sqle) {
			sqle.printStackTrace(Access.getConsoleWriter(myAccess));
			AuditLog.log("SQLMap", sqle.getMessage(), Level.SEVERE, (myAccess != null ? (myAccess != null ? myAccess.accessID : "unknown access") : "unknown access"));
			throw new UserException(-1, sqle.getMessage(), sqle);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace(Access.getConsoleWriter(myAccess));
				}
				rs = null;
			}
			this.resetAll();
		}

		if (debug || timeAlert > 0) {
			long end = System.currentTimeMillis();
			double total = (end - start) / 1000.0;
			if (timeAlert > 0 && (int) (end - start) > timeAlert) {
				AuditLogEvent ale = new AuditLogEvent("SQLMAPTIMEALERT", "Query took " + (end - start) + " millis:\n" + (query != null ? query : update), Level.WARNING);
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

		} catch (Exception e) {
			AuditLog.log("SQLMap", e.getMessage(), Level.SEVERE, (myAccess != null ? myAccess.accessID : "unknown access"));
			throw new UserException(-1, e.getMessage());
		}
		// System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> OPEN RESULT SETS: "
		// + openResultSets);
	}

	/**
	 * sets the (absolute) start row number for the resultset to support lazy
	 * messaging.
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
			throw new UserException(-1,
					"Could not create connection to datasource "
							+ this.datasource + ", using username "
							+ this.username);
		}
		return fixedBroker.getMetaData(this.datasource, null, null);
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
		} else {
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
		} else {
			return "Not Connected.";
		}

	}

	public void setReplaceQueryDoubleQuotes(boolean b) {
		this.replaceQueryDoubleQuotes = b;
	}

	public int getStartIndex(String s) {
		return startIndex;
	}

	/**
	 * Set the (absolute) end row number for the resultset to support lazy
	 * messaging.
	 * 
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
	 * called. The password may also be passed at the same time using the
	 * designated delimiter
	 * 
	 * @param String
	 *            containing database user name plus optional password
	 *            information
	 * @throws UserException
	 *             if we pass an empty string, shouldn't really happen unless
	 *             you're stupid
	 */
	public void setUsername(final String s) throws MappableException, UserException {
		final StringTokenizer tokenizer = new StringTokenizer(s, this.USERPWDDELIMITER);
		if (!tokenizer.hasMoreTokens()) {
			throw new UserException(-1, "tried to set an empty database user name");
		}
		this.alternativeUsername = tokenizer.nextToken().trim();
		if (this.debug) {
			Access.writeToConsole(myAccess, this.getClass()
					+ ": set database user name to '"
					+ this.alternativeUsername + "'\n");
		}
		if (tokenizer.hasMoreTokens()) {
			this.alternativePassword = tokenizer.nextToken().trim();
			if (this.debug) {
				Access.writeToConsole(myAccess, this.getClass()
						+ ": set database user password to '"
						+ this.alternativePassword + "'\n");
			}
		}

	}

	public String getUsername() {
		if (this.alternativeUsername != null) {
			return this.alternativeUsername;
		}
		return (this.username);
	}

	public String getPassword() {
		if (this.alternativePassword != null) {
			return this.alternativePassword;
		}
		return (this.password);
	}

	private void checkDefaultDatasource() {
		if (!fixedBroker.haveSimilarBroker(this.DEFAULTSRCNAME)) {
			final String msg = "Could not create default broker [driver = "
					+ driver + ", url = " + url + ", username = '" + username
					+ "', password = '" + password + "']";

			AuditLog.log("SQLMap", msg, Level.WARNING,
					(myAccess != null ? myAccess.accessID : "unknown access"));

			if (debug) {
				Access.writeToConsole(myAccess, this.getClass() + ": " + msg
						+ "\n");
			}
		}
	}

	/**
	 * Get the total number of rows for the defined query.
	 * 
	 * @return
	 */
	public final int getTotalRows() {
		// savedQuery = savedQuery.toUpperCase();
		if (debug) {
			Access.writeToConsole(myAccess, "savedQuery is " + savedQuery
					+ "\n");
		}

		savedQuery = savedQuery.replaceAll("[fF][rR][oO][Mm]", "FROM");
		savedQuery = savedQuery.replaceAll("[Oo][rR][dD][eE][rR]", "ORDER");

		String countQuery = "SELECT count(*) "
				+ savedQuery.substring(
						savedQuery.lastIndexOf("FROM"),
						(savedQuery.indexOf("ORDER") != -1 ? savedQuery
								.lastIndexOf("ORDER") : savedQuery.length()));

		PreparedStatement count = null;
		ResultSet rs = null;
		int total = 0;

		try {
			createConnection();

			if (debug) {
				Access.writeToConsole(myAccess, "Executing count query: " + countQuery + "......\n");
			}
			count = con.prepareStatement(countQuery);
			this.setStatementParameters(count);
			rs = count.executeQuery();

			total = 0;
			if (rs.next()) {
				total = rs.getInt(1);
			}
			if (debug) {
				Access.writeToConsole(myAccess, "Result = " + total + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace(Access.getConsoleWriter(myAccess));
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (count != null) {
					count.close();
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace(Access.getConsoleWriter(myAccess));
			}
		}

		return total;
	}

	public static void main(String[] args) throws Exception {

	}

	public String getQuery() {
		// replace parameters.
		String dbQuery = savedQuery;
		if (this.parameters != null) {
			StringBuffer queryWithParameters = new StringBuffer(
					dbQuery.length());
			int index = 0;
			for (int i = 0; i < dbQuery.length(); i++) {
				if (dbQuery.charAt(i) != '?') {
					queryWithParameters.append(dbQuery.charAt(i));
				} else {
					Object o = parameters.get(index++);
					if (o instanceof String && o != null) {
						queryWithParameters.append("'" + o.toString() + "'");
					} else {
						if (o != null) {
							queryWithParameters.append(o.toString());
						} else {
							queryWithParameters.append("null");
						}
					}
				}
			}
			return queryWithParameters.toString();
		} else {
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
	 * 
	 * @return
	 */
	public Binary getRecords() throws UserException {
		java.io.File tempFile = null;
		ResultSet rs = null;
		try {
			Binary b = null;
			rs = getDBResultSet(false);

			tempFile = File.createTempFile("sqlmap_records", "navajo");
			FileOutputStream fos = new FileOutputStream(tempFile);
			OutputStreamWriter fw = new OutputStreamWriter(fos, "UTF-8");

			int columns = 0;
			ResultSetMetaData meta = null;

			try {
				meta = rs.getMetaData();
				columns = meta.getColumnCount();

				if (this.showHeader) {
					for (int j = 0; j < columns; j++) {
						String column = meta.getColumnLabel(j + 1);
						if (j == 0) {
							fw.write(column);
						} else {
							fw.write(this.separator + column);
						}
					}
					fw.write("\n");
				}
			} catch (Exception e) {
				e.printStackTrace(Access.getConsoleWriter(myAccess));
			}

			while (rs.next()) {
				for (int j = 1; j <= columns; j++) {
					String value = (rs.getObject(j) != null ? rs.getString(j)
							+ "" : "");
					if (j == 1) {
						fw.write(value);
					} else {
						fw.write(this.separator + value);
					}
				}
				fw.write("\n");
			}
			fw.flush();
			fw.close();

			b = new Binary(tempFile, false);

			if (fos != null) {
				fos.close();
			}

			return b;
		} catch (Exception ioe) {
			throw new UserException(-1, ioe.getMessage(), ioe);
		} finally {
			if (rs != null) {
				try {
					rs.close();
					rs = null;
					resetAll();
				} catch (SQLException e) {
					e.printStackTrace(Access.getConsoleWriter(myAccess));
				}
			}
			if (tempFile != null) {
				try {
					tempFile.delete();
				} catch (Exception ioe2) {
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
	 * controls the inclusion of a header row in the Binary CSV (see
	 * getRecords())
	 * 
	 * @param b
	 */
	public void setShowHeader(boolean b) {
		this.showHeader = b;
	}

	public String getDatasourceUrl(String datasource, String username) {
		if (fixedBroker != null) {
			return fixedBroker.getDatasourceUrl(datasource);
		} else {
			return null;
		}
	}

	/**
	 * METADATA INFORMATION.
	 */
	public DependentResource[] getDependentResourceFields() {
		return new DependentResource[] {
				new GenericDependentResource("database", "datasource", AdapterFieldDependency.class),
				new GenericMultipleDependentResource("sql", "update", SQLFieldDependency.class),
				new GenericMultipleDependentResource("sql", "query", SQLFieldDependency.class) 
				};
	}

	public static ResourceManager getResourceManager(String resourceType) {
		if (resourceType.equals("database")) {
			return fixedBroker;
		}
		return null;
	}

	public int getTimeAlert() {
		return timeAlert;
	}

	public void setTimeAlert(int timeAlert) {
		this.timeAlert = timeAlert;
	}

	public boolean getDebug() {
		return this.debug;
	}
	
	public void addToBinaryStreamList(InputStream binaryStream) {
		binaryStreamList.add(binaryStream);
	}
	public ArrayList getBinaryStreamList() {
		return binaryStreamList;
	}
}
