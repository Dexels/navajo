/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.resource.jdbc.mssql;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.service.jdbc.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.sqlserver.jdbc.osgi.SQLServerDataSourceFactory;

public class MSSQLWrapped implements DataSource {

	private DataSource wrapped = null;
	private DataSourceFactory data;
	
	private static final Logger logger = LoggerFactory.getLogger(MSSQLWrapped.class);
	
	
	public void activate(Map<String,Object> settings) {
		// todo: use osgi service reference?
		data = new SQLServerDataSourceFactory();
		try {
			Properties p = new Properties();
			for (Entry<String,Object> element : settings.entrySet()) {
				p.put(element.getKey(),element.getValue());
			}
			wrapped = data.createDataSource(p);
		} catch (SQLException e) {
			logger.error("Error creating mssql driver ",e);
		}
		logger.info("Created postgresql datasource with setting: {}",settings);
	}
	
	public void deactivate() {
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
