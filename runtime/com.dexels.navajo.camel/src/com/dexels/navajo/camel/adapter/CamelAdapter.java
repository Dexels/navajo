package com.dexels.navajo.camel.adapter;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import navajocamel.Version;

import org.apache.camel.CamelContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.mapping.DependentResource;
import com.dexels.navajo.mapping.GenericDependentResource;
import com.dexels.navajo.mapping.HasDependentResources;
import com.dexels.navajo.mapping.compiler.meta.AdapterResourceDependency;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class CamelAdapter implements Mappable, HasDependentResources {

	private final Set<ServiceReference<CamelContext>> references = new HashSet<ServiceReference<CamelContext>>();

//	private String camelName;

	private String endpointName;

	private CamelContext camelContext = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(CamelAdapter.class);
	
	private String propertyName = null;
	private final Map<String,Object> properties = new HashMap<String,Object>();

	private String resource;
	
	@Override
	public void load(Access access) throws MappableException, UserException {
		logger.info("CamelAdapter load");
	}

	public void setResource(String resourceName) throws UserException {
		this.resource = resourceName;
		this.camelContext  = getCamelContext(resourceName);
		
	}
	
	public void setPropertyName(String name) {
		this.propertyName = name;
	}

	public void setPropertyValue(Object value) {
		properties.put(propertyName, value);
	}
	
	@Override
	public void store() throws MappableException, UserException {
		for (ServiceReference<CamelContext> ref : references) {
			Version.getDefaultBundleContext().ungetService(ref);
		}
		logger.info("CamelAdapter store");
	}

	@Override
	public void kill() {
		for (ServiceReference<CamelContext> ref : references) {
			Version.getDefaultBundleContext().ungetService(ref);
		}
		logger.info("CamelAdapter kill");
	}

	@Override
	public DependentResource[] getDependentResourceFields() {
		return new DependentResource[] {
				new GenericDependentResource("resource", "resource",
						AdapterResourceDependency.class)
		};
	}

	
	public void setEndpointName(String endpointName) {
		this.endpointName = endpointName;
	}

	
	public void setBody(Object body) throws UserException {
		logger.debug("Sending body!");
		if(resource==null) {
			throw new UserException(-1,"Error setting body: call setResource first!");
		}
		camelContext.createProducerTemplate().sendBodyAndHeaders(endpointName, body, properties);
//		camelContext.createProducerTemplate().sendBody(endpointName, body);
		logger.info("Body sent to camelContext: {}", resource);
	}
	
	public CamelContext getCamelContext(String name) throws UserException {
		final String filter = "(camel.context.name="+name+")";
		try {
			Collection<ServiceReference<CamelContext>> sr = Version.getDefaultBundleContext().getServiceReferences(CamelContext.class, filter);
			if(sr.isEmpty()) {
				throw new UserException(-1, "No camelcontext found with name: "+name);
			}
			ServiceReference<CamelContext> s = sr.iterator().next();
			references.add(s);
			return Version.getDefaultBundleContext().getService(s);
		} catch (Exception e) {
			logger.error("Camel problem:",e);
			throw new UserException(-1, "Error applying filter: "+filter,e);
		}
	}
}
