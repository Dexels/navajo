package com.dexels.navajo.tipi.dev.server.appmanager.operations.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreOperation;
import com.dexels.navajo.tipi.dev.server.websocket.TipiCallbackSession;

public class SendMessage extends BaseOperation implements AppStoreOperation {

	
	private final static Logger logger = LoggerFactory
			.getLogger(SendMessage.class);
	
	private static final long serialVersionUID = 4412190224396292038L;
	private final Map<String,TipiCallbackSession> members = new HashMap<String,TipiCallbackSession>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		verifyAuthorization(req, resp);
		String session = req.getParameter("session");
		String message = req.getParameter("text");
		System.err.println("Session:"+session);
		System.err.println("Message:"+message);
		//------
		verifyAuthorization(req,resp);
		resp.setContentType("application/json");
		TipiCallbackSession s = members.get(session);
		if(s!=null) {
			s.sendMessage(message);
		} else {
			logger.info("no session found");
			resp.sendError(401,"No session found");
		}
		resp.getWriter().write("ok");
	}

	
	public void addSocket(TipiCallbackSession s,Map<String,Object> settings) {
		String session = (String) settings.get("session");
		members.put(session,s);
	}

	public void removeSocket(TipiCallbackSession s,Map<String,Object> settings) {
		String session = (String) settings.get("session");
		members.remove(session);
	}

	
	@Override
	public void build(RepositoryInstance a) throws IOException {

	}
}
