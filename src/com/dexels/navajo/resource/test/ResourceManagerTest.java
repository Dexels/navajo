package com.dexels.navajo.resource.test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.PooledConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.JDBCMap;
import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.resource.ResourceInstance;
import com.dexels.navajo.resource.manager.ResourceManager;
import com.dexels.navajo.resource.manager.ResourceReference;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class ResourceManagerTest {
	private ResourceManager resourceManager;
	private static ResourceManagerTest instance;
	private static final Logger logger = LoggerFactory.getLogger(ResourceManagerTest.class);
	

	public ResourceManagerTest() {
		System.err.println("CREATED!");
	}
	public ResourceManager getResourceManager() {
		return resourceManager;
	}

	public static ResourceManagerTest getInstance() {
		return instance;
	}

	public void setResourceManager(ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
		try {
			testResourceManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
		instance = this;
	}


	public void clearResourceManager(ResourceManager resourceManager) {
		this.resourceManager = null;
	}

	private void testResourceManager() throws Exception {
		logger.info("Starting test");
		testNonExisting();
		testH2();
		testMysql();
		
	}

public void testH2() throws Exception, SQLException {
		ResourceReference rr;
		Map<String, Object> settings = new HashMap<String, Object>(); 
		rr = new ResourceReference("h2database", "h2",settings);
		settings.put("url", "jdbc:h2:~/test");
		settings.put("name", "testdatabase");
		settings.put("user", "sa");
		settings.put("password", "");
		resourceManager.addResourceReference(rr);
		if(rr.isActive()) {
			logger.info("Good, H2 exists");
		} else {
			logger.warn("Problem: H2 unavailable");
			return;
		}
		ConnectionPoolDataSource d = (ConnectionPoolDataSource) rr.getInstance().getSource();
		PooledConnection pooledConnection = d.getPooledConnection();
		Connection connection = pooledConnection.getConnection();
		PreparedStatement s2 = connection.prepareStatement("show tables;");
		ResultSet ts = s2.executeQuery();
		logger.info("RWOS: "+ts);
		pooledConnection.close();
	}

public void testMongo() throws Exception, SQLException {
	Map<String, Object> settings = new HashMap<String, Object>(); 
	settings.put("host", "localhost");
	settings.put("database", "demo");
	ResourceReference rr = new ResourceReference("mongodb demo", "mongodb",settings);
	resourceManager.addResourceReference(rr);
	if(rr.isActive()) {
		logger.info("Good, mongo exists");
	} else {
		logger.warn("Problem: Mongo unavailable");
		return;
	}
	DB d = (DB) rr.getInstance().getSource();
	DBCollection cc = d.getCollection("Person");
	long count = cc.count();
	logger.info("Size: "+count);
	if(count>3) {
		logger.info("Ok, it adds up");
	}
}

public void testMysql() throws Exception, SQLException {
		ResourceReference rr;
		Map<String, Object> settings = new HashMap<String, Object>(); 
		rr = new ResourceReference("mysqldatabase", "mysql",settings);
		settings.put("url", "jdbc:mysql://localhost/swingstreet");
		settings.put("user", "root");
		settings.put("password", "root");
		resourceManager.addResourceReference(rr);
		if(rr.isActive()) {
			logger.info("Good, Mysql exists");
		} else {
			logger.warn("Bad: MySql not found");
			return;
		}
//		resourceManager.getResourceInstance("")
		DataSource d = (DataSource) rr.getInstance().getSource();
		Connection connection = d.getConnection();
		PreparedStatement s2 = connection.prepareStatement("select name from jos_components");
		ResultSet ts = s2.executeQuery();
		  while (ts.next()) {
	            String title = ts.getString("name");
	            logger.info("title: "+title);
		  }
		connection.close();
	}
public void testLoadedMysql() throws Exception, SQLException {
	ResourceInstance rr = resourceManager.getResourceInstance("mysql_swingstreet");
	DataSource d = (DataSource) rr.getSource();
	Connection connection = d.getConnection();
	PreparedStatement s2 = connection.prepareStatement("select name from jos_components");
	ResultSet ts = s2.executeQuery();
	  while (ts.next()) {
            String title = ts.getString("name");
            logger.info("title: "+title);
	  }
	connection.close();
}

	private Map<String, Object> testNonExisting() throws Exception {
		Map<String,Object> settings = new HashMap<String, Object>();
		ResourceReference rr = new ResourceReference("unknowndatabase", "monkey",settings);
		resourceManager.addResourceReference(rr);
		if(!rr.isActive()) {
			logger.info("Good, no 'monkey' database exists");
		}
		return settings;
	}
	
	public void testJDBCMap() throws MappableException, UserException {
		JDBCMap jm = new JDBCMap();
		Access a = new Access(1,2,"demo","rpcname","agent","ip","host",null);
		jm.load(a);
		jm.setDatasource("mysql_swingstreet");
		jm.setQuery("select name from jos_components");
//		Binary b = jm.getRecords();
//		String res = new String(b.getData());
//		logger.info("result: {}",res);
		ResultSetMap[] rsm = jm.getResultSet();
		for (ResultSetMap resultSetMap : rsm) {
			logger.info("result: {}",resultSetMap.getColumnValue("name"));
		}
	}
	
	public void testJDBCMap2() throws MappableException, UserException {
		JDBCMap jm = new JDBCMap();
		Access a = new Access(1,2,"demo","rpcname","agent","ip","host",null);
		jm.load(a);
		jm.setDatasource("test");
		jm.setQuery("select * from person");
//		Binary b = jm.getRecords();
//		String res = new String(b.getData());
//		logger.info("result: {}",res);
		ResultSetMap[] rsm = jm.getResultSet();
		for (ResultSetMap resultSetMap : rsm) {
			logger.info("result: {}",resultSetMap.getColumnValue("lastname"));
		}
	}
	
	public void loadResources() {
		InputStream is = getClass().getClassLoader().getResourceAsStream("datasources.xml");
		resourceManager.loadResourceTml(is);
	}
}
