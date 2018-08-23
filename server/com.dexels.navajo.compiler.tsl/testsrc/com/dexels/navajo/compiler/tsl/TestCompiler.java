package com.dexels.navajo.compiler.tsl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

import org.eclipse.jdt.internal.compiler.tool.EclipseCompiler;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestCompiler {
	
	
	private final static Logger logger = LoggerFactory.getLogger(TestCompiler.class);


	@Test
	public void testSimpleProgram() {
		try {
			System.err.println("Start");
//			EclipseCompiler e;
			EclipseCompiler e = new EclipseCompiler();
//			public EclipseCompilerImpl(PrintWriter out, PrintWriter err, boolean systemExitWhenFinished) {
//				super(out, err, systemExitWhenFinished, null/*options*/, null/*progress*/);
//			}
			System.err.println("source version: "+e.getSourceVersions());
			StandardJavaFileManager aj = e.getStandardFileManager(new DiagnosticListener<JavaFileObject>() {

				@Override
				public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
					System.err.println("Thing: "+diagnostic);
				}
			}, Locale.getDefault(), Charset.forName("UTF-8"));
			System.err.println("got fm");
			InputStream is = TestCompiler.class.getClassLoader().getResourceAsStream("Example.java.txt");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ByteArrayOutputStream err = new ByteArrayOutputStream();
			System.err.println("ready");
//			JavaCompiler eclipseCompiler = getEclipseCompiler();
			System.err.println("compiler created");
			e.run(is, baos, err, new String[] {});
//			e.getTask(., fileManager, someDiagnosticListener, options, classes, compilationUnits)
			System.err.println("compiled");
			System.err.println("ST> "+new String(err.toByteArray()));
			Thread.sleep(10000);
			
			System.err.println("bye");
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			System.err.println("finally");
		}
		System.err.println("exit");
	}
	
	protected JavaCompiler getEclipseCompiler() {
		try {
			Class<? extends JavaCompiler> jc = (Class<? extends JavaCompiler>) Class
					.forName("org.eclipse.jdt.internal.compiler.tool.EclipseCompiler");
			JavaCompiler jj = jc.newInstance();
			return jj;
		} catch (Exception e) {
			logger.warn("Error retrieving Eclipse compiler", e);
		}
		return null;
	}
}
