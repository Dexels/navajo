package com.dexels.navajo.article.ui;

import org.ops4j.pax.web.extender.whiteboard.ResourceMapping;
import org.ops4j.pax.web.extender.whiteboard.runtime.DefaultResourceMapping;

public class UIResolver implements ResourceMapping {
	private final DefaultResourceMapping resourceMapping = new DefaultResourceMapping();

	
	
	public void activate() {
		resourceMapping.setAlias("/web");
		resourceMapping.setPath("article/ui");
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
