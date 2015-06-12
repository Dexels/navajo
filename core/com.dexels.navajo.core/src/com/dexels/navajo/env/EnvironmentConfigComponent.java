package com.dexels.navajo.env;

import java.io.IOException;
import java.util.Map;

import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentConfigComponent {

	private ConfigurationAdmin configAdmin;
	
	private final static Logger logger = LoggerFactory
			.getLogger(EnvironmentConfigComponent.class);
	
	public EnvironmentConfigComponent() {
	}

	public void activate() {
		try {
			Map<String, String> env = System.getenv();
			boolean isDev = "true".equals(env.get("DEVELOP_MODE"));
			if (isDev) {
				configAdmin.getConfiguration("navajo.sharedstore.file");
			} else {
				configAdmin.getConfiguration("navajo.sharedstore.mongo");
			}
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
