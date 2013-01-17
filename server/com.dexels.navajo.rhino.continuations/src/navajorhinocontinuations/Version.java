package navajorhinocontinuations;

import navajoextension.AbstractCoreExtension;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.adapter.rhino.RhinoAdapterLibrary;

public class Version extends AbstractCoreExtension {

//	private final Set<ServiceRegistration> adapterRegs = new HashSet<ServiceRegistration>();

	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
		RhinoAdapterLibrary library = new RhinoAdapterLibrary();
		registerAll(library);

	}

	@Override
	public void stop(BundleContext bc) throws Exception {
		super.stop(bc);
	}
	
	
}
