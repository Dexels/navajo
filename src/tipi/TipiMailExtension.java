package tipi;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;

public class TipiMailExtension extends TipiAbstractXMLExtension implements TipiExtension {

	private static TipiMailExtension instance = null;
	
	public static TipiMailExtension getInstance() {
		return instance;
	}
	
	public TipiMailExtension() {
		//will be handled by registerTipiExtension
		//loadDescriptor();
		
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

