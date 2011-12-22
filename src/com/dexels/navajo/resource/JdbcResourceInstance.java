package com.dexels.navajo.resource;

import java.sql.Connection;
import java.sql.SQLException;

public interface JdbcResourceInstance extends ResourceInstance {
//	public ConnectionPoolDataSource getPooledDataSource();
	public Connection getConnection() throws SQLException;

}
