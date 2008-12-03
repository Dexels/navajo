package com.dexels.navajo.tipi.jabber;

import java.io.*;
import java.util.*;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.filter.*;
import org.jivesoftware.smack.packet.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.*;
import org.jivesoftware.smackx.muc.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.connectors.*;
import com.dexels.navajo.tipi.internal.*;

public class TipiJabberConnector extends TipiBaseConnector implements TipiConnector {

	protected String server = "";
	protected int port = 5222;
	protected String chatDomain = "";

	protected String username = "";
	protected String password = "";

	protected ConnectionConfiguration config;
	protected XMPPConnection connection;
	private String currentUser;

	protected MultiUserChat myMultiUserChat = null;
	private boolean doLogin = false;

	// public abstract void appendMessage(String msg);

	private final Map<String, String> userMap = new HashMap<String, String>();

	// private String nickname = null;
	private String conferenceName;

	private final Set<String> roomOccupants = new HashSet<String>();
	
	public TipiJabberConnector() {

	}

	public String getDefaultEntryPoint() {
		return "PostRoster";
	}

	public void initialize() throws XMPPException {
		if (connection != null) {
			if (connection.isConnected()) {
				connection.disconnect();
			}
		}
		config = new ConnectionConfiguration(server, port, chatDomain);
		connection = new XMPPConnection(config);

		connection.connect();
		try {
			performTipiEvent("onConnect", null, false);
		} catch (TipiException e) {
			e.printStackTrace();
		}
		// System.err.println("Connect ok");

		if (doLogin) {
			connection.login(username, password, NavajoClientFactory.getClient().getSessionToken());
			postRoster(connection.getRoster());
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
					System.err.println("Presence changed: " + p.getFrom());
					rosterUpdated();
				}

			});
			System.err.println("Login ok");
		} else {
			System.err.println("Anonymous mode!");
			connection.loginAnonymously();
		}

	}

	
	
	@Override
	protected Object getComponentValue(String name) {
		if(name.equals("nickName")) {
			if(myMultiUserChat!=null) {
				return myMultiUserChat.getNickname();
			}
			return null;
		}
		return super.getComponentValue(name);
		
	}

	private void addConnectionListener() {
		if (!connection.isConnected()) {
			System.err.println("Connection not connected. skipping registration of listeners");
			return;
		}
		connection.addPacketListener(new PacketListener() {

			public void processPacket(Packet p) {
				String type = (String) p.getProperty("navajoType");
				Message m = (Message) p;
				String tml = (String) m.getProperty("tml");
//
//				for (Body b : m.getBodies()) {
//					System.err.println(">>> " + b.getMessage());
//				}
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
//				System.err.println("Received. Thread: " + m.getThread() + " subject: " + m.getSubject() + " from: " + m.getFrom());
				messageReceived(m);
			}
		}, new PacketTypeFilter(Message.class));

		myContext.addShutdownListener(new ShutdownListener() {

			public void contextShutdown() {
				if (connection != null) {
					connection.disconnect();
				}
			}
		});
	}

	public static void main(String[] args) throws XMPPException {
		TipiJabberConnector tjc = new TipiJabberConnector();
		tjc.initialize();
		tjc.conferenceName = "conference.sportlink.com";
		// tjc.postRoomMembers("serverroom");
		// tjc.postRooms();
		// tjc.createRoom("Den_aepenrots", "Albertus den Aep");
		// Thread.sleep(20000);

		String jid = "dashboard@sportlink.com/matthijs";
		String jidUser = jid.substring(0, jid.indexOf("@"));
		System.err.println("Jod: " + jidUser);

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
				Property name = NavajoFactory.getInstance().createProperty(n, "Name", Property.STRING_PROPERTY, rr.getName(), 0, "",
						Property.DIR_OUT, null);
				e.addProperty(name);
				String statusString = "Offline";
				if (presence != null) {
					statusString = presence.toString();
				}
				Property status = NavajoFactory.getInstance().createProperty(n, "Status", Property.STRING_PROPERTY, statusString, 0, "",
						Property.DIR_OUT, null);
				e.addProperty(status);
				m.addMessage(e);
			}
			n.write(System.err);
			// to allow it to run straight from main:
			if (myContext != null) {
				getContext().loadNavajo(n, "JabberRoster");
			}
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
		if (recipient == null) {
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
		if (name.equals("nickname")) {
			// nickname = (String) object;
		}
		if (name.equals("conferenceName")) {
			conferenceName = (String) object;
		}

	}



	protected void messageReceived(Message message) {
		System.err.println("Message received: "+ message.getBody());
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("body", message.getBody());
		String fromJid = message.getFrom();
		String from = message.getFrom().substring(0, fromJid.indexOf("@"));
		StringTokenizer st = new StringTokenizer(fromJid, "/");
		String usr = st.nextToken();
		m.put("fromJid", fromJid);
		m.put("from", from);
//		System.err.println("Getting: " + usr);
		m.put("name", userMap.get(usr));
//		System.err.println("Usermap: " + userMap);
//		System.err.println("NAME: " + userMap.get(usr));
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
				try {
					initialize();
				} catch (XMPPException e) {
					throw new TipiException("Error connecting jabber: " + e.getMessage(), e);
				}
			} catch (TipiException e) {
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
				if (p != null) {
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

		if (name.equals("joinRoom")) {
			com.dexels.navajo.document.Operand o = compMeth.getEvaluatedParameter("roomName", event);
			com.dexels.navajo.document.Operand op = compMeth.getEvaluatedParameter("nickName", event);
			if (o != null) {
				String result = (String) o.value;
				String nick = (String) op.value;
				try {
					myMultiUserChat = JabberUtils.joinRoom(connection, myContext, conferenceName, result.toLowerCase(), nick ,roomOccupants);
				} catch (XMPPException e) {
					e.printStackTrace();
				}
				myMultiUserChat.addMessageListener(new PacketListener(){

					public void processPacket(Packet p) {
						if(p instanceof Message) {
							Message m = (Message)p;
							messageReceived(m);
							
						}
					}});
			}
		}
		if (name.equals("talk")) {
			System.err.println("entering talk!");
			String text =  (String) compMeth.getEvaluatedParameterValue("text", event);
			System.err.println("text:  "+text);
				if(myMultiUserChat!=null) {
				try {
//					myMultiUserChat.sendMessage()
					Message m = myMultiUserChat.createMessage();
					m.setBody(text);
					m.setType(Type.groupchat);
					myMultiUserChat.sendMessage(m);
				} catch (XMPPException e) {
					System.err.println("Sent: "+text);
					e.printStackTrace();
				}
			} else {
				System.err.println("Chat not initialized");
			}
		}
		if (name.equals("startListener")) {
			addConnectionListener();
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
		if (service.equals("JabberRoster")) {
			postRoster(connection.getRoster());
		}
		throw new UnsupportedOperationException("umm, need destination for jabber!");
	}

	public void doTransaction(Navajo n, String service, String destination) throws TipiBreakException, TipiException {
		if (destination == null) {
//			try {
//				broadcastMessage("Broadcast navajo service: " + service);
//			} catch (XMPPException e1) {
//				e1.printStackTrace();
//			}

		} else {
			try {
				sendMessage("Shake it!", destination);
			} catch (XMPPException e1) {
				e1.printStackTrace();
			}

		}
		try {
			Message p = new Message(destination, Type.chat);
			p.setProperty("navajoType", service);
			if (n != null) {
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
