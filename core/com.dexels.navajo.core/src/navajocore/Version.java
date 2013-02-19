package navajocore;

import navajoextension.AbstractCoreExtension;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.dexels.navajo.adapter.core.NavajoCoreAdapterLibrary;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.functions.core.CoreFunctionDefinitions;
import com.dexels.navajo.server.GenericHandler;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.enterprise.monitoring.AgentFactory;
import com.dexels.navajo.server.enterprise.statistics.StatisticsRunnerFactory;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.server.resource.ResourceCheckerManager;
import com.dexels.navajo.util.AuditLog;


public class Version extends AbstractCoreExtension {

//	private static ServiceRegistration navajoConfig;
//	private static ServiceRegistration dispatcherRegistration;
//	private ServiceRegistration dummyStats;
	
	private static BundleContext bundleContext;

	@Override
	public void start(BundleContext bc) throws Exception {
			super.start(bc);
			bundleContext = bc;
			logger.debug("Bundle context set in Navajo Version: "+osgiActive()+" hash: "+Version.class.hashCode());
			NavajoCoreAdapterLibrary library = new NavajoCoreAdapterLibrary();
			registerAll(library);
			CoreFunctionDefinitions functions = new CoreFunctionDefinitions();
			registerAll(functions);

	}

	@Override
	public void shutdown() {
		super.shutdown();
		if(!osgiActive()) {
			 // Stop JMX.
			  JMXHelper.destroy();
				ResourceCheckerManager.clearInstance();
			  
			  // 1. Kill all supporting threads.
			  GenericThread.killAllThreads();
			  // remove the static links
			  StatisticsRunnerFactory.shutdown();
			  
			  // 2. Clear all classloaders.
			  GenericHandler.doClearCache();
			  
		      // 3. Shutdown monitoring agent.
			  AgentFactory.shutdown();
			  
			  // 4. Kill tribe manager.
			  TribeManagerFactory.shutdown();
			  
			  NavajoEventRegistry.getInstance().shutdown();
			  NavajoEventRegistry.clearInstance();
			  // 5. Shut down DbConnectionBroker
			  // Very ugly should be handled in a better way:
			  // - By registering 'resources' to be killable
			  // - OSGi package lifecycles
			  // right now I just dug up 

//			  NavajoConfig.terminate();
			
			  AuditLog.log(AuditLog.AUDIT_MESSAGE_DISPATCHER, "Navajo Dispatcher terminated.");
			
		}
		  
	
	}


	

	@Override
	public void stop(BundleContext arg0) throws Exception {
		super.stop(arg0);
//		deregisterTaskRunner();
//		deregisterClock();
		bundleContext = null;
	}
	
	public static BundleContext getDefaultBundleContext() {
		Bundle b = org.osgi.framework.FrameworkUtil.getBundle(Version.class);
		if(b!=null) {
			return b.getBundleContext();
		}
		return bundleContext;
	}
	
//	private void registerClock() {
//		Dictionary<String, Object> properties = new Hashtable<String, Object>();
//		properties.put("service.ranking", Integer.MIN_VALUE);
//		ClockInterface c = new DummyClock();
//		clockRegistration = getDefaultBundleContext().registerService(ClockInterface.class, c, properties);
//	}
//	
//	private void deregisterClock() {
//		if(clockRegistration!=null) {
//			clockRegistration.unregister();
//		}
//	}
//	
//	private void registerTaskRunner() {
//		Dictionary<String, Object> properties = new Hashtable<String, Object>();
//		properties.put("service.ranking", Integer.MIN_VALUE);
//		TaskRunnerInterface c = new DummyTaskRunner();
//		taskRunnerRegistration = getDefaultBundleContext().registerService(TaskRunnerInterface.class, c, properties);
//	}
//	
//	private void deregisterTaskRunner() {
//		if(taskRunnerRegistration!=null) {
//			taskRunnerRegistration.unregister();
//		}
//	}
	
	

}
