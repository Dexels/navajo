package org.dexels.servlet.smtp;


/**
 * Title:        Toolbox
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


public class SmtpListener {

    Store store = null;
    String protocol = null;
    // Message currentMessage = null;
    Folder currentFolder = null;
    Session session = null;
    Folder[] folders = null;

    public SmtpListener(Session session, Configuration rcp)  throws MessagingException {
        this.session = session;
        if (store == null) {
            store = session.getStore("imap");
            store.connect(rcp.host, rcp.port, rcp.username, rcp.password);
            cacheFolders();
            // setFolder("INBOX");
        }
    }

    public void setFolder(String folderName) throws MessagingException {

        currentFolder = findFolder(folderName);
        if ((!currentFolder.isOpen()) && (currentFolder.exists())
                && ((currentFolder.getType() & Folder.HOLDS_MESSAGES) != 0)) {
            currentFolder.open(Folder.READ_WRITE);
        }
    }

    Folder findFolder(String folderName) {
        for (int i = 0; i < folders.length; ++i) {
            if (folders[i].getName().equals(folderName)) {
                return folders[i];
            }
        }
        return null;
    }

    void cacheFolders() throws MessagingException {
        Folder defaultFolder = store.getDefaultFolder();
        Folder[] childFolders = defaultFolder.list();

        List availableFolders = new ArrayList();

        for (int i = 0; i < childFolders.length; ++i) {
            String folderName = childFolders[i].getName();

            // Ignore "hidden" folders and INBOX.
            // We want to add INBOX to the head of the list.

            if (!folderName.startsWith(".")
                    && !folderName.equalsIgnoreCase("INBOX")) {
                availableFolders.add(childFolders[i]);
            }
        }

        // Sort the folders by name

        Collections.sort(availableFolders, new Comparator() {
                    public int compare(Object o1, Object o2) {
                        return ((Folder) o1).getName().compareToIgnoreCase(((Folder) o2).getName());
                    }
                }
                );

        // Add INBOX as the first folder in the list.
        Folder inbox = defaultFolder.getFolder("INBOX");

        if (inbox.exists()) {
            availableFolders.add(0, inbox);

        }
        folders = (Folder[]) availableFolders.toArray(new Folder[0]);
    }

    private ArrayList receive(Configuration rcp) throws MessagingException, java.io.IOException {

        ArrayList messages = new ArrayList();

        if (store != null) {

            List availableFolders = new ArrayList();

            ArrayList msgList = new ArrayList();

            for (int i = 0; i < folders.length; ++i) {
                String folderName = folders[i].getName();

                System.out.println("SmtpListener() Found " + folderName + " = " + i);
                if (folderName.equals(rcp.folder)) {
                    setFolder(folderName);
                    if (currentFolder != null) // Delete old messages.
                        currentFolder.expunge();
                    int messageCount = currentFolder.getMessageCount();

                    System.out.println("messageCount = " + messageCount);
                    for (int j = 1; j <= messageCount; j++) {
                        Message message = currentFolder.getMessage(j);

                        msgList.add((Message) currentFolder.getMessage(j));
                        boolean not_deleted = true;

                        try {
                            not_deleted = !message.isSet(Flags.Flag.DELETED);
                        } catch (Exception e) {
                            System.out.println("Problem with flags");
                        }
                        if (not_deleted) {
                            String subject = message.getSubject();

                            System.out.println("Scanning mail with subject = " + subject + "(listening for " + rcp.className + ": " + rcp.subject + ")");
                            Address[] fromList = message.getFrom();
                            String from = "";

                            for (int k = 0; k < fromList.length; k++) {
                                Address fromAdr = fromList[k];

                                from = Utils.getEmailAdress(fromAdr.toString());
                            }
                            if (((rcp.subject.equals(subject)
                                    || rcp.subject.equals("")))
                                    && (rcp.sender.equals(from)
                                    || rcp.sender.equals(""))) {
                                System.out.println("subject: " + subject);
                                System.out.println("from: " + from);
                                messages.add(message);
                                if (rcp.deleteMail) {
                                    message.setFlag(Flags.Flag.DELETED, true);
                                }
                            }
                        }
                    }
                }
            }
        }
        return messages;
    }

    private String[] getProtocols() {
        Provider[] providers = session.getProviders();

        List storeProtocols = new ArrayList();

        for (int i = 0; i < providers.length; ++i) {
            if (providers[i].getType() == Provider.Type.STORE) {
                storeProtocols.add(providers[i].getProtocol());
                // System.out.println(providers[i].getProtocol());
            }
        }

        return (String[]) storeProtocols.toArray(new String[0]);
    }

    public ArrayList getMessages(Configuration rcp) {

        ArrayList messages = null;

        try {
            getProtocols();
            messages = receive(rcp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;

    }

}
