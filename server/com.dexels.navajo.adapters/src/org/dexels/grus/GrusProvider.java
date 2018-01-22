package org.dexels.grus;

import java.util.Map;

import javax.sql.DataSource;

import com.dexels.navajo.script.api.UserException;


public interface GrusProvider {

	public GrusConnection requestConnection(String instance, String name) throws UserException;
	public GrusConnection requestReuseThreadConnection(String instance, String name) throws UserException;
	public boolean threadContainsConnection(String instance, String name);
	
	public String getDatabaseIdentifier(String instance, String name) throws UserException;

	public GrusConnection requestConnection(long id);
	public void release(GrusConnection grusDataSource);
	public DataSource getInstanceDataSource(String instance, String name);
	public Map<String, Object> getInstanceDataSourceSettings(String instance, String name) throws UserException;
}
