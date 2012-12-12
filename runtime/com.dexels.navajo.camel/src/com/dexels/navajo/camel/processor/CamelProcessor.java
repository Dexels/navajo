package com.dexels.navajo.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.camel.component.CamelComponent;
import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public class CamelProcessor implements Processor {

	
	private final static Logger logger = LoggerFactory
			.getLogger(CamelProcessor.class);
	private final CamelComponent camelComponent;
	private String service;
	private String username;
	private String password;
	

	public CamelProcessor() {
		System.err.println("Starting processor without component");
		this.camelComponent = null;
	}
	public CamelProcessor(CamelComponent cc) {
		this.camelComponent = cc;
	}
	
	@Override
	public void process(Exchange exchange) throws Exception {
		Message in = exchange.getIn();
//		String service = (String) in.getHeader("service");
		
		if(in.getBody()==null) {
			logger.error("No input in camel processor");
			Navajo n = NavajoFactory.getInstance().createNavajo();
			Header h = NavajoFactory.getInstance().createHeader(n, service, this.username, this.password, -1);
			n.addHeader(h);
			in.setBody(n);
		}
		
		Navajo input = (Navajo) in.getBody();
		logger.info("Should be calling service: "+service);
		
		Navajo out = camelComponent.getLocalClient().call(input);
		logger.info("Result available. Navajo: ");
		exchange.getOut().setBody(out);
		out.write(System.err);
	}

	public void setService(String service) {
		this.service = service;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
