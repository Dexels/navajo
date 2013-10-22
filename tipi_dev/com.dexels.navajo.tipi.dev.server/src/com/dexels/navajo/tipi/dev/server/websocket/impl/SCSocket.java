package com.dexels.navajo.tipi.dev.server.websocket.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.jetty.websocket.WebSocket;
import org.osgi.framework.ServiceRegistration;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.dev.server.websocket.TipiCallbackSession;

public class SCSocket implements WebSocket.OnTextMessage, TipiCallbackSession {
	/**
	 * 
	 */
	private final CallbackServlet callbackServlet;
	private Connection connection;
	private boolean initialized = false;
	private ServiceRegistration<TipiCallbackSession> registration;
	private String application;
	private String tenant;
	private String session;
	
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
		this.application = elements[0];
		this.tenant = elements[1];
		this.session = elements[2];
		settings.put("application", application);
		settings.put("tenant",tenant);
		settings.put("session", session);
		registration = callbackServlet.getBundleContext().registerService(TipiCallbackSession.class,this,settings);
		
	}


	@Override
	public void sendMessage(String data) throws IOException {
		Navajo out = NavajoFactory.getInstance().createNavajo();
		Message message = NavajoFactory.getInstance().createMessage(out, "Message");
		out.addMessage(message);
		Property body = NavajoFactory.getInstance().createProperty(out, "Body", Property.STRING_PROPERTY, data, 10000, "", Property.DIR_IN);
		message.addProperty(body);
		StringWriter sw = new StringWriter();
		out.write(sw);
		connection.sendMessage(sw.toString());
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


	@Override
	public String getApplication() {
		return application;
	}


	@Override
	public String getProfile() {
		return tenant;
	}


	@Override
	public String getSession() {
		return session;
	}

}