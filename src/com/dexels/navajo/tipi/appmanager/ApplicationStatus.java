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

	public List<String> getExtensions() {
		return extensions;
	}

	public void setExtensions(List<String> extensions) {
		this.extensions = extensions;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationLink() {
		return getApplicationName()+"/Application.jnlp";
	}
	
	private List<String> extensions;
	private String applicationName;
	
	public void load(File appDir ) throws IOException {
		File tipiSettings = new File(appDir,"settings/tipi.properties");
		if(!tipiSettings.exists()) {
			setApplicationName(appDir.getName());
				String template = (String) getManager().getContext().getInitParameter("defaultTemplate");
			String repository =  (String) getManager().getContext().getInitParameter("repository");
			String developmentRepository =  (String) getManager().getContext().getInitParameter("developmentRepository");

			//String name = request.getParameter("name");
			ClientActions.downloadZippedDemoFiles(developmentRepository,repository, appDir,template);

			return;
		}
		FileInputStream fis = new FileInputStream(tipiSettings);
		PropertyResourceBundle settings = new PropertyResourceBundle(fis);
		fis.close();
		extensions = new LinkedList<String>();
		StringTokenizer st = new StringTokenizer(settings.getString("extensions"));
	    while (st.hasMoreElements()) {
			String element = (String) st.nextElement();
			extensions.add(element);
		}
	    setExists(true);
	    applicationName = appDir.getName();
	}
}
