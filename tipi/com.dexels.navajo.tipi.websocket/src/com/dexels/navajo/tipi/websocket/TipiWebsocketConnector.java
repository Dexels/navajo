package com.dexels.navajo.tipi.websocket;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiContext;

public class TipiWebsocketConnector {

	private final static Logger logger = LoggerFactory
			.getLogger(TipiWebsocketConnector.class);

	private TipiContext context;

	private final WebSocketClientFactory factory;

	private WebSocket.Connection connection;
	


	public TipiWebsocketConnector(TipiContext context) throws Exception {
		this.context = context;
		factory = new WebSocketClientFactory();
		factory.start();
	}
	
	public void sendMessage(String message) throws IOException {
		connection.sendMessage(message);
	}
	
	public void startup(URI uri) throws Exception {
		   WebSocketClient client = factory.newWebSocketClient();
		   connection = client.open(uri, new WebsocketSession(context)).get(5, TimeUnit.SECONDS);
	}



}
