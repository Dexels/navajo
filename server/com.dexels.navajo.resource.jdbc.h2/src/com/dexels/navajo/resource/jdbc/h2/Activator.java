package com.dexels.navajo.resource.jdbc.h2;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.h2.jdbc.service.H2DataSourceFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.jdbc.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.resource.jdbc.JdbcManagedResourceFactory;

public class Activator implements BundleActivator {

	private final static Logger logger = LoggerFactory
			.getLogger(Activator.class);
	private JdbcManagedResourceFactory managedFactory;
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
        H2DataSourceFactory factory = new H2DataSourceFactory();
        factory.init();

        managedFactory = new JdbcManagedResourceFactory(bundleContext, factory,  "navajo.resource.h2",  "Navajo H2 Resource Driver");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		logger.info("Stopping h2");
		managedFactory.close();
	}

}
