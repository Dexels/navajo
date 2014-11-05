package com.dexels.navajo.resource.mail;

import java.util.List;

import com.dexels.navajo.adapter.mailmap.AttachmentMapInterface;
import com.dexels.navajo.script.api.UserException;

public interface MailResource {
	public void send(String from, String[] to, String[] cc, String[] bcc, String subject, String body, List<AttachmentMapInterface> attachments, String contentType, boolean relatedMultipart) throws UserException;
}
