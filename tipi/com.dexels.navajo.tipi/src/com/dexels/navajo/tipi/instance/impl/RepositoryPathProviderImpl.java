package com.dexels.navajo.tipi.instance.impl;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.tipi.instance.InstancePathProvider;

public class RepositoryPathProviderImpl implements InstancePathProvider {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(RepositoryPathProviderImpl.class);
	
	private RepositoryInstance repositoryInstance;
	
	public void setRepositoryInstance(RepositoryInstance ri) {
		this.repositoryInstance = ri;
	}

	public void clearRepositoryInstance(RepositoryInstance ri) {
		this.repositoryInstance = null;
	}

	public void activate() {
		logger.info("Activate repo");
	}
	
	public void deactivate() {
		logger.info("Deactivate repo");
	}
	
	@Override
	public File getInstancePath() {
		return repositoryInstance.getRepositoryFolder();
	}

}
