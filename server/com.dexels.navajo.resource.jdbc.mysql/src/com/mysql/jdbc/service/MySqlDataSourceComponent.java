package com.mysql.jdbc.service;

import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.service.component.ComponentContext;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class MySqlDataSourceComponent extends MysqlDataSource implements DataSource {
	private static final long serialVersionUID = 2471245090084063117L;
//	private ConnectionPoolDataSource connectionPoolDataSource;
	private DataSource datasource;
	
	public void activate(ComponentContext cc) {
		
		MySQLJDBCDataSourceService service = new MySQLJDBCDataSourceService();
		Dictionary dd = cc.getProperties();
		Enumeration en = dd.keys();
		Properties pr = new Properties();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			pr.put(key, dd.get(key));
		}
		try {
			datasource = service.createDataSource(pr);
		} catch (SQLException e) {
			e.printStackTrace();
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
