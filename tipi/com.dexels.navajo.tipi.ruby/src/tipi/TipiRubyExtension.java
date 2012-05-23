package tipi;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;

public class TipiRubyExtension extends TipiAbstractXMLExtension implements TipiExtension {

	private static final long serialVersionUID = -1716188511412526400L;

	public TipiRubyExtension() {
	}

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

