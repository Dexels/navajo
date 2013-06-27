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
	private final Map<Integer,Connection> transactionMap = new ConcurrentHashMap<Integer, Connection>();
	private static BundleContext bundleContext;
	
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
//			final ResourceManager mngr = getInstance().manager;
			DataSource dataSource = getDataSource(name);
			return dataSource;
		} catch (InvalidSyntaxException e) {
			logger.error("Can not resolve datasource{}",name,e);
			return null;
		}
	}
	
	

	public static DataSource getDataSource(String shortName) throws InvalidSyntaxException {
		ServiceReference<DataSource> ss = getDataSourceReference(shortName);
		return bundleContext.getService(ss);
	}
	private static ServiceReference<DataSource> getDataSourceReference(String shortName) throws InvalidSyntaxException {
		logger.debug("Getting datasource reference: "+shortName);
		Collection<ServiceReference<DataSource>> dlist = bundleContext.getServiceReferences(DataSource.class,"(name="+shortName+")");
		if(dlist.size()!=1) {
			logger.info("Matched: {} datasources.",dlist.size());
		}
		ServiceReference<DataSource> dref = dlist.iterator().next();
		return dref;
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
