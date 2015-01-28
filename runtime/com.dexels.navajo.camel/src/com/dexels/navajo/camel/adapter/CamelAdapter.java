package com.dexels.navajo.camel.adapter;

import java.io.StringReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import navajocamel.Version;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class CamelAdapter implements Mappable  {

	private String endPointUri;
	private String contextName;
	private Access myAccess;
	private String propertyName = null;
	private final Map<String, Object> properties = new HashMap<String, Object>();
	private Set<ServiceReference<CamelContext>> references = new HashSet<ServiceReference<CamelContext>>();
	private String rpcName = "";
	private boolean async = true;
	 protected Navajo inDoc;

	private final static Logger logger = LoggerFactory
			.getLogger(CamelAdapter.class);

	
	@Override
	public void load(Access access) throws MappableException, UserException {
		myAccess = access;
		
	}

	@Override
	public void store() throws MappableException, UserException {
		CamelContext context = getCamelContext(contextName);
		try {
			context.start();
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
		ProducerTemplate producer = context.createProducerTemplate();
		
		Navajo navajoDoc = generateNavajo();
		
		if (async) {
			 producer.sendBodyAndHeaders(endPointUri, navajoDoc, properties);
		     logger.info("Body sent to camelContext: {}", endPointUri);
		} else {
		     String response = producer.requestBodyAndHeaders(endPointUri, navajoDoc, properties, String.class);
		     logger.info("Body sent to camelContext: {}", endPointUri);
			 try {
				StringReader r = new StringReader(response);
				inDoc = NavajoFactory.getInstance().createNavajo(r);
			 } catch (Exception e) {
					logger.error("Error in processing Camel reply - maybe not in navajo format?");
					throw new UserException("Error in processing Camel reply - maybe not in navajo format?", e);
				}

				myAccess.setOutputDoc(inDoc);
			
		}
		ungetServices();
	}

	private Navajo generateNavajo() {
		Navajo navajoDoc = myAccess.getInDoc();
		if (navajoDoc.getMessage("__globals__") != null) {
			navajoDoc.removeMessage("__globals__");
		}
		if (navajoDoc.getMessage("__parms__") != null) {
			navajoDoc.removeMessage("__parms__");
		}
		
		Header header = NavajoFactory.getInstance().createHeader(navajoDoc, rpcName, myAccess.rpcUser,myAccess.rpcPwd, -1);
		
		for (String key : properties.keySet()) {
			header.setHeaderAttribute(key, (String) properties.get(key));
		}
		
		navajoDoc.addHeader(header);
		return navajoDoc;
	}

	@Override
	public void kill() {
		ungetServices();
		logger.info("CamelAdapter kill");
	}

	private void ungetServices() {
		for (ServiceReference<CamelContext> ref : references) {
			Version.getDefaultBundleContext().ungetService(ref);
		}
	}

	public String getEndpointName() {
		return endPointUri;
	}

	public void setEndpointName(String endpointName) {
		this.endPointUri = endpointName;
	}

	
	
	public String getContextName() {
		return contextName;
	}

	public void setContextName(String contextName) {
		this.contextName = contextName;
	}
	
	public void setPropertyName(String name) {
		this.propertyName = name;
	}

	public void setPropertyValue(Object value) {
		properties.put(propertyName, value);
	}
	
	public String rpcName() {
		return rpcName;
	}
	
	public void setRpcName(String rpcName) {
		this.rpcName = rpcName;
	}
	
	public boolean async() {
		return this.async;
	}
	
	public void setAsync(boolean async) {
		this.async = async;
	}

	public CamelContext getCamelContext(String name) throws UserException {
		
		String filter = null;
		if (name == null) {
			// Default context
			name = "default";
		}
		filter = "(camel.context.name=" + name + ")";

		try {
			Collection<ServiceReference<CamelContext>> sr = Version.getDefaultBundleContext()
					.getServiceReferences(CamelContext.class, filter);
			if (sr.isEmpty()) {
				throw new UserException(-1, "No camelcontext found with name: " + name);
			}
			ServiceReference<CamelContext> s = sr.iterator().next();
			references.add(s);
			return Version.getDefaultBundleContext().getService(s);
		} catch (Exception e) {
			logger.error("Camel problem:", e);
			throw new UserException(-1, "Error applying filter: " + filter, e);
		}
	}

	
}
