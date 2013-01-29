package com.dexels.navajo.adapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.DependentResource;
import com.dexels.navajo.mapping.GenericDependentResource;
import com.dexels.navajo.mapping.HasDependentResources;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;
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
		HasDependentResources,
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

	public String smtpUser = "";
	public String smtpPass = "";

	public AttachementMap[] multipleAttachments = null;
	public AttachementMap attachment = null;
	public boolean ignoreFailures = false;

	public boolean relatedMultipart = false;
	private List<AttachmentMapInterface> attachments = null;
	private String[] recipientArray = null;
	private String[] ccArray = null;
	private String[] bccArray = null;
	private Navajo doc = null;
	private String failure = "";
	// private static Object semaphore = new Object();

	public int retries = 0;
	public int maxRetries = 100;
	public boolean queuedSend = false;
	public long waitUntil = 0;
	public static int maxRunningInstances = -1;

	private Navajo myNavajo;
	private Access myAccess;
	
	private final static Logger logger = LoggerFactory.getLogger(MailMap.class);
	
	private boolean useEncryption = false;

	public MailMap() {
	}

	public void kill() {
	}

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

	public void store() throws MappableException, UserException {
		if (!queuedSend) {
			sendMail();
		} else {
			try {
				RequestResponseQueueFactory.getInstance().send(this, 100);
			} catch (Exception e) {
				AuditLog.log("MailMap", e.getMessage(), Level.WARNING,
						myAccess.accessID);
				logger.error("Error: sending request (?)",e);
			}
		}
	}

	public boolean send() {
		retries++;
		try {
			sendMail();
		} catch (Exception e) {
			// e.printStackTrace(System.err);
			if (myAccess != null) {
				AuditLog.log("MailMap", e.getMessage(), Level.WARNING,
						myAccess.accessID);
				myAccess.setException(e);
			}
			return false;
		}
		return true;
	}

	private final void sendMail() throws UserException {

		retries++;

		try {
			String result = "";

			result = text;

			Session session = createSession();
			javax.mail.Message msg = new MimeMessage(session);

			if (sender == null || "".equals(sender)) {
				throw new UserException(-1,
						"Error: Required sender address not set!");
			}
			msg.setFrom(new InternetAddress(sender));

			InternetAddress[] addresses = new InternetAddress[this.recipientArray.length];

			for (int i = 0; i < this.recipientArray.length; i++) {
				addresses[i] = new InternetAddress(this.recipientArray[i]);
			}

			msg.setRecipients(javax.mail.Message.RecipientType.TO, addresses);

			if (ccArray != null) {
				InternetAddress[] extra = new InternetAddress[this.ccArray.length];
				for (int i = 0; i < this.ccArray.length; i++) {
					extra[i] = new InternetAddress(this.ccArray[i]);
				}
				msg.setRecipients(javax.mail.Message.RecipientType.CC, extra);
			}

			if (bccArray != null) {
				InternetAddress[] extra = new InternetAddress[this.bccArray.length];
				for (int i = 0; i < this.bccArray.length; i++) {
					extra[i] = new InternetAddress(this.bccArray[i]);
				}
				msg.setRecipients(javax.mail.Message.RecipientType.BCC, extra);
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
				Document doc = XMLDocumentUtils.createDocument(bis, false);
				bis.close();
				result = XMLDocumentUtils.transform(doc, xsl);
			}

			if (attachments == null && contentType.equals("text/plain")) {
				msg.setText(result);
			} else {
				Multipart multipart = (relatedMultipart ? new MimeMultipart(
						"related") : new MimeMultipart());
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
							FileDataSource fileDatasource = new FileDataSource(
									file);
							bp.setDataHandler(new DataHandler(fileDatasource));
						} else if (content != null) {

							BinaryDataSource bds = new BinaryDataSource(
									content, "");
							DataHandler dh = new DataHandler(bds);
							bp.setDataHandler(dh);

							if (encoding != null) {
								bp.setHeader("Content-Transfer-Encoding",
										encoding);
								encoding = null;
							}
						}

						bp.setFileName(userFileName);
						if (relatedMultipart) {
							bp.setHeader("Content-ID", "<attach-nr-" + i + ">");
						}

						// iPhone headers
						//bp.setDisposition("attachment");
						bp.setDisposition(am.getAttachContentDisposition());

						multipart.addBodyPart(bp);
					}
				}
				msg.setContent(multipart);
			}
			Transport.send(msg);

		} catch (Exception e) {
			if (ignoreFailures) {
				AuditLog.log("MailMap", e.getMessage(), Level.WARNING,
						myAccess.accessID);
				failure = e.getMessage();
			} else {
				AuditLog.log("MailMap", e.getMessage(), Level.SEVERE,
						myAccess.accessID);
				throw new UserException(-1, e.getMessage(), e);
			}
		}
	}

	private Session createSession() {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", mailServer);

		if (smtpUser != null && !"".equals(smtpUser)) {
			// Use auth
			props.put("mail.smtp.auth", "true");
			props.put("mail.debug", "true");
			if (useEncryption) {
				logger.info("Using encrypt + auth. ");
				props.put("mail.smtp.port", "465");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class",
						"javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.socketFactory.fallback", "false");
			}
			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getDefaultInstance(props, auth);
			return session;
		} else {
			props.put("mail.smtp.port", "25");
			Session session = Session.getInstance(props);
			return session;
		}
	}

	/**
	 * SimpleAuthenticator is used to do simple authentication when the SMTP
	 * server requires it.
	 */
	private class SMTPAuthenticator extends javax.mail.Authenticator {

		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(smtpUser, smtpPass);
		}
	}

	public String getFailure() {
		return failure;
	}

	public void setIgnoreFailures(boolean b) {
		ignoreFailures = b;
	}

	public String getSmtpUser() {
		return smtpUser;
	}

	public void setSmtpUser(String smtpUser) {
		this.smtpUser = smtpUser;
	}

	public String getSmtpPass() {
		return smtpPass;
	}

	public void setSmtpPass(String smtpPass) {
		this.smtpPass = smtpPass;
	}

	public void setRecipients(String s) {

		java.util.StringTokenizer tok = new StringTokenizer(s, ",");

		this.recipientArray = new String[tok.countTokens()];
		int index = 0;

		while (tok.hasMoreTokens()) {
			this.recipientArray[index++] = tok.nextToken();
		}
		this.recipients = s;
	}

	public void setSender(String s) {
		this.sender = s;
	}

	public void setMailServer(String s) {
		this.mailServer = s;
	}

	public void setSubject(String s) {
		this.subject = s;
	}

	public void setContentType(String s) {
		this.contentType = s;
	}

	public void setXslFile(String s) {
		this.xslFile = s;
	}

	public void setText(String s) {
		this.text += s;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
		java.util.StringTokenizer tok = new StringTokenizer(bcc, ",");

		this.bccArray = new String[tok.countTokens()];
		int index = 0;

		while (tok.hasMoreTokens()) {
			this.bccArray[index++] = tok.nextToken();
		}
	}

	public void setCc(String cc) {
		this.cc = cc;
		java.util.StringTokenizer tok = new StringTokenizer(cc, ",");

		this.ccArray = new String[tok.countTokens()];
		int index = 0;

		while (tok.hasMoreTokens()) {
			this.ccArray[index++] = tok.nextToken();
		}
	}

	public void setMultipleAttachments(AttachmentMapInterface[] c) {

		if (attachments == null) {
			attachments = new ArrayList<AttachmentMapInterface>();
		}

		for (int i = 0; i < c.length; i++) {
			attachments.add(c[i]);
		}
	}

	public void setRelatedMultipart(boolean b) {
		this.relatedMultipart = b;
	}

	public void setAttachment(AttachmentMapInterface m) {
		if (attachments == null) {
			attachments = new ArrayList<AttachmentMapInterface>();
		}
		attachments.add(m);
	}

	public int getMaxRetries() {
		return maxRetries;
	}

	public Binary getRequest() {
		return null;
	}

	public boolean isUseEncryption() {
		return useEncryption;
	}

	public void setUseEncryption(boolean useEncryption) {
		this.useEncryption = useEncryption;
	}

	public Binary getResponse() {
		return null;
	}

	public int getRetries() {
		return retries;
	}

	public long getWaitUntil() {
		return waitUntil;
	}

	public void resetRetries() {
		retries = 0;
	}

	public void setMaxRetries(int r) {
		maxRetries = r;
	}

	public void setQueuedSend(boolean b) {
		queuedSend = b;
	}

	public void setWaitUntil(long w) {
		waitUntil = w;
	}

	public Access getAccess() {
		return myAccess;
	}

	public Navajo getNavajo() {
		return myNavajo;
	}

	public int getMaxRunningInstances() {
		return maxRunningInstances;
	}

	public void setMaxRunningInstances(int maxRunningInstances) {
		setStaticMaxRunningInstances(maxRunningInstances);
	}

	private static void setStaticMaxRunningInstances(int maxRunningInstances) {
		MailMap.maxRunningInstances = maxRunningInstances;
	}

	public DependentResource[] getDependentResourceFields() {
		return new DependentResource[] { new GenericDependentResource(
				"mailserver", "mailServer", AdapterFieldDependency.class) };
	}

}
