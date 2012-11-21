package com.dexels.navajo.camel.component;


import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The com.dexels.navajo.camel.component producer.
 */
public class CamelProducer extends DefaultProducer {
    private static final transient Logger logger = LoggerFactory.getLogger(CamelProducer.class);
    private CamelEndpoint endpoint;

    public CamelProducer(CamelEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    public void process(Exchange exchange) throws Exception {
        System.out.println(exchange.getIn().getBody());    
    }

}
