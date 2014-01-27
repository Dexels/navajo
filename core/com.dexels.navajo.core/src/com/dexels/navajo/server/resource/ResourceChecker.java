package com.dexels.navajo.server.resource;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.mapping.GenericDependentResource;
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.CompiledScriptInterface;
import com.dexels.navajo.script.api.Dependency;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.GenericHandler;

/**
 * This class can be used to check the availability of 'Resources' that a web service depends on.
 * Furthermore it can give a 'waiting time' hint for retries in case of unavailablity of one of the resources.
 *
 * @TODO:
 * Use Full QoS model to define status of Resources: responsetime, availability, ...
 * 
 * @author arjen
 *
 */
public class ResourceChecker {

	HashMap<AdapterFieldDependency,Method> managedResources = new HashMap<AdapterFieldDependency,Method>();
	HashSet<String> scriptDependencies = new HashSet<String>();
	
	private Navajo inMessage = null;
	private boolean initialized = false;
	private CompiledScriptInterface myCompiledScript = null;
	private String webservice;
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(ResourceChecker.class);
	
	public ResourceChecker(CompiledScriptInterface s) {
		// For unit tests.
		this.myCompiledScript = s;
		this.webservice = s.getClass().getSimpleName();
		init();
	}
	
	protected ResourceChecker(String webservice) {

		this.webservice = webservice;
		// Don't know if this is needed
		GenericHandler gh = new GenericHandler(DispatcherFactory.getInstance().getNavajoConfig());
		StringBuffer compilerErrors = new StringBuffer();
		try {
			Access a = new Access();
			a.rpcName = webservice;
			this.myCompiledScript = gh.compileScript(a, compilerErrors);
			if ( myCompiledScript == null ) {
				System.err.println("ResourceChecker: Could not find compiledscript for: " + webservice);
			} else {
				init();
			}
		} catch (Throwable t) {
			System.err.println("ResourceChecker: Could not find compiledscript for: " + webservice + " (" + t.getMessage() + ")");
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public final void init() {

		if ( myCompiledScript.getDependentObjects() == null ) {
			System.err.println("ResourceChecker: Could not find dependent objects for: " + myCompiledScript.getClass());
			return;
		}
		Iterator<Dependency> dependencies = myCompiledScript.getDependentObjects().iterator();
		while ( dependencies.hasNext() ) {
			Dependency dep = dependencies.next();
			if ( AdapterFieldDependency.class.isAssignableFrom(dep.getClass()) ) {
				AdapterFieldDependency afd = (AdapterFieldDependency) dep;
				if ( !afd.getType().equals(GenericDependentResource.SERVICE_DEPENDENCY) ) {
					try {
						Class c = Class.forName(afd.getJavaClass(), true, myCompiledScript.getClass().getClassLoader());
						Method m = c.getMethod("getResourceManager", new Class[]{String.class});
						if ( m != null ) {
							//System.err.println("Found method getResourceManager() for " + afd.getJavaClass());
							managedResources.put(afd, m);
						}
					} catch (Throwable e) {  }
				} else {
					// Script dependency
					scriptDependencies.add(evaluateResourceId(afd.getId()));
				}
			}
		}
		// Also add my CPU as a resource.
		try {
			managedResources.put(new AdapterFieldDependency(-1, "com.dexels.navajo.server.Dispatcher", "dispatcher", "dispatcher"), 
				com.dexels.navajo.server.Dispatcher.class.getMethod("getResourceManager", new Class[]{String.class}));
		} catch (Throwable e) {  
			logger.error("Error: ", e);
		}
		
		initialized = true;
	}
	
	public boolean isInitialized() {
		return initialized;
	}

	public void setInMessage(Navajo n) {
		this.inMessage = n;
	}
	
	private final String evaluateResourceId(String resourceIdExpression) {
		
		String result = "";
		
		if ( inMessage != null ) {
			try {
				result = Expression.evaluate(resourceIdExpression, inMessage).value + "";
			} catch (Exception e1) {
				result = resourceIdExpression;
			} 
		} else {
			try {
				result = Expression.evaluate(resourceIdExpression, NavajoFactory.getInstance().createNavajo()).value + "";
			} catch (Exception e1) {
				result = resourceIdExpression;
			} 
		}
		
		return result;
	}
	
	private ServiceAvailability checkScriptDependencies(HashSet<String> checkedServices) {
		Iterator<String> deps = scriptDependencies.iterator();
		int finalHealth = -1;
		ServiceAvailability finalAvailability = null;
		while ( deps.hasNext() ) {
			String service = deps.next();
			if ( !checkedServices.contains(service) ) {
				ServiceAvailability sa = ResourceCheckerManager.getInstance().getResourceChecker(service, inMessage).getServiceAvailability(checkedServices);
				if ( sa.getHealth() > finalHealth || !sa.isAvailable() ) {
					finalAvailability = sa;
				}
			}
		}
		return finalAvailability;
	}
	
	public ServiceAvailability getServiceAvailability() {
		return getServiceAvailability(new HashSet<String>());
	}
	
	/**
	 * 
	 * @param checkedServices, contains set of services that have already been checked to prevent infinite loop due
	 * to (wrong) circular dependencies.
	 * 
	 * @return
	 */
	private ServiceAvailability getServiceAvailability(HashSet<String> checkedServices) {

		ArrayList<String> unavailableIds = new ArrayList<String>();

		boolean available = true;
		boolean unknown   = false;
		int maxWaitingTime = 0;
		int finalHealth = 0;
		
		for (Entry <AdapterFieldDependency,Method> e : managedResources.entrySet()) {
			AdapterFieldDependency afd = e.getKey();
			Method m = e.getValue();
			try {
				Object o = m.invoke(null, new Object[]{afd.getType()});
				if ( o != null ) {
					ResourceManager rm = (ResourceManager) o;
					String resourceId = evaluateResourceId(afd.getId());
					synchronized (rm) {
						
						int health = rm.getHealth(resourceId);
						unknown = ( unknown || health == ServiceAvailability.STATUS_UNKNOWN );
						if ( health > finalHealth ) {
							finalHealth = health;
						}
						if ( rm.isAvailable(resourceId) == false || health == ServiceAvailability.STATUS_DEAD ) {
							available = false;
							int wt = rm.getWaitingTime(resourceId);
							if ( wt > maxWaitingTime ) {
								maxWaitingTime = wt;
							}
							unavailableIds.add(resourceId);
						}
					}
				}
			} catch (Exception e1) { 
				System.err.println("Could not check avail of: " + afd.getType() + ", msg: " + e1.getMessage()); 
			}
		}

		if ( unknown ) {
			finalHealth = ServiceAvailability.STATUS_UNKNOWN;
		}
		
		String [] ids = new String[unavailableIds.size()];
		ids = unavailableIds.toArray(ids);
		ServiceAvailability sa = new ServiceAvailability(webservice, available, finalHealth, maxWaitingTime, ids);
		//System.err.println("First order health of " + webservice + " is: " + finalHealth);
		checkedServices.add(webservice);
		
		// Check dependecies
		ServiceAvailability dependencyHealth = checkScriptDependencies(checkedServices);
//		if ( dependencyHealth != null ) {
//			System.err.println("Found child dep with health: " + dependencyHealth.getHealth());
//		}
		if ( dependencyHealth != null && ( dependencyHealth.getHealth() > sa.getHealth() || !dependencyHealth.isAvailable() ) ) {
			return dependencyHealth;
		} else {
			return sa;
		}

	}
	
}
