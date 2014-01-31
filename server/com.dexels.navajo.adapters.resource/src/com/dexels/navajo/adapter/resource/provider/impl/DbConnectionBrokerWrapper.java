package com.dexels.navajo.adapter.resource.provider.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.dexels.grus.DbConnectionBroker;
import org.dexels.grus.GrusConnection;

public class DbConnectionBrokerWrapper implements DbConnectionBroker {

	private final GrusDataSource myGrusDataSource;
	private final String myUsername;
	private final int maxConnections;
	private boolean supportsAutoCommit = true;
	private String dbIdentifier = "";
	
	public DbConnectionBrokerWrapper(GrusDataSource gds, String username, int maxConnections) {
		myGrusDataSource = gds;
		myUsername = username;
		this.maxConnections = maxConnections;
	}
	
	@Override
	public String getUsername() {
		return myUsername;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return myGrusDataSource.getConnection();
	}

	@Override
	public boolean hasAutoCommit() {
		return supportsAutoCommit;
	}

	@Override
	public int getMaxCount() {
		return maxConnections;
	}

	@Override
	public boolean isDead() {
		return false;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public int getUseCount() {
		return 0;
	}

	@Override
	public void freeConnection(GrusConnection gc) {
		try {
			myGrusDataSource.getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public GrusConnection getGrusConnection() {
		return myGrusDataSource;
	}

	@Override
	public String getDbIdentifier() {
		return dbIdentifier;
	}

	@Override
	public void setDbIdentifier(String dbIdentifier) {
		this.dbIdentifier = dbIdentifier;
	}

	@Override
	public void setSupportsAutoCommit(boolean b) {
		supportsAutoCommit = b;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void freeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setCloseAll() {
		// TODO Auto-generated method stub

	}

}
