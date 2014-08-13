package com.dexels.navajo.camel.component;


import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.component.http.HttpOperationFailedException;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;

/**
 * The com.dexels.navajo.camel.component producer.
 * This one should only be used as 'sink'
 */
public class NavajoCamelProducer extends DefaultProducer {
//    private static final transient Logger logger = LoggerFactory.getLogger(CamelProducer.class);
//    private CamelEndpoint endpoint;
	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoCamelProducer.class);
	
	final LocalClient localClient;

	private final String service;
	private final String username;
	private final String password;
	
    public NavajoCamelProducer(CamelEndpoint endpoint, LocalClient localClient, String service, String username, String password) {
        super(endpoint);
        this.localClient = localClient;
        this.service = service;
        this.username = username;
        this.password = password;
    }

    @Override
	public void process(Exchange exchange) throws Exception {
//    	exchange.get
    	Message in = exchange.getIn();
    	Object rawbody = in.getBody();
    	if(rawbody instanceof Navajo) {
    		Navajo navajoDoc = (Navajo) rawbody;
    		setupHeader(navajoDoc);
    		
    		// Check for errors - if so add Error message
    		Object exCaught = exchange.getProperty("CamelExceptionCaught");
    		if (exCaught != null) {
    			// Somewhere in the camel route, an exception took place. We add a message to the Navajo input
    			// with information about the caught exception
    			Exception e1 = (Exception) exCaught;
				com.dexels.navajo.document.Message errorMessage = NavajoFactory.getInstance()
						.createMessage(navajoDoc, "Errors");
				navajoDoc.addMessage(errorMessage);
				errorMessage.addProperty(NavajoFactory.getInstance().createProperty(navajoDoc, "Error",
						"boolean", "true", 1, null, null));
				errorMessage.addProperty(NavajoFactory.getInstance().createProperty(navajoDoc,
						"Exception", "String", e1.getClass().getName(), 1, null, null));
				errorMessage.addProperty(NavajoFactory.getInstance().createProperty(navajoDoc,
						"Message", "String", e1.getMessage(), 1, null, null));
				
				if (exCaught instanceof HttpOperationFailedException) {
					errorMessage.addProperty(NavajoFactory.getInstance().createProperty(navajoDoc,
							"HttpBody", "String", ((HttpOperationFailedException) exCaught).getResponseBody(), 1, null, null));
				}
				

    			System.err.println(exCaught);
    		}
    		
    		// Check for errors - if so add Error message
    		Object origBody = exchange.getProperty("origBody");
    		if (origBody != null) {
    			Navajo origNavajo = (Navajo) origBody;
    			com.dexels.navajo.document.Message inputMessage = NavajoFactory.getInstance().createMessage(navajoDoc, "_Input");
    			navajoDoc.addMessage(inputMessage);
    			for (com.dexels.navajo.document.Message m : origNavajo.getAllMessages()) {
    				inputMessage.addMessage(m);
    			}
    		}
    		
    		Navajo result = localClient.call(navajoDoc);
    		if (result.getMessage("error") != null) {
    			// An exception occured in handling Navajo!
    			logger.error("Error in handling Camel navajo: {}", rawbody);
    		}
    		//result.write(System.err);
    		exchange.getOut().setBody(result);
    	} else {
    		logger.warn("Unexpected body in navajo producer: "+rawbody);
    	}
    	
    }

	private void setupHeader(Navajo input) {
		Header h = input.getHeader();
		if(h==null) {
			h = NavajoFactory.getInstance().createHeader(input, service,username, password, -1);
			input.addHeader(h);
		} else {
			h.setRPCName(service);
			h.setRPCUser(username);
			h.setRPCPassword(password);
		}
	}

}
