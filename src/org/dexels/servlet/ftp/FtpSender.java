package org.dexels.servlet.ftp;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */
import java.io.*;
import javax.xml.transform.stream.StreamResult;

import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.enterprisedt.net.ftp.FTPConnectMode;

import com.dexels.navajo.document.*;
import com.dexels.navajo.xml.*;


public class FtpSender {

    public final static int PORT = 21;

    public FtpSender() {}

    public void send(RecipientData rcp, Navajo doc, String label) {
        try {
            FTPClient ftp = new FTPClient(rcp.host, rcp.port);

            ftp.login(rcp.username, rcp.password);
            ftp.chdir(rcp.path);
            java.io.ByteArrayOutputStream stream = new java.io.ByteArrayOutputStream();

            XMLDocumentUtils.toXML(doc.getMessageBuffer(), null, null, new StreamResult(stream));

            ftp.put(stream.toByteArray(), label);
            ftp.quit();
        } catch (FTPException ftpe) {
            ftpe.printStackTrace();
            System.err.println(ftpe.getMessage());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (NavajoException xe) {
            xe.printStackTrace();
        }
    }

    public static void main(String args[]) throws Exception {
        Navajo doc = new Navajo();
        Message m = Message.create(doc, "berichtje");

        doc.addMessage(m);
        Property p = Property.create(doc, "propje", Property.STRING_PROPERTY, "10", 10, "Beschrijving", Property.DIR_IN);

        m.addProperty(p);
        RecipientData d = new RecipientData();
        FtpSender sender = new FtpSender();

        d.host = "10.0.0.130";
        d.password = "p4137677";
        d.username = "arjen";
        d.path = "/home/arjen";
        d.port = 21;
        sender.send(d, doc, "navajo.tml");
    }
}
