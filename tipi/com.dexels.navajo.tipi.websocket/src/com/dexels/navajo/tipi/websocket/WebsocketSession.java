package com.dexels.navajo.tipi.websocket;

import java.io.StringReader;

import org.eclipse.jetty.websocket.WebSocket;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.tipi.TipiContext;

public class WebsocketSession implements WebSocket.OnTextMessage {

	private Connection connection = null;
	private final TipiContext context;
	private TipiWebsocketConnector tipiWebsocketConnector;
	
    public WebsocketSession(TipiContext context, TipiWebsocketConnector tipiWebsocketConnector) {
    	this.context = context;
    	this.tipiWebsocketConnector = tipiWebsocketConnector;
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
   	 try {
		tipiWebsocketConnector.restart();
	} catch (Exception e) {
		e.printStackTrace();
	}
    }

    @Override
	public void onMessage(String data)
    {
   	 System.err.println("max text: "+ connection.getMaxTextMessageSize());
   	 try {
		Navajo n = NavajoFactory.getInstance().createNavajo(new StringReader(data));
		context.injectNavajo("appstore/callback", n);
		n.write(System.err);
	} catch (Exception e) {
		e.printStackTrace();
	}
   	 
   	 System.err.println("Message: "+data);
    }
}
