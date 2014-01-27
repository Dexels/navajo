package com.dexels.navajo.resource.jdbc.oracle;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.sql.DataSource;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;

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
	}
	
	public void activate(Map<String,Object> settings) {
		data = new OracleJDBCDataSourceService();
		try {
			Properties p = new Properties();
			for (Entry<String,Object> element : settings.entrySet()) {
				p.put(element.getKey(),element.getValue());
				
			}

				datasource = new OracleConnectionPoolDataSource();
			
			wrapped = data.setup(datasource,p);
		} catch (SQLException e) {
			logger.error("Error creating oracle ",e);
		} catch (Exception e) {
			logger.error("Error creating oracle ",e);
		}
//		logger.info("Activation successful");
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
