package com.dexels.navajo.camel.processor.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.activation.DataHandler;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.camel.processor.NavajoProcessor;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;

public class NavajoInputProcessorImpl implements Processor, NavajoProcessor {

	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoInputProcessorImpl.class);
	
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
		if(headers!=null && !headers.isEmpty()) {
			Message hd = NavajoFactory.getInstance().createMessage(n, "Headers");
			n.addMessage(hd);
			for (Map.Entry<String, Object> e : headers.entrySet()) {
				Property p = NavajoFactory.getInstance().createProperty(n, e.getKey(), Property.STRING_PROPERTY, null, 0, null, Property.DIR_OUT);
				p.setAnyValue(e.getValue());
			}
		}
		if(body!=null) {
			Message bodyMsg = NavajoFactory.getInstance().createMessage(n, "Body");
			n.addMessage(bodyMsg);
			appendBody(bodyMsg,body);
		}
		if(attachments!=null) {
			Message attachMsg = NavajoFactory.getInstance().createMessage(n, "Attachments");
			n.addMessage(attachMsg);
			for (Map.Entry<String, DataHandler> e : attachments.entrySet()) {
				appendDataHandler(attachMsg,e.getKey(), e.getValue());
			}
		}
		
		in.setBody(n);
		System.err.println("Converted to navajo:");
		n.write(System.err);
	}

	private void appendDataHandler(Message attachMsg, String name, DataHandler value) throws IOException {
		Property p = NavajoFactory.getInstance().createProperty(attachMsg.getRootDoc(), name, Property.BINARY_PROPERTY, null, 0, null, Property.DIR_OUT);
		attachMsg.addProperty(p);
		Binary b = new Binary(value.getInputStream());
		b.setMimeType(value.getContentType());
		p.setAnyValue(b);
	}

	private void appendBody(Message bodyMsg, Object body) {
		Property p = NavajoFactory.getInstance().createProperty(bodyMsg.getRootDoc(), "Content", Property.STRING_PROPERTY, null, 0, null, Property.DIR_OUT);
		bodyMsg.addProperty(p);
		if(body instanceof InputStream) {
			Binary b = new Binary((InputStream) body);
			p.setAnyValue(b);
		}
	}

}
