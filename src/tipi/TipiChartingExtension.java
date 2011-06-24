package tipi;

import java.io.IOException;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class TipiChartingExtension extends TipiAbstractXMLExtension implements TipiExtension {

	public TipiChartingExtension() throws XMLParseException,
			IOException {
		loadDescriptor();
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
