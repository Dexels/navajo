/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.resource.http;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResourceFactory {

	private static HttpResourceFactory instance = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(HttpResourceFactory.class);
	
	private final Map<String,HttpResource> httpResource = new HashMap<String, HttpResource>();
	public void activate() {
		instance = this;
	}

	public void deactivate() {
		instance = null;
	}
	
	public static HttpResourceFactory getInstance() {
		return instance;
	}
	public void addHttpResource(HttpResource resource, Map<String,Object> settings) {
		String name = (String) settings.get("name");
		if(name==null) {
			logger.warn("Can not register http resource: It has no name.");
			return;
		}
		httpResource.put(name, resource);
	}

	public void removeHttpResource(HttpResource resource, Map<String,Object> settings) {
		String name = (String) settings.get("name");
		if(name==null) {
			logger.warn("Can not deregister http resource: It has no name.");
			return;
		}
		httpResource.remove(name);
	}
	
	public HttpResource getHttpResource(String name) {
	    if (!httpResource.containsKey(name)) {
	        logger.warn("Can not locate http resource: {}. Available http resources: {}",name,httpResource.keySet());
	        return null;
	    }
		return httpResource.get(name);
	}
}
