/*
 *************************************************************************
 * Copyright (c) 2010 <<Your Company Name here>>
 *  
 *************************************************************************
 */

package com.dexels.navajo.oda.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map.Entry;
import java.util.Properties;
import org.eclipse.datatools.connectivity.oda.IConnection;
import org.eclipse.datatools.connectivity.oda.IDataSetMetaData;
import org.eclipse.datatools.connectivity.oda.IQuery;
import org.eclipse.datatools.connectivity.oda.OdaException;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.types.Binary;
import com.ibm.icu.util.ULocale;

/**
 * Implementation class of IConnection for an ODA runtime driver.
 */
public class NqlConnection implements IConnection
{
    private boolean m_isOpen = false;
    
    private String navajoServer;
    private String password;
    private String username;

	private String urlString;

	private Message resultMessage = null;

	private String queryText;
	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#open(java.util.Properties)
	 */
	public void open( Properties connProperties ) throws OdaException
	{
		
		// http://spiritus.dexels.nl:9080/NavajoNql/Nql?query=call:person/InitSearchPersons|call:person/ProcessSearchPersons|output:Persons|format:tsv
        // TODO replace with data source specific implementation
	    m_isOpen = true;        
	    for(Entry<Object,Object> e : connProperties.entrySet()) {
	    	System.err.println("E: "+e.getKey()+" val: "+e.getValue());
	    }
	    navajoServer = connProperties.getProperty("navajoNql");
	    password = connProperties.getProperty("password");
	    username = connProperties.getProperty("username");
	    
	    this.urlString = navajoServer+"?username="+username+"&password="+password;
	    try {
			URL u = new URL(navajoServer+"?ping=true&username="+username+"&password="+password);
			InputStream is = u.openStream();
			Binary b = new Binary(is);
			System.err.println("Contents: "+new String(b.getData()));
			is.close();
	    } catch (MalformedURLException e1) {
			throw new OdaException(e1);
	    } catch (IOException e) {
	    	throw new OdaException(e);
	    }
	}

	
	public Navajo performQuery(String query) throws IOException, NavajoException {
		try {
			String totalUrl = urlString+"&query="+query+"|format:tml";
			System.err.println("Total url: "+totalUrl);
			URL u = new URL(totalUrl);
			InputStream is = u.openStream();
			Navajo result = NavajoFactory.getInstance().createNavajo(is);
			is.close();
			result.write(System.err);
			resultMessage = result.getAllMessages().get(0);
			return result;
		} catch (MalformedURLException e) {
			throw NavajoFactory.getInstance().createNavajoException(e);
		}
	}
//	http://spiritus.dexels.nl:9080/NavajoNql/Nql?query=call:person/InitSearchPersons|call:person/ProcessSearchPersons|output:Persons|format:tml	
//	http://spiritus.dexels.nl:9080/NavajoNql/Nql?username=demo&password=demo&query=call:person/InitSearchPersons|call:person/ProcessSearchPersons|output:Persons&format=tml

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#setAppContext(java.lang.Object)
	 */
	public void setAppContext( Object context ) throws OdaException
	{
	    // do nothing; assumes no support for pass-through context
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#close()
	 */
	public void close() throws OdaException
	{
        // TODO replace with data source specific implementation
	    m_isOpen = false;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#isOpen()
	 */
	public boolean isOpen() throws OdaException
	{
        // TODO Auto-generated method stub
		return m_isOpen;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#getMetaData(java.lang.String)
	 */
	public IDataSetMetaData getMetaData( String dataSetType ) throws OdaException
	{
	    // assumes that this driver supports only one type of data set,
        // ignores the specified dataSetType
		return new DataSetMetaData( this );
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#newQuery(java.lang.String)
	 */
	public IQuery newQuery( String dataSetType ) throws OdaException
	{
        // assumes that this driver supports only one type of data set,
        // ignores the specified dataSetType
		NqlQuery nqlQuery = new NqlQuery();
		nqlQuery.setConnection(this);
		return nqlQuery;
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#getMaxQueries()
	 */
	public int getMaxQueries() throws OdaException
	{
		return 0;	// no limit
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#commit()
	 */
	public void commit() throws OdaException
	{
	    // do nothing; assumes no transaction support needed
	}

	/*
	 * @see org.eclipse.datatools.connectivity.oda.IConnection#rollback()
	 */
	public void rollback() throws OdaException
	{
        // do nothing; assumes no transaction support needed
	}

    /* (non-Javadoc)
     * @see org.eclipse.datatools.connectivity.oda.IConnection#setLocale(com.ibm.icu.util.ULocale)
     */
    public void setLocale( ULocale locale ) throws OdaException
    {
        // do nothing; assumes no locale support
    }


	public Message getResult() throws IOException, NavajoException {
		if(resultMessage==null) {
			System.err.println("No result message. Attempting query: "+queryText);
			performQuery(queryText);
		}
		
		return resultMessage;
	}


	public void setQueryText(String queryText) {
		this.queryText = queryText;
		
	}
    
}
