package com.dexels.navajo.tipi.jabber;

import java.util.*;

import org.jivesoftware.smack.*;
import org.jivesoftware.smackx.*;
import org.jivesoftware.smackx.muc.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;

public class JabberUtils {
	public static MultiUserChat createRoom(String roomName, String nickName, String conferenceName, XMPPConnection connection)
			throws XMPPException {
		String roomJid = roomName + "@" + conferenceName;
		MultiUserChat muc = new MultiUserChat(connection, roomJid);

		// Create the room
		muc.create(roomJid);
		Form form = muc.getConfigurationForm();
		// Create a new form to submit based on the original form
		Form submitForm = form.createAnswerForm();
		// Add default answers to the form to submit
		for (Iterator<FormField> fields = form.getFields(); fields.hasNext();) {
			FormField field = fields.next();
			Iterator<String> values = field.getValues();
			while (values.hasNext()) {
				// String val = values.next();
			}
			if (!FormField.TYPE_HIDDEN.equals(field.getType()) && field.getVariable() != null) {
			}
			if ("muc#roomconfig_maxusers".equals(field.getVariable())) {
				System.err.println("type: " + field.getType());
				List<String> l = new ArrayList<String>();
				l.add("20");
				submitForm.setAnswer(field.getVariable(), l);
				Iterator<String> it = submitForm.getField("muc#roomconfig_maxusers").getValues();
				while (it.hasNext()) {
					String string = it.next();
					System.err.println("String>>>   >" + string + "<");

				}
			}
			if ("muc#roomconfig_persistentroom".equals(field.getVariable())) {
				System.err.println("muc#roomconfig_persistentroom: type: " + field.getType());

				submitForm.setAnswer(field.getVariable(), false);
			}

		}

		muc.sendConfigurationForm(submitForm);
		return muc;
	}

	public static Navajo postRooms(XMPPConnection connection, String conferenceName) {
		try {
			Navajo n = NavajoFactory.getInstance().createNavajo();
			com.dexels.navajo.document.Message m = NavajoFactory.getInstance().createMessage(n, "Rooms",
					com.dexels.navajo.document.Message.MSG_TYPE_ARRAY);
			n.addMessage(m);
			Collection<String> services = MultiUserChat.getServiceNames(connection);
			for (String string : services) {
				System.err.println("Service: " + string);
			}
			Collection<HostedRoom> aa = MultiUserChat.getHostedRooms(connection, conferenceName);
			for (HostedRoom hostedRoom : aa) {
				System.err.println("DESCRIPTION: " + hostedRoom.getName() + " # of occupants: " + hostedRoom.getJid()
						+ " conference name: " + conferenceName);
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
				// joinRoom(hostedRoom.getName(), "den aep");
			}
			return n;
		} catch (NavajoException e1) {
			e1.printStackTrace();
		} catch (TipiBreakException e) {
			e.printStackTrace();
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Navajo postRoomMembers(XMPPConnection connection, String conferenceName, String roomName) {
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
				Property nick = NavajoFactory.getInstance().createProperty(n, "Nickname", Property.STRING_PROPERTY, hostedRoom.getJid(), 0,
						"", Property.DIR_OUT, null);
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

	public static MultiUserChat joinRoom(XMPPConnection connection, final TipiContext context, String conferenceName, String roomName, String nickName, final Set<String> occupants)
			throws XMPPException {

		if (connection == null || !connection.isConnected() || !connection.isAuthenticated()) {
			System.err.println("Can not join room: not connected");
			return null;
		}
		Collection<HostedRoom> aa = MultiUserChat.getHostedRooms(connection, conferenceName);
		boolean found = false;
		for (HostedRoom hostedRoom : aa) {
			System.err.println("Checking room: " + hostedRoom.getName() + " looking for: " + roomName);
			if (roomName.toLowerCase().equals(hostedRoom.getName().toLowerCase())) {
				MultiUserChat myMultiuserChat = new MultiUserChat(connection, hostedRoom.getJid());
				System.err.println("JID: "+hostedRoom.getJid());
				occupants.clear();
				registerRoomListeners(nickName, myMultiuserChat, context,occupants);
				System.err.println("Joining");
				System.err.println("Before Join: People in the room: "+myMultiuserChat.getOccupantsCount());

				nickName = join(nickName, myMultiuserChat, 0);
				System.err.println("People in the room: "+myMultiuserChat.getOccupantsCount());
				// This odd construction is necessary. We need to release this thread before the
				// actual joining happens.
				new Thread(){
					public void run() {
						try {
							System.err.println("Joined, waiting");
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if(context!=null) {
							Navajo nn = postRoomUpdate(occupants);
							context.injectNavajo("RoomPresence", nn);
						}
					}
					
				}.start();
				

				found = true;
				return myMultiuserChat;
			}

		}
		 
		if (!found) {
			MultiUserChat muc = JabberUtils.createRoom(roomName, nickName, conferenceName, connection);
			occupants.clear();

			registerRoomListeners(nickName, muc, context,occupants);
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
	private static String join(String nickName, MultiUserChat myMultiuserChat, int count) {
			try {
				if(count>0) {
					myMultiuserChat.join(nickName+" ("+count+")");
				} else {
					myMultiuserChat.join(nickName);
				}
			} catch (XMPPException e) {
				System.err.println("Nick taken!");
				return join(nickName, myMultiuserChat, count+1);
			}
			return nickName;
	}

	private static void registerRoomListeners(String nickName, final MultiUserChat muc, final TipiContext tc,final Set<String> occupants) throws XMPPException {
		muc.addParticipantStatusListener(new ParticipantStatusListener() {
			public void adminGranted(String arg0) {
			}

			public void adminRevoked(String arg0) {
			}

			public void banned(String arg0, String arg1, String arg2) {
			}

			public void joined(String j) {
				System.err.println("JOINED: " + j);
				System.err.println("occupants: "+occupants);
				occupants.add(j);
				Navajo n = postRoomUpdate(occupants);
				tc.injectNavajo("RoomPresence", n);
			}

			public void kicked(String arg0, String arg1, String arg2) {
			}

			public void left(String j) {
				System.err.println("LEFT:" + j);
				occupants.remove(j);
				Navajo n = postRoomUpdate(occupants);
				tc.injectNavajo("RoomPresence", n);

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
			com.dexels.navajo.document.Message m = NavajoFactory.getInstance().createMessage(n, "RoomMembers",
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

	private static void addRoomMember(com.dexels.navajo.document.Message m, String occupant) throws NavajoException {
		com.dexels.navajo.document.Message element = NavajoFactory.getInstance().createMessage(m.getRootDoc(), "RoomMembers",
				com.dexels.navajo.document.Message.MSG_TYPE_ARRAY_ELEMENT);
		m.addMessage(element);
		
		element.addProperty(NavajoFactory.getInstance().createProperty(m.getRootDoc(), "Id", Property.STRING_PROPERTY, occupant, 0, "",
				Property.DIR_OUT, null));
		element.addProperty(NavajoFactory.getInstance().createProperty(m.getRootDoc(), "Name", Property.STRING_PROPERTY, extractName(occupant), 0, "",
				Property.DIR_OUT, null));

	}

	/**
	 * @param occupant
	 * @return
	 */
	private static String extractName(String occupant) {
		return occupant.substring(occupant.lastIndexOf("/")+1,occupant.length());
	}

	
}
