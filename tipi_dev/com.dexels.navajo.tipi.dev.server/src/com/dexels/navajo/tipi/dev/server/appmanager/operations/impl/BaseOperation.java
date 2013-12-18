package com.dexels.navajo.tipi.dev.server.appmanager.operations.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.repository.api.RepositoryManager;
import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreManager;
import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreOperation;
import com.dexels.navajo.tipi.dev.server.appmanager.impl.RepositoryInstanceWrapper;

public abstract class BaseOperation extends HttpServlet  implements AppStoreOperation {

	private static final long serialVersionUID = 7744618301328519140L;
	protected final Map<String,RepositoryInstance> applications = new HashMap<String, RepositoryInstance>();
	protected AppStoreManager appStoreManager = null;
	
	private RepositoryManager repositoryManager;

	public void setRepositoryManager(RepositoryManager repositoryManager) {
		this.repositoryManager = repositoryManager;
	}

	public void clearRepositoryManager(RepositoryManager repositoryManager) {
		this.repositoryManager = null;
	}
	
	public RepositoryManager getRepositoryManager() {
		return this.repositoryManager;
	}

	public void setAppStoreManager(AppStoreManager am) {
		this.appStoreManager = am;
	}
	
	protected Map<String,RepositoryInstanceWrapper> getApplications() {
		Map<String,RepositoryInstanceWrapper> result = new HashMap<String, RepositoryInstanceWrapper>();
		for (Map.Entry<String, RepositoryInstance> e : applications.entrySet()) {
			result.put(e.getKey(), new RepositoryInstanceWrapper(e.getValue()));
		}
		return result;
	}

	protected void verifyAuthorization(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if(!isAuthorized(req)) {
			resp.sendError(400,"Not authorized for operation");
		}
	}
	protected boolean isAuthorized(HttpServletRequest req) {
		HttpSession hs = req.getSession();
		Boolean b = (Boolean)hs.getAttribute("authorized");
		if(b==null) {
			return false;
		}
		return b;
	}


	public void addRepositoryInstance(RepositoryInstance a) {
		applications.put(a.getRepositoryName(), a);
	}
	
	public void removeRepositoryInstance(RepositoryInstance a) {
		applications.remove(a.getRepositoryName());
	}

	protected Set<String> listApplications() {
		 return appStoreManager.listApplications();
	}
	
	protected void writeValueToJsonArray(OutputStream os, Object value) throws IOException {  
		final ObjectMapper mapper = new ObjectMapper();
		
		mapper.writerWithDefaultPrettyPrinter().writeValue(os,value);
	}
}
