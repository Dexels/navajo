package com.dexels.navajo.tipi.dev.server.websocket.impl;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;
import org.osgi.framework.BundleContext;

/**
 * Jetty WebSocketServlet implementation class ChatWebSocketServlet
 */

public class CallbackServlet extends WebSocketServlet implements Runnable {

	private static final long serialVersionUID = 8386266364760399706L;
	
	private final Set<SCSocket> members = new CopyOnWriteArraySet<SCSocket>();
	private final Thread heartbeatThread = new Thread(this);

	private BundleContext bundleContext;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	

	@Override
	public void init() throws ServletException {
		super.init();
		heartbeatThread.start();
	}

	public void activate(Map<String,Object> settings, BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	public void deactivate() {
		this.bundleContext = null;
	}
	public BundleContext getBundleContext() {
		return bundleContext;
	}



	/*
	 * @see org.eclipse.jetty.websocket.WebSocketServlet#doWebSocketConnect(javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	@Override
	public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
		return new SCSocket(this);
	}
	
	public void addSocket(SCSocket s) {
		members.add(s);
	}

	public void removeSocket(SCSocket s) {
		members.remove(s);
	}

	
	private void notifyMembers(String message) {

		for(SCSocket member: members){
			if(member.isOpen()){
				try{
					member.sendMessage(message);
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void run() {
		int count = 0;
		while(true) {
			notifyMembers("Heartbeat #"+count);
			count++;
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
 
}
