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
		// add extension shutdown code
	}
	
}
