package com.dexels.navajo.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.ProcessorEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.camel.component.CamelComponent;
import com.dexels.navajo.document.Navajo;

public class CamelProcessorEndpoint extends ProcessorEndpoint {
	
	private final CamelComponent camelComponent;
	
	private final static Logger logger = LoggerFactory
			.getLogger(CamelProcessorEndpoint.class);
	private final CamelProcessor myProcessor;

	
	public CamelProcessorEndpoint(String uri, CamelComponent cp, CamelProcessor p) {
		super(uri,cp,p);
		this.myProcessor = p;
		this.camelComponent = cp;
	}
	
	public void setService(String service) {
		myProcessor.setService(service);
	}

	public void setUsername(String username) {
		myProcessor.setUsername(username);
	}
	public void setPassword(String password) {
		myProcessor.setPassword(password);
	}

}
