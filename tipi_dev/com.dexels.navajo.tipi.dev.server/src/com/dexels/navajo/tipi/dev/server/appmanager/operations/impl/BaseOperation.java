package com.dexels.navajo.tipi.dev.server.appmanager.operations.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServlet;

import org.codehaus.jackson.map.ObjectMapper;

import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreOperation;
import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationManager;
import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationStatus;

public abstract class BaseOperation extends HttpServlet  implements AppStoreOperation {

	private static final long serialVersionUID = 7744618301328519140L;
	protected final Map<String,ApplicationStatus> applications = new HashMap<String, ApplicationStatus>();
	protected ApplicationManager applicationManager = null;

	
	public void setApplicationManager(ApplicationManager am) {
		this.applicationManager = am;
	}

	public void clearApplicationManager(ApplicationManager am) {
		this.applicationManager = null;
	}

	public void addApplicationStatus(ApplicationStatus a) {
		applications.put(a.getApplicationName(), a);
	}
	
	public void removeApplicationStatus(ApplicationStatus a) {
		applications.remove(a.getApplicationName());
	}

	protected Set<String> listApplications() {
		 return applicationManager.listApplications();
	}
	
	protected void writeValueToJsonArray(OutputStream os, Object value) throws IOException {  
		final ObjectMapper mapper = new ObjectMapper();
		
		mapper.writerWithDefaultPrettyPrinter().writeValue(os,value);
	}
}
