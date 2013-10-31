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

import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreOperation;
import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationStatus;

public class List extends BaseOperation implements AppStoreOperation {

	
	private static final long serialVersionUID = 8640712571228602628L;
	
	public void list(CommandSession session ) throws IOException {
		Map<String,Map<String,?>> wrap = new HashMap<String, Map<String,?>>();
		wrap.put("applications", applications);
		
		writeValueToJsonArray(session.getConsole(),wrap);
	}
	

	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		verifyAuthorization(req,resp);
		resp.setContentType("application/json");
		
		java.util.List<ApplicationStatus> ll = new ArrayList<ApplicationStatus>(applications.values());
		Collections.sort(ll);
		Map<String,Map<String,?>> wrap = new LinkedHashMap<String, Map<String,?>>();
		final Map<String,ApplicationStatus> extwrap = new HashMap<String, ApplicationStatus>();
		Map<String,String> user = new HashMap<String, String>();
		wrap.put("user", user);
		HttpSession session = req.getSession();
		user.put("login", (String)session.getAttribute("username"));
		user.put("image", (String)session.getAttribute("image"));
		

		for (ApplicationStatus applicationStatus : ll) {
			extwrap.put(applicationStatus.getApplicationName(), applicationStatus);
		}
		wrap.put("applications", extwrap);
		writeValueToJsonArray(resp.getOutputStream(),wrap);
	}

	@Override
	public void build(ApplicationStatus a) throws IOException {

	}
}
