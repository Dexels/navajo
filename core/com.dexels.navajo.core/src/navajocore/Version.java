package navajocore;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.core.NavajoCoreAdapterLibrary;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.server.GenericHandler;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.enterprise.monitoring.AgentFactory;
import com.dexels.navajo.server.enterprise.statistics.StatisticsRunnerFactory;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.server.resource.ResourceCheckerManager;
import com.dexels.navajo.util.AuditLog;

import navajoextension.AbstractCoreExtension;


public class Version extends AbstractCoreExtension {

	private static BundleContext bundleContext;
	
	private static final Logger logger = LoggerFactory.getLogger(Version.class);

	@Override
	public void start(BundleContext bc) throws Exception {
			super.start(bc);
			setBundleContext(bc);
			logger.debug("Bundle context set in Navajo Version: {} hash: {}",osgiActive(), Version.class.hashCode());
			NavajoCoreAdapterLibrary library = new NavajoCoreAdapterLibrary();
			registerAll(library);

	}

	@Override
	public void shutdown() {
		super.shutdown();
	
	}


	

	@Override
	public void stop(BundleContext arg0) throws Exception {
		super.stop(arg0);
		setBundleContext(null);
	}


	public static void setBundleContext(BundleContext bundleContext) {
		Version.bundleContext = bundleContext;
	}

	public static BundleContext getDefaultBundleContext() {
		Bundle b = org.osgi.framework.FrameworkUtil.getBundle(Version.class);
		if(b!=null) {
			return b.getBundleContext();
		}
		return bundleContext;
	}
	
}
