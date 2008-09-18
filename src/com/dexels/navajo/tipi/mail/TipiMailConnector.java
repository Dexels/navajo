package com.dexels.navajo.tipi.mail;

import java.io.*;
import java.util.*;

import javax.mail.*;
import javax.mail.event.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.connectors.*;
import com.dexels.navajo.tipi.internal.*;

public class TipiMailConnector extends TipiBaseConnector implements TipiConnector {
	private String host = "85.92.144.24";
	private String username = "flyaruu@dexels.com";
	private String password = "xxxxxxxx";
	private Session session;
	private ConnectionListener myConnectionListener = null;
	private Store store = null;
	private FolderListener myFolderListener = null;
	private StoreListener myStoreListener = null;
	private String mailMode = "imap";

	public void doTransaction(Navajo n, String service, String destination) throws TipiBreakException, TipiException {
		if (service == null) {
			service = "InitMail";
		}
		if (service.equals("InitMail")) {
			injectNavajo(service, createMailInit());
		}
		if (service.equals("InitGetFolders")) {
			injectNavajo(service, createFolderNavajo());

		}
		if (service.equals("InitGetMessages")) {
			injectNavajo(service, createInitGetMessages());
		}

		if (service.equals("GetInboxMessages")) {
			injectNavajo(service, createMessagesNavajo(null));
		}
		if (service.equals("GetMessages")) {
			injectNavajo(service, createMessagesNavajo(n));
		}
		if (service.equals("InitGetDefaultMessages")) {
			injectNavajo(service, createMessagesNavajo(n));
		}
		if (service.equals("GetMessage")) {
			injectNavajo(service, createGetMessage(n));
		}
	}

	public String getDefaultEntryPoint() {
		return "InitMail";
	}

	private Navajo createInitGetMessages() throws TipiException {

		try {
			Navajo n = NavajoFactory.getInstance().createNavajo();
			Message m = NavajoFactory.getInstance().createMessage(n, "Folder");
			n.addMessage(m);
			Property p = NavajoFactory.getInstance().createProperty(n, "Name", Property.STRING_PROPERTY, "", 0, "", Property.DIR_IN);
			m.addProperty(p);

			Property start = NavajoFactory.getInstance().createProperty(n, "Start", Property.INTEGER_PROPERTY, "", 0, "", Property.DIR_IN);
			m.addProperty(start);
			Property end = NavajoFactory.getInstance().createProperty(n, "End", Property.INTEGER_PROPERTY, "", 0, "", Property.DIR_IN);
			m.addProperty(end);

			Property q = NavajoFactory.getInstance().createProperty(n, "MessageNumber", Property.INTEGER_PROPERTY, "", 0, "",
					Property.DIR_IN);
			m.addProperty(q);
			n.addMethod(NavajoFactory.getInstance().createMethod(n, "GetMessages", null));
			return n;
		} catch (NavajoException e) {
			throw new TipiException("Can not get init messages", e);
		}

	}

	private Navajo createMailInit() {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		try {
			n.addMethod(NavajoFactory.getInstance().createMethod(n, "InitGetFolders", null));
			n.addMethod(NavajoFactory.getInstance().createMethod(n, "GetInboxMessages", null));

		} catch (NavajoException e) {
			e.printStackTrace();
		}
		return n;
	}

	private Navajo createGetMessage(Navajo n) throws TipiException {
		String name = null;
		try {
			n.write(System.err);
		} catch (NavajoException e) {
			e.printStackTrace();
		}
		name = (String) n.getProperty("/Folder/Name").getTypedValue();

		if ("POP3".equalsIgnoreCase(mailMode)) {
			name = "INBOX";
		}
		int messageNumber = (Integer) n.getProperty("/Folder/MessageNumber").getTypedValue();
		System.err.println("Getting message: " + name + " nr. " + messageNumber);
		return createSingleMessageNavajo(name, messageNumber);
	}

	private Navajo createSingleMessageNavajo(String name, int messageNumber) throws TipiException {
		Folder fff;
		try {
			fff = store.getFolder(name);

		} catch (MessagingException e) {
			throw new TipiException("Error opening mailbox: " + name, e);
		}
		if (fff == null) {
			throw new TipiException("Mailbox not found: " + name);
		}
		try {
			fff.open(Folder.READ_ONLY);
			javax.mail.Message m = fff.getMessage(messageNumber);
			Navajo myNavajo = NavajoFactory.getInstance().createNavajo();
			Message mm = addMessageProperties(m, myNavajo);
			myNavajo.addMessage(mm);
			//System.err.println("Content class: "+m.getInputStream().getClass()
			// );
			Binary b = new Binary(m.getInputStream(), false);
			addProperty(mm, "Content", b, name);
			// addProperty(current, "ContentType",
			// currentImapMessage.getContentType(), Property.STRING_PROPERTY);
			myNavajo.write(System.err);
			fff.close(false);
			return myNavajo;
		} catch (MessagingException e) {
			throw new TipiException("Error getting message: " + messageNumber + " from box: " + name, e);
		} catch (NavajoException e) {
			throw new TipiException("Error getting message: " + messageNumber + " from box: " + name, e);
		} catch (IOException e) {
			throw new TipiException("Error getting message: " + messageNumber + " from box: " + name, e);
		}
	}

	private Navajo createMessagesNavajo(Navajo n) throws TipiException {
		String name = null;

		System.err.println("F0lder Name: " + name);
		if (n == null) {
			name = "INBOX";
		} else {
			name = (String) n.getProperty("/Folder/Name").getTypedValue();
		}

		if ("POP3".equalsIgnoreCase(mailMode)) {
			name = "INBOX";
		}
		Navajo myNavajo = NavajoFactory.getInstance().createNavajo();

		if (store == null) {
			try {
				connect();
			} catch (MessagingException e) {
				throw new TipiException("WTF? " + e.getMessage(), e);
			}
		}
		Folder fff;
		try {
			fff = store.getFolder(name);
		} catch (MessagingException e) {
			throw new TipiException("Error opening mailbox: " + n, e);
		}
		if (fff == null) {
			throw new TipiException("Mailbox not found: " + n);
		}

		addFolderToNavajo(fff, myNavajo, "MailBox", n);
		return myNavajo;
	}

	protected void setComponentValue(String name, Object object) {
		if (name.equals("server")) {
			host = (String) object;
		}
		if (name.equals("username")) {
			username = (String) object;
		}
		if (name.equals("password")) {
			password = (String) object;
		}
		if (name.equals("mailMode")) {
			mailMode = (String) object;
		}

		super.setComponentValue(name, object);
	}

	public String getConnectorId() {
		return "mail";
	}

	@Override
	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
		if (name.equals("connect")) {
			try {
				connect();
			} catch (MessagingException e) {
				e.printStackTrace();
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("message", "Error connecting to server: " + host);
				m.put("exception", "Error connecting to server: " + e.getMessage());
				try {
					performTipiEvent("onMailError", m, false);
				} catch (TipiException e1) {
					e1.printStackTrace();
				}
			}
		}
		if (name.equals("disconnect")) {
			try {
				disconnect();
			} catch (MessagingException e) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("message", "Error disconnecting from server: " + host);
				m.put("exception", "Error disconnecting from server: " + e.getMessage());
				try {
					performTipiEvent("onMailError", m, false);
				} catch (TipiException e1) {
					e1.printStackTrace();
				}
			}
		}

		super.performComponentMethod(name, compMeth, event);
	}

	public void connect() throws MessagingException {
		Properties props = new Properties();

		session = Session.getDefaultInstance(props, null);
		store = session.getStore(mailMode);
		store.connect(host, username, password);
		myConnectionListener = new ConnectionListener() {
			public void closed(ConnectionEvent e) {
				try {
					performTipiEvent("onConnectionClosed", null, false);
					store = null;
				} catch (TipiException e1) {
					e1.printStackTrace();
				}
			}

			public void disconnected(ConnectionEvent arg0) {
				try {
					performTipiEvent("onConnectionLost", null, false);
					store = null;
				} catch (TipiException e1) {
					e1.printStackTrace();
				}
			}

			public void opened(ConnectionEvent arg0) {
				try {
					performTipiEvent("onConnectionCreated", null, false);
				} catch (TipiException e1) {
					e1.printStackTrace();
				}
			}
		};
		store.addConnectionListener(myConnectionListener);
		myFolderListener = new FolderListener() {

			public void folderCreated(FolderEvent arg0) {
				System.err.println("Folder created");
			}

			public void folderDeleted(FolderEvent arg0) {
				System.err.println("Folder deleted");
			}

			public void folderRenamed(FolderEvent arg0) {
				System.err.println("Folder renamed");
			}

		};
		store.addFolderListener(myFolderListener);

		myStoreListener = new StoreListener() {

			public void notification(StoreEvent ee) {
				System.err.println("Store notification: " + ee.getMessage() + " " + ee.getMessageType());
			}

		};
		store.addStoreListener(myStoreListener);

	}

	public void disconnect() throws MessagingException {
		if (store != null) {
			store.removeConnectionListener(myConnectionListener);
			store.removeFolderListener(myFolderListener);
			store.removeStoreListener(myStoreListener);
			// Close connection
			// folder.close(false);
			store.close();
			store = null;
		}
	}

	public Navajo createFolderNavajo() throws TipiException {
		Navajo myNavajo;
		if (store == null) {
			try {
				connect();
			} catch (MessagingException e) {
				throw new TipiException("Error connecting: " + e.getMessage(), e);
			}
		}
		try {
			Folder g = store.getDefaultFolder();
			myNavajo = NavajoFactory.getInstance().createNavajo();
			Message folderMessage = NavajoFactory.getInstance().createMessage(myNavajo, "Folders", Message.MSG_TYPE_ARRAY);
			myNavajo.addMessage(folderMessage);
			Folder[] fff = g.list();
			for (int i = 0; i < fff.length; i++) {
				addFolderMessage(folderMessage, fff[i]);
			}
			return myNavajo;
		} catch (Exception e) {
			e.printStackTrace();
			throw new TipiException("Error getting folders: ", e);
		}
		// recursiveShow(g);
	}

	private void addFolderMessage(Message folderMessage, Folder folder) throws NavajoException, MessagingException {
		Message element = NavajoFactory.getInstance().createMessage(folderMessage.getRootDoc(), "Folders", Message.MSG_TYPE_ARRAY_ELEMENT);
		folderMessage.addMessage(element);
		addFolderToMessage(element, folder);
	}

	public void testMessage() throws MessagingException, TipiBreakException, TipiException {
		connect();
		doTransaction("InitGetFolders");
		doTransaction("InitGetDefaultMessages");

		disconnect();

	}

	private void addFolderToNavajo(Folder f, Navajo myNavajo, String folderName, Navajo inputNavajo) throws TipiException {
		try {
			Message folderMessage = NavajoFactory.getInstance().createMessage(myNavajo, folderName);
			f.open(Folder.READ_ONLY);
			myNavajo.addMessage(folderMessage);
			addFolderToMessage(folderMessage, f);
			javax.mail.Message message[] = f.getMessages();
			Message mailMessages = NavajoFactory.getInstance().createMessage(myNavajo, "Mail", Message.MSG_TYPE_ARRAY);
			folderMessage.addMessage(mailMessages);
			inputNavajo.write(System.err);
			Integer start = (Integer) inputNavajo.getProperty("Folder/Start").getTypedValue();
			Integer end = (Integer) inputNavajo.getProperty("Folder/End").getTypedValue();
			int startInt = 0;
			if (start != null) {
				startInt = start;
			}
			int endInt = message.length;
			if (end != null) {
				endInt = end;
			}
			for (int i = startInt; i < endInt; i++) {
				addMessages(mailMessages, message[i]);

			}
			System.err.println("Start: " + startInt + " end: " + endInt);
			f.close(false);
		} catch (Exception e) {
			throw new TipiException("Error getting messages from folder: " + folderName + " problem: " + e.getMessage(), e);
		}
	}

	private void addMessages(Message imapFolder, javax.mail.Message currentImapMessage) throws NavajoException, MessagingException {
		Navajo n = imapFolder.getRootDoc();
		Message current = addMessageProperties(currentImapMessage, n);

		imapFolder.addMessage(current);

	}

	private Message addMessageProperties(javax.mail.Message currentImapMessage, Navajo n) throws NavajoException, MessagingException {
		Message current = NavajoFactory.getInstance().createMessage(n, "Mail", Message.MSG_TYPE_ARRAY_ELEMENT);
		addProperty(current, "Subject", currentImapMessage.getSubject(), Property.STRING_PROPERTY);
		addProperty(current, "ContentType", currentImapMessage.getContentType(), Property.STRING_PROPERTY);
		addProperty(current, "Description", currentImapMessage.getDescription(), Property.STRING_PROPERTY);
		addProperty(current, "Disposition", currentImapMessage.getDisposition(), Property.STRING_PROPERTY);
		addProperty(current, "FileName", currentImapMessage.getFileName(), Property.STRING_PROPERTY);
		addProperty(current, "Date", currentImapMessage.getSentDate(), Property.DATE_PROPERTY);
		addProperty(current, "MessageNumber", currentImapMessage.getMessageNumber(), Property.INTEGER_PROPERTY);

		addAddressProperty(current, "From", currentImapMessage.getFrom());
		addAddressProperty(current, "ReplyTo", currentImapMessage.getReplyTo());
		addAddressProperty(current, "To", currentImapMessage.getRecipients(javax.mail.Message.RecipientType.TO));
		addAddressProperty(current, "Cc", currentImapMessage.getRecipients(javax.mail.Message.RecipientType.CC));
		addAddressProperty(current, "Bcc", currentImapMessage.getRecipients(javax.mail.Message.RecipientType.BCC));
		return current;
	}

	private void addAddressProperty(Message current, String name, Address[] addressList) throws NavajoException {
		if (addressList == null) {
			return;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < addressList.length; i++) {
			sb.append(addressList[i].toString());
			if (i != addressList.length - 1) {
				sb.append(",");
			}
		}
		addProperty(current, name, sb.toString(), name);
	}

	private void addFolderToMessage(Message folderMessage, Folder c) throws NavajoException, MessagingException {
		addProperty(folderMessage, "FolderName", c.getFullName(), Property.STRING_PROPERTY);
		addProperty(folderMessage, "MessageCount", "" + c.getMessageCount(), Property.INTEGER_PROPERTY);
		addProperty(folderMessage, "NewMessageCount", "" + c.getNewMessageCount(), Property.INTEGER_PROPERTY);
		addProperty(folderMessage, "UnreadMessageCount", "" + c.getUnreadMessageCount(), Property.INTEGER_PROPERTY);
		addProperty(folderMessage, "", c.getName(), Property.STRING_PROPERTY);
	}

	private void addProperty(Message m, String name, Object value, String type) throws NavajoException {
		addProperty(m, name, value, type, Property.DIR_OUT);
	}

	private void addProperty(Message m, String name, Object value, String type, String direction) throws NavajoException {
		Navajo n = m.getRootDoc();
		Property p = NavajoFactory.getInstance().createProperty(n, name, type, null, 0, null, Property.DIR_IN);
		p.setAnyValue(value);
		m.addProperty(p);
	}

	public static void main(String[] args) throws MessagingException, TipiBreakException, TipiException {
		TipiMailConnector ttt = new TipiMailConnector();
		ttt.testMessage();
	}

	public Set<String> getEntryPoints() {
		Set<String> s = new HashSet<String>();
		s.add("InitMail");
		return s;
	}
}
