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
import java.util.*;
import javax.xml.transform.stream.StreamResult;

import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.enterprisedt.net.ftp.FTPConnectMode;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.w3c.dom.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.xml.*;

public class FtpListener  {

  private int interval;
  public FtpListener(int interval) {
    this.interval = interval;
  }

  /**
   * Start main thread.
   * Om de x secs. contact maken met FTP server om nieuwe berichten op te halen.
   * Vervolgens middels HttpClient bericht versturen naar PostmanServlet.
   * Ontvangen Navajo dispatchen naar NavajoSender
   */

   public Navajo get(RecipientData rcp, String label) {

      Document xml = null;
      try {
         FTPClient ftp = new FTPClient(rcp.host, rcp.port);
         ftp.login(rcp.username, rcp.password);
         ftp.chdir(rcp.path);
         byte [] input = ftp.get(label);
         java.io.ByteArrayInputStream inputStream = new java.io.ByteArrayInputStream(input);

         xml = XMLDocumentUtils.createDocument(inputStream, false);

         ftp.rename(label, label+".rec");
         ftp.quit();
      } catch (FTPException fpte) {
        fpte.printStackTrace();
        return null;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }

      return new Navajo(xml, "");
   }

   public Iterator getMessages(RecipientData rcp) {
      return null;
   }

   public static void main(String args[]) throws Exception {

    while (true) {
      FtpListener l = new FtpListener(10000);
      RecipientData rcp = new RecipientData();
      rcp.host = "10.0.0.130";
      rcp.port = 21;
      rcp.path = "/home/arjen";
      rcp.username = "arjen";
      rcp.password = "p4137677";
      Navajo doc = l.get(rcp, "navajo2.tml");
      if (doc != null) {
      System.out.println("Received:");

      XMLDocumentUtils.toXML(doc.getMessageBuffer(), null, null, new StreamResult(System.err));

      }
      System.out.println("----");
      Thread.sleep(l.interval);
    }
   }
}