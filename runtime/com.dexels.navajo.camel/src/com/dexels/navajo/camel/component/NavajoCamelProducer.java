package com.dexels.navajo.camel.component;


import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
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
	
    public NavajoCamelProducer(CamelEndpoint endpoint, LocalClient localClient, String service) {
        super(endpoint);
        this.localClient = localClient;
        this.service = service;
    }

    @Override
	public void process(Exchange exchange) throws Exception {
//    	exchange.get
    	Message in = exchange.getIn();
    	Object rawbody = in.getBody();
    	if(rawbody instanceof Navajo) {
    		Navajo input = (Navajo)rawbody;
    		Header h = input.getHeader();
    		if(h==null) {
    			h = NavajoFactory.getInstance().createHeader(input, service, "R3OT", "stup1d0", -1);
    			input.addHeader(h);
    		} else {
    			h.setRPCName(service);
    			h.setRPCUser("R3OT");
    			h.setRPCPassword("stup1d0");
    		}
    		
    		Navajo result = localClient.call(input);
    		result.write(System.err);
    		exchange.getOut().setBody(result);;
    	} else {
    		logger.warn("Unexpected body in navajo producer: "+rawbody);
    	}
    	
    }

}
