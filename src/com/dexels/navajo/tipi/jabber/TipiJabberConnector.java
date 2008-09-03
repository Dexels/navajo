package com.dexels.navajo.tipi.jabber;

import java.io.*;
import java.util.*;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.filter.*;
import org.jivesoftware.smack.packet.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.*;
import org.jivesoftware.smackx.*;
import org.jivesoftware.smackx.FormField.*;
import org.jivesoftware.smackx.muc.*;

//import com.dexels.navajo.document.*;
import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.connectors.*;
import com.dexels.navajo.tipi.internal.*;

public class TipiJabberConnector extends TipiBaseConnector implements TipiConnector {

	protected String server = "hermes1.dexels.com";
	protected int port = 5222;
	protected String chatDomain = "sportlink.com";

	protected String username = "admin";
	protected String password = "xxxxxx";

	protected ConnectionConfiguration config;
	protected XMPPConnection connection;
	private String currentUser;

	// public abstract void appendMessage(String msg);

	private final Map<String, String> userMap = new HashMap<String, String>();
	
	private String nickname = null;
	private String conferenceName;

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
		System.err.println(":server " + server + " port: " + port + " >> " + chatDomain);
		System.err.println("Username: " + username + " pasS: " + password);
		config = new ConnectionConfiguration(server, port, chatDomain);
		// config.setSASLAuthenticationEnabled(true);
		// XMPPConnection.DEBUG_ENABLED = true;
		System.err.println("Configuration created");
		connection = new XMPPConnection(config);
		System.err.println("Connection created");

		connection.connect();
		System.err.println("Connection connected");
		try {
			performTipiEvent("onConnect", null, false);
		} catch (TipiException e) {
			e.printStackTrace();
		}
//		System.err.println("Connect ok");
		
		connection.login(username, password, NavajoClientFactory.getClient().getSessionToken());

		System.err.println("Login ok");
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

		postRoster(connection.getRoster());

	}

	private void addConnectionListener() {
		if(!connection.isConnected()) {
			System.err.println("Connection not connected. skipping registration of listeners");
			return;
		}
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
				System.err.println("Received. Thread: " + m.getThread() + " subject: " + m.getSubject()+" from: "+m.getFrom());
				messageReceived(m);
			}
		}, new PacketTypeFilter(Message.class));

		myContext.addShutdownListener(new ShutdownListener(){

			public void contextShutdown() {
				if(connection!=null) {
					connection.disconnect();
				}
			}});
	}

	public static void  main(String[] args) throws XMPPException, InterruptedException {
		TipiJabberConnector tjc = new TipiJabberConnector();
		tjc.initialize();
		tjc.conferenceName = "conference.sportlink.com";
//		tjc.postRoomMembers("serverroom");
//		tjc.postRooms();
		tjc.createRoom("Den_aepenrots", "Albertus den Aep");
//		Thread.sleep(20000);

		String jid = "dashboard@sportlink.com/matthijs";
		String jidUser = jid.substring(0,jid.indexOf("@"));
		System.err.println("Jod: "+jidUser);

	}
	
	private void postRooms() {
		try {
			Navajo n = NavajoFactory.getInstance().createNavajo();
			com.dexels.navajo.document.Message m = NavajoFactory.getInstance().createMessage(n, "Rooms",
					com.dexels.navajo.document.Message.MSG_TYPE_ARRAY);
			n.addMessage(m);
			Collection<String> services = MultiUserChat.getServiceNames(connection);
			for (String string : services) {
				System.err.println("Service: "+string);
			}
			Collection<HostedRoom> aa = MultiUserChat.getHostedRooms(connection, conferenceName);
			for (HostedRoom hostedRoom : aa) {
				System.err.println("DESCRIPTION: " + hostedRoom.getName() + " # of occupants: " + hostedRoom.getJid()+" conference name: "+conferenceName);
					com.dexels.navajo.document.Message e = NavajoFactory.getInstance().createMessage(n, "Rooms",
							com.dexels.navajo.document.Message.MSG_TYPE_ARRAY_ELEMENT);
					// NavajoFactory.getInstance().createProperty(n,)
					Property user = NavajoFactory.getInstance().createProperty(n, "Name", Property.STRING_PROPERTY, hostedRoom.getName(), 0,
							"", Property.DIR_OUT, null);
					e.addProperty(user);
					Property name = NavajoFactory.getInstance().createProperty(n, "Jid", Property.STRING_PROPERTY, hostedRoom.getJid(), 0, "",
							Property.DIR_OUT, null);
					e.addProperty(name);
					m.addMessage(e);
					joinRoom(hostedRoom.getName(), "den aep" );
			}
//			n.write(System.err);
			if(myContext!=null) {
				getContext().loadNavajo(n, "JabberRooms");
			}
		} catch (NavajoException e1) {
			e1.printStackTrace();
		} catch (TipiBreakException e) {
			e.printStackTrace();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void joinRoom(String roomName, String nickName) throws XMPPException {

		if(connection==null || !connection.isConnected() || !connection.isAuthenticated()) {
			System.err.println("Can not join room: not connected");
			return;
		}
		Collection<HostedRoom> aa = MultiUserChat.getHostedRooms(connection, conferenceName);
		boolean found = false;
		for (HostedRoom hostedRoom : aa) {
			System.err.println("DESCRIPTION: " + hostedRoom.getName() + " # of occupants: " + hostedRoom.getJid());
			if (roomName.equals(hostedRoom.getName())) {
				MultiUserChat myMultiuserChat = new MultiUserChat(connection, hostedRoom.getJid());
				try {
					joinRoom(nickName, myMultiuserChat);
					found = true;
				} catch (XMPPException e) {
					System.err.println("E: " + e.getXMPPError());
					e.printStackTrace();
				}
			}
		}
		if (!found) {
			createRoom(roomName, nickName);
		}
//		postRooms();
//		postRoomMembers(roomName);
		
	}

	private void createRoom(String roomName, String nickName) throws XMPPException {
		String roomJid = roomName + "@" + conferenceName;
		MultiUserChat muc = new MultiUserChat(connection, roomJid);

		// Create the room
		muc.create(roomJid);
		Form form = muc.getConfigurationForm();
	      // Create a new form to submit based on the original form
	      Form submitForm = form.createAnswerForm();
	      // Add default answers to the form to submit
	      for (Iterator fields = form.getFields(); fields.hasNext();) {
	          FormField field = (FormField) fields.next();
				Iterator<String> values = field.getValues();
			//	System.err.println("F: " +field.getVariable()+ "  - " + field.getLabel() + " name: " + field.getDescription());
				while (values.hasNext()) {
					String val = values.next();
//					System.err.println("VAL: "+val);
				}
				if (!FormField.TYPE_HIDDEN.equals(field.getType()) && field.getVariable() != null) {
	              // Sets the default value as the answer
	            //  submitForm.setDefaultAnswer(field.getVariable());
	          }
	          if("muc#roomconfig_maxusers".equals(field.getVariable())) {
	        	  System.err.println("type: "+field.getType());
	        	  List<String> l = new ArrayList<String>();
	        	  l.add("20");
	        	  submitForm.setAnswer(field.getVariable(), l);
	        	  Iterator<String> it = submitForm.getField("muc#roomconfig_maxusers").getValues();
	        	  while (it.hasNext()) {
						String string = it.next();
						System.err.println("String>>>   >"+string+"<");
						
					}
	          }
	          if("muc#roomconfig_persistentroom".equals(field.getVariable())) {
	        	  System.err.println("muc#roomconfig_persistentroom: type: "+field.getType());
	        	  
	          	  submitForm.setAnswer(field.getVariable(), false);
	          }
	          
	      }
		      
//		Form form = new Form(Form.TYPE_SUBMIT);
		Iterator<FormField> it = form.getFields();
		while (it.hasNext()) {
			FormField f = it.next();
		}
		muc.sendConfigurationForm(submitForm);
		joinRoom(nickName, muc);
	}

	private void joinRoom(String nickName, MultiUserChat muc) throws XMPPException {
		muc.join(nickName);
		muc.addParticipantListener(new PacketListener(){

			public void processPacket(Packet pp) {
				System.err.println("pp: "+pp.getClass()+" qq: "+pp.getPropertyNames()+ "" + pp);
				if(pp instanceof Presence) {
					Presence q = (Presence)pp;
					System.err.println("Mode: "+q.getMode()+" status: "+q.getStatus());
				}
			}});
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
//			 n.write(System.err);
			 // to allow it to run straight from main:
			 if(myContext!=null) {
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
			nickname = (String) object;
		}
		if (name.equals("conferenceName")) {
			conferenceName = (String) object;
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
			if (p != null) {
				sendMessage(msg, user);
			}
		}

	}

	protected void messageReceived(Message message) {
		System.err.println("Message received!!!");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("body", message.getBody());
		String fromJid = message.getFrom();
		String from = message.getFrom().substring(0,fromJid.indexOf("@"));
		StringTokenizer st = new StringTokenizer(fromJid, "/");
		String usr = st.nextToken();
		m.put("fromJid", fromJid);
		m.put("from", from);
		System.err.println("Getting: " + usr);
		m.put("name", userMap.get(usr));
		System.err.println("Usermap: " + userMap);
		System.err.println("NAME: " + userMap.get(usr));
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
//				myContext.execute(new Runnable(){
//
//					public void run() {
						try {
							initialize();
						} catch (XMPPException e) {
							// TODO Auto-generated catch block
//							e.printStackTrace();
							throw new TipiException("Error connecting jabber: "+e.getMessage(),e);
						}
//				}});
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
		if (name.equals("joinRoom")) {
			com.dexels.navajo.document.Operand o = compMeth.getEvaluatedParameter("roomName", event);
			com.dexels.navajo.document.Operand op = compMeth.getEvaluatedParameter("nickName", event);
			if (o != null) {
				String result = (String) o.value;
				String nick = (String) op.value;
				try {
					joinRoom(result, nick);
				} catch (XMPPException e) {
					e.printStackTrace();
				}
			}
		}
		if (name.equals("startListener")) {
			addConnectionListener();
		}

	}

	private void postRoomMembers(String roomName) {
		try {
			
			
			Navajo n = NavajoFactory.getInstance().createNavajo();
			com.dexels.navajo.document.Message m = NavajoFactory.getInstance().createMessage(n, "RoomMembers",
					com.dexels.navajo.document.Message.MSG_TYPE_ARRAY);
			n.addMessage(m);
			Collection<HostedRoom> aa = MultiUserChat.getHostedRooms(connection, conferenceName);
			for (HostedRoom hostedRoom : aa) {
				System.err.println("DESCRIPTION: " + hostedRoom.getName() + " # of occupants: " + hostedRoom.getJid());
				com.dexels.navajo.document.Message e = NavajoFactory.getInstance().createMessage(n, "Rooms",
						com.dexels.navajo.document.Message.MSG_TYPE_ARRAY_ELEMENT);
				// NavajoFactory.getInstance().createProperty(n,)
				Property user = NavajoFactory.getInstance().createProperty(n, "Name", Property.STRING_PROPERTY, hostedRoom.getName(), 0,
						"", Property.DIR_OUT, null);
				e.addProperty(user);
				Property name = NavajoFactory.getInstance().createProperty(n, "Jid", Property.STRING_PROPERTY, hostedRoom.getJid(), 0, "",
						Property.DIR_OUT, null);
				e.addProperty(name);
				Property nick = NavajoFactory.getInstance().createProperty(n, "Nickname", Property.STRING_PROPERTY, hostedRoom.getJid(), 0, "",
						Property.DIR_OUT, null);
				e.addProperty(nick);

				m.addMessage(e);
			}
			n.write(System.err);
//			getContext().loadNavajo(n, "JabberRoomMembers");
		} catch (NavajoException e1) {
			e1.printStackTrace();
//		} catch (TipiBreakException e) {
//			e.printStackTrace();
		} catch (XMPPException e) {
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
		System.err.println("Doin it now!");
		if (destination == null) {
			try {
				broadcastMessage("Broadcast navajo service: " + service);
			} catch (XMPPException e1) {
				e1.printStackTrace();
			}

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
