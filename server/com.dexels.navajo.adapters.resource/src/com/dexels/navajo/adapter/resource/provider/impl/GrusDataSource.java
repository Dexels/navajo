/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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

import com.dexels.navajo.adapter.sqlmap.SQLMapConstants;
import com.dexels.navajo.script.api.UserException;

public class GrusDataSource implements GrusConnection {
 
	private final DataSource datasource;
	private DbConnectionBroker dbConnectionBroker;
	private final int id;
	private final Connection connection;

	private static final Logger logger = LoggerFactory.getLogger(GrusDataSource.class);
	
	
	public GrusDataSource(int id, DataSource dataSourceInstance,Map<String, Object> settings, GrusProvider provider) throws SQLException, UserException {
		this.datasource = dataSourceInstance;
		this.id = id;

		String user = (String) settings.get("user");
		int maxConns = 99;
		final Object maxObject = settings.get("max_connections");
		if(maxObject!=null) {
			maxConns = (Integer) maxObject;
		}
		if(this.datasource==null) {
			throw new UserException(-1,"No datasource in GrusDataSource");
		}
		this.dbConnectionBroker = new DbConnectionBrokerWrapper(this, user, maxConns);   
		this.connection = this.datasource.getConnection();
		
        if (connection.getMetaData() != null) {
            String driverName = connection.getMetaData().getDriverName();
            if (driverName.startsWith("PostgreSQL")) {
                dbConnectionBroker.setDbIdentifier(SQLMapConstants.POSTGRESDB);
            } else if (driverName.startsWith("MySQL")) {
                dbConnectionBroker.setDbIdentifier(SQLMapConstants.MYSQLDB);
            } else if (driverName.startsWith("Oracle")) {
                dbConnectionBroker.setDbIdentifier(SQLMapConstants.ORACLEDB);
            }
        }
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

	}

	@Override
	public boolean isAged() {
		return false;
	}

	@Override
	public void setAgedForced() {

	}

	@Override
	public void destroy() {
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
		logger.debug("Ignoring set instanceId: {}", l);
		return 0;
	}

	@Override
	public void autocommit(boolean b) {
		
	}

	@Override
	public void rollback(boolean b) {
		
	}

	@Override
    public DataSource getDatasource() {
        return datasource;
    }
	
	

}
