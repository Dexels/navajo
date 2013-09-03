package com.dexels.navajo.tipi.dev.server.appmanager.impl;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationManager;

public class ApplicationManagerImpl implements ApplicationManager {
	
	
	private File appsFolder;
	List<ApplicationStatus> applications;
	private String currentApplication;
	private String documentationRepository;
	
	private final static Logger logger = LoggerFactory
			.getLogger(ApplicationManagerImpl.class);
	

	public String getDocumentationRepository() {
		return documentationRepository;
	}

	public void setCurrentApplication(String currentApplication) {
		this.currentApplication = currentApplication;
	}

	public String getCurrentApplication() {
		return currentApplication;
	}

	public void activate(Map<String,Object> configuration) throws IOException {
		String path = (String) configuration.get("tipi.store.path");
		setAppsFolder(new File(path));
	}
	
	public ApplicationStatus getApplication() {
		for (ApplicationStatus a : applications) {
			if(a.getApplicationName().equals(currentApplication)) {
				return a;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.tipi.dev.server.appmanager.impl.ApplicationManager#getAppsFolder()
	 */
	@Override
	public File getAppsFolder() {
		return appsFolder;
	}

	public void setAppsFolder(File appsFolder) throws IOException {
		logger.info("Using application folder: "+appsFolder.getAbsolutePath());
		this.appsFolder = appsFolder;
		File[] apps = appsFolder.listFiles();
		List<ApplicationStatus> appStats = new LinkedList<ApplicationStatus>();
		this.applications = appStats;
		if(apps==null) {
			return;
		}
		for (File file : apps) {
			if(file.getName().equals("WEB-INF")) {
				continue;
			}
			if(file.getName().equals("META-INF")) {
				continue;
			}
			if(!file.isDirectory()) {
				continue;
			}
			if(!isTipiAppDir(file)) {
				continue;
			}
			logger.info("Application found: "+file.getName());
			ApplicationStatus appStatus = new ApplicationStatus();
			appStatus.setManager(this);
			appStatus.load(file);
			appStats.add(appStatus);
		}
	}

	private boolean isTipiAppDir(File tipiRoot) {
		File tipiDir = new File(tipiRoot,"tipi");
		File settingsProp = new File(tipiRoot,"settings/tipi.properties");
		return tipiDir.exists() && settingsProp.exists();
	}

	public  List<ApplicationStatus> getApplications()  {
//		logger.info("Getting applications: "+applications);
		return applications;
	}

}
