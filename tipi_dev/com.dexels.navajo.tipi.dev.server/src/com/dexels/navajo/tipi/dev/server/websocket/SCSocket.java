package com.dexels.navajo.tipi.dev.server.websocket;

import java.io.IOException;

import org.eclipse.jetty.websocket.WebSocket;

public class SCSocket implements WebSocket.OnTextMessage {
	/**
	 * 
	 */
	private final CallbackServlet callbackServlet;

	/**
	 * @param callbackServlet
	 */
	public SCSocket(CallbackServlet callbackServlet) {
		this.callbackServlet = callbackServlet;
	}

	Connection connection;
	
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