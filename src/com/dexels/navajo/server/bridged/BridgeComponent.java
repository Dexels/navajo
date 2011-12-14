package com.dexels.navajo.server.bridged;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;

import com.dexels.navajo.server.listener.NavajoContextListener;
import com.dexels.navajo.server.listener.http.TmlHttpServlet;
import com.dexels.navajo.server.listener.http.continuation.TmlContinuationServlet;
import com.dexels.navajo.server.listener.nql.NqlServlet;

public class BridgeComponent {

	private static final String SERVLET_ALIAS = "/Postman";
	private static final String LEGACY_SERVLET_ALIAS = "/PostmanLegacy";

	public void setBundleContext(BundleContext bundleContext) {
//		this.bundleContext = bundleContext;
	}

	
//	<servlet>
//	<servlet-name>Comet</servlet-name>
//	<servlet-class>com.dexels.navajo.server.listener.http.continuation.TmlContinuationServlet</servlet-class>
//    <init-param>
//        <param-name>schedulerClass</param-name>
//        <param-value>com.dexels.navajo.server.listener.http.schedulers.priority.PriorityThreadPoolScheduler</param-value>
//    </init-param>
//    <init-param>
//        <param-name>priorityPoolSize</param-name>
//        <param-value>15</param-value>
//    </init-param>
//    <init-param>
//        <param-name>normalPoolSize</param-name>
//        <param-value>20</param-value>
//    </init-param>
//    <init-param>
//        <param-name>systemPoolSize</param-name>
//        <param-value>2</param-value>
//    </init-param>
//    <async-supported>true</async-supported>
//    </servlet>	
	
	public void setHttpService(HttpService httpService) {
		System.err.println("Injecting HTTP service");
		try {
			HttpContext cc = httpService.createDefaultHttpContext();
			System.out.println("Staring up sevlet at " + SERVLET_ALIAS);
			NavajoContextListener ncl = new NavajoContextListener();
			
			NqlServlet ns = new NqlServlet();
			httpService.registerServlet("/Nql", ns, null, cc);
			ncl.init(ns.getServletContext());
	
			TmlHttpServlet servlet = new TmlHttpServlet();
			httpService.registerServlet(LEGACY_SERVLET_ALIAS, servlet, null, cc);

			
			Dictionary<String, Object> wb = new Hashtable<String, Object>();
			 wb.put("schedulerClass", "com.dexels.navajo.server.listener.http.schedulers.priority.PriorityThreadPoolScheduler");
			 wb.put("priorityPoolSize", "15");
			 wb.put("normalPoolSize", "20");
			 wb.put("systemPoolSize", "2");

			TmlContinuationServlet tcs = new TmlContinuationServlet();
			
			httpService.registerServlet(SERVLET_ALIAS, tcs, wb, cc);

		
			ServletContext servletContext = servlet.getServletContext();
			System.err.println("Serv: "+servletContext.getContextPath());
			System.err.println("Serv2: "+servletContext.getServletContextName());
					NavajoContextListener.initializeContext(servletContext, null);
			System.err.println("Context initialized!");
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void startup() {

	}

	public void shutdown() {

	}

	public void modified() {

	}

}
