package com.dexels.navajo.server.instance;

import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import org.osgi.framework.InvalidSyntaxException;
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
			addAdapterListener(contextPath,installPath);
			logger.info("Instantiate server: "+contextPath+" installpath: "+installPath+" servletContextPath: "+servletContextPath);
			wrapped = NavajoContextListener.initializeServletContext(contextPath,servletContextPath,installPath);
		} catch (Exception e) {
			logger.error("Error starting navajo server: ", e);
		}
	}
	
	private void addAdapterListener(String contextPath, String installPath) throws IOException, InvalidSyntaxException {
		File cp = new File(installPath);
		File adapters = new File(cp,"adapters");

		//fileInstallConfiguration = myConfigurationAdmin.createFactoryConfiguration("org.apache.felix.fileinstall",null);
		final String absolutePath = adapters.getAbsolutePath();
		fileInstallConfiguration = getResourceConfig(contextPath, absolutePath);
		Dictionary<String,Object> d = fileInstallConfiguration.getProperties();
		if(d==null) {
			d = new Hashtable<String,Object>();
		}
		d.put("felix.fileinstall.dir",absolutePath );
		d.put("contextPath",contextPath );

		fileInstallConfiguration.update(d);	
	}
	
	private Configuration getResourceConfig(String name, String path)
			throws IOException, InvalidSyntaxException {
		final String factoryPid = "org.apache.felix.fileinstall";
		Configuration[] cc = myConfigurationAdmin
				.listConfigurations("(&(service.factoryPid=" + factoryPid
						+ ")(felix.fileinstall.dir=" + path + "))");
		if (cc != null) {

			if (cc.length != 1) {
				logger.info("Odd length: " + cc.length);
			}
			return cc[0];
		} else {
			logger.info("Not found: " + name+" creating a new factory config for: "+factoryPid);
			Configuration c = myConfigurationAdmin.createFactoryConfiguration(
					factoryPid, null);
			return c;
		}
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
