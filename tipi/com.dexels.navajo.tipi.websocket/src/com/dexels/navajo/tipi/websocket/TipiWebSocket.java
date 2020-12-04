/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.websocket;
import java.io.IOException;
import java.net.URI;
import java.util.Random;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiContext;
 
/**
 * Basic Echo Client Socket
 */
@WebSocket(maxTextMessageSize = 64 * 1024)
public class TipiWebSocket {
 
	
	private final static Logger logger = LoggerFactory.getLogger(TipiWebSocket.class);
	private final Random rand = new Random(System.currentTimeMillis());

	private final TipiContext context;
	
	private final String sessionString;

	private final URI uri;

	private WebSocketClient client;
 
    public TipiWebSocket(URI uri, String sessionString, TipiContext context) throws Exception {
    	this.sessionString = sessionString;
    	this.context = context;
    	this.uri = uri;
		this.client = new WebSocketClient();
        client.start();
        connect();

    }
    
    private void connect() throws IOException {
        ClientUpgradeRequest request = new ClientUpgradeRequest();
        client.connect(this, uri, request);
    }
// 
//    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
//    }
// 
    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.err.printf("Connection closed: %d - %s%n", statusCode, reason);
        try {
			int waitTime = 1000+rand.nextInt(2000);
			System.err.println("waiting for: "+waitTime);
			Thread.sleep(waitTime);
		} catch (InterruptedException e1) {
		}
        try {
			connect();
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
    }
 
    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.err.println("Got connect: "+ session);
        try {
        	long idle = session.getIdleTimeout();
        	System.err.println(">>> "+idle);
            session.getRemote().sendString(sessionString);
//            session.set
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
 
    @OnWebSocketMessage
    public void onMessage(String data) {
    	logger.info("Message received: "+data);
    	Navajo out;
		try {
			out = NavajoFactory.getInstance().createNavajo();
			Message message = NavajoFactory.getInstance().createMessage(out, "Message");
			out.addMessage(message);
			Property body = NavajoFactory.getInstance().createProperty(out, "Body", Property.STRING_PROPERTY, data, 10000, "", Property.DIR_IN);
			message.addProperty(body);
			if(context!=null) {
				context.injectNavajo("appstore/callback", out);
			}
		} catch (Throwable e) {
			logger.error("Error: ", e);
		}

//    	System.err.println("max text: "+ connection.getMaxTextMessageSize());
//		try {
//			session.getRemote().sendString("ack");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}


    }

}