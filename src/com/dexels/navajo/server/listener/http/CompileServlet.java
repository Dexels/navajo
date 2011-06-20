package com.dexels.navajo.server.listener.http;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.mapping.compiler.TslCompiler;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.NavajoConfigInterface;
import com.dexels.navajo.server.SystemException;
import com.dexels.navajo.util.AuditLog;

@Deprecated
public class CompileServlet extends HttpServlet implements Servlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9070062352955050654L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DispatcherFactory.getInstance();
	}

	public static CompiledScript compileScript(Access a, String serviceName,
			StringBuffer compilerErrors) throws Exception {

		NavajoConfigInterface properties = DispatcherFactory.getInstance()
				.getNavajoConfig();
		TslCompiler tslCompiler = new com.dexels.navajo.mapping.compiler.TslCompiler(
				properties.getClassloader());

		String prefix = serviceName.substring(0, serviceName.lastIndexOf("/"));
		String script = serviceName.substring(serviceName.lastIndexOf("/") + 1,
				serviceName.length());

		File sourceDir = new File(properties.getScriptPath());
		File packageDir = new File(sourceDir, prefix);
		File sourceFile = new File(packageDir, script + ".xml");

		try {

			tslCompiler.compileScript(serviceName, properties.getScriptPath(),
					properties.getCompiledScriptPath(), prefix,
					properties.getConfigPath());
			// compilerErrors.append(recompileJava(a, sourceFileName,
			// sourceFile, serviceName.replaceAll("/","."),
			// targetFile,serviceName));

		} catch (SystemException ex) {
			sourceFile.delete();
			AuditLog.log(AuditLog.AUDIT_MESSAGE_SCRIPTCOMPILER,
					ex.getMessage(), Level.SEVERE, a.accessID);
			throw ex;
		}

		return null;
	}

	public static void main(String[] args) {
		String aap = "aap/noot/mies";
		String prefix = aap.substring(0, aap.lastIndexOf("/"));
		String script = aap.substring(aap.lastIndexOf("/") + 1, aap.length());
		System.err.println("aap: " + aap);
		System.err.println("prefix: " + prefix);
		System.err.println("script: " + script);
	}
}
