package navajobinarystore;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapters.binarystore.BinaryStoreAdapterLibrary;
import com.dexels.navajo.adapters.binarystore.functions.BinaryStoreFunctionLibrary;

import navajoextension.AbstractCoreExtension;

public class Version extends AbstractCoreExtension {


	private static BundleContext bundleContext;
	
	private static final Logger logger = LoggerFactory.getLogger(Version.class);

	
	public Version() {
	}

	private static void setContext(BundleContext bc) {
		bundleContext = bc;
	}
	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
		setContext(bc);
		try {
			BinaryStoreAdapterLibrary library = new BinaryStoreAdapterLibrary();
			BinaryStoreFunctionLibrary funcLib = new BinaryStoreFunctionLibrary();
			registerAll(library);
			registerAll(funcLib);
		} catch (Exception e) {
			logger.error("Trouble starting Binary Store bundle",e);
		}
	}
	
	@Override
	public void shutdown() {
	}


	public static BundleContext getDefaultBundleContext() {
		return bundleContext;
	}
	

}
