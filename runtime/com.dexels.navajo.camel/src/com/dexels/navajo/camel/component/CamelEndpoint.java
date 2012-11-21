package com.dexels.navajo.camel.component;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;

/**
 * Represents a com.dexels.navajo.camel.component endpoint.
 */
public class CamelEndpoint extends DefaultEndpoint {

    public CamelEndpoint() {
    	System.err.println("Endpoint created");
    }

    public CamelEndpoint(String uri, CamelComponent component) {
        super(uri, component);
    	System.err.println("Endpoint created with URI: "+uri);
    }

    public Producer createProducer() throws Exception {
        return new CamelProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        return new CamelConsumer(this, processor);
    }

    public boolean isSingleton() {
        return true;
    }
}
