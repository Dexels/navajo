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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.core.projectbuilder.Dependency;
import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationStatus;

public class ApplicationStatusImpl implements ApplicationStatus {

	private List<String> profiles;
	
	private final static Logger logger = LoggerFactory
			.getLogger(ApplicationStatusImpl.class);
	
	private String applicationName;
	protected File applicationFolder;
	private final List<Dependency> dependencies = new ArrayList<Dependency>();
	
	private PropertyResourceBundle settings;

	@Override
	public File getAppFolder() {
		return this.applicationFolder;
	}

	@Override
	public List<String> getProfiles() {
		return profiles;
	}
	
	public Map<String,String> getProfileStatus() {
		Map<String,String> result = new HashMap<String, String>();
		for (String profile: profiles) {
			result.put(profile, profileStatus(profile));
		}
		return result;
		
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.dev.server.appmanager.impl.ApplicationStatus#getApplicationName()
	 */
	@Override
	public String getApplicationName() {
		return applicationName;
	}

	@Override
	public void load() throws IOException {
		load(applicationFolder);
	}

	
	
	public void activate(Map<String,Object> settings) throws IOException {
		String appFolder = (String) settings.get("path");
		File appDir = new File(appFolder);
		load(appDir);
	}
	
	private String profileStatus(String name) {
		File jnlp = new File(applicationFolder,name+".jnlp");
		if(!jnlp.exists()) {
			return STATUS_MISSING;
		}
		File applicationProperties = new File(applicationFolder, "settings/tipi.properties");
		File tipiSettings = new File(applicationFolder, "settings/tipi.properties");
		File profileFile = new File(applicationFolder,"settings/profiles/"+name+".properties");
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

	protected void load(File appDir) throws IOException {
		this.applicationFolder = appDir;
		applicationName = appDir.getName();
		File tipiSettings = new File(appDir, "settings/tipi.properties");
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
			logger.error("No 'dependencies' setting found in application: "+getApplicationName(), e);
		}

	}
	
	@Override
	public List<Dependency> getDependencies() {
		return dependencies;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.dev.server.appmanager.impl.ApplicationStatus#getSettingString(java.lang.String)
	 */
	@Override
	public String getSettingString(String key) {
		return settings.getString(key);
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.dev.server.appmanager.impl.ApplicationStatus#getSettingsBundle()
	 */
	@Override
	public ResourceBundle getSettingsBundle() {
		return settings;
	}

	private void processProfiles() {
		List<String> pro = new LinkedList<String>();
		File profilesDir = new File(getAppFolder(), "settings/profiles");

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

}
