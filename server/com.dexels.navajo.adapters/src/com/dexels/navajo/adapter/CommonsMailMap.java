package com.dexels.navajo.adapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.mailmap.AttachementMap;
import com.dexels.navajo.adapter.mailmap.AttachmentMapInterface;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.DependentResource;
import com.dexels.navajo.mapping.GenericDependentResource;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.enterprise.queue.Queuable;
import com.dexels.navajo.server.enterprise.queue.RequestResponseQueueFactory;
import com.dexels.navajo.util.AuditLog;

public class CommonsMailMap implements Mappable, Queuable {
	private static final long serialVersionUID = 5625969473841204407L;
	private final static Logger logger = LoggerFactory.getLogger(CommonsMailMap.class);
	@SuppressWarnings("unused")
	private Navajo doc = null;
	private Navajo myNavajo;
	private Access myAccess;
	public int retries = 0;
	public int maxRetries = 100;
	public boolean queuedSend = false;
	public long waitUntil = 0;
	public static int maxRunningInstances = -1;
	private boolean useEncryption = false;
	// Mail stuff
	public String nonHtmlText = "Your email client does not support HTML messages";
	public String mailServer = "";
	public String smtpUser = "";
	public String smtpPass = "";
	public String from = null;
	public String subject = "";
	public String to = null;
	public String cc = null;
	public String bcc = null;
	String bodyText = "";
	private String[] toArray = null;
	private String[] ccArray = null;
	private String[] bccArray = null;
	private List<AttachmentMapInterface> attachments = null;
	public AttachementMap[] multipleAttachments = null;
	public AttachementMap attachment = null;

	
	public CommonsMailMap() {}
	
	/**
	 * Create the new HtmlEmail message and set session props
	 * @return HtmlEmail
	 * @throws UserException
	 * @throws EmailException
	 */
	private HtmlEmail getNewHtmlEmail() throws UserException, EmailException {
		// Create the email message
		Session session = createSession();
		
		HtmlEmail email = new HtmlEmail();
		email.setMailSession(session);
		if (from == null || "".equals(from)) {
			throw new UserException(-1, "Error: Required sender address not set!");
		}
		return email;
	}
	
	/**
	 * Fill the basics like addressee
	 * @param email
	 * @return HtmlEmail
	 * @throws EmailException
	 */
	private HtmlEmail fillHtmlEmailBasics(HtmlEmail email) throws EmailException {
	    email.setFrom(this.from, "");
		// Add addressees
		if (this.to != null) {
			email.addTo(this.getToArray(this.to));
		}
		if (this.cc != null) {
			email.addTo(this.getCcArray(this.cc));
		}
		if (this.bcc != null) {
			email.addTo(this.getBccArray(this.bcc));
		}
		
	    // Subject
		email.setSubject(this.getSubject());

		return email;
	}
	
	/**
	 * This is where the actual mail is constructed and send
	 * Inline images can be used through attachments
	 * Annotation is cid:{?}. This will be replaced with cid:?
	 * The first ? refers to the index number in the attachments starting with 0
	 * The second ? will contain the generated id
	 * EXAMPLE:
	 * <hmtl>Some text <img src=\"cid:{0}\"></html>
	 * The {0} will then be replaced with the first generated inline tag
	 * @throws UserException
	 */
	public void sendMail() throws UserException {
		try {
			// Create the email message and fill the basics
			HtmlEmail email = getNewHtmlEmail();
			fillHtmlEmailBasics(email);
		    // add attachments
			List<String> inlineImages = new ArrayList<String>();
			if (this.attachments != null) {
				for (int i = 0; i < this.attachments.size(); i++) {
					AttachmentMapInterface am = this.attachments.get(i);
					String file = am.getAttachFile();
					String userFileName = am.getAttachFileName();
					Binary content = am.getAttachFileContent();
					String contentDisposition = am.getAttachContentDisposition();
					//String encoding = am.getEncoding();

					// Figure out how to get the path and then the url
					String fileName = "";
					if (content != null) {
						fileName = content.getTempFileName(false);
					} else {
						fileName = file;
					}
				    URL url = new URL("file:/" + fileName);
					
					if (contentDisposition != null && contentDisposition.equalsIgnoreCase("Inline")) {
					    // embed the image and get the content id
					    inlineImages.add(email.embed(url, userFileName));
					} else {
						email.attach(this.getEmailAttachment(fileName, url, contentDisposition, userFileName, userFileName));
					}
				}
			}
		  
		  // Replace any inline image tags with the created ones
		  bodyText = replaceInlineImageTags(bodyText, inlineImages);
		  // Finally set the complete html
		  email.setHtmlMsg(bodyText);

		  // set the alternative message
		  email.setTextMsg(this.getNonHtmlText());

		  // send the email
		  email.send();
		} catch (MalformedURLException e) {
			AuditLog.log("CommonsMailMap", e.getMessage(), Level.SEVERE, myAccess.accessID);
			throw new UserException(-1, e.getMessage(), e);
		} catch (EmailException e) {
			AuditLog.log("CommonsMailMap", e.getMessage(), Level.SEVERE, myAccess.accessID);
			throw new UserException(-1, e.getMessage(), e);
		}
	}
	
	/**
	 * Replace the image tags from the html with the created ones
	 * @param html
	 * @param inlineImages
	 * @return String
	 */
	private String replaceInlineImageTags(String html, List<String> inlineImages) {
		String result = html;
		int i = 0;
		for (String inlineImage : inlineImages) {
			result = result.replace("cid:{" + i + "}", "cid:" + inlineImage + "");
			i++;
		}
		return result;
	}
	
	/**
	 * Create an attachment
	 * @param path
	 * @param disposition
	 * @param description
	 * @param name
	 * @return EmailAttachment
	 */
	private EmailAttachment getEmailAttachment(String path, URL url, String disposition, String description, String name) {
		  // Create the attachment
		  EmailAttachment attachment = new EmailAttachment();
		  attachment.setPath(path);
		  if (disposition != null && disposition.equalsIgnoreCase("INLINE")) {
			  attachment.setDisposition(EmailAttachment.INLINE);
		  } else {
			  attachment.setDisposition(EmailAttachment.ATTACHMENT);
		  }
		  attachment.setDescription(description);
		  attachment.setName(name);
		  attachment.setURL(url);
		  return attachment;
	}

	public String[] getToArray(String s) {
		this.toArray = null;
		java.util.StringTokenizer tok = new StringTokenizer(s, ",");
		this.toArray = new String[tok.countTokens()];
		int index = 0;

		while (tok.hasMoreTokens()) {
			this.toArray[index++] = tok.nextToken();
		}
		return this.toArray;
	}

	public String[] getBccArray(String bcc) {
		this.bccArray = null;
		java.util.StringTokenizer tok = new StringTokenizer(bcc, ",");
		this.bccArray = new String[tok.countTokens()];
		int index = 0;

		while (tok.hasMoreTokens()) {
			this.bccArray[index++] = tok.nextToken();
		}
		return this.bccArray;
	}

	public String[] getCcArray(String cc) {
		this.ccArray = null;
		java.util.StringTokenizer tok = new StringTokenizer(cc, ",");
		this.ccArray = new String[tok.countTokens()];
		int index = 0;

		while (tok.hasMoreTokens()) {
			this.ccArray[index++] = tok.nextToken();
		}
		return this.ccArray;
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
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
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

	public DependentResource[] getDependentResourceFields() {
		return new DependentResource[] { new GenericDependentResource(
				"mailserver", "mailServer", AdapterFieldDependency.class) };
	}

	public void load(Access access) throws MappableException {
		if (access != null) {
			doc = access.getInDoc();
			myNavajo = access.getInDoc();
			myAccess = access;
		}
	}

	public void store() throws MappableException, UserException {
		if (!queuedSend) {
			sendMail();
		} else {
			try {
				RequestResponseQueueFactory.getInstance().send(this, 100);
			} catch (Exception e) {
				AuditLog.log("CommonsMailMap", e.getMessage(), Level.WARNING, myAccess.accessID);
				logger.error("Error: sending request (?)",e);
			}
		}
	}

	public void kill() {
	}

	public boolean send() {
		retries++;
		try {
			sendMail();
		} catch (Exception e) {
			if (myAccess != null) {
				AuditLog.log("CommonsMailMap", e.getMessage(), Level.WARNING, myAccess.accessID);
				myAccess.setException(e);
			}
			return false;
		}
		return true;
	}

	public String getMailServer() {
		return this.mailServer;
	}
	
	public void setMailServer(String s) {
		this.mailServer = s;
	}

	public String getSmtpUser() {
		return this.smtpUser;
	}

	public void setSmtpUser(String smtpUser) {
		this.smtpUser = smtpUser;
	}

	public String getSmtpPass() {
		return this.smtpPass;
	}

	public void setSmtpPass(String smtpPass) {
		this.smtpPass = smtpPass;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String s) {
		this.subject = s;
	}
	
	public String getBodyText() {
		return this.bodyText;
	}
	
	public void setBodyText(String s) {
		this.bodyText = s;
	}
	
	public String getTo() {
		return this.to;
	}
	
	public void setTo(String s) {
		this.to = s;
	}
	
	public String getCc() {
		return this.cc;
	}
	
	public void setCc(String s) {
		this.cc = s;
	}
	
	public String getBcc() {
		return this.bcc;
	}
	
	public void setBcc(String s) {
		this.bcc = s;
	}
	
	public String getFrom() {
		return this.from;
	}
	
	public void setFrom(String s) {
		this.from = s;
	}

	public void setQueuedSend(boolean b) {
		queuedSend = b;
	}

	public void setMaxRetries(int r) {
		maxRetries = r;
	}

	public void setWaitUntil(long w) {
		waitUntil = w;
	}

	public int getMaxRetries() {
		return maxRetries;
	}

	public long getWaitUntil() {
		return waitUntil;
	}

	public Binary getResponse() {
		return null;
	}

	public Binary getRequest() {
		return null;
	}

	public Access getAccess() {
		return myAccess;
	}

	public Navajo getNavajo() {
		return myNavajo;
	}

	public void resetRetries() {
		retries = 0;
	}

	public int getRetries() {
		return retries;
	}

	public int getMaxRunningInstances() {
		return maxRunningInstances;
	}

	public void setMaxRunningInstances(int maxRunningInstances) {
		setStaticMaxRunningInstances(maxRunningInstances);
	}

	private static void setStaticMaxRunningInstances(int maxRunningInstances) {
		CommonsMailMap.maxRunningInstances = maxRunningInstances;
	}

	public boolean isUseEncryption() {
		return useEncryption;
	}

	public void setUseEncryption(boolean useEncryption) {
		this.useEncryption = useEncryption;
	}
	
	public String getNonHtmlText() {
		return nonHtmlText;
	}

	public void setNonHtmlText(String nonHtmlText) {
		this.nonHtmlText = nonHtmlText;
	}

	public Binary[] getAttachments() {
		Binary[] bins = new Binary[attachments.size()];
		bins = attachments.toArray(bins);
		return bins;
	}

	public void setMultipleAttachments(AttachmentMapInterface[] c) {
		if (attachments == null) {
			attachments = new ArrayList<AttachmentMapInterface>();
		}

		for (int i = 0; i < c.length; i++) {
			attachments.add(c[i]);
		}
	}

	public void setAttachment(AttachmentMapInterface m) {
		if (attachments == null) {
			attachments = new ArrayList<AttachmentMapInterface>();
		}
		attachments.add(m);
	}
}
