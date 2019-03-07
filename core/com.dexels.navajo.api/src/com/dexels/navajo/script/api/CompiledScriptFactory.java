package com.dexels.navajo.script.api;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CompiledScriptFactory {
	
	private static final Logger logger = LoggerFactory
			.getLogger(CompiledScriptFactory.class);
	
	private String serviceName;
	private String debug;
	private final Map<String,Object> resources = new HashMap<>();
	
	public abstract CompiledScriptInterface getCompiledScript() throws InstantiationException, IllegalAccessException, ClassNotFoundException;
	
	
	public final void initialize(CompiledScriptInterface instance){
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
		instance.setConfigDebugMode(debug);
		instance.setFactory(this);
	}
	
	protected abstract String getScriptName();
	
	public void activate(Map<String,String> properties) {
		serviceName = properties.get("navajo.scriptName");
		debug = properties.get("navajo.debug");
	}

	public void deactivate() {
		logger.info("Deactivating script: {}", serviceName);
	}
	

	public Object getResource(String name) {
		return resources.get(name);
	}


	protected void setResource(String name, Object o) {
		resources.put(name, o);
	}

	protected void clearResource(String name, Object o) {
		resources.remove(name);
	}

}
