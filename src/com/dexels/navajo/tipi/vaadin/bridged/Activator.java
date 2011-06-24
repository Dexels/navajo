package com.dexels.navajo.tipi.vaadin.bridged;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.vaadin.application.servlet.TipiVaadinServlet;

public class Activator implements BundleActivator {

	private static final Logger logger = LoggerFactory.getLogger(Activator.class); 

	private ServiceTracker httpServiceTracker;
	
	public void start(BundleContext context) throws Exception {
		httpServiceTracker = new HttpServiceTracker(context);
		httpServiceTracker.open();
		System.err.println("Bridged bundle strted");
		logger.info("Starting bundle");
//		ServiceReference refs = context.getServiceReference(HttpService.class.getName());
//		HttpService hs =  (HttpService) context.getService(refs);
//
//		hs.registerServlet("/helloworld2", new HelloWorldServlet(), null, null);
//		System.err.println("Bundle started!");
	}

	public void stop(BundleContext context) throws Exception {
		logger.info("Stopping bundle");

		httpServiceTracker.close();
		httpServiceTracker = null;
	}

	private class HttpServiceTracker extends ServiceTracker {

		public HttpServiceTracker(BundleContext context) {
			super(context, HttpService.class.getName(), null);
			logger.info("Service tracker created");
		}

		public Object addingService(ServiceReference reference) {
			HttpService httpService = (HttpService) context.getService(reference);
			try {			
				logger.info("Service tracker created");

				System.err.println("Registering to httpService: "+httpService);
				httpService.registerResources("/helloworld.html", "/helloworld.html", null); 
				
				TipiVaadinServlet vaadin = new TipiVaadinServlet();
//				sh.setInitParameter("application", "com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication");
//				vaadin.
				Dictionary<String, String> dict = new Hashtable<String, String>();
				dict.put("application", "com.dexels.navajo.tipi.vaadin.application.TipiVaadinApplication");
				httpService.registerServlet("/app/*", vaadin, dict, null); //$NON-NLS-1$
			} catch (Exception e) {
				e.printStackTrace();
			}
			return httpService;
		}		
		
		public void removedService(ServiceReference reference, Object service) {
			HttpService httpService = (HttpService) service;
			httpService.unregister("/helloworld.html"); //$NON-NLS-1$
			httpService.unregister("/helloworld"); //$NON-NLS-1$
			super.removedService(reference, service);
		}
	}
}
