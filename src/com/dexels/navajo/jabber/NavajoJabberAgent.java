package com.dexels.navajo.jabber;

import java.io.*;
import java.util.*;
import java.util.Map.*;


import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.MultiUserChat;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.server.*;

public class NavajoJabberAgent  {
	
	private String postmanUrl;
	private String conferenceName;
	private String nickName;
	private String serverGroupName;
	
	private final Map<String, List<Chat>> myTailMap = new HashMap<String, List<Chat>>();
	private final Map<String, List<String>> inverseTailMap = new HashMap<String, List<String>>();

	protected XMPPConnection connection;
	
	public boolean isConnected() {
		if (connection == null) {
			return false;
		}
		return connection.isConnected();
	}

//	public boolean hasJoinedRoom(String roomName)  {
//		Iterator<String> joinedRooms = MultiUserChat.getJoinedRooms(connection, conferenceName);
//		System.err.println("hasJoinedRoom(" + roomName + ")");
//		while ( joinedRooms.hasNext() ) {
//			String joinedRoom = joinedRooms.next();
//			System.err.println("Checking room: " + roomName + ", joinedRoom: " + joinedRoom);
//			if ( joinedRoom.equalsIgnoreCase(roomName)  ) {
//				return true;
//			}
//		}
//		return false;
//	}
	
	public MultiUserChat joinRoom(String roomName) throws UserException {

		try {
			MultiUserChat muc = JabberUtils.joinRoom(connection, conferenceName, roomName, nickName, new HashSet<String>());
			return muc;
		} catch (XMPPException e) {
			throw new UserException(-1, e.getMessage(), e);
		}
		
	}
	
	public void initialize(String server, int port, String chatDomain, String postmanUrl) throws XMPPException {
		// XMPPConnection.DEBUG_ENABLED = true;
		
		this.postmanUrl = postmanUrl;
	
		if (connection != null) {
			if (connection.isConnected()) {
				connection.disconnect();
			}
		}
		ConnectionConfiguration config = new ConnectionConfiguration(server, port);
		connection = new XMPPConnection(config);
		
		connection.connect();
		connection.loginAnonymously();
		
		this.conferenceName = chatDomain + "." + connection.getServiceName();
		this.nickName =  "server-" + DispatcherFactory.getInstance().getNavajoConfig().getInstanceName();
		this.serverGroupName = "navajotribe-" + DispatcherFactory.getInstance().getNavajoConfig().getInstanceGroup();
		
		//connection.login(username, password, NavajoClientFactory.getClient().getSessionToken());
		MultiUserChat muc = JabberUtils.joinRoom(connection, conferenceName, serverGroupName, nickName, new HashSet<String>());
		JabberWorker.getInstance().setMultiUserChat("server", muc);
		//System.err.println("Login ok");

		// TODO: REFACTOR THIS TO 	muc.addMessageListener(listener)...
	
		connection.addPacketListener(new PacketListener() {
			public void processPacket(Packet p) {
				
				if ( p instanceof Message ) {
					
					Message m = (Message) p;
					
					if ( m.getProperty("itisi") != null && m.getProperty("itisi").equals("leclerck") ) {
						//System.err.println("The madonna with the big boobies.");
						return;
					} 
					//System.err.println("Incoming packet from: "+m.getFrom()+" to: "+m.getTo()+" about: "+m.getSubject());
//					System.err.println("------ INCOMING CHAT MESSAGE ----");
					System.err.println("FROM: " + m.getFrom());
					System.err.println("TO  : " + m.getTo());
					System.err.println("SUBJECT: " + m.getSubject());

					//System.err.println("BODY: "+m.toXML());
					if(m.getFrom().equals(m.getTo())) {
						System.err.println("Circular shit!");
						return;
					}
					//System.err.println("\nQueuesize: "+ JabberWorker.getInstance().getQueueSize());
					String from = m.getFrom();
					String to = m.getTo();
					if (from.equals(to)) {
						return;
					}
					if("serverResponse".equals(m.getSubject())) {
						//System.err.println("Ignoring server response!");
						return;
					}

					Chat c = connection.getChatManager().getThreadChat(m.getThread());
	//					if (c != null) {
	//						//System.err.println("Chat found");
	//					} else {
	//						//System.err.println("chat not found!");
	//					}
					try {
						messageReceived(m);
					} catch (XMPPException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if ( p instanceof Presence){
//					System.err.println("RECEIVED PRESENCE: " + p + "(" + p.getClass() + ")");
//					Presence pr = (Presence ) p;
//					System.err.println("FROM: " + pr.getFrom());
//					System.err.println("STATUS: " + pr.getStatus());
//					System.err.println("TYPE: " + pr.getType());
				} else {
					//System.err.println("RECEIVED PRESENCE: " + p + "(" + p.getClass() + ")");
				}
				
				//if ( Dispatcher.getInstance() != null && TribeManagerFactory.getInstance().getIsChief() ) { 
					// Only the chief perform jabber trigger requests, for now... 
					// rewrite as TimeTrigger using a function that only fires trigger and puts it in a activatedlisteners map.
					Iterator<JabberTrigger> all = JabberWorker.getInstance().getTriggers().iterator();
					while ( all.hasNext() ) {
						JabberTrigger jt = all.next();
						if ( p instanceof Message ) {
							try {
								// Fake access to support Navajo Document in message body...
								Access newAccess = new Access(-1,-1,-1,"Unknown","Unknown","","","",false,null);
								Message m = (Message) p;
								Navajo n = NavajoFactory.getInstance().createNavajo(new StringReader(m.getBody()));
								newAccess.setInDoc(n);
								jt.setAccess(newAccess);
							} catch (Throwable t) {
								System.err.println("WARNING, INCOMING JABBER MESSAGE DOES NOT CONTAIN VALID NAVAJO: " + t);
							}
						}
						System.err.println("CHECKING TRIGGER: " + jt.getDescription()  + ", type = " + jt.getType());
						if ( p.getFrom().matches(jt.getFrom() ) ) {
							if ( jt.getType().equals(JabberTrigger.TYPE_MESSAGE) && (p instanceof Message) ) {
								System.err.println("Peforming task...");
								jt.perform();
							} else if ( jt.getType().equals(JabberTrigger.TYPE_PRESENCE) && ( p instanceof Presence )) {
								Presence pp = (Presence) p;
								String stat = pp.getType() + "/" + pp.getStatus();
								System.err.println("status = " + jt.getStatus() + ", received status: " + stat );
								if ( jt.getStatus() == null || stat.startsWith(jt.getStatus()) ) {
									System.err.println("Peforming task...");
									jt.perform();
								}
							}
						}
					}
				//}
			}
		}, null);
	}

	public void sendMessage(String text, String recipient, String navajoType) throws XMPPException {
		Chat c = connection.getChatManager().createChat(recipient, null);

		// connection.getAccountManager().
		Message m = new Message(text);
		if (navajoType != null) {
			m.setProperty("navajoType", navajoType);
			m.setProperty("tml", text);
		}
		c.sendMessage(m);
	}

	protected void broadcastMessage(String msg, String navajoType) throws XMPPException {
		Roster r = connection.getRoster();
		System.err.println("# of entries: " + r.getEntryCount());
		for (RosterEntry rosterEntry : r.getEntries()) {
			String user = rosterEntry.getUser();
			System.err.println("RosterEntry: " + user);
			if (r.getPresence(user).isAvailable()) {
				System.err.println("SENT: " + msg + " to: " + user + " :: " + rosterEntry);
				sendMessage(msg, user, navajoType);
			}
		}
	}

	public void disconnect() {
		if (connection != null) {
			connection.disconnect();
		}
	}

	/**
	 * @param args
	 */
//	public static void main(String[] args) throws XMPPException, InterruptedException {
//		NavajoClientFactory.getClient().setUsername(navajoUsername);
//		NavajoClientFactory.getClient().setPassword(navajoPassword);
//		NavajoClientFactory.getClient().setServerUrl(navajoServerUrl);
//		NavajoJabberAgent ppp = new NavajoJabberAgent();
//		ppp.initialize("hermes1.sportlink.com", 5222, "sportlink.com", "distel", "distel");
//		while (true) {
//			Thread.sleep(500);
//		}
//
//	}

	public void setPresenceStatus(String statusMessage) {
		System.err.println("STATUS: " + statusMessage);
		if (connection != null && connection.isConnected()) {
			Presence presence = new Presence(Presence.Type.available);
			presence.setStatus(statusMessage);
			connection.sendPacket(presence);
			System.err.println("Presence setnt");
		}
	}

	protected void messageReceived(Message message) throws XMPPException {
		if (message.getProperty("navajoType") != null) {
			//System.err.println("Calling service " + message.getBody() + " from: " + message.getFrom());
			try {
				Navajo result = NavajoClientFactory.getClient().doSimpleSend(NavajoFactory.getInstance().createNavajo(), message.getBody());
				StringWriter sw = new StringWriter();
				try {
					result.write(sw);
				} catch (NavajoException e) {
					e.printStackTrace();
				}
				sendMessage(sw.toString(), message.getFrom(), "reply");

			} catch (ClientException e) {
				e.printStackTrace();
			}
		} else {
			commandReceived(message);
		}
	}

	private void commandReceived(Message message) throws XMPPException {
		String text = message.getBody();
		StringTokenizer st = new StringTokenizer(text, " ");
		if (!st.hasMoreTokens()) {
			replyText("What?", message);
			return;
		}
		String command = st.nextToken();
		
		System.err.println("Received command: " + command);
		
		if ("help".equals(command)) {
			replyHelpMessage(message);
			return;
		}
		if ("fire".equals(command)) {
			List<String> services = inverseTailMap.get(message.getFrom());
			for (String service : services) {
				replyText("Service: " + service, message);
			}
			return;
		}

		if ("tail".equals(command)) {
			if (!st.hasMoreTokens()) {
				replyHelpMessage(message);
				return;
			}
			String service = st.nextToken();
			registerTail(service, getChat(message), message.getFrom());
			replyText("Tailing " + service, message);
			return;
		}
		if ("untail".equals(command)) {
			if (!st.hasMoreTokens()) {
				replyHelpMessage(message);
				return;
			}
			String service = st.nextToken();
			deRegisterTail(service, getChat(message), message.getFrom());
			replyText("Tail removed: " + service, message);
			return;
		}
		if ("status".equals(command)) {
			Navajo n;
			try {
				n = callService("InitNavajoStatus");
				replyText("Status:\n" + n.toString(), message);
			} catch (FatalException e) {
				e.printStackTrace();
				replyText("Error calling serivice:\n", message);
			}
			return;
		}
		if ("tails".equals(command)) {
			List<String> ss = inverseTailMap.get(message.getFrom());
			if (ss==null || ss.isEmpty()) {
				replyText("No registered tails!", message);
				return;
			}
			for (String string : ss) {
				replyText("Tailing: " + string, message);
			}
			return;
		}
		if ("debug".equals(command)) {
			debugTailRegistry(message);
			return;
		}
		if ("postman".equals(command)) {
			System.err.println("Returning: " + postmanUrl);
			replyText(postmanUrl, message);
			
			return;
		}
		replyHelpMessage(message);
	}

	private void registerTail(String service, Chat chat, String from) {
		// maps services on lists of chat sessions
		List<Chat> l = myTailMap.get(service);
		if (l == null) {
			l = new ArrayList<Chat>();
			myTailMap.put(service, l);
		}
		l.add(chat);
		// inverse: maps users to list of services
		List<String> m = inverseTailMap.get(from);
		if (m == null) {
			m = new ArrayList<String>();
			inverseTailMap.put(from, m);
		}
		m.add(service);

	}

	private void deRegisterTail(String service, Chat chat, String from) {
		// maps services on lists of chat sessions
		List<Chat> l = myTailMap.get(service);
		if (l == null || l.isEmpty()) {
			// huh?
		} else {
			l.remove(chat);
			if(l.isEmpty()) {
				myTailMap.remove(service);
			}
		}
		// inverse: maps users to list of services
		List<String> m = inverseTailMap.get(from);
	
		if (m == null) {
			} else {
			
			m.remove(service);
		}
	}

	private void deRegisterAllTails(Chat chat, String from) {
		// maps services on lists of chat sessions
		for (Entry<String, List<Chat>> c : myTailMap.entrySet()) {
			c.getValue().remove(chat);
			if (c.getValue().isEmpty()) {
				myTailMap.remove(c.getKey());
			}
		}
		inverseTailMap.remove(from);
	}

	public void fireTail(String service, Navajo n) throws XMPPException {
		List<Chat> l = myTailMap.get(service);
		if (l == null) {
			return;
		}
		for (Chat chat : l) {
			chat.sendMessage("TAIL FOR: \n" + n.toString()+"\n queue size: "+JabberWorker.getInstance().getQueueSize());
		}
	}

	private void debugTailRegistry(Message m) throws XMPPException {
		replyText("TAIL: " +myTailMap.toString(), m);
		replyText("INVERSETAIL: " + inverseTailMap.toString(), m);
		System.err.println("TAIL: " + myTailMap);
		System.err.println();
	}

	private void replyHelpMessage(Message message) throws XMPPException {
		String help;
		try {
			help = getStringFromStream(getClass().getResourceAsStream("help.txt"));
			replyText(help, message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void replyText(String text, Message message) throws XMPPException {
		//Chat c = getChat(message);
		Message m = new Message();
		m.setBody(text);
		m.setThread(message.getThread());
		m.setFrom(message.getTo());
		m.setTo(message.getFrom());
		m.setSubject("serverResponse");
		//System.err.println(message.toXML());
		//System.err.println("threadid: " + c.getThreadID());
		connection.sendPacket(m);
	}

	private Chat getChat(Message message) {
		Chat c = connection.getChatManager().getThreadChat(message.getThread());
		if (c == null) {
			System.err.println(">>>> No chat found.");
			c = connection.getChatManager().createChat(message.getFrom(), null);
		}
		return c;
	}

	protected void flushTails(String from) {
		List<String> serviceList = inverseTailMap.get(from);
		if (serviceList == null) {
			return;
		}
		for (String service : serviceList) {
			myTailMap.remove(service);
		}
		inverseTailMap.remove(from);

	}

	public final boolean isRegisteredAsTail(String service) {
		return myTailMap.containsKey(service);
	}
	
	public Navajo callService(String service) throws FatalException {
		Navajo in = NavajoFactory.getInstance().createNavajo();
		Header h = NavajoFactory.getInstance().createHeader(in, service, "unknown", "unknown", -1);
		in.addHeader(h);
		Navajo reply = DispatcherFactory.getInstance().handle(in);
		return reply;
	}

	private final String getStringFromStream(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		copyResource(bos, is);
		return new String(bos.toByteArray());
	}

	private final void copyResource(OutputStream out, InputStream in) throws IOException {
		BufferedInputStream bin = new BufferedInputStream(in);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		byte[] buffer = new byte[1024];
		int read;
		while ((read = bin.read(buffer)) > -1) {
			bout.write(buffer, 0, read);
		}
		bin.close();
		bout.flush();
		bout.close();
	}

	public String getConferenceName() {
		return conferenceName;
	}

	public String getPostmanUrl() {
		return postmanUrl;
	}

	public String getServerGroupName() {
		return serverGroupName;
	}


}
