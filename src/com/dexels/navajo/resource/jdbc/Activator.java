package com.dexels.navajo.resource.jdbc;

import java.sql.Driver;
import java.util.Dictionary;
import java.util.Hashtable;

import org.h2.jdbc.service.H2DataSourceFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.jdbc.DataSourceFactory;

import com.dexels.navajo.resource.ResourceConfig;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceRegistration<DataSourceFactory> service;
	private ServiceRegistration<ResourceConfig> configService;
	private static DataSourceFactory dataSourceFactory;

	
	public static DataSourceFactory getDataSourceFactory() {
		return dataSourceFactory;
	}

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
        H2DataSourceFactory factory = new H2DataSourceFactory();
        factory.init();
        Driver driver = factory.createDriver(null);
        Dictionary<String, Object> properties = new Hashtable<String, Object>();
        properties.put(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS, "org.h2.Driver");
        properties.put("h2", "resourceType");
        properties.put(DataSourceFactory.OSGI_JDBC_DRIVER_NAME, "H2, the Java SQL database");
        properties.put(DataSourceFactory.OSGI_JDBC_DRIVER_VERSION, driver.getMajorVersion() + "." + driver.getMinorVersion());
        service = context.registerService(DataSourceFactory.class, factory, properties);
        dataSourceFactory = factory;
        // now, register config type
        Dictionary<String, Object> prop = new Hashtable<String, Object>();
        H2ResourceConfig h2r = new H2ResourceConfig();
        prop.put(ResourceConfig.CONFIGNAME, h2r.getConfigName());
        prop.put(ResourceConfig.TYPE, h2r.getType());
        configService =  context.registerService(ResourceConfig.class, h2r, prop);
        
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		System.err.println("Stopping h2 stuff");
		try {
            service.unregister();
            configService.unregister();
            service = null;
            configService = null;
        } catch (RuntimeException rte) {
            //Ignore
        }
	}

}
