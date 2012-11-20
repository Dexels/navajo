package com.dexels.navajo.runtime.provisioning;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.obr.RepositoryAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PullConfigurator {
	
	private final static Logger logger = LoggerFactory
			.getLogger(PullConfigurator.class);
	
	@SuppressWarnings("unused")
	private ConfigurationAdmin myConfigurationAdmin = null;
	@SuppressWarnings("unused")
	private RepositoryAdmin myRepositoryAdmin = null;

	public void addRepositoryAdmin(RepositoryAdmin admin) {
		this.myRepositoryAdmin = admin;
	}

	/**
	 * @param admin the RepositoryAdmin to remove 
	 */
	public void clearRepositoryAdmin(RepositoryAdmin admin) {
		this.myRepositoryAdmin = null;
	}

	public void addConfigurationAdmin(ConfigurationAdmin admin) {
		logger.info("Adding config adming");
		this.myConfigurationAdmin = admin;
	}

	/**
	 * The configurationAdmin to remove
	 * @param admin
	 */
	public void clearConfigurationAdmin(ConfigurationAdmin admin) {
		this.myConfigurationAdmin = null;
	}

	public void activate() {
	}
}
