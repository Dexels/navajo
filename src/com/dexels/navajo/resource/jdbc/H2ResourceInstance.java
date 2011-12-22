package com.dexels.navajo.resource.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.service.jdbc.DataSourceFactory;

import com.dexels.navajo.resource.JdbcResourceInstance;
import com.dexels.navajo.resource.ResourceConfig;

public class H2ResourceInstance implements JdbcResourceInstance {

	
	private DataSource source;
	private ResourceConfig config;
	@Override
	public void instantiate(ResourceConfig conf, Map<String, Object> settings) throws Exception {
		this.config = conf;
		DataSourceFactory factory = Activator.getDataSourceFactory();
		Properties prop = new Properties(); 
	    prop.put(DataSourceFactory.JDBC_URL,settings.get(DataSourceFactory.JDBC_URL)); 
	    prop.put(DataSourceFactory.JDBC_USER, settings.get(DataSourceFactory.JDBC_USER)); 
	    prop.put(DataSourceFactory.JDBC_PASSWORD, settings.get(DataSourceFactory.JDBC_PASSWORD)); 
	    source = factory.createDataSource(prop); 
	    // register source as service
	}

//	@Override
//	public ConnectionPoolDataSource getPooledDataSource() {
//		return source;
//	}

	public Connection getConnection() throws SQLException {
		return source.getConnection();
	}
	
	@Override
	public ResourceConfig getConfig() {
		return config;
	}

	@Override
	public void close() {
//		source.
	}

	@Override
	public Object getSource() {
		return source;
	}


}
