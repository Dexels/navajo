package com.dexels.navajo.example.adapter;

import navajoextension.AbstractCoreExtension;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
public class Activator extends AbstractCoreExtension implements BundleActivator {


	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		ExampleAdapterLibrary library = new ExampleAdapterLibrary();
		registerAll(library);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		super.stop(bundleContext);
	}

}
