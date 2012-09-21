package com.dexels.navajo.server.instance;

import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
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
	private ConfigurationAdmin myConfigurationAdmin = null;
	private Configuration fileInstallConfiguration = null;
	
	public void activate(ComponentContext c) {
		logger.info("Activating HTTP server component");
		updated(c.getProperties());
	}
	
	public void modified() {
		logger.info("Navajo HTTP service modified. Why?");
	}
	
	@SuppressWarnings("rawtypes")
	public void updated(Dictionary settings) {
		String pid = (String) settings.get("service.pid");
//		logger.info("Configuration received, pid: "+pid);
		try {
			logger.info("Instantiating with pid: "+pid);
			Properties prop = new Properties(); 
			Enumeration en = settings.keys();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				final Object object = settings.get(key);
				prop.put(key, object);
				logger.info("Keys: "+key+" - "+object);
			}
			
			// TODO add context prefix, and link?
			String contextPath = (String)settings.get("contextPath");
			String servletContextPath = (String)settings.get("servletContextPath");
			String installPath = (String)settings.get("installationPath");
			addAdapterListener(installPath);
			logger.info("Instantiate server: "+contextPath+" installpath: "+installPath);
			wrapped = NavajoContextListener.initializeServletContext(contextPath,servletContextPath,installPath);
		} catch (Exception e) {
			logger.error("Error starting navajo server: ", e);
		}
	}
	
	private void addAdapterListener(String installPath) throws IOException {
		fileInstallConfiguration = myConfigurationAdmin.createFactoryConfiguration("org.apache.felix.fileinstall",null);
		Dictionary<String,Object> d = fileInstallConfiguration.getProperties();
		if(d==null) {
			d = new Hashtable<String,Object>();
		}
		File cp = new File(installPath);
		File adapters = new File(cp,"adapters");
		d.put("felix.fileinstall.dir",adapters.getAbsolutePath() );
		fileInstallConfiguration.update(d);	
		
	}

	public void deactivate() throws IOException {
		logger.info("Deactivating service component");
		if(fileInstallConfiguration!=null) {
			fileInstallConfiguration.delete();
		}
	}

	@Override
	public DispatcherInterface getDispatcher() {
		return wrapped.getDispatcher();
	}

	@Override
	public String getInstallationPath() {
		return wrapped.getInstallationPath();
	}

	public void addConfigurationAdmin(ConfigurationAdmin admin) {
		this.myConfigurationAdmin = admin;
	}

	/**
	 * 
	 * @param admin the ConfigurationAdmin to remove
	 */
	public void clearConfigurationAdmin(ConfigurationAdmin admin) {
		this.myConfigurationAdmin = null;
	}

}
