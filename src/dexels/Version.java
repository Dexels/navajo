package dexels;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Version extends BaseVersion implements BundleActivator {

	protected BundleContext context = null;
	public void start(BundleContext bc) throws Exception {
		context = bc;
		ServiceReference a = null;// bc.getServiceReference(HttpService.class.getName());
		System.err.println("Bundle started: "+bc.getBundle().toString());
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
			System.err.println("No OSGi present.");
		}
		System.err.println("Shutting down bundle: "+getClass().getName());
	}
	

	  @SuppressWarnings("unchecked")
	public static void shutdownNavajoExtension(String name) {
		  // This should be replaced by OSGi bundle management
		  try {
			Class <? extends dexels.Version> version = (Class<? extends dexels.Version>) Class.forName(name.toLowerCase()+".Version");
			dexels.Version v = version.newInstance();
			v.shutdown();
		  } catch (Throwable e) {
			  System.err.println("Extension shutdown failed.");
		}
		  
	}

}
