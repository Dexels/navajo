package com.dexels.navajo.tipi.vaadin.embedded.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.vaadin.embedded.JettyServer;

public class Activator implements BundleActivator {

	private static BundleContext context;

	private final static String DEFAULTCONTEXTPATH = "/oao";
	private final static int DEFAULTPORT = 9090;

	
	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
//		URL u = bundleContext.getBundle().getResource("VAADIN/themes/base/styles.css");
//		logger.info("u: "+u);

		String portString = System.getProperty("tipi.vaadin.embedded.port");
		int port;
		if (portString==null) {
			port = DEFAULTPORT;
		} else {
			port = Integer.parseInt(portString);
		}
		String context = System.getProperty("tipi.vaadin.contextpath");
		if(context==null) {
			context = DEFAULTCONTEXTPATH;
		}
		JettyServer js = new JettyServer();
		js.init(port,context,bundleContext.getBundle());
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
