package com.dexels.navajo.server.resource;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import com.dexels.navajo.mapping.compiler.meta.Dependency;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.GenericHandler;

public class ResourceChecker {

	HashMap<AdapterFieldDependency,ResourceManager> managedResources = new HashMap<AdapterFieldDependency,ResourceManager>();
	
	public ResourceChecker() {
	}
	
	public ResourceChecker(String webservice) {

		GenericHandler gh = new GenericHandler();
		StringBuffer compilerErrors = new StringBuffer();
		try {
			CompiledScript myCompiledScript = gh.compileScript(new Access(), webservice, compilerErrors);
			init(myCompiledScript);
		} catch (Throwable t) {
			t.printStackTrace(System.err);
		}
	}
	
	public void init(CompiledScript myCompiledScript) {

		Iterator<Dependency> dependencies = myCompiledScript.getDependentObjects().iterator();
		while ( dependencies.hasNext() ) {
			Dependency dep = dependencies.next();
			if ( AdapterFieldDependency.class.isAssignableFrom(dep.getClass()) ) {
				AdapterFieldDependency afd = (AdapterFieldDependency) dep;
				try {
					Class c = Class.forName(afd.getJavaClass());
					Method m = c.getMethod("getResourceManager", new Class[]{String.class});
					if ( m != null ) {
						Object o = m.invoke(null, new Object[]{afd.getType()});
						if ( o != null ) {
							ResourceManager rm = (ResourceManager) o;
							managedResources.put(afd, rm);
						}
					}
				} catch (Exception e) { e.printStackTrace(System.err); }
			}
		}
	}
	
	public boolean isAvailable() {
		for (Entry <AdapterFieldDependency,ResourceManager> e : managedResources.entrySet()) {
			AdapterFieldDependency afd = e.getKey();
			ResourceManager rm = e.getValue();
			String resourceId = afd.getId();
			if ( rm.isAvailable(resourceId) == false ) {
				return false;
			}
		}
		return true;
	}
	
	public int getWaitingTime() {
		int max = 0;
		for (Entry <AdapterFieldDependency,ResourceManager> e : managedResources.entrySet()) {
			AdapterFieldDependency afd = e.getKey();
			ResourceManager rm = e.getValue();
			String resourceId = afd.getId();
			int wt = rm.getWaitingTime(resourceId);
			if ( wt > max ) {
				max = wt;
			}
		}
		return max;
	}
	
}
