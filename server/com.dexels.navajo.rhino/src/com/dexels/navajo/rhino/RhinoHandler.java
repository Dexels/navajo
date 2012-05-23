package com.dexels.navajo.rhino;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.listeners.NavajoDoneException;
import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.AuthorizationException;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.ServiceHandler;
import com.dexels.navajo.server.SystemException;
import com.dexels.navajo.server.UserException;

public class RhinoHandler extends ServiceHandler {

	
	private final static Logger logger = LoggerFactory
			.getLogger(RhinoHandler.class);
	@Override
	// Will always recompile, no big deal, it's pretty fast. TODO: Only
	// recompile when needed.
	public Navajo doService() throws NavajoException, UserException,
			SystemException, AuthorizationException, NavajoDoneException {

		// System.err.println("Rhino: Handler");
		CompiledScript cso;
		try {
			cso = compileScript(access, new StringBuffer());
			access.setCompiledScript(cso);
			cso.run(access);
			return access.getOutputDoc();
		} catch (NavajoDoneException e1) {
			// e1.printStackTrace();
			logger.info("NavajoDone caught in handler. Rethrowing");
			throw e1;
		} catch (Throwable e) {

			e.printStackTrace();
			try {
				Navajo generateErrorMessage = DispatcherFactory.getInstance()
						.generateErrorMessage(access, e.getMessage(), -1, 1, e);
				generateErrorMessage.write(System.err);
				access.setOutputDoc(generateErrorMessage);
				return generateErrorMessage;
			} catch (Throwable e1) {
				e1.printStackTrace();
			}

		}
		return null;
	}

	public static CompiledScript compileScript(Access a,
			StringBuffer compilerErrors) throws Exception {
		
		InputStream inStr = DispatcherFactory.getInstance().getNavajoConfig()
				.getScript(a.getRpcName());
		File compiledPath = new File(DispatcherFactory.getInstance()
				.getNavajoConfig().getCompiledScriptPath()
				+ "/" + a.getRpcName() + ".js");
		File scriptPath = new File(DispatcherFactory.getInstance()
				.getNavajoConfig().getScriptPath()
				+ "/" + a.getRpcName() + ".xml");
		if (!scriptPath.exists()) {
			scriptPath = new File(DispatcherFactory.getInstance()
					.getNavajoConfig().getScriptPath()
					+ "/" + a.getRpcName() + ".tsl");

		}
		long lastCompiled = compiledPath.lastModified();
		long scriptEdited = scriptPath.lastModified();
		if (scriptEdited < lastCompiled) {
			// nothing to compile
			logger.info("Skipping compile step!");
		} else {

			// TODO reuse this bugger
			BasicScriptCompiler compiler = new BasicScriptCompiler();
			
			logger.info("Compiling: " + compiledPath.getAbsolutePath()
					+ " to: " + scriptPath.getAbsolutePath()
					+ " (actually the other way around)");

			compiler.setIncludeBase(new File(DispatcherFactory.getInstance()
					.getNavajoConfig().getScriptPath()));

			File compileScriptDir = compiledPath.getParentFile();
			if (!compileScriptDir.exists()) {
				compileScriptDir.mkdirs();
			}
			if (inStr == null) {
				throw new FileNotFoundException("Script "
						+ scriptPath.getAbsolutePath() + " not found.");
			}
			FileOutputStream baos = new FileOutputStream(compiledPath);
			compiler.compileTsl(a.getRpcName(), inStr, baos, compilerErrors);

			baos.flush();
			baos.close();
		}

		ClassLoader cl = DispatcherFactory.getInstance().getNavajoConfig()
				.getClassloader();
		try {
			Class.forName("com.dexels.navajo.rhino.RhinoCompiledScript", true,
					cl);
		} catch (Throwable e) {
			logger.info("Classloading error in Rhino Handler. This can be removed once OSGi is functional");
		}
		RhinoCompiledScript rcs = new RhinoCompiledScript(); // (CompiledScript)
																// rcsClass.newInstance();
		rcs.setScript(compiledPath);
		// Method ss = rcsClass.getMethod("setScript", File.class);
		// ss.invoke(rcs, compiledPath);
		return rcs;

	}

	@Override
	public boolean needsRecompile() {
		logger.warn("Need to implement needsRecompile() for RhinoHandler.");
		return false;
	}

}
