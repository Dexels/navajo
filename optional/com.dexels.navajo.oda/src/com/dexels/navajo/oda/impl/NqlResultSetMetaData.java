/*
 *************************************************************************
 * Copyright (c) 2010 <<Your Company Name here>>
 *  
 *************************************************************************
 */

package com.dexels.navajo.oda.impl;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import org.eclipse.datatools.connectivity.oda.IBlob;
import org.eclipse.datatools.connectivity.oda.IClob;
import org.eclipse.datatools.connectivity.oda.IResultSet;
import org.eclipse.datatools.connectivity.oda.IResultSetMetaData;
import org.eclipse.datatools.connectivity.oda.OdaException;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.document.types.Memo;
import com.dexels.navajo.document.types.StopwatchTime;

/**
 * Implementation class of IResultSetMetaData for an ODA runtime driver.
 * <br>
 * For demo purpose, the auto-generated method stubs have
 * hard-coded implementation that returns a pre-defined set
 * of meta-data and query results.
 * A custom ODA driver is expected to implement own data source specific
 * behavior in its place. 
 */
public class NqlResultSetMetaData implements IResultSetMetaData, IResultSet
{
    private  Message message;
    private  Message definition;
    private  Message element;
	private int m_maxRows = -1;
	private int m_currentRowId;
	
	public NqlResultSetMetaData(Message m ) throws OdaException {
		initialize(m);
	    
	}


	private void initialize(Message m) throws OdaException {
		message = m;
		if(!message.isArrayMessage()) {
			element = m;
			definition = m;
		} else {
			if(m.getAllMessages().size()>0) {
				element = m.getAllMessages().get(0);
			} else {
				element = null;
			}
			if(m.getDefinitionMessage()!=null) {
				definition = m.getDefinitionMessage();
			} else {
				definition = element;
			}
		}
		int arraySize = this.message.getArraySize();
		System.err.println("Arr: "+arraySize);
		setMaxRows(arraySize);
	}
	
	
	public Object getColumnPropertyValue(int index) {
		System.err.println("Getting property for column: "+index);
		return getColumnProperty(index).getTypedValue();
	}
	
	@Override
	public IResultSetMetaData getMetaData() throws OdaException
	{
        /* TODO Auto-generated method stub
         * Replace with implementation to return an instance 
         * based on this result set.
         */
		return this;
	}
	
	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnCount()
	 */
	@Override
	public int getColumnCount() throws OdaException
	{
		int size = definition.getAllProperties().size();
		System.err.println("Columncount: "+size);
		return size;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnName(int)
	 */
	@Override
	public String getColumnName( int index ) throws OdaException
	{
		return getColumnProperty(index).getName();
	}
	private Property getColumnProperty(int index) {
		return element.getAllProperties().get(index-1);
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnLabel(int)
	 */
	@Override
	public String getColumnLabel( int index ) throws OdaException
	{
		String description = getColumnProperty(index).getDescription();
		if(description==null) {
			return getColumnName(index);
		}
		return description;		// default
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnType(int)
	 */
	@Override
	public int getColumnType( int index ) throws OdaException
	{
		System.err.println("Looking for column: "+index);
		return getPropertyTypeId(index);
		
        // TODO replace with data source specific implementation

        // hard-coded for demo purpose
	}
	
	
	private int getPropertyTypeId(int index) {
		Property e =  getColumnProperty(index);
		String type = e.getType();
		
		if(type.equals(Property.BINARY_PROPERTY)) {
			return java.sql.Types.BLOB;
		}
		if(type.equals(Property.BOOLEAN_PROPERTY)) {
			return java.sql.Types.BOOLEAN;
		}
		if(type.equals(Property.CLOCKTIME_PROPERTY)) {
			return java.sql.Types.TIME;
		}
		if(type.equals(Property.DATE_PROPERTY)) {
			return java.sql.Types.DATE;
		}
		if(type.equals(Property.FLOAT_PROPERTY)) {
			return java.sql.Types.FLOAT;
		}
		if(type.equals(Property.INTEGER_PROPERTY)) {
			return java.sql.Types.INTEGER;
		}
		if(type.equals(Property.LIST_PROPERTY)) {
			return java.sql.Types.JAVA_OBJECT;
		}
		if(type.equals(Property.LONG_PROPERTY)) {
			return java.sql.Types.BIGINT;
		}
		if(type.equals(Property.MEMO_PROPERTY)) {
			return java.sql.Types.CLOB;
		}
		if(type.equals(Property.MONEY_PROPERTY)) {
			return java.sql.Types.NUMERIC;
		}
		if(type.equals(Property.PASSWORD_PROPERTY)) {
			return java.sql.Types.VARCHAR;
		}
		if(type.equals(Property.PERCENTAGE_PROPERTY)) {
			return java.sql.Types.FLOAT;
		}
		if(type.equals(Property.SELECTION_PROPERTY)) {
			return java.sql.Types.JAVA_OBJECT;
		}
		if(type.equals(Property.STOPWATCHTIME_PROPERTY)) {
			return java.sql.Types.TIME;
		}
		if(type.equals(Property.URL_PROPERTY)) {
			return java.sql.Types.VARCHAR;
		}
			return java.sql.Types.VARCHAR;

		

	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnTypeName(int)
	 */
	@Override
	public String getColumnTypeName( int index ) throws OdaException
	{
		
        int nativeTypeCode = getColumnType( index );
        return Driver.getNativeDataTypeName( nativeTypeCode );
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getColumnDisplayLength(int)
	 */
	@Override
	public int getColumnDisplayLength( int index ) throws OdaException
	{
        // TODO replace with data source specific implementation

        // hard-coded for demo purpose
		int length = getColumnProperty(index).getLength();
		if(length<1) {
			length = 8;
		}
		return length;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getPrecision(int)
	 */
	@Override
	public int getPrecision( int index ) throws OdaException
	{
        // TODO Auto-generated method stub
		return -1;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#getScale(int)
	 */
	@Override
	public int getScale( int index ) throws OdaException
	{
		return -1;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSetMetaData#isNullable(int)
	 */
	@Override
	public int isNullable( int index ) throws OdaException
	{
		return IResultSetMetaData.columnNullableUnknown;
	}
	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSet#setMaxRows(int)
	 */
	@Override
	public void setMaxRows( int max ) throws OdaException
	{
		m_maxRows = max;
	}
	
	/**
	 * Returns the maximum number of rows that can be fetched from this result set.
	 * @return the maximum number of rows to fetch.
	 */
	protected int getMaxRows()
	{
		return m_maxRows;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSet#next()
	 */
	@Override
	public boolean next() throws OdaException
	{
        int maxRows = getMaxRows();
        if(maxRows<=0) {
        	initialize(message);
        }
        System.err.println("Next. current row: "+m_currentRowId+" max: "+maxRows);
		if (m_currentRowId < maxRows )
        {
            m_currentRowId++;
            element = message.getAllMessages().get(m_currentRowId-1);
            System.err.println("True");
            return true;
        }
        System.err.println("False");
        return false;        
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSet#close()
	 */
	@Override
	public void close() throws OdaException
	{
        // TODO Auto-generated method stub       
        m_currentRowId = 0;     // reset row counter
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSet#getRow()
	 */
	@Override
	public int getRow() throws OdaException
	{
		return m_currentRowId;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSet#getString(int)
	 */
	@Override
	public String getString( int index ) throws OdaException
	{
		Object columnPropertyValue = getColumnPropertyValue(index);
		if(columnPropertyValue==null) {
			return ">null<";
		}
		return columnPropertyValue.toString();
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSet#getString(java.lang.String)
	 */
	@Override
	public String getString( String columnName ) throws OdaException
	{
	    return getString( findColumn( columnName ) );
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSet#getInt(int)
	 */
	@Override
	public int getInt( int index ) throws OdaException
	{
		return (Integer) getColumnPropertyValue(index);
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSet#getInt(java.lang.String)
	 */
	@Override
	public int getInt( String columnName ) throws OdaException
	{
	    return getInt( findColumn( columnName ) );
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSet#getDouble(int)
	 */
	@Override
	public double getDouble( int index ) throws OdaException
	{
        // TODO Auto-generated method stub
		return (Float) getColumnPropertyValue(index);
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSet#getDouble(java.lang.String)
	 */
	@Override
	public double getDouble( String columnName ) throws OdaException
	{
	    return getDouble( findColumn( columnName ) );
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSet#getBigDecimal(int)
	 */
	@Override
	public BigDecimal getBigDecimal( int index ) throws OdaException
	{
		return (BigDecimal) getColumnPropertyValue(index);
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSet#getBigDecimal(java.lang.String)
	 */
	@Override
	public BigDecimal getBigDecimal( String columnName ) throws OdaException
	{
	    return getBigDecimal( findColumn( columnName ) );
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSet#getDate(int)
	 */
	@Override
	public Date getDate( int index ) throws OdaException
	{
		Object columnPropertyValue = getColumnPropertyValue(index);
		if(!(columnPropertyValue instanceof java.util.Date)) {
			return null;
		}
		java.util.Date dd = (java.util.Date) columnPropertyValue;
		
		return new Date(dd.getTime());
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSet#getDate(java.lang.String)
	 */
	@Override
	public Date getDate( String columnName ) throws OdaException
	{
	    return getDate( findColumn( columnName ) );
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSet#getTime(int)
	 */
	@Override
	public Time getTime( int index ) throws OdaException
	{
		StopwatchTime time = (StopwatchTime) getColumnPropertyValue(index);
		return new Time(time.getMillis());
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSet#getTime(java.lang.String)
	 */
	@Override
	public Time getTime( String columnName ) throws OdaException
	{
	    return getTime( findColumn( columnName ) );
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSet#getTimestamp(int)
	 */
	@Override
	public Timestamp getTimestamp( int index ) throws OdaException
	{
		return new Timestamp(getDate(index).getTime());
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IResultSet#getTimestamp(java.lang.String)
	 */
	@Override
	public Timestamp getTimestamp( String columnName ) throws OdaException
	{
	    return getTimestamp( findColumn( columnName ) );
	}

    /* 
     * @see org.eclipse.datatools.connectivity.oda.IResultSet#getBlob(int)
     */
    @Override
	public IBlob getBlob( int index ) throws OdaException
    {
    	final Binary b = (Binary) getColumnPropertyValue(index);
    	IBlob bb = new IBlob() {
			
			@Override
			public long length() throws OdaException {
				return b.getLength();
			}
			
			@Override
			public byte[] getBytes(long pos, int len) throws OdaException {
				byte[] data = b.getData();
				byte[] res = new byte[len];
				System.arraycopy(data, (int) pos, res, 0, len);
				return res;
			}
			
			@Override
			public InputStream getBinaryStream() throws OdaException {
				return b.getDataAsStream();
			}
		};
		return bb;
    }

    /* 
     * @see org.eclipse.datatools.connectivity.oda.IResultSet#getBlob(java.lang.String)
     */
    @Override
	public IBlob getBlob( String columnName ) throws OdaException
    {
        return getBlob( findColumn( columnName ) );
    }

    /* 
     * @see org.eclipse.datatools.connectivity.oda.IResultSet#getClob(int)
     */
    @Override
	public IClob getClob( int index ) throws OdaException
    {
    	final Object b = getColumnPropertyValue(index);

    	final Memo m = (Memo)b;
        // TODO Auto-generated method stub
    	return new IClob() {
			
			@Override
			public long length() throws OdaException {
				return m.toString().length();
			}
			
			@Override
			public String getSubString(long beginIndex, int len) throws OdaException {
				
				return m.toString().substring((int) beginIndex,len);
			}
			
			@Override
			public Reader getCharacterStream() throws OdaException {
				return new StringReader(m.toString());
			}
		};
    }

    /* 
     * @see org.eclipse.datatools.connectivity.oda.IResultSet#getClob(java.lang.String)
     */
    @Override
	public IClob getClob( String columnName ) throws OdaException
    {
        return getClob( findColumn( columnName ) );
    }

    /* (non-Javadoc)
     * @see org.eclipse.datatools.connectivity.oda.IResultSet#getBoolean(int)
     */
    @Override
	public boolean getBoolean( int index ) throws OdaException
    {
		return (Boolean) getColumnPropertyValue(index);
    }

    /* (non-Javadoc)
     * @see org.eclipse.datatools.connectivity.oda.IResultSet#getBoolean(java.lang.String)
     */
    @Override
	public boolean getBoolean( String columnName ) throws OdaException
    {
        return getBoolean( findColumn( columnName ) );
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.datatools.connectivity.oda.IResultSet#getObject(int)
     */
    @Override
	public Object getObject( int index ) throws OdaException
    {
		return getColumnPropertyValue(index);
    }

    /* (non-Javadoc)
     * @see org.eclipse.datatools.connectivity.oda.IResultSet#getObject(java.lang.String)
     */
    @Override
	public Object getObject( String columnName ) throws OdaException
    {
        return getObject( findColumn( columnName ) );
    }

    /*
     * @see org.eclipse.datatools.connectivity.oda.IResultSet#wasNull()
     */
    @Override
	public boolean wasNull() throws OdaException
    {
        // TODO Auto-generated method stub
        
        // hard-coded for demo purpose
    	return false;
    }

    /*
     * @see org.eclipse.datatools.connectivity.oda.IResultSet#findColumn(java.lang.String)
     */
    @Override
	public int findColumn( String columnName ) throws OdaException
    {
        // TODO replace with data source specific implementation
    	int index = 0;
    	for (Property p : definition.getAllProperties()) {
    		if(p.getName().equals(columnName)) {
    			return index+1;
    		}
    		index++;
		}
    	return -1;
    }

    
}
