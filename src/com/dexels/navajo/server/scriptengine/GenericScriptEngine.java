package com.dexels.navajo.server.scriptengine;

import java.io.File;
import java.io.FileReader;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.serviceplugin.JavaPlugin;

public class GenericScriptEngine extends JavaPlugin {

	private File scriptFile;
	private String scriptName;
	private Access access;
	
	public void setAccess(Access access) {
		this.access = access;
	}


	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}


	public void setScriptFile(File scriptFile) {
		this.scriptFile = scriptFile;
	}


	@Override
	public Navajo process(Navajo in) throws Exception {
		
//		List<ScriptEngineFactory> scriptFactories = DispatcherFactory.getScriptEngineManager().getEngineFactories();
//		for (ScriptEngineFactory factory : scriptFactories) {
//			String langName = factory.getLanguageName();
//			String langVersion = factory.getLanguageVersion();
//			System.err.println("Languages: " + langName + " version: " + langVersion + " extension: " + factory.getExtensions()
//					+ " engine: " + factory.getEngineName());
////
////			if (factory.getScriptEngine() instanceof Invocable) {
////				System.err.println("Yes, invocable");
////			}
//		}
//		
		if(scriptFile.getName().indexOf('.')==-1) {
			throw new IllegalArgumentException("No extension found, unable to determine script engine type");
		}
      int dotPos = scriptFile.getName().lastIndexOf(".");
      String extension = scriptFile.getName().substring(dotPos+1);
      System.err.println("Scriptfile: "+scriptFile.getName()+"ext: "+extension);
      
      ScriptEngineManager sem = new ScriptEngineManager(); //DispatcherFactory.getScriptEngineManager();
      
      ScriptEngine se = sem.getEngineByExtension(extension);
		Navajo result = NavajoFactory.getInstance().createNavajo();
		se.getBindings(ScriptContext.ENGINE_SCOPE).clear();
		
		se.put("input", in);
		se.put("scriptName", scriptName);
		se.put("scriptFile", scriptFile);
		se.put("access", access);
		se.put("output", result);
		
		if(se==null) {
			System.err.println("Warning: No scriptengine found");
		}
		String adapterPath = DispatcherFactory.getInstance().getNavajoConfig().getAdapterPath();
		String includePath = adapterPath + se.getFactory().getLanguageName()+"/include";
		// engine dependent, should be moved. (this is JRuby)
		se.eval("$LOAD_PATH.push('"+includePath+"');" +
				"$LOAD_PATH.push('"+DispatcherFactory.getInstance().getNavajoConfig().getScriptPath()+"');");
		System.err.println("Added include path: "+includePath);
	
		
//		if(se instanceof Invocable) {
//			System.err.println("Yes, invocable");
			FileReader fr = new FileReader(scriptFile);
			se.eval(fr);
			fr.close();
//			Invocable invocableEngine = (Invocable)se;
//			invocableEngine.invokeFunction("process", in, result);
//		}
		return result;
	}


	public static void main(String[] args) {

	}

}
