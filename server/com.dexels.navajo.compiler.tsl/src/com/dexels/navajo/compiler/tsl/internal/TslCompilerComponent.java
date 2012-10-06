package com.dexels.navajo.compiler.tsl.internal;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.ScriptCompiler;
import com.dexels.navajo.compiler.tsl.custom.PackageListener;
import com.dexels.navajo.compiler.tsl.custom.PackageReportingClassLoader;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.mapping.compiler.TslCompiler;
import com.dexels.navajo.mapping.compiler.meta.Dependency;
import com.dexels.navajo.server.NavajoIOConfig;

public class TslCompilerComponent implements ScriptCompiler {

	private NavajoIOConfig navajoIOConfig = null;
	private ClassLoader classLoader = null;
	private final static Logger logger = LoggerFactory.getLogger(TslCompilerComponent.class);
	private TslCompiler compiler;
	String[] standardPackages = new String[]{"com.dexels.navajo.document","com.dexels.navajo.script.api","com.dexels.navajo.server","com.dexels.navajo.mapping","com.dexels.navajo.server.enterprise.tribe","com.dexels.navajo.mapping.compiler.meta","com.dexels.navajo.parser","com.dexels.navajo.loader"};
	/* (non-Javadoc)
	 * @see com.dexels.navajo.compiler.tsl.ScriptCompiler#compileTsl(java.lang.String)
	 */
	@Override
	public void compileTsl(String scriptPath, String compileDate, List<Dependency> dependencies) throws Exception {
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
		String scriptPackage = packagePath;
//       if("".equals(packagePath)) {
//    	   scriptPackage = "defaultPackage";
//       }
		String scriptString = scriptPath.replaceAll("/", "_");
		compiler.compileToJava(script, navajoIOConfig.getScriptPath(), navajoIOConfig.getCompiledScriptPath(), packagePath, scriptPackage, prc, navajoIOConfig,dependencies);
		//		logger.info("Javafile: "+javaFile);
//		System.err.println("Packages: "+packages);
		generateFactoryClass(script, packagePath);

		generateManifest(scriptString,"1.0.0",packagePath, script,packages,compileDate);
		generateDs(packagePath, script,dependencies);
	}
	
	private void generateFactoryClass(String script, String packagePath) throws IOException {
		
		String javaPackagePath = packagePath.replaceAll("/", ".");
//		public CompiledScript getCompiledScript() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
//			Class<? extends CompiledScript> c;
//			logger.info("About to load class: "+serviceName+" I am: "+getClass().getName());
//			c = (Class<? extends CompiledScript>) Class.forName(getScriptName());
//			CompiledScript instance = c.newInstance();
//			return instance;
//		}
//		
		
		
		PrintWriter w = new PrintWriter(navajoIOConfig.getOutputWriter(navajoIOConfig.getCompiledScriptPath(), packagePath, script+"Factory", ".java"));
		if ("".equals(packagePath)) {
			w.println("package defaultPackage;");
		} else {
			w.println("package "+javaPackagePath+";");
		}
		
		w.println("import com.dexels.navajo.server.*;");
		w.println("import com.dexels.navajo.mapping.*;");
		w.println();
		w.println("public class "+script+"Factory extends CompiledScriptFactory {");
		w.println("	protected String getScriptName() {");
		if ("".equals(packagePath)) {
			w.println("		return \"defaultPackage."+ script+"\";");
		} else {
			w.println("		return \""+javaPackagePath+"."+ script+"\";");
		}
		w.println("	}");
		w.println("public CompiledScript getCompiledScript() throws InstantiationException, IllegalAccessException, ClassNotFoundException {");
		w.println("	Class<? extends CompiledScript> c;");
		w.println("	c = (Class<? extends CompiledScript>) Class.forName(getScriptName());");
		w.println("	CompiledScript instance = c.newInstance();");
		w.println("	super.initialize(instance);");
		w.println("	return instance;");
		w.println("}");
		w.println("");
		w.println("");
		
		w.println("}");
		w.flush();
		w.close();
		
	}
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
	
	private void generateDs(String packagePath, String script,List<Dependency> dependencies) throws IOException {
		
		String fullName;
		if (packagePath.equals("")) {
			fullName = script;
		} else {
			fullName = packagePath+"/"+script;

		}
		String javaPackagePath;
		if("".equals(packagePath)) {
			javaPackagePath = "defaultPackage";
		} else {
			javaPackagePath = packagePath.replaceAll("/", ".");
		}
		String symbolicName = fullName.replaceAll("/", ".");
		XMLElement xe = new CaseSensitiveXMLElement("scr:component");
		xe.setAttribute("xmlns:scr", "http://www.osgi.org/xmlns/scr/v1.1.0");
		xe.setAttribute("immediate", "false");
		xe.setAttribute("name",symbolicName);
		xe.setAttribute("activate", "activate");
		xe.setAttribute("deactivate", "deactivate");
		XMLElement implementation = new CaseSensitiveXMLElement("implementation");
		xe.addChild(implementation);
		implementation.setAttribute("class",javaPackagePath+"."+script+"Factory");
		XMLElement service = new CaseSensitiveXMLElement("service");
		xe.addChild(service);
		XMLElement provide = new CaseSensitiveXMLElement("provide");
		service.addChild(provide);
		for (Dependency dependency : dependencies) {
			XMLElement dep = new CaseSensitiveXMLElement("reference");
			dep.setAttribute("bind", "setDependency");
			dep.setAttribute("deptype", dependency.getType());
			dep.setAttribute("depId", dependency.getId());
			dep.setAttribute("depStamp", dependency.getCurrentTimeStamp());
			xe.addChild(dep);
		}
		provide.setAttribute("interface", "com.dexels.navajo.server.CompiledScriptFactory");
		XMLElement property = new CaseSensitiveXMLElement("property");
		xe.addChild(property);
		property.setAttribute("name", "navajo.scriptName");
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

	/**
	 * @param cls the classloader to clear
	 */
	public void clearClassLoader(ClassLoader cls) {
		this.classLoader = null;
	}

	public void setIOConfig(NavajoIOConfig config) {
		this.navajoIOConfig = config;
	}
	
	/**
	 * @param config the navajoconfig to clear
	 */
	public void clearIOConfig(NavajoIOConfig config) {
		this.navajoIOConfig = null;
	}
	
	public void activate() {
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
