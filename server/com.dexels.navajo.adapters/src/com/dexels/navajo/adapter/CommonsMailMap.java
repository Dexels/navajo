package com.dexels.navajo.adapter;

import java.io.File;
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
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.DependentResource;
import com.dexels.navajo.mapping.GenericDependentResource;
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Debugable;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.enterprise.queue.Queuable;
import com.dexels.navajo.server.enterprise.queue.RequestResponseQueueFactory;
import com.dexels.navajo.util.AuditLog;

public class CommonsMailMap implements Mappable, Queuable,Debugable {
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
	public String mailPort = "";
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
	private List<AttachementMap> attachments = null;
	public AttachementMap[] multipleAttachments = null;
	public AttachementMap attachment = null;
	private Integer port = null;
	private boolean debug;

	


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
		email.setCharset(org.apache.commons.mail.EmailConstants.UTF_8);
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
			email.addCc(this.getCcArray(this.cc));
		}
		if (this.bcc != null) {
			email.addBcc(this.getBccArray(this.bcc));
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
			logger.info("Sending mail to: "+to+" subject: "+subject);
			// Create the email message and fill the basics
			HtmlEmail email = getNewHtmlEmail();
			if(debug) {
				email.setDebug(debug);
			}
			fillHtmlEmailBasics(email);
		    // add attachments
			List<String> inlineImages = new ArrayList<String>();
			if (this.attachments != null) {
				for (int i = 0; i < this.attachments.size(); i++) {
					logger.info("# of attachments found: "+attachments.size());
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
					File fl = new File(fileName);
					URL url = fl.toURI().toURL();
					logger.info("Using url: "+url);
					if (contentDisposition != null && contentDisposition.equalsIgnoreCase("Inline")) {
					    // embed the image and get the content id
					    inlineImages.add(email.embed(url, userFileName));
					} else {
						email.attach(this.getEmailAttachment(fileName, url, contentDisposition, userFileName, userFileName));
					}
				}
			} else {
				logger.info("No attachments");
			}
		logger.info("Setting body, before replace: "+bodyText);
		  
		  // Replace any inline image tags with the created ones
		  bodyText = replaceInlineImageTags(bodyText, inlineImages);
		  // Finally set the complete html
		  logger.info("Setting body: "+bodyText);
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
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
	
	private Session createSession() {
		Properties props = new Properties();
		props.putAll( System.getProperties());
		props.put("mail.smtp.host", mailServer);
		
		String actualport = getPort() == null ? null: port.toString(); 
		if (actualport == null || actualport.equals("")) {
			actualport = useEncryption ?  "465" : "25";
		}

		if (getSmtpUser() != null && !"".equals(getSmtpUser())) {
			// Use auth
			props.put("mail.smtp.auth", "true");
//			props.put("mail.debug", "true");
			if (useEncryption) {
				logger.info("Using encrypt + auth. ");

				props.put("mail.smtp.port", actualport);
				props.put("mail.smtp.socketFactory.port", actualport);
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.socketFactory.fallback", "false");
			} else {

				props.put("mail.smtp.port", actualport);
			}
			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props, auth);
			return session;
		} else {

			props.put("mail.smtp.port", actualport);
			Session session = Session.getInstance(props);
			return session;
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

	public DependentResource[] getDependentResourceFields() {
		return new DependentResource[] { new GenericDependentResource(
				"mailserver", "mailServer", AdapterFieldDependency.class) };
	}

	@Override
	public void load(Access access) throws MappableException {
		if (access != null) {
			doc = access.getInDoc();
			myNavajo = access.getInDoc();
			myAccess = access;
		}
	}

	@Override
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

	@Override
	public void kill() {
	}

	@Override
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

	public String getMailPort() {
		return this.mailPort;
	}
	
	public void setMailPort(String s) {
		this.mailPort = s;
	}

	public String getSmtpUser() {
		if(smtpUser!=null && !"".equals(smtpUser)) {
			return smtpUser;
		}
		Property mailUserProperty = myNavajo.getProperty("__globals__/MailUser");
		if(mailUserProperty!=null) {
			return mailUserProperty.getValue();
		}
		return smtpUser;
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

	@Override
	public void setQueuedSend(boolean b) {
		queuedSend = b;
	}

	public boolean getQueuedSend() {
		return queuedSend;
	}
	
	@Override
	public void setMaxRetries(int r) {
		maxRetries = r;
	}

	@Override
	public void setWaitUntil(long w) {
		waitUntil = w;
	}

	@Override
	public int getMaxRetries() {
		return maxRetries;
	}

	@Override
	public long getWaitUntil() {
		return waitUntil;
	}

	@Override
	public Binary getResponse() {
		return null;
	}

	@Override
	public Binary getRequest() {
		return null;
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
	public void resetRetries() {
		retries = 0;
	}

	@Override
	public int getRetries() {
		return retries;
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
		CommonsMailMap.maxRunningInstances = maxRunningInstances;
	}

	public boolean isUseEncryption() {
		return useEncryption;
	}
	
	// for scala compatibility
	public boolean getUseEncryption() {
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

	public void setMultipleAttachments(AttachementMap[] c) {
		if (attachments == null) {
			attachments = new ArrayList<AttachementMap>();
		}

		for (int i = 0; i < c.length; i++) {
			attachments.add(c[i]);
		}
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
	
	public void setAttachment(AttachementMap m) {
		if (attachments == null) {
			attachments = new ArrayList<AttachementMap>();
		}
		attachments.add(m);
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
