/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter.resource.jdbcbroker;

import java.sql.Connection;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcResourceComponent {
	private static JdbcResourceComponent instance = null;
	private static final Logger logger = LoggerFactory.getLogger(JdbcResourceComponent.class);
	private final Map<Integer,Connection> transactionMap = new ConcurrentHashMap<>();
	private BundleContext bundleContext;
	
	public void activate(BundleContext bc) {
		bundleContext = bc;
		setInstance(this);
	}

	public void deactivate() {
		bundleContext = null;
		setInstance(null);
	}

	private static void setInstance(JdbcResourceComponent jdbcResourceComponent) {
		instance = jdbcResourceComponent;
		
	}

	public static JdbcResourceComponent getInstance() {
		return instance;
	}


	public static DataSource getJdbc(String name) {
		try {
			return getDataSource(name);
		} catch (InvalidSyntaxException e) {
			logger.error("Can not resolve datasource{}",name,e);
			return null;
		}
	}
	
	

	public static DataSource getDataSource(String shortName) throws InvalidSyntaxException {
		ServiceReference<DataSource> ss = getDataSourceReference(shortName);
		return instance.bundleContext.getService(ss);
	}
	private static ServiceReference<DataSource> getDataSourceReference(String shortName) throws InvalidSyntaxException {
		logger.debug("Getting datasource reference: {}", shortName);
		Collection<ServiceReference<DataSource>> dlist = getInstance().bundleContext.getServiceReferences(DataSource.class,"(name="+shortName+")");
		if(dlist.size()!=1) {
			logger.info("Matched: {} datasources.",dlist.size());
		}
		return dlist.iterator().next();
	}
	

	public static void setTestConnection() {
	}

	public Connection getJdbc(int transactionContext) {
		return getInstance().transactionMap.get(transactionContext);
	}
	public void registerTransaction(int transactionContext, Connection con) {
		logger.info("Registring context {}",transactionContext);
		transactionMap.put(transactionContext, con);
	}
	public void deregisterTransaction(int transactionContext) {
		logger.info("Deregistring context {}",transactionContext);
		transactionMap.remove(transactionContext);
	}

}
