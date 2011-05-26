package com.dexels.navajo.version;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;



public abstract class AbstractVersion extends BaseVersion implements BundleActivator {


	
	protected BundleContext context = null;
	public void start(BundleContext bc) throws Exception {
		context = bc;
		logger.info("Bundle started: "+bc.getBundle().toString());
	}

	public void stop(BundleContext arg0) throws Exception {
		context = null;
		
	}

	@Override
	public int getMajor() {
		return context.getBundle().getVersion().getMajor();
	}

	@Override
	public int getMinor() {
		return context.getBundle().getVersion().getMajor();
	}

	@Override
	public int getPatchLevel() {
		return context.getBundle().getVersion().getMicro();
	}

	@Override
	public String getVendor() {
		return "Dexels";
	}

	@Override
	public String getProductName() {
		return context.getBundle().getSymbolicName();
	}

	public void shutdown() {
		if(context==null) {
			logger.info("No OSGi present.");

		}
		logger.info("Shutting down bundle: "+getClass().getName());
	}
	
	public static double getRandom() {
		// Silly, was getting paranoid. Can be removed now.
		return Math.random();
	}

	  @SuppressWarnings("unchecked")
	public static void shutdownNavajoExtension(String name) {
		  // This should be replaced by OSGi bundle management
		  try {
			Class <? extends AbstractVersion> version = (Class<? extends AbstractVersion>) Class.forName(name.toLowerCase()+".Version");
			AbstractVersion v = version.newInstance();
			v.shutdown();
		  } catch (Throwable e) {
				logger.warn("Extension shutdown failed.",e);
		}
		  
	}

}
