package com.dexels.navajo.xmmp;

import java.util.*;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.filter.*;
import org.jivesoftware.smack.packet.*;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.internal.*;


public abstract class ServerChatComponent extends TipiComponentImpl {

	protected String server = "iris.dexels.nl";
	protected int port = 5222;
	protected String chatDomain = "gmail.com";
	protected String username;
	protected String password;
	protected ConnectionConfiguration config;
	protected XMPPConnection connection;

	public static void main(String[] args) throws XMPPException, InterruptedException {

	}

	public void initialize() throws XMPPException {
		if (connection != null) {
			if (connection.isConnected()) {
				connection.disconnect();
			}
		}
		config = new ConnectionConfiguration(server, port, chatDomain);
		config.setSASLAuthenticationEnabled(true);
		connection = new XMPPConnection(config);
		connection.connect();
		try {
			performTipiEvent("onConnect", null, false);
		} catch (TipiException e) {
			e.printStackTrace();
		}
		System.err.println("Connect ok");
		connection.login(username, password, chatDomain);
		System.err.println("Login ok?");
		RosterGroup rg = connection.getRoster().getGroup("dev");
		if (rg != null) {
			System.err.println("Dev found!");
		}

		connection.addPacketListener(new PacketListener() {
			public void processPacket(Packet p) {
				// TODO Auto-generated method stub
				Message m = (Message) p;
				System.err.println("Pingpong" + p.getFrom());
				messageReceived(m);
			}
		}, new PacketTypeFilter(Message.class));

	}

	public void sendMessage(String text, String recipient) throws XMPPException {
		if (connection == null) {
			initialize();
		}
		Chat c = connection.getChatManager().createChat(recipient, null);
		c.sendMessage(text);
	}

	protected void broadcastMessage(String msg) throws XMPPException {
		if (connection == null) {
			initialize();
		}
		Roster r = connection.getRoster();
		for (Iterator iterator = r.getEntries().iterator(); iterator.hasNext();) {
			RosterEntry rosterEntry = (RosterEntry) iterator.next();
			String user = rosterEntry.getUser();
			sendMessage(msg, user);
		}
	}

	protected void messageReceived(Message message) {
		Map m = new HashMap();
		m.put("body", message.getBody());
		m.put("from", message.getFrom());
		m.put("to", message.getTo());
		m.put("subject", message.getSubject());
		try {
			performTipiEvent("onMessageReceived", m, false);
		} catch (TipiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void disconnect() {
		connection.disconnect();
		try {
			performTipiEvent("onDisconnect", null, false);
		} catch (TipiException e) {
			e.printStackTrace();
		}
	}
}
