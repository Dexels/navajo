package com.dexels.navajo.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.loader.NavajoClassSupplier;
import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;
import com.dexels.navajo.mapping.compiler.meta.Dependency;
import com.dexels.navajo.mapping.compiler.meta.ExpressionValueDependency;
import com.dexels.navajo.mapping.compiler.meta.IncludeDependency;
import com.dexels.navajo.mapping.compiler.meta.InheritDependency;
import com.dexels.navajo.mapping.compiler.meta.JavaDependency;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.GenericHandler;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.test.TestDispatcher;

public class ScriptIntrospection {

	public static final String DEFAULT_SERVER_XML = "config/server.xml";
	
	// Setters
	public String script;
	public String packageName = "";
	
	private CompiledScript myCompiledScript;
	private CVSVersionControl cvs;
	private String rootPath;
	private String errors = "";
	private boolean hasErrors = false;
	
	public ScriptIntrospection() {
		// Empty constructor, can be used from scripts.
	}
	
	public ScriptIntrospection(String rootPath, String script) {
		try {
			this.rootPath = rootPath;
			initializeDispatcher(rootPath);
			this.script = script;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected static void initializeDispatcher(String rootPath) throws Exception {
		if ( DispatcherFactory.getInstance() == null ) {
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
					configurationUrl.openStream(), rootPath,rootPath); 
			TestDispatcher td = new TestDispatcher(nc);
			new DispatcherFactory(td);
		}

	}
	
	private void initializeScript(String script) {
//		GenericHandler gh = new GenericHandler();
		StringBuffer compilerErrors = new StringBuffer();
//		String error = "";
		try {
			Access a = new Access();
			a.rpcName = script;
			myCompiledScript = GenericHandler.compileScript(a, compilerErrors);
		} catch (Throwable t) {
			errors = compilerErrors.toString();
			if ( errors.equals("" )) {
				errors = t.getMessage();
			}
			hasErrors = true;
		}
//		if ( !compilerErrors.toString().equals("") ) {
//			System.err.println(compilerErrors.toString());
//		}
		// CVS
		cvs = new CVSVersionControl(new File(DispatcherFactory.getInstance().getNavajoConfig().getScriptPath() + "/" + script + ".xml"));
	}
	
	@SuppressWarnings({"unused","rawtypes"})
	private final void printAllDependencies(int indent, Class specific) {
		ArrayList<Dependency> dependencies = myCompiledScript.getDependentObjects();
		showDependencies(indent, dependencies, specific, specific);
	}
	
	@SuppressWarnings("unused")
	private final void printAllDependencies(int indent) throws Exception {
		
		if ( getCompiledScript() == null ) {
			return;
		}
		ArrayList<Dependency> dependencies = getCompiledScript().getDependentObjects();
		
		System.err.println("================ DEPENDENCIES ==================");
		showDependencies(indent, dependencies, InheritDependency.class, null);
		showDependencies(indent, dependencies, IncludeDependency.class, null);
		showDependencies(indent, dependencies, JavaDependency.class, null);
		//showDependencies(indent, dependencies, NavajoDependency.class, null);
		showDependencies(indent, dependencies, AdapterFieldDependency.class, null);
		showDependencies(indent, dependencies, ExpressionValueDependency.class, null);
		System.err.println("================================================");
	}
	
	@SuppressWarnings("unused")
	private final void printVersionInfo() {
		if ( cvs == null ) {
			return;
		}
		System.err.println("================= VERSION INFO =================");
		System.err.println("Version: " + cvs.getVersion());
		System.err.println("Date   : " + cvs.getDate());
		System.err.println("Author : " + cvs.getAuthor());
		System.err.println("Root   : " + cvs.getCvsRoot());
		System.err.println("Repos. : " + cvs.getRepository());
		System.err.println("================================================");
	}
	
	private final String printIndent(int count) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			sb.append(' ');
		}
		return sb.toString();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private final void showDependencies(int indent, ArrayList<Dependency> deps, Class check, Class subCheck)  {
		for (int i = 0; i < deps.size(); i++) {
			Dependency dep = deps.get(i);
			//System.err.println("check = " + check + ",found = " + dep.getClass() + "->" + check.isAssignableFrom(dep.getClass()));
			if ( check.isAssignableFrom(dep.getClass()) ) {
				if ( check.isAssignableFrom(AdapterFieldDependency.class) ) {
					System.err.println(printIndent(indent) + check.getSimpleName() + ": " + 
							((AdapterFieldDependency) deps.get(i)).getEvaluatedId() + " (class=" + 
							((AdapterFieldDependency) deps.get(i)).getJavaClass() + ", type=" + 
							((AdapterFieldDependency) deps.get(i)).getType() +", id=" + deps.get(i).getId() + ")");
				} else {
					System.err.println(printIndent(indent) + check.getSimpleName() + ": " + deps.get(i).getId() + " (" + dep.getType() + ")");
				}
			}
		}
	}
	
	public void getAllScriptDependentScripts(HashSet<String> allScripts) throws Exception {
		
		if ( getCompiledScript() == null ) {
			return;
		}
		
		ArrayList<Dependency> deps = getCompiledScript().getDependentObjects();
		for (int i = 0; i < deps.size(); i++) {
			Dependency dep = deps.get(i);
			
			if ( dep instanceof IncludeDependency ) {
				allScripts.add(deps.get(i).getId());
			}
			if ( dep instanceof AdapterFieldDependency ) {
				String type = ((AdapterFieldDependency) deps.get(i)).getType();
				if ( type.equals("script") )  {
					String name = ((AdapterFieldDependency) deps.get(i)).getEvaluatedId();
					allScripts.add(name);
					// Check recursively.
					ScriptIntrospection depScript = new ScriptIntrospection(this.rootPath, name);
					depScript.getAllScriptDependentScripts(allScripts);
				}
			}
		}
		
	}
	
	public static void main(String [] args) throws Exception {
		
//		String rootPath = args[0] + "/";
//		String script = args[1];
//		
//		ScriptIntrospection si = new ScriptIntrospection(rootPath, script);
//		si.printAllDependencies(0);
//		si.printVersionInfo();		
//		HashSet<String> allScripts = new HashSet<String>();
//		si.getAllScriptDependentScripts(allScripts);
//		System.err.println("================== ALL DEPENDENT SCRIPTS ====================");
//		Iterator all = allScripts.iterator();
//		while ( all.hasNext() ) {
//			System.err.println(all.next());
//		}
//		System.err.println("=============================================================");
//		if ( si.getCompiledScript() != null ) {
//			System.err.println("Original author    : " + si.getCompiledScript().getAuthor());
//			System.err.println("Script description : " + si.getCompiledScript().getDescription());
//			System.err.println("Script type        : " + si.getCompiledScript().getScriptType());
//		}
//		System.err.println("Has errors         : " + si.getHasErrors());
//		if ( si.getHasErrors() ) {
//			System.err.println("Error message: " + si.getError());
//		}
//		System.exit(1);
		
		
		ScriptIntrospection s = new ScriptIntrospection();
		File f = new File("/home/arjen/projecten/sportlink-serv/navajo-tester/auxilary/scripts/navajo");
		ArrayList<ScriptDefinition> all = new ArrayList<ScriptDefinition>();
		s.getScriptsFromPath("navajo", f, all);
		for (int i = 0; i < all.size(); i++) {
			System.err.println(all.get(i).getScriptName());
		}
	}

	public boolean getHasErrors() {
		if ( myCompiledScript == null && !hasErrors ) {
			initializeScript(script);
		}
		return hasErrors;
	}
	
	public String getError() {
		return errors;
	}
	
	public CompiledScript getCompiledScript() throws Exception {
		if ( myCompiledScript == null && !hasErrors ) {
			initializeScript(script);
		}
		return myCompiledScript;
	}

	public CVSVersionControl getCvs() throws Exception {
		if ( myCompiledScript == null && !hasErrors ) {
			initializeScript(script);
		}
		return cvs;
	}

	public void setScript(String script) {
		this.script = script;
	}
	
	private void getScriptsFromPath(String base, File f, ArrayList<ScriptDefinition> list) {

		File [] files = f.listFiles();
		for ( int i = 0; i < files.length; i++ ) {
			if ( files[i].isDirectory() ) {
				String newBase = ( !"".equals(base) ? base + "/" : "" )  + files[i].getName();
				getScriptsFromPath(newBase, files[i], list);
			} else {
				String name = files[i].getName();
				if ( name.endsWith(".xml") ) {
					String scriptName = ( !"".equals(base) ? base + "/" : "" ) + name;
					scriptName = scriptName.replaceAll("\\.xml", "");
					list.add(new ScriptDefinition(scriptName));
				}
			}
		}
	}
	
	public ScriptDefinition [] getScripts() {
		ArrayList<ScriptDefinition> names = new ArrayList<ScriptDefinition>();
		File parent = new File(DispatcherFactory.getInstance().getNavajoConfig().getScriptPath() +  
				           ( !"".equals(packageName) ? "/" + packageName : "" ) );
		getScriptsFromPath(packageName, parent, names);
		ScriptDefinition [] result = new ScriptDefinition[names.size()];
		result = (ScriptDefinition []) names.toArray(result);
		
		return result;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	
}
