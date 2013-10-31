package com.dexels.navajo.server.instance;

import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.api.NavajoServerContext;

public class NavajoHttpServiceContextComponent implements NavajoServerContext{

	private static final Logger logger = LoggerFactory.getLogger(NavajoHttpServiceContextComponent.class);
	
//	private NavajoServerInstance wrapped = null;
	private ConfigurationAdmin myConfigurationAdmin = null;
	private final Set<Configuration> monitoredFolderConfigurations = new HashSet<Configuration>();

//	private DispatcherInterface dispatcher;

	private String installationPath;
	
	public NavajoHttpServiceContextComponent() {
	}
	
	public void activate(Map<String,Object> properties) {
		try {
			logger.info("Activating HTTP server component");
			updated(properties);
		} catch (Throwable e) {
			logger.warn("Error activating NavajoHttpServiceContextComponent ",e);
		}
	}
	
	public void modified() {
			logger.info("Navajo HTTP service modified. Why?");
	}
	
	public void updated(Map<String,Object> settings) {
//		String pid = (String) settings.get("service.pid");
//		logger.info("Configuration received, pid: "+pid);
		try {
//			logger.info("Instantiating with pid: "+pid);
//			Properties prop = new Properties(); 
//			Enumeration en = settings.keys();
//			while (en.hasMoreElements()) {
//				String key = (String) en.nextElement();
//				final Object object = settings.get(key);
//				prop.put(key, object);
//				logger.info("Keys: "+key+" - "+object);
//			}
//			
//			// TODO add context prefix, and link?
			String contextPath = (String)settings.get("contextPath");
			String servletContextPath = (String)settings.get("servletContextPath");
			String installPath = (String)settings.get("installationPath");
			
			this.installationPath = installPath;
			addFolderMonitorListener(contextPath,installPath,"adapters");
			addFolderMonitorListener(contextPath,installPath,"camel");
			logger.info("Instantiate server: "+contextPath+" installpath: "+installPath+" servletContextPath: "+servletContextPath);
//			wrapped = NavajoContextListener.initializeServletContext(contextPath,servletContextPath,installPath);
		} catch (Exception e) {
			logger.error("Error starting navajo server: ", e);
		}
	}
	
	private void addFolderMonitorListener(String contextPath, String installPath, String subFolder) throws IOException, InvalidSyntaxException {
		File cp = new File(installPath);
		File monitoredFolder = new File(cp,subFolder);
		if(!monitoredFolder.exists()) {
			logger.warn("FileInstaller should monitor folder: {} but it does not exist. Will not try again.", monitoredFolder.getAbsolutePath());
			return;
		}
		//fileInstallConfiguration = myConfigurationAdmin.createFactoryConfiguration("org.apache.felix.fileinstall",null);
		final String absolutePath = monitoredFolder.getAbsolutePath();
		Configuration newConfig = getUniqueResourceConfig(contextPath, absolutePath);
		Dictionary<String,Object> d = newConfig.getProperties();
		if(d==null) {
			d = new Hashtable<String,Object>();
		}
		d.put("felix.fileinstall.dir",absolutePath );
		d.put("contextPath",contextPath );
		monitoredFolderConfigurations.add(newConfig);
		newConfig.update(d);	
	}
	
	private Configuration getUniqueResourceConfig(String name, String path)
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
//
//	public void setDispatcher(DispatcherInterface di) {
//		this.dispatcher = di;
//	}
//	
//	public void clearDispatcher(DispatcherInterface di) {
//		this.dispatcher = null;
//	}
	

	public void deactivate() throws IOException {
		logger.info("Deactivating service component");
		if(monitoredFolderConfigurations!=null) {
			for (Configuration c : monitoredFolderConfigurations) {
				c.delete();
			}
			monitoredFolderConfigurations.clear();
		}
	}

//	@Override
//	public DispatcherInterface getDispatcher() {
////		return wrapped.getDispatcher();
//		return dispatcher;
//	}

	@Override
	public String getInstallationPath() {
//		return wrapped.getInstallationPath();
		return this.installationPath;
	}

	public void addConfigurationAdmin(ConfigurationAdmin admin) {
		logger.info("Adding configuration admin");
		this.myConfigurationAdmin = admin;
	}

	/**
	 * 
	 * @param admin the ConfigurationAdmin to remove
	 */
	public void clearConfigurationAdmin(ConfigurationAdmin admin) {
		logger.info("Removing configuration admin");
		this.myConfigurationAdmin = null;
	}

}
