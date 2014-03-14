package tipicharting;

import org.osgi.framework.BundleContext;

import tipi.TipiAbstractXMLExtension;
import tipi.TipiExtension;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class TipiChartingExtension extends TipiAbstractXMLExtension implements TipiExtension {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5107021813164792108L;

	public TipiChartingExtension() throws XMLParseException {
// NO		loadDescriptor();
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
		deregisterTipiExtension(context);
	}

	

}
