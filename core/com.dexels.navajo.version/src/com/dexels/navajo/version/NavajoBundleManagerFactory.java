package com.dexels.navajo.version;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class NavajoBundleManagerFactory {
	
	private  static BundleContext myContext;
	public static void initialize(BundleContext bc) {
		myContext = bc;
	}
	
	public static INavajoBundleManager getInstance() {
		ServiceReference<INavajoBundleManager> refs = myContext.getServiceReference(INavajoBundleManager.class);
		return myContext.getService(refs);
	}
}
