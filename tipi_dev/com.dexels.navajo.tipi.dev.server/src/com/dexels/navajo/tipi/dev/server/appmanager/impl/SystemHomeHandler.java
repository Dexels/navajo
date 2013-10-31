package com.dexels.navajo.tipi.dev.server.appmanager.impl;

import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemHomeHandler {

	private ConfigurationAdmin configurationAdmin;
	
	private final static Logger logger = LoggerFactory
			.getLogger(SystemHomeHandler.class);

	private Configuration systemPropertyConfiguration;

	private Configuration applicationManagerConfiguration;
	
	public void setConfigurationAdmin(ConfigurationAdmin configurationAdmin) {
		this.configurationAdmin = configurationAdmin;
	}


	public void clearConfigurationAdmin(ConfigurationAdmin a) {
		this.configurationAdmin = null;
	}

	
	public void activate() throws IOException {
		String homePath = System.getProperty("tipi.store.path");
		if(homePath!=null) {
			File homeFolder = new File(homePath);
			systemPropertyConfiguration = createOrReuse("org.apache.felix.fileinstall", "(name=systemproperty)");
			Dictionary<String,Object> settings = new Hashtable<String,Object>();
			settings.put("name", "systemproperty");
			settings.put("felix.fileinstall.dir", new File(homeFolder,"load").getAbsolutePath());

			applicationManagerConfiguration = createOrReuse("tipi.dev.applicationmanager", "(name=manager.systemproperty)");
			Dictionary<String,Object> appsettings = new Hashtable<String,Object>();
			appsettings.put("name", "manager.systemproperty");
			appsettings.put("tipi.store.path", homePath);

			updateIfChanged(systemPropertyConfiguration, settings);
			updateIfChanged(applicationManagerConfiguration, appsettings);

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

	public void deactivate() {
		if(systemPropertyConfiguration!=null) {
			try {
				systemPropertyConfiguration.delete();
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
		}
		if(applicationManagerConfiguration!=null) {
			try {
				applicationManagerConfiguration.delete();
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
		}

	}

}
