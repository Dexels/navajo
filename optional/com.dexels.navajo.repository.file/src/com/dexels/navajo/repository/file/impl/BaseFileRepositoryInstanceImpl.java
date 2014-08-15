package com.dexels.navajo.repository.file.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.repository.api.AppStoreOperation;
import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.repository.api.diff.RepositoryLayout;

public class BaseFileRepositoryInstanceImpl implements RepositoryInstance {
	
	protected String repositoryName;
	protected File applicationFolder;
	
	private EventAdmin eventAdmin = null;
	private ConfigurationAdmin configAdmin;
	private final Map<String,Configuration> resourcePids = new HashMap<String, Configuration>();
	
	protected final Map<String,Object> settings = new HashMap<String, Object>();
	private final Map<String,AppStoreOperation> operations = new HashMap<String, AppStoreOperation>();
	private RepositoryLayout repositoryLayout = null;
	private final Map<String,Map<String,Object>> operationSettings = new HashMap<String, Map<String,Object>>();

	protected final Set<Path> monitoredPaths = new HashSet<Path>();
	protected String type;
	
	private final static Logger logger = LoggerFactory
			.getLogger(FileRepositoryInstanceImpl.class);
	protected WatchDir watchDir;
	protected String deployment;
	
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


	
	@Override
	public File getRepositoryFolder() {
		return this.applicationFolder;
	}


	@Override
	public String getRepositoryName() {
		return repositoryName;
	}

	


	@Override
	public List<String> getConfigurationFolders() {
		if(repositoryLayout==null) {
			logger.warn("Unknown repository layout: "+type+", change monitoring might not work!");
			return null;
		}
		return repositoryLayout.getConfigurationFolders();
	}
	
	@Override
	public List<String> getMonitoredFolders() {
		if(repositoryLayout==null) {
			logger.warn("Unknown repository layout: "+type+", change monitoring might not work!");
			return null;
		}
		return repositoryLayout.getMonitoredFolders();
	}
	
	public void setRepositoryLayout(RepositoryLayout r) {
		repositoryLayout = r;
	}
	
	public void clearRepositoryLayout(RepositoryLayout r) {
		repositoryLayout = null;
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

	@Override
	public int refreshApplication() throws IOException {
		return 0;
	}

	@Override
	public String getDeployment() {
		if(deployment!=null && !"".equals(deployment)) {
			return deployment;
		}
		String envDeployment = System.getProperty("DEPLOYMENT");
		return envDeployment;
	}

	protected void registerFileInstallLocations() throws IOException {
		List<String> locations = getConfigurationFolders();
		for (String location : locations) {
			File current = new File(getRepositoryFolder(),location);
			addFolderMonitorListener(current);
		}
		
	}

	private void addFolderMonitorListener(File monitoredFolder) throws IOException {
		if(!monitoredFolder.exists()) {
			logger.warn("FileInstaller should monitor folder: {} but it does not exist. Will not try again.", monitoredFolder.getAbsolutePath());
			return;
		}
		//fileInstallConfiguration = myConfigurationAdmin.createFactoryConfiguration("org.apache.felix.fileinstall",null);
//		monitoredFolder.getCanonicalFile().getAbsolutePath()
		final String absolutePath = monitoredFolder.getCanonicalFile().getAbsolutePath();
		Configuration newConfig = getUniqueResourceConfig( absolutePath);
		Dictionary<String,Object> d = newConfig.getProperties();
		if(d==null) {
			d = new Hashtable<String,Object>();
		}
		d.put("felix.fileinstall.dir",absolutePath );
		d.put("injectedBy","repository-instance" );
		String pid = newConfig.getPid();
		resourcePids.put(pid, newConfig);
		newConfig.update(d);	
	}
	
	private Configuration getUniqueResourceConfig(String path)
			throws IOException {
		final String factoryPid = "org.apache.felix.fileinstall";
		Configuration[] cc;
		String filter = "(&(service.factoryPid=" + factoryPid
				+ ")(felix.fileinstall.dir=" + path + "))";
		try {
			cc = configAdmin.listConfigurations(filter);
		} catch (InvalidSyntaxException e) {
			logger.error("Error discovering previous fileinstalls filter: "+filter, e);
			return null;
		}
		if (cc != null) {

			if (cc.length != 1) {
				logger.info("Odd length: " + cc.length);
			}
			return cc[0];
		} else {
			logger.info("Not found: " + path+" creating a new factory config for: "+factoryPid);
			Configuration c = configAdmin.createFactoryConfiguration(
					factoryPid, null);
			return c;
		}
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
}
