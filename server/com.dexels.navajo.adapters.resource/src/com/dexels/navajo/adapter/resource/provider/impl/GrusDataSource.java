package com.dexels.navajo.adapter.resource.provider.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

import javax.sql.DataSource;

import org.dexels.grus.DbConnectionBroker;
import org.dexels.grus.GrusConnection;

public class GrusDataSource implements GrusConnection {

	private final DataSource datasource;
	private final Map<String, Object> settings;
	private DbConnectionBroker dbConnectionBroker;
	private final int id;

	public GrusDataSource(int id, DataSource dataSourceInstance,Map<String, Object> settings) {
		this.datasource = dataSourceInstance;
		this.id = id;

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
		return this.datasource.getConnection();
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

	}

	@Override
	public long getId() {
		return 0;
	}

	@Override
	public DbConnectionBroker getMyBroker() {
		return dbConnectionBroker;
	}
	
	@Override
	public long setInstanceId(long l) {
		// TODO Auto-generated method stub
		return 0;
	}

}
