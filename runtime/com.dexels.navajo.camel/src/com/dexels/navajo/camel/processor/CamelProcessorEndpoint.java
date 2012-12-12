package com.dexels.navajo.camel.processor;

import org.apache.camel.impl.ProcessorEndpoint;

import com.dexels.navajo.camel.component.CamelComponent;

public class CamelProcessorEndpoint extends ProcessorEndpoint {
	
//	private final CamelComponent camelComponent;
	
	private final CamelProcessor myProcessor;

	
	public CamelProcessorEndpoint(String uri, CamelComponent cp, CamelProcessor p) {
		super(uri,cp,p);
		this.myProcessor = p;
//		this.camelComponent = cp;
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
