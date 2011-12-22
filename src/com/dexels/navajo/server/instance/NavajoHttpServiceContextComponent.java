package com.dexels.navajo.server.instance;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.api.NavajoServerContext;
import com.dexels.navajo.server.listener.NavajoContextListener;
import com.dexels.navajo.server.listener.http.TmlHttpServlet;
import com.dexels.navajo.server.listener.http.continuation.TmlContinuationServlet;
import com.dexels.navajo.server.listener.nql.NqlServlet;

public class NavajoHttpServiceContextComponent {

	private static final String SERVLET_ALIAS = "/Postman";
	private static final String LEGACY_SERVLET_ALIAS = "/PostmanLegacy";

	private static final Logger logger = LoggerFactory.getLogger(NavajoHttpServiceContextComponent.class);
	
	private HttpContext httpContext;
	private ServletContext servletContext;



	protected void setHttpService(HttpService httpService) {
		try {
			httpContext = httpService.createDefaultHttpContext();
			
			NqlServlet ns = new NqlServlet();
			httpService.registerServlet("/Nql", ns, null, httpContext);
			TmlHttpServlet servlet = new TmlHttpServlet();
			httpService.registerServlet(LEGACY_SERVLET_ALIAS, servlet, null, httpContext);
			
			Dictionary<String, Object> wb = new Hashtable<String, Object>();
			wb.put("schedulerClass","com.dexels.navajo.server.listener.http.schedulers.priority.PriorityThreadPoolScheduler");
			wb.put("priorityPoolSize", "15");
			wb.put("normalPoolSize", "20");
			wb.put("systemPoolSize", "2");
			TmlContinuationServlet tcs = new TmlContinuationServlet();
			httpService.registerServlet(SERVLET_ALIAS, tcs, wb, httpContext);
		
			
			NavajoContextListener ncl = new NavajoContextListener();
			ncl.init(ns.getServletContext());
	
			servletContext = servlet.getServletContext();
			NavajoContextListener.initializeContext(servletContext, null);
//			installationPath = NavajoContextListener.getInstallationPath(servletContext, null);
			logger.info("Vintage OSGi HttpService started. Somebody should notice.");
			
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void removeHttpService(HttpService httpService) {
		logger.info("Shutting down navajo instance");
		NavajoContextListener.destroyContext(servletContext);
		servletContext = null;
		httpService = null;
	}


}
