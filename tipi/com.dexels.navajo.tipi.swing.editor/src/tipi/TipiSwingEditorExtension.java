package tipi;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;

public class TipiSwingEditorExtension extends TipiAbstractXMLExtension implements TipiExtension {
	private static final long serialVersionUID = 854576126360753155L;

	@Override
	public void initialize(TipiContext tc) {
		// Do nothing
		
	}

	@Override
	public void start(BundleContext context) throws Exception {
		registerTipiExtension(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		deregisterTipiExtension(context);
	}

}
