package com.dexels.navajo.adapter.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Types;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.resource.jdbcbroker.JdbcResourceComponent;
import com.dexels.navajo.adapter.sqlmap.DatabaseInfo;
import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.adapter.sqlmap.SQLBatchUpdateHelper;
import com.dexels.navajo.adapter.sqlmap.SQLMapConstants;
import com.dexels.navajo.adapter.sqlmap.SQLMapHelper;
import com.dexels.navajo.adapter.sqlmap.StreamClosable;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.jdbc.JDBCMappable;
import com.dexels.navajo.mapping.DependentResource;
import com.dexels.navajo.mapping.GenericDependentResource;
import com.dexels.navajo.mapping.GenericMultipleDependentResource;
import com.dexels.navajo.mapping.HasDependentResources;
import com.dexels.navajo.mapping.compiler.meta.AdapterResourceDependency;
import com.dexels.navajo.mapping.compiler.meta.SQLFieldDependency;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Debugable;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.util.AuditLog;


@SuppressWarnings({"rawtypes", "unchecked", "unused"})
public class JDBCMap implements Mappable, HasDependentResources, Debugable, JDBCMappable,StreamClosable {
	private static final Logger logger = LoggerFactory.getLogger(JDBCMap.class);
	protected final String DEFAULTSRCNAME = "default";
	private String dbIdentifier = null;
	private boolean debug = false;
	private boolean updateOnly;
	private boolean isLegacyMode;
	private String update;
	private String query;
	private String savedQuery;
	private boolean autoCommit = true;
	private boolean replaceQueryDoubleQuotes = true;

	private int rowCount = 0;
	private int updateCount = 0;
	private ResultSetMap[] resultSet = null;
	private int transactionContext = -1;
	private String separator = ";";
	private boolean showHeader = true;
	private Connection con = null;
	private PreparedStatement statement = null;
	private ArrayList parameters = null;
	private final ArrayList binaryStreamList = new ArrayList();
	private boolean batchMode = false;
	private SQLBatchUpdateHelper helper = null;

	private String datasource = this.DEFAULTSRCNAME;
	private int connectionId = -1;
	protected Access myAccess;
	public int resultSetIndex = 0;
	private boolean ownContext = false;
	private String username;

	public JDBCMap() {
		this.isLegacyMode = SQLMapConstants.isLegacyMode();
	}
  

	/*
	 * (non-Javadoc)
	 * @see com.dexels.navajo.adapter.JDBCMappable#getParameter(int)
	 */
	@Override
	public Object getParameter(int index) {
		if (parameters == null) {
			return null;
		}
		return parameters.get(index);
	}
  

	/*
	 * (non-Javadoc)
	 * @see com.dexels.navajo.adapter.JDBCMappable#setDebug(boolean)
	 */
	@Override
	public void setDebug(boolean b) {
		this.debug = b;
	}
  
	private void cleanupBinaryStreams() {
		for (int i = 0; i < binaryStreamList.size(); i++) {
			InputStream is = (InputStream) binaryStreamList.get(i);
			try {
				is.close();
			} catch (Throwable e) {
				logger.error("Error cleaning up streams", e);
			}
		}
		binaryStreamList.clear();
	}


	/*
	 * (non-Javadoc)
	 * @see
	 * com.dexels.navajo.adapter.JDBCMappable#load(com.dexels.navajo.server.
	 * Access)
	 */
	@Override
	public void load(Access access) throws MappableException, UserException {
		myAccess = access;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.dexels.navajo.adapter.JDBCMappable#setDatasource(java.lang.String)
	 */
	@Override
	public void setDatasource(String s) {
		datasource = s;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dexels.navajo.adapter.JDBCMappable#setKill(boolean)
	 */
	@Override
	public void setKill(boolean b) {
		if (b) {
			kill();
		}
	}
  
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.adapter.JDBCMappable#kill()
	 */
	@Override
	public void kill() {

		try {
			// if (autoCommitMap.get(this.datasource) == null) {
			// return;
			// }
			// boolean ac = (this.overideAutoCommit) ? autoCommit : ( (Boolean)
			// autoCommitMap.get(datasource)).booleanValue();
			// if (!ac) {
			if (con != null) {
				// kill = true;
				if(!autoCommit) {
					con.rollback();
				}
			}
			// }
		} catch (SQLException sqle) {
			AuditLog.log("SQLMap", sqle.getMessage(), Level.SEVERE, (myAccess != null ? myAccess.accessID : "unknown access"));
			sqle.printStackTrace(Access.getConsoleWriter(myAccess));
		} finally {
			try {
				store();
			} catch (MappableException e) {
			} catch (UserException e) {
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dexels.navajo.adapter.JDBCMappable#store()
	 */
	@Override
	public void store() throws MappableException, UserException {
		cleanupBinaryStreams();

		if (transactionContext != -1 && ownContext) {
			logger.info(":::Creating transactioncontext: " + transactionContext);
			JdbcResourceComponent.getInstance().deregisterTransaction(transactionContext);
			if (con != null) {
				if(autoCommit==false) {
					try {
						con.commit();
					} catch (SQLException e) {
						logger.error("Problem committing connection", e);
					}
				}
				try {
					con.close();
				} catch (SQLException e) {
					logger.error("Problem closing pooled connection", e);
				}
			}
		}
	}


	/*
	 * (non-Javadoc)
	 * @see com.dexels.navajo.adapter.JDBCMappable#setTransactionContext(int)
	 */
	@Override
	public void setTransactionContext(int i) throws UserException {
		if (debug) {
			Access.writeToConsole(myAccess, "IN SETTRANSACTIONCONTEX(), I = " + i + "\n");
		}
		this.transactionContext = i;
		this.con = JdbcResourceComponent.getInstance().getJdbc(i);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dexels.navajo.adapter.JDBCMappable#setRowCount(int)
	 */
	@Override
	public void setRowCount(int i) {
		this.rowCount = i;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dexels.navajo.adapter.JDBCMappable#getRowCount()
	 */
	@Override
	public int getRowCount() throws UserException {
		if (resultSet == null) {
			resultSet = getResultSet();
		}
		return this.rowCount;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dexels.navajo.adapter.JDBCMappable#setUpdateCount(int)
	 */
	@Override
	public void setUpdateCount(int i) {
		this.updateCount = 0;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dexels.navajo.adapter.JDBCMappable#getUpdateCount()
	 */
	@Override
	public int getUpdateCount() throws UserException {
		return (this.updateCount);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dexels.navajo.adapter.JDBCMappable#setUpdate(java.lang.String)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * @see com.dexels.navajo.adapter.JDBCMappable#setDoUpdate(boolean)
	 */
	@Override
	public final void setDoUpdate(final boolean doit) throws UserException {
		this.getResultSet(true);
	}

  /* (non-Javadoc)
 * @see com.dexels.navajo.adapter.JDBCMappable#getColumnName(java.lang.Integer)
 */
@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.adapter.JDBCMappable#getColumnValue(java.lang.Integer)
	 */
	@Override
	public Object getColumnValue(final Integer index) throws UserException {
		if (resultSet == null) {
			getResultSet();
		}
		if ((resultSet == null) || (resultSet.length == 0)) {
			throw new UserException(-1, "No records found for query: " + getQuery());
		}

		ResultSetMap rm = resultSet[resultSetIndex];
		return rm.getColumnValue(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.adapter.JDBCMappable#getColumnValue(java.lang.String)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.adapter.JDBCMappable#setBinaryQuery(com.dexels.navajo
	 * .document.types.Binary)
	 */
	@Override
	public void setBinaryQuery(Binary b) throws UserException {
		String query = new String(b.getData());
		setQuery(query);
	}
  

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.adapter.JDBCMappable#setQuery(java.lang.String)
	 */
	@Override
	public void setQuery(final String newQuery) throws UserException {
		if (newQuery.indexOf(";") != -1) {
			throw new UserException(-1, "Use of semicolon in query fields is not allowed, maybe you meant to use an update field?");
		}

		query = newQuery.replace('"', (this.replaceQueryDoubleQuotes) ? '\'' : '\"');
		if (debug) {
			Access.writeToConsole(myAccess, "SQLMap(): query = " + query + "\n");
		}
		this.savedQuery = query;
		this.resultSet = null;
		this.update = null;
		parameters = new ArrayList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.adapter.JDBCMappable#setMultipleParameters(java.lang
	 * .Object)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.adapter.JDBCMappable#setParameter(java.lang.Object)
	 */
	@Override
	public void setParameter(final Object param) {
		if (debug) {
			Access.writeToConsole(myAccess, "in setParameter(), param = " + param + " (" + ((param != null) ? param.getClass().getName() : "") + ")\n");
		}
		if (parameters == null) {
			parameters = new ArrayList();
		}
		parameters.add(param);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.adapter.JDBCMappable#setKillConnection()
	 */
	@Override
	public void setKillConnection() {
		if (con != null) {
			try {
				// ?
				con.close();
			} catch (Throwable ex) {
				// ex.printStackTrace(Access.getConsoleWriter(myAccess));
				logger.error("Error killing connection", ex);
			}
		}
	}

	protected final void createConnection() throws SQLException, UserException {

		if (this.debug) {
			Access.writeToConsole(myAccess, this.getClass() + ": in createConnection()\n");
		}

		if (transactionContext != -1) {
			con = JdbcResourceComponent.getInstance().getJdbc(transactionContext);
			if (con == null) {
				throw new UserException(-1, "Invalid transaction context set: " + transactionContext);
			}

			// Make sure to set connection id.
			this.connectionId = transactionContext;
		}

		if (con == null) { // Create connection if it does not yet exist.

			if (this.debug) {
				Access.writeToConsole(myAccess, "in createConnection() for datasource " + datasource);
			}

			DataSource jdbc = JdbcResourceComponent.getJdbc(datasource);

			// if(jdbc instanceof ConnectionPoolDataSource) {
			// logger.info("USING POOL");
			// ConnectionPoolDataSource cpds = (ConnectionPoolDataSource)jdbc;
			// this.currentPooledConnection = cpds.getPooledConnection();
			// con = currentPooledConnection.getConnection();
			// } else {
			con = jdbc.getConnection();
			// }

			setDbIdentifier(con);

			if (this.username != null) {
				String query = "ALTER SESSION SET CURRENT_SCHEMA = " + username;
				if (this.dbIdentifier.equals(SQLMapConstants.POSTGRESDB)) {
					query = "SET SEARCH_PATH TO " + username;
				}
				PreparedStatement ps = con.prepareStatement(query);
				ps.executeUpdate();
				ps.close();
				logger.info("Username set to: " + this.username);
			}

			if (con == null) {
				AuditLog.log("SQLMap", "Could (still) not connect to database: " + datasource + ", check your connection", Level.SEVERE);
				throw new UserException(-1, "Could not connect to database: " + datasource + " ()" + ", check your connection");
			} else {
				if (this.debug) {
					Access.writeToConsole(myAccess, this.getClass() + ": returned a good connection from the broker manager\n");
				}
			}

			this.transactionContext = con.hashCode();
			logger.info(":::Creating transactioncontext: " + transactionContext);
			this.ownContext = true;
			// con = pooledConnection.getConnection();
			JdbcResourceComponent.getInstance().registerTransaction(con.hashCode(), con);


			this.connectionId = con.hashCode();
			if (this.debug) {
				Access.writeToConsole(myAccess, this.getClass() + ": put connection no. " + this.connectionId + " into the connection map\n");
			}
		}

	}
  
	/**
	 * Sets the dbIdentifier which will be used to create certain deviations in
	 * the code
	 * 
	 * @param con
	 * @throws SQLException
	 */
	private void setDbIdentifier(Connection con) throws SQLException {
		this.dbIdentifier = "";
		if (con != null) {
			DatabaseMetaData dbmd = con.getMetaData();
			if (dbmd.getDriverName().contains("Postgres")) {
				this.dbIdentifier = SQLMapConstants.POSTGRESDB;
			}
		}
	}

	private DatabaseInfo getMetaData(Connection c) throws SQLException {
		DatabaseMetaData dbmd = c.getMetaData();
		boolean supportsAutocommit = dbmd.supportsTransactions();
		return new DatabaseInfo(dbmd, this.datasource);
	}


	private final void setStatementParameters(PreparedStatement statement) throws java.sql.SQLException {
		if (parameters != null) {
			// System.err.println("parameters = " + parameters);
			for (int i = 0; i < parameters.size(); i++) {
				Object param = parameters.get(i);
				SQLMapHelper.setParameter(statement, 
										  param, 
										  i,
										  this, 
										  this.dbIdentifier,
										  this.isLegacyMode, 
										  this.debug, 
										  this.myAccess);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.adapter.JDBCMappable#getDBResultSet(boolean)
	 */
	@Override
	public final ResultSet getDBResultSet(boolean updateOnly) throws SQLException, UserException {
		createConnection();

		if (con == null) {
			AuditLog.log("SQLMap", "Could not connect to database: " + datasource + ", check your connection", Level.SEVERE, (myAccess != null ? myAccess.accessID : "unknown access"));
			throw new UserException(-1, "in SQLMap. Could not open database connection']");
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
												   this.dbIdentifier,
												   this, 
												   this.isLegacyMode, 
												   this.debug,
												   updateOnly);
			this.updateCount = this.helper.getUpdateCount();
			this.batchMode = false;
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
		if (debug) {
			Access.writeToConsole(myAccess, "AFTER PREPARESTATEMENT(), SETTING MAXROWS...\n");
		}

		if (debug) {
			Access.writeToConsole(myAccess, "SET MAXROWS DONE..SETTING STATEMENT PARAMETERS\n");
		}
		setStatementParameters(statement);

		ResultSet rs = null;

		if (updateOnly) {
			this.statement.executeUpdate();
		} else {
			if (debug) {
				Access.writeToConsole(myAccess, "CALLING EXECUTEQUERY()\n");
			}
			rs = this.statement.executeQuery();

			if (debug) {
				Access.writeToConsole(myAccess, "GOT RESULTSET!!!!!\n");
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


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.adapter.JDBCMappable#getResultSet()
	 */
	@Override
	public ResultSetMap[] getResultSet() throws UserException {
		if (resultSet == null) {
			return getResultSet(false);
		} else {
			return resultSet;
		}
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

	protected ResultSetMap[] getResultSet(boolean updateOnly) throws UserException {
		ResultSet rs = null;

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
					throw new UserException(-1, "Error retrieving metadata / columncount", e);
				}
				ArrayList dummy = new ArrayList();
				int index = 1;
				rowCount = 0;

				try {

					while (rs.next()) {

						ResultSetMap rm = new ResultSetMap();

						for (int i = 1; i < (columns + 1); i++) {
							String param = meta.getColumnLabel(i);
							int type = meta.getColumnType(i);

							Object value = null;
							final Object strVal = rs.getObject(i);
							// final String strVal = rs.getString(i);

							if ((strVal != null && !rs.wasNull()) || type == Types.BLOB) {
								value = SQLMapHelper.getColumnValue(rs, type, i);
							}
							rm.addValue(param.toUpperCase(), value);
						}
						dummy.add(rm);
					}
					// else if (index >= startIndex) {
					// remainCount++;
					// }
					rowCount++;
					index++;

				} catch (Exception e) {
					/*************************************************
					 * this is a bit of a kludge, for batch mode, we'll poke
					 * ahead to see if we really do have a result set,
					 * otherwise, just set it to null.
					 *************************************************/

					if (debug) {
						Access.writeToConsole(myAccess, "batch mode did not provide a fully baked result set, sorry.\n");
						Access.writeToConsole(myAccess, "SQL exception is '" + e.toString() + "'\n");
						logger.warn("Some sql problem: ", e);
					}
					rs.close();
					rs = null;
					resetAll();

				}
				if (debug) {
					Access.writeToConsole(myAccess, "GOT RESULTSET. Size: " + dummy.size() + " indexcounter says: " + index + "\n");
				}
				resultSet = new ResultSetMap[dummy.size()];
				resultSet = (ResultSetMap[]) dummy.toArray(resultSet);
				rowCount = resultSet.length;
			}
		} catch (SQLException sqle) {
			logger.error("SQL Problem: ");
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

		return resultSet;
	}

	protected void resetAll() throws UserException {
		this.query = this.update = null;

		try {

			if (this.statement != null) {
				this.statement.close();
				this.statement = null;
			}

		} catch (Exception e) {
			AuditLog.log("SQLMap", e.getMessage(), Level.SEVERE, (myAccess != null ? myAccess.accessID : "unknown access"));
			throw new UserException(-1, e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.adapter.JDBCMappable#setReplaceQueryDoubleQuotes(boolean
	 * )
	 */
	@Override
	public void setReplaceQueryDoubleQuotes(boolean b) {
		this.replaceQueryDoubleQuotes = b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.adapter.JDBCMappable#getQuery()
	 */
	@Override
	public String getQuery() {
		// replace parameters.
		String dbQuery = savedQuery;
		if (this.parameters != null) {
			StringBuffer queryWithParameters = new StringBuffer(dbQuery.length());
			int index = 0;
			for (int i = 0; i < dbQuery.length(); i++) {
				if (dbQuery.charAt(i) != '?') {
					queryWithParameters.append(dbQuery.charAt(i));
				} else {
					Object o = parameters.get(index++);
					if (o instanceof String ) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.adapter.JDBCMappable#getDatasource()
	 */
	@Override
	public String getDatasource() {
		if (transactionContext != -1) {
			return "See parent map";
		}
		return datasource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.adapter.JDBCMappable#getConnectionId()
	 */
	@Override
	public int getConnectionId() {
		return connectionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.adapter.JDBCMappable#isAutoCommit()
	 */
	@Override
	public boolean isAutoCommit() {
		return autoCommit;
	}
  
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.adapter.JDBCMappable#getRecords()
	 */
	@Override
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
				logger.error("Error writing output binary", e);
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
			fos.close();
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
					logger.error("Error writing output binary", e);
				}
			}
			if (tempFile != null) {
				try {
					tempFile.delete();
				} catch (Exception ioe2) {
					logger.error("Error writing output binary", ioe2);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.adapter.JDBCMappable#setSeparator(java.lang.String)
	 */
	@Override
	public void setSeparator(String s) {
		this.separator = s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.adapter.JDBCMappable#setShowHeader(boolean)
	 */
	@Override
	public void setShowHeader(boolean b) {
		this.showHeader = b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.adapter.JDBCMappable#getDependentResourceFields()
	 */
	@Override
	public DependentResource[] getDependentResourceFields() {
		return new DependentResource[] {
				new GenericDependentResource("resource", "datasource",
						AdapterResourceDependency.class),
				new GenericMultipleDependentResource("sql", "update",
						SQLFieldDependency.class),
				new GenericMultipleDependentResource("sql", "query",
						SQLFieldDependency.class) };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dexels.navajo.adapter.JDBCMappable#getDebug()
	 */
	@Override
	public boolean getDebug() {
		return this.debug;
	}

	/**
	 * For interface-compatibility with SQLMap
	 */
	@Override
	public void setUsername(final String s) throws MappableException, UserException {
		// ignore completely
		String[] elements = s.split("/");
		this.username = elements[0];
		logger.info("Username set to: " + this.username);
	}

	// dummy for now
	public int getTransactionContext() {
		if (con == null) {
			try {
				createConnection();
			} catch (SQLException e) {
				logger.error("Error: ", e);
			} catch (UserException e) {
				logger.error("Error: ", e);
			}
		}
		return transactionContext;
	}

	@Override
	public void setEndIndex(int i) {
		logger.warn("setEndIndex not properly implemented in JDBCMap");
	}

	public Connection getConnection() {
		return con;
	}

	
	public void addToBinaryStreamList(InputStream binaryStream) {
		binaryStreamList.add(binaryStream);
	}
	public ArrayList getBinaryStreamList() {
		return binaryStreamList;
	}


    @Override
    public String getDbIdentifier() {
        return dbIdentifier;
    }
}
