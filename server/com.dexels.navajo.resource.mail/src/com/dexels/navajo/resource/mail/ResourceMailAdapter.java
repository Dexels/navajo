package com.dexels.navajo.resource.mail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.dexels.navajo.adapter.MailMapInterface;
import com.dexels.navajo.adapter.mailmap.AttachementMap;
import com.dexels.navajo.adapter.mailmap.AttachmentMapInterface;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class ResourceMailAdapter implements MailMapInterface, Mappable {

	private Navajo doc;
	private String[] recipientArray;
	private String sender;
	private String contentType;
	private String subject;
	public String text = "";
	private String xslFile;
	private boolean relatedMultipart;
	private List<AttachementMap> attachments;
	private String[] bccArray;
	private String[] ccArray;
	private String name;
	
	private final static Logger logger = LoggerFactory
			.getLogger(ResourceMailAdapter.class);
	
	@Override
	public void load(Access access) throws MappableException, UserException {
		if (access != null) {
			doc = access.getInDoc();
		}		
	}

	private String parseWithXslStyle(String path) throws TransformerConfigurationException, NavajoException, TransformerException, IOException {
		java.io.File xsl = new java.io.File(path);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		doc.write(bos);
		bos.close();
		ByteArrayInputStream bis = new ByteArrayInputStream(
				bos.toByteArray());
		Document doc = XMLDocumentUtils.createDocument(bis, false);
		bis.close();
		return XMLDocumentUtils.transform(doc, xsl);

	}
	
	@Override
	public void store() throws MappableException, UserException {
		MailResource resource = MailResourceFactory.getInstance().getMailResource(name);
		if(resource==null) {
			throw new MappableException("Missing mail resource with name: "+name);
		}
		String body = this.text;
		if(this.xslFile!=null && !"".equals(xslFile)) {
			try {
				body = parseWithXslStyle(xslFile);
			} catch (Throwable e) {
				throw new MappableException("Error parsing using XSL file", e);
			}
		}
		List<AttachmentMapInterface> attach = new ArrayList<AttachmentMapInterface>(attachments);
		resource.send(sender, this.recipientArray, this.ccArray, this.bccArray, this.subject,body, attach, contentType, relatedMultipart);
	}

	@Override
	public void kill() {
	}

	public void setResource(String name) {
		this.name = name;
	}
	
	@Override
	public void setIgnoreFailures(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRecipients(String s) {

		java.util.StringTokenizer tok = new StringTokenizer(s, ",");

		this.recipientArray = new String[tok.countTokens()];
		int index = 0;

		while (tok.hasMoreTokens()) {
			this.recipientArray[index++] = tok.nextToken();
		}
	}

	@Override
	public void setSubject(String s) {
		this.subject = s;
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
	public void setContentType(String s) {
		this.contentType = s;
	}


	@Override
	public void setSender(String s) {
		this.sender = s;
	}


	@Override
	public void setBcc(String bcc) {
		java.util.StringTokenizer tok = new StringTokenizer(bcc, ",");

		this.bccArray = new String[tok.countTokens()];
		int index = 0;

		while (tok.hasMoreTokens()) {
			this.bccArray[index++] = tok.nextToken();
		}
	}

	@Override
	public void setCc(String cc) {
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
			attachments = new ArrayList<AttachementMap>();
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
			attachments = new ArrayList<AttachementMap>();
		}
		attachments.add(m);
	}


	@Override
	public void setMailServer(String s) {
		logger.warn("setMailServer makes no sense for ResourceMailAdapter");
		
	}

	

}
