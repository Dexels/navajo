package com.dexels.navajo.server.impl;

import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.api.NavajoServerContext;

public class NavajoServerContextComponent implements NavajoServerContext {

	private String installationPath;

	private final static Logger logger = LoggerFactory
			.getLogger(NavajoServerContextComponent.class);
	
//	@Override
//	public DispatcherInterface getDispatcher() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	private ConfigurationAdmin myConfigurationAdmin = null;
//	private final Set<Configuration> monitoredFolderConfigurations = new HashSet<Configuration>();
	private final Map<String,Configuration> resourcePids = new HashMap<String,Configuration>();

	public void setConfigurationAdmin(ConfigurationAdmin ca) {
		this.myConfigurationAdmin = ca;
	}

	public void clearConfigurationAdmin(ConfigurationAdmin ca) {
		this.myConfigurationAdmin = null;
	}

	public void activate(Map<String,Object> settings) {
		try {
			String contextPath = (String)settings.get("contextPath");
			installationPath = (String) settings.get("installationPath");
			addFolderMonitorListener(contextPath,installationPath,"adapters");
			addFolderMonitorListener(contextPath,installationPath,"camel");
			emitLogbackConfiguration(installationPath);
			
		} catch (IOException e) {
			logger.error("Error creating folder monitor: ",e);
		} catch (InvalidSyntaxException e) {
			logger.error("Error creating folder monitor: ",e);
		} catch( Throwable t) {
			logger.error("Whoops: ",t);
		}
	}
	
	public void deactivate()  {
		try {
//			if(monitoredFolderConfigurations!=null) {
//				for (Configuration c : monitoredFolderConfigurations) {
//					c.delete();
//				}
//				monitoredFolderConfigurations.clear();
//			}
			for (Entry<String,Configuration> entry : resourcePids.entrySet()) {
				try {
					entry.getValue().delete();
				} catch (IOException e) {
					logger.error("Problem deleting configuration for pid: "+entry.getKey(),e);
				}
			}
		} catch (Throwable e) {
			logger.error("Error: ", e);
		}
	}
	
	@Override
	public String getInstallationPath() {
		return installationPath;
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
		String pid = newConfig.getPid();
		resourcePids.put(pid, newConfig);
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
	
	private void emitLogbackConfiguration(String filePath) throws IOException {
		final String logbackPath = "config/logback.xml";
//		emitIfChanged("navajo.logback", filter, settings)
		File root = new File(filePath);
		File logbackConfigFile = new File(root,logbackPath);
		if(!logbackConfigFile.exists()) {
			logger.warn("No logback configuration file found. Not emitting configuration");
			return;
		}
		Dictionary<String,Object> settings = new Hashtable<String, Object>();
		settings.put("rootPath", filePath);
		settings.put("logbackPath", logbackPath);
		emitConfig("navajo.logback",settings);
	}
	
	private void emitConfig(String pid, Dictionary<String,Object> settings) throws IOException {
		Configuration config =  myConfigurationAdmin.getConfiguration(pid);
		updateIfChanged(config, settings);
	}
	private void updateIfChanged(Configuration c, Dictionary<String,Object> settings) throws IOException {
		Dictionary<String,Object> old = c.getProperties();
		if(old!=null) {
			if(!old.equals(settings)) {
				c.update(settings);
			} else {
				logger.info("Ignoring equal");
			}
		} else {
			// this will make this component 'own' this configuration, unsure if this is desirable.
			resourcePids.put(c.getPid(),c);
			c.update(settings);
		}
	}
	


}
