package com.dexels.navajo.tipi.websocket;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;

import com.dexels.navajo.tipi.TipiContext;

public class TipiWebsocketConnector {

	private TipiContext context;

	private final WebSocketClientFactory factory;

	private WebSocket.Connection connection;

	private URI uri;

	private String sessionString;
	


	public TipiWebsocketConnector(TipiContext context) throws Exception {
		this.context = context;
		factory = new WebSocketClientFactory();
		factory.start();
	}
	
	public void sendMessage(String message) throws IOException {
		connection.sendMessage(message);
	}
	
	public void startup(URI uri, String sessionString) throws Exception {
			this.uri = uri;
			this.sessionString = sessionString;
			restart();
	}

	public void restart() throws Exception {
		   WebSocketClient client = factory.newWebSocketClient();
		   connection = client.open(uri, new WebsocketSession(context,this)).get(5, TimeUnit.SECONDS);
		   connection.sendMessage(sessionString);

	}



}
