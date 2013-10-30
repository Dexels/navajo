package com.dexels.navajo.resource.mongodb;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Activator implements BundleActivator {

	private static BundleContext context;
	private static final Logger logger = LoggerFactory.getLogger(Activator.class);


	static BundleContext getContext() {
		return context;
	}

	private MongoManagedResourceFactory managedFactory;
	
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
//		logger.info("Starting MongoDb");
        managedFactory = new MongoManagedResourceFactory(bundleContext,  "navajo.resource.mongodb",  "Navajo MongoDb Resource Driver");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		logger.info("Stopping MongoDb");
		managedFactory.close();
	}

	
}
