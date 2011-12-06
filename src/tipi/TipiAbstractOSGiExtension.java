package tipi;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipipackage.ITipiExtensionRegistry;
import tipipackage.TipiJarServiceExtensionProvider;

public abstract class TipiAbstractOSGiExtension implements TipiExtension,
		BundleActivator {

	private static final long serialVersionUID = 7871411864902044319L;
	private transient BundleContext context = null;
	private static final Logger logger = LoggerFactory.getLogger(TipiAbstractOSGiExtension.class); 

	private static final ITipiExtensionRegistry nonOSGiRegistry = new TipiJarServiceExtensionProvider();
	
	
	
	@SuppressWarnings("unchecked")
	protected void registerTipiExtension(BundleContext context)
			throws Exception {
		this.context = context;
		logger.info("Registering tipi extension: "+getClass().getName());
		loadDescriptor();
		ITipiExtensionRegistry reg = null;
		
//		registerWhiteBoardExtension();
		if(context==null) {
			reg = nonOSGiRegistry;
			reg.registerTipiExtension(this);
		} else {
			ServiceRegistration.registerWhiteBoardExtension(this,context);
			// Actually, only for non-whiteboard, but it still should work.
			
			/* DEPRECATED!
			ServiceReference<? extends ITipiExtensionRegistry> refs = (ServiceReference<? extends ITipiExtensionRegistry>) context
					.getServiceReference(ITipiExtensionRegistry.class.getName());
			reg = (ITipiExtensionRegistry) context.getService(refs);
			reg.registerTipiExtension(this);
			*/
		}
	}


	
	
	protected void deregisterTipiExtension(BundleContext context)
			throws Exception {
///		this.context = context;
		// deregister
	}	
	
	@SuppressWarnings("unchecked")
	public ITipiExtensionRegistry getTipiExtensionRegistry() {
		if(context==null) {
			return nonOSGiRegistry;
		}
		ServiceReference<? extends ITipiExtensionRegistry> refs = (ServiceReference<? extends ITipiExtensionRegistry>) context
				.getServiceReference(ITipiExtensionRegistry.class.getName());
		ITipiExtensionRegistry reg = (ITipiExtensionRegistry) context
				.getService(refs);
		return reg;
	}

//	@Override
//	public void start(BundleContext context) throws Exception {
//		registerTipiExtension(context);
//	}
//
//	@Override
//	public void stop(BundleContext context) throws Exception {
//
//	}
	
	public BundleContext getBundleContext() {
		return context;
	}

	public void installAllExtensions(File extensionDir) {
		File[] list =extensionDir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".jar");
			}
		});
		if(list==null) {
			logger.info("No extension dir found ("+extensionDir.getAbsolutePath()+"). Resuming.");
			return;
		}
		// TODO Refactor to agnostic impl
		for (File file : list) {
			installExtension(file);
		}
	}

	
	public void installExtension( File library) {
		List<Bundle> loaded = new LinkedList<Bundle>();
		try {
			Bundle b = context.installBundle(library.toURI().toURL().toString());
			logger.info("Bundle: "+b.getSymbolicName()+" id: "+b.getBundleId()+" loaded succesfully.");
			loaded.add(b);
		} catch (MalformedURLException e) {
			logger.error("Bundle could not be loaded. Path invalid: "+library,e);
		} catch (BundleException e) {
			if(e.getType()==BundleException.DUPLICATE_BUNDLE_ERROR) {
				logger.info("Bundle already present. Ignoring.");
			} else {
				logger.error("Bundle with path: "+library+" could not be loaded.",e);
			}
					
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
