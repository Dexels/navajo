package com.dexels.navajo.client.push.jabber;

import java.util.HashSet;
import java.util.Set;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.client.jabber.JabberUtils;
import com.dexels.navajo.client.push.NavajoPushSession;
import com.dexels.navajo.document.Navajo;


public class NavajoJabberSession extends NavajoPushSession {
	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoJabberSession.class);
	private XMPPConnection connection = null;
//	private MultiUserChat muc = null;
	
	public void load(Navajo n, String agentId) throws ClientException{
		String server = (String) n.getProperty("SessionParameters/ChatServer").getTypedValue();
		int port = (Integer) n.getProperty("SessionParameters/ChatPort").getTypedValue();
		String chatService = (String) n.getProperty("SessionParameters/ChatService").getTypedValue();
		String nickname = (String) n.getProperty("SessionParameters/ChatNickname").getTypedValue();
		String room = (String) n.getProperty("SessionParameters/ChatRoom").getTypedValue();
// Agent/ApplicationId
		
		
		ConnectionConfiguration config = new ConnectionConfiguration(server, port);
		connection = new XMPPConnection(config);
		try {
			connection.connect();
			connection.loginAnonymously();
		} catch (XMPPException e) {
			logger.error("Error: ", e);
		}
		Set<String> occupants = new HashSet<String>();
		try {
			JabberUtils.joinRoom(connection, chatService, room, nickname, occupants);
		} catch (XMPPException e) {
			logger.error("Error: ", e);
		}
		NavajoClientFactory.getClient().doSimpleSend(n, "navajo/ProcessClientSession");
		
	}

}
