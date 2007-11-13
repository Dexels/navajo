package com.dexels.navajo.tipi.xmmp;

import java.util.*;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.filter.*;
import org.jivesoftware.smack.packet.*;

//import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.internal.*;

public abstract class TipiChatComponent extends TipiComponentImpl {

	protected String server = "iris.dexels.nl";
	protected int port = 5222;
	protected String chatDomain = "gmail.com";

	protected String username;
	protected String password;

	protected ConnectionConfiguration config;
	protected XMPPConnection connection;

	public abstract void appendMessage(String msg);

	public static void main(String[] args) throws XMPPException, InterruptedException {

		// TipiChatComponent tcc = new TipiChatComponent();
	}

	public void initialize() throws XMPPException {
		if(connection!=null) {
			if(connection.isConnected()) {
				connection.disconnect();
			}
		}
		System.err.println(":server "+server+" port: "+port+" >> "+chatDomain);
		System.err.println("Username: "+username+" pasS: "+password);
		config = new ConnectionConfiguration(server, port, chatDomain);
		config.setSASLAuthenticationEnabled(true);
// XMPPConnection.DEBUG_ENABLED = true;
		connection = new XMPPConnection(config);
		System.err.println("Connection created");

		// XMPPConnection connection = new
		// XMPPConnection("talk.google.com:5222");
		// / connection.
		connection.connect();
		try {
			performTipiEvent("onConnect", null, false);
		} catch (TipiException e) {
			e.printStackTrace();
		}
		System.err.println("Connect ok");
		connection.login(username, password,chatDomain);
		System.err.println("Login ok?");
		Collection<RosterGroup> rg = connection.getRoster().getGroups();
		for (Iterator<RosterGroup> iterator = rg.iterator(); iterator.hasNext();) {
			RosterGroup gg = iterator.next();
			System.err.println("Group: "+gg);
			
		}
		if(rg!=null) {
			System.err.println("Dev found!");
		}

		getContext().addNavajoListener(new TipiNavajoListener(){

			@Override
			public void navajoReceived(com.dexels.navajo.document.Navajo doc, final String service) {
				Thread t = new Thread(){

					@Override
					public void run() {
						try {
							broadcastMessage("Received navajo:"+service);
							System.err.println("Received navajo: "+service);
						} catch (XMPPException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}};
				t.start();
			}

			@Override
			public void navajoSent(com.dexels.navajo.document.Navajo doc, String service) {
				// TODO Auto-generated method stub
				
				}});
		
		connection.addPacketListener(new PacketListener(){

			@Override
			public void processPacket(Packet p) {
				// TODO Auto-generated method stub
				Message m = (Message)p;
				System.err.println("Received. Thread: "+m.getThread()+" subject: "+m.getSubject());
				messageReceived(m);
			}}, new PacketTypeFilter(Message.class));
		
// chat = connection.getChatManager().createChat(recipient,new MessageListener()
// {
//
// public void processMessage(Chat chat, Message message) {
// System.out.println("Received message: " + message.getBody());
// messageReceived(message);
// }
// });
		// chat.sendMessage("Howdy!");
	}

	public void sendMessage(String text, String recipient) throws XMPPException {
		if (connection == null) {
			initialize();
		}
		System.err.println("Name: " + recipient);
		Chat c = connection.getChatManager().createChat(recipient, null);
		c.sendMessage(text);
		// Message m = new Message(recipient);
		// m.setType(Message.Type.chat);
		// connection.sendPacket(m);

	}

	@Override
	public Object createContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		// TODO Auto-generated method stub
		super.setComponentValue(name, object);
		if (name.equals("username")) {
			username = (String) object;
		}
		if (name.equals("password")) {
			password = (String) object;
		}
		if (name.equals("domain")) {
			chatDomain = (String) object;
		}
		if (name.equals("port")) {
			port = Integer.parseInt((String) object);
		}
		if (name.equals("server")) {
			server = (String) object;
		}

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

	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);
		if (name.equals("connect")) {
			try {
				initialize();
			} catch (XMPPException e) {
				e.printStackTrace();
			}
		}
		if (name.equals("disconnect")) {
			disconnect();
		}
		if (name.equals("send")) {
			com.dexels.navajo.document.Operand o = compMeth.getEvaluatedParameter("text", event);
			com.dexels.navajo.document.Operand p = compMeth.getEvaluatedParameter("recipient", event);
			if (o != null) {
				String result = (String) o.value;
				String recipient = (String) p.value;
				try {
					sendMessage(result, recipient);
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (name.equals("broadcast")) {
			com.dexels.navajo.document.Operand o = compMeth.getEvaluatedParameter("text", event);
			if (o != null) {
				String result = (String) o.value;
				try {
					broadcastMessage(result);
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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

	@Override
	public void disposeComponent() {
		disconnect();
		super.disposeComponent();
	}

}
