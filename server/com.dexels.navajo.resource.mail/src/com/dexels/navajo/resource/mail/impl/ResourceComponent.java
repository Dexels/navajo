package com.dexels.navajo.resource.mail.impl;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.mailmap.AttachmentMapInterface;
import com.dexels.navajo.datasource.BinaryDataSource;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.resource.mail.MailResource;
import com.dexels.navajo.script.api.UserException;

public class ResourceComponent implements MailResource {

	private final static Logger logger = LoggerFactory
			.getLogger(ResourceComponent.class);

	private Session session;

	@Override
	public void send(String from, String[] to, String[] cc, String[] bcc, String subject,
			String body, List<AttachmentMapInterface> attachments, String contentType, boolean relatedMultipart) throws UserException {
		javax.mail.Message msg = new MimeMessage(session);
		if (from == null || "".equals(from)) {
			throw new UserException(-1,
					"Error: Required sender address not set!");
		}
		try {
			msg.setFrom(new InternetAddress(from));

			InternetAddress[] addresses = new InternetAddress[to.length];

			for (int i = 0; i < to.length; i++) {
				addresses[i] = new InternetAddress(to[i]);
			}

			msg.setRecipients(javax.mail.Message.RecipientType.TO, addresses);

			if (cc != null) {
				InternetAddress[] extra = new InternetAddress[cc.length];
				for (int i = 0; i < cc.length; i++) {
					extra[i] = new InternetAddress(cc[i]);
				}
				msg.setRecipients(javax.mail.Message.RecipientType.CC, extra);
			}

			if (bcc != null) {
				InternetAddress[] extra = new InternetAddress[bcc.length];
				for (int i = 0; i < bcc.length; i++) {
					extra[i] = new InternetAddress(bcc[i]);
				}
				msg.setRecipients(javax.mail.Message.RecipientType.BCC, extra);
			}

			msg.setSubject(subject);
			msg.setSentDate(new java.util.Date());
			if (attachments == null && contentType.equals("text/plain")) {
				msg.setText(body);
			} else {
				Multipart multipart = (relatedMultipart ? new MimeMultipart(
						"related") : new MimeMultipart());
				BodyPart textBody = new MimeBodyPart();
				textBody.setContent(body, contentType);

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
	

		} catch (AddressException e) {
			throw new UserException("Error sending mail:", e);
		} catch (MessagingException e) {
            throw new UserException("Error sending mail:", e);
        }


	}

	
	public void deactivate() {
		this.session = null;
	}
	
	public void activate(Map<String,Object> settings) {

		final String host = (String)settings.get("host");
		Integer port = null;
		Object rawPort = settings.get("port");
		if(rawPort!=null) {
			if(rawPort instanceof String) {
				port = Integer.parseInt((String)settings.get("port"));
			} else if (rawPort instanceof Integer) {
				port = (Integer)settings.get("port");
			}
		}

		final String username = (String)settings.get("user");
		final String password = (String)settings.get("password");
		final Object rawEncrypt = settings.get("encrypt");
		boolean encrypt = false;
		if(rawEncrypt!=null) {
			if(rawEncrypt instanceof String) {
				encrypt = Boolean.parseBoolean((String) rawEncrypt);
			} else if (rawEncrypt instanceof Boolean) {
				encrypt = (Boolean)rawEncrypt;
			}
		}
		Properties props = new Properties();
		props.putAll( System.getProperties());
		props.put("mail.smtp.host", host);

		if (username != null && !"".equals(username)) {
			// Use auth
			props.put("mail.smtp.auth", "true");
			props.put("mail.debug", "true");
			if (encrypt) {
				logger.info("Using encrypt + auth. ");
				if(port!=null) {
					props.put("mail.smtp.port",""+port);
					props.put("mail.smtp.socketFactory.port", ""+port);
					
				} else {
					props.put("mail.smtp.port", "465");
					props.put("mail.smtp.socketFactory.port", "465");
				}
				props.put("mail.smtp.socketFactory.class",
						"javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.socketFactory.fallback", "false");
			}
			Authenticator auth = new Authenticator(){

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
				
			};
			this.session = Session.getInstance(props, auth);
		} else {
			if(port!=null) {
				props.put("mail.smtp.port", ""+port);
			} else {
				props.put("mail.smtp.port", "25");
			}
			this.session = Session.getInstance(props);
		}
	}

}
