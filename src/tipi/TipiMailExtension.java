package tipi;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;

public class TipiMailExtension extends TipiAbstractXMLExtension implements TipiExtension {


	private static final long serialVersionUID = -8495583222148257940L;
	private static TipiMailExtension instance = null;
	
	public static TipiMailExtension getInstance() {
		return instance;
	}
	
	public TipiMailExtension() {
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
	}



}

