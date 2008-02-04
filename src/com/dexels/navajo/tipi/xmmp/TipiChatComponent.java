package com.dexels.navajo.tipi.xmmp;

import java.io.*;
import java.util.*;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.filter.*;
import org.jivesoftware.smack.packet.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.*;

//import com.dexels.navajo.document.*;
import com.dexels.navajo.document.*;
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
	private String currentUser;

	public abstract void appendMessage(String msg);

	private final Map<String,String> userMap = new HashMap<String,String>();
	
	public static void main(String[] args) throws XMPPException, InterruptedException {

		// TipiChatComponent tcc = new TipiChatComponent();
	}

	public void initialize() throws XMPPException {
		if (connection != null) {
			if (connection.isConnected()) {
				connection.disconnect();
			}
		}
		System.err.println(":server " + server + " port: " + port + " >> " + chatDomain);
		System.err.println("Username: " + username + " pasS: " + password);
		config = new ConnectionConfiguration(server, port, chatDomain);
		config.setSASLAuthenticationEnabled(true);
		// XMPPConnection.DEBUG_ENABLED = true;
		connection = new XMPPConnection(config);
		System.err.println("Connection created");

		connection.connect();
		try {
			performTipiEvent("onConnect", null, false);
		} catch (TipiException e) {
			e.printStackTrace();
		}
		System.err.println("Connect ok");
		connection.login(username, password, chatDomain);
		System.err.println("Login ok?");
		connection.getRoster().addRosterListener(new RosterListener() {

			@Override
			public void entriesAdded(Collection<String> arg0) {
				postRoster(connection.getRoster());
			}

			@Override
			public void entriesDeleted(Collection<String> arg0) {
				postRoster(connection.getRoster());
			}

			@Override
			public void entriesUpdated(Collection<String> arg0) {
				postRoster(connection.getRoster());
			}

			@Override
			public void presenceChanged(Presence p) {
				System.err.println("Presence changed: "+p.getFrom());
				postRoster(connection.getRoster());
			}
		});

		postRoster(connection.getRoster());
		connection.addPacketListener(new PacketListener() {

			@Override
			public void processPacket(Packet p) {
				// TODO Auto-generated method stub
				String type = (String) p.getProperty("navajoType");
				System.err.println("My type: " + type);
				Message m = (Message) p;
				String tml = (String) m.getProperty("tml");

				for (Body b : m.getBodies()) {
					System.err.println(">>> " + b.getMessage());
				}
				if (type != null) {
					Navajo n = NavajoFactory.getInstance().createNavajo(new StringReader(tml));
					try {
						System.err.println("Loading navajo: " + type);
						n.write(System.err);
						myContext.loadNavajo(n, type);
					} catch (TipiException e) {
						e.printStackTrace();
					} catch (TipiBreakException e) {
						e.printStackTrace();
					} catch (NavajoException e) {
						e.printStackTrace();
					}
				}
				System.err.println("Received. Thread: " + m.getThread() + " subject: " + m.getSubject());
				messageReceived(m);
			}
		}, new PacketTypeFilter(Message.class));

		// chat = connection.getChatManager().createChat(recipient,new
		// MessageListener()
		// {
		//
		// public void processMessage(Chat chat, Message message) {
		// System.out.println("Received message: " + message.getBody());
		// messageReceived(message);
		// }
		// });
		// chat.sendMessage("Howdy!");
	}

	protected void postRoster(Roster roster) {
		try {
			Navajo n = NavajoFactory.getInstance().createNavajo();
			com.dexels.navajo.document.Message m = NavajoFactory.getInstance().createMessage(n, "Roster",
					com.dexels.navajo.document.Message.MSG_TYPE_ARRAY);
			n.addMessage(m);
			for (RosterEntry rr : roster.getEntries()) {
				Presence presence = roster.getPresence(rr.getUser());
				
				com.dexels.navajo.document.Message e = NavajoFactory.getInstance().createMessage(n, "Roster",
						com.dexels.navajo.document.Message.MSG_TYPE_ARRAY_ELEMENT);
				// NavajoFactory.getInstance().createProperty(n,)
				Property user;
				user = NavajoFactory.getInstance().createProperty(n, "UserName", Property.STRING_PROPERTY, rr.getUser(), 0, "",
						Property.DIR_OUT, null);
				e.addProperty(user);
				userMap.put(rr.getUser(), rr.getName());
				Property name = NavajoFactory.getInstance().createProperty(n, "Name", Property.STRING_PROPERTY, rr.getName(), 0,
						"", Property.DIR_OUT, null);
				e.addProperty(name);
				String statusString = "Offline";
				if(presence!=null) {
					statusString = presence.toString();
					}
				Property status = NavajoFactory.getInstance().createProperty(n, "Status", Property.STRING_PROPERTY,statusString, 0, "", Property.DIR_OUT, null);
				e.addProperty(status);
				m.addMessage(e);
			}
//			n.write(System.err);
			getContext().loadNavajo(n, "JabberRoster");
		} catch (NavajoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TipiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TipiBreakException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessage(String text, String recipient) throws XMPPException {
		if (connection == null) {
			initialize();
		}
		if(recipient==null) {
			recipient = currentUser;
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
		if (name.equals("currentUser")) {
			currentUser = (String) object;
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
			Presence p = r.getPresence(user);
			if(p!=null) {
				sendMessage(msg, user);
			}
		}

	}

	protected void messageReceived(Message message) {
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("body", message.getBody());
		String fromName = message.getFrom();
		StringTokenizer st = new StringTokenizer(fromName,"/");
		String usr = st.nextToken();
		m.put("from", fromName);
		System.err.println("Getting: "+usr);
		m.put("name", userMap.get(usr));
		System.err.println("Usermap: "+userMap);
		System.err.println("NAME: "+userMap.get(usr));
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
				String recipient = null;
				if(p!=null) {
					recipient = (String) p.value;
				} else {
					recipient = currentUser;
				}
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
