package tipi;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;

public class TipiSvgBatikExtension extends TipiAbstractXMLExtension implements TipiExtension {
	
	private static final long serialVersionUID = 8831107698662135206L;

	public TipiSvgBatikExtension() {
		loadDescriptor();
	}

	@Override
	public void initialize(TipiContext tc) {
		
	}

	@Override
	public void start(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		registerTipiExtension(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		deregisterTipiExtension(context);
		
	}
}
