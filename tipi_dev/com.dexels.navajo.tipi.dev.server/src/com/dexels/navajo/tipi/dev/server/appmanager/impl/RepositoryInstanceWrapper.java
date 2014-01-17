package com.dexels.navajo.tipi.dev.server.appmanager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.repository.api.AppStoreOperation;
import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.tipi.dev.core.projectbuilder.Dependency;

public class RepositoryInstanceWrapper implements RepositoryInstance {
	
	private final List<Dependency> dependencies = new ArrayList<Dependency>();
	private final RepositoryInstance instance;
	private List<String> profiles = new ArrayList<String>();
	private transient PropertyResourceBundle settings;
	
	private final static Logger logger = LoggerFactory
			.getLogger(RepositoryInstanceWrapper.class);
	
	
	public final static String STATUS_MISSING = "MISSING";
	public final static String STATUS_OK = "OK";
	public final static String STATUS_OUTDATED = "OUTDATED";

	
	public RepositoryInstanceWrapper(RepositoryInstance instance) {
		this.instance = instance;
		try {
			load();
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
	}

	
	public Map<String,String> getProfileStatus() {
		Map<String,String> result = new HashMap<String, String>();
		if(profiles == null) {
			return result;
		}
		for (String profile: profiles) {
			result.put(profile, profileStatus(profile));
		}
		return result;
	}

	public boolean isBuilt() {
		Map<String,String> result = getProfileStatus();
		if(result.isEmpty()) {
			return false;
		}
		if(result.values().contains(STATUS_OUTDATED)) {
			return false;
		}
		if(result.values().contains(STATUS_MISSING)) {
			return false;
		}
		return true;
		
	}
	
	private String profileStatus(String name) {
		File jnlp = new File(instance.getRepositoryFolder(),name+".jnlp");
		if(!jnlp.exists()) {
			return STATUS_MISSING;
		}
		File applicationProperties = new File(instance.getRepositoryFolder(), "settings/tipi.properties");
		File tipiSettings = new File(instance.getRepositoryFolder(), "settings/tipi.properties");
		File profileFile = new File(instance.getRepositoryFolder(),"settings/profiles/"+name+".properties");
		long jnlpModified = jnlp.lastModified();
		if(applicationProperties.lastModified()>=jnlpModified) {
			return STATUS_OUTDATED;
		}
		if(tipiSettings.lastModified()>=jnlpModified) {
			return STATUS_OUTDATED;
		}
		if(profileFile.lastModified()>=jnlpModified) {
			return STATUS_OUTDATED;
		}
		return STATUS_OK;
	}

	
	public List<String> getProfiles() {
		return profiles;
	}
	
	
	public void load() throws IOException {
		dependencies.clear();
		File tipiSettings = new File(instance.getRepositoryFolder(), "settings/tipi.properties");
		if (!tipiSettings.exists()) {
			return;
		}
		FileInputStream fis = new FileInputStream(tipiSettings);
		settings = new PropertyResourceBundle(fis);
		fis.close();
		processProfiles();
		
		String deps;
		try {
			deps = getSettingString("dependencies");
			String[] d = deps.split(",");
			for (String dependency : d) {
				
				Dependency dd = new Dependency(dependency);
				dependencies.add(dd);
			}
		} catch (MissingResourceException e) {
			logger.error("No 'dependencies' setting found in application: "+instance.getRepositoryName(), e);
		}

	}
	
	public String getSettingString(String key) {
		return settings.getString(key);
	}

	
	public List<Dependency> getDependencies() {
		return dependencies;
	}

	@JsonIgnore
	public ResourceBundle getSettingsBundle() {
		return settings;
	}

	private void processProfiles() {
		List<String> pro = new LinkedList<String>();
		File profilesDir = new File(instance.getRepositoryFolder(), "settings/profiles");

		if (profilesDir.exists()) {
			for (File file : profilesDir.listFiles()) {
				if (file.canRead() && file.isFile()
						&& file.getName().endsWith(".properties")) {
					String profileName = file.getName().substring(0,
							file.getName().length() - ".properties".length());
					pro.add(profileName);
				}
			}
		}
		this.profiles = pro;
	}


	@Override
	public int compareTo(RepositoryInstance o) {
		return instance.getRepositoryName().compareTo(o.getRepositoryName());
	}


	@Override
	public String getRepositoryName() {
		return instance.getRepositoryName();
	}




	@Override
	public File getRepositoryFolder() {
		return instance.getRepositoryFolder();
	}


	@Override
	public Map<String, Object> getSettings() {
		// TODO Auto-generated method stub
		return instance.getSettings();
	}


	@Override
	public void addOperation(AppStoreOperation op, Map<String, Object> settings) {
		instance.addOperation(op, settings);
	}


	@Override
	public void removeOperation(AppStoreOperation op,
			Map<String, Object> settings) {
		instance.removeOperation(op, settings);
		
	}


	@Override
	public List<String> getOperations() {
		return instance.getOperations();
	}


	@Override
	public int refreshApplication() throws IOException {
		return 0;
	}


	@Override
	public String repositoryType() {
		return instance.repositoryType();
	}


	@Override
	public String applicationType() {
		return instance.applicationType();
	}

	@Override
	public String toString() {
		return getRepositoryName()+": "+repositoryType()+"=>"+applicationType();
	}


	@Override
	public List<String> getMonitoredFolders() {
		return instance.getMonitoredFolders();
	}

	
}
