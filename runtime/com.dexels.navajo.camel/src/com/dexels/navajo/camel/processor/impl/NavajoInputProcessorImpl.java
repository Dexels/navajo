package com.dexels.navajo.camel.processor.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.activation.DataHandler;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;

public class NavajoInputProcessorImpl implements Processor {

	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoInputProcessorImpl.class);
	private Binary inputBinary;
	
	public NavajoInputProcessorImpl() {
		logger.warn("Navajo input processor present!");
	}

	@Override
	public void process(Exchange ex) throws Exception {
		logger.warn("Start proccing NavajoInput");
		org.apache.camel.Message in = ex.getIn();
		Map<String,Object> headers = in.getHeaders();
		Object body = in.getBody();
		Map<String,DataHandler> attachments = in.getAttachments();
		Navajo n = NavajoFactory.getInstance().createNavajo();
//		javax.mail.Message m;
		
		if(headers!=null && !headers.isEmpty()) {
			Message hd = NavajoFactory.getInstance().createMessage(n, "Headers");
			n.addMessage(hd);
			for (Map.Entry<String, Object> e : headers.entrySet()) {
				Property p = NavajoFactory.getInstance().createProperty(n, e.getKey(), Property.STRING_PROPERTY, null, 0, null, Property.DIR_OUT);
				p.setAnyValue(e.getValue());
				hd.addProperty(p);
			}
		}
		if(body!=null) {
			Message bodyMsg = NavajoFactory.getInstance().createMessage(n, "Body");
			n.addMessage(bodyMsg);
			appendBody(bodyMsg,body);
		}
		Message attachMsg = NavajoFactory.getInstance().createMessage(n, "Attachments", Message.MSG_TYPE_ARRAY);
		n.addMessage(attachMsg);
		if(attachments!=null) {
			for (Map.Entry<String, DataHandler> e : attachments.entrySet()) {
				appendDataHandler(attachMsg,e.getKey(), e.getValue());
			}
		}
//		GetMailNavajo gmn = new GetMailNavajo();
//		gmn.evaluate(inputBinary, attachMsg);
		in.setBody(n);
//		System.err.println("Converted to navajo:");
//		n.write(System.err);
	}

	private void appendDataHandler(Message attach, String name, DataHandler value) throws IOException {
		Message attachMsg = attach.addElement(NavajoFactory.getInstance().createMessage(attach.getRootDoc(), attach.getName()));
		Property content = NavajoFactory.getInstance().createProperty(attachMsg.getRootDoc(), "Content", Property.BINARY_PROPERTY, null, 0, null, Property.DIR_OUT);
		attachMsg.addProperty(content);
		
		Binary b = new Binary(value.getInputStream());
		b.setMimeType(value.getContentType());
		content.setAnyValue(b);

		Property contentType = NavajoFactory.getInstance().createProperty(attachMsg.getRootDoc(), "ContentType", Property.STRING_PROPERTY, value.getContentType(), 0, null, Property.DIR_OUT);
		attachMsg.addProperty(contentType);

		Property attachName = NavajoFactory.getInstance().createProperty(attachMsg.getRootDoc(), "Name", Property.STRING_PROPERTY, name, 0, null, Property.DIR_OUT);
		attachMsg.addProperty(attachName);

	}

	private void appendBody(Message bodyMsg, Object body) {
		Property p = NavajoFactory.getInstance().createProperty(bodyMsg.getRootDoc(), "Content", Property.STRING_PROPERTY, null, 0, null, Property.DIR_OUT);
		bodyMsg.addProperty(p);
		if(body instanceof InputStream) {
			inputBinary = new Binary((InputStream) body);
			p.setAnyValue(inputBinary);
		}
	}

}
