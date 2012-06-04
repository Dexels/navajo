package navajo;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.dexels.navajo.adapter.core.NavajoCoreAdapterLibrary;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.GenericHandler;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.server.enterprise.monitoring.AgentFactory;
import com.dexels.navajo.server.enterprise.scheduler.ClockInterface;
import com.dexels.navajo.server.enterprise.scheduler.DummyClock;
import com.dexels.navajo.server.enterprise.scheduler.DummyTaskRunner;
import com.dexels.navajo.server.enterprise.scheduler.TaskRunnerInterface;
import com.dexels.navajo.server.enterprise.statistics.DummyStatisticsRunner;
import com.dexels.navajo.server.enterprise.statistics.StatisticsRunnerFactory;
import com.dexels.navajo.server.enterprise.statistics.StatisticsRunnerInterface;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;
import com.dexels.navajo.server.enterprise.xmpp.JabberWorkerFactory;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.server.resource.ResourceCheckerManager;
import com.dexels.navajo.util.AuditLog;

/**
 * 
 * VERSION HISTORY
 * 
 * 9.2.0 - Started adding support for javascript compiled scripts.
 * 
 * 9.0.2 - Extended Resource mechanism to Dispatcher (is also a resource that can have a certain health.
 * 
 * 9.0.1 - Added internalService header attribute as an indication for the Dispatcher to ignore the server too busy check.
 *       - Make sure that header is always added for response Navajo.
 *       
 * 9.0.0 - Added support for Non-Blocking I/O Listener (Tomcat Comet implementation).
 *       - Added pluggable support for non-native scripting engines (Ruby implementation included).
 *       - Some changes in cache implementation, now using script package name for cache path.
 *       
 * 8.2.7 - Refactored Dispatcher. Indoc is not passed as separate parameter anymore. Access object is always used instead.
 * 
 * 8.2.6 - Selected attribute in option tag now supports expressions (either boolean or integer outcome supported)
 *       - navajo_ping bypasses authentication, for the rest: removed check on isSpecialWebservice for bypassing authorization
 *       - Added Xtra defensive server.xml parsing
 *       - Fix for old-skool configuration in DispatcherFactory
 *       - Fix for full debug of scripts (debug=true in tsl/navascript tag)
 *       - Possible to use child tags in navascript 'method' tags to improve script readability.
 *       - Added support for merging Navajo's in Access class
 *       - Support for antimessage in scripts.
 * 
 * 8.2.5 - Implemented Debugable Interface.
 * 
 * 8.2.4 - Refactored Navajo Internal Structure
 *       - Possible to define expression in description attribute of validation check
 *       
 * 8.2.3 - Fixed problem with carriage return under Windows with expression between <value> tags.
 *       - Added printwriter console to Access object for debugging purposes.
 *       - Added support for firing AccessLogEvent to notify of 1 or more written full access logs.
 *       - Log ServerTooBusyEvent, HealthCheckEvent, QueuableFailureEvent and TribeMemberDownEvent to auditlog table. 
 *       - Added support for debugAll in CompiledScript and Access objects. debugAll triggers a full access log.
 *       - Added support for second resolution offsettime trigger. Clock now has default resolution of 500millis.
 *       - Explicit check on maintenance services instead of using 'navajo' prefix check.
 *       - Fixed problem with async webservices being broken due to change in Dispatcher (8.1.2). 
 *       - Explicitly closing fileinput-/outpustreams in final blocks in SharedFileStore, RequestResponseQueue and TaskRunnner.
 *       - Respect <defines/> tag in navascript.
 *       - ClearCacheEvent added, published by PersistenceManager in case of cache expiry.
 *       - Now possible to 'nest' navascript method tags to improve readability.
 *       - Possible to use ?input=response in Navajo Trigger definition to specify using the response of a Webservice
 *         as input for the task (webservice that will be called as a result of the trigger).
 *  
 * 8.1.2 - Fixes for script inheritance, fixed 'order' problem with scripts using inheritance.
 *       - Exposing postmanURL via TribeMember class.
 *       
 * 8.1.1 - Fixes for script inheritance. Now supports 'inject' semantics within other tags.
 *       - Added support for AgentId. Can be used to monitor requests from specific agents (client applications).
 *       - Needsrecompile in Dispatcher is only done after it has been established that we are dealing
 *         with a cached service to avoid double checking.
 *       
 * 8.1.0 Added support for keeping track of Script Dependencies, i.e. dependencies on other scripts (includes, navajomap),
 *       Dependencies on Adapters, dependencies on specific adapter fields, i.e. server field in MailMap, query field
 *       in SQLMap. Created specific AdapterField depencency classes, like SQLFieldDependency.
 *       
 *       Fixed bug problem in Navascript.
 * 
 * 8.0.0 Added support for Multi Lingual scripts. Added Java support for scripts as first example.
 * 
 * 7.7.4 Release Candidate
 *       - Now using Service lookup mechanism for adapters as well. Also added utility to generate navascript XSD file.
 *       - Fixed support for multiple include script that contain validations.
 * 
 * 7.7.3 Beta release with 
 *       - support for type checking functions and using functions via Service lookup mechanism.
 *       - Refactored Jabber support to Navajo Listeners.
 * 
 * 7.7.2 Added support for blocking inherited messages/properties.
 * 
 * 7.7.1 Implemented extend/replace inheritance semantics.
 * 
 * 7.7.0 Added support for Script Inheritance via <inject> semantics.
 * 
 * 7.0.4 Minor Changes. Do not persist access objects anymore. Clone access and do not clone indoc and outdoc if
 *       not needed. This should reduce the burden on the GC.
 *       
 * 7.0.3 Removed NanoTslCompiler. Now using TslCompiler also for plug in.
 * 
 * 7.0.2 Delete "Temp Space" files when starting Dispatcher to clean up remains of files after crash/kill/stop of Dispatcher.
 * 
 * 7.0.1 Statistics Runner is now more GC friendly: Persists access object and restore objecstream instead of
         putting Access object in Map.

 * 7.0.0 Lot's of refactoring.
 * 	     Changed interface of Mappable, now load(Access) instead of redundant load(Parameter, Navajo, Access, NavajoConfig)
 *       NavajoConfigInterface/TestNavajoConfig and DispatcherInterface/TestDispatcher/DispatcherFactory created for better testability.
 *       Moved lot's of functionality away from NavajoConfig initialization to prevent weird Dispatcher loops.
 *       Created testsrc structure and started working on unit tests, especially focusing on threading issues.
 *       Fixed problem with NavajoEventTrigger being evaluated each Tribal Member in case of workflow initialization.
 *       Added NavajoExceptionEvent 
 *       Publish AuditLog Event in case of Authentication/Authorization exception. 
 *       
 * 6.5.6 Some restructuring of Dispatcher/NavajoConfig. Better deadlock prevention for waiting for the chief in TribeManager.
 * 
 * 6.5.4-5 Added support for enabling/disabling and shutting down Navajo Server. Added support for forwarding
 *       Async Request to proper Tribal Member. 
 * 
 * 6.5.3 Fixed proper initialization of Navajo instance under heavy load. 
 * 		 Minor refactorings and additional code comments added.
 * 
 * 6.5.2 Improved versioning information
 * 
 * 6.5.1 Added support for <defines> section in scripts.
 * 		 Added support for groupInstance specific scripts (using _[groupinstance name] postfix in script name).
 * 
 * 6.0.0 Strict separation of standard (free) and enterprise edition functionality
 * 
 * 5.1.2 Change in TslCompiler and NanoTslCompiler: when calling getXYZ() method that returns a mappable Array (Mappable []),
 * assign value to variable and in subsequent calls use this variable instead of calling getXYZ() again. This will fix
 * a common problem in calling InitNavajoStatus which can return -1 in case of frequent User count changes. Furthermore, this
 * new implementation makes more sense and does not require a getXYZ() method that is robust for subsequent calls (e.g.
 * getResultSet() in SQLMap).
 * 
 * 5.1.3 The dispatcher will add 'serverTime' attribute to the header, indicating how much time the server needed to process
 * this transaction
 * 
 * 5.1.4 Removed access set size counter. Totaltime of access object now includes parsetime. 
 * 
 * 5.1.5 Minor fix for global messages. Directclient uses system props. Listeners use application.properties.
 *       direct client works now for both types. 
 *       
 * 5.1.6 Compiler fixes. The navajo plugin had trouble loading the javacompiler class, because of different
 *       class loaders. Also removed debug data.
 *       
 * 5.1.7 Added some classloader functionality. Now the script classes can get reloaded separately from the jarfiles. Should help performance of the plugin remote runner.
 * 
 * 5.2.0 Added Task Scheduler functionality.
 * 
 * 5.2.1 Change in CompiledScript. Now using outDoc directly from Access object ( getOutDoc() ), 
 *       instead of using local field.
 *      
 * 5.2.2 Task Scheduler extended: multiple weekdays, error logging.
 * 
 * 5.3.0 Added Integrity Worker for allowing multiple retries to already succeeded webservice calls.
 * 
 * 5.4.0 Added Locking Manager for setting maximum instance count of webservices.
 * 
 * 5.4.1 Dispatcher is now true singleton. Implemented kill() methods, should be robuust to servlet engine
 *       destroys now.
 *       
 * 5.4.2 Field tags can now address parent maps
 * 
 * 5.4.3 Bug fix in the compiler
 * 
 * 5.4.2 Some performance improvements in ASTTmlNode.
 * 
 * 5.4.3 Document class can be defined in server.xml
 * 
 * 5.4.4 Closing inputstreams when creating new Navajo object (for SAXP).
 * 
 * 5.4.5 Added AdminServlet again. Comparing binaries correctly.
 * 
 * 5.4.5 Support for long in TslCompiler/NanoTslCompiler.
 * 
 * 5.4.6 Some minor changes in Worker.
 * 
 * 5.4.7 Added support for logging AsyncMappable object access when finished or killed
 * 
 * 5.4.8 Added support in JAXP TslCompiler for CDATA section under <expression> tag
 * 
 * 5.4.9 Fixed match request functionality for Locks and improved lock error messages.
 * 
 * 5.5.0 Added clienttokens.
 * 
 * 5.5.1 Better memory usage for adapter jars
 * 
 * 5.5.2 Support for mappable adapter statistics
 * 
 * 5.5.3 Support for orderby construct in array messages.
 * 
 * 5.6.0 Restructured Navajo Classloader.
 * 
 * 5.6.1 Removed some possibly harmful synchronized waiting blocks
 * 
 * 5.6.2 Added support for <value> tag
 * 
 * 5.6.3 Added JMX support
 * 
 */

public class Version extends com.dexels.navajo.version.AbstractVersion {

	public static final int MAJOR = 9;
	public static final int MINOR = 2;
	public static final int PATCHLEVEL = 0;
	public static final String SPECIAL_VERSION = "Navajo 9.2 Release Candidate I";
	
	public static final String VENDOR = "Dexels";
	public static final String PRODUCTNAME = "Navajo Kernel";
	public static final String RELEASEDATE = "2010-08-26";
	private static ServiceRegistration navajoConfig;
	private static ServiceRegistration dispatcherRegistration;
	private ServiceRegistration dummyStats;
	
	private static BundleContext bundleContext;
	private ServiceRegistration clockRegistration;
	private ServiceRegistration taskRunnerRegistration;

	
	public static String getDescription() {
		return MAJOR + "." + MINOR + "." + PATCHLEVEL + " (" + SPECIAL_VERSION + ")";
	}
	
	
	public static boolean osgiActive() {
		try {
			return getDefaultBundleContext()!=null;
		} catch (Throwable t) {
			return false;
		}
	}
	
	@Override
	public void start(BundleContext bc) throws Exception {
			super.start(bc);
			FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
			fi.init();
			
			fi.clearFunctionNames();

			NavajoCoreAdapterLibrary library = new NavajoCoreAdapterLibrary();
			fi.injectExtension(library);
			try {
				for(String adapterName: fi.getAdapterNames(library)) {

					String adapterClass = fi.getAdapterClass(adapterName,library);
					Class<?> c = Class.forName(adapterClass);

					 Dictionary<String, Object> props = new Hashtable<String, Object>();
					 props.put("adapterName", adapterName);
					 props.put("adapterClass", c.getName());
					 System.err.println("registering: "+adapterName);
					 if(adapterClass!=null) {
						context.registerService(Class.class.getName(), c, props);
					}
				}
			} catch (Throwable e) {
				logger.error("Error starting navajo core bundle.",e);
			}
			StatisticsRunnerInterface ptps = new DummyStatisticsRunner();
			Dictionary<String, Object> wb = new Hashtable<String, Object>();
			wb.put("threadClass","com.dexels.navajo.server.enterprise.statistics.DummyStatisticsRunner");
			wb.put("name","dummy");
			dummyStats = bc.registerService(StatisticsRunnerInterface.class.getName(), ptps, wb);

			registerClock();
			registerTaskRunner();

	}



	public static void main(String [] args) {
		Version v = new Version();
		System.err.println(v.toString());
		Version [] d = (Version [] ) v.getIncludePackages();
		for (int i = 0; i < d.length; i++) {
			System.err.println("\t"+d[i].toString());
		}
	}



	@Override
	public void shutdown() {
		super.shutdown();
		 if(dummyStats!=null) {
			 dummyStats.unregister();
		 }
		 navajoConfig.unregister();
		 dispatcherRegistration.unregister();
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

		  NavajoConfig.terminate();
		  
		  DispatcherFactory.getInstance().shutdown();
		  AuditLog.log(AuditLog.AUDIT_MESSAGE_DISPATCHER, "Navajo Dispatcher terminated.");

		  JabberWorkerFactory.shutdown();
		  DispatcherFactory.shutdown();
		  
	
	}


	

	@Override
	public void stop(BundleContext arg0) throws Exception {
		super.stop(arg0);
		deregisterTaskRunner();
		deregisterClock();
	}


	public static void registerNavajoConfig(NavajoConfigInterface nc) {
		Dictionary<String, Object> properties = new Hashtable<String, Object>();
		properties.put("rootPath", nc.getRootPath());
		properties.put("instanceName", nc.getInstanceName());
		properties.put("instanceGroup", nc.getInstanceGroup());
		try {
			if ( getDefaultBundleContext() != null ) {
				navajoConfig = getDefaultBundleContext().registerService(NavajoConfigInterface.class.getName(), nc, properties);
			}else {
				logger.warn("Could NOT find default bundle context. No OSGi?");
			}
		} catch (Throwable t) {
			logger.warn("Could NOT find method getDefaultBundleContext(). No OSGi?");
		}
	}



	public static void registerDispatcher(DispatcherInterface instance) {
		Dictionary<String, Object> properties = new Hashtable<String, Object>();
		properties.put("edition", instance.getEdition());
		properties.put("product", instance.getProduct());
		properties.put("applicationId", instance.getApplicationId());
		properties.put("serverId", instance.getServerId());
		try {
			if ( getDefaultBundleContext() != null ) {
				dispatcherRegistration = getDefaultBundleContext().registerService(DispatcherInterface.class.getName(), instance, properties);
			} else {
				logger.warn("Could NOT find default bundle context. No OSGi?");
			}
		} catch (Throwable t) {
			logger.warn("Could NOT find method  getDefaultBundleContext(). No OSGi?");
		}
	}
	
	public static BundleContext getDefaultBundleContext() {
		return bundleContext;
	}
	
	private void registerClock() {
		Dictionary<String, Object> properties = new Hashtable<String, Object>();
		properties.put("service.ranking", Integer.MIN_VALUE);
		ClockInterface c = new DummyClock();
		clockRegistration = getDefaultBundleContext().registerService(ClockInterface.class, c, properties);
	}
	
	private void deregisterClock() {
		if(clockRegistration!=null) {
			clockRegistration.unregister();
		}
	}
	
	private void registerTaskRunner() {
		Dictionary<String, Object> properties = new Hashtable<String, Object>();
		properties.put("service.ranking", Integer.MIN_VALUE);
		TaskRunnerInterface c = new DummyTaskRunner();
		taskRunnerRegistration = getDefaultBundleContext().registerService(TaskRunnerInterface.class, c, properties);
	}
	
	private void deregisterTaskRunner() {
		if(taskRunnerRegistration!=null) {
			taskRunnerRegistration.unregister();
		}
	}
	
	

}
