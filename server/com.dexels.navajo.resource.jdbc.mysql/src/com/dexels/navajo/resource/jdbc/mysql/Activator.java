package com.dexels.navajo.resource.jdbc.mysql;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
//		MySQLJDBCDataSourceService factory = new MySQLJDBCDataSourceService();
//        factory.start();
//        managedFactory = new JdbcManagedResourceFactory(bundleContext, factory,  "navajo.resource.mysql",  "Navajo MySQL Resource Driver");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
//		logger.info("Stopping MySQL");
//		managedFactory.close();
	}


}