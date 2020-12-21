/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.version;


import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public  class AbstractVersion implements BundleActivator {


	
	private static final Logger logger = LoggerFactory.getLogger(AbstractVersion.class);

	
	protected BundleContext context = null;
	
	@Override
	public void start(BundleContext bc) throws Exception {
		context = bc;
		if(bc==null) {
			logger.debug("Bundle started in non-osgi environment: {}",getClass().getName());
		}
	}

	
	
	public static boolean osgiActive() {
		try {
			Bundle b = org.osgi.framework.FrameworkUtil.getBundle(AbstractVersion.class);
			
			return b!=null;
		} catch (Exception t) {
			return false;
		}
	}
	
	
	public BundleContext getBundleContext() {
		return context;
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		context = null;
		
	}

	public void shutdown() {
		if(context==null) {
			logger.info("No OSGi present.");

		}
		logger.info("Shutting down bundle: {}",getClass().getName());
	}
	

	@SuppressWarnings("unchecked")
	public static void shutdownNavajoExtension(String name) {
		  // This should be replaced by OSGi bundle management
		  logger.warn("non-OSGi Extension shutdown of: {}",name);
		  try {
			Class <? extends AbstractVersion> version = (Class<? extends AbstractVersion>) Class.forName(name.toLowerCase()+".Version");
			AbstractVersion v = version.getDeclaredConstructor().newInstance();
			v.shutdown();
		  } catch (Exception e) {
				logger.warn("Extension shutdown failed.",e);
		}
		  
	}

}
