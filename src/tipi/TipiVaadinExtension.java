package tipi;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.tipixml.XMLParseException;

public class TipiVaadinExtension extends TipiAbstractXMLExtension implements TipiExtension {

	private static final Logger logger = LoggerFactory.getLogger(TipiVaadinExtension.class); 
	
	private static TipiVaadinExtension instance = null;
	
	private BundleContext context;
	
	
	public TipiVaadinExtension() throws XMLParseException,
			IOException {
		loadDescriptor();
		instance = this;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		this.context = context;
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

	public void installExtension(File library) {
		List<Bundle> loaded = new LinkedList<Bundle>();
		try {
			Bundle b = context.installBundle(library.toURI().toURL().toString());
			logger.info("Bundle: "+b.getSymbolicName()+" id: "+b.getBundleId()+" loaded succesfully.");
			loaded.add(b);
		} catch (MalformedURLException e) {
			logger.error("Bundle could not be loaded. Path invalid: "+library,e);
//			e.printStackTrace();
		} catch (BundleException e) {
//			e.printStackTrace();
			logger.error("Bundle with path: "+library+" could not be loaded.",e);
		}
		for (Bundle b : loaded) {
			try {
				b.start();
				logger.info("Bundle: "+b.getSymbolicName()+" id: "+b.getBundleId()+" started succesfully.");
			} catch (BundleException e) {
				e.printStackTrace();
				logger.error("Bundle: "+b.getSymbolicName()+" id: "+b.getBundleId()+" could not start.",e);
			}
		}
	}
}
