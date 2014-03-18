package com.dexels.navajo.repository.file.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.repository.api.RepositoryInstance;

public class MultitenantSystemPropertyFileRepositoryInstanceImpl extends BaseFileRepositoryInstanceImpl implements RepositoryInstance {
	
	public MultitenantSystemPropertyFileRepositoryInstanceImpl() {
		
	}
	
	private final static Logger logger = LoggerFactory
			.getLogger(MultitenantSystemPropertyFileRepositoryInstanceImpl.class);

	public void activate(Map<String,Object> configuration) throws IOException {
		String path = System.getProperty("file.repository.path");
		type = System.getProperty("file.repository.type");
		repositoryName = System.getProperty("file.repository.name");
//		final String fileInstallPath= (String) configuration.get("felix.fileinstall.filename");
		if(path==null) {
			throw new IOException("No 'file.repository.path' set, so navajo.multitenant.repository.file.system is disabled");
		}
		if(!"multitenant".equals(type)) {
			throw new IOException("No 'file.repository.type' of type multitenant, so navajo.multitenant.repository.file.system is disabled");
		}
		applicationFolder = new File(path); //findConfiguration(path,fileInstallPath);
		setupMonitoredFolders();
		active = true;
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
		active = false;
	}
	



}
