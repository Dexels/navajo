package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.adapter.mailmap.AttachementMap;
import com.dexels.navajo.document.*;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.*;
import com.dexels.navajo.util.*;
import org.w3c.dom.Document;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.datasource.ByteArrayDataSource;


/**
 * Title:        Thispas/Navajo Servlets
 * Description:
 * Copyright:    Copyright (c) 2001, 2002, 2003, 2004, 2005, 2006
 * Company:      Dexels BV
 * @author 	     Arjen Schoneveld en Martin Bergman
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
    //public String encoding = null;
    //public String attachFile = "";
    //public String attachFileName = "";
    //public Binary attachFileContent = null;
    public AttachementMap [] multipleAttachments = null;
    public AttachementMap attachment = null;
    public boolean ignoreFailures = false;

    public boolean relatedMultipart = false;

    private ArrayList attachments = null;
    //private ArrayList attachmentNames = null;

    private String[] recipientArray = null;
    private String[] ccArray = null;
    private String[] bccArray = null;
    private Navajo doc = null;
    private String failure = "";

    public MailMap() {}

    public void kill() {}

    public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException {
        doc = inMessage;
        Util.debugLog("in MailMap load()");
    }

//    public void setEncoding(String s) {
//    	this.encoding = s;
//    }
    
//    public void setAttachFileName(String name) {
//    	
//      if ( attachment == null ) {
//    	  attachment = new AttachementMap
//      }
//      if (attachmentNames == null) {
//        attachmentNames = new ArrayList();
//      }
//      attachmentNames.add(name);
//    }

//    public void setAttachFile(String fileName) {
//      if (attachments == null) {
//        attachments = new ArrayList();
//      }
//      attachments.add(fileName);
//    }
//
//    public void setAttachFileContent(Binary content) {
//      if (attachments == null) {
//        attachments = new ArrayList();
//      }
//      attachments.add(content);
//    }


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
              Multipart multipart = (relatedMultipart ? new MimeMultipart( "related") : new MimeMultipart() );
              BodyPart textBody = new MimeBodyPart();
              textBody.setContent(result, contentType);

              multipart.addBodyPart(textBody);

              if (attachments != null) {
                for (int i = 0; i < attachments.size(); i++) {
                  AttachementMap am = (AttachementMap) attachments.get(i);
                  String file = am.getAttachFile();
                  String userFileName = am.getAttachFileName();
                  Binary content = am.getAttachFileContent();
                  String encoding = am.getEncoding();
                  BodyPart bp = new MimeBodyPart();
                  
                  if (file != null) {
                    if ( userFileName  == null ) {
                    	userFileName = file;
                    }
                    FileDataSource fileDatasource = new FileDataSource(file);
                    bp.setDataHandler(new DataHandler(fileDatasource));
                  } else if ( content != null ) {
                   
                    ByteArrayDataSource byteArraySource = new ByteArrayDataSource(content.getData(),
                        ( content.getMimeType().startsWith("unknown") ? "text/plain" : content.getMimeType() ), "");
                    DataHandler dh = new DataHandler(byteArraySource);
                    bp.setDataHandler(dh);
                    
                    if ( encoding != null ) {
                    	bp.setHeader("Content-Transfer-Encoding", encoding);
                    	encoding = null;
                    }
                    
                  }
                  
                  bp.setFileName(userFileName);
                  if (relatedMultipart) {
                  	bp.setHeader("Content-ID", "<attach-nr-"+i+">");
                  }
                  multipart.addBodyPart(bp);
                }
              }

              msg.setContent(multipart);

            }
            Transport.send(msg);

        } catch (Exception e) {
          if(ignoreFailures){
            System.err.println("MailMap: Failure logged: " + e.getMessage());
            failure = e.getMessage();
          }else{
            e.printStackTrace();
            throw new UserException( -1, e.getMessage());
          }
        }
    }

    public String getFailure(){
      return failure;
    }

    public void setIgnoreFailures(boolean b){
      ignoreFailures = b;
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
    
    if (attachments == null) {
      attachments = new ArrayList();
    }
  
    for (int i = 0; i < c.length; i++) {
       attachments.add(c[i]);
     }
  }

  public void setRelatedMultipart(boolean b) {
  	this.relatedMultipart = b;
  }

  public void setAttachment(AttachementMap m) {
	  System.err.println(">>>>>>>>>>>>>>>>>>>>>> in setAttachment");
	  this.attachment = m;
	  if (attachments == null) {
		  attachments = new ArrayList();
	  }
	  attachments.add(m);
  }

}
