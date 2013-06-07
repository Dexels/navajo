package com.mysql.jdbc.service;

import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.service.jdbc.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class MySqlDataSourceComponent extends MysqlDataSource implements DataSource {
	private static final long serialVersionUID = 2471245090084063117L;
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
}
