package com.dexels.navajo.dev.console;


import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.JavaCompiler;
import com.dexels.navajo.compiler.ScriptCompiler;
import com.dexels.osgicompiler.OSGiJavaCompiler;

public class CompileCommand {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(CompileCommand.class);
	private final BundleContext context;

	public CompileCommand(BundleContext context) {
		this.context = context;
	}

	private ServiceReference<ScriptCompiler> getScriptCompiler() {
		return context.getServiceReference(ScriptCompiler.class);
	}
	
	private ServiceReference<JavaCompiler> getJavaCompiler() {
		return context.getServiceReference(JavaCompiler.class);
	}
	
	public void compile(String script) {
		ServiceReference<ScriptCompiler> sc = getScriptCompiler();
		if(sc==null) {
			logger.error("No script compiler service reference found.");
			return;
		}
		ScriptCompiler compiler = context.getService(sc);
		if(compiler==null) {
			logger.error("Null compiler in service reference?!");
			return;
		}
		ServiceReference<JavaCompiler> sc2 = getJavaCompiler();
		if(sc2==null) {
			logger.error("No java compiler service reference found.");
			return;
		}
		JavaCompiler javaCompiler = context.getService(sc2);
		if(javaCompiler==null) {
			logger.error("Null java compiler in service reference?!");
			return;
		}
		try {
			compiler.compileTsl(script);
			logger.info("Tsl compiling complete.");
			
			javaCompiler.compileJava(script);
			logger.info("Compiling java ocmplete.");
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
		context.ungetService(sc);
	}
}
