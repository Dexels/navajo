/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
import javax.mail.BodyPart;
import javax.mail.Multipart;
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
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import com.dexels.navajo.script.api.Access;
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
public class MailMapAlternative implements MailMapInterface, Mappable,
		HasDependentResources,
		com.dexels.navajo.server.enterprise.queue.Queuable {

	private static final long serialVersionUID = -6829674936474299750L;
	
	private static final Logger logger = LoggerFactory
			.getLogger(MailMapAlternative.class);
	
	public String recipients = "";
	public String mailServer = "";
	public String sender = "";
	public String subject = "";
	public String cc = "";
	public String bcc = "";
	public String xslFile = "";
	public String text = "";
	public String contentType = "text/plain";

	public AttachementMap[] multipleAttachments = null;
	public AttachementMap attachment = null;
	public AttachementMap[] multipleRelatedBodyParts = null;
	public AttachementMap relatedBodyPart = null;
	public boolean ignoreFailures = false;

	public boolean relatedMultipart = false;
	private transient List<AttachmentMapInterface> attachments = null;
	private transient ArrayList<AttachmentMapInterface> bodyparts = null;
	private String[] recipientArray = null;
	private String[] ccArray = null;
	private String[] bccArray = null;
	private Navajo doc = null;
	private String failure = "";

	public int retries = 0;
	public int maxRetries = 100;
	public boolean queuedSend = false;
	public long waitUntil = 0;
	public static int maxRunningInstances = -1;

	private Navajo myNavajo;
	private Access myAccess;

	public MailMapAlternative() {
	}

	@Override
	public void kill() {
	}

	@Override
	public void load(Access access) throws MappableException {
		doc = access.getInDoc();
		myNavajo = access.getInDoc();
		myAccess = access;
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
				AuditLog.log("MailMap", e.getMessage(),e, Level.WARNING,
						myAccess.accessID);
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

	private final void sendMail() throws UserException {

		retries++;

		try {
			String result = "";
			result = text;
			Properties props = System.getProperties();
			props.put("mail.smtp.host", mailServer);
			Session session = Session.getInstance(props);
			javax.mail.Message msg = new MimeMessage(session);
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
				Document navajoDoc = XMLDocumentUtils.createDocument(bis, false);
				bis.close();
				result = XMLDocumentUtils.transform(navajoDoc, xsl);
			}

			if (attachments == null && contentType.equals("text/plain")) {
				msg.setText(result);
			} else {
				Multipart multipart = new MimeMultipart("mixed");
				BodyPart textBody = new MimeBodyPart();
				textBody.setContent(result, contentType);

				Multipart related = new MimeMultipart("related");

				related.addBodyPart(textBody);

				if (bodyparts != null && !bodyparts.isEmpty()) {

					// Put related bodyparts in related.
					for (int i = 0; i < bodyparts.size(); i++) {
						AttachmentMapInterface am = bodyparts
								.get(i);
						String file = am.getAttachFile();
						String userFileName = am.getAttachFileName();
						Binary content = am.getAttachFileContent();
						String encoding = am.getEncoding();
						String attachContentType = am.getAttachContentType();
						MimeBodyPart bp = new MimeBodyPart();

						logger.debug("Embedding: {}", userFileName);

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
						if (attachContentType != null ) {
							bp.setHeader("Content-Type", attachContentType);
						}
						bp.setHeader("Content-ID", "<attach-nr-" + i + ">");
						related.addBodyPart(bp);
					}
				}

				MimeBodyPart bop = new MimeBodyPart();
				bop.setContent(related);
				multipart.addBodyPart(bop);

				if (attachments != null) {
					for (int i = 0; i < attachments.size(); i++) {

						AttachmentMapInterface am = attachments
								.get(i);
						String file = am.getAttachFile();
						String userFileName = am.getAttachFileName();
						Binary content = am.getAttachFileContent();
						String encoding = am.getEncoding();
						String attachContentType = am.getAttachContentType();
						String attachContentDisposition = am.getAttachContentDisposition();
						MimeBodyPart bp = new MimeBodyPart();

						logger.debug("Attaching: {}", userFileName);

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
							}
						}

						bp.setFileName(userFileName);
						if (attachContentType != null ) {
							bp.setHeader("Content-Type", attachContentType);
						}
						bp.setDisposition(attachContentDisposition);
						multipart.addBodyPart(bp);
					}
				}

				msg.setContent(multipart);
			}
			logger.info("Sending mail to {} cc: {} bcc: {} with subject: {}",recipients,cc,bcc,subject);

			Transport.send(msg);

		} catch (Exception e) {
			if (ignoreFailures) {
				AuditLog.log("MailMap", e.getMessage(),e, Level.WARNING,
						myAccess.accessID);
				failure = e.getMessage();
			} else {
				AuditLog.log("MailMap", e.getMessage(),e, Level.SEVERE,
						myAccess.accessID);
				throw new UserException(-1, e.getMessage());
			}
		}
	}

	public String getFailure() {
		return failure;
	}

	@Override
	public void setIgnoreFailures(boolean b) {
		ignoreFailures = b;
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

	public void setMultipleRelatedBodyParts(AttachmentMapInterface[] c) {

		if (bodyparts == null) {
			bodyparts = new ArrayList<>();
		}

		for (int i = 0; i < c.length; i++) {
			bodyparts.add(c[i]);
		}
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

	public void setRelatedBodyPart(AttachmentMapInterface m) {
		if (bodyparts == null) {
			bodyparts = new ArrayList<>();
		}
		bodyparts.add(m);
	}

	@Override
	public int getMaxRetries() {
		return maxRetries;
	}

	@Override
	public Binary getRequest() {
		return null;
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

	public String getMailServer() {
		return mailServer;
	}

	public boolean getQueuedSend() {
		return queuedSend;
	}

	public String getRecipients() {
		return recipients;
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

	public static void main(String[] args) throws UserException {
		MailMapAlternative mma = new MailMapAlternative();
		mma.setMailServer("mail.dexels.com");
		mma.setSubject("Test");
		mma.setSender("arnoud@dexels.com");
		mma.setRecipients("arnoud@dexels.com");
		AttachementMap am = new AttachementMap();
		am.setAttachFileName("attachment.png");
		am.setEncoding("base64");
		am.setAttachFile("c:/feyenoord-goud.png");
		mma.setAttachment(am);

		AttachementMap bm = new AttachementMap();
		bm.setAttachFileName("bodypart.png");
		bm.setEncoding("base64");
		bm.setAttachFile("c:/feyenoord-goud.png");
		mma.setRelatedBodyPart(bm);
		mma.setContentType("text/html");
		mma.setText("<html><head> </head><body>Test<img src=\"cid:attach-nr-0\"></body></html>");

		mma.send();
	}
	


	public AttachementMap[] getMultipleAttachments() {
		if (attachments == null) {
			return new AttachementMap[0];
		}
		
		return attachments.toArray(new AttachementMap[attachments.size()]);
	}
	
	public AttachementMap getAttachment() {
		return attachment;
	}

	public AttachementMap getRelatedBodyPart() {
		return relatedBodyPart;
	}

	 
}
