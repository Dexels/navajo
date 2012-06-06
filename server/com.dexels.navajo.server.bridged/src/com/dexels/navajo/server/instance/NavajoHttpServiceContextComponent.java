package com.dexels.navajo.server.instance;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Properties;

import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.api.NavajoServerContext;
import com.dexels.navajo.server.api.impl.NavajoServerInstance;
import com.dexels.navajo.server.listener.NavajoContextListener;

public class NavajoHttpServiceContextComponent implements NavajoServerContext{

	private static final Logger logger = LoggerFactory.getLogger(NavajoHttpServiceContextComponent.class);
	
	private NavajoServerInstance wrapped = null;
	
	public void activate(ComponentContext c) {
		logger.info("Activating HTTP server component");
		updated(c.getProperties());
	}
	
	@SuppressWarnings("rawtypes")
	public void updated(Dictionary settings) {
		String pid = (String) settings.get("service.pid");
		logger.info("Configuration received, pid: "+pid);
		try {
			logger.info("Instantiating with pid: "+pid);
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
			logger.info("Instantiate server: "+contextPath+" installpath: "+installPath);
			wrapped = NavajoContextListener.initializeServletContext(contextPath,servletContextPath,installPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deactivate() {
		logger.info("Deactivating service component");
	}

	@Override
	public DispatcherInterface getDispatcher() {
		return wrapped.getDispatcher();
	}

	@Override
	public String getInstallationPath() {
		return wrapped.getInstallationPath();
	}


}
