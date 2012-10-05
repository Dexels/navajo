package com.dexels.navajo.resource;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ManagedResourceFactory<T> implements ManagedServiceFactory {

	
	private final String name;
	private final String pid;
	private final BundleContext bundleContext;
	
	
//	private final Map<String,DataSource> contextMap = new HashMap<String, DataSource>();
	private final Map<String,ServiceRegistration<T>> registryMap = new HashMap<String,ServiceRegistration<T>>();

	private final static Logger logger = LoggerFactory.getLogger(ManagedResourceFactory.class);
	@SuppressWarnings("rawtypes")
	private ServiceRegistration factoryRegistration;
	private Class<?> serviceClass;
	
	public ManagedResourceFactory(Class<?> serviceClass, BundleContext bc,String pid, String name) {
		this.bundleContext = bc;
		this.pid = pid;
		this.name = name;
		this.serviceClass = serviceClass;
		
        Dictionary<String, Object> managedProperties = new Hashtable<String, Object>();
        managedProperties.put(Constants.SERVICE_PID, this.pid);
        factoryRegistration = bundleContext.registerService(ManagedServiceFactory.class.getName(), this, managedProperties);
        logger.info("Registering resource service: "+serviceClass.getName()+" - "+pid);
	}


	@Override
	public void deleted(String pid) {
		logger.info("Shutting down instance: "+pid);
		ServiceRegistration<T> reg = registryMap.get(pid);
		ServiceReference<T> reference = reg.getReference();
		T s = bundleContext.getService( reference);
		closeService(s);
		reg.unregister();

	}

	
	
	public abstract void closeService(T service);
	
	@Override
	public String getName() {
		return name;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void updated(String pid, Dictionary settings)
			throws ConfigurationException {
		logger.info("Configuration received, pid: "+pid+" class: "+serviceClass);
		try {
			Object source = instantiate(bundleContext, pid,settings);
			ServiceRegistration<T> reg =  (ServiceRegistration<T>) bundleContext.registerService(serviceClass.getName(),source, settings);
			registryMap.put(pid, reg);
//			contextMap.put(pid, (DataSource) source);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	public abstract T instantiate(BundleContext bc, String pid, Dictionary settings) throws Exception;
//	{
//		Properties prop = new Properties(); 
//		Enumeration en = settings.keys();
//		while (en.hasMoreElements()) {
//			String key = (String) en.nextElement();
//			prop.put(key, settings.get(key));
//		}
//	    prop.put(DataSourceFactory.JDBC_URL,settings.get(DataSourceFactory.JDBC_URL)); 
//	    prop.put(DataSourceFactory.JDBC_USER, settings.get(DataSourceFactory.JDBC_USER)); 
//	    prop.put(DataSourceFactory.JDBC_PASSWORD, settings.get(DataSourceFactory.JDBC_PASSWORD)); 
//	    prop.put(DataSourceFactory.JDBC_MAX_POOL_SIZE, 10); 
//	    prop.put(DataSourceFactory.JDBC_INITIAL_POOL_SIZE, 10); 
////	    prop.put(DataSourceFactory.JDBC_PROPERTY_CYCLE, arg1)
//	    DataSource source = factory.createDataSource(prop); 
//	    return source;
//	}

	
	public void close() {
		for (Entry<String,ServiceRegistration<T>> s: registryMap.entrySet()) {
			s.getValue().unregister();
		}
		registryMap.clear();
		factoryRegistration.unregister();
	}
}
