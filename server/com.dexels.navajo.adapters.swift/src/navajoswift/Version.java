package navajoswift;

import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapters.swift.SwiftAdapterLibrary;
import com.dexels.navajo.adapters.swift.functions.SwiftAdapterFunctionLibrary;

import navajoextension.AbstractCoreExtension;

public class Version extends AbstractCoreExtension {


	private static BundleContext bundleContext;
	
	private final static Logger logger = LoggerFactory.getLogger(Version.class);

	
	public Version() {
	}


	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
		bundleContext = bc;
		try {
			SwiftAdapterLibrary library = new SwiftAdapterLibrary();
			SwiftAdapterFunctionLibrary funcLib = new SwiftAdapterFunctionLibrary();
			registerAll(library);
			registerAll(funcLib);
		} catch (Throwable e) {
			logger.error("Trouble starting Swift bundle",e);
		}
	}
	
	@Override
	public void shutdown() {
	}


	public static BundleContext getDefaultBundleContext() {
		return bundleContext;
	}
	

}
