package com.dexels.navajo.tipi.dev.server.appmanager.operations.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.felix.service.command.CommandSession;

import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreOperation;
import com.dexels.navajo.tipi.dev.server.appmanager.impl.RepositoryInstanceWrapper;

public class List extends BaseOperation implements AppStoreOperation {

	
	private static final long serialVersionUID = 8640712571228602628L;
	
	public void list(CommandSession session ) throws IOException {
		Map<String,Map<String,?>> wrap = new HashMap<String, Map<String,?>>();
		wrap.put("applications", getApplications());
		wrap.put("settings", getSettings());
		writeValueToJsonArray(session.getConsole(),wrap);
	}




	private Map<String, Object> getSettings() {
		Map<String,Object> settings = new HashMap<String, Object>();
		settings.put("manifestcodebase", appStoreManager.getManifestCodebase());
		settings.put("codebase", appStoreManager.getCodeBase());
		settings.put("organization", appStoreManager.getOrganization());
		settings.put("sessions", appStoreManager.getSessionCount());
		return settings;
	}
	

	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
//		verifyAuthorization(req,resp);
		resp.setContentType("application/json");
		
		java.util.List<RepositoryInstanceWrapper> ll = new ArrayList<RepositoryInstanceWrapper>(getApplications().values());
		Collections.sort(ll);
		Map<String,Map<String,?>> wrap = new LinkedHashMap<String, Map<String,?>>();
		final Map<String,RepositoryInstanceWrapper> extwrap = new HashMap<String, RepositoryInstanceWrapper>();
		Map<String,String> user = new HashMap<String, String>();
		wrap.put("user", user);
		HttpSession session = req.getSession();
		user.put("login", (String)session.getAttribute("username"));
		user.put("image", (String)session.getAttribute("image"));
		

		for (RepositoryInstanceWrapper applicationStatus : ll) {
			extwrap.put(applicationStatus.getRepositoryName(), applicationStatus);
		}
		wrap.put("applications", extwrap);
		wrap.put("settings", getSettings());

		writeValueToJsonArray(resp.getOutputStream(),wrap);
	}

	@Override
	public void build(RepositoryInstance a) throws IOException {

	}
}
