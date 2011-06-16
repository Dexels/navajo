package tipi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import tipipackage.ITipiExtensionRegistry;

public abstract class TipiAbstractOSGiExtension implements TipiExtension,BundleActivator {

	private BundleContext context = null;


	protected void registerTipiExtension(BundleContext context) throws Exception {
		this.context = context;
		loadDescriptor();
		ServiceReference refs = context.getServiceReference(ITipiExtensionRegistry.class.getName());
		ITipiExtensionRegistry reg = (ITipiExtensionRegistry) context.getService(refs);
		reg.registerTipiExtension(this);
		
		
	}

	public ITipiExtensionRegistry getTipiExtensionRegistry() {
		ServiceReference refs = context.getServiceReference(ITipiExtensionRegistry.class.getName());
		ITipiExtensionRegistry reg = (ITipiExtensionRegistry) context.getService(refs);
		return reg;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		registerTipiExtension(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		
	}
}
