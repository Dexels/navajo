package com.dexels.navajo.tipi.websocket;

import org.eclipse.jetty.websocket.WebSocket;

import com.dexels.navajo.tipi.TipiContext;

public class WebsocketSession implements WebSocket.OnTextMessage {

	private Connection connection = null;
	private final TipiContext context;
    public WebsocketSession(TipiContext context) {
    	this.context = context;
    	
    }

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
}
