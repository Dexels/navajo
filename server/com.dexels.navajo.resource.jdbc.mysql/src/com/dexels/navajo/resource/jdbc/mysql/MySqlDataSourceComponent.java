package com.dexels.navajo.resource.jdbc.mysql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.service.jdbc.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.cj.jdbc.MysqlDataSource;

public class MySqlDataSourceComponent implements DataSource {
//	private ConnectionPoolDataSource connectionPoolDataSource;
	private DataSource datasource;
	
	private final static Logger logger = LoggerFactory
			.getLogger(MySqlDataSourceComponent.class);
	
	public void activate(Map<String,Object> props) {
		try {
			Properties pr = new Properties();
			pr.putAll(props);
			pr.put(DataSourceFactory.JDBC_DATABASE_NAME, props.get("name"));
			try {
				datasource = createDataSource(pr);
			} catch (SQLException e) {
				logger.error("Error: ", e);
			}
		} catch (Throwable e) {
			logger.error("Error activating datasource:",e);
		}
	}

    public DataSource createDataSource(Properties props) throws SQLException {
        MysqlDataSource source = new MysqlDataSource();
        setup(source, props);
        return source;
    }
    
    protected void setup(MysqlDataSource source, Properties props) {
        if (props == null) {
            return;
        }
        for(Object o :props.keySet()) {
        	logger.debug("Setting up mysql: key {} val: {}",o,props.get(o));
        }
        
        if (props.containsKey(DataSourceFactory.JDBC_DATABASE_NAME)) {
            source.setDatabaseName(props.getProperty(DataSourceFactory.JDBC_DATABASE_NAME));
        }
        if (props.containsKey(DataSourceFactory.JDBC_DATASOURCE_NAME)) {
            //not supported?
        }
        if (props.containsKey(DataSourceFactory.JDBC_DESCRIPTION)) {
            //not suported?
        }
        if (props.containsKey(DataSourceFactory.JDBC_NETWORK_PROTOCOL)) {
            //not supported?
        }
        if (props.containsKey(DataSourceFactory.JDBC_PASSWORD)) {
            source.setPassword(props.getProperty(DataSourceFactory.JDBC_PASSWORD));
        }
        if (props.containsKey(DataSourceFactory.JDBC_PORT_NUMBER)) {
            source.setPortNumber(Integer.parseInt(props.getProperty(DataSourceFactory.JDBC_PORT_NUMBER)));
        }
        if (props.containsKey(DataSourceFactory.JDBC_ROLE_NAME)) {
            //not supported?
        }
        if (props.containsKey(DataSourceFactory.JDBC_SERVER_NAME)) {
            source.setServerName(props.getProperty(DataSourceFactory.JDBC_SERVER_NAME));
        }
        if (props.containsKey(DataSourceFactory.JDBC_URL)) {
            source.setURL(props.getProperty(DataSourceFactory.JDBC_URL));
        }
        if (props.containsKey(DataSourceFactory.JDBC_USER)) {
            source.setUser(props.getProperty(DataSourceFactory.JDBC_USER));
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
