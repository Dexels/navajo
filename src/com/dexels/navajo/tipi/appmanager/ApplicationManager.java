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

	public void setContext(ServletContext context) {
		this.context = context;
		documentationRepository = context.getInitParameter("documentationRepository");
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
			ApplicationStatus appStatus = new ApplicationStatus();
			appStatus.setManager(this);
			appStatus.load(file);
			appStats.add(appStatus);
		}
		this.applications = appStats;
	}

	public  List<ApplicationStatus> getApplications() throws IOException {
		System.err.println("Getting applications: "+applications);
		return applications;
	}
	
	public static void main(String[] args) throws IOException {
		ApplicationManager m = new ApplicationManager();
		m.setAppsFolder(new File("WebContent"));
		List<ApplicationStatus> apps = m.getApplications();
		System.err.println("Appcount: "+apps.size());
	}
}
