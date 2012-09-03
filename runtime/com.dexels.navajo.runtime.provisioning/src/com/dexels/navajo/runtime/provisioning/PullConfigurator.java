package com.dexels.navajo.runtime.provisioning;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.obr.RepositoryAdmin;

public class PullConfigurator {
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
