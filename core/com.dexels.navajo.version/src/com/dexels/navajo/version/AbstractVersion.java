package com.dexels.navajo.version;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


/**
 * Is no more abstract, should refactor FIXME
 * @author frank
 *
 */
public  class AbstractVersion extends BaseVersion implements BundleActivator {


	
	protected BundleContext context = null;
	
	public void start(BundleContext bc) throws Exception {
		context = bc;
//		defaultContext = bc;
		if(bc==null) {
			logger.debug("Bundle started in non-osgi environment: {}",getClass().getName());
		} else {
			logger.debug("Bundle started: {}",bc.getBundle().toString());
		}
	}


	public BundleContext getBundleContext() {
		return context;
	}

	public void stop(BundleContext arg0) throws Exception {
		context = null;
		
	}
	
	public boolean hasOSGiContext() {
		return context!=null;
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
		  logger.warn("non-OSGi Extension shutdown of: "+name);
		  try {
			Class <? extends AbstractVersion> version = (Class<? extends AbstractVersion>) Class.forName(name.toLowerCase()+".Version");
			AbstractVersion v = version.newInstance();
			v.shutdown();
		  } catch (Throwable e) {
				logger.warn("Extension shutdown failed.",e);
		}
		  
	}

}
