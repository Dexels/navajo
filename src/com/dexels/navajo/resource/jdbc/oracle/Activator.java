package com.dexels.navajo.resource.jdbc.oracle;

import java.sql.Driver;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.jdbc.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.resource.ResourceConfig;
import com.oracle.jdbc.service.OracleJDBCDataSourceService;


public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceRegistration<DataSourceFactory> service;
	private ServiceRegistration<ResourceConfig> configService;
	private static DataSourceFactory dataSourceFactory;
	private static final Logger logger = LoggerFactory.getLogger(Activator.class);
	
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
		logger.info("Starting Oracle driver");

		OracleJDBCDataSourceService factory = new OracleJDBCDataSourceService();
        Driver driver = factory.createDriver(null);
        Dictionary<String, Object> properties = new Hashtable<String, Object>();
        properties.put(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS, "oracle.jdbc.OracleDriver");
        properties.put("oracle", "resourceType");
        properties.put(DataSourceFactory.OSGI_JDBC_DRIVER_NAME, "Driver for Oracle SQL database");
        properties.put(DataSourceFactory.OSGI_JDBC_DRIVER_VERSION, driver.getMajorVersion() + "." + driver.getMinorVersion());
        service = context.registerService(DataSourceFactory.class, factory, properties);
        dataSourceFactory = factory;
        // now, register config type
        Dictionary<String, Object> prop = new Hashtable<String, Object>();
        OracleResourceConfig h2r = new OracleResourceConfig();
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
		service.unregister();
		configService.unregister();
	}

}
