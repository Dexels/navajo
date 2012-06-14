package com.dexels.navajo.runtime.homecontext;

import org.osgi.service.component.ComponentContext;

public class SystemPropertyContextIdentifier implements ContextIdentifier {

	private String componentName;
	private ComponentContext componentContext = null;
	public void activate(ComponentContext cc) {
		componentName = (String) cc.getProperties().get("component.name");
		this.componentContext = cc;
		String myContext = getContextPath();
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
