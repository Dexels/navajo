package com.dexels.navajo.camel.component;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.apache.camel.Component;
import org.apache.camel.Consumer;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.camel.component.impl.CamelConsumerImpl;
import com.dexels.navajo.camel.consumer.NavajoCamelConsumer;
import com.dexels.navajo.script.api.LocalClient;

/**
 * Represents the component that manages {@link com.dexels.navajo.camel.componentEndpoint}.
 */
public class NavajoCamelComponent extends DefaultComponent implements Component {

	private LocalClient localClient;
	private BundleContext bundleContext;
	private final Map<String,CamelEndpoint> endPoints = new HashMap<String, CamelEndpoint>();
	private final Map<String,NavajoCamelConsumer> consumers = new HashMap<String, NavajoCamelConsumer>();
	private final Map<String, ServiceRegistration<Endpoint>> endPointRegistrations = new HashMap<String, ServiceRegistration<Endpoint>>();
	private final Map<String, ServiceRegistration<Consumer>> consumerRegistrations = new HashMap<String, ServiceRegistration<Consumer>>();
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoCamelComponent.class);
	
	public LocalClient getLocalClient() {
		return localClient;
	}

	public void activate(BundleContext bc) {
		this.bundleContext = bc;
	}
	
	public void deactivate() {
		logger.info("Shutting down Component");
		try {
			doShutdown();
		} catch (Exception e) {
			logger.warn("Shutting down problem: ",e);
		}
	}
	
	public void setLocalClient(LocalClient lc) {
		this.localClient = lc;
	}

	public void clearLocalClient(LocalClient lc) {
		this.localClient = null;
	}

	@Override
	protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
    	logger.info("uri: "+uri);
    	logger.info("remaining: "+remaining);
    	CamelEndpoint endpoint = new CamelEndpoint(uri, this,localClient,remaining,parameters);
    	registerEndPoint(endpoint, remaining);
        setProperties(endpoint, parameters);
        return endpoint;
    }
	
	private void registerEndPoint(CamelEndpoint e, String id) {
		endPoints.put(id, e);
		ServiceRegistration<Endpoint> reg = bundleContext.registerService(Endpoint.class, e, null);
		endPointRegistrations.put(id, reg);
	}

	public CamelEndpoint getEndpoint(String id) {
		return endPoints.get(id);
	}

	public void registerConsumer(CamelConsumerImpl e, String id) {
		consumers.put(id, e);
		Dictionary<String,Object> so = new Hashtable<String,Object>();
		so.put("type", "navajo");
		so.put("id", id);
		ServiceRegistration<Consumer> reg = (ServiceRegistration<Consumer>) bundleContext.registerService(new String[]{Consumer.class.getName(),NavajoCamelConsumer.class.getName()}, e, so);
		consumerRegistrations.put(id, reg);
	}

	public NavajoCamelConsumer getConsumer(String id) {
		return consumers.get(id);
	}

	
	@Override
	protected void doStop() throws Exception {
		for (ServiceRegistration<Endpoint> e : endPointRegistrations.values()) {
			e.unregister();
		}
		for (ServiceRegistration<Consumer> e : consumerRegistrations.values()) {
			e.unregister();
		}
		super.doStop();
	}


	
	
	
	

	

}
