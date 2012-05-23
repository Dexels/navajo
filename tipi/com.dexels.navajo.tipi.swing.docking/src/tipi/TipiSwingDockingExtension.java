package tipi;

import java.io.IOException;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class TipiSwingDockingExtension extends TipiAbstractXMLExtension implements TipiExtension {

	private static final long serialVersionUID = -5687079133065592997L;

	public TipiSwingDockingExtension() throws XMLParseException,
			IOException {
//		loadXML();
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
