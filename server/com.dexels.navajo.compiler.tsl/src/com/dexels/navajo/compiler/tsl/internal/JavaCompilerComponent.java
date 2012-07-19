package com.dexels.navajo.compiler.tsl.internal;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.JavaCompiler;
import com.dexels.navajo.compiler.ScriptCompiler;
import com.dexels.navajo.mapping.compiler.TslCompiler;
import com.dexels.navajo.server.NavajoIOConfig;
import com.dexels.osgicompiler.OSGiJavaCompiler;

public class JavaCompilerComponent implements JavaCompiler {

	private NavajoIOConfig navajoIOConfig = null;
	private OSGiJavaCompiler javaCompiler = null;

	private final static Logger logger = LoggerFactory
			.getLogger(JavaCompilerComponent.class);

	
	public void setIOConfig(NavajoIOConfig config) {
		this.navajoIOConfig = config;
	}
	
	public void clearIOConfig(NavajoIOConfig config) {
		this.navajoIOConfig = null;
	}
	
	public void setJavaCompiler(OSGiJavaCompiler comp) {
		javaCompiler = comp;
	}

	public void clearJavaCompiler(OSGiJavaCompiler comp) {
		javaCompiler = comp;
	}
	
	public void activate() {
		
	}

	public void deactivate() {
		
	}

	
	@Override
	public void compileJava(String script) throws Exception {
//		String pack = script.substring(0,script.lastIndexOf('/'));
		final File file = new File(navajoIOConfig.getCompiledScriptPath()+"/"+script+".java");
		System.err.println("File: "+file.getAbsolutePath());
		FileInputStream fis = new FileInputStream(file);
		byte[] bb = javaCompiler.compile(script, fis);
		fis.close();
		navajoIOConfig.writeOutput(script, ".class", new ByteArrayInputStream(bb));
	}

}
