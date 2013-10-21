package com.dexels.navajo.tipi.dev.server.websocket.impl;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.jetty.websocket.WebSocket;
import org.osgi.framework.ServiceRegistration;

import com.dexels.navajo.tipi.dev.server.websocket.TipiCallbackSession;

public class SCSocket implements WebSocket.OnTextMessage, TipiCallbackSession {
	/**
	 * 
	 */
	private final CallbackServlet callbackServlet;
	private Connection connection;
	private boolean initialized = false;
	private ServiceRegistration<TipiCallbackSession> registration;
	
	/**
	 * @param callbackServlet
	 */
	public SCSocket(CallbackServlet callbackServlet) {
		this.callbackServlet = callbackServlet;
	}

	
	@Override
	public void onClose(int code, String message) {
		this.callbackServlet.removeSocket(this);
		if(registration!=null) {
			registration.unregister();
			registration = null;
		}
	}

	@Override
	public void onOpen(Connection con) {
		this.connection = con;
		this.callbackServlet.addSocket(this); 
		try{
			connection.sendMessage("You have been connected");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private void registerService(String initialMessage) {
		String[] elements = initialMessage.split(";");
		Dictionary<String,Object> settings = new Hashtable<String,Object>();
		settings.put("application", elements[0]);
		settings.put("tenant", elements[1]);
		settings.put("session", elements[2]);
		registration = callbackServlet.getBundleContext().registerService(TipiCallbackSession.class,this,settings);
		
	}


	@Override
	public void sendMessage(String data) throws IOException {
		connection.sendMessage(data);
	}
	
	@Override
	public void onMessage(String data) {
		System.out.println("Received: "+data);
		if(!initialized) {
			registerService(data);
			initialized = true;
		}
	}
	
	public boolean isOpen() {
		return connection.isOpen();
	}

}