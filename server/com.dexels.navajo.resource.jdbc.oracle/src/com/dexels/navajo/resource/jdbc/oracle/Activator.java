package com.dexels.navajo.resource.jdbc.oracle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.jdbc.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.resource.jdbc.JdbcManagedResourceFactory;
import com.oracle.jdbc.service.impl.OracleJDBCDataSourceService;


public class Activator implements BundleActivator {

	private static BundleContext context;
	private static DataSourceFactory dataSourceFactory;
	private static final Logger logger = LoggerFactory.getLogger(Activator.class);
	

	public static DataSourceFactory getDataSourceFactory() {
		return dataSourceFactory;
	}

	static BundleContext getContext() {
		return context;
	}

	private JdbcManagedResourceFactory managedFactory;
	
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		try {
			OracleJDBCDataSourceService factory = new OracleJDBCDataSourceService();
			factory.start();
			managedFactory = new JdbcManagedResourceFactory(bundleContext, factory,  "navajo.resource.oracle",  "Navajo Oracle Resource Driver");
		} catch (Exception e) {
			logger.error("Error starting oracle bundle: ",e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		logger.info("Stopping Oracle");
		managedFactory.close();
	}

	
}
