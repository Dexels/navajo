package com.dexels.navajo.tipi.jabber;

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
import com.dexels.navajo.tipi.connectors.*;
import com.dexels.navajo.tipi.internal.*;

public class TipiJabberConnector extends TipiBaseConnector implements TipiConnector {

	protected String server = "iris.dexels.nl";
	protected int port = 5222;
	protected String chatDomain = "gmail.com";

	protected String username;
	protected String password;

	protected ConnectionConfiguration config;
	protected XMPPConnection connection;
	private String currentUser;

//	public abstract void appendMessage(String msg);

	private final Map<String,String> userMap = new HashMap<String,String>();
	
	
	
	public TipiJabberConnector() {
		
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

			public void entriesAdded(Collection<String> arg0) {
				rosterUpdated();
			}

			public void entriesDeleted(Collection<String> arg0) {
				rosterUpdated();
			}

			public void entriesUpdated(Collection<String> arg0) {
				rosterUpdated();
			}

			public void presenceChanged(Presence p) {
				System.err.println("Presence changed: "+p.getFrom());
				rosterUpdated();
			}
		});

		postRoster(connection.getRoster());
		connection.addPacketListener(new PacketListener() {

			public void processPacket(Packet p) {
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
			e1.printStackTrace();
		} catch (TipiBreakException e) {
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
	}

	@Override
	public Object createContainer() {
		return null;
	}

	@Override
	protected void setComponentValue(String name, Object object) {
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
		for (Iterator<RosterEntry> iterator = r.getEntries().iterator(); iterator.hasNext();) {
			RosterEntry rosterEntry = iterator.next();
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

	protected void rosterUpdated() {
		postRoster(connection.getRoster());
		try {
			performTipiEvent("onRosterChanged", null, false);
		} catch (TipiException e) {
			e.printStackTrace();
		}
	}

	public void doTransaction(Navajo n, String service) throws TipiBreakException, TipiException {
		if(service.equals("JabberRoster")) {
			postRoster(connection.getRoster());
		}
		throw new UnsupportedOperationException("umm, need destination for jabber!");
	}

	public void doTransaction(Navajo n, String service, String destination) throws TipiBreakException, TipiException {
		System.err.println("Doin it now!");
		try {
			sendMessage("Shake it!", destination);
		} catch (XMPPException e1) {
			e1.printStackTrace();
		}
		try {
			Message p = new Message(destination,Type.chat);
			p.setProperty("navajoType",service);
			if(n!=null) {
				StringWriter sw = new StringWriter();
				n.write(sw);
				sw.flush();
				sw.close();
				p.setBody(sw.toString());
				
			}
			connection.sendPacket(p);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NavajoException e) {
			e.printStackTrace();
		}
	}

	public String getConnectorId() {
		return "jabber";
	}

	public Set<String> getEntryPoints() {
		Set<String> s = new HashSet<String>();
		s.add("*");
		s.add("PostRoster");
		return s;
	}

}
