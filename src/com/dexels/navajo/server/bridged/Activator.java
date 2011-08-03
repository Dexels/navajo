package com.dexels.navajo.server.bridged;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;

public class Activator implements BundleActivator {

	private static BundleContext context;
	 private HttpServiceTracker serviceTracker;
	 
	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(final BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.err.println("Unactive activator starting!");
		serviceTracker = new HttpServiceTracker(context);
	    serviceTracker.open();
	    
		ServiceReference sr = bundleContext.getServiceReference("org.osgi.service.http.HttpService");
		HttpService o = (HttpService) bundleContext.getService(sr);
		BridgeComponent bc = new BridgeComponent();
		bc.setHttpService(o);
		bc.startup();
	}

	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	    serviceTracker.close();
	    serviceTracker = null;
	}

}
