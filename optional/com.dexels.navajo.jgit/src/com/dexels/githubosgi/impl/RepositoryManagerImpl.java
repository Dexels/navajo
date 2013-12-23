package com.dexels.githubosgi.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.repository.api.RepositoryManager;

public class RepositoryManagerImpl implements RepositoryManager {
	
	
	protected String organization;
	private File repositoryFolder;
	private File sshFolder;
	private File configurationFolder;
	
	private final static Logger logger = LoggerFactory
			.getLogger(RepositoryManagerImpl.class);
	

	
	public void activate(Map<String,Object> configuration) throws IOException {
		String path = (String) configuration.get("storage.path");
		
		if(path==null || "".equals(path)) {
			path = System.getProperty("storage.path");
		}
		if(path==null) {
			throw new IOException("No storage.path set in configuration!");
		}
		File storeFolder = new File(path);
		
		repositoryFolder = new File(storeFolder,"repositories");
		if(!repositoryFolder.exists()) {
			repositoryFolder.mkdirs();
		}
		sshFolder = new File(storeFolder,"gitssh");
		if(!sshFolder.exists()) {
			sshFolder.mkdirs();
		}
		configurationFolder = new File(storeFolder,"etc");
		if(!configurationFolder.exists()) {
			configurationFolder.mkdirs();
		}
		logger.info("Repository manager activated");

	}
	
	public void deactivate() {

	}


	@Override
	public File getSshFolder() {
		return sshFolder;
	}

	@Override
	public File getRepositoryFolder() {
		return repositoryFolder;
	}

	@Override
	public File getConfigurationFolder() {
		return configurationFolder;
	}

}
