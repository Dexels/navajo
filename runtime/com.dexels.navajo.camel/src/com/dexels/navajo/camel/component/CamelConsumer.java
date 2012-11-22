package com.dexels.navajo.camel.component;

import org.apache.camel.Consumer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The com.dexels.navajo.camel.component consumer.
 */
public class CamelConsumer extends DefaultConsumer implements Consumer {
    private final CamelEndpoint endpoint;
    
	private final static Logger logger = LoggerFactory
			.getLogger(CamelConsumer.class);
    public CamelConsumer(CamelEndpoint endpoint, Processor processor) {
    	super(endpoint, processor);
        
    	this.endpoint = endpoint;
    	logger.info("Created standard consumer");
    }
    

    public void process() throws Exception {
    	Exchange ex = endpoint.createExchange();
    	getProcessor().process(ex);
    }


}
