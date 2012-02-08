package com.dexels.navajo.server.instance;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private BundleContext bundleContext;
	private NavajoContextFactory factory;
	private HttpService httpService;


	public void activate(ComponentContext c) {
		logger.info("Activating HTTP server component");
		this.bundleContext = c.getBundleContext();
		NavajoContextFactory ncf = new NavajoContextFactory(this.bundleContext,this.httpService);
		this.factory = ncf;
	}
	
	public void deactivate() {
		this.factory.getFactoryRegistration().unregister();
	}

	protected void setHttpService(HttpService httpService) {
//		createContext(httpService);
		this.httpService = httpService;
	}

//	private void createContext(HttpService httpService) {
//		try {
//			httpContext = httpService.createDefaultHttpContext();
//			
//			NqlServlet ns = new NqlServlet();
//			httpService.registerServlet("/Nql", ns, null, httpContext);
//			TmlHttpServlet servlet = new TmlHttpServlet();
//			httpService.registerServlet(LEGACY_SERVLET_ALIAS, servlet, null, httpContext);
//			
//			Dictionary<String, Object> wb = new Hashtable<String, Object>();
//			wb.put("schedulerClass","com.dexels.navajo.server.listener.http.schedulers.priority.PriorityThreadPoolScheduler");
//			wb.put("priorityPoolSize", "15");
//			wb.put("normalPoolSize", "20");
//			wb.put("systemPoolSize", "2");
//			TmlContinuationServlet tcs = new TmlContinuationServlet();
//			httpService.registerServlet(SERVLET_ALIAS, tcs, wb, httpContext);
//		
//			
//			NavajoContextListener ncl = new NavajoContextListener();
//			ncl.init(ns.getServletContext());
//	
//			servletContext = servlet.getServletContext();
//			String contextPath = servletContext.getContextPath();
//			String servletContextPath = servletContext.getRealPath("");
//			String installationPath = NavajoContextListener.getInstallationPath(contextPath);
//
//			NavajoContextListener.initializeServletContext(contextPath,servletContextPath,installationPath);
////			installationPath = NavajoContextListener.getInstallationPath(servletContext, null);
//			logger.info(" OSGi HttpService started. Somebody should notice.");
//			
//		} catch (ServletException e) {
//			e.printStackTrace();
//		} catch (Throwable e) {
//			e.printStackTrace();
//		}
//	}

	public void removeHttpService(HttpService httpService) {
		logger.info("Shutting down navajo instance");
		NavajoContextListener.destroyContext(servletContext);
		servletContext = null;
		httpService = null;
	}


}
