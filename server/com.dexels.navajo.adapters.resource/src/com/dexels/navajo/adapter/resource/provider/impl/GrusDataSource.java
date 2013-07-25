package com.dexels.navajo.adapter.resource.provider.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.dexels.grus.DbConnectionBroker;
import org.dexels.grus.GrusConnection;
import org.dexels.grus.GrusProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrusDataSource implements GrusConnection {

	private final DataSource datasource;
	private final Map<String, Object> settings;
	private DbConnectionBroker dbConnectionBroker;
	private final int id;
	private Connection connection;

	private final GrusProvider grusProvider;
	
	private final static Logger logger = LoggerFactory.getLogger(GrusDataSource.class);
	
	
	public GrusDataSource(int id, DataSource dataSourceInstance,Map<String, Object> settings, GrusProvider provider) {
		this.datasource = dataSourceInstance;
		this.id = id;
		this.grusProvider = provider;
//		component.id = 115
//		component.name = navajo.resource.oracle
//		instance = tbn
//		max_connections = 100
//		min_connections = 1
//		name = finance
//		objectClass = [javax.sql.DataSource]
//		password = tbn
//		refresh = 0.01
//		service.factoryPid = navajo.resource.oracle
//		service.pid = navajo.resource.oracle-1371816104784-98
//		type = oracle
//		url = jdbc:oracle:thin:@odysseus:1521:SLTEST02
//		user = tbnkern
		String url = (String) settings.get("url");
		String user = (String) settings.get("user");
		String password = (String) settings.get("password");
		int minConns = (Integer) settings.get("min_connections");
		int maxConns = (Integer) settings.get("max_connections");
		double refresh = (Double) settings.get("refresh");
		
		try {
			this.dbConnectionBroker = new DbConnectionBroker(null, url, user, password, minConns, maxConns, refresh);
		} catch (ClassNotFoundException e) {
			// doesnt happen
		}
		this.settings = settings;
	}

	@Override
	public long getInstanceId() {
		return id;
	}

	@Override
	public Connection getConnection() throws SQLException {
		if(connection==null) {
			connection = this.datasource.getConnection();
		}
		return connection;
	}

	@Override
	public void setAged() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isAged() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAgedForced() {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		if(connection!=null) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("Error closing connection: ", e);
			}
			connection = null;
		}
		logger.info("Destroying datasource. Removing from provider");
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public DbConnectionBroker getMyBroker() {
		return dbConnectionBroker;
	}
	
	@Override
	public long setInstanceId(long l) {
		logger.debug("Ignoring set instanceId: "+l);
		return 0;
	}

	@Override
	public void autocommit(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rollback(boolean b) {
		// TODO Auto-generated method stub
		
	}	

}
