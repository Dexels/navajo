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
import com.dexels.navajo.document.ExpressionEvaluator;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.mapping.compiler.TslCompiler;
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import com.dexels.navajo.script.api.CompiledScriptFactory;
import com.dexels.navajo.script.api.Dependency;
import com.dexels.navajo.server.NavajoIOConfig;

public class TslCompilerComponent implements ScriptCompiler {

	private NavajoIOConfig navajoIOConfig = null;
	private ClassLoader classLoader = null;
	private final static Logger logger = LoggerFactory.getLogger(TslCompilerComponent.class);
	private TslCompiler compiler;
	String[] standardPackages = new String[]{"com.dexels.navajo.document","com.dexels.navajo.document.types","com.dexels.navajo.script.api","com.dexels.navajo.server","com.dexels.navajo.mapping","com.dexels.navajo.server.enterprise.tribe","com.dexels.navajo.mapping.compiler.meta","com.dexels.navajo.parser","com.dexels.navajo.loader","org.osgi.framework"};
	private ExpressionEvaluator expressionEvaluator;
	/* (non-Javadoc)
	 * @see com.dexels.navajo.compiler.tsl.ScriptCompiler#compileTsl(java.lang.String)
	 */
	@Override
	public void compileTsl(String scriptPath, String compileDate, List<Dependency> dependencies, String tenant, boolean hasTenantSpecificFile) throws Exception {
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
		String scriptString = null;
		if("".equals(scriptPackage)) {
			scriptString = scriptPath;
		} else {
			scriptString = packagePath + "/"+script.replaceAll("_", "|");
		}
//		scriptString = scriptPath.replaceAll("_", "|");
		String scriptSource = script;

//		Navajo n = NavajoFactory.getInstance().createNavajo(navajoIOConfig.getScript(scriptPath));
//		List<Operation> all =  n.getAllOperations();
//		if(!all.isEmpty()) {
//			parseEntity(n);
//		}
//		
		//		if(hasTenantSpecificFile) {
//			scriptSource = script+"_"+tenant;
//		}
//		String scriptString = scriptPath.replaceAll("/", "_");
		compiler.compileToJava(scriptSource, navajoIOConfig.getScriptPath(), navajoIOConfig.getCompiledScriptPath(), packagePath, scriptPackage, prc, navajoIOConfig,dependencies,tenant,hasTenantSpecificFile);
		//		logger.info("Javafile: "+javaFile);
//		System.err.println("Packages: "+packages);
		Set<String> dependentResources = new HashSet<String>();
		
		for (Dependency d : dependencies) {
			if("resource".equals(d.getType())) {
				if(d instanceof AdapterFieldDependency) {
					final AdapterFieldDependency adapterFieldDep = (AdapterFieldDependency)d;
					logger.info("It's an aadapter field. with multiple: "+adapterFieldDep.hasMultipleDependencies()+" type: "+d.getClass());
					logger.info("id: "+adapterFieldDep.getId());
					Operand op = expressionEvaluator.evaluate(adapterFieldDep.getId(), null);
					if(op!=null && op.value instanceof String) {
						logger.debug("Succeeded evaluation of id: "+((String)op.value));
						dependentResources.add((String) op.value);
					} else {
						logger.info("Eval failed");
					}
					logger.info("Resource dependency detected:"+d.getClass().getName()+" type: "+d.getType()+" dependency id: "+d.getId());

					Dependency[] subs = adapterFieldDep.getMultipleDependencies();
					if (subs!=null) {
						for (Dependency dependency : subs) {
							logger.info("Nested dependency detected:"+dependency.getClass().getName()+" type: "+dependency.getType());
						}
					}
				}
			}
		}
		generateFactoryClass(script, packagePath,dependentResources);

		generateManifest(scriptString,"1.0.0",packagePath, script,packages,compileDate);
		generateDs(packagePath, script,dependencies,dependentResources,tenant,hasTenantSpecificFile);
	}
	
	private void parseEntity(Navajo n) {
		
	}

	private void generateFactoryClass(String script, String packagePath, Set<String> resources) throws IOException {
		
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
		w.println("import com.dexels.navajo.script.api.*;");
		w.println();
		w.println("public class "+script+"Factory extends CompiledScriptFactory {");
		w.println("	protected String getScriptName() {");
		if ("".equals(packagePath)) {
			w.println("		return \"defaultPackage."+ script+"\";");
		} else {
			w.println("		return \""+javaPackagePath+"."+ script+"\";");
		}
		w.println("	}");
		for (String res : resources) {
			addResourceField(res, w);
		}
		
		w.println("public CompiledScript getCompiledScript() throws InstantiationException, IllegalAccessException, ClassNotFoundException {");
		w.println("	Class<? extends CompiledScript> c;");
		w.println("	c = (Class<? extends CompiledScript>) Class.forName(getScriptName());");
		w.println("	CompiledScript instance = c.newInstance();");
		w.println("	super.initialize(instance);");
		w.println("	return instance;");
		w.println("}");
		w.println("");
		for (String res : resources) {
			addResourceDependency(res, w,"set");
			addResourceDependency(res, w,"clear");
		}
		w.println("");
		
		w.println("}");
		w.flush();
		w.close();
		
	}
	private void addResourceField(String res, PrintWriter w) {
		w.println("private Object _"+res+";");
	}

	private void addResourceDependency(String res, PrintWriter w,String prefix) {
		w.println("void "+prefix+res+"(Object resource) {");
		w.println("  this._"+res+" = resource;");
		w.println("  "+prefix+"Resource(\""+res+"\",resource); ");
		w.println("}\n");
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
	
	private void generateDs(String packagePath, String script,List<Dependency> dependencies, Set<String> dependentResources,final String tenant, boolean hasTenantSpecificFile) throws IOException {
		
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
		String symbolicName = null;
//		String tenant = null;
		
		symbolicName = fullName.replaceAll("/", ".");
//		if(symbolicName.indexOf("_")!=-1) {
//			final String[] split = symbolicName.split("_");
//			symbolicName = split[0];
//			tenant = split[1];
//			logger.error("Anomaly in creating bundle: shouldn't happen");
//		}

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
		provide.setAttribute("interface", CompiledScriptFactory.class.getName());

		addProperty("navajo.scriptName","String",symbolicName, xe);
		if(hasTenantSpecificFile) {
			addProperty("navajo.tenant","String",tenant, xe);
			addProperty("service.ranking","Integer","1000", xe);
		} else {
			addProperty("service.ranking","Integer","0", xe);
			
		}
//		for (Dependency dependency : dependencies) {
//			XMLElement dep = new CaseSensitiveXMLElement("reference");
//			dep.setAttribute("bind", "setDependency");
//			dep.setAttribute("deptype", dependency.getType());
//			dep.setAttribute("depId", dependency.getId());
//			dep.setAttribute("depStamp", dependency.getCurrentTimeStamp());
//			xe.addChild(dep);
//			logger.debug("Dependency: "+dep.toString());
//		}
//		  <reference bind="setIOConfig" cardinality="1..1" interface="com.dexels.navajo.server.NavajoIOConfig" name="NavajoIOConfig" policy="dynamic" unbind="clearIOConfig"/>
		for (String resource : dependentResources) {
			XMLElement dep = new CaseSensitiveXMLElement("reference");
			dep.setAttribute("bind", "set"+resource);
			dep.setAttribute("unbind", "clear"+resource);
			dep.setAttribute("policy", "static");
			dep.setAttribute("cardinality", "1..1");
			dep.setAttribute("interface", "javax.sql.DataSource");
			dep.setAttribute("target", "(navajo.resource.name="+resource+")");
			xe.addChild(dep);
		}
		PrintWriter w = new PrintWriter(navajoIOConfig.getOutputWriter(navajoIOConfig.getCompiledScriptPath(), packagePath, script, ".xml"));
		w.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xe.write(w);
		w.flush();
		w.close();
	}

	protected void addProperty(final String key, final String type, final String value, final XMLElement xe) {
		XMLElement property = new CaseSensitiveXMLElement("property");
		xe.addChild(property);
		property.setAttribute("name", key);
		property.setAttribute("type", type);
		property.setAttribute("value", value);
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
	
	void setExpressionEvaluator(ExpressionEvaluator e) {
		this.expressionEvaluator = e;
	}

	void clearExpressionEvaluator(ExpressionEvaluator e) {
		this.expressionEvaluator = null;
	}

}
