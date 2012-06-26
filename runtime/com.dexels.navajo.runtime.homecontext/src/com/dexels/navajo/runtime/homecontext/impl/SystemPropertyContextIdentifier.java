package com.dexels.navajo.runtime.homecontext.impl;

import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.osgi.runtime.ContextIdentifier;


public class SystemPropertyContextIdentifier implements ContextIdentifier {

	

	private final static Logger logger = LoggerFactory
			.getLogger(SystemPropertyContextIdentifier.class);
	private String componentName;
	private ComponentContext componentContext = null;
	public void activate(ComponentContext cc) {
		componentName = (String) cc.getProperties().get("component.name");
		this.componentContext = cc;
		String myContext = getContextPath();
		logger.info("Starting system property identifier. Using context: "+myContext);
		if(myContext==null) {
			cc.disableComponent(componentName);
			logger.warn("No system property found: navajo.context, so disabling SystemPropertyContextIdentifier");
		}
//		componentContext.disableComponent(c)
	}

	public void deactivate() {
		logger.info("SystemPropertyContextIdentifier is disabling");
	}
	
	@Override
	public String getContextPath() {
		String context = System.getProperty("navajo.context");
		System.err.println("***** **** System context: "+context);
		if(context==null) {
			System.err.println("<> assuming /navajodeploy");
			return "/navajodeploy";
		}
		return context;
	}

}
