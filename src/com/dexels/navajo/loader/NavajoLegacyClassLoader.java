package com.dexels.navajo.loader;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dexels.utils.JarResources;

public class NavajoLegacyClassLoader extends NavajoClassLoader {

	public NavajoLegacyClassLoader(ClassLoader parent) {
		super(parent);
	}

	 public NavajoLegacyClassLoader(String adapterPath, String compiledScriptPath, boolean beta, ClassLoader parent) {
		 super(adapterPath, compiledScriptPath, beta, parent);
	 }
	 
	 public NavajoLegacyClassLoader(String adapterPath, String compiledScriptPath, ClassLoader parent) {
		 super(adapterPath, compiledScriptPath, parent);
	 }
	 
	 @Override
	    public Enumeration<URL> getResources(String name) throws IOException {


	    	HashSet<URL> s = new HashSet<URL>();

	    	if ( jarResources == null || betaJarResources == null ) {
	    		initializeJarResources();
	    	}

	    	if (jarResources == null) {
	    		return getParent().getResources(name);
	    	}

	    	// If beta classloader first try betaJarResources.
	    	if ( beta ) {
	    		Iterator<JarResources> allResources = betaJarResources.iterator();
	    		while (allResources.hasNext()) {

	    			JarResources d = allResources.next();

	    			try {

	    				URL resource = d.getPathURL(name);
	    				if (resource != null && d.hasResource(name)) {
	    					s.add(resource);
	    				}
	    			}
	    			catch (Exception e) {
	    			}
	    		}

	    		return this.getClass().getClassLoader().getResources(name);
	    	} else {

	    		Iterator<JarResources> allResources = jarResources.iterator();
	    		while (allResources.hasNext()) {

	    			JarResources d = allResources.next();

	    			try {

	    				URL resource = d.getPathURL(name);
	    				if (resource != null && d.hasResource(name)) {
	    					s.add(resource);
	    				}
	    			}
	    			catch (Exception e) {
	    			}
	    		}
	    	}
	    	List<Enumeration<URL>> list = new LinkedList<Enumeration<URL>>();
	    	list.add(Collections.enumeration(s));
	    	list.add(getClass().getClassLoader().getResources(name));
	    	return new CompositeEnumeration<URL>(list);

	    }

}
