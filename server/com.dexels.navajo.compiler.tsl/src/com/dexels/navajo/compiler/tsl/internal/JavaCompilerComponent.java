package com.dexels.navajo.compiler.tsl.internal;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.JavaCompiler;
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

	/**
	 * @param config
	 *            the navajo config to clear
	 */
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
		logger.debug("Activating java compiler");
	}

	public void deactivate() {
		logger.debug("Deactivating java compiler");
	}

	@Override
	public void compileJava(final String script) throws Exception {
		// grab the file from the orig location
		final Semaphore block = new Semaphore(1);
		Thread t = new Thread(new Internal(navajoIOConfig,javaCompiler,script,block));
		System.err.println("acquire");
		block.acquire();
		System.err.println("starting");
		t.start();
		System.err.println("blocking");
		block.acquire();
		System.err.println("Ready");
		
	}

	private static class Internal implements Runnable {
		
		private final NavajoIOConfig navajoIOConfig;
		private final OSGiJavaCompiler javaCompiler;
		private String script;
		private final Semaphore semaphore;
		
		public Internal(NavajoIOConfig config, OSGiJavaCompiler javaCompiler, String script, Semaphore sema) {
			navajoIOConfig = config;
			this.javaCompiler = javaCompiler;
			this.script = script;
			this.semaphore = sema;
		}
		
		private void internalCompile() throws FileNotFoundException,IOException {
			final File file = new File(navajoIOConfig.getCompiledScriptPath() + "/"
					+ script + ".java");
			// but alter the path dir for the compiler:
			if (script.indexOf('/') == -1) {
				logger.warn("Creating compiledScript for default package!");
				script = "defaultPackage/" + script;
			}
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				byte[] bb = javaCompiler.compile(script, fis);
				if (bb == null) {
					logger.warn("Java compilation failed for script: " + script);
				} else {
					navajoIOConfig.writeOutput(script, ".class",
							new ByteArrayInputStream(bb));
				}
			} finally {
				if(fis!=null) {
					fis.close();
				}
			}			
	}

		@Override
		public void run() {
			try {
				internalCompile();
			} catch (FileNotFoundException e) {
				logger.error("Compile error",e);
			} catch (Throwable e) {
				logger.error("Compile error",e);
			} finally {
				semaphore.release();
			}
		}
	}
//	private void internalCompile(String script) throws FileNotFoundException,
//			IOException {
//		final File file = new File(navajoIOConfig.getCompiledScriptPath() + "/"
//				+ script + ".java");
//		// but alter the path dir for the compiler:
//		if (script.indexOf('/') == -1) {
//			logger.warn("Creating compiledScript for default package!");
//			script = "defaultPackage/" + script;
//		}
//		FileInputStream fis = null;
//		try {
//			fis = new FileInputStream(file);
//			byte[] bb = javaCompiler.compile(script, fis);
//			if (bb == null) {
//				logger.warn("Java compilation failed for script: " + script);
//			} else {
//				navajoIOConfig.writeOutput(script, ".class",
//						new ByteArrayInputStream(bb));
//			}
//		} finally {
//			if(fis!=null) {
//				fis.close();
//			}
//		}
//	}
}
