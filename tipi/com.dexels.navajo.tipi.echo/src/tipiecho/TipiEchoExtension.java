package tipiecho;

import java.io.IOException;

import org.osgi.framework.BundleContext;

import tipi.TipiAbstractXMLExtension;
import tipi.TipiExtension;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class TipiEchoExtension extends TipiAbstractXMLExtension implements TipiExtension {

	private static final long serialVersionUID = -6095356723833425401L;
	private static TipiEchoExtension instance;

	public TipiEchoExtension() throws XMLParseException, IOException {
		instance = this;
	}
	
	public static TipiEchoExtension getInstance() {
		return instance;
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
