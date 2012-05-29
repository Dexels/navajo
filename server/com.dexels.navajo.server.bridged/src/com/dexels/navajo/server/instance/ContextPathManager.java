package com.dexels.navajo.server.instance;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.ServletContext;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.listener.NavajoContextListener;

public class ContextPathManager {
	
	private ServletContext servletContext = null;
	private ConfigurationAdmin configurationAdmin = null;
	

	private final static Logger logger = LoggerFactory
			.getLogger(ContextPathManager.class);
	
	public void activate() {
		logger.info("Activating configuration manager");
		String contextPath = servletContext.getContextPath();
		String path = NavajoContextListener.getInstallationPath(contextPath);
		String servletContextPath = servletContext.getRealPath("/");
		try {
			injectConfig(contextPath, servletContextPath, path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	private void injectConfig(String contextPath, String servletContextPath, String installationPath) throws IOException {
		Configuration c = configurationAdmin.createFactoryConfiguration("navajo.server.context.factory");
		Dictionary<String, String> d = new Hashtable<String,String>();
		d.put("contextPath", contextPath);
		d.put("servletContextPath", servletContextPath);
		d.put("installationPath", installationPath);
		c.update(d);
		logger.info(">>>>>>>>>>>>>INJECT CONPLETE: "+installationPath);
	}
	
	public void deactivate() {
		
	}
	
	public void setConfigAdmin(ConfigurationAdmin ca) {
		configurationAdmin = ca;
	}

	public void removeConfigAdmin(ConfigurationAdmin ca) {
		configurationAdmin = null;
	}

	public void setServletContext(ServletContext sc) {
		servletContext = sc;
	}

	public void removeServletContext(ServletContext sc) {
		servletContext = null;
	}

}
