package com.dexels.navajo.tipi.dev.ui;

import org.ops4j.pax.web.extender.whiteboard.ResourceMapping;
import org.ops4j.pax.web.extender.whiteboard.runtime.DefaultResourceMapping;

public class UIResolver implements ResourceMapping {
	private final DefaultResourceMapping resourceMapping = new DefaultResourceMapping();

	
	
	public void activate() {
		resourceMapping.setAlias("/ui");
		resourceMapping.setPath("/ui");
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
