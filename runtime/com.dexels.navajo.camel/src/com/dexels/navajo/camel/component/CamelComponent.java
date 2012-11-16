package com.dexels.navajo.camel.component;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

/**
 * Represents the component that manages {@link com.dexels.navajo.camel.componentEndpoint}.
 */
public class CamelComponent extends DefaultComponent {

    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        Endpoint endpoint = new CamelEndpoint(uri, this);
        setProperties(endpoint, parameters);
        return endpoint;
    }
}
