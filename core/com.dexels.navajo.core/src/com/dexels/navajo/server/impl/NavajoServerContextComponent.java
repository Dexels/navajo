package com.dexels.navajo.server.impl;

import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.api.NavajoServerContext;

public class NavajoServerContextComponent implements NavajoServerContext {

	protected String installationPath;

	private final static Logger logger = LoggerFactory
			.getLogger(NavajoServerContextComponent.class);
	
	private ConfigurationAdmin myConfigurationAdmin = null;
	private final Map<String,Configuration> resourcePids = new HashMap<String,Configuration>();

	protected boolean suppressAdapters = false;;

	public void setConfigurationAdmin(ConfigurationAdmin ca) {
		this.myConfigurationAdmin = ca;
	}

	public void clearConfigurationAdmin(ConfigurationAdmin ca) {
		this.myConfigurationAdmin = null;
	}
	
	public ConfigurationAdmin getConfigurationAdmin() {
		return myConfigurationAdmin;
	}
	

	public void activate(Map<String,Object> settings) {
		try {
			String contextPath = (String)settings.get("contextPath");
			installationPath = (String) settings.get("installationPath");
			String injectedPath = System.getenv("navajo.path");
			if(injectedPath!=null) {
				installationPath = injectedPath;
			}
			String suppressAdapters = System.getProperty("navajo.suppress.adaptersfolder");
			if("true".equals(suppressAdapters)) {
				this.suppressAdapters  = true;
			}
			initializeContext(installationPath,contextPath);
		} catch (IOException e) {
			logger.error("Error creating folder monitor: ",e);
		} catch( Throwable t) {
			logger.error("Whoops: ",t);
		}
	}

	protected void initializeContext(String installationPath,String contextPath) throws IOException {
		this.installationPath = installationPath;
		emitLogbackConfiguration(installationPath);
	}
	
	public void deactivate()  {
		try {

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
	

	@Override
	public String getOutputPath() {
		return getInstallationPath();
	}

	@Override
	public String getTempPath() {
		return getInstallationPath();
	}
	

	
	@Deprecated
	// shouldn't be necessary, move log config to the log components
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

    @Override
    public String getDeployment() {
        logger.warn("getDeployment not implemented in OSGi implementation");
        return null;
    }


}
