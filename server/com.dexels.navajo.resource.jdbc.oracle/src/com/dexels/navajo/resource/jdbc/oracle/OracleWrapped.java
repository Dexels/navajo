package com.dexels.navajo.resource.jdbc.oracle;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.sql.DataSource;

import oracle.jdbc.pool.OracleDataSource;

import org.osgi.service.jdbc.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OracleWrapped implements DataSource {

    
	private OracleDataSource wrapped;
//	private OracleConnectionPoolDataSource datasource;
//	private OracleJDBCDataSourceService data;
//	
	private final static Logger logger = LoggerFactory
			.getLogger(OracleWrapped.class);
	
	
	public OracleWrapped() {

	}
	
	public void activate(Map<String,Object> settings) {
		try {
			wrapped = new OracleDataSource();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			setup(wrapped,settings);
		} catch (SQLException e) {
			logger.error("Error creating oracle ",e);
		} catch (Exception e) {
			logger.error("Error creating oracle ",e);
		}
		logger.info("Created oracle datasource with setting: "+settings);
//		logger.info("Activation successful");
	}
	
	private void setup(OracleDataSource ods, Map<String,Object> settings) throws SQLException {
		
		Properties props = new Properties();
		for (Entry<String,Object> element : settings.entrySet()) {
			props.put(element.getKey(),element.getValue());
			
		}
		props.put("ValidateConnection", "true");
	    ods.setConnectionCachingEnabled(true); // be sure set to true    
	    ods.setConnectionCacheProperties (props);    
//	    ods.setConnectionCacheName("ImplicitCache01"); // this cache's name  
//	    ods.set
	    String databaseName =(String) settings.get("databaseName");
	    if(databaseName!=null) {
        	ods.setDatabaseName(databaseName);
	    }
//        if (props.containsKey(DataSourceFactory.JDBC_DATASOURCE_NAME)) {
//            // not supported?
//        }
//        if (props.containsKey(DataSourceFactory.JDBC_DESCRIPTION)) {
//            // not suported?
//        }
//        if (props.containsKey(DataSourceFactory.JDBC_NETWORK_PROTOCOL)) {
//            // not supported?
//        }
        String password = (String) settings.get("password");
        if(password!=null) {
        	ods.setPassword(password);
        }
        String portNumberString = (String) settings.get("portNumber");
        if(portNumberString!=null) {
        	ods.setPortNumber(Integer.parseInt(portNumberString));
        }
        String serverName = (String) settings.get("serverName");
        if(serverName!=null) {
        	ods.setServerName(serverName);
        }
//        if (props.containsKey(DataSourceFactory.JDBC_SERVER_NAME)) {
//        	ods.setServerName(props.getProperty(DataSourceFactory.JDBC_SERVER_NAME));
//        }
//        if (props.containsKey(DataSourceFactory.JDBC_URL)) {
//        	ods.setURL(props.getProperty(DataSourceFactory.JDBC_URL));
//        }
        String url = (String) settings.get("url"); {
        	if(url!=null) {
        		ods.setURL(url);
        	}
        }
        String username = (String) settings.get("user");
        if(username!=null) {
        	ods.setUser(username);
        }
       
//        if (props.containsKey(DataSourceFactory.JDBC_USER)) {
//        	ods.setUser(props.getProperty(DataSourceFactory.JDBC_USER));
//        }
        
  
//        if (props.containsKey("max_wait")) {
//            maxWait = (Integer) props.get("max_wait");
//        }
        ods.setConnectionProperties(props);
	}

	public void deactivate() {
//		logger.info("Oracle driver deactivated");

	}
	
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return wrapped.getLogWriter();
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return wrapped.getLoginTimeout();
	}

	@Override
	public void setLogWriter(PrintWriter pw) throws SQLException {
		wrapped.setLogWriter(pw);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		wrapped.setLoginTimeout(seconds);
	}

	@Override
	public boolean isWrapperFor(Class<?> c) throws SQLException {
		return wrapped.isWrapperFor(c);
	}

	@Override
	public <T> T unwrap(Class<T> c) throws SQLException {
		return wrapped.unwrap(c);
	}

	@Override
	public Connection getConnection() throws SQLException {	  
		return wrapped.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
//		PreparedStatement stmt = null;
//		if (SQLMapConstants.POSTGRESDB.equals(this.getDbIdentifier()) || SQLMapConstants.ENTERPRISEDB.equals(this.getDbIdentifier())) {
//			stmt = con.prepareStatement("SET SEARCH_PATH TO " + this.alternativeUsername);
//		} else {
//			stmt = con.prepareStatement("ALTER SESSION SET CURRENT_SCHEMA = " + this.alternativeUsername);
//		}
//		stmt.executeUpdate();
//		stmt.close();
		return wrapped.getConnection(username, password);
	}

	public java.util.logging.Logger getParentLogger() {
		return null;
	}

}
