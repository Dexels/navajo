package com.dexels.navajo.adapter;

import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.mailmap.AttachmentMapInterface;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;
import com.dexels.navajo.server.DispatcherFactory;

public class DummyMailMap implements MailMapInterface, Mappable {


	public DummyAttachmentMap attachment;
	public DummyAttachmentMap [] multipleAttachments;
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(DummyMailMap.class);
	
	public String sender;
	public String subject;
	public String recipients;
	public String cc;
	public String bcc;
	public String text;
	
	@Override
	public void setAttachment(AttachmentMapInterface m) {
		logger.info("In DummyMailMap, setAttachment(" + m + ")");
	}

	@Override
	public void setBcc(String bcc) {
		logger.info("In DummyMailMap, setBcc(" + bcc + ")");	
		this.bcc = bcc;
	}

	@Override
	public void setCc(String cc) {
		logger.info("In DummyMailMap, setCc(" + cc + ")");	
		this.cc = cc;
	}

	@Override
	public void setContentType(String s) {
		logger.info("In DummyMailMap, setContentType(" + s + ")");	
	}

	@Override
	public void setIgnoreFailures(boolean b) {
		logger.info("In DummyMailMap, setIgnoreFailures(" + b + ")");
	}

	@Override
	public void setMailServer(String s) {
		logger.info("In DummyMailMap, setMailServer(" + s + ")");
	}

	@Override
	public void setMultipleAttachments(AttachmentMapInterface[] c) {
		logger.info("In DummyMailMap, setMultipleAttachments size: " + c.length );
		this.multipleAttachments = (DummyAttachmentMap []) c;
	}

	@Override
	public void setRecipients(String s) {
		logger.info("In DummyMailMap, setRecipients(" + s + ")");
		this.recipients = s;
	}

	@Override
	public void setRelatedMultipart(boolean b) {
		logger.info("In DummyMailMap, setRelatedMultipart(" + b + ")");
	}

	@Override
	public void setSender(String s) {
		logger.info("In DummyMailMap, setSender(" + s + ")");
		this.sender = s;
	}
	
	public String getSender() {
		return this.sender;
	}

	@Override
	public void setSubject(String s) {
		logger.info("In DummyMailMap, setSubject(" + s + ")");
		this.subject = s;
	}

	@Override
	public void setText(String s) {
		logger.info("In DummyMailMap, setText(" + s + ")");
		this.text = s;
	}

	@Override
	public void setXslFile(String s) {
		logger.info("In DummyMailMap, setXslFile(" + s + ")");
	}

	@Override
	public void kill() {
		
	}

	@Override
	public void load(Access access) throws MappableException, UserException {
		
	}

	@Override
	public void store() throws MappableException, UserException {
		String mailStore = DispatcherFactory.getInstance().getNavajoConfig().getRootPath() + "/log/dummymailmap.log";
		try {
			FileWriter fw = new FileWriter(mailStore, true);
			fw.write(this.sender + "," + this.recipients + "," + this.cc + "," + this.bcc + "," + this.subject + "," + this.text + "\n");
			fw.close();
		} catch (IOException e) {
			throw new UserException(-1, "Error writing dummy data", e);
		}
		
	}

	public DummyAttachmentMap[] getMultipleAttachments() {
		return multipleAttachments;
	}

}
