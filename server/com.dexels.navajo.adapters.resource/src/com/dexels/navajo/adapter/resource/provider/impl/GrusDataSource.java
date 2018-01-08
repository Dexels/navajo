package com.dexels.navajo.adapter.resource.provider.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.dexels.grus.DbConnectionBroker;
import org.dexels.grus.GrusConnection;
import org.dexels.grus.GrusProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.SQLMaintenanceMap;
import com.dexels.navajo.adapter.sqlmap.SQLMapConstants;

public class GrusDataSource implements GrusConnection {

	private final DataSource datasource;
	private final Map<String, Object> settings;
	private DbConnectionBroker dbConnectionBroker;
	private final int id;
	private final Connection connection;

	private final GrusProvider grusProvider;

	
	private final static Logger logger = LoggerFactory.getLogger(GrusDataSource.class);
	
	
	public GrusDataSource(int id, DataSource dataSourceInstance,Map<String, Object> settings, GrusProvider provider) throws Exception {
		this.datasource = dataSourceInstance;
		this.id = id;
		this.grusProvider = provider;

		String user = (String) settings.get("user");
		final Object minObject = settings.get("min_connections");
		int minConns = 1;
		if(minObject!=null) {
			minConns = (Integer) minObject;
		}
		int maxConns = 99;
		final Object maxObject = settings.get("max_connections");
		if(maxObject!=null) {
			maxConns = (Integer) maxObject;
		}
		final Object refreshObject = settings.get("refresh");
		double refresh = 0.01;
		if(refreshObject!=null) {
			refresh = (Double) refreshObject;
		}

		if(this.datasource==null) {
			throw new Exception("No datasource in GrusDataSource");
		}
		this.dbConnectionBroker = new DbConnectionBrokerWrapper(this, user, maxConns);   
		this.connection = this.datasource.getConnection();
		
		if (connection != null && connection.getMetaData() != null) {
		    String driverName = connection.getMetaData().getDriverName();
		    if (driverName.startsWith("PostgreSQL")) {
		        dbConnectionBroker.setDbIdentifier(SQLMapConstants.POSTGRESDB);
		    }  else if (driverName.startsWith("Oracle")) {
	            dbConnectionBroker.setDbIdentifier(SQLMapConstants.ORACLEDB);
	        }
		}
		
		this.settings = settings;
	}

	@Override
	public long getInstanceId() {
		return id;
	}

	@Override
	public Connection getConnection() throws SQLException {
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
		}
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

	@Override
    public DataSource getDatasource() {
        return datasource;
    }
	
	

}
