package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.*;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import java.util.*;
import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.*;
import javax.xml.transform.stream.*;
import com.dexels.navajo.util.*;
import org.w3c.dom.Document;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.datasource.ByteArrayDataSource;


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
    public String cc = "";
    public String bcc = "";
    public String xslFile = "";
    public String text = "";
    public String contentType = "text/plain";
    public String attachFile = "";
    public String attachFileName = "";
    public Binary attachFileContent = null;
    public AttachementMap [] multipleAttachments = null;

    private ArrayList attachments = null;
    private ArrayList attachmentNames = null;

    private String[] recipientArray = null;
    private String[] ccArray = null;
    private String[] bccArray = null;
    private Navajo doc = null;

    public MailMap() {}

    public void kill() {}

    public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException {
        doc = inMessage;
        Util.debugLog("in MailMap load()");
    }

    public void setAttachFileName(String name) {
      if (attachmentNames == null) {
        attachmentNames = new ArrayList();
      }
      attachmentNames.add(name);
    }

    public void setAttachFile(String fileName) {
      if (attachments == null) {
        attachments = new ArrayList();
      }
      attachments.add(fileName);
    }

    public void setAttachFileContent(Binary content) {
      if (attachments == null) {
        attachments = new ArrayList();
      }
      attachments.add(content);
    }


    public void store() throws MappableException, UserException {

        try {

            String result = "";

            System.err.println("in MailMap store()");

            // Use Navajo input document if no text specified.
            if (text.equals("")) {
                java.io.StringWriter writer = new java.io.StringWriter();
                doc.write(writer);
                result = writer.toString();
            } else
                result = text;

            Properties props = System.getProperties();

            props.put("mail.smtp.host", mailServer);
            Session session = Session.getDefaultInstance(props);
            javax.mail.Message msg = new MimeMessage(session);

            System.err.println("Created mime message: " + msg);

            msg.setFrom(new InternetAddress(sender));

            InternetAddress[] addresses = new InternetAddress[this.recipientArray.length];

            for (int i = 0; i < this.recipientArray.length; i++) {
                addresses[i] = new InternetAddress(this.recipientArray[i]);
                System.err.println("Set recipient " + i + ": " + this.recipientArray[i]);
            }

            msg.setRecipients(javax.mail.Message.RecipientType.TO, addresses);

            if (ccArray != null) {
              InternetAddress[] extra = new InternetAddress[this.ccArray.length];
              for (int i = 0; i < this.ccArray.length; i++) {
                extra[i] = new InternetAddress(this.ccArray[i]);
                System.err.println("Set cc " + i + ": " + this.ccArray[i]);
              }
              msg.setRecipients(javax.mail.Message.RecipientType.CC, extra);
            }

            if (bccArray != null) {
              InternetAddress[] extra = new InternetAddress[this.bccArray.length];
              for (int i = 0; i < this.bccArray.length; i++) {
                extra[i] = new InternetAddress(this.bccArray[i]);
                System.err.println("Set cc " + i + ": " + this.bccArray[i]);
              }
              msg.setRecipients(javax.mail.Message.RecipientType.BCC, extra);
            }


            msg.setSubject(subject);
            msg.setSentDate(new java.util.Date());

            // Use stylesheet if specified.
            if (!xslFile.equals("")) {
                java.io.File xsl = new java.io.File(xslFile);
                result = XMLDocumentUtils.transform((Document) doc.getMessageBuffer(), xsl);
            }

            if (attachments == null && contentType.equals("text/plain")) {
              msg.setText(result);
            }
            else {
              Multipart multipart = new MimeMultipart();
              BodyPart textBody = new MimeBodyPart();
              textBody.setContent(result, contentType);

              multipart.addBodyPart(textBody);

              if (attachments != null) {
                for (int i = 0; i < attachments.size(); i++) {
                  Object file = attachments.get(i);
                  BodyPart bp = new MimeBodyPart();
                  String fileName = "unknown";
                  if (file instanceof String) {
                    fileName = (String) file;
                    FileDataSource fileDatasource = new FileDataSource(fileName);
                    bp.setDataHandler(new DataHandler(fileDatasource));
                  } else if (file instanceof Binary) {
                    Binary content = (Binary) file;
                    System.err.println("MIMETYPE of attchement is " + content.getMimeType());
                    ByteArrayDataSource byteArraySource = new ByteArrayDataSource(content.getData(), content.getMimeType(), "");
                    bp.setDataHandler(new DataHandler(byteArraySource));
                  }
                  String userFileName = ( (attachmentNames != null) && i < attachmentNames.size() &&
                                           attachmentNames.get(i) != null) ? (String) attachmentNames.get(i) : fileName;
                  System.err.println("userFileName = " + userFileName);
                  bp.setFileName(userFileName);
                  multipart.addBodyPart(bp);
                }
              }
              msg.setContent(multipart);

            }

            System.err.println("About to send....");
            Transport.send(msg);

            System.err.println("Mail has been sent.");
        } catch (Exception e) {
            e.printStackTrace();
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

    public void setBcc(String bcc) {
    this.bcc = bcc;
    Util.debugLog("in setBcc()");
    java.util.StringTokenizer tok = new StringTokenizer(bcc, ",");

    this.bccArray = new String[tok.countTokens()];
    int index = 0;

    while (tok.hasMoreTokens()) {
      this.bccArray[index++] = tok.nextToken();
   }
  }

  public void setCc(String cc) {
    this.cc = cc;
    Util.debugLog("in setCc()");
    java.util.StringTokenizer tok = new StringTokenizer(cc, ",");

    this.ccArray = new String[tok.countTokens()];
    int index = 0;

    while (tok.hasMoreTokens()) {
        this.ccArray[index++] = tok.nextToken();
    }
  }
  public void setMultipleAttachments(AttachementMap[] c) {
    this.multipleAttachments = c;
    if (attachments == null) {
      attachments = new ArrayList();
    }
    if (attachmentNames == null) {
      attachmentNames = new ArrayList();
    }
    for (int i = 0; i < multipleAttachments.length; i++) {
      Object o = multipleAttachments[i].getAttachFileContent();
      if (o == null) {
        o = multipleAttachments[i].getAttachFile();
       }
       attachments.add(o);
       attachmentNames.add(multipleAttachments[i].getAttachFileName());
     }

  }
}
