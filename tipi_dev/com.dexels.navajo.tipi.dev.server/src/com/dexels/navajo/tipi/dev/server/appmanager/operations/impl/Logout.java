package com.dexels.navajo.tipi.dev.server.appmanager.operations.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.felix.service.command.CommandSession;

import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreOperation;

public class Logout extends BaseOperation implements AppStoreOperation {

	
	private static final long serialVersionUID = 8640712571228602628L;
	
	public void list(CommandSession session ) throws IOException {
		Map<String,Map<String,RepositoryInstance>> wrap = new HashMap<String, Map<String,RepositoryInstance>>();
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
	public void build(RepositoryInstance a) throws IOException {

	}
}
