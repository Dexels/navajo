package tipi;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;

public class TipiJxLayerExtension extends TipiAbstractXMLExtension implements TipiExtension {

	private static final long serialVersionUID = 4875391482415045916L;

	@Override
	public void initialize(TipiContext tc) {
	//	tc.getTipiValidationDecorator(new JxValidationDecorator());
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


