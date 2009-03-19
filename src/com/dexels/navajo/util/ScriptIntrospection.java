package com.dexels.navajo.util;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.loader.NavajoClassLoader;
import com.dexels.navajo.loader.NavajoClassSupplier;
import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.mapping.compiler.meta.Dependency;
import com.dexels.navajo.mapping.compiler.meta.IncludeDependency;
import com.dexels.navajo.mapping.compiler.meta.InheritDependency;
import com.dexels.navajo.mapping.compiler.meta.JavaDependency;
import com.dexels.navajo.mapping.compiler.meta.NavajoDependency;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.GenericHandler;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.test.TestDispatcher;

public class ScriptIntrospection {

	public static final String DEFAULT_SERVER_XML = "config/server.xml";
	
	public ScriptIntrospection() {
		
	}
	
	private final static void showDependencies(ArrayList<Dependency> deps, Class check)  {
		for (int i = 0; i < deps.size(); i++) {
			Dependency dep = deps.get(i);
			if ( dep.getClass().getName().equals(check.getName()) ) {
				System.err.println(check.getSimpleName() + ": " + deps.get(i).getId());
			}
		}
	}
	
	public static void main(String [] args) throws Exception {
		
		String rootPath = args[0] + "/";
		String script = args[1];
		
		URL configurationUrl = null;
		try {
			File f = new File(rootPath);
			URL rootUrl = f.toURI().toURL();
			configurationUrl = new URL(rootUrl, DEFAULT_SERVER_XML);
		} catch (MalformedURLException e) {
			throw NavajoFactory.getInstance().createNavajoException(e);
		}
		
		NavajoConfig nc = new NavajoConfig(new com.dexels.navajo.server.FileInputStreamReader(), 
				                           (NavajoClassSupplier) null,
				                           configurationUrl.openStream(), rootPath); 
		TestDispatcher td = new TestDispatcher(nc);
		new DispatcherFactory(td);
		
		GenericHandler gh = new GenericHandler();
		StringBuffer compilerErrors = new StringBuffer();
		CompiledScript cso = gh.compileScript(new Access(), script, compilerErrors);
		
		ArrayList<Dependency> dependencies = cso.getDependentObjects();
		
		System.err.println("================ DEPENDENCIES ==================");
		showDependencies(dependencies, InheritDependency.class);
		showDependencies(dependencies, IncludeDependency.class);
		showDependencies(dependencies, JavaDependency.class);
		showDependencies(dependencies, NavajoDependency.class);
		System.err.println("================================================");
		
		System.err.println(compilerErrors.toString());
		
		System.exit(1);
		
	}
}
