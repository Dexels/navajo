package com.dexels.navajo.tipi.dev.server.appmanager.impl;

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

import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationManager;

public class ApplicationManagerImpl implements ApplicationManager {
	
	
	private File appsFolder;
	private ConfigurationAdmin configurationAdmin;
	private boolean running = false;
	private Thread scanThread;
	
	public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		this.configurationAdmin = configurationAdmin;
	}


	public void clearConfigurationAdmin(ConfigurationAdmin a) {
		this.configurationAdmin = null;
	}

	
	private final static Logger logger = LoggerFactory
			.getLogger(ApplicationManagerImpl.class);
	

	public void activate(Map<String,Object> configuration) throws IOException {
		final String path = (String) configuration.get("tipi.store.path");
		setAppsFolder(new File(path));
		running = true;
		this.scanThread = new Thread() {

			@Override
			public void run() {
				while(running) {
					try {
						scan();
						Thread.sleep(SLEEP_TIME);
					} catch (IOException e) {
						logger.error("Error: ", path);
					} catch (InterruptedException e) {
						logger.error("Error: ", path);
					}
				}
			}
			
		};
		scanThread.start();
	}
	
	public void deactivate() {
		this.running = false;
		if(scanThread!=null) {
			scanThread.interrupt();
		}
	}

	public synchronized void  scan() throws IOException {
		logger.info("scanning...");
		Map<String, Configuration> configs = new HashMap<String, Configuration>();
		try {
			Configuration[] l =  configurationAdmin.listConfigurations("(service.factoryPid="+TIPI_STORE_APPLICATION+")");
			if(l!=null) {
				for (Configuration configuration : l) {
					String name = (String) configuration.getProperties().get("name");
					configs.put(name, configuration);
				}
			}
		} catch (InvalidSyntaxException e) {
			logger.error("Error: ", e);
		}
		System.err.println(">> "+configs.keySet());
		File[] apps = appsFolder.listFiles();
		for (File file : apps) {

			if(!file.isDirectory()) {
				continue;
			}
			if(!isTipiAppDir(file)) {
				continue;
			}
			final String name = file.getName();
			logger.info("Application found: "+name);
			Configuration c = createOrReuse(TIPI_STORE_APPLICATION, "(name="+name+")");
			Dictionary<String,Object> settings = new Hashtable<String,Object>();
			settings.put("name", name);
			settings.put("path", file.getAbsolutePath());
			updateIfChanged(c, settings);
			configs.remove(name);
			
//			ApplicationStatusImpl appStatus = new ApplicationStatusImpl();
//			appStatus.setManager(this);
//			appStatus.load(file);
//			appStats.add(appStatus);
		}
		if(!configs.isEmpty()) {
			logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+configs);
			for (Entry<String,Configuration> e : configs.entrySet()) {
				Configuration c = e.getValue();
				if(c!=null) {
					c.delete();
				}
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.dev.server.appmanager.impl.ApplicationManager#getAppsFolder()
	 */
	@Override
	public File getAppsFolder() {
		return appsFolder;
	}

	public void setAppsFolder(File appsFolder) throws IOException {
		logger.info("Using application folder: "+appsFolder.getAbsolutePath());
		this.appsFolder = appsFolder;
	}

	
	protected void deleteConfigurations(String filter) throws IOException, InvalidSyntaxException {
//		"(service.factoryPid="+TIPI_STORE_APPLICATION+")"
		Configuration[] l =  configurationAdmin.listConfigurations(filter);
		if(l!=null) {
			for (Configuration configuration : l) {
				configuration.delete();
			}
		}
	}
	protected Configuration createOrReuse(String pid, final String filter)
			throws IOException {
		Configuration cc = null;
		try {
			Configuration[] c = configurationAdmin.listConfigurations(filter);
			if(c!=null && c.length>1) {
				logger.warn("Multiple configurations found for filter: {}", filter);
			}
			if(c!=null && c.length>0) {
				cc = c[0];
			}
		} catch (InvalidSyntaxException e) {
			logger.error("Error in filter: {}",filter,e);
		}
		if(cc==null) {
			cc = configurationAdmin.createFactoryConfiguration(pid,null);
//			resourcePids.add(cc.getPid());
		}
		return cc;
	}
	
	private void updateIfChanged(Configuration c, Dictionary<String,Object> settings) throws IOException {
		Dictionary<String,Object> old = c.getProperties();
		if(old!=null) {
			if(!old.equals(settings)) {
				c.update(settings);
			}
		} else {
			c.update(settings);
		}
	}

	
	private boolean isTipiAppDir(File tipiRoot) {
		File tipiDir = new File(tipiRoot,"tipi");
		File settingsProp = new File(tipiRoot,"settings/tipi.properties");
		return tipiDir.exists() && settingsProp.exists();
	}

}
