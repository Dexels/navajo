package com.dexels.navajo.env;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentConfigComponent {

	private ConfigurationAdmin configAdmin;

//	private Set<Configuration> ownedConfigurations = new HashSet<Configuration>();
//	private final Set<String> resourcePids = new HashSet<String>();

	private final static Logger logger = LoggerFactory.getLogger(EnvironmentConfigComponent.class);
	
	public EnvironmentConfigComponent() {
	}

	public void activate() {
		try {
			Map<String, String> env = System.getenv();
			boolean isDev = "true".equals(env.get("SHAREDSTORE_DEVELOP_MODE"));
			Configuration config = null;
			if (isDev) {
				config = configAdmin.getConfiguration("navajo.sharedstore.file",null);
			} else {
				config = configAdmin.getConfiguration("navajo.sharedstore.mongo",null);
			}
			config.update(new Hashtable<String, Object>());
			
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}

	public void setConfigAdmin(ConfigurationAdmin configAdmin) {
		this.configAdmin = configAdmin;
	}

	/**
	 * @param configAdmin
	 *            the configAdmin to remove
	 */
	public void clearConfigAdmin(ConfigurationAdmin configAdmin) {
		this.configAdmin = null;
	}
}
