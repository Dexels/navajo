package com.mysql.jdbc.service;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.service.jdbc.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySqlDataSourceComponent implements DataSource {
//	private ConnectionPoolDataSource connectionPoolDataSource;
	private DataSource datasource;
	
	private final static Logger logger = LoggerFactory
			.getLogger(MySqlDataSourceComponent.class);
	
	public void activate(Map<String,Object> props) {
	
		try {
//			MySQLJDBCDataSourceService factory = new MySQLJDBCDataSourceService();
			MySQLJDBCDataSourceService service = new MySQLJDBCDataSourceService();
			service.start();
//			Dictionary dd = cc.getProperties();
//			Enumeration en = dd.keys();
			Properties pr = new Properties();
			pr.putAll(props);
//			while (en.hasMoreElements()) {
//				String key = (String) en.nextElement();
//				pr.put(key, dd.get(key));
//				logger.debug("Adding property: "+key+" , "+dd.get(key));
//			}
//		    prop.put(DataSourceFactory.JDBC_DATABASE_NAME, settings.get("name")); 

			pr.put(DataSourceFactory.JDBC_DATABASE_NAME, props.get("name"));
			try {
				datasource = service.createDataSource(pr);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (Throwable e) {
			logger.error("Error activating datasource:",e);
		}
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return datasource.isWrapperFor(iface);
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return datasource.unwrap(iface);
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return datasource.getLogWriter();
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return datasource.getLoginTimeout();
	}

	@Override
	public void setLogWriter(PrintWriter p) throws SQLException {
		datasource.setLogWriter(p);
	}

	@Override
	public void setLoginTimeout(int t) throws SQLException {
		datasource.setLoginTimeout(t);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return datasource.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		return datasource.getConnection(username, password);
	}

	// or something. Needed by 1.7, not adding Override, as it would break 1.6
	public java.util.logging.Logger getParentLogger() {
		return null;
	}
}
