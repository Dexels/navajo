package com.dexels.navajo.camel.component;

import java.util.Map;

import org.apache.camel.Consumer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.camel.component.impl.CamelConsumerImpl;
import com.dexels.navajo.camel.message.NavajoMessage;
import com.dexels.navajo.script.api.LocalClient;

/**
 * Represents a com.dexels.navajo.camel.component endpoint.
 */
public class CamelEndpoint extends DefaultEndpoint {

//	private String instance;
	private final LocalClient localclient;

	private final static Logger logger = LoggerFactory
			.getLogger(CamelEndpoint.class);

	private final NavajoCamelComponent myComponent;

	private final String service;
	private String username;
	private String password;
	


	private final Map<String, Object> parameters;
	
	public CamelEndpoint(String uri, NavajoCamelComponent component, LocalClient localClient, String service, Map<String, Object> parameters) {
		super(uri, component);
		this.localclient = localClient;
		this.service = service;
		this.parameters = parameters;
		myComponent = component;
		logger.info("Endpoint created with URI: " + uri);
	}

	public void setInstance(String instance) {
		logger.info("Instance set to: " + instance);
//		this.instance = instance;
	}

	@Override
	public Producer createProducer() throws Exception {
		return new NavajoCamelProducer(this,localclient,service,username,password);
	}

	@Override
	public Consumer createConsumer(Processor processor) throws Exception {
		final CamelConsumerImpl camelConsumerImpl = new CamelConsumerImpl(this, processor);
		myComponent.registerConsumer(camelConsumerImpl, getId());
		return camelConsumerImpl;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}


	@Override
	public Exchange createExchange() {
		Exchange e = super.createExchange();
		e.setIn(new NavajoMessage());
		return e;
		
	}
	

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
