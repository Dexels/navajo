package com.dexels.navajo.dsl.expression.proposals;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class NavajoManagerActivator implements BundleActivator {

	private static NavajoManagerActivator INSTANCE;
	
	@Override
	public void start(BundleContext arg0) throws Exception {
		INSTANCE = this;

	}

	@Override
	public void stop(BundleContext arg0) throws Exception {

	}

	public static NavajoManagerActivator getInstance() {
		return INSTANCE;
	}
}
