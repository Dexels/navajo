package com.dexels.navajo.server.instance;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.api.NavajoServerContext;
import com.dexels.navajo.server.api.impl.NavajoServerInstance;
import com.dexels.navajo.server.listener.NavajoContextListener;
import com.dexels.navajo.server.listener.http.TmlHttpServlet;
import com.dexels.navajo.server.listener.http.continuation.TmlContinuationServlet;
import com.dexels.navajo.server.listener.nql.NqlServlet;

public class NavajoContextFactory implements ManagedServiceFactory {

	private final Map<String,NavajoServerContext> contextMap = new HashMap<String, NavajoServerContext>();
	private final Map<String,ServiceRegistration<NavajoServerContext>> registryMap = new HashMap<String,ServiceRegistration<NavajoServerContext>>();
	private final ServiceRegistration<ManagedServiceFactory> factoryRegistration;
	private final BundleContext bundleContext;
	private final String pid;

	private static final String SERVLET_ALIAS = "/Postman";
	private static final String LEGACY_SERVLET_ALIAS = "/PostmanLegacy";

	
	private HttpContext httpContext;
	private ServletContext servletContext;
	private HttpService httpService;

	private final static Logger logger = LoggerFactory.getLogger(NavajoContextFactory.class);
	
	public NavajoContextFactory(BundleContext bc, HttpService httpService) {
		this.bundleContext = bc;
		this.pid = "navajo.server.context";
		this.httpService = httpService;
        Dictionary<String, Object> managedProperties = new Hashtable<String, Object>();
        managedProperties.put(Constants.SERVICE_PID, this.pid);
        factoryRegistration = bundleContext.registerService(ManagedServiceFactory.class, this, managedProperties);

	}

	
	public ServiceRegistration<ManagedServiceFactory> getFactoryRegistration() {
		return factoryRegistration;
	}


	@Override
	public void deleted(String pid) {
		logger.info("Shutting down instance: "+pid);
		NavajoServerContext nc = contextMap.get(pid);
		if(nc==null) {
			logger.warn("Strange: Deleting, but already gone.");
			return;
		}
		contextMap.remove(pid);
		ServiceRegistration<NavajoServerContext> reg = registryMap.get(pid);
		reg.unregister();

	}

	@Override
	public String getName() {
		return "Navajo Context Factory";
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public void updated(String pid, Dictionary settings)
			throws ConfigurationException {
		logger.info("Configuration received, pid: "+pid);
		try {
			NavajoServerContext source = instantiate(bundleContext, pid,settings);
			logger.info("Activating Navajo Service service: "+settings);
			contextMap.put(pid, (NavajoServerContext) source);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public NavajoServerContext instantiate(BundleContext bc, String pid, Dictionary settings) throws Exception {
		Properties prop = new Properties(); 
		Enumeration en = settings.keys();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			prop.put(key, settings.get(key));
		}
		// TODO add context prefix, and link?
		String contextPath = (String)settings.get("contextPath");
		String servletContextPath = (String)settings.get("servletContextPath");
		String installPath = (String)settings.get("installationPath");
		
		NavajoServerContext nsi = createContext(this.httpService,contextPath,servletContextPath,installPath);
		ServiceRegistration<NavajoServerContext> reg =  bundleContext.registerService(NavajoServerContext.class,nsi , settings);
		registryMap.put(pid, reg);
		return nsi;
	}
	
	private NavajoServerContext createContext(HttpService httpService, String contextPath, String servletPath, String installPath) {
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
			ncl.init(ns.getServletContext(),contextPath,servletPath,installPath);
	
			servletContext = servlet.getServletContext();
//			String contextPath = servletContext.getContextPath();
//			String servletContextPath = servletContext.getRealPath("");
//			String installationPath = NavajoContextListener.getInstallationPath(contextPath);

			NavajoServerContext nsc = NavajoContextListener.initializeServletContext(contextPath,servletPath,installPath);
//			installationPath = NavajoContextListener.getInstallationPath(servletContext, null);
			logger.info(" OSGi HttpService started. Somebody should notice.");
			return nsc;
			
		} catch (ServletException e) {
			logger.error("Problem instantiating server instance",e);
		} catch (Throwable e) {
			logger.error("Problem instantiating server instance",e);
		}
		return null;
	}

	
	// unused?
	public NavajoServerInstance initializeServletContext(String contextPath, String servletContextPath, String installationPath) {
		try {
			DispatcherInterface dispatcher = NavajoContextListener.initDispatcher(servletContextPath, servletContextPath, installationPath);
			NavajoServerInstance nsi = new NavajoServerInstance(installationPath, dispatcher);
			return nsi;
		} catch (Exception e) {
			logger.error("Error initializing dispatcher", e);
		}
		return null;
	}

	
	public void close() {
		for (Entry<String,ServiceRegistration<NavajoServerContext>> s: registryMap.entrySet()) {
			s.getValue().unregister();
		}

		for (Entry<String, NavajoServerContext> s: contextMap.entrySet()) {
			// close individual datasources?
		}

		registryMap.clear();
		contextMap.clear();
		
		factoryRegistration.unregister();
		
	}

}
