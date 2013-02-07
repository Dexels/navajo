package com.dexels.navajo.tipi.vaadin.instance.impl;

import java.util.Set;

import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.persistence.impl.PersistenceManagerImpl;
import com.dexels.navajo.tipi.vaadin.instance.InstanceConfigurationProvider;

public class InstanceConfigurationProviderImpl implements
		InstanceConfigurationProvider {

	private ConfigurationAdmin myConfigurationAdmin;
	
	private final static Logger logger = LoggerFactory
			.getLogger(InstanceConfigurationProviderImpl.class);
	
	@Override
	public Set<String> getProfiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getDeployments() {
		// TODO Auto-generated method stub
		return null;
	}
	

	public void setConfigAdmin(ConfigurationAdmin configAdmin) {
		this.myConfigurationAdmin = configAdmin;
	}

	/**
	 * @param configAdmin the configAdmin to remove 
	 */
	public void clearConfigAdmin(ConfigurationAdmin configAdmin) {
		this.myConfigurationAdmin = null;
	}
	
	public void activate() {
		logger.info("========>  Activating");
	}

	public void deactivate() {
		logger.info(">>>>>> deactivating navajo config");
	}
	
}
