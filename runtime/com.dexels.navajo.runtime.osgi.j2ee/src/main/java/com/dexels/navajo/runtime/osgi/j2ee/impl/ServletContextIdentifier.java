package com.dexels.navajo.runtime.osgi.j2ee.impl;

import javax.servlet.ServletContext;

import com.dexels.navajo.osgi.runtime.ContextIdentifier;

public final class ServletContextIdentifier implements ContextIdentifier {
	
	

	private ServletContext context;
	
	public ServletContextIdentifier(ServletContext ctxt) {
		this.context = ctxt;
		context.log("Starting ContextIdentifier with contextpath: "+context.getContextPath());
	}
	
	@Override
	public String getContextPath() {
		context.log("Identifying current context as: "+context.getContextPath());
		return context.getContextPath();
	}
}
