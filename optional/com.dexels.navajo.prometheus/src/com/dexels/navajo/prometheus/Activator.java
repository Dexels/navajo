package com.dexels.navajo.prometheus;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import io.prometheus.client.Gauge;

public class Activator implements BundleActivator {
	
	
//	static final Gauge inprogressRequests = Gauge.build()
//		     .name("inprogress_requests").help("Inprogress requests.").register();
	
	
	
	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("gem---Started...");
		
		//inprogressRequests.inc();
		
//		Server server = new Server(1234);
//		ServletContextHandler context1 = new ServletContextHandler();
//		context1.setContextPath("/");
//		server.setHandler(context1);
//	
//		context1.addServlet(new ServletHolder(), "/metrics");
//		server.start();
		
		//from intelij
        MetricsServlet ms = new MetricsServlet();

        Server server = new Server(1234);
        ServletContextHandler context1 = new ServletContextHandler();
        context1.setContextPath("/");
        server.setHandler(context1);

        context1.addServlet(new ServletHolder(ms), "/metrics");
        server.start();
        System.out.println("main");
		
		
	
	}

	
	
	@Override
	public void stop(BundleContext context) throws Exception {
		
		System.out.println("gem---Stopped...");
	}

}
