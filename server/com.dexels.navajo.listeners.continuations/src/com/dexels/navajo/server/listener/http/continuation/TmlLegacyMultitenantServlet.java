package com.dexels.navajo.server.listener.http.continuation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.script.api.LocalClient;


public class TmlLegacyMultitenantServlet extends HttpServlet {

	private static final long serialVersionUID = 2816820977641967253L;
			
	private final Map<String,LocalClient> clients = new HashMap<String, LocalClient>();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//LocalClient client = clients.get(determineInstanceFromRequest(req));

	}

	public void addLocalClient(LocalClient client, Map<String,Object> settings) {
		clients.put((String) settings.get("instance"), client);
	}

	public void removeLocalClient(LocalClient client, Map<String,Object> settings) {
		clients.remove(settings.get("instance"));
	}
	
	@SuppressWarnings("unused")
    private String determineInstanceFromRequest(final HttpServletRequest req) {
		String requestInstance = req.getHeader("X-Navajo-Instance");
		if(requestInstance!=null) {
			return requestInstance;
		}
		String pathinfo = req.getPathInfo();
		if(pathinfo.startsWith("/")) {
			pathinfo = pathinfo.substring(1);
		}
		String instance = null;
		if(pathinfo.indexOf('/')!=-1) {
			instance = pathinfo.substring(0, pathinfo.indexOf('/'));
		} else {
			instance = pathinfo;
		}
		return instance;
	}
}
