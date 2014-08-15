package com.dexels.githubosgi.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.repository.api.AppStoreOperation;
import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.repository.api.RepositoryManager;
import com.dexels.navajo.repository.api.diff.RepositoryLayout;

public abstract class RepositoryInstanceImpl implements RepositoryInstance {
	
	protected String repositoryName;
	protected File applicationFolder;
	protected RepositoryManager repositoryManager;
	private final Map<String,Object> settings = new HashMap<String, Object>();
	private final Map<String,AppStoreOperation> operations = new HashMap<String, AppStoreOperation>();
	private final Map<String,Map<String,Object>> operationSettings = new HashMap<String, Map<String,Object>>();
	private ConfigurationAdmin configAdmin;
	private final Map<String,Configuration> resourcePids = new HashMap<String, Configuration>();

	protected String type;

	private final Map<String,RepositoryLayout> repositoryLayout = new HashMap<String, RepositoryLayout>();


	
	private final static Logger logger = LoggerFactory
			.getLogger(RepositoryInstanceImpl.class);
	
	@Override
	public File getRepositoryFolder() {
		return this.applicationFolder;
	}

	public void setRepositoryManager(RepositoryManager repositoryManager) {
		this.repositoryManager = repositoryManager;
	}

	public void clearRepositoryManager(RepositoryManager repositoryManager) {
		this.repositoryManager = null;
	}


	@Override
	public String getRepositoryName() {
		return repositoryName;
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

			if("global".equals(operationType)) {
				continue;
			}
			if("any".equals(this.type)) {
				result.add(operationName);
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
	
	@Override
	public String toString() {
		return getRepositoryName()+": "+repositoryType()+"=>"+applicationType();
	}



	public void addRepositoryLayout(RepositoryLayout r, Map<String,Object> settings) {
		repositoryLayout.put((String) settings.get("name"),r);
	}
	
	public void removeRepositoryLayout(RepositoryLayout r, Map<String,Object> settings) {
		repositoryLayout.remove(settings.get("name"));
	}

	@Override
	public List<String> getMonitoredFolders() {
		RepositoryLayout r = repositoryLayout.get(type);
		if(r==null) {
			logger.warn("Unknown repository layout: "+type+", change monitoring might not work!");
			return null;
		}
		return r.getMonitoredFolders();
	}
	

	@Override
	public List<String> getConfigurationFolders() {
		RepositoryLayout r = repositoryLayout.get(type);
		if(r==null) {
			logger.warn("Unknown repository layout: "+type+", change monitoring might not work!");
			return null;
		}
		return r.getConfigurationFolders();
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
	protected void deregisterFileInstallLocations() {
		for (Map.Entry<String, Configuration> element : resourcePids.entrySet()) {
			try {
				element.getValue().delete();
			} catch (IOException e) {
				logger.warn("Problem removing fileinstalled location: ", element.getKey(),e);
			}
		}
		// TODO Auto-generated method stub
		
	}
}
