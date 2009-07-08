package com.dexels.navajo.tipi.appmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;

import com.dexels.navajo.tipi.projectbuilder.ClientActions;

public class ApplicationStatus {
	//private String name;
	private Date builtAt;
	private boolean exists = false;

	private ApplicationManager manager;
	private List<String> profiles;
	private List<ExtensionEntry> extensions;
	private String applicationName;
	private File appFolder;
	private String extensionRepository;
	
	public String getRepository() {
		return extensionRepository;
	}

//	public void setRepository(String extensionRepository) {
//		this.extensionRepository = extensionRepository;
//	}

	public List<String> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<String> profiles) {
		this.profiles = profiles;
	}

	public ApplicationManager getManager() {
		return manager;
	}

	public void setManager(ApplicationManager manager) {
		this.manager = manager;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}

	public Date getBuiltAt() {
		return builtAt;
	}

	public void setBuiltAt(Date builtAt) {
		this.builtAt = builtAt;
	}

	public List<ExtensionEntry> getExtensions() {
		return extensions;
	}

	public void setExtensions(List<ExtensionEntry> extensions) {
		this.extensions = extensions;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

//	public List<String> getApplicationLink() {
//		return getApplicationName()+"/Application.jnlp";
//	}
	

	
	public void load(File appDir ) throws IOException {
		this.appFolder = appDir;
		File tipiSettings = new File(appDir,"settings/tipi.properties");
		if(!tipiSettings.exists()) {
			setApplicationName(appDir.getName());
				String template = (String) getManager().getContext().getInitParameter("defaultTemplate");
			String repository =  (String) getManager().getContext().getInitParameter("extensionRepository");
			String developmentRepository =  (String) getManager().getContext().getInitParameter("developmentRepository");

			//String name = request.getParameter("name");
			ClientActions.downloadZippedDemoFiles(developmentRepository,repository, appDir,template);

			return;
		}
		FileInputStream fis = new FileInputStream(tipiSettings);
		PropertyResourceBundle settings = new PropertyResourceBundle(fis);
		fis.close();
		extensionRepository = settings.getString("repository");
		extensions = new LinkedList<ExtensionEntry>();
		StringTokenizer st = new StringTokenizer(settings.getString("extensions"),",");
	    while (st.hasMoreElements()) {
			String element = (String) st.nextElement();
			extensions.add(new ExtensionEntry(element));
				
			}
	    setExists(true);
	    applicationName = appDir.getName();
	    processProfiles(appDir);
	    
	}

	private void processProfiles(File appDir) {
		List<String> pro = new LinkedList<String>();
		File profilesDir = new File(appDir,"settings/profiles");
		if(!profilesDir.exists()) {
			pro.add("Default");
			setProfiles(pro);
			return;
		}
		for (File file : profilesDir.listFiles()) {
			//pro.add(file.getName());
			if(file.canRead() && file.isFile() && file.getName().endsWith(".properties")) {
				String profileName = file.getName().substring(0,file.getName().length()-".properties".length());
				System.err.println("Profilename: "+profileName);
				pro.add(profileName);
			}
		}
		if(pro.isEmpty()) {
			pro.add("Default");
		}
		setProfiles(pro);
	}

	public boolean isValid() {
		File tipiJar = new File(appFolder,"lib/Tipi.jar");
		return tipiJar.exists();
	}
}
