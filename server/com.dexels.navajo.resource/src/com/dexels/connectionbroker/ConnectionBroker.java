/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.connectionbroker;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;

import org.osgi.service.jdbc.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionBroker {
	
	private static final Logger logger = LoggerFactory.getLogger(ConnectionBroker.class);

	private final Map<String,DataSourceFactory> driverMap = new HashMap<String,DataSourceFactory>();
	
	
	public void activate() {
//		Dictionary properties = context.getProperties();
//			Enumeration en = properties.keys();
//			while(en.hasMoreElements()) {
//				String key = (String)en.nextElement();
//			}
	}
	
	public void registerDriver(DataSourceFactory df) {
		logger.debug("Registering driver: "+df.getClass()+" : "+DataSourceFactory.JDBC_DESCRIPTION);
		String name = df.getClass().getName();
		driverMap.put(name, df);
	}

	public void unregisterDriver(DataSourceFactory df) {
		logger.info("Unregistering driver: "+df.getClass()+" : "+DataSourceFactory.JDBC_DESCRIPTION);
	}

	public ConnectionPoolDataSource getPooledConnection(String name, Properties settings) throws SQLException {
		DataSourceFactory dsf = driverMap.get(name);
		return dsf.createConnectionPoolDataSource(settings);
	}
}
