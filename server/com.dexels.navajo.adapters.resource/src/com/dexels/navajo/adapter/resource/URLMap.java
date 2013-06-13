package com.dexels.navajo.adapter.resource;

import navajoadaptersresource.Version;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.dexels.navajo.adapter.HTTPMap;
import com.dexels.navajo.http.HTTPMapInterface;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.resource.http.HttpResource;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.UserException;

public class URLMap extends HTTPMap implements HTTPMapInterface, Mappable {

	private static final long serialVersionUID = 5960405491845545518L;
	
	private final static Logger logger = LoggerFactory.getLogger(URLMap.class);
	private ServiceReference httpResourceReference = null;
	private HttpResource httpResource;

	
	@Override
	public void load(Access access) throws MappableException, UserException {
		super.load(access);
	}

	@Override
	public void store() throws MappableException, UserException {
		if(httpResourceReference!=null) {
			Version.getDefaultBundleContext().ungetService(httpResourceReference);
			httpResourceReference = null;
		}
		super.store();
	}

	@Override
	public void kill() {
		if(httpResourceReference!=null) {
			Version.getDefaultBundleContext().ungetService(httpResourceReference);
			httpResourceReference = null;
		}
		super.kill();
	}
	
	public void setDoSend(boolean b) throws UserException {
		url = httpResource.getURL();
		super.setDoSend(b);
	}
	
	public void setResourceName(String name) {
		this.httpResource = getComponent("navajo.resource."+name, "name", HttpResource.class);
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> T getComponent( final String name, String serviceKey, Class<T> interfaceClass)  {
		BundleContext context = Version.getDefaultBundleContext();
		try {
			ServiceReference[] refs = context.getServiceReferences(interfaceClass.getName(), "("+serviceKey+"="+name+")");
			if(refs==null) {
				logger.error("Service resolution failed: Query: "+"("+serviceKey+"="+name+")"+" class: "+interfaceClass.getName());
				return null;
			}
			if(refs.length==0) {
				logger.error("Service resolution worked but no match: Query: "+"("+serviceKey+"="+name+")"+" class: "+interfaceClass.getName());
				return null;
			}
			httpResourceReference = refs[0];
			return (T) context.getService(httpResourceReference);
			
		} catch (InvalidSyntaxException e) {
			logger.error("Error: ", e);
		}
		return null;
	}
}
