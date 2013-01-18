package com.dexels.navajo.camel.component;


import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.TmlRunnable;

/**
 * The com.dexels.navajo.camel.component producer.
 */
public class NavajoCamelProcessor extends DefaultProducer {
//    private static final transient Logger logger = LoggerFactory.getLogger(CamelProducer.class);
//    private CamelEndpoint endpoint;

    public NavajoCamelProcessor(CamelEndpoint endpoint) {
        super(endpoint);
//        this.endpoint = endpoint;
    }

    public void process(Exchange exchange) throws Exception {
    	TmlRunnable tr = (TmlRunnable) exchange.getProperty("tmlRunnable");
    	Navajo indoc = (Navajo) exchange.getIn().getBody();
    	tr.setResponseNavajo(indoc);
    	tr.endTransaction();
    }

}
