package com.dexels.navajo.dsl.model.tsl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class TslModelActivator implements BundleActivator {

	@Override
	public void start(BundleContext bc) throws Exception {
		System.err.println("Activating model!");
		TslPackage.eINSTANCE.eAdapters();
	}

	@Override
	public void stop(BundleContext bc) throws Exception {
		// TODO Auto-generated method stub

	}

}
