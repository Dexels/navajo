package com.dexels.navajo.tipi.instance.impl;

import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.tipi.instance.InstancePathProvider;

public class RepositoryPathProviderImpl implements InstancePathProvider {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(RepositoryPathProviderImpl.class);
	
	private RepositoryInstance repositoryInstance;

	private ConfigurationAdmin configAdmin;

	private Configuration configuration;
	
	public RepositoryPathProviderImpl() {
		
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
	
	
	public void setRepositoryInstance(RepositoryInstance ri) {
		this.repositoryInstance = ri;
	}

	public void clearRepositoryInstance(RepositoryInstance ri) {
		this.repositoryInstance = null;
	}

	public void activate() {
		logger.debug("Activate repo");
		File instancePath = getInstancePath();
		File etc = new File(instancePath,"etc");
		if(etc.exists()) {
			try {
				configuration = configAdmin.createFactoryConfiguration("org.apache.felix.fileinstall",null);
				Dictionary<String,Object> d = new Hashtable<String,Object>();
				d.put("felix.fileinstall.dir", etc.getAbsolutePath());
				configuration.update(d);
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
			
		}
	}
	
	public void deactivate() {
		logger.debug("Deactivate repo");
		if(configuration!=null) {
			try {
				configuration.delete();
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
		}
	}
	
	@Override
	public File getInstancePath() {
		return repositoryInstance.getRepositoryFolder();
	}

}
