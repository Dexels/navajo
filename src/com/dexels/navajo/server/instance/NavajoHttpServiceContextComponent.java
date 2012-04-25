package com.dexels.navajo.server.instance;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
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
	private ConfigurationAdmin configurationAdmin;

	public void activate(ComponentContext c) {
		logger.info("Activating HTTP server component");
		this.bundleContext = c.getBundleContext();
		NavajoContextFactory ncf = new NavajoContextFactory(this.bundleContext,this.httpService);
		this.factory = ncf;
//		try {
//			Configuration configuration = configurationAdmin.createFactoryConfiguration("navajo.server.context.factory");
//			Dictionary<String,String> dict = new Hashtable<String,String>();
//			dict.put("contextPath", "/");
//			dict.put("installationPath", "/Users/frank/Documents/workspace-indigo/sportlink-serv/navajo-tester/auxilary");
//			configuration.update(dict);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
//		"navajo.server.context"
		
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

	protected void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		this.configurationAdmin = configurationAdmin;
	}
	protected void clearConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		this.configurationAdmin = null;
	}

}
