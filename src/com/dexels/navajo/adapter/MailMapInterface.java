package com.dexels.navajo.adapter;

import com.dexels.navajo.adapter.mailmap.AttachmentMapInterface;

public interface MailMapInterface {

	public void setIgnoreFailures(boolean b);
	public void setRecipients(String s) ;
	public void setSender(String s) ;
	public void setMailServer(String s);
	public void setSubject(String s);
	public void setContentType(String s);
	public void setXslFile(String s);
	public void setText(String s);
	public void setBcc(String bcc);
	public void setCc(String cc);
	public void setMultipleAttachments(AttachmentMapInterface[] c);
	public void setRelatedMultipart(boolean b);
	public void setAttachment(AttachmentMapInterface m);
	
}
