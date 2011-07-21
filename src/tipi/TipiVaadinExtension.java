package tipi;

import java.io.File;
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
	
	private File installationFolder = null;
		
	public void initialializeExtension(File installationFolder) {
		// This method will be called multiple times. It should only be done one for every extension directory.
		// OTOH, OSGi should be safe for this and simply refuse to re-add an extension
		this.installationFolder = installationFolder;
		File extensions = new File(installationFolder, "extensions");
		TipiVaadinExtension.getInstance().installAllExtensions(extensions);
	}

	public BundleContext getBundleContext() {
		return context;
	}

	public TipiVaadinExtension() 
			 {
		instance = this;
		loadDescriptor();
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
