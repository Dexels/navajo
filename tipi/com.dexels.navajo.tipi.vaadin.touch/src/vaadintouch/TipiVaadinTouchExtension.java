package vaadintouch;

import java.io.File;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.TipiAbstractXMLExtension;
import tipi.TipiExtension;

import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;
import com.dexels.navajo.tipi.TipiContext;

public class TipiVaadinTouchExtension extends TipiAbstractXMLExtension implements TipiExtension {

	private static final long serialVersionUID = 6145737357886902779L;

	private static final Logger logger = LoggerFactory.getLogger(TipiVaadinTouchExtension.class); 
	
	private static TipiVaadinTouchExtension instance = null;
	
	private transient BundleContext context;
	
	private File installationFolder = null;
		
	public void initialializeExtension(File installationFolder) {
		// This method will be called multiple times. It should only be done one for every extension directory.
		// OTOH, OSGi should be safe for this and simply refuse to re-add an extension
		this.installationFolder = installationFolder;
		File extensions = new File(installationFolder, "extensions");
		TipiVaadinTouchExtension.getInstance().installAllExtensions(extensions);
	}

	@Override
	public BundleContext getBundleContext() {
		return context;
	}

	public TipiVaadinTouchExtension() 
			 {
		instance = this;
		loadDescriptor();
	}

	@Override
	public void start(BundleContext bc) throws Exception {
		this.context = bc;
		logger.info("Starting vaadin tipi bundle");
		registerTipiExtension(context);
		FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
		fi.init();
		fi.clearFunctionNames();
	}

	
	@Override
	public void stop(BundleContext context) throws Exception {
		deregisterTipiExtension(context);
	}
	
	@Override
	public void initialize(TipiContext tc) {
		// Do nothing
		
	}
	public static TipiVaadinTouchExtension getInstance() {
		return instance;
	}

	public File getInstallationFolder() {
		return this.installationFolder;
	}
	
	
//
//	@Override
//	public void start(BundleContext context) throws Exception {
//		super.start(context);
//		extensionContext = context;
//	}

}
