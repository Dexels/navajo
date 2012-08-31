package com.dexels.navajo.server.scriptengine;

import java.io.File;
import java.io.FileReader;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.Access;
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
		if(scriptFile.getName().indexOf('.')==-1) {
			throw new IllegalArgumentException("No extension found, unable to determine script engine type");
		}
      int dotPos = scriptFile.getName().lastIndexOf(".");
      String extension = scriptFile.getName().substring(dotPos+1);
//      System.err.println("Scriptfile: "+scriptFile.getName()+"ext: "+extension);
      
      ScriptEngineManager sem = new ScriptEngineManager(); //DispatcherFactory.getScriptEngineManager();
      
      ScriptEngine se = sem.getEngineByExtension(extension);
      if(se==null) {
			throw new IllegalArgumentException("No engine found, unable to determine script engine type for extension: "+extension);      	
      }
		Navajo result = NavajoFactory.getInstance().createNavajo();
		se.getBindings(ScriptContext.ENGINE_SCOPE).clear();
		
		se.put("input", in);
		se.put("scriptName", scriptName);
		se.put("scriptFile", scriptFile);
		se.put("access", access);
		se.put("output", result);
		se.put("navajoConfig", DispatcherFactory.getInstance().getNavajoConfig());
		
		String adapterPath = DispatcherFactory.getInstance().getNavajoConfig().getAdapterPath();
		String includePath = adapterPath + se.getFactory().getLanguageName()+"/include";
		// TODO rewrite to OSGi services
		Class<? extends IncludeManager> manager = (Class<? extends IncludeManager>) Class.forName("com.dexels.navajo.server.scriptengine.include."+se.getFactory().getLanguageName());
		IncludeManager incl = manager.newInstance();
		incl.loadIncludes(se, includePath);
		FileReader fr = new FileReader(scriptFile);
		se.eval(fr);
		fr.close();
		return result;
	}


	public static void main(String[] args) {

	}

}
