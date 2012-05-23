package tipi;

import java.io.Serializable;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;

public class TipiCoreExtension extends TipiAbstractXMLExtension implements Serializable {

	private static final long serialVersionUID = -1916256809513988908L;

	public TipiCoreExtension() {
		super();
	}

	public void initialize(TipiContext tc) {
		// Do nothing
	}

	@Override
	public void start(BundleContext context) throws Exception {
		registerTipiExtension(context);
		// register as 'special service, so components can 'require' a core, as this is always the case.
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		deregisterTipiExtension(context);
	}

}
