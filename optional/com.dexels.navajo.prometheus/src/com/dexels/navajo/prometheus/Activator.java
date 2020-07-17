package com.dexels.navajo.prometheus;

import java.rmi.activation.ActivateFailedException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.dexels.navajo.server.listener.http.schedulers.priority.PriorityThreadPoolSchedulerMBean;

import io.prometheus.client.Gauge;

//public class Activator implements BundleActivator {
public class Activator{	
	private PriorityThreadPoolSchedulerMBean nomralPoolInfoService;
//	static final Gauge inprogressRequests = Gauge.build()
//		     .name("inprogress_requests").help("Inprogress requests.").register();

	
//	@Override
//	public void start(BundleContext context) throws Exception {
//		System.out.println("gem---Started...");
//		
//        MetricsServlet ms = new MetricsServlet();
//
//        Server server = new Server(1234);
//        ServletContextHandler context1 = new ServletContextHandler();
//        context1.setContextPath("/");
//        server.setHandler(context1);
//
//        context1.addServlet(new ServletHolder(ms), "/metrics");
//        server.start();
//        System.out.println("main");
//	}

	public void activate() throws Exception {
		System.out.println("gem---Started...");
		
        MetricsServlet ms = new MetricsServlet();

        Server server = new Server(1234);
        ServletContextHandler context1 = new ServletContextHandler();
        context1.setContextPath("/");
        server.setHandler(context1);

        context1.addServlet(new ServletHolder(ms), "/metrics");
        server.start();
        System.out.println("main");
	}
	
	public void deactivate () {
		
	}
	
    public void setNomralPoolInfo (PriorityThreadPoolSchedulerMBean m) {
    	System.out.println("vg---set");
    	this.nomralPoolInfoService = m;
    }

    public void clearNomralPoolInfo (PriorityThreadPoolSchedulerMBean m) {
    	System.out.println("vg---clear");
    	this.nomralPoolInfoService = null;
    }
	
	
//	public void stop(BundleContext context) throws Exception {
//		
//		System.out.println("gem---Stopped...");
//	}

}
