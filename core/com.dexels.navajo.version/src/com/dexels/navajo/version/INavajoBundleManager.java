package com.dexels.navajo.version;

import java.io.File;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

public interface INavajoBundleManager {

	public abstract BundleContext getBundleContext();

//	public abstract void loadAdapterPackages(File navajoRoot, BundleContext bc);
	public Bundle locateBundleForClass(String clazz);
	public Class<?> loadClassInAnyBundle(String clazz) throws ClassNotFoundException;
	public void uninstallAdapterBundles();
	public void loadAdapterPackages(File navajoRoot);
			
}