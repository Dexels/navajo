package dexels;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class NavajoBundleManagerFactory {
	
	private  static BundleContext myContext;
	public static void initialize(BundleContext bc) {
		myContext = bc;
	}
	
	public static INavajoBundleManager getInstance() {
		ServiceReference refs = myContext.getServiceReference("dexels.INavajoBundleManager");
		return (INavajoBundleManager) myContext.getService(refs);
	}
}
