package tipi;

import java.util.*;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.*;

public class TipiCobraBrowserExtension extends TipiAbstractXMLExtension implements TipiExtension {

	public TipiCobraBrowserExtension() {
		
	}
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
	}
}

