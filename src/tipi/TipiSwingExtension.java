package tipi;

import java.io.IOException;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class TipiSwingExtension extends TipiAbstractXMLExtension implements
		TipiExtension {
	private static TipiSwingExtension instance = null;

	private static final Logger logger = LoggerFactory.getLogger(TipiSwingExtension.class); 

	
	public TipiSwingExtension() throws XMLParseException, IOException {
		instance = this;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		logger.info("Registering Swing ");
		registerTipiExtension(context);
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		
	}
	
	public static TipiSwingExtension getInstance() {
		return instance;
	}
	
	public void initialize(TipiContext tc) {
		// Do nothing

	}
}
