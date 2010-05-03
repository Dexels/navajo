package com.dexels.navajo.server.resource;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import com.dexels.navajo.mapping.compiler.meta.Dependency;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.GenericHandler;

/**
 * This class can be used to check the availability of 'Resources' that a web service depends on.
 * Furthermore it can give a 'waiting time' hint for retries in case of unavailablity of one of the resources.
 * 
 * @author arjen
 *
 */
public class ResourceChecker {

	HashMap<AdapterFieldDependency,ResourceManager> managedResources = new HashMap<AdapterFieldDependency,ResourceManager>();
	
	private Navajo inMessage = null;
	private boolean initialized = false;
	
	public ResourceChecker() {
		// For unit tests.
	}
	
	protected ResourceChecker(String webservice) {

		GenericHandler gh = new GenericHandler();
		StringBuffer compilerErrors = new StringBuffer();
		try {
			Access a = new Access();
			a.rpcName = webservice;
			CompiledScript myCompiledScript = gh.compileScript(a, compilerErrors);
			if ( myCompiledScript == null ) {
				System.err.println("ResourceChecker: Could not find compiledscript for: " + webservice);
			} else {
				init(myCompiledScript);
			}
		} catch (Throwable t) {
			t.printStackTrace(System.err);
		}
	}
	
	public final void init(CompiledScript myCompiledScript) {

		if ( myCompiledScript.getDependentObjects() == null ) {
			System.err.println("ResourceChecker: Could not find dependent objects for: " + myCompiledScript.getClass());
			return;
		}
		Iterator<Dependency> dependencies = myCompiledScript.getDependentObjects().iterator();
		while ( dependencies.hasNext() ) {
			Dependency dep = dependencies.next();
			if ( AdapterFieldDependency.class.isAssignableFrom(dep.getClass()) ) {
				AdapterFieldDependency afd = (AdapterFieldDependency) dep;
				try {
					Class c = Class.forName(afd.getJavaClass(), true, myCompiledScript.getClass().getClassLoader());
					Method m = c.getMethod("getResourceManager", new Class[]{String.class});
					if ( m != null ) {
						Object o = m.invoke(null, new Object[]{afd.getType()});
						if ( o != null ) {
							ResourceManager rm = (ResourceManager) o;
							managedResources.put(afd, rm);
						} 
					}
				} catch (Throwable e) {  }
			}
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
	
	public ServiceAvailability getServiceAvailability() {

		ArrayList<String> unavailableIds = new ArrayList<String>();

		boolean available = true;
		int maxWaitingTime = 0;

		for (Entry <AdapterFieldDependency,ResourceManager> e : managedResources.entrySet()) {
			AdapterFieldDependency afd = e.getKey();
			ResourceManager rm = e.getValue();
			String resourceId = evaluateResourceId(afd.getId());
			synchronized (rm) {
				System.err.println("Checking availability of resource: " + resourceId);
				if ( rm.isAvailable(resourceId) == false ) {
					available = false;
					int wt = rm.getWaitingTime(resourceId);
					if ( wt > maxWaitingTime ) {
						maxWaitingTime = wt;
					}
					unavailableIds.add(resourceId);
				}
			}
		}

		String [] ids = new String[unavailableIds.size()];
		ids = unavailableIds.toArray(ids);
		ServiceAvailability sa = new ServiceAvailability(available, maxWaitingTime, ids);

		return sa;

	}
	
}
