package tipi;

import java.io.IOException;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class TipiRcpExtension extends TipiAbstractXMLExtension implements TipiExtension {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5107021813164792108L;

	public TipiRcpExtension() throws XMLParseException,
			IOException {
// NO		loadDescriptor();
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
