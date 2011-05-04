package com.dexels.navajo.version;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class NavajoBundleManagerFactory {
	
	private  static BundleContext myContext;
	public static void initialize(BundleContext bc) {
		myContext = bc;
	}
	
	public static INavajoBundleManager getInstance() {
		ServiceReference refs = myContext.getServiceReference(INavajoBundleManager.class.getName());
		return (INavajoBundleManager) myContext.getService(refs);
	}
}
