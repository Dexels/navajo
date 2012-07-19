package com.dexels.navajo.compiler.tsl.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.ScriptCompiler;
import com.dexels.navajo.mapping.compiler.TslCompiler;
import com.dexels.navajo.server.NavajoIOConfig;

public class TslCompilerComponent implements ScriptCompiler {

	private NavajoIOConfig navajoIOConfig = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TslCompilerComponent.class);
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.compiler.tsl.ScriptCompiler#compileTsl(java.lang.String)
	 */
	@Override
	public void compileTsl(String script) throws Exception {
		String packagePath = script.substring(0,script.lastIndexOf('/'));
		String javaFile = TslCompiler.compileToJava(script, navajoIOConfig.getScriptPath(), navajoIOConfig.getCompiledScriptPath(), packagePath, null, navajoIOConfig);
		logger.info("Javafile: "+javaFile);
	}
	
	public void setIOConfig(NavajoIOConfig config) {
		this.navajoIOConfig = config;
	}
	
	public void clearIOConfig(NavajoIOConfig config) {
		this.navajoIOConfig = null;
	}
	
	public void activate() {
		logger.info("ACTIVATE");
//		TslCompiler.compileToJava(script, input, output, packagePath, getClass().getClassLoader(),navajoIOConfig);
	}
	
	public static void main(String[] args) {
		String script = "club/InitUpdateClub";
		String pack = script.substring(0,script.lastIndexOf('/'));
		System.err.println("pack: "+pack);
	}
}
