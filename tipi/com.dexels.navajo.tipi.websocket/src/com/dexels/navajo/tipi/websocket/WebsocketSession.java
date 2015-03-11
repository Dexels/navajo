package com.dexels.navajo.tipi.websocket;

import org.eclipse.jetty.websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiContext;
//import com.dexels.navajo.tipi.dev.server.websocket.impl.Message;
//import com.dexels.navajo.tipi.dev.server.websocket.impl.Property;

public class WebsocketSession implements WebSocket.OnTextMessage {

	private Connection connection = null;
	private final TipiContext context;
	private TipiWebsocketConnector tipiWebsocketConnector;
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(WebsocketSession.class);
	
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
		logger.error("Error: ", e);
	}
    }

    @Override
	public void onMessage(String data)
    {
    	logger.info("Message received: "+data);
    	Navajo out = NavajoFactory.getInstance().createNavajo();
		Message message = NavajoFactory.getInstance().createMessage(out, "Message");
		out.addMessage(message);
		Property body = NavajoFactory.getInstance().createProperty(out, "Body", Property.STRING_PROPERTY, data, 10000, "", Property.DIR_IN);
		message.addProperty(body);

    	System.err.println("max text: "+ connection.getMaxTextMessageSize());
		context.injectNavajo("appstore/callback", out);
    }
}
