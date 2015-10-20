package com.dexels.navajo.tester.js.servlet;

import java.util.Map;
import org.ops4j.pax.web.extender.whiteboard.ResourceMapping;
import org.ops4j.pax.web.extender.whiteboard.runtime.DefaultResourceMapping;

public class NavajoTesterResourceResolver  implements ResourceMapping {
	 
	private final DefaultResourceMapping resourceMapping = new DefaultResourceMapping();
	
	public void activate(Map<String, Object> settings) {
		resourceMapping.setAlias("/");
		resourceMapping.setPath("resources");
	}


	@Override
	public String getAlias() {
		return resourceMapping.getAlias();
	}


	@Override
	public String getHttpContextId() {
		return resourceMapping.getHttpContextId();
	}


	@Override
	public String getPath() {
		return resourceMapping.getPath();
	}

}
