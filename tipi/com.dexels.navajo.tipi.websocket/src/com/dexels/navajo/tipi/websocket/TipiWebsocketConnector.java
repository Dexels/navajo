package com.dexels.navajo.tipi.websocket;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.connectors.TipiBaseConnector;
import com.dexels.navajo.tipi.connectors.TipiConnector;

public class TipiWebsocketConnector extends TipiBaseConnector implements TipiConnector {

	private static final long serialVersionUID = 1969154253419166142L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiWebsocketConnector.class);
	


	public TipiWebsocketConnector() {

	}

	@Override
	public Set<String> getEntryPoints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefaultEntryPoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doTransaction(Navajo n, String service, String destination)
			throws TipiBreakException, TipiException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getConnectorId() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException, IOException, URISyntaxException, Exception {
		   WebSocketClientFactory factory = new WebSocketClientFactory();
		   factory.start();

		   WebSocketClient client = factory.newWebSocketClient();
		   // Configure the client

		   WebSocket.Connection connection = client.open(new URI("ws://localhost:8080/websocket"), new WebSocket.OnTextMessage()
		   {
		     @Override
			public void onOpen(Connection connection)
		     {
		       // open notification
		    	 System.err.println("open!");
		     }

		     @Override
			public void onClose(int closeCode, String message)
		     {
		    	 System.err.println("close!");
		     }

		     @Override
			public void onMessage(String data)
		     {
		    	 System.err.println("Message: "+data);
		     }
		   }).get(5, TimeUnit.SECONDS);

		   connection.sendMessage("Hello World");
		   Thread.sleep(100000);
		 
	}
}
