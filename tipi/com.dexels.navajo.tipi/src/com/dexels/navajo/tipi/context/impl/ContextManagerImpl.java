package com.dexels.navajo.tipi.context.impl;

import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.context.ContextFactory;
import com.dexels.navajo.tipi.context.ContextInstance;
import com.dexels.navajo.tipi.context.ContextManager;

public class ContextManagerImpl implements ContextManager {
	
	private final Map<String,ContextInstance> contexts = new HashMap<String,ContextInstance>();
	private ConfigurationAdmin configAdmin;
	private static ContextManager instance = null;
	private final Map<String,Configuration> resourcePids = new HashMap<String,Configuration>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(ContextManagerImpl.class);
			
			
	public void activate(Map<String,String> params) {
		ContextFactory.setInstance(this);
		String path = System.getProperty("tipiDir");
		if(path!=null) {
			File tipiDir = new File(path);
			if(tipiDir.exists()) {
				File deploy = new File(tipiDir,"deploy");
				if(deploy.exists()) {
					try {
						addFolderMonitorListener(deploy.getAbsolutePath());
					} catch (IOException e) {
						logger.error("Error: ", e);
					} catch (InvalidSyntaxException e) {
						logger.error("Error: ", e);
					}
				}
			}
		} else {
			logger.warn("No tipiDir set");
		}
	}

	public void deactivate() {
		ContextFactory.setInstance(null);
		logger.info(">>>>>> deactivating context manager");
		for (Entry<String,Configuration> entry : resourcePids.entrySet()) {
			try {
				entry.getValue().delete();
			} catch (IOException e) {
				logger.error("Problem deleting configuration for pid: "+entry.getKey(),e);
			}
		}
	}

	public static ContextManager getInstance() {
		return instance;
	}
	
	private void addFolderMonitorListener(String path) throws IOException, InvalidSyntaxException {
		File monitoredFolder = new File(path);
//		File monitoredFolder = new File(cp,subFolder);
		if(!monitoredFolder.exists()) {
			logger.warn("FileInstaller should monitor folder: {} but it does not exist. Will not try again.", monitoredFolder.getAbsolutePath());
			return;
		}
		//fileInstallConfiguration = myConfigurationAdmin.createFactoryConfiguration("org.apache.felix.fileinstall",null);
		final String absolutePath = monitoredFolder.getAbsolutePath();
		Configuration newConfig = getUniqueResourceConfig(absolutePath);
		Dictionary<String,Object> d = newConfig.getProperties();
		if(d==null) {
			d = new Hashtable<String,Object>();
		}
		d.put("felix.fileinstall.dir",absolutePath );
		String pid = newConfig.getPid();
		resourcePids.put(pid, newConfig);
		newConfig.update(d);	
	}
	
	private Configuration getUniqueResourceConfig(String path)
			throws IOException, InvalidSyntaxException {
		final String factoryPid = "org.apache.felix.fileinstall";
		Configuration[] cc = configAdmin
				.listConfigurations("(&(service.factoryPid=" + factoryPid
						+ ")(felix.fileinstall.dir=" + path + "))");
		if (cc != null) {

			if (cc.length != 1) {
				logger.info("Odd length: " + cc.length);
			}
			return cc[0];
		} else {
			logger.info("Not found. creating a new factory config for: "+factoryPid);
			Configuration c = configAdmin.createFactoryConfiguration(
					factoryPid, null);
			return c;
		}
	}
		
	public void setConfigAdmin(ConfigurationAdmin configAdmin) {
		this.configAdmin = configAdmin;
	}

	/**
	 * @param configAdmin the configAdmin to remove 
	 */
	public void clearConfigAdmin(ConfigurationAdmin configAdmin) {
		this.configAdmin = null;
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.context.impl.ContextManager#addContextInstance(com.dexels.navajo.tipi.context.ContextInstance)
	 */
	@Override
	public void addContextInstance(ContextInstance ci) {
		String context = ci.getContext();
		contexts.put(context, ci);
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.context.impl.ContextManager#removeContextInstance(com.dexels.navajo.tipi.context.ContextInstance)
	 */
	@Override
	public void removeContextInstance(ContextInstance ci) {
		contexts.remove(ci.getContext());
	}
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.context.impl.ContextManager#getContext(java.lang.String)
	 */
	@Override
	public ContextInstance getContext(String context) {
		return contexts.get(context);
	}

}
