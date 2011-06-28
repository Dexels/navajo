package com.dexels.navajo.server.bridged;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

public class HttpServiceTracker extends ServiceTracker {
	private BridgeComponent bridgeComponent;

	public HttpServiceTracker(BundleContext context) {
		super(context, HttpService.class.getName(), null);
		System.err.println("Tracker created!");
	}

	public Object addingService(ServiceReference reference) {
		System.err.println("Service detected!");
		HttpService httpService = (HttpService) super.addingService(reference);
		bridgeComponent = new BridgeComponent();
		bridgeComponent.setHttpService(httpService);
		bridgeComponent.startup();

		return httpService;
	}

	 public void removedService(ServiceReference reference, Object service) {
		    bridgeComponent.shutdown();
		  }
}
