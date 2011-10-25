package com.dexels.navajo.server.bridged;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		System.err.println("Activating");
		System.err.println("Server started");
//		ServiceReference<HttpService> hs = context.getServiceReference(HttpService.class);
//		HttpService hhs = context.getService(hs);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

}
