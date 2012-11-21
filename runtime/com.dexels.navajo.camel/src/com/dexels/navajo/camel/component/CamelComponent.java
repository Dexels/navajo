package com.dexels.navajo.camel.component;

import java.util.Map;

import org.apache.camel.Component;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.apache.camel.impl.ProcessorEndpoint;

import com.dexels.navajo.camel.processor.CamelProcessor;
import com.dexels.navajo.camel.processor.CamelProcessorEndpoint;
import com.dexels.navajo.script.api.LocalClient;

/**
 * Represents the component that manages {@link com.dexels.navajo.camel.componentEndpoint}.
 */
public class CamelComponent extends DefaultComponent implements Component {

	private LocalClient localClient;

	public LocalClient getLocalClient() {
		return localClient;
	}

	public void setLocalClient(LocalClient lc) {
		this.localClient = lc;
	}

	public void clearLocalClient(LocalClient lc) {
		this.localClient = null;
	}

	protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
    	System.err.println("uri: "+uri);
    	System.err.println("remaining: "+remaining);
    	if(remaining.equals("call")) {
    		CamelProcessor cp = new CamelProcessor(this);
    		ProcessorEndpoint pe = new CamelProcessorEndpoint(uri,this, cp);
    		return pe;
    	}
    	if(remaining.equals("call")) {
    	}
    	Endpoint endpoint = new CamelEndpoint(uri, this);
        setProperties(endpoint, parameters);
        return endpoint;
    }


}
