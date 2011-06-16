package tipi;

import java.io.IOException;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class TipiVaadinExtension extends TipiAbstractXMLExtension implements TipiExtension {

	private static final Logger logger = LoggerFactory.getLogger(TipiVaadinExtension.class); 
	
	private static TipiVaadinExtension instance = null;
	
	private BundleContext context;
	
	
	public BundleContext getBundleContext() {
		return context;
	}

	public TipiVaadinExtension() throws XMLParseException,
			IOException {
		loadDescriptor();
		instance = this;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		this.context = context;
		logger.info("Registering VAADIN ");
		registerTipiExtension(context);
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		
	}
	
	public void initialize(TipiContext tc) {
		// Do nothing
		
	}
	public static TipiVaadinExtension getInstance() {
		return instance;
	}
	
//
//	@Override
//	public void start(BundleContext context) throws Exception {
//		super.start(context);
//		extensionContext = context;
//	}

}
