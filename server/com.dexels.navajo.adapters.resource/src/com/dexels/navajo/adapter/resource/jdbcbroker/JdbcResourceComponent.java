package com.dexels.navajo.adapter.resource.jdbcbroker;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.osgi.framework.InvalidSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.resource.manager.ResourceManager;

public class JdbcResourceComponent {
	private ResourceManager manager;
	private static JdbcResourceComponent instance = null;
	private static final Logger logger = LoggerFactory.getLogger(JdbcResourceComponent.class);
	private final Map<Integer,Connection> transactionMap = new HashMap<Integer, Connection>();
//	private BundleContext bundleContext;
	
	public void activate() {
		setInstance(this);
	}

	public void deactivate() {
		setInstance(null);
	}

	private static void setInstance(JdbcResourceComponent jdbcResourceComponent) {
		instance = jdbcResourceComponent;
		
	}
	public void setResourceManager(ResourceManager r) {
		logger.info("Adding Resource Manager, instantiating JdbcResourceComponent");
		manager = r;
		instance = this;
	}

	/**
	 * @param r the resource manager to remove 
	 */
	public void removeResourceManager(ResourceManager r) {
		logger.info("Removing Resource Manager, uninstantiating JdbcResourceComponent");
		manager = null;
		instance = null;
		transactionMap.clear();
	}
	
	public static JdbcResourceComponent getInstance() {
		return instance;
	}


	public static DataSource getJdbc(String name) {
		try {
			final ResourceManager mngr = getInstance().manager;
			DataSource dataSource = mngr.getDataSource(name);
			return dataSource;
		} catch (InvalidSyntaxException e) {
			logger.error("Can not resolve datasource{}",name,e);
			return null;
		}
	}
	
	

	public static void setTestConnection() {
//		testConnection = new Mongo();
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
