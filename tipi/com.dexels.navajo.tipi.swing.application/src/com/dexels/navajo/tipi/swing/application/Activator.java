package com.dexels.navajo.tipi.swing.application;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.TipiApplicationInstance;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private TipiApplicationInstance instance;

	
	private final static Logger logger = LoggerFactory
			.getLogger(Activator.class);
	
	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */

	public void start(final BundleContext bc) throws Exception {
		Activator.context = bc;
		

	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		logger.info("Stopping swing application bundle");
		if(instance!=null) {
			instance.getCurrentContext().shutdown();
			
		}
		Activator.context = null;
		
	}

}
