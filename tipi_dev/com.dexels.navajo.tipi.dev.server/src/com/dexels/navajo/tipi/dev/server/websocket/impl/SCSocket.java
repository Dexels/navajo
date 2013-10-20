package com.dexels.navajo.tipi.dev.server.websocket.impl;

import java.io.IOException;

import org.eclipse.jetty.websocket.WebSocket;

import com.dexels.navajo.tipi.dev.server.websocket.TipiCallbackSession;

public class SCSocket implements WebSocket.OnTextMessage, TipiCallbackSession {
	/**
	 * 
	 */
	private final CallbackServlet callbackServlet;
	private Connection connection;

	/**
	 * @param callbackServlet
	 */
	public SCSocket(CallbackServlet callbackServlet) {
		this.callbackServlet = callbackServlet;
	}

	
	@Override
	public void onClose(int code, String message) {
		this.callbackServlet.removeSocket(this);
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
	
	@Override
	public void sendMessage(String data) throws IOException {
		connection.sendMessage(data);
	}
	
	@Override
	public void onMessage(String data) {
		System.out.println("Received: "+data);
	}
	
	public boolean isOpen() {
		return connection.isOpen();
	}

}