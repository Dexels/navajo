/*
 *************************************************************************
 * Copyright (c) 2010 <<Your Company Name here>>
 *  
 *************************************************************************
 */

package com.dexels.navajo.oda.impl;

import org.eclipse.datatools.connectivity.oda.IConnection;
import org.eclipse.datatools.connectivity.oda.IDataSetMetaData;
import org.eclipse.datatools.connectivity.oda.IResultSet;
import org.eclipse.datatools.connectivity.oda.OdaException;

/**
 * Implementation class of IDataSetMetaData for an ODA runtime driver.
 * <br>
 * For demo purpose, the auto-generated method stubs have
 * hard-coded implementation that assume this custom ODA data set
 * is capable of handling a query that returns a single result set and 
 * accepts scalar input parameters by index.
 * A custom ODA driver is expected to implement own data set specific
 * behavior in its place. 
 */
public class DataSetMetaData implements IDataSetMetaData
{
	private IConnection m_connection;
	
	DataSetMetaData( IConnection connection )
	{
		System.err.println("Dataset metadata created!");
		m_connection = connection;
	}
	
	/*
	 * @see org.eclipse.datatools.connectivity.oda.IDataSetMetaData#getConnection()
	 */
	@Override
	public IConnection getConnection() throws OdaException
	{
        // TODO Auto-generated method stub
		return m_connection;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IDataSetMetaData#getDataSourceObjects(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public IResultSet getDataSourceObjects( String catalog, String schema, String object, String version ) throws OdaException
	{
	    throw new UnsupportedOperationException();
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IDataSetMetaData#getDataSourceMajorVersion()
	 */
	@Override
	public int getDataSourceMajorVersion() throws OdaException
	{
        // TODO Auto-generated method stub
		return 1;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IDataSetMetaData#getDataSourceMinorVersion()
	 */
	@Override
	public int getDataSourceMinorVersion() throws OdaException
	{
        // TODO Auto-generated method stub
		return 0;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IDataSetMetaData#getDataSourceProductName()
	 */
	@Override
	public String getDataSourceProductName() throws OdaException
	{
        // TODO Auto-generated method stub
		return "NQL Data Source";
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IDataSetMetaData#getDataSourceProductVersion()
	 */
	@Override
	public String getDataSourceProductVersion() throws OdaException
	{
		return Integer.toString( getDataSourceMajorVersion() ) + "." +   //$NON-NLS-1$
			   Integer.toString( getDataSourceMinorVersion() );
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IDataSetMetaData#getSQLStateType()
	 */
	@Override
	public int getSQLStateType() throws OdaException
	{
        // TODO Auto-generated method stub
		return IDataSetMetaData.sqlStateSQL99;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IDataSetMetaData#supportsMultipleResultSets()
	 */
	@Override
	public boolean supportsMultipleResultSets() throws OdaException
	{
		return false;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IDataSetMetaData#supportsMultipleOpenResults()
	 */
	@Override
	public boolean supportsMultipleOpenResults() throws OdaException
	{
		return true;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IDataSetMetaData#supportsNamedResultSets()
	 */
	@Override
	public boolean supportsNamedResultSets() throws OdaException
	{
		return true;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IDataSetMetaData#supportsNamedParameters()
	 */
	@Override
	public boolean supportsNamedParameters() throws OdaException
	{
        // TODO Auto-generated method stub
		return false;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IDataSetMetaData#supportsInParameters()
	 */
	@Override
	public boolean supportsInParameters() throws OdaException
	{
        // TODO Auto-generated method stub
		return false;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IDataSetMetaData#supportsOutParameters()
	 */
	@Override
	public boolean supportsOutParameters() throws OdaException
	{
        // TODO Auto-generated method stub
		return false;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IDataSetMetaData#getSortMode()
	 */
	@Override
	public int getSortMode()
	{
        // TODO Auto-generated method stub
		return IDataSetMetaData.sortModeNone;
	}
    
}
