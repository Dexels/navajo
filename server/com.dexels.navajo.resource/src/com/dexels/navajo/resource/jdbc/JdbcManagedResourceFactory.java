package com.dexels.navajo.resource.jdbc;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.jdbc.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcManagedResourceFactory implements ManagedServiceFactory {

	
	private final String name;
	private final String pid;
	private final BundleContext bundleContext;
	
	
//	private final Map<String,DataSource> contextMap = new HashMap<String, DataSource>();
	private final Map<String,ServiceRegistration<DataSource>> registryMap = new HashMap<String,ServiceRegistration<DataSource>>();

    private final DataSourceFactory factory;

	
	private final static Logger logger = LoggerFactory.getLogger(JdbcManagedResourceFactory.class);
	@SuppressWarnings("rawtypes")
	private ServiceRegistration factoryRegistration;
	
	public JdbcManagedResourceFactory(BundleContext bc,DataSourceFactory factory, String pid, String name) {
		this.bundleContext = bc;
		this.pid = pid;
		this.name = name;
		this.factory = factory;
		
        Dictionary<String, Object> managedProperties = new Hashtable<String, Object>();
        managedProperties.put(Constants.SERVICE_PID, this.pid);
        factoryRegistration = bundleContext.registerService(ManagedServiceFactory.class.getName(), this, managedProperties);

	}
	
	@Override
	public void deleted(String pid) {
		logger.info("Shutting down instance: "+pid);
//		DataSource nc = contextMap.get(pid);
//		if(nc==null) {
//			logger.warn("Strange: Deleting, but already gone.");
//			return;
//		}
//		contextMap.remove(pid);
		ServiceRegistration<DataSource> reg = registryMap.get(pid);
		reg.unregister();

	}

	@Override
	public String getName() {
		return name;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void updated(String pid, Dictionary settings)
			throws ConfigurationException {
//		logger.info("Configuration received, pid: "+pid);
		try {
			Object source = instantiate(settings);
			ServiceRegistration<DataSource> reg =  bundleContext.registerService(DataSource.class,(DataSource)source, settings);
			registryMap.put(pid, reg);
			logger.info("Resource registered for: "+pid);
//			contextMap.put(pid, (DataSource) source);
		} catch (Exception e) {
			logger.error("Error registering service for: "+pid);
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	private Object instantiate(Dictionary settings) throws Exception {
		Properties prop = new Properties(); 
		Enumeration en = settings.keys();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			prop.put(key, settings.get(key));
		}
	    prop.put(DataSourceFactory.JDBC_URL,settings.get(DataSourceFactory.JDBC_URL)); 
	    prop.put(DataSourceFactory.JDBC_USER, settings.get(DataSourceFactory.JDBC_USER)); 
	    prop.put(DataSourceFactory.JDBC_PASSWORD, settings.get(DataSourceFactory.JDBC_PASSWORD)); 
	    prop.put(DataSourceFactory.JDBC_MAX_POOL_SIZE, 10); 
	    prop.put(DataSourceFactory.JDBC_INITIAL_POOL_SIZE, 10); 
//	    prop.put(DataSourceFactory.JDBC_PROPERTY_CYCLE, arg1)
	    DataSource source = factory.createDataSource(prop); 
	    return source;
	}

	public void close() {
		for (Entry<String,ServiceRegistration<DataSource>> s: registryMap.entrySet()) {
			s.getValue().unregister();
		}
		registryMap.clear();
		factoryRegistration.unregister();
	}
}
