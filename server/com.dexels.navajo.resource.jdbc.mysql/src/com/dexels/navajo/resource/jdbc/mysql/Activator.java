package com.dexels.navajo.resource.jdbc.mysql;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.resource.jdbc.JdbcManagedResourceFactory;
import com.mysql.jdbc.service.MySQLJDBCDataSourceService;

public class Activator implements BundleActivator {

	private static final Logger logger = LoggerFactory.getLogger(Activator.class);
	private JdbcManagedResourceFactory managedFactory;


	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		MySQLJDBCDataSourceService factory = new MySQLJDBCDataSourceService();

        factory.start();

        managedFactory = new JdbcManagedResourceFactory(bundleContext, factory,  "navajo.resource.mysql",  "Navajo MySQL Resource Driver");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		logger.info("Stopping MySQL");
		managedFactory.close();
	}


}