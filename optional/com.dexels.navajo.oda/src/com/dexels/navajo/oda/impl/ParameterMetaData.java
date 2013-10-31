/*
 *************************************************************************
 * Copyright (c) 2010 <<Your Company Name here>>
 *  
 *************************************************************************
 */

package com.dexels.navajo.oda.impl;

import org.eclipse.datatools.connectivity.oda.IParameterMetaData;
import org.eclipse.datatools.connectivity.oda.OdaException;

/**
 * Implementation class of IParameterMetaData for an ODA runtime driver.
 * <br>
 * For demo purpose, the auto-generated method stubs have
 * hard-coded implementation that returns a pre-defined set
 * of meta-data and query results.
 * A custom ODA driver is expected to implement own data source specific
 * behavior in its place. 
 */
public class ParameterMetaData implements IParameterMetaData 
{

	/* 
	 * @see org.eclipse.datatools.connectivity.oda.IParameterMetaData#getParameterCount()
	 */
	@Override
	public int getParameterCount() throws OdaException 
	{
        // TODO replace with data source specific implementation

        // hard-coded for demo purpose
        return 1;
	}

    /*
	 * @see org.eclipse.datatools.connectivity.oda.IParameterMetaData#getParameterMode(int)
	 */
	@Override
	public int getParameterMode( int param ) throws OdaException 
	{
        // TODO Auto-generated method stub
		return IParameterMetaData.parameterModeIn;
	}

    /* (non-Javadoc)
     * @see org.eclipse.datatools.connectivity.oda.IParameterMetaData#getParameterName(int)
     */
    @Override
	public String getParameterName( int param ) throws OdaException
    {
        // TODO Auto-generated method stub
        return null;    // name is not available
    }

	/* 
	 * @see org.eclipse.datatools.connectivity.oda.IParameterMetaData#getParameterType(int)
	 */
	@Override
	public int getParameterType( int param ) throws OdaException 
	{
        // TODO replace with data source specific implementation

        // hard-coded for demo purpose
        return java.sql.Types.CHAR;   // as defined in data set extension manifest
	}

	/* 
	 * @see org.eclipse.datatools.connectivity.oda.IParameterMetaData#getParameterTypeName(int)
	 */
	@Override
	public String getParameterTypeName( int param ) throws OdaException 
	{
        int nativeTypeCode = getParameterType( param );
        return Driver.getNativeDataTypeName( nativeTypeCode );
	}

	/* 
	 * @see org.eclipse.datatools.connectivity.oda.IParameterMetaData#getPrecision(int)
	 */
	@Override
	public int getPrecision( int param ) throws OdaException 
	{
        // TODO Auto-generated method stub
		return -1;
	}

	/* 
	 * @see org.eclipse.datatools.connectivity.oda.IParameterMetaData#getScale(int)
	 */
	@Override
	public int getScale( int param ) throws OdaException 
	{
        // TODO Auto-generated method stub
		return -1;
	}

	/* 
	 * @see org.eclipse.datatools.connectivity.oda.IParameterMetaData#isNullable(int)
	 */
	@Override
	public int isNullable( int param ) throws OdaException 
	{
        // TODO Auto-generated method stub
		return IParameterMetaData.parameterNullableUnknown;
	}

}
