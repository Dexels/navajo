package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.*;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.xml.XMLDocumentUtils;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.Context;
import javax.mail.internet.*;
import javax.xml.transform.stream.*;
import com.dexels.navajo.util.*;

/**
 * Title:        Thispas/Navajo Servlets
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Da Vinci Software
 * @author Arjen Schoneveld en Martin Bergman
 * @version      $Id$
 */

/**
 * This business object is used as a mail agent in Navajo Script files.
 */
public class MailMap implements Mappable {

  public String recipients = "";
  public String mailServer = "";
  public String sender = "";
  public String subject = "";
  public String xslFile = "";
  public String text = "";
  public String contentType = "text/plain";

  private String [] recipientArray = null;
  private Navajo doc = null;

  public MailMap() {
  }

  public void kill() {

  }

  public void load(Context context, Parameters parms, Navajo inMessage, Access access, ArrayList keyList) throws MappableException {
    doc = inMessage;
    Util.debugLog("in MailMap load()");
  }

  public void store() throws MappableException, UserException {

    try {

     String result = "";

     Util.debugLog("in MailMap store()");

     // Use Navajo input document if no text specified.
     if (text.equals("")) {
       java.io.StringWriter writer = new java.io.StringWriter();
       XMLDocumentUtils.toXML(doc.getMessageBuffer(), "", "", new StreamResult(writer));
       result = writer.toString();
     } else
        result = text;

     Properties props = System.getProperties();
     props.put("mail.smtp.host", mailServer);
     Session session = Session.getDefaultInstance(props, null);
     javax.mail.Message msg = new MimeMessage(session);
     msg.setFrom(new InternetAddress(sender));

     InternetAddress [] addresses = new InternetAddress[this.recipientArray.length];
     for (int i = 0; i < this.recipientArray.length; i++) {
      addresses[i] = new InternetAddress(this.recipientArray[i]);
     }

     msg.setRecipients(javax.mail.Message.RecipientType.TO, addresses);
     msg.setSubject(subject);
     msg.setSentDate(new java.util.Date());

     // Use stylesheet if specified.
     if (!xslFile.equals("")) {
        java.io.File xsl = new java.io.File(xslFile);
        result = XMLDocumentUtils.transform(doc.getMessageBuffer(), xsl);
     }


      msg.setText(result);

     Transport.send(msg);

     Util.debugLog("Mail has been sent.");
    } catch (Exception e) {
      throw new UserException(-1, e.getMessage());
    }
  }

  public void setRecipients(String s) {

    Util.debugLog("in setRecipients()");
    java.util.StringTokenizer tok = new StringTokenizer(s, ",");

    this.recipientArray = new String[tok.countTokens()];
    int index = 0;
    while (tok.hasMoreTokens()) {
      this.recipientArray[index++] = tok.nextToken();
    }

    this.recipients = s;
  }

  public void setSender(String s) {
    Util.debugLog("in setSender()");
    this.sender = s;
  }

  public void setMailServer(String s) {
    Util.debugLog("in setMailServer()");
    this.mailServer = s;
  }

  public void setSubject(String s) {
    Util.debugLog("in setSubject()");
    this.subject = s;
  }

  public void setContentType(String s) {
    this.contentType = s;
  }

  public void setXslFile(String s) {
    Util.debugLog("in setXslFile()");
    this.xslFile = s;
  }

  public void setText(String s) {
    this.text += s;
    Util.debugLog("text = " + text);
  }
}
