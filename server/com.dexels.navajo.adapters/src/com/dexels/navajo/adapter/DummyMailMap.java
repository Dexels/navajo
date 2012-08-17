package com.dexels.navajo.adapter;

import java.io.FileWriter;
import java.io.IOException;

import com.dexels.navajo.adapter.mailmap.AttachmentMapInterface;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.UserException;

public class DummyMailMap implements MailMapInterface, Mappable {


	public DummyAttachmentMap attachment;
	public DummyAttachmentMap [] multipleAttachments;
	
	public String sender;
	public String subject;
	public String recipients;
	public String cc;
	public String bcc;
	public String text;
	
	public void setAttachment(AttachmentMapInterface m) {
		System.err.println("In DummyMailMap, setAttachment(" + m + ")");
	}

	public void setBcc(String bcc) {
		System.err.println("In DummyMailMap, setBcc(" + bcc + ")");	
		this.bcc = bcc;
	}

	public void setCc(String cc) {
		System.err.println("In DummyMailMap, setCc(" + cc + ")");	
		this.cc = cc;
	}

	public void setContentType(String s) {
		System.err.println("In DummyMailMap, setContentType(" + s + ")");	
	}

	public void setIgnoreFailures(boolean b) {
		System.err.println("In DummyMailMap, setIgnoreFailures(" + b + ")");
	}

	public void setMailServer(String s) {
		System.err.println("In DummyMailMap, setMailServer(" + s + ")");
	}

	public void setMultipleAttachments(AttachmentMapInterface[] c) {
		System.err.println("In DummyMailMap, setMultipleAttachments size: " + c.length );
		this.multipleAttachments = (DummyAttachmentMap []) c;
	}

	public void setRecipients(String s) {
		System.err.println("In DummyMailMap, setRecipients(" + s + ")");
		this.recipients = s;
	}

	public void setRelatedMultipart(boolean b) {
		System.err.println("In DummyMailMap, setRelatedMultipart(" + b + ")");
	}

	public void setSender(String s) {
		System.err.println("In DummyMailMap, setSender(" + s + ")");
		this.sender = s;
	}
	
	public String getSender() {
		return this.sender;
	}

	public void setSubject(String s) {
		System.err.println("In DummyMailMap, setSubject(" + s + ")");
		this.subject = s;
	}

	public void setText(String s) {
		System.err.println("In DummyMailMap, setText(" + s + ")");
		this.text = s;
	}

	public void setXslFile(String s) {
		System.err.println("In DummyMailMap, setXslFile(" + s + ")");
	}

	public void kill() {
		
	}

	public void load(Access access) throws MappableException, UserException {
		
	}

	public void store() throws MappableException, UserException {
		String mailStore = DispatcherFactory.getInstance().getNavajoConfig().getRootPath() + "/log/dummymailmap.log";
		try {
			FileWriter fw = new FileWriter(mailStore, true);
			fw.write(this.sender + "," + this.recipients + "," + this.cc + "," + this.bcc + "," + this.subject + "," + this.text + "\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public DummyAttachmentMap[] getMultipleAttachments() {
		return multipleAttachments;
	}

}
