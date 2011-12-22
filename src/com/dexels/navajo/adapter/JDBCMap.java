package com.dexels.navajo.adapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.dexels.navajo.adapter.jdbcbroker.JdbcResourceComponent;
import com.dexels.navajo.adapter.sqlmap.DatabaseInfo;
import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.Memo;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.types.NavajoType;
import com.dexels.navajo.document.types.Percentage;
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
import com.dexels.navajo.server.Repository;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.util.AuditLog;


public class JDBCMap implements Mappable, HasDependentResources, Debugable {

  protected final String DEFAULTSRCNAME = "default";

  private boolean debug = false;
  
  private static final Logger logger = LoggerFactory.getLogger(JDBCMap.class);
  
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
  
  private String datasource = this.DEFAULTSRCNAME;
  private int connectionId = -1;
  protected Access myAccess;
  public int resultSetIndex = 0;

  

  public Object getParameter(int index) {
	  if(parameters==null) {
		  return null;
	  }
	  return parameters.get(index);
  }
  

  public void setDebug(boolean b) {
    this.debug = b;
  }
  

  private void cleanupBinaryStreams() {
	  for (int i = 0; i < binaryStreamList.size(); i++) {
		InputStream is = (InputStream)binaryStreamList.get(i);
		try {
			is.close();
		} catch (Throwable e) {
			logger.error("Error cleaning up streams",e);
		}
	}
	  binaryStreamList.clear();
  }



  public void load(Access access) throws MappableException, UserException {
    myAccess = access;
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
	  
//	  try {
//		  if (autoCommitMap.get(this.datasource) == null) {
//			  return;
//		  }
//		  boolean ac = (this.overideAutoCommit) ? autoCommit :
//			  ( (Boolean) autoCommitMap.get(datasource)).booleanValue();
//		  if (!ac) {
//			  if (con != null) {
//				  kill = true;
//				  con.rollback();
//			  }
//		  }
//	  }
//	  catch (SQLException sqle) {
//		  AuditLog.log("SQLMap", sqle.getMessage(), Level.SEVERE, (myAccess != null ? myAccess.accessID : "unknown access") );
//		  sqle.printStackTrace(Access.getConsoleWriter(myAccess));
//	  } finally {
//		  try {
//			store();
//		} catch (MappableException e) {
//		} catch (UserException e) {
//		}
//	  }
  }

  public void store() throws MappableException, UserException {
	  cleanupBinaryStreams();
  }

//  public void setTransactionIsolationLevel(int j) {
//    transactionIsolation = j;
//  }


  public void setTransactionContext(int i) throws UserException {

    if (debug) {
    	Access.writeToConsole(myAccess, "IN SETTRANSACTIONCONTEX(), I = " + i + "\n");
    }
    this.transactionContext = i;
 
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
  
  public void setBinaryQuery(Binary b) throws UserException {
	  String query = new String(b.getData());
	  setQuery(query);
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
    parameters.add(param);
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
			  // ?
			  con.close();
		  }
		  catch (Throwable ex) {
//			  ex.printStackTrace(Access.getConsoleWriter(myAccess));
			  logger.error("Error killing connection",ex);
		  }
	  }
  }

  protected final void createConnection() throws SQLException, UserException {

	  if (this.debug) {
		  Access.writeToConsole(myAccess, this.getClass() + ": in createConnection()\n");
	  }

	  if ( transactionContext != -1 ) {
		 
    	  con = JdbcResourceComponent.getJdbc(transactionContext);
    	   if (con == null) {
    	    	throw new UserException( -1, "Invalid transaction context set: " + transactionContext);
    	    }
    	   // Make sure to set connection id.  
    	   this.connectionId = transactionContext;
      }
	  
    if (con == null) { // Create connection if it does not yet exist.

      if (this.debug) {
    	  Access.writeToConsole(myAccess, "in createConnection() for datasource " + datasource);
      }

      con = JdbcResourceComponent.getJdbc(datasource).getConnection();
//      con = pooledConnection.getConnection();
     
      
      if (con == null) {
    		  AuditLog.log("SQLMap",  "Could (still) not connect to database: " + datasource +
    				  ", check your connection", Level.SEVERE);

    		  throw new UserException( -1,
    				  "Could not connect to database: " +
    				  datasource + " ()" +
    		  ", check your connection");
    //	  }
      }
      else {
        if (this.debug) {
        	Access.writeToConsole(myAccess, this.getClass() +
              ": returned a good connection from the broker manager\n");
        }
      }

      
      if ( this.con != null) {
          this.connectionId = con.hashCode();
          if (this.debug) {
        	  Access.writeToConsole(myAccess, this.getClass() + ": put connection no. " +
                               this.connectionId + " into the connection map\n");
          }
        }
    }
    
  }
  
  private DatabaseInfo getMetaData(Connection c) throws SQLException {
		DatabaseMetaData dbmd = c.getMetaData();
		boolean supportsAutocommit = dbmd.supportsTransactions();
		return new DatabaseInfo(dbmd, this.datasource);

  }


  private final void setStatementParameters(PreparedStatement statement) throws
      java.sql.SQLException {
    if (parameters != null) {
      // System.err.println("parameters = " + parameters);
      for (int i = 0; i < parameters.size(); i++) {
        Object param = parameters.get(i);

        // System.err.println("parameter " + i + " = " + param);
        if ( (param == null) || (param instanceof NavajoType && ((NavajoType) param).isEmpty() )  ) {
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
          
          long time = ( (java.util.Date) param).getTime();
          if(isLegacyMode()) {
              java.sql.Date sqlDate = new java.sql.Date( time);
              statement.setDate(i + 1, sqlDate);
          } else {
              Timestamp sqlDate = new java.sql.Timestamp( time);
              statement.setTimestamp(i + 1, sqlDate);
          }
          
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
 
private boolean isLegacyMode() {
	Repository r = DispatcherFactory.getInstance().getNavajoConfig().getRepository();
	return r.useLegacyDateMode();
}

/**
 * BEWARE! Possible resource leak!!! Should the stream be closed?
 * @param statement
 * @param i
 * @param b
 * @throws SQLException
 */
  
  protected void setBlob(PreparedStatement statement, int i, Binary b) throws SQLException {

	  if ( b != null ) {
		  
		  InputStream os = b.getDataAsStream();

		  if ( os != null && b.getLength() > 0) {
			  statement.setBinaryStream(i+1,os,(int)b.getLength());
			  // All streams in this list will be closed on kill() or store()
			  binaryStreamList.add(os);
		  } else {
			  statement.setNull(i+1, Types.BLOB );
		  }
	  } else {
		  statement.setNull(i+1, Types.BLOB );
	  }
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
                ", check your connection", Level.SEVERE, (myAccess != null ? myAccess.accessID : "unknown access") );
    
      throw new UserException( -1,
          "in SQLMap. Could not open database connection']");
    }

    if (debug) {
    	Access.writeToConsole(myAccess, "SQLMAP, GOT CONNECTION, STARTING QUERY\n");
    }

    // batch mode?
//    this.batchMode = updateOnly &&
//        ( (this.query == null) || (this.query.length() == 0)) && (this.update != null) &&
//        (this.update.indexOf(SQLBatchUpdateHelper.DELIMITER) > 0);
//    if (this.batchMode) {
//      if (this.debug) {
//    	  Access.writeToConsole(myAccess, this.getClass() +
//                           ": detected batch mode, trying a batch update\n");
//      }
//      this.helper = new SQLBatchUpdateHelper(this.update,
//                                             this.con, this.parameters,
//                                             this.debug,this.updateOnly);
//      this.updateCount = this.helper.getUpdateCount();
//      this.batchMode = false;
//      return (this.helper.getResultSet());
//    }

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
    } else {
      this.statement = con.prepareStatement(update);
    }
    if (debug) { Access.writeToConsole(myAccess, "AFTER PREPARESTATEMENT(), SETTING MAXROWS...\n"); }



    if (debug) { Access.writeToConsole(myAccess, "SET MAXROWS DONE..SETTING STATEMENT PARAMETERS\n"); }
    setStatementParameters(statement);

    ResultSet rs = null;

    if (updateOnly) {
      this.statement.executeUpdate();
    }
    else {
        if (debug) { Access.writeToConsole(myAccess, "CALLING EXECUTEQUERY()\n"); }
        rs = this.statement.executeQuery();

        if (debug) { Access.writeToConsole(myAccess, "GOT RESULTSET!!!!!\n"); }
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
        }
        catch (Exception e) {

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
            }
            //else if (index >= startIndex) {
            //  remainCount++;
            //}
            rowCount++;
            index++;

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
    	logger.error("SQL problem",sqle);
//      sqle.printStackTrace(Access.getConsoleWriter(myAccess));
      AuditLog.log("SQLMap", sqle.getMessage(), Level.SEVERE, (myAccess != null ? (myAccess != null ? myAccess.accessID : "unknown access") : "unknown access") );
      throw new UserException( -1, sqle.getMessage(),sqle);
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
    
    return resultSet;
  }

  protected void resetAll() throws UserException {
    this.query = this.update = null;

    try {
     
      if (this.statement != null) {
        this.statement.close();
        this.statement = null;
      }
 
    }
    catch (Exception e) {
    	AuditLog.log("SQLMap", e.getMessage(), Level.SEVERE, (myAccess != null ? myAccess.accessID : "unknown access") );
      throw new UserException( -1, e.getMessage());
    }
  }

  public void setReplaceQueryDoubleQuotes( boolean b ) {
    this.replaceQueryDoubleQuotes = b;
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
			  logger.error("Error writing output binary",e);
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
				  logger.error("Error writing output binary",e);
			  }
		  }
		  if ( tempFile != null ) {
			  try {
				  tempFile.delete();
			  } catch (Exception ioe2 ) {
				  logger.error("Error writing output binary",ioe2);
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
  
  public DependentResource [] getDependentResourceFields() {
	  return new DependentResource[]{new GenericDependentResource("database", "datasource", AdapterFieldDependency.class), 
			                         new GenericMultipleDependentResource("sql", "update", SQLFieldDependency.class), 
			                         new GenericMultipleDependentResource("sql", "query", SQLFieldDependency.class)};
  }

  public boolean getDebug() {
	  return this.debug;
  }
  
 
}
