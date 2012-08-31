package com.dexels.navajo.resource.test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.resource.JDBCMap;
import com.dexels.navajo.adapter.sqlmap.ResultSetMap;
import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.resource.manager.ResourceManager;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.server.Access;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class ResourceManagerTest {
	private ResourceManager resourceManager;
	private ComponentContext componentContext;
	private BundleContext bundleContext;
	private static ResourceManagerTest instance;
	private static final Logger logger = LoggerFactory.getLogger(ResourceManagerTest.class);
	
	public ResourceManager getResourceManager() {
		return resourceManager;
	}

	public void activate(ComponentContext c) {
		this.componentContext = c;
		this.bundleContext = componentContext.getBundleContext();

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
//		logger.info("Starting test");
//		testNonExisting();
//		testH2();
//		testMysql();
		
	}

public void testH2() throws Exception, SQLException {
//		ResourceReference rr;
//		Map<String, Object> settings = new HashMap<String, Object>(); 
//		rr = new ResourceReference("h2database", "h2",settings);
//		settings.put("url", "jdbc:h2:~/test");
//		settings.put("name", "testdatabase");
//		settings.put("user", "sa");
//		settings.put("password", "");
//		resourceManager.addResourceReference(rr);
//		if(rr.isActive()) {
//			logger.info("Good, H2 exists");
//		} else {
//			logger.warn("Problem: H2 unavailable");
//			return;
//		}
//		ConnectionPoolDataSource d = (ConnectionPoolDataSource) rr.getInstance().getSource();
//		PooledConnection pooledConnection = d.getPooledConnection();
//		Connection connection = pooledConnection.getConnection();
		DataSource ds = getDataSource("h2_test");
		Connection connection = ds.getConnection();
		PreparedStatement s2 = connection.prepareStatement("show tables;");
		ResultSet ts = s2.executeQuery();
		logger.info("RWOS: "+ts);
		connection.close();
	}

public void testMongo() throws Exception, SQLException {
	Map<String, Object> settings = new HashMap<String, Object>(); 
	settings.put("host", "localhost");
	settings.put("database", "demo");
//	ResourceReference rr = new ResourceReference("mongodb demo", "mongodb",settings);
//	resourceManager.addResourceReference(rr);
//	if(rr.isActive()) {
//		logger.info("Good, mongo exists");
//	} else {
//		logger.warn("Problem: Mongo unavailable");
//		return;
//	}
	
	DB d = (DB) getResourceReference("navajostore");
	DBCollection cc = d.getCollection("Person");
	long count = cc.count();
	logger.info("Size: "+count);
	if(count>3) {
		logger.info("Ok, it adds up");
	}
}


public void testMysqlService() throws SQLException, InvalidSyntaxException {
	ServiceReference<DataSource> dref =  getDataSourceReference("mysql_swingstreet"); // dlist.iterator().next();
	DataSource d = bundleContext.getService(dref);
	Connection connection = d.getConnection();
	PreparedStatement s2 = connection.prepareStatement("select name from jos_components");
	ResultSet ts = s2.executeQuery();
	  while (ts.next()) {
            String title = ts.getString("name");
            logger.info("title: "+title);
	  }
	connection.close();
	bundleContext.ungetService(dref);
	dref = null;

}

public ServiceReference<DataSource> getDataSourceReference(String shortName) throws InvalidSyntaxException {
	Collection<ServiceReference<DataSource>> dlist = bundleContext.getServiceReferences(DataSource.class,"(name=navajo.resource."+shortName+")");
	if(dlist.size()!=1) {
		logger.info("Matched: {} datasources.",dlist.size());
	}
	ServiceReference<DataSource> dref = dlist.iterator().next();
	return dref;
}

public DataSource getDataSource(String shortName) throws InvalidSyntaxException {
	ServiceReference<DataSource> ss = getDataSourceReference(shortName);
	return bundleContext.getService(ss);
}

public ServiceReference<Object> getResourceReference(String shortName) throws InvalidSyntaxException {
	Collection<ServiceReference<Object>> dlist = bundleContext.getServiceReferences(Object.class,"(name=navajo.resource."+shortName+")");
	if(dlist.size()!=1) {
		logger.info("Matched: {} datasources.",dlist.size());
	}
	if(dlist.isEmpty()) {
		logger.error("Can not find datasource: {}",shortName);
	}
	ServiceReference<Object> dref = dlist.iterator().next();
	return dref;
}

public Object getResourceSource(String shortName) throws InvalidSyntaxException {
	ServiceReference<Object> ss = getResourceReference(shortName);
	return bundleContext.getService(ss);
}


public void testOracle() throws Exception, SQLException {
//	ResourceReference rr;
//	Map<String, Object> settings = new HashMap<String, Object>(); 
//	rr = new ResourceReference("oracle_develop", "oracle",settings);
//	settings.put("url", "jdbc:oracle:thin:@10.0.0.1:1521:aardnoot");
//	settings.put("user", "knvbkern");
//	settings.put("password", "knvb");
//	resourceManager.addResourceReference(rr);
//	if(rr.isActive()) {
//		logger.info("Good, Oracle exists");
//	} else {
//		logger.warn("Bad: Oracle not found");
//		return;
//	}
//	resourceManager.getResourceInstance("")
	DataSource d = getDataSource("sportlinkkernel");
	Connection connection = d.getConnection();
	PreparedStatement s2 = connection.prepareStatement("select * from sport");
	ResultSet ts = s2.executeQuery();
	  while (ts.next()) {
            String title = ts.getString("sportid");
            logger.info("title: "+title);
	  }
	  logger.warn("Connection class: {}",connection);
	connection.close();
}


//	private Map<String, Object> testNonExisting() throws Exception {
//		Map<String,Object> settings = new HashMap<String, Object>();
//		ResourceReference rr = new ResourceReference("unknowndatabase", "monkey",settings);
//		resourceManager.addResourceReference(rr);
//		if(!rr.isActive()) {
//			logger.info("Good, no 'monkey' database exists");
//		}
//		return settings;
//	}
//	

	
	public void testJDBCMap2() throws Exception {
//		Map<String,Object> settings = new HashMap<String, Object>();
//		ResourceReference rr = new ResourceReference("mysqldatabase", "mysql",settings);
//		settings.put("url", "jdbc:mysql://localhost/swingstreet");
//		settings.put("user", "root");
//		settings.put("password", "root");
//		resourceManager.addResourceReference(rr);
//
		
		JDBCMap jm = new JDBCMap();
		jm.setDebug(true);
		Access a = new Access(1,2,"demo","rpcname","agent","ip","host",null);
		jm.load(a);
		jm.setDatasource("mysql_swingstreet");
		jm.setQuery("select name from jos_components");

		ResultSetMap[] rsm = jm.getResultSet();
		for (ResultSetMap resultSetMap : rsm) {
			logger.info("result: {}",resultSetMap.getColumnValue("name"));
		}
		
		int trans = jm.getTransactionContext();
		logger.info("Transaction: {}",trans);
		
		JDBCMap jm2 = new JDBCMap();
		jm2.setDebug(true);
		jm2.load(a);
		jm2.setTransactionContext(trans);
		jm2.setQuery("select name from jos_components");
		
		//		Binary b = jm.getRecords();
//		String res = new String(b.getData());
//		logger.info("result: {}",res);
		ResultSetMap[] rsm2 = jm2.getResultSet();
		for (ResultSetMap resultSetMap : rsm2) {
			logger.info("result2: {}",resultSetMap.getColumnValue("name"));
		}
	}
	
	public void loadResources() {
		InputStream is = getClass().getClassLoader().getResourceAsStream("datasources.xml");
		resourceManager.loadResourceTml(is);
	}
	public void testAAA() throws InvalidSyntaxException, SQLException {
		String regionFetch = "SELECT DISTINCT district_cd, district_name FROM sportlink_user_districts WHERE user_id = 3723 ORDER BY district_name";
		DataSource d = getDataSource("sportlinkkernel");
		Connection connection = d.getConnection();
		PreparedStatement s2 = connection.prepareStatement(regionFetch);
		ResultSet ts = s2.executeQuery();
		  while (ts.next()) {

	            String title = ts.getString("district_cd");
	            logger.info("title: "+title);
		  }
		connection.close();

	}
	public void testClient() throws InvalidSyntaxException, ClientException {
		ServiceReference<LocalClient> l = bundleContext.getServiceReference(LocalClient.class);
		LocalClient cc = bundleContext.getService(l);
		// do magic
//		cc.callService("navajo/InitNavajoStatus");
//		Navajo n = cc.getNavajo("navajo/InitNavajoStatus");
		Navajo n = NavajoFactory.getInstance().createNavajo(getClass().getResourceAsStream("process.xml"));
		Navajo response = null;
		for (int i = 0; i < 10; i++) {
			try {
				response = cc.call(n);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		response.write(System.err);
		
		//		xx.
	}
}
