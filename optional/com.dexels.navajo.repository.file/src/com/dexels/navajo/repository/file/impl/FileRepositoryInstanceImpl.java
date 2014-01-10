package com.dexels.navajo.repository.file.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.repository.api.AppStoreOperation;
import com.dexels.navajo.repository.api.RepositoryInstance;

public class FileRepositoryInstanceImpl implements RepositoryInstance {
	
	protected String repositoryName;
	protected File applicationFolder;
	
	private EventAdmin eventAdmin = null;

	private final Map<String,Object> settings = new HashMap<String, Object>();
	private final Map<String,AppStoreOperation> operations = new HashMap<String, AppStoreOperation>();
	private final Map<String,Map<String,Object>> operationSettings = new HashMap<String, Map<String,Object>>();
	protected String type;
	
	private final static Logger logger = LoggerFactory
			.getLogger(FileRepositoryInstanceImpl.class);
	private WatchDir watchDir;
	
	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}

	/**
	 * 
	 * @param eventAdmin
	 *            the eventadmin to clear
	 */
	public void clearEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = null;
	}


	@Override
	public int refreshApplication() throws IOException {
//		logger.debug(">>> last commit version: " + oldVersion);
//		try {
//			String newVersion = getLastCommitVersion();
//			
//			List<String> added = new ArrayList<String>();
//			List<String> modified = new ArrayList<String>();
//			List<String> copied = new ArrayList<String>();
//			List<String> deleted = new ArrayList<String>();
//
//			List<DiffEntry> diffEntries = diff(oldVersion);
//			if(newVersion.equals(oldVersion)) {
//				logger.info("Identical versions. Nothing pulled");
//				return 0;
//			}
//			if(diffEntries.isEmpty()) {
//				logger.info("Empty changeset (but there was a commit). Maybe empty commit?");
//				return 0;
//			}
//
//			for (DiffEntry diffEntry : diffEntries) {
//				
//				if (diffEntry.getChangeType().equals(ChangeType.ADD)) {
//					added.add(diffEntry.getNewPath());
//				} else if (diffEntry.getChangeType().equals(ChangeType.MODIFY)) {
//					modified.add(diffEntry.getNewPath());
//				} else if (diffEntry.getChangeType().equals(ChangeType.COPY)) {
//					copied.add(diffEntry.getOldPath());
//				} else if (diffEntry.getChangeType().equals(ChangeType.DELETE)) {
//					deleted.add(diffEntry.getOldPath());
//				} else if (diffEntry.getChangeType().equals(ChangeType.RENAME)) {
//					added.add(diffEntry.getNewPath());
//					deleted.add(diffEntry.getOldPath());
//				}
//				
//			}
//			Map<String, Object> properties = new HashMap<String, Object>();
//			properties.put(ChangeType.ADD.name(), added);
//			properties.put(ChangeType.MODIFY.name(), modified);
//			properties.put(ChangeType.COPY.name(), copied);
//			properties.put(ChangeType.DELETE.name(), deleted);
//			if (oldVersion != null) {
//				properties.put("oldCommit", oldVersion);
//			}
//			properties.put("newCommit", newVersion);
//			String url = getUrl();
//			if (url != null) {
//				properties.put("url", url);
//			}
//			sendChangeEvent("githubosgi/change", properties);
//			return 1;
//		} catch (GitAPIException e) {
//			logger.error("Error: ", e);
			return -1;
//		}

	}

	
	private void sendChangeEvent(String topic, Map<String, Object> properties) {
		if (eventAdmin == null) {
			logger.warn("No event administrator, not sending any events");
			return;
		}
		properties.put("repository", this);
		 properties.put("repositoryName", getRepositoryName());
		Event event = new Event(topic, properties);

		eventAdmin.postEvent(event);

	}	
	
	
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
		Path currentPath = Paths.get(applicationFolder.toURI());
		watchDir = new WatchDir(currentPath, true);
		Thread t = new Thread() {
			@Override
			public void run() {
				watchDir.processEvents();
			}
		};
		t.start();
	}

	public void deactivate() {
		if(watchDir!=null) {
			try {
				watchDir.close();
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
		}
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
	public String repositoryType() {
		return "file";
	}

	@Override
	public String applicationType() {
		return type;
	}


}
