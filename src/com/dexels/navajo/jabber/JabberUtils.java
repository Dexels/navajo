package com.dexels.navajo.jabber;

import java.util.*;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.*;
import org.jivesoftware.smackx.muc.*;

import com.dexels.navajo.document.*;

public class JabberUtils {
	
	private static MultiUserChat createRoom(String roomName, String nickName,
			String conferenceName, XMPPConnection connection)
			throws XMPPException {
		String roomJid = roomName + "@" + conferenceName;

		System.err.println("roomJid = " + roomJid);
		MultiUserChat muc = new MultiUserChat(connection, roomJid);

		// Create the room
		muc.create(roomJid);

		Form form = muc.getConfigurationForm();
		System.err.println("Form: " + form.getDataFormToSend().toXML());
		// Create a new form to submit based on the original form
		Form submitForm = form.createAnswerForm();

		// Add default answers to the form to submit
		for (Iterator<FormField> fields = form.getFields(); fields.hasNext();) {
			FormField field = fields.next();
			if ("muc#roomconfig_allowinvites".equals(field.getVariable())) {

				submitForm.setAnswer(field.getVariable(), true);

				System.err.println("Current field: " + field.getVariable());
				System.err.println("type: " + field.getType());

				Iterator<String> values = field.getValues();
				while (values.hasNext()) {
					String val = values.next();
					System.err.println("value: " + val);
				}

			} else if ("muc#roomconfig_maxusers".equals(field.getVariable())) {
				System.err.println("Current field: " + field.getVariable());
				System.err.println("type: " + field.getType());
				List<String> l = new ArrayList<String>();
				l.add("20");
				submitForm.setAnswer(field.getVariable(), l);
				Iterator<String> it = form.getField("muc#roomconfig_maxusers")
						.getValues();
				while (it.hasNext()) {
					String string = it.next();
					System.err.println("String>>>   >" + string + "<");

				}
			} else if ("muc#roomconfig_persistentroom".equals(field
					.getVariable())) {
				System.err.println("muc#roomconfig_persistentroom: type: "
						+ field.getType());

				submitForm.setAnswer(field.getVariable(), true);
			}

		}

		System.err.println(submitForm.getDataFormToSend().toXML());

		muc.sendConfigurationForm(submitForm);

		return muc;
	}

	public static Navajo postRooms(XMPPConnection connection,
			String conferenceName) {
		try {
			Navajo n = NavajoFactory.getInstance().createNavajo();
			com.dexels.navajo.document.Message m = NavajoFactory.getInstance()
					.createMessage(n, "Rooms",
							com.dexels.navajo.document.Message.MSG_TYPE_ARRAY);
			n.addMessage(m);
			Collection<String> services = MultiUserChat
					.getServiceNames(connection);
			for (String string : services) {
				System.err.println("Service: " + string);
			}
			Collection<HostedRoom> aa = MultiUserChat.getHostedRooms(
					connection, conferenceName);
			for (HostedRoom hostedRoom : aa) {
				System.err.println("DESCRIPTION: " + hostedRoom.getName()
						+ " # of occupants: " + hostedRoom.getJid()
						+ " conference name: " + conferenceName);
				com.dexels.navajo.document.Message e = NavajoFactory
						.getInstance()
						.createMessage(
								n,
								"Rooms",
								com.dexels.navajo.document.Message.MSG_TYPE_ARRAY_ELEMENT);
				// NavajoFactory.getInstance().createProperty(n,)
				Property user = NavajoFactory.getInstance().createProperty(n,
						"Name", Property.STRING_PROPERTY, hostedRoom.getName(),
						0, "", Property.DIR_OUT, null);
				e.addProperty(user);
				Property name = NavajoFactory.getInstance().createProperty(n,
						"Jid", Property.STRING_PROPERTY, hostedRoom.getJid(),
						0, "", Property.DIR_OUT, null);
				e.addProperty(name);
				m.addMessage(e);
				// joinRoom(hostedRoom.getName(), "den aep");
			}
			return n;
		} catch (NavajoException e1) {
			e1.printStackTrace();
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Navajo postRoomMembers(XMPPConnection connection,
			String conferenceName, String roomName) {
		try {

			Navajo n = NavajoFactory.getInstance().createNavajo();
			com.dexels.navajo.document.Message m = NavajoFactory.getInstance()
					.createMessage(n, "RoomMembers",
							com.dexels.navajo.document.Message.MSG_TYPE_ARRAY);
			n.addMessage(m);
			Collection<HostedRoom> aa = MultiUserChat.getHostedRooms(
					connection, conferenceName);
			for (HostedRoom hostedRoom : aa) {
				System.err.println("DESCRIPTION: " + hostedRoom.getName()
						+ " # of occupants: " + hostedRoom.getJid());
				com.dexels.navajo.document.Message e = NavajoFactory
						.getInstance()
						.createMessage(
								n,
								"Rooms",
								com.dexels.navajo.document.Message.MSG_TYPE_ARRAY_ELEMENT);
				// NavajoFactory.getInstance().createProperty(n,)
				Property user = NavajoFactory.getInstance().createProperty(n,
						"Name", Property.STRING_PROPERTY, hostedRoom.getName(),
						0, "", Property.DIR_OUT, null);
				e.addProperty(user);
				Property name = NavajoFactory.getInstance().createProperty(n,
						"Jid", Property.STRING_PROPERTY, hostedRoom.getJid(),
						0, "", Property.DIR_OUT, null);
				e.addProperty(name);
				Property nick = NavajoFactory.getInstance().createProperty(n,
						"Nickname", Property.STRING_PROPERTY,
						hostedRoom.getJid(), 0, "", Property.DIR_OUT, null);
				e.addProperty(nick);

				m.addMessage(e);
			}
			n.write(System.err);
			return n;
			// getContext().loadNavajo(n, "JabberRoomMembers");
		} catch (NavajoException e1) {
			e1.printStackTrace();
			// } catch (TipiBreakException e) {
			// e.printStackTrace();
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static MultiUserChat joinRoom(XMPPConnection connection,
			String conferenceName, String roomName, String nickName,
			final Set<String> occupants) throws XMPPException {

		if (connection == null || !connection.isConnected()
				|| !connection.isAuthenticated()) {
			System.err.println("Can not join room: not connected");
			return null;
		}
		System.err.println("Room join: " + conferenceName);

		Collection<HostedRoom> aa = MultiUserChat.getHostedRooms(connection, conferenceName);

		boolean found = false;
		for (HostedRoom hostedRoom : aa) {
			System.err.println("Checking room: " + hostedRoom.getName()
					+ " looking for: " + roomName);
			if (roomName.toLowerCase().equals(
					hostedRoom.getName().toLowerCase())) {
				MultiUserChat myMultiuserChat = new MultiUserChat(connection,
						hostedRoom.getJid());
				System.err.println("JID: " + hostedRoom.getJid());
				occupants.clear();
				//registerRoomListeners(nickName, myMultiuserChat, occupants);
				System.err.println("Joining");
				System.err.println("Before Join: People in the room: "
						+ myMultiuserChat.getOccupantsCount());

				nickName = join(nickName, myMultiuserChat, 0);
				System.err.println("People in the room: "
						+ myMultiuserChat.getOccupantsCount());
				// This odd construction is necessary. We need to release this
				// thread before the
				// actual joining happens.
				new Thread() {
					public void run() {
						try {
							System.err.println("Joined, waiting");
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}

				}.start();

				found = true;
				return myMultiuserChat;
			}

		}

		if (!found) {
			MultiUserChat muc = JabberUtils.createRoom(roomName, nickName,
					conferenceName, connection);
			occupants.clear();

			//registerRoomListeners(nickName, muc, occupants);
			
			nickName = join(nickName, muc, 0);
			return muc;
		}
		return null;
	}

	/**
	 * @param nickName
	 * @param myMultiuserChat
	 * @throws XMPPException
	 */
	private static String join(String nickName, MultiUserChat myMultiuserChat,
			int count) {
		try {
			if (count > 0) {
				myMultiuserChat.join(nickName + " (" + count + ")");
			} else {
				myMultiuserChat.join(nickName);
			}
		} catch (XMPPException e) {
			System.err.println(e.getMessage());
			System.err.println("Nick taken: " + nickName);
			return join(nickName, myMultiuserChat, count + 1);
		}
		return nickName;
	}

	private static void registerRoomListeners(String nickName,
			final MultiUserChat muc, final Set<String> occupants) {
		muc.addParticipantStatusListener(new ParticipantStatusListener() {
			public void adminGranted(String arg0) {
			}

			public void adminRevoked(String arg0) {
			}

			public void banned(String arg0, String arg1, String arg2) {
			}

			public void joined(String j) {
				System.err.println("JOINED: " + j);
				System.err.println("occupants: " + occupants);
				occupants.add(j);
				Navajo n = postRoomUpdate(occupants);
			}

			public void kicked(String arg0, String arg1, String arg2) {
			}

			public void left(String j) {
				System.err.println("LEFT:" + j);
				occupants.remove(j);
				Navajo n = postRoomUpdate(occupants);

			}

			public void membershipGranted(String membershipGranted) {
				System.err.println("membershipGranted: " + membershipGranted);
			}

			public void membershipRevoked(String membershipRevoked) {
			}

			public void moderatorGranted(String moderatorGranted) {
			}

			public void moderatorRevoked(String moderatorRevoked) {
			}

			public void nicknameChanged(String arg0, String arg1) {
			}

			public void ownershipGranted(String arg0) {
			}

			public void ownershipRevoked(String arg0) {
			}

			public void voiceGranted(String arg0) {
			}

			public void voiceRevoked(String arg0) {
			}
		});
	}

	protected static Navajo postRoomUpdate(Set<String> occupants) {
		try {
			Navajo n = NavajoFactory.getInstance().createNavajo();
			com.dexels.navajo.document.Message m = NavajoFactory.getInstance()
					.createMessage(n, "RoomMembers",
							com.dexels.navajo.document.Message.MSG_TYPE_ARRAY);
			n.addMessage(m);

			for (String element : occupants) {
				addRoomMember(m, element);

			}

			// }
			return n;
		} catch (NavajoException e) {
			e.printStackTrace();
		}
		return null;

	}

	private static void addRoomMember(com.dexels.navajo.document.Message m,
			String occupant) throws NavajoException {
		com.dexels.navajo.document.Message element = NavajoFactory
				.getInstance()
				.createMessage(
						m.getRootDoc(),
						"RoomMembers",
						com.dexels.navajo.document.Message.MSG_TYPE_ARRAY_ELEMENT);
		m.addMessage(element);

		element.addProperty(NavajoFactory.getInstance().createProperty(
				m.getRootDoc(), "Id", Property.STRING_PROPERTY, occupant, 0,
				"", Property.DIR_OUT, null));
		element.addProperty(NavajoFactory.getInstance().createProperty(
				m.getRootDoc(), "Name", Property.STRING_PROPERTY,
				extractName(occupant), 0, "", Property.DIR_OUT, null));

	}

	/**
	 * @param occupant
	 * @return
	 */
	private static String extractName(String occupant) {
		return occupant.substring(occupant.lastIndexOf("/") + 1, occupant
				.length());
	}

	public static void main(String[] args) throws Exception {

		ConnectionConfiguration config = new ConnectionConfiguration(
				"spiritus", 5222);
		XMPPConnection connection = new XMPPConnection(config);
		connection.connect();
		// connection.getAccountManager().createAccount("harrie", "xxxxxx");
		connection.login("harrie", "xxxxxx");
		System.err.println("servicename = " + connection.getServiceName());

		Collection<HostedRoom> c = MultiUserChat.getHostedRooms(connection,
				"conference.dexels.nl");

		for (HostedRoom hostedRoom : c) {
			System.err.println("room =" + hostedRoom.getName());
			RoomInfo info = MultiUserChat.getRoomInfo(connection, hostedRoom
					.getJid());

			MultiUserChat myMultiuserChat = new MultiUserChat(connection,
					hostedRoom.getJid());
			myMultiuserChat.join("dashboard");

			Iterator<String> occupants = myMultiuserChat.getOccupants();

			myMultiuserChat.addParticipantListener(new PacketListener() {

				public void processPacket(Packet arg0) {
					System.err.println("IN addParticipantListener: "
							+ arg0.getClass().getName());
					Presence pres = (Presence) arg0;
					System.err.println("WHO: "
							+ pres.getFrom().substring(
									pres.getFrom().indexOf("/") + 1));
					System.err.println(pres.toXML());
				}
			});
			// Thread.sleep(1000);

			System.err.println("occupants: "
					+ myMultiuserChat.getOccupantsCount());

			occupants = myMultiuserChat.getOccupants();

			while (occupants.hasNext()) {
				String okkie = occupants.next();
				Occupant occu = myMultiuserChat.getOccupant(okkie);
				System.err.println("occupant: " + occu.getNick());
				if (!occu.getNick().equals("dashboard")) {
					Chat chat = myMultiuserChat.createPrivateChat(okkie,
							new MessageListener() {

								public void processMessage(Chat arg0,
										Message arg1) {
									System.err.println("IN processMessage..");
									System.err.println(arg1.getBody());

								}
							});

					System.err.println("Sending message..."
							+ chat.getThreadID());
					chat.sendMessage("postman");
				}
			}
		}
		// connection.login(username, password,
		// NavajoClientFactory.getClient().getSessionToken());
		// joinRoom(connection, "conference.dexels.nl", "MyNavajoGroup5",
		// "MyNavajoServer3", new HashSet<String>());

		// MultiUserChat myMultiuserChat = new MultiUserChat(connection,
		// "MyNavajoGroup@conference.dexels.nl");
		// myMultiuserChat.join("MyNavajoServer2");
		// myMultiuserChat.sendMessage("apenoot");
		// System.err.println("joined = " + myMultiuserChat.isJoined());
		//		
		while (true) {
			Thread.sleep(10000);
		}
	}
}
