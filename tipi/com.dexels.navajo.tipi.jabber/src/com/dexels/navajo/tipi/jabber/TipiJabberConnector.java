package com.dexels.navajo.tipi.jabber;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.Occupant;
import org.jivesoftware.smackx.muc.RoomInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.jabber.JabberUtils;
import com.dexels.navajo.client.sessiontoken.SessionTokenFactory;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.core.ShutdownListener;
import com.dexels.navajo.tipi.connectors.TipiBaseConnector;
import com.dexels.navajo.tipi.connectors.TipiConnector;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class TipiJabberConnector extends TipiBaseConnector implements TipiConnector {

	private static final long serialVersionUID = 1969154253419166142L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiJabberConnector.class);
	
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

		// logger.info("Servert: " + server +", port: " + port
		// +", conference: " + conferenceName + ", domain: " + chatDomain+
		// ", iser: " + username + ", pw: " + password);

		config = new ConnectionConfiguration(server, port); // , chatDomain);
		// ConnectionConfiguration config = new
		// ConnectionConfiguration("jabber.org", 5222);
		config.setCompressionEnabled(true);
		config.setSASLAuthenticationEnabled(true);

		connection = new XMPPConnection(config);

		connection.connect();
		// logger.info("SAS: " + connection.getSASLAuthentication());
		logger.info("Connection ok. Name: " + connection.getServiceName());
		try {
			performTipiEvent("onConnect", null, false);
		} catch (TipiException e) {
			logger.error("Error: ",e);
		}
		// logger.info("onConnect ok");
		
		connection.addPacketListener(new PacketListener() {
			public void processPacket(Packet p) {
				if ( p instanceof Message ) {
					Message m = (Message) p;
					messageReceived(m);
				}else{		
					listUsers();
//					logger.info("What is this smeg packet? " + p.getClass() + ", id: " + p.getPacketID());
//					Collection<PacketExtension> ext = p.getExtensions();
//					Iterator<PacketExtension> it = ext.iterator();
//					while(it.hasNext()){
//						PacketExtension et = it.next();
//						logger.info("Extention: " + et.getElementName());
//					}
				}
			}
		}, null);
		
		if (doLogin) {
			// logger.info("W00t! " +
//			 NavajoClientFactory.getClient().getSessionToken());
			
			// TODO resolve this static problem
			connection.login(username, password, SessionTokenFactory.getSessionTokenProvider().getSessionToken());
			// logger.info("W11t!");
			/*
			 * postRoster(connection.getRoster());
			 * connection.getRoster().addRosterListener(new RosterListener() {
			 * 
			 * public void entriesAdded(Collection<String> arg0) {
			 * rosterUpdated(); }
			 * 
			 * public void entriesDeleted(Collection<String> arg0) {
			 * rosterUpdated(); }
			 * 
			 * public void entriesUpdated(Collection<String> arg0) {
			 * rosterUpdated(); }
			 * 
			 * public void presenceChanged(Presence p) {
			 * logger.info("Presence changed: " + p.getFrom());
			 * rosterUpdated(); }
			 * 
			 * });
			 */

			logger.info("Login ok");
		} else {
			logger.info("Anonymous mode!");
			connection.loginAnonymously();
		}
	}

	public void listRooms() throws XMPPException {
		if (connection.isConnected()) {
			Collection<HostedRoom> rooms = MultiUserChat.getHostedRooms(connection, conferenceName + "." + chatDomain);
			Navajo n = NavajoFactory.getInstance().createNavajo();
			com.dexels.navajo.document.Message roomList = NavajoFactory.getInstance().createMessage(n, "Room", com.dexels.navajo.document.Message.MSG_TYPE_ARRAY);
			try {
				n.addMessage(roomList);

				for (HostedRoom hostedRoom : rooms) {
					// logger.info("JID: " + hostedRoom.getJid() + ", " +
					// hostedRoom.getName());

					com.dexels.navajo.document.Message room = NavajoFactory.getInstance().createMessage(n, "Room", com.dexels.navajo.document.Message.MSG_TYPE_ARRAY_ELEMENT);
					Property roomName = NavajoFactory.getInstance().createProperty(n, "Name", "string", hostedRoom.getName(), 128, "Naam", Property.DIR_OUT);
					Property roomId = NavajoFactory.getInstance().createProperty(n, "Id", "string", hostedRoom.getName() + "@" + conferenceName + "." + chatDomain, 256, "Id", Property.DIR_OUT);

					room.addProperty(roomName);
					room.addProperty(roomId);

					int occupants = 0;
					try {
						RoomInfo info = MultiUserChat.getRoomInfo(connection, hostedRoom.getJid());
						occupants = info.getOccupantsCount();
					} catch (Exception e) {
						// logger.info("Occupantcount unknown");
					}
					Property roomOccupantCount = NavajoFactory.getInstance().createProperty(n, "Occupants", "integer", "" + occupants, 6, "Occupants", Property.DIR_OUT);
					room.addProperty(roomOccupantCount);
					roomList.addElement(room);

				}
				// n.write(System.err);
				injectNavajo("RoomList", n);
			} catch (NavajoException e) {
				logger.error("Error: ",e);
			}
		}
	}

	public void listUsers() {
		try {
			Iterator<String> occupants = myMultiUserChat.getOccupants();

			Navajo n = NavajoFactory.getInstance().createNavajo();
			com.dexels.navajo.document.Message userList = NavajoFactory.getInstance().createMessage(n, "User", com.dexels.navajo.document.Message.MSG_TYPE_ARRAY);
			n.addMessage(userList);

			while (occupants.hasNext()) {

				String occ = occupants.next();
				// logger.info("Adding: " + occ
				// +" in TipiJabberConnector");
				Occupant oc = myMultiUserChat.getOccupant(occ);
//				Presence pres = myMultiUserChat.getOccupantPresence(occ);
				com.dexels.navajo.document.Message user = NavajoFactory.getInstance().createMessage(n, "User", com.dexels.navajo.document.Message.MSG_TYPE_ARRAY_ELEMENT);
				Property userName = NavajoFactory.getInstance().createProperty(n, "Name", "string", oc.getNick(), 128, "Naam", Property.DIR_OUT);
				Property userJid = NavajoFactory.getInstance().createProperty(n, "Jid", "string", oc.getJid(), 128, "Jid", Property.DIR_OUT);
				user.addProperty(userName);
				user.addProperty(userJid);
				userList.addElement(user);
			}
			injectNavajo("UserList", n);
		} catch (Exception e) {

		}
	}

	@Override
	protected Object getComponentValue(String name) {
		if (name.equals("nickName")) {
			if (myMultiUserChat != null) {
				return myMultiUserChat.getNickname();
			}
			return null;
		}
		return super.getComponentValue(name);

	}

	private void addConnectionListener() {
		if (!connection.isConnected()) {
			logger.info("Connection not connected. skipping registration of listeners");
			return;
		}
		// connection.addPacketListener(new PacketListener() {
		//
		// public void processPacket(Packet p) {

		// messageReceived(m);
		// }
		// }, new PacketTypeFilter(Message.class));

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
		tjc.server = "jabber.org";
		tjc.doLogin = true;
		// tjc.connection.getSASLAuthentication().authenticateAnonymously();

		tjc.conferenceName = "conference";
		tjc.chatDomain = "jabber.org";
		tjc.username = "arnoud";
		tjc.password = "test";
		tjc.initialize();

		tjc.listRooms();
		// tjc.postRoomMembers("serverroom");
		// tjc.postRooms();
		// tjc.createRoom("Den_aepenrots", "Albertus den Aep");
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			logger.error("Error: ",e);
		}

		// String jid = "dashboard@sportlink.com/matthijs";
		// String jidUser = jid.substring(0, jid.indexOf("@"));
		// logger.info("Jod: " + jidUser);
		// tjc.conferenceName = "conference";
		// tjc.joinRoom("aap", "The monkey");

	}

	protected void postRoster(Roster roster) {
		try {
			Navajo n = NavajoFactory.getInstance().createNavajo();
			com.dexels.navajo.document.Message m = NavajoFactory.getInstance().createMessage(n, "Roster", com.dexels.navajo.document.Message.MSG_TYPE_ARRAY);
			n.addMessage(m);

			for (RosterEntry rr : roster.getEntries()) {
				Presence presence = roster.getPresence(rr.getUser());

				com.dexels.navajo.document.Message e = NavajoFactory.getInstance().createMessage(n, "Roster", com.dexels.navajo.document.Message.MSG_TYPE_ARRAY_ELEMENT);
				// NavajoFactory.getInstance().createProperty(n,)
				Property user;
				user = NavajoFactory.getInstance().createProperty(n, "UserName", Property.STRING_PROPERTY, rr.getUser(), 0, "", Property.DIR_OUT, null);
				e.addProperty(user);
				userMap.put(rr.getUser(), rr.getName());
				Property name = NavajoFactory.getInstance().createProperty(n, "Name", Property.STRING_PROPERTY, rr.getName(), 0, "", Property.DIR_OUT, null);
				e.addProperty(name);
				String statusString = "Offline";
				if (presence != null) {
					statusString = presence.toString();
				}
				Property status = NavajoFactory.getInstance().createProperty(n, "Status", Property.STRING_PROPERTY, statusString, 0, "", Property.DIR_OUT, null);
				e.addProperty(status);
				m.addMessage(e);
			}
			// n.write(System.err);
			// to allow it to run straight from main:
			if (myContext != null) {
				getContext().loadNavajo(n, "JabberRoster");
			}
		} catch (NavajoException e1) {
			e1.printStackTrace();
		} catch (TipiBreakException e) {
			logger.error("Error: ",e);
		}
	}

	public void sendMessage(String text, String recipient) throws XMPPException {
		if (connection == null) {
			initialize();
		}
		if (recipient == null) {
			recipient = currentUser;
		}
		
		// logger.info("Name: " + recipient);
//		Chat c = connection.getChatManager().createChat(recipient, null);
		 Message m = myMultiUserChat.createMessage();
		 m.setBody(text);
		 m.setTo(recipient);
		 myMultiUserChat.sendMessage(m);	
		 
		 
		 
		// c.sendMessage(m);
//		c.sendMessage(text);
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
			doLogin = true;
		}
		if (name.equals("password")) {
			password = (String) object;
			doLogin = true;
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
//		 logger.info("Message received: "+ message.getBody());

		// String type = (String) message.getProperty("navajoType");
		// String tml = (String) message.getProperty("tml");
		// if (type != null) {
		// logger.info("Type found: "+type);
		if (!"serverResponse".equals(message.getSubject())) {
			try {
				Navajo n = NavajoFactory.getInstance().createNavajo(new StringReader(message.getBody()));

				// try {
				if (n != null) {
					com.dexels.navajo.document.Message jabber = n.getMessage("Jabber");
					if (jabber == null) {
						jabber = NavajoFactory.getInstance().createMessage(n, "Jabber");
					}

					try {
						Property occupant = NavajoFactory.getInstance().createProperty(n, "Occupant", "string", message.getFrom(), 256, "Occupant", Property.DIR_OUT);
						jabber.addProperty(occupant);
						n.addMessage(jabber);
					} catch (NavajoException e) {
						logger.error("Error: ",e);
					}

					if (n.getHeader().getRPCName().equals("ProcessExceptionToRoom")) {
						injectNavajo("JabberException", n);
					} else {						
						injectNavajo(n.getHeader().getRPCName(), n);
					}
				}
			} catch (Exception e) {
				// it was no Navajo
				logger.info("Ok, no Navajo: " + message.getBody());
			}
		} else {
			logger.info("Body: " + message.getBody() + " from: " + message.getFrom() + ", to:  " + message.getTo() + ", subjct: " + message.getSubject());

			Map<String, Object> m = new HashMap<String, Object>();
			m.put("body", message.getBody());
			String fromJid = message.getFrom();
			String from = message.getFrom().substring(0, fromJid.indexOf("@"));
			StringTokenizer st = new StringTokenizer(fromJid, "/");
			String usr = st.nextToken();
			m.put("fromJid", fromJid);
			m.put("from", from);
			// logger.info("Getting: " + usr);
			m.put("name", userMap.get(usr));
			// logger.info("Usermap: " + userMap);
			// logger.info("NAME: " + userMap.get(usr));
			m.put("to", message.getTo());
			m.put("subject", message.getSubject());
			try {
				performTipiEvent("onMessageReceived", m, false);
			} catch (TipiException e) {
				logger.error("Error: ",e);
			}
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
				logger.error("Error: ",e);
			}
		}
		if (name.equals("disconnect")) {
			disconnect();
		}
		if (name.equals("listRooms")) {
			try {
				listRooms();
			} catch (XMPPException e) {
				logger.error("Error: ",e);
			}
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
					logger.error("Error: ",e);
				}
			}
		}

		if (name.equals("joinRoom")) {
			com.dexels.navajo.document.Operand o = compMeth.getEvaluatedParameter("roomName", event);
			com.dexels.navajo.document.Operand op = compMeth.getEvaluatedParameter("nickName", event);
			if (o != null) {
				String result = (String) o.value;
				String nick = (String) op.value;
				joinRoom(result, nick);
			}
		}
		if (name.equals("talk")) {
			// logger.info("entering talk!");
			String text = (String) compMeth.getEvaluatedParameterValue("text", event);
			// logger.info("text:  " + text);
			if (myMultiUserChat != null) {
				try {
					// myMultiUserChat.sendMessage()
					Message m = myMultiUserChat.createMessage();
					m.setBody(text);
					m.setType(Type.groupchat);
					myMultiUserChat.sendMessage(m);
				} catch (XMPPException e) {
					// logger.info("Sent: " + text);
					logger.error("Error: ",e);
				}
			} else {
				logger.info("Chat not initialized");
			}
		}
		if (name.equals("startListener")) {
			addConnectionListener();
		}

	}

	private void joinRoom(String roomName, String nick) {
		try {

			String conferenceName = this.conferenceName + "." + connection.getServiceName();
			// logger.info("Connecting to conference: " + conferenceName
			// + ", " + conferenceName.toLowerCase() + ", " + nick);

			if (myMultiUserChat != null && myMultiUserChat.isJoined() && connection.isConnected()) {
				try {
					myMultiUserChat.leave();
				} catch (IllegalStateException ise) {
					// The most peculiar case is that we can still get an error
					// stating we are NOT connected. Dispite the checks above.
					logger.info("WARNING: Tried leaving, but could not part.");
				}
			}

			myMultiUserChat = JabberUtils.joinRoom(connection, conferenceName, roomName.toLowerCase(), nick, roomOccupants);

			try {
				myContext.execute(new Runnable() {
					public void run() {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							logger.error("Error: ",e);
						}
						listUsers();
					}
				});
			} catch (TipiException e) {
				logger.error("Error: ",e);
			}

		} catch (XMPPException e) {
			logger.error("Error: ",e);
		}

		myMultiUserChat.addParticipantListener(new PacketListener() {
			public void processPacket(Packet p) {
				logger.info("Participant event...");
				if (p instanceof Message) {
					logger.info("It is a message too :)");
					Message m = (Message) p;
					messageReceived(m);
				}
				listUsers();
			}
		});

//		myMultiUserChat.addMessageListener(new PacketListener() {
//
//			public void processPacket(Packet p) {
//				logger.info("aap <------------------------------------------------ " + p);
//				if (p instanceof Message) {
//					Message m = (Message) p;
//					messageReceived(m);
//				}
//			}
//		});
		
		

	}

	private void disconnect() {
		connection.disconnect();
		try {
			performTipiEvent("onDisconnect", null, false);
		} catch (TipiException e) {
			logger.error("Error: ",e);
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
			logger.error("Error: ",e);
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
			// try {
			// broadcastMessage("Broadcast navajo service: " + service);
			// } catch (XMPPException e1) {
			// e1.printStackTrace();
			// }

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
			logger.error("Error: ",e);
		} catch (NavajoException e) {
			logger.error("Error: ",e);
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
