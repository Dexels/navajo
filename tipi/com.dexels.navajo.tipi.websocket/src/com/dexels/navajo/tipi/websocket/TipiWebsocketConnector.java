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

	private static final long serialVersionUID = 1969154253419166142L;
	
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
		   connection = client.open(uri, new WebSocket.OnTextMessage()
		   {
			private Connection connection = null;
			
		     @Override
			public void onOpen(Connection connection)
		     {
		    	 System.err.println("open!");
		    	 this.connection = connection;
		     }

		     @Override
			public void onClose(int closeCode, String message)
		     {
		    	 System.err.println("close!");
		     }

		     @Override
			public void onMessage(String data)
		     {
		    	 System.err.println("max text: "+ connection.getMaxTextMessageSize());
		    	 
		    	 System.err.println("Message: "+data);
		     }
		   }).get(5, TimeUnit.SECONDS);
	}



}
