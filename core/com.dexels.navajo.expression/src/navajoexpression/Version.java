package navajoexpression;

import navajoextension.AbstractCoreExtension;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;


public class Version extends AbstractCoreExtension {

	private static BundleContext bundleContext;
	


	@Override
	public void start(BundleContext bc) throws Exception {
			super.start(bc);
			bundleContext = bc;
	}

	@Override
	public void shutdown() {
		super.shutdown();
		if(!osgiActive()) {
		}
		  
	
	}


	

	@Override
	public void stop(BundleContext arg0) throws Exception {
		super.stop(arg0);
		bundleContext = null;
	}
	
	public static BundleContext getDefaultBundleContext() {
		Bundle b = org.osgi.framework.FrameworkUtil.getBundle(Version.class);
		if(b!=null) {
			return b.getBundleContext();
		}
		return bundleContext;
	}
}
