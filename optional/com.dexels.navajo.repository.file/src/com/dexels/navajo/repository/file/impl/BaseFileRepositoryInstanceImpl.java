package com.dexels.navajo.repository.file.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.repository.core.impl.RepositoryInstanceImpl;

public abstract class BaseFileRepositoryInstanceImpl extends RepositoryInstanceImpl implements RepositoryInstance {
	
	private EventAdmin eventAdmin = null;
	
	protected final Set<Path> monitoredPaths = new HashSet<Path>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(FileRepositoryInstanceImpl.class);
	protected WatchDir watchDir;
	
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


	
	void sendChangeEvent(String topic, Map<String, Object> properties) {
		if (eventAdmin == null) {
			logger.warn("No event administrator, not sending any events");
			return;
		}
		properties.put("repository", this);
		 properties.put("repositoryName", getRepositoryName());
		Event event = new Event(topic, properties);

		eventAdmin.postEvent(event);

	}	
	
	protected void setupMonitoredFolders() throws IOException {
		List<String> monitored = getMonitoredFolders();
		if(monitored==null) {
			logger.info("Can not setup up monitored folders: Layout isn't known (yet?): "+type); 
			return;
		}
		for (String element : monitored) {
			File c = new File(applicationFolder,element);
			if(c.exists()) {
				Path currentPath = Paths.get(applicationFolder.toURI());
				monitoredPaths.add(currentPath);
			}
		}
		logger.info("Repository instance activated");
		try {
			watchDir = new WatchDir(this);
		} catch (Throwable e) {
			logger.error("Error registering watchdir: ", e);
			throw(new IOException(e));
		}
	}

	protected File findConfiguration(String path, String fileInstallPath)
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
	public String applicationType() {
		return type;
	}

	@Override
	public void refreshApplication() throws IOException {
	}

}
