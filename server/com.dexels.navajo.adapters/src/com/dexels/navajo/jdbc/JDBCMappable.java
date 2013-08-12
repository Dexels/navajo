package com.dexels.navajo.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.DependentResource;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public interface JDBCMappable {

	public Object getParameter(int index);

	public void setDebug(boolean b);

	public void load(Access access) throws MappableException, UserException;

	public void setDatasource(String s);

	public void setUsername(final String s) throws MappableException, UserException;

	/**
	 * Possibility to explictly rollback transactions, by calling kill setKill.
	 * 
	 * @param b
	 */
	public void setKill(boolean b);

	public void kill();

	public void store() throws MappableException, UserException;

	public void setTransactionContext(int i) throws UserException;

	public void setRowCount(int i);

	public int getRowCount() throws UserException;

	public void setUpdateCount(int i);

	public int getUpdateCount() throws UserException;

	public void setUpdate(final String newUpdate) throws UserException;

	public void setDoUpdate(final boolean doit) throws UserException;

	public Object getColumnName(final Integer index) throws UserException;

	public Object getColumnValue(final Integer index) throws UserException;

	public Object getColumnValue(final String columnName) throws UserException;

	public void setBinaryQuery(Binary b) throws UserException;

	/**
	 * Use this method to define a new query.
	 * All parameters used by a previous query are removed.
	 * replace " characters with ' characters.
	 */
	public void setQuery(final String newQuery) throws UserException;

	/**
	 * Set multiple parameter using a single string. Parameters MUST be seperated by semicolons (;).
	 *
	 * @param param contains the parameter(s). Multiple parameters are support for string types.
	 */
	public void setMultipleParameters(final Object param);

	/**
	 * Setting (a single) parameter of a SQL query.
	 *
	 * @param param the parameter.
	 */
	public void setParameter(final Object param);

	public void setKillConnection();

	/**
	 * NOTE: DO NOT USE THIS METHOD ON LARGE RESULTSETS WITHOUT SETTING ENDINDEX.
	 *
	 */
	public ResultSet getDBResultSet(boolean updateOnly) throws SQLException,
			UserException;

	public ResultSetMap[] getResultSet() throws UserException;

	public Iterator<ResultSetMap> getStreamingResultSet() throws UserException;
	
	public void setReplaceQueryDoubleQuotes(boolean b);

	public String getQuery();

	public String getDatasource();

	public int getConnectionId();

	public boolean isAutoCommit();

	/**
	 * Get all records from resultset as Binary object (x-separated file)
	 * @return
	 */
	public Binary getRecords() throws UserException;

	/**
	 * Sets the separator for the Binary CSV (see getRecords())
	 * 
	 * @param s
	 */
	public void setSeparator(String s);

	/**
	 * controls the inclusion of a header row in the Binary CSV (see getRecords())
	 * @param b
	 */
	public void setShowHeader(boolean b);

	public DependentResource[] getDependentResourceFields();

	public boolean getDebug();

	public void setEndIndex(int i);
	
	public String getDbIdentifier();

}