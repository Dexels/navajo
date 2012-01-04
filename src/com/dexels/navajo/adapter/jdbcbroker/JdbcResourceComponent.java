package com.dexels.navajo.adapter.jdbcbroker;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.JDBCMap;
import com.dexels.navajo.resource.JdbcResourceInstance;
import com.dexels.navajo.resource.ResourceInstance;
import com.dexels.navajo.resource.manager.ResourceManager;

public class JdbcResourceComponent {
	private ResourceManager manager;
	private static JdbcResourceComponent instance = null;
	private static final Logger logger = LoggerFactory.getLogger(JdbcResourceComponent.class);
	private final Map<Integer,Connection> transactionMap = new HashMap<Integer, Connection>();
	
	public void setup() {
		instance =  this;
	}
	public void setResourceManager(ResourceManager r) {
		logger.info("Adding Resource Manager, instantiating JdbcResourceComponent");
		manager = r;
		instance = this;
	}

	public void removeResourceManager(ResourceManager r) {
		logger.info("Removing Resource Manager, uninstantiating JdbcResourceComponent");
		manager = null;
		instance = null;
		transactionMap.clear();
	}
	
	public static JdbcResourceComponent getInstance() {
		return instance;
	}

	private ResourceInstance getResourceInstance(String name) {
		return manager.getResourceInstance(name);
	}
	
	public static DataSource getJdbc(String name) {
		logger.info("Looking for jdbc connection for resource: "+name);
		JdbcResourceInstance ri = (JdbcResourceInstance) getInstance().getResourceInstance(name);
		return  (DataSource) ri.getSource();
	}
	
	public static void setTestConnection() {
//		testConnection = new Mongo();
	}

	public Connection getJdbc(int transactionContext) {
		return getInstance().transactionMap.get(transactionContext);
	}
	public void registerTransaction(int transactionContext, Connection con) {
		transactionMap.put(transactionContext, con);
	}
	public void deregisterTransaction(int transactionContext) {
		logger.info("Deregistring context {}",transactionContext);
		transactionMap.remove(transactionContext);
	}

}
