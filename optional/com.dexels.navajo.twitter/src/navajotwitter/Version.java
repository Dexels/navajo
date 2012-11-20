package navajotwitter;

import navajoextension.AbstractCoreExtension;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.dexels.twitter.TwitterAdapterLibrary;

public class Version extends AbstractCoreExtension implements BundleActivator {

	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
		TwitterAdapterLibrary library = new TwitterAdapterLibrary();
		registerAll(library);

	}

	@Override
	public void stop(BundleContext bc) throws Exception {
		super.stop(bc);
		
	}
	
	@Override
	public void shutdown() {
		super.shutdown();
	}
	

	public static void main(String[] args) {
		
	}
}
