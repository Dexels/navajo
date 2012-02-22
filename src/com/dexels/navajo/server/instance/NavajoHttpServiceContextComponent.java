package com.dexels.navajo.server.instance;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.http.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.listener.NavajoContextListener;

public class NavajoHttpServiceContextComponent {

	private static final Logger logger = LoggerFactory.getLogger(NavajoHttpServiceContextComponent.class);
	
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
		this.httpService = httpService;
	}


	public void removeHttpService(HttpService httpService) {
		logger.info("Shutting down navajo instance");
		NavajoContextListener.destroyContext(servletContext);
		servletContext = null;
		httpService = null;
	}


}
