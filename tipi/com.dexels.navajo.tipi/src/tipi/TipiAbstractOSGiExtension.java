package tipi;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

import navajoextension.AbstractCoreExtension;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipipackage.ITipiExtensionRegistry;
import tipipackage.TipiJarServiceExtensionProvider;

public abstract class TipiAbstractOSGiExtension extends AbstractCoreExtension implements TipiExtension,
		BundleActivator {

	private static final long serialVersionUID = 7871411864902044319L;
	private transient BundleContext context = null;
	private ServiceRegistration<TipiMainExtension> mainExtensionReg;
	private ServiceRegistration<TipiCoreExtension> coreExtensionReg;
	private ServiceRegistration<TipiExtension> extensionReg;
	private static final Logger logger = LoggerFactory.getLogger(TipiAbstractOSGiExtension.class); 

	private static final ITipiExtensionRegistry nonOSGiRegistry = new TipiJarServiceExtensionProvider();
	
	
	
	protected void registerTipiExtension(BundleContext context)
			throws Exception {
		this.context = context;
		logger.debug("Registering tipi extension: "+getClass().getName());
		loadDescriptor();
		ITipiExtensionRegistry reg = null;
		
//		registerWhiteBoardExtension();
		if(context==null) {
			reg = nonOSGiRegistry;
			reg.registerTipiExtension(this);
		} else {
			extensionReg = ServiceRegistrationUtils.registerWhiteBoardExtension(this,context);
			if(this instanceof TipiMainExtension) {
				mainExtensionReg = ServiceRegistrationUtils.registerMainExtension((TipiMainExtension) this, context);
			}
			if(this instanceof TipiCoreExtension) {
				coreExtensionReg = ServiceRegistrationUtils.registerCoreExtension((TipiCoreExtension) this, context);
			}
		}
	}


	
	
	/**
	 * @param context  
	 */
	protected void deregisterTipiExtension(BundleContext context)
			throws Exception {
		if(extensionReg!=null) {
			extensionReg.unregister();
			extensionReg = null;
		}
		if(coreExtensionReg!=null) {
			coreExtensionReg.unregister();
			coreExtensionReg = null;
		}
		if(mainExtensionReg!=null) {
			mainExtensionReg.unregister();
			mainExtensionReg = null;
		}

	}	
	
	public ITipiExtensionRegistry getTipiExtensionRegistry() {
		if(context==null) {
			return nonOSGiRegistry;
		}
		ServiceReference<? extends ITipiExtensionRegistry> refs = (ServiceReference<? extends ITipiExtensionRegistry>) context
				.getServiceReference(ITipiExtensionRegistry.class.getName());
		// TODO Sometimes this value is null, just af
		ITipiExtensionRegistry reg = context.getService(refs);
		return reg;
	}


	@Override
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
				logger.error("Error: ",e);
				logger.error("Bundle: "+b.getSymbolicName()+" id: "+b.getBundleId()+" could not start.",e);
			}
		}
	}
}
