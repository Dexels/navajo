package com.dexels.navajo.jabber;

import java.io.*;
import java.util.*;
import java.util.Map.*;

import javax.jws.WebParam.*;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.filter.*;
import org.jivesoftware.smack.packet.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.*;
import org.jivesoftware.smack.packet.Presence.Mode;
import org.jivesoftware.smackx.*;
import org.jivesoftware.smackx.muc.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.events.*;
import com.dexels.navajo.events.types.*;
import com.dexels.navajo.server.*;

public class NavajoJabberAgent  {
	protected static String navajoUsername = "ROOT";
	protected static String navajoPassword = "R20T";
	protected static String navajoServerUrl = "penelope1.dexels.com/sportlink/knvb/servlet/Postman";

	protected static String server = "hermes1.dexels.com";
	protected static int port = 5222;
	protected static String chatDomain = "sportlink.com";
	protected static String username = "distel";
	protected static String password = "distel";

	private final Map<String, MultiUserChat> myChats = new HashMap<String, MultiUserChat>();

	private final Map<String, List<Chat>> myTailMap = new HashMap<String, List<Chat>>();
	private final Map<String, List<String>> inverseTailMap = new HashMap<String, List<String>>();

	private String myRoom = null;

	protected ConnectionConfiguration config;
	protected XMPPConnection connection;

	public boolean isConnected() {
		if (connection == null) {
			return false;
		}
		return connection.isConnected();
	}

	public void initialize(String server, int port, String chatDomain, String username, String password) throws XMPPException {
		// XMPPConnection.DEBUG_ENABLED = true;
		if (connection != null) {
			if (connection.isConnected()) {
				connection.disconnect();
			}
		}
		config = new ConnectionConfiguration(server, port);
		connection = new XMPPConnection(config);
		connection.connect();
		connection.login(username, password, NavajoClientFactory.getClient().getSessionToken());
		//System.err.println("Login ok");

		connection.addPacketListener(new PacketListener() {
			public void processPacket(Packet p) {
				Message m = (Message) p;
				//System.err.println("Incoming packet from: "+m.getFrom()+" to: "+m.getTo()+" about: "+m.getSubject());
				//System.err.println("BODY: "+m.getBody());
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
				if (c != null) {
					System.err.println("Chat found");
				} else {
					System.err.println("chat not found!");
				}
				try {
					messageReceived(m);
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, new PacketTypeFilter(Message.class));

		connection.getRoster().addRosterListener(new RosterListener() {
			public void entriesAdded(Collection<String> arg0) {
			}

			public void entriesDeleted(Collection<String> arg0) {
			}

			public void entriesUpdated(Collection<String> arg0) {
			}

			public void presenceChanged(Presence p) {
				System.err.println("Presence: " + p.getFrom() + " status: " + p.getStatus() + " type: " + p.getType());
				if (p.getType() == Presence.Type.unavailable) {

//					debugTailRegistry();
					flushTails(p.getFrom());
					System.err.println("Flushed: " + p.getFrom());
//					debugTailRegistry();
				}
			}
		});
		// Response handling not enabled

		// connection.addPacketListener(new PacketListener(){
		// public void processPacket(Packet p) {
		// Message m = (Message)p;
		// messageReceived(m);
		// }}, new PacketTypeFilter(Message.class));

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
	public static void main(String[] args) throws XMPPException, InterruptedException {
		NavajoClientFactory.getClient().setUsername(navajoUsername);
		NavajoClientFactory.getClient().setPassword(navajoPassword);
		NavajoClientFactory.getClient().setServerUrl(navajoServerUrl);
		NavajoJabberAgent ppp = new NavajoJabberAgent();
		ppp.initialize("hermes1.sportlink.com", 5222, "sportlink.com", "distel", "distel");
		while (true) {
			Thread.sleep(500);
		}

	}

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
			System.err.println("Calling service " + message.getBody() + " from: " + message.getFrom());
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
		Chat c = getChat(message);
		Message m = new Message();
		m.setBody(text);
		m.setThread(c.getThreadID());
		m.setFrom(message.getTo());
		m.setTo(message.getFrom());
		m.setSubject("serverResponse");
		c.sendMessage(m);
	}

	private Chat getChat(Message message) {
		Chat c = connection.getChatManager().getThreadChat(message.getThread());
		if (c == null) {
			System.err.println("No chat found.");
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

	public void joinRoom(String roomName, String nickName, String conferenceName) throws XMPPException {
		System.err.println("Getting conference service: " + conferenceName);
		Collection<HostedRoom> aa = MultiUserChat.getHostedRooms(connection, conferenceName);
		boolean found = false;
		for (HostedRoom hostedRoom : aa) {
			System.err.println("DESCRIPTION: " + hostedRoom.getName() + " # of occupants: " + hostedRoom.getJid());
			if (roomName.equals(hostedRoom.getName())) {
				MultiUserChat myMultiuserChat = new MultiUserChat(connection, hostedRoom.getJid());
				myChats.put(roomName, myMultiuserChat);
				try {
					myMultiuserChat.join(nickName);
					found = true;
					myRoom = roomName;
				} catch (XMPPException e) {
					System.err.println("E: " + e.getXMPPError());
					e.printStackTrace();
				}
			}
		}
		if (!found) {
			MultiUserChat muc = new MultiUserChat(connection, roomName + "@" + conferenceName);

			// Create the room
			muc.create(roomName);
			Form form = new Form(Form.TYPE_SUBMIT);
			Iterator<FormField> it = form.getFields();
			while (it.hasNext()) {
				FormField f = it.next();
				System.err.println("F: " + f.getLabel() + " name: " + f.getDescription() + " value: " + f.getValues());
			}
			muc.sendConfigurationForm(form);
			muc.join(nickName);
			myChats.put(roomName, muc);
			myRoom = roomName;

		}
	}

	public void broadcastIntoRoom(String roomName, String text) throws XMPPException {
		MultiUserChat muc = myChats.get(roomName);
		muc.sendMessage(text);
		// Collection<Occupant> o = muc.getParticipants();
		// for (Occupant occupant : o) {
		// }
	}

	public void setNickName(String nickName) throws XMPPException {
		if (myRoom != null) {
			MultiUserChat m = myChats.get(myRoom);
			if (m != null) {
				m.changeNickname(nickName);
			}
		}
	}

	public void setAvailabilityStatus(String status) {
		if (myRoom != null) {
			MultiUserChat m = myChats.get(myRoom);
			if (m != null) {
				m.changeAvailabilityStatus(status, Mode.away);
			}
		}
		setPresenceStatus(status);
	}

	public void sendAlert() {
		if (myRoom != null) {
			MultiUserChat m = myChats.get(myRoom);
			if (m != null) {
				try {
					m.sendMessage(new Message("Hey!", Type.chat));
				} catch (XMPPException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Navajo callService(String service) throws FatalException {
		Navajo in = NavajoFactory.getInstance().createNavajo();
		Header h = NavajoFactory.getInstance().createHeader(in, service, "unknown", "unknown", -1);
		in.addHeader(h);
		Navajo reply = Dispatcher.getInstance().handle(in);
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


}
