/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter.resource.provider.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.dexels.grus.DbConnectionBroker;
import org.dexels.grus.GrusConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbConnectionBrokerWrapper implements DbConnectionBroker {

	private final GrusDataSource myGrusDataSource;
	private final String myUsername;
	private final int maxConnections;
	private boolean supportsAutoCommit = true;
	private String dbIdentifier = "";
	
	private static final Logger logger = LoggerFactory
			.getLogger(DbConnectionBrokerWrapper.class);
	
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
			logger.warn("Error closing connection: ", e);
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
		return 0;
	}

	@Override
	public void freeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			logger.warn("Error closing connection ", e);
		}
	}

	@Override
	public void setCloseAll() {

	}

}
