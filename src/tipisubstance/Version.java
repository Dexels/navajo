package tipisubstance;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import tipi.TipiSubstanceExtension;

public class Version implements BundleActivator{

	private final TipiSubstanceExtension te = new TipiSubstanceExtension();
	@Override
	public void start(BundleContext context) throws Exception {
		te.start(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		te.stop(context);
	}
}
