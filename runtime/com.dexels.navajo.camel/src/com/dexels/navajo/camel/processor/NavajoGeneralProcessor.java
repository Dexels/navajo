package com.dexels.navajo.camel.processor;

import java.util.Map.Entry;
import java.util.Set;

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

public class NavajoGeneralProcessor implements Processor {

	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoGeneralProcessor.class);
	
	public NavajoGeneralProcessor() {
		System.err.println("Creating navajo general processor");
	}
	
	@Override
	public void process(Exchange ex) throws Exception {
		org.apache.camel.Message in = ex.getIn();
		org.apache.camel.Message out = ex.getOut();
		Navajo outputNavajo = NavajoFactory.getInstance().createNavajo();
		out.setBody(outputNavajo);
		
		Message headers = NavajoFactory.getInstance().createMessage(outputNavajo, "Headers");
		outputNavajo.addMessage(headers);
		for (Entry<String,Object> ie : in.getHeaders().entrySet()) {
			Property p = NavajoFactory.getInstance().createProperty(outputNavajo, ie.getKey(),Property.STRING_PROPERTY, "",0, "", Property.DIR_IN);
			p.setAnyValue(ie.getValue());
			headers.addProperty(p);
		}
		Message attachments = NavajoFactory.getInstance().createMessage(outputNavajo, "Attachments");
		outputNavajo.addMessage(attachments);
		final Set<Entry<String, DataHandler>> attachmentSet = in.getAttachments().entrySet();
		logger.info("# of attachments: {}",attachmentSet.size());
		for (Entry<String,DataHandler> ie : attachmentSet) {
			Property p = NavajoFactory.getInstance().createProperty(outputNavajo, ie.getKey(),Property.STRING_PROPERTY, "",0, "", Property.DIR_IN);
			Binary b = new Binary(ie.getValue().getInputStream());
			p.setAnyValue(b);
			attachments.addProperty(p);
		}
		
		Message body = NavajoFactory.getInstance().createMessage(outputNavajo, "Body");
		outputNavajo.addMessage(body);
		Property p = NavajoFactory.getInstance().createProperty(outputNavajo, "Body",Property.STRING_PROPERTY, "",0, "", Property.DIR_IN);
		p.setAnyValue(in.getBody());
		body.addProperty(p);
		
		logger.info("Processed cammel interaction. ");
		outputNavajo.write(System.err);
		
		
	}

}
