package com.dexels.navajo.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.core.osgi.OsgiDefaultCamelContext;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class CamelStarter {
	private final static Logger logger = LoggerFactory
			.getLogger(CamelStarter.class);

	public void activate(BundleContext bc) {
		CamelContext camelContext = new OsgiDefaultCamelContext(bc); 
	}
	
}
