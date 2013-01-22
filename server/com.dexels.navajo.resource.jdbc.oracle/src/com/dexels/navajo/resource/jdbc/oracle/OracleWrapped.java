package com.dexels.navajo.resource.jdbc.oracle;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Properties;

import javax.sql.DataSource;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;

import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oracle.jdbc.service.impl.OracleJDBCDataSourceService;

public class OracleWrapped implements DataSource {

	private DataSource wrapped = null;
	private OracleConnectionPoolDataSource datasource;
	private OracleJDBCDataSourceService data;
	
	private final static Logger logger = LoggerFactory
			.getLogger(OracleWrapped.class);
	
	
	public OracleWrapped() {
		logger.info("Oracle driver constructed");
	}
	
	public void activate(ComponentContext cc) {
		data = new OracleJDBCDataSourceService();
		try {
			Properties p = new Properties();
			Dictionary d = cc.getProperties();
			final Enumeration keys = d.keys();
			while (keys.hasMoreElements()) {
				String key = (String)keys.nextElement();
				p.put(key, d.get(key));
				
			}
				datasource = new OracleConnectionPoolDataSource();
			
			wrapped = data.setup(datasource,p);
		} catch (SQLException e) {
			logger.error("Error creating oracle ",e);
		} catch (Exception e) {
			logger.error("Error creating oracle ",e);
		}
		logger.info("Activation successful");
	}
	
	public void deactivate() {
		logger.info("Oracle driver deactivated");

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
		return wrapped.getConnection(username, password);
	}

	public java.util.logging.Logger getParentLogger() {
		return null;
	}

}
