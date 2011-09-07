package tipi;

import java.io.IOException;

import org.osgi.framework.BundleContext;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class TipiEchoExtension extends TipiAbstractXMLExtension implements TipiExtension {

	private static final long serialVersionUID = -6095356723833425401L;

	public TipiEchoExtension() throws XMLParseException, IOException {
	}

	public void initialize(TipiContext tc) {

	}
	
	@Override
	public void start(BundleContext context) throws Exception {
		registerTipiExtension(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
	}


}
