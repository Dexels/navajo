package com.dexels.navajo.tipi.swing.application;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import tipi.TipiApplicationInstance;

import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.TipiSwingWrapper;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private TipiApplicationInstance instance;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */

	public void start(final BundleContext bc) throws Exception {
		final String context = System.getProperty("tipi.context");
		Activator.context = bc;
		
		Thread t = new Thread() {

			@Override
			public void run() {
				try {
					instance = TipiSwingWrapper.runApp(bc,context);
					instance.getCurrentContext().switchToDefinition(instance.getDefinition());
				} catch (TipiException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		System.err.println("Stopping bundle");
		if(instance!=null) {
			instance.getCurrentContext().shutdown();
			
		}
		Activator.context = null;
		
	}

}
