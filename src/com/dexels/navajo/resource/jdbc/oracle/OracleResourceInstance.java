package com.dexels.navajo.resource.jdbc.oracle;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.service.jdbc.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.resource.JdbcResourceInstance;
import com.dexels.navajo.resource.ResourceConfig;

public class OracleResourceInstance implements JdbcResourceInstance {

	
	private DataSource source;
	private ResourceConfig config;
	private final static  Logger logger= LoggerFactory.getLogger(OracleResourceInstance.class);
	@Override
	public void instantiate(ResourceConfig conf, Map<String, Object> settings) throws Exception {
		this.config = conf;
		DataSourceFactory factory = Activator.getDataSourceFactory();

		Properties prop = new Properties(); 
	    prop.put(DataSourceFactory.JDBC_URL,settings.get(DataSourceFactory.JDBC_URL)); 
	    prop.put(DataSourceFactory.JDBC_USER, settings.get(DataSourceFactory.JDBC_USER)); 
	    prop.put(DataSourceFactory.JDBC_PASSWORD, settings.get(DataSourceFactory.JDBC_PASSWORD)); 
	    source = factory.createDataSource(prop); 
//	   source.setLogWriter(new PrintWriter(new Writer(){
//
//		@Override
//		public void close() throws IOException {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void flush() throws IOException {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void write(char[] arg0, int arg1, int arg2) throws IOException {
//			// TODO Auto-generated method stub
//			
//		}}));
////	    PooledConnection oe = source.getPooledConnection()
//	    // register source as service
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

		logger.info("Closing mysql instance");
	}
	@Override
	public Object getSource() {
		return source;
	}



}
