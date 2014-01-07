package com.dexels.navajo.repository.file.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.repository.api.AppStoreOperation;
import com.dexels.navajo.repository.api.RepositoryInstance;

public class FileRepositoryInstanceImpl implements RepositoryInstance {
	
	protected String repositoryName;
	protected File applicationFolder;
	private final Map<String,Object> settings = new HashMap<String, Object>();
	private final Map<String,AppStoreOperation> operations = new HashMap<String, AppStoreOperation>();
	private final Map<String,Map<String,Object>> operationSettings = new HashMap<String, Map<String,Object>>();
	protected String type;
	
	private final static Logger logger = LoggerFactory
			.getLogger(FileRepositoryInstanceImpl.class);
	
	
	@Override
	public File getRepositoryFolder() {
		return this.applicationFolder;
	}


	@Override
	public String getRepositoryName() {
		return repositoryName;
	}

	
	public void activate(Map<String,Object> configuration) throws IOException {

		String path = (String) configuration.get("repository.folder");
		repositoryName = (String) configuration.get("repository.name");
		final String fileInstallPath= (String) configuration.get("felix.fileinstall.filename");

		applicationFolder = findConfiguration(path,fileInstallPath);
		logger.info("Repository instance activated");

	}

	private File findConfiguration(String path, String fileInstallPath)
			throws IOException {
		
		if(path==null || "".equals(path)) {
			path = System.getProperty("storage.path");
		}
		File storeFolder = null;
		if(path==null) {
			logger.info("No storage.path found, now trying to retrieve from felix.fileinstall.filename");
			storeFolder = findByFileInstaller(fileInstallPath,".");
		} else {
			final File file = new File(path);
			if(file.isAbsolute()) {
				storeFolder = file;
			}
		}
		if(storeFolder==null || !storeFolder.isAbsolute()) {
			storeFolder = findByFileInstaller(fileInstallPath,path);
		}
		if(storeFolder==null || !storeFolder.exists()) {
			storeFolder = findByFileInstaller(fileInstallPath,path);
		}
		if(storeFolder==null || !storeFolder.exists()) {
			throw new IOException("No storage.path set in configuration!");
		}
		return storeFolder;
	}

	// f-in' beautiful
	private File findByFileInstaller(final String fileNamePath,String relative) {
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
						File storage = new File(root,relative);
						if(storage.exists()) {
							return storage;
						}
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
	public int compareTo(RepositoryInstance o) {
		return getRepositoryName().compareTo(o.getRepositoryName());
	}

	@Override
	public Map<String,Object> getSettings() {
		return new HashMap<String, Object>(settings);
	}
	
	protected void setSettings(Map<String,Object> settings) {
		this.settings.clear();
		this.settings.putAll(settings);
	}

	@Override
	public void addOperation(AppStoreOperation op, Map<String,Object> settings) {
		operations.put((String)settings.get("name"),op);
		operationSettings.put((String)settings.get("name"),settings);
	}

	@Override
	public void removeOperation(AppStoreOperation op,
			Map<String, Object> settings) {
		operations.remove(settings.get("name"));
		operationSettings.remove(settings.get("name"));
		
	}
	
	@Override
	public List<String> getOperations() {
		List<String> result = new ArrayList<String>();
		for (Map.Entry<String, AppStoreOperation> entry : operations.entrySet()) {
			String operationName = entry.getKey();
			Map<String,Object> settings = operationSettings.get(operationName);
			String operationType = (String) settings.get("type");
			// TODO match repository type
			if(this.type==null) {
				//
			}
			if("global".equals(operationType)) {
				continue;
			}
			if(operationType!=null && !this.type.equals(operationType)) {
				logger.warn("Operation type not matching: "+type+" vs. "+operationType);
				continue;
			}
			result.add(operationName);
		}
		return result;
		
	}

	@Override
	public int refreshApplication() throws IOException {
		return 0;
	}

}
