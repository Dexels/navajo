package com.dexels.navajo.compiler.tsl.internal;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.ScriptCompiler;
import com.dexels.navajo.compiler.tsl.custom.PackageListener;
import com.dexels.navajo.compiler.tsl.custom.PackageReportingClassLoader;
import com.dexels.navajo.mapping.compiler.TslCompiler;
import com.dexels.navajo.server.NavajoIOConfig;

public class TslCompilerComponent implements ScriptCompiler {

	private NavajoIOConfig navajoIOConfig = null;
	private ClassLoader classLoader = null;
	private final static Logger logger = LoggerFactory
			.getLogger(TslCompilerComponent.class);
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.compiler.tsl.ScriptCompiler#compileTsl(java.lang.String)
	 */
	@Override
	public void compileTsl(String script) throws Exception {
		String packagePath = null;
		if(script.indexOf('/')>=0) {
			packagePath = script.substring(0,script.lastIndexOf('/'));
		} else {
			packagePath ="";
		}
		final Set<String> packages = new HashSet<String>();
		PackageReportingClassLoader prc = new PackageReportingClassLoader(classLoader);
		prc.addPackageListener(new PackageListener() {
			
			@Override
			public void packageFound(String name) {
				System.err.println("PACKAGE FOUND: "+name);
				packages.add(name);
			}
		});
		
		File output = new File(navajoIOConfig.getCompiledScriptPath());
		File temp = new File(output,"temp");
		System.err.println("::::: "+temp.getAbsolutePath());
		if(temp.exists()) {
			temp.delete();
		}
		temp.mkdirs();
		
		String javaFile = TslCompiler.compileToJava(script, navajoIOConfig.getScriptPath(), temp.getAbsolutePath(), packagePath, prc, navajoIOConfig);
		logger.info("Javafile: "+javaFile);
		System.err.println("Packages: "+packages);
		generateManifest("Unknown","1.1.1", script,packages);
	}
	
	
//	Manifest-Version: 1.0
//	Bundle-ManifestVersion: 2
//	Bundle-Name: Tsl compiler for Navajo
//	Bundle-SymbolicName: com.dexels.navajo.compiler.tsl
//	Bundle-Version: 1.0.0.qualifier
//	Bundle-Vendor: Dexels
//	Bundle-RequiredExecutionEnvironment: JavaSE-1.6
//	Import-Package: javax.tools,
//	 org.apache.commons.io,
//	 org.apache.tools.ant,
//	 org.osgi.framework;version="1.6.0",
//	 org.osgi.framework.wiring;version="1.0.0",
//	 org.osgi.service.cm;version="1.4.0",
//	 org.osgi.service.component;version="1.2.0",
//	 org.slf4j;version="1.6.1"
//	Bundle-Activator: navajotsl.Activator
//	Require-Bundle: com.dexels.navajo.core;bundle-version="2.9.10"
//	Service-Component: OSGI-INF/navajoTslCompiler.xml, OSGI-INF/javaCompiler.xml, OSGI-INF/javaCompilerComponent.xml
//	Export-Package: com.dexels.osgicompiler
	
	
	private void generateManifest(String description, String version, String script, Set<String> packages) {
		String symbolicName = "navajo.script."+script.replaceAll("/", ".");
		System.err.println("Bundle-SymbolicName: "+symbolicName);
		System.err.println("Bundle-Version: "+version);
		System.err.println("Bundle-Name: "+description);
		System.err.println("Bundle-RequiredExecutionEnvironment: JavaSE-1.6");
		
		StringBuffer sb = new StringBuffer();
		Iterator<String> it = packages.iterator();
		while (it.hasNext()) {
			String pck =  it.next();
			sb.append(pck);
			if(it.hasNext()) {
				sb.append(",");
			}
			
		}
		System.err.println("Import-Package: "+sb.toString());
		System.err.println("Service-Component: OSGI-INF/script.xml");
	}

	public void setClassLoader(ClassLoader cls) {
		this.classLoader = cls;
	}

	public void clearClassLoader(ClassLoader cls) {
		this.classLoader = null;
	}

	public void setIOConfig(NavajoIOConfig config) {
		this.navajoIOConfig = config;
	}
	
	public void clearIOConfig(NavajoIOConfig config) {
		this.navajoIOConfig = null;
	}
	
	public void activate() {
		logger.debug("Activating TSL compiler");
	}
	
	public void deactivate() {
		logger.debug("Deactivating TSL compiler");
	}
	
	public static void main(String[] args) {
		String script = "club/InitUpdateClub";
		String pack = script.substring(0,script.lastIndexOf('/'));
		System.err.println("pack: "+pack);
	}
}
