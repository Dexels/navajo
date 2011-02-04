package dexels;

import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.FrameworkEvent;
import org.osgi.framework.FrameworkListener;

public class NavajoBundleManager extends Version {

	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
		System.err.println("Starting Dexels version manager1");
		bc.addBundleListener(new BundleListener() {
			
			@Override
			public void bundleChanged(BundleEvent event) {
				System.err.println("Bundle event: "+event.getType()+" bundle: "+event.getBundle().getBundleId());
			}
		}
		);
		bc.addFrameworkListener(new FrameworkListener() {
			
			@Override
			public void frameworkEvent(FrameworkEvent event) {
				System.err.println("frameworkEvent: "+event.getType()+" bundle: "+event.getBundle().getBundleId());
			}
		});
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub
		super.stop(arg0);
	}

}
