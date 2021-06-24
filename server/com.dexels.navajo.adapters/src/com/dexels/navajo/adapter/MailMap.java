/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.adapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.dexels.navajo.adapter.mailmap.AttachementMap;
import com.dexels.navajo.adapter.mailmap.AttachmentMapInterface;
import com.dexels.navajo.datasource.BinaryDataSource;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.DependentResource;
import com.dexels.navajo.mapping.GenericDependentResource;
import com.dexels.navajo.mapping.HasDependentResources;
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Debugable;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.enterprise.queue.RequestResponseQueueFactory;
import com.dexels.navajo.util.AuditLog;

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
public class MailMap implements MailMapInterface, Mappable,
		HasDependentResources, Debugable,
		com.dexels.navajo.server.enterprise.queue.Queuable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6829674936474299750L;

	public String recipients = "";
	public String mailServer = "";
	public String sender = "";
	public String subject = "";
	public String cc = "";
	public String bcc = "";
	public String xslFile = "";
	public String text = "";
	public String contentType = "text/plain";
	
	private Integer port = null;

	public String smtpUser = "";
	public String smtpPass = "";

	public AttachementMap[] multipleAttachments = null;
	public AttachementMap attachment = null;
	public boolean ignoreFailures = false;

	public boolean relatedMultipart = false;
	private transient List<AttachementMap> attachments = null;
	private String[] recipientArray = null;
	private String[] ccArray = null;
	private String[] bccArray = null;
	private Navajo doc = null;
	private StringBuilder failureBuffer = new StringBuilder();

	public int retries = 0;
	public int maxRetries = 100;
	public boolean queuedSend = false;
	public long waitUntil = 0;
	public static int maxRunningInstances = -1;

	private Navajo myNavajo;
	private Access myAccess;
	
	private static final Logger logger = LoggerFactory.getLogger(MailMap.class);
	
	private boolean useEncryption = false;

	private boolean debug = false;

	public MailMap() {
	}

	@Override
	public void kill() {
	}

	@Override
	public void load(Access access) throws MappableException {
		if (access != null) {
			doc = access.getInDoc();
			myNavajo = access.getInDoc();
			myAccess = access;
		}
	}

	public Binary[] getAttachments() {
		Binary[] bins = new Binary[attachments.size()];
		bins = attachments.toArray(bins);
		return bins;
	}

	@Override
	public void store() throws MappableException, UserException {
		if (!queuedSend) {
			sendMail();
		} else {
			try {
				RequestResponseQueueFactory.getInstance().send(this, 100);
			} catch (Exception e) {
				AuditLog.log("MailMap", e.getMessage(),e , Level.WARNING,
						myAccess.accessID);
				logger.error("Error: sending request (?)",e);
			}
		}
	}

	@Override
	public boolean send() {
		retries++;
		try {
			sendMail();
		} catch (Exception e) {
			if (myAccess != null) {
				AuditLog.log("MailMap", e.getMessage(),e, Level.WARNING,
						myAccess.accessID);
				myAccess.setException(e);
			}
			return false;
		}
		return true;
	}

    /**
     * Converts each e-mail string of an array of strings to their corresponding
     * InternetAddreses and returns an array with InternetAddreses
     * 
     * If ignoreFailures is set, then no exceptions will be thrown
     */
    private InternetAddress[] convertToInternetAddresses(String[] strAddreses) throws UserException {

        ArrayList<InternetAddress> addresses = new ArrayList<>();

        for (int i = 0; i < strAddreses.length; i++) {
            try {
                addresses.add(new InternetAddress(strAddreses[i]));
            } catch (Exception e) {
                logger.warn("Invalid e-mail address was provided : {}", strAddreses[i]);
                if (ignoreFailures) {
                    AuditLog.log("MailMap", e.getMessage(), e, Level.WARNING, myAccess.accessID);
                    logger.warn("ingoreFailures flag is set. Ignoring the invalid e-mail address.");
                    failureBuffer.append(e.getMessage());
                    failureBuffer.append(";");
                } else {
                    AuditLog.log("MailMap", e.getMessage(), e, Level.SEVERE, myAccess.accessID);
                    throw new UserException(-1, e.getMessage(), e);
                }
            }
        }

        InternetAddress[] addr = new InternetAddress[addresses.size()];
        for (int i = 0; i < addresses.size(); i++) {
            addr[i] = addresses.get(i);
        }

        return addr;
    }

	private final void sendMail() throws UserException {
	    final ClassLoader current = Thread.currentThread().getContextClassLoader();

		retries++;

		try {
	        Thread.currentThread().setContextClassLoader(javax.mail.Session.class.getClassLoader());

			String result = "";

			result = text;

			Session session = createSession();
			javax.mail.Message msg = new MimeMessage(session);

			if (sender == null || "".equals(sender)) {
				throw new UserException(-1, "Error: Required sender address not set!");
			}
			msg.setFrom(new InternetAddress(sender));

            InternetAddress[] recipients = convertToInternetAddresses(this.recipientArray);
            msg.setRecipients(javax.mail.Message.RecipientType.TO, recipients);

            InternetAddress[] ccrecipients = null;
			if (ccArray != null) {
                ccrecipients = convertToInternetAddresses(this.ccArray);
                msg.setRecipients(javax.mail.Message.RecipientType.CC, ccrecipients);
			}

            InternetAddress[] bccrecipients = null;
			if (bccArray != null) {
                bccrecipients = convertToInternetAddresses(this.bccArray);
                msg.setRecipients(javax.mail.Message.RecipientType.BCC, bccrecipients);
			}

			msg.setSubject(subject);
			msg.setSentDate(new java.util.Date());

			// Use stylesheet if specified.
			if (!xslFile.equals("")) {
				java.io.File xsl = new java.io.File(xslFile);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				doc.write(bos);
				bos.close();
				ByteArrayInputStream bis = new ByteArrayInputStream(
						bos.toByteArray());
				Document dc = XMLDocumentUtils.createDocument(bis, false);
				bis.close();
				result = XMLDocumentUtils.transform(dc, xsl);
			}

			if (attachments == null && contentType.equals("text/plain")) {
				msg.setText(result);
			} else if ( attachments == null || attachments.isEmpty()) {
				msg.setContent( result, contentType ); 
			} else {
				Multipart multipart = (relatedMultipart ? new MimeMultipart("related") : new MimeMultipart());
				BodyPart textBody = new MimeBodyPart();
				textBody.setContent(result, contentType);

				multipart.addBodyPart(textBody);

				if (attachments != null) {
					for (int i = 0; i < attachments.size(); i++) {
						AttachmentMapInterface am = attachments.get(i);
						String file = am.getAttachFile();
						String userFileName = am.getAttachFileName();
						Binary content = am.getAttachFileContent();
						String encoding = am.getEncoding();
						
						MimeBodyPart bp = new MimeBodyPart();

						if (file != null) {
							if (userFileName == null) {
								userFileName = file;
							}
							FileDataSource fileDatasource = new FileDataSource( file);
							bp.setDataHandler(new DataHandler(fileDatasource));
							
						} else if (content != null) {

							BinaryDataSource bds = new BinaryDataSource(content, "");
							DataHandler dh = new DataHandler(bds);
							bp.setDataHandler(dh);
							bp.setFileName(userFileName);
							if (encoding != null) {
								bp.setHeader("Content-Transfer-Encoding", encoding);
								encoding = null;
							}
						}

						
						if (relatedMultipart) {
							bp.setHeader("Content-ID", "<attach-nr-" + i + ">");
						}
						bp.getFileName();

						// iPhone headers
						bp.setDisposition(am.getAttachContentDisposition());
						
						multipart.addBodyPart(bp);
					}
				}
				msg.setContent(multipart);
			}
            logger.info("Sending mail to: {}, cc: {}, bcc: {} with subject: {}", Arrays.toString(recipients),
                    ccrecipients != null ? Arrays.toString(ccrecipients) : "[]",
                    bccrecipients != null ? Arrays.toString(bccrecipients) : "[]", subject);
			Transport.send(msg);
		} catch (Exception e) {
		    logger.error("Exception on sending mail!", e);
		    if (ignoreFailures) {
                AuditLog.log("MailMap", e.getMessage(), e, Level.WARNING, myAccess.accessID);
                logger.warn("ingoreFailures flag is set. Ignoring the invalid e-mail address.");
                failureBuffer.append(e.getMessage());
                failureBuffer.append(";");
            } else {
                AuditLog.log("MailMap", e.getMessage(), e, Level.SEVERE, myAccess.accessID);
                throw new UserException(-1, e.getMessage(), e);
            }
		} finally {
            Thread.currentThread().setContextClassLoader( current );
        }
	}

	private Session createSession() {
		Properties props = new Properties();
		props.putAll( System.getProperties());
		props.put("mail.smtp.host", getMailServer());
		String actualport = port == null ? null: port.toString(); 
		if (actualport == null || actualport.equals("")) {
			actualport = useEncryption ?  "465" : "25";
		}
		if (getSmtpUser() != null && !"".equals(getSmtpUser())) {
			// Use auth
			props.put("mail.smtp.auth", "true");
			if(this.debug) {
				props.put("mail.debug", "true");
			}
			if (useEncryption) {
				logger.info("Using encrypt + auth. ");
				props.put("mail.smtp.port", actualport);
				props.put("mail.smtp.socketFactory.port", actualport);
				props.put("mail.smtp.socketFactory.class",
						"javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.socketFactory.fallback", "false");
			}
			Authenticator auth = new SMTPAuthenticator();
			return Session.getInstance(props, auth);
		} else {
			props.put("mail.smtp.port", actualport);
			return Session.getInstance(props);
		}
	}

	/**
	 * SimpleAuthenticator is used to do simple authentication when the SMTP
	 * server requires it.
	 */
	private class SMTPAuthenticator extends javax.mail.Authenticator {

		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(getSmtpUser(), getSmtpPass());
		}
	}

	public String getFailure() {
		return failureBuffer.toString();
	}

	@Override
	public void setIgnoreFailures(boolean b) {
		ignoreFailures = b;
	}

	public String getSmtpUser() {
		if(smtpUser!=null && !"".equals(smtpUser)) {
			return smtpUser;
		}
		if(myNavajo==null) {
			return null;
		}
		Property mailUserProperty = myNavajo.getProperty("__globals__/MailUser");
		if(mailUserProperty!=null) {
			return mailUserProperty.getValue();
		}
		return smtpUser;
	}

	public Integer getPort() {
		if(port!=null && port>0) {
			return port;
		}
		Property mailPortProperty = myNavajo.getProperty("__globals__/MailPort");
		if(mailPortProperty!=null) {
			try {
				return Integer.parseInt(mailPortProperty.getValue());
			} catch (NumberFormatException e) {
				logger.error("Can not parse mail port value: "+mailPortProperty.getValue(), e);
			}
		}
		return port;
	}
	
	public void setSmtpUser(String smtpUser) {
		this.smtpUser = smtpUser;
	}

	public String getSmtpPass() {
		if(smtpPass!=null && !"".equals(smtpPass)) {
			return smtpPass;
		}
		Property mailPassProperty = myNavajo.getProperty("__globals__/MailPassword");
		if(mailPassProperty!=null) {
			return mailPassProperty.getValue();
		}
		return smtpPass;
	}

	public void setSmtpPass(String smtpPass) {
		this.smtpPass = smtpPass;
	}

	@Override
	public void setRecipients(String s) {

		java.util.StringTokenizer tok = new StringTokenizer(s, ",");

		this.recipientArray = new String[tok.countTokens()];
		int index = 0;

		while (tok.hasMoreTokens()) {
			this.recipientArray[index++] = tok.nextToken();
		}
		this.recipients = s;
	}

	@Override
	public void setSender(String s) {
		this.sender = s;
	}

	@Override
	public void setMailServer(String s) {
		this.mailServer = s;
	}

	@Override
	public void setSubject(String s) {
		this.subject = s;
	}

	@Override
	public void setContentType(String s) {
		this.contentType = s;
	}

	@Override
	public void setXslFile(String s) {
		this.xslFile = s;
	}

	@Override
	public void setText(String s) {
		this.text += s;
	}

	@Override
	public void setBcc(String bcc) {
		this.bcc = bcc;
		java.util.StringTokenizer tok = new StringTokenizer(bcc, ",");

		this.bccArray = new String[tok.countTokens()];
		int index = 0;

		while (tok.hasMoreTokens()) {
			this.bccArray[index++] = tok.nextToken();
		}
	}

	@Override
	public void setCc(String cc) {
		this.cc = cc;
		java.util.StringTokenizer tok = new StringTokenizer(cc, ",");

		this.ccArray = new String[tok.countTokens()];
		int index = 0;

		while (tok.hasMoreTokens()) {
			this.ccArray[index++] = tok.nextToken();
		}
	}

	@Override
	public void setMultipleAttachments(AttachementMap[] c) {

		if (attachments == null) {
			attachments = new ArrayList<>();
		}

		for (int i = 0; i < c.length; i++) {
			attachments.add(c[i]);
		}
	}
	
	public AttachementMap[] getMultipleAttachments() {
		return attachments.toArray(new AttachementMap[attachments.size()]);
	}
	
	public AttachementMap getAttachment() {
		return attachments.get(0);
	}

	@Override
	public void setRelatedMultipart(boolean b) {
		this.relatedMultipart = b;
	}

	@Override
	public void setAttachment(AttachementMap m) {
		if (attachments == null) {
			attachments = new ArrayList<>();
		}
		attachments.add(m);
	}

	@Override
	public int getMaxRetries() {
		return maxRetries;
	}

	@Override
	public Binary getRequest() {
		return null;
	}

	// for scala compatibility
	public boolean getUseEncryption() {
		return isUseEncryption();
	}

	
	public boolean isUseEncryption() {
		return useEncryption;
	}

	public void setUseEncryption(boolean useEncryption) {
		this.useEncryption = useEncryption;
	}

	@Override
	public Binary getResponse() {
		return null;
	}

	@Override
	public int getRetries() {
		return retries;
	}

	@Override
	public long getWaitUntil() {
		return waitUntil;
	}

	@Override
	public void resetRetries() {
		retries = 0;
	}

	@Override
	public void setMaxRetries(int r) {
		maxRetries = r;
	}

	@Override
	public void setQueuedSend(boolean b) {
		queuedSend = b;
	}

	@Override
	public void setWaitUntil(long w) {
		waitUntil = w;
	}

	@Override
	public Access getAccess() {
		return myAccess;
	}

	@Override
	public Navajo getNavajo() {
		return myNavajo;
	}

	@Override
	public int getMaxRunningInstances() {
		return maxRunningInstances;
	}

	@Override
	public void setMaxRunningInstances(int maxRunningInstances) {
		setStaticMaxRunningInstances(maxRunningInstances);
	}

	private static void setStaticMaxRunningInstances(int maxRunningInstances) {
		MailMap.maxRunningInstances = maxRunningInstances;
	}

	@Override
	public DependentResource[] getDependentResourceFields() {
		return new DependentResource[] { new GenericDependentResource(
				"mailserver", "mailServer", AdapterFieldDependency.class) };
	}

	public String getRecipients() {
		return recipients;
	}

	public String getMailServer() {
		if(mailServer!=null && !"".equals(mailServer)) {
			return mailServer;
		}
		Property mailServerProperty = myNavajo.getProperty("__globals__/MailServer");
		if(mailServerProperty!=null) {
			return mailServerProperty.getValue();
		}
		return mailServer;
	}

	public String getSender() {
		return sender;
	}

	public String getSubject() {
		return subject;
	}

	public String getText() {
		return text;
	}

	public boolean getIgnoreFailures() {
		return ignoreFailures;
	}

	public boolean getQueuedSend() {
		return queuedSend;
	}



	public void setPort(Integer port) {
		this.port = port;
	}
	
	public String getContentType() {
		return this.contentType;
	}

	@Override
	public void setDebug(boolean b) {
		this.debug = b;
	}

	@Override
	public boolean getDebug() {
		return this.debug;
	}

	
}
