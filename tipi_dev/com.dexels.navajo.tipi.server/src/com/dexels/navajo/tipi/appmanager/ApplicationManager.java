package com.dexels.navajo.tipi.appmanager;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;

public class ApplicationManager {
	
	private File appsFolder;
	List<ApplicationStatus> applications;
	private String currentApplication;
	private String documentationRepository;
	

	public String getDocumentationRepository() {
		return documentationRepository;
	}

	public void setCurrentApplication(String currentApplication) {
		this.currentApplication = currentApplication;
	}

	public String getCurrentApplication() {
		return currentApplication;
	}

	public ApplicationStatus getApplication() {
		for (ApplicationStatus a : applications) {
			if(a.getApplicationName().equals(currentApplication)) {
				return a;
			}
		}
		return null;
	}
private ServletContext context = null;
	
	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) throws IOException {
		this.context = context;
		documentationRepository = context.getInitParameter("documentationRepository");
		String appFolder = context.getInitParameter("appFolder"); 
		File ff = null;
		if(appFolder==null) {
			File contextFolder = new File(context.getRealPath("."));
			ff = new File(contextFolder, "DefaultApps");
		} else {
			File suppliedFolder = new File(appFolder);
			if(suppliedFolder.isAbsolute()) {
				ff = suppliedFolder;
			} else {
				ff = new File(context.getRealPath(appFolder));
			}
		}
		setAppsFolder(ff);

	}
	
	public void setApplications(List<ApplicationStatus> applications) {
		this.applications = applications;
	}

	public File getAppsFolder() {
		return appsFolder;
	}

	public void setAppsFolder(File appsFolder) throws IOException {
		
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
//		return false;
	}

	public  List<ApplicationStatus> getApplications() throws IOException {
//		System.err.println("Getting applications: "+applications);
		return applications;
	}
	
	public static void main(String[] args) throws IOException {
		ApplicationManager m = new ApplicationManager();
		m.setAppsFolder(new File("WebContent"));
		List<ApplicationStatus> apps = m.getApplications();
		System.err.println("Appcount: "+apps.size());
	}
}
