package com.dexels.navajo.repository.file.impl;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.repository.api.RepositoryInstance;

public class FileRepositoryInstanceImpl extends BaseFileRepositoryInstanceImpl implements RepositoryInstance {
	
	private final static Logger logger = LoggerFactory
			.getLogger(FileRepositoryInstanceImpl.class);

	public void activate(Map<String,Object> configuration) throws IOException {

		String path = (String) configuration.get("repository.folder");
		type = (String) configuration.get("type");
		repositoryName = (String) configuration.get("repository.name");
		final String fileInstallPath= (String) configuration.get("felix.fileinstall.filename");

		applicationFolder = findConfiguration(path,fileInstallPath);
		super.setupMonitoredFolders();
	}

	public void deactivate() {
		if(watchDir!=null) {
			try {
				watchDir.close();
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
			watchDir = null;
		}
	}
	



}
