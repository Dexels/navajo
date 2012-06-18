package com.dexels.navajo.runtime.homecontext.impl;

import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.runtime.homecontext.ContextIdentifier;

public class SystemPropertyContextIdentifier implements ContextIdentifier {

	

	private final static Logger logger = LoggerFactory
			.getLogger(SystemPropertyContextIdentifier.class);
	private String componentName;
	private ComponentContext componentContext = null;
	public void activate(ComponentContext cc) {
		componentName = (String) cc.getProperties().get("component.name");
		this.componentContext = cc;
		String myContext = getContextPath();
		logger.info("Starting system property identifier");
//		if(myContext==null) {
//			cc.disableComponent(componentName);
//		}
	}

	
	
	@Override
	public String getContextPath() {
		String context = System.getProperty("navajo.context");
		
		return context;
	}

}
