package com.dexels.navajo.version;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;

import com.dexels.navajo.version.AbstractVersion;

public class NavajoBundleManager extends AbstractVersion implements INavajoBundleManager {

	private static INavajoBundleManager instance = null;

	private BundleContext myBundleContext = null;
	private Map<URL,Bundle> bundleMap = new HashMap<URL,Bundle>();

	
	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
		myBundleContext = bc;
		instance = this;
		System.err.println("Starting Dexels version manager1");
		bc.addBundleListener(new BundleListener() {
			
			@Override
			public void bundleChanged(BundleEvent event) {
//				System.err.println("Bundle event: "+event.getType()+" bundle: "+event.getBundle().getSymbolicName()+" id: "+event.getBundle().getBundleId());
			}
		}
		);
		bc.addFrameworkListener(new FrameworkListener() {
			
			@Override
			public void frameworkEvent(FrameworkEvent event) {
//				System.err.println("frameworkEvent: "+displayFrameworkEventType(event.getType())+" bundle: "+event.getBundle().getSymbolicName()+" id: "+event.getBundle().getBundleId());
			
			}
		});
		NavajoBundleManagerFactory.initialize(bc);
		bc.registerService(INavajoBundleManager.class.getName(), this, null);
		System.err.println("NavajoBundleManager... registered");
	}

	private String displayFrameworkEventType(int type) {
		switch (type) {
		case FrameworkEvent.ERROR:
			return "INFO";
		case FrameworkEvent.INFO:
			return "INFO";
		case FrameworkEvent.PACKAGES_REFRESHED:
			return "PACKAGES_REFRESHED";
		case FrameworkEvent.STARTED:
			return "STARTED";
		case FrameworkEvent.STARTLEVEL_CHANGED:
			return "STARTLEVEL_CHANGED";
		case FrameworkEvent.STOPPED:
			return "STOPPED";
		case FrameworkEvent.STOPPED_BOOTCLASSPATH_MODIFIED:
			return "STOPPED_BOOTCLASSPATH_MODIFIED";
		case FrameworkEvent.STOPPED_UPDATE:
			return "STOPPED_UPDATE";
		case FrameworkEvent.WAIT_TIMEDOUT:
			return "WAIT_TIMEDOUT";
		case FrameworkEvent.WARNING:
			return "WARNING";

		default:
			return "UNKNOWN: "+type;
		}
	}
	
	public static boolean usesOSGi() {
		return getInstance()!=null;
	}
	
	public static INavajoBundleManager getInstance() {
		return instance;
	}

	/* (non-Javadoc)
	 * @see dexels.INavajoBundleManager#getBundleContext()
	 */
	@Override
	public BundleContext getBundleContext() {
		return myBundleContext;
	}

	
	public Bundle locateBundleForClass(String clazz) {
		for (Map.Entry<URL,Bundle> e : bundleMap.entrySet() ) {
			Class loaded;
			try {
				loaded = e.getValue().loadClass(clazz);
				if(loaded != null) {
					return e.getValue();
				}
			} catch (ClassNotFoundException e1) {
				System.err.println("Class: "+clazz+" not found in bundle: "+e.getValue().getSymbolicName());
			}
		}
		return null;
	}
	
	public Class loadClassInAnyBundle(String clazz) throws ClassNotFoundException {
		Bundle b = locateBundleForClass(clazz);
		if(b!=null) {
			return b.loadClass(clazz);
		}
		return null;
	}
	
	@Override
	public void stop(BundleContext arg0) throws Exception {
		super.stop(arg0);
		instance = null;
	}
	
	/* (non-Javadoc)
	 * @see dexels.INavajoBundleManager#loadAdapterPackages(java.io.File, org.osgi.framework.BundleContext)
	 */
	@Override
	public void loadAdapterPackages(File navajoRoot) {
		File adapters = new File(navajoRoot,"adapters");
		if(!adapters.exists()) {
			return;
		}
		File[] children = adapters.listFiles();
		for (File file : children) {
			if(file.isFile() && file.getName().endsWith("jar")) {
				try {
					injectJar(file,myBundleContext);
				} catch (BundleException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	public void uninstallAdapterBundles() {
		for (Bundle b : bundleMap.values()) {
			try {
				b.stop();

				System.err.println("Uninstalling bundle: "+b.getBundleId()+" name: "+b.getSymbolicName()+" @ "+b.getLocation());
				b.uninstall();
			} catch (BundleException e) {
				e.printStackTrace();
			}
		}
	}
	

	private void injectJar(File file, BundleContext bc) throws BundleException {
		try {
		
			URL u = file.toURI().toURL();
			// TODO uninstall optional existing bundles
			System.err.println("Checking injection point: "+u);
			Bundle installed = bc.installBundle(u.toString());
			installed.start();
			bundleMap.put(u, installed);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}


}
