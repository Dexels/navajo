package com.dexels.navajo.version;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;



public abstract class AbstractVersion extends BaseVersion implements BundleActivator {


	
	protected BundleContext context = null;
	protected static BundleContext defaultContext = null;
	protected static AbstractVersion instance = null;
	
	public void start(BundleContext bc) throws Exception {
		context = bc;
		defaultContext = bc;
		if(bc==null) {
			logger.debug("Bundle started in non-osgi environment: {}",getClass().getName());
		} else {
			logger.debug("Bundle started: {}",bc.getBundle().toString());
		}
		instance = this;
	}

	public AbstractVersion getActivatorInstance() {
		return instance;
	}
	public BundleContext getBundleContext() {
		return context;
	}

	// TODO test, I am not at all sure if this works well.
	public static BundleContext getDefaultBundleContext() {
		return defaultContext;
	}

	
	public void stop(BundleContext arg0) throws Exception {
		context = null;
		
	}
	
	public boolean hasOSGiContext() {
		return context!=null;
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
