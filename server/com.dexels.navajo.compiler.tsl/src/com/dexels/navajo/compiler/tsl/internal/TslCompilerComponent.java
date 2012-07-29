package com.dexels.navajo.compiler.tsl.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.ScriptCompiler;
import com.dexels.navajo.compiler.tsl.custom.PackageListener;
import com.dexels.navajo.compiler.tsl.custom.PackageReportingClassLoader;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.mapping.compiler.TslCompiler;
import com.dexels.navajo.server.NavajoIOConfig;

public class TslCompilerComponent implements ScriptCompiler {

	private NavajoIOConfig navajoIOConfig = null;
	private ClassLoader classLoader = null;
	private final static Logger logger = LoggerFactory.getLogger(TslCompilerComponent.class);
	private TslCompiler compiler;
	String[] standardPackages = new String[]{"com.dexels.navajo.document","com.dexels.navajo.script.api","com.dexels.navajo.server","com.dexels.navajo.mapping","com.dexels.navajo.server.enterprise.tribe","com.dexels.navajo.mapping.compiler.meta"};
	/* (non-Javadoc)
	 * @see com.dexels.navajo.compiler.tsl.ScriptCompiler#compileTsl(java.lang.String)
	 */
	@Override
	public void compileTsl(String scriptPath, String compileDate) throws Exception {
		String packagePath = null;
		String script = null;
		if(scriptPath.indexOf('/')>=0) {
			packagePath = scriptPath.substring(0,scriptPath.lastIndexOf('/'));
			script = scriptPath.substring(scriptPath.lastIndexOf('/')+1);
		} else {
			packagePath ="";
			script=scriptPath;
		}
		final Set<String> packages = new HashSet<String>();
		for (String pkg : standardPackages) {
			packages.add(pkg);
		}
		PackageReportingClassLoader prc = new PackageReportingClassLoader(classLoader);
		prc.addPackageListener(new PackageListener() {
			
			@Override
			public void packageFound(String name) {
				packages.add(name);
			}
		});
		String scriptString = scriptPath.replaceAll("/", "_");
		String javaFile = compiler.compileToJava(script, navajoIOConfig.getScriptPath(), navajoIOConfig.getCompiledScriptPath(), packagePath, prc, navajoIOConfig);
//		logger.info("Javafile: "+javaFile);
//		System.err.println("Packages: "+packages);
		generateManifest(scriptString,"1.0.0",packagePath, script,packages,compileDate);
		generateDs(packagePath, script);
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
	
	
	private void generateManifest(String description, String version, String packagePath, String script, Set<String> packages, String compileDate) throws IOException {
		String symbolicName = "navajo.script."+description;
		PrintWriter w = new PrintWriter(navajoIOConfig.getOutputWriter(navajoIOConfig.getCompiledScriptPath(), packagePath, script, ".MF"));
		
		//		properties.getCompiledScriptPath(), pathPrefix, serviceName, ".java"
		w.print("Manifest-Version: 1.0\r\n");
		w.print("Bundle-SymbolicName: "+symbolicName+"\r\n");
		w.print("Bundle-Version: "+version+"."+compileDate+"\r\n");
		w.print("Bundle-Name: "+description+"\r\n");
		w.print("Bundle-RequiredExecutionEnvironment: JavaSE-1.6\r\n");
		w.print("Bundle-ManifestVersion: 2\r\n");
		w.print("Bundle-ClassPath: .\r\n");
		
		StringBuffer sb = new StringBuffer();
		Iterator<String> it = packages.iterator();
		boolean first = true;
		while (it.hasNext()) {
			if(!first) {
				sb.append(" ");
			}
			first = false;
			String pck =  it.next();
			sb.append(pck);
			if(it.hasNext()) {
				sb.append(",\r\n");
			}
			
		}
		w.print("Import-Package: "+sb.toString()+"\r\n");
		w.print("Service-Component: OSGI-INF/script.xml\r\n");
		w.print("\r\n");
		w.flush();
		w.close();
	}

//	<?xml version="1.0" encoding="UTF-8"?>
//	<scr:component xmlns:scr="http://www.osgi.org/xmlns/scr/v1.1.0" immediate="true" name="club.InitUpdateClub">
//	   <implementation class="com.dexels.navajo.server.CompiledScriptFactory"/>
//	   <service>
//	      <provide interface="com.dexels.navajo.server.CompiledScriptFactory"/>
//	   </service>
//	   <property name="serviceName" type="String" value="club.InitUpdateClub"/>
//	</scr:component>
//
	
	private void generateDs(String packagePath, String script) throws IOException {
		String fullName = packagePath+"/"+script;
		String symbolicName = fullName.replaceAll("/", ".");
		XMLElement xe = new CaseSensitiveXMLElement("scr:component");
		xe.setAttribute("xmlns:scr", "http://www.osgi.org/xmlns/scr/v1.1.0");
		xe.setAttribute("immediate", "true");
		xe.setAttribute("name",symbolicName);
		XMLElement implementation = new CaseSensitiveXMLElement("implementation");
		xe.addChild(implementation);
		implementation.setAttribute("class", symbolicName);
		XMLElement service = new CaseSensitiveXMLElement("service");
		xe.addChild(service);
		XMLElement provide = new CaseSensitiveXMLElement("provide");
		service.addChild(provide);
		provide.setAttribute("interface", "com.dexels.navajo.server.CompiledScriptFactory");
		XMLElement property = new CaseSensitiveXMLElement("property");
		xe.addChild(property);
		property.setAttribute("name", "serviceName");
		property.setAttribute("type", "String");
		property.setAttribute("value", symbolicName);
		PrintWriter w = new PrintWriter(navajoIOConfig.getOutputWriter(navajoIOConfig.getCompiledScriptPath(), packagePath, script, ".xml"));
		w.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xe.write(w);
		w.flush();
		w.close();
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
	
	public void activate(Map<String,Object> properties) {
		logger.debug("Activating TSL compiler");
		compiler = new TslCompiler(classLoader, navajoIOConfig);
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
