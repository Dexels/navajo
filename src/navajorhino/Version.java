package navajorhino;

import org.osgi.framework.BundleContext;

public class Version extends dexels.Version {

	@Override
	public void start(BundleContext bc) throws Exception {
		System.err.println("Starting Navajo Rhino");
		super.start(bc);
	}

}
