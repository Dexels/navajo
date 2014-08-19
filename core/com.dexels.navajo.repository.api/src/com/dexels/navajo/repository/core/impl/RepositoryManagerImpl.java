package com.dexels.navajo.repository.core.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
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
		final String fileInstallPath= (String) configuration.get("felix.fileinstall.filename");

		File storeFolder = findConfiguration(path,fileInstallPath);

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
		logger.info("Repository manager activated. Using repositoryfolder: "+repositoryFolder.getAbsolutePath());

	}

	private File findConfiguration(String path, String fileInstallPath)
			throws IOException {
		
		if(path==null || "".equals(path)) {
			path = System.getProperty("storage.path");
		}
		File storeFolder = null;
		if(path==null) {
			logger.info("No storage.path found, now trying to retrieve from felix.fileinstall.filename");
			storeFolder = findByFileInstaller(fileInstallPath,"storage");
		} else {
			
			File suppliedPath = new File(path);
			if(suppliedPath.isAbsolute()) {
				storeFolder = suppliedPath;
			} else {
				storeFolder =findByFileInstaller(fileInstallPath,path);
			}
		}
		if(storeFolder==null ) {
			storeFolder = findByFileInstaller(fileInstallPath,"storage");
		}
		if(storeFolder==null || !storeFolder.exists()) {
			throw new IOException("No storage.path set in configuration!");
		}
		return storeFolder;
	}

	// f-in' beautiful
	private File findByFileInstaller(final String fileNamePath, String storagePath) {
		try {
			URL url = new URL(fileNamePath);
			File f;
			try {
			  f = new File(url.toURI());
			} catch(URISyntaxException e) {
			  f = new File(url.getPath());
			}
			if(f!=null) {
				File etc = f.getParentFile();
				if(etc!=null) {
					File root = etc.getParentFile();
					if(root!=null) {
						File storage = new File(root,storagePath);
						if(!storage.exists()) {
							storage.mkdirs();
						}
						return storage;
					}
				}
			}
		} catch (MalformedURLException e) {
			logger.warn("Fileinstall.filename based resolution also failed.",e);
		}
		return null;
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
