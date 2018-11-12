package com.dexels.navajo.resource.jdbc.postgresql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.sql.DataSource;

import org.postgresql.ds.PGPoolingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.postgresql.jdbc.service.impl.PostgreSQLJDBCDataSourceService;

@SuppressWarnings("deprecation")
public class PostgresqlWrapped implements DataSource {

	private DataSource wrapped = null;
	private PostgreSQLJDBCDataSourceService data;
	
	private final static Logger logger = LoggerFactory.getLogger(PostgresqlWrapped.class);
	
	
	public void activate(Map<String,Object> settings) {
		data = new PostgreSQLJDBCDataSourceService();
		try {
			Properties p = new Properties();
			for (Entry<String,Object> element : settings.entrySet()) {
				p.put(element.getKey(),element.getValue());
			}

	    	PGPoolingDataSource source = new PGPoolingDataSource();
			wrapped = data.setup(source,p);
		} catch (SQLException e) {
			logger.error("Error creating postgresql ",e);
		} catch (Exception e) {
			logger.error("Error creating postgresql ",e);
		}
		logger.info("Created postgresql datasource with setting: "+settings);
//		logger.info("Activation successful");
	}
	
	public void deactivate() {
//		logger.info("PostgreSQL driver deactivated");

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
