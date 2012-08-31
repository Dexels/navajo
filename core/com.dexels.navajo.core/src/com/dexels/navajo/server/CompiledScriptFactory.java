package com.dexels.navajo.server;

import java.io.File;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.loader.NavajoClassSupplier;
import com.dexels.navajo.mapping.CompiledScript;

public abstract class CompiledScriptFactory {
	
	private final static Logger logger = LoggerFactory
			.getLogger(CompiledScriptFactory.class);
	
	private String serviceName;
	
	public abstract CompiledScript getCompiledScript() throws InstantiationException, IllegalAccessException, ClassNotFoundException;
	
	
	public final void initialize(CompiledScript instance){
		instance.setClassLoader(new NavajoClassSupplier() {
			
			@Override
			public File[] getJarFiles(String path, boolean beta) {
				return null;
			}
			
			@Override
			public Class<?> getCompiledNavaScript(String className)
					throws ClassNotFoundException {
				return null;
			}
			
			@Override
			public Class<?> getClass(String className) throws ClassNotFoundException {
				return Class.forName(className, true, CompiledScriptFactory.this.getClass().getClassLoader()); 
			}
		});
	}
	
	protected abstract String getScriptName();
	
	public void activate(Map<String,String> properties) {
//		logger.info("Activating compiledscriptfactory");
		serviceName = properties.get("navajo.scriptName");
		
	}

	public void deactivate() {
		logger.info("Deactivating script: "+serviceName);
	}
}
