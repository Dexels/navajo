package com.dexels.navajo.tipi.dev.server.appmanager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.core.projectbuilder.Dependency;
import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationStatus;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class ApplicationStatusImpl implements ApplicationStatus {

	private List<String> profiles;

	private String applicationName;
	private File appFolder;
	private final List<Dependency> dependencies = new ArrayList<Dependency>();
	private Map<String, Boolean> profileNeedsRebuild = new HashMap<String, Boolean>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(ApplicationStatusImpl.class);
	
	private PropertyResourceBundle settings;

	@Override
	public File getAppFolder() {
		return this.appFolder;
	}

	@Override
	public List<String> getProfiles() {
		return profiles;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.dev.server.appmanager.impl.ApplicationStatus#getApplicationName()
	 */
	@Override
	public String getApplicationName() {
		return applicationName;
	}

	public void activate(Map<String,Object> settings) throws IOException {
		String appFolder = (String) settings.get("path");
		File appDir = new File(appFolder);
		load(appDir);
	}

	void load(File appDir) throws IOException {
		this.appFolder = appDir;
		applicationName = appDir.getName();
		File tipiSettings = new File(appDir, "settings/tipi.properties");
		if (!tipiSettings.exists()) {
			return;
		}
		FileInputStream fis = new FileInputStream(tipiSettings);
		settings = new PropertyResourceBundle(fis);
		fis.close();
		processProfiles();
		
		String deps = getSettingString("dependencies");
		String[] d = deps.split(",");
		for (String dependency : d) {
			
			Dependency dd = new Dependency(dependency);
			dependencies.add(dd);
		}

//		build();
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

	public boolean profileNeedsRebuild(File profileProperties,
			String profileName, File appDir) {
		File jnlpFile = new File(appDir, profileName + ".jnlp");
		if (!jnlpFile.exists()) {
			return false;
		}
		if (profileProperties != null) {
			if (profileProperties.lastModified() > jnlpFile.lastModified()) {
				return true;
			}
		}
		File args = new File(appDir, "settings/arguments.properties");
		if (args.lastModified() > jnlpFile.lastModified()) {
			return true;
		}
		File tipiprops = new File(appDir, "settings/arguments.properties");
		if (tipiprops.lastModified() > jnlpFile.lastModified()) {
			return true;
		}

		return false;
	}

	public boolean isValid() {
		File libDir = new File(appFolder, "lib");
		if (!libDir.exists()) {
			return false;
		}
		if (libDir.list().length == 0) {
			return false;
		}
		return true;
	}

	private void processProfiles() {
		List<String> pro = new LinkedList<String>();
		File profilesDir = new File(appFolder, "settings/profiles");

		if (profilesDir.exists()) {
			for (File file : profilesDir.listFiles()) {
				if (file.canRead() && file.isFile()
						&& file.getName().endsWith(".properties")) {
					String profileName = file.getName().substring(0,
							file.getName().length() - ".properties".length());
					boolean b = profileNeedsRebuild(file, profileName, appFolder);
					pro.add(profileName);
					profileNeedsRebuild.put(profileName, b);
				}
			}
		}
		if (pro.isEmpty()) {
			String profileName = "Default";
			boolean b = profileNeedsRebuild(null, profileName, appFolder);
			pro.add(profileName);
			profileNeedsRebuild.put(profileName, b);
		}
		this.profiles = pro;
	}
}
