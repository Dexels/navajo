package com.dexels.navajo.tipi.dev.server.appmanager.operations.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.felix.service.command.CommandSession;

import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreOperation;
import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationStatus;

public class Logout extends BaseOperation implements AppStoreOperation {

	
	private static final long serialVersionUID = 8640712571228602628L;
	
	public void list(CommandSession session ) throws IOException {
		Map<String,Map<String,ApplicationStatus>> wrap = new HashMap<String, Map<String,ApplicationStatus>>();
		wrap.put("applications", applications);
		writeValueToJsonArray(session.getConsole(),wrap);
	}
	

	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.invalidate();

		resp.sendRedirect("ui/index.html");
	}
	

	@Override
	public void build(ApplicationStatus a) throws IOException {

	}
}
