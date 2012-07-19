package com.dexels.osgicompiler.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject.Kind;

import org.apache.commons.io.IOUtils;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.osgicompiler.OSGiJavaCompiler;
import com.dexels.osgicompiler.filemanager.impl.CustomClassloaderJavaFileManager;
import com.dexels.osgicompiler.filemanager.impl.CustomJavaFileObject;

public class OSGiJavaCompilerImplementation implements OSGiJavaCompiler {

	
	private final static Logger logger = LoggerFactory
			.getLogger(OSGiJavaCompilerImplementation.class);
	private BundleContext context;
	private StandardJavaFileManager fileManager;
	private CustomClassloaderJavaFileManager customJavaFileManager;
	private JavaCompiler compiler;
	private DiagnosticListener<JavaFileObject> compilerOutputListener;

	public OSGiJavaCompilerImplementation() {
		
	}
	
	public void activateCompiler(ComponentContext c) {
		logger.info("Activating java compiler.");
		this.context = c.getBundleContext();
		compiler = ToolProvider.getSystemJavaCompiler();
		compilerOutputListener = new DiagnosticListener<JavaFileObject>() {

			@Override
			public void report(Diagnostic<? extends JavaFileObject> jfo) {

				logger.warn("Compilation problem: "+jfo.getMessage(Locale.ENGLISH));
				
			}
			
		};
		fileManager = compiler.getStandardFileManager(compilerOutputListener, null, null);
		customJavaFileManager = new CustomClassloaderJavaFileManager(context, getClass().getClassLoader(), fileManager);

		// test the example, it shouldn't really be here, actually
//		try {
//			test();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public void deactivate() {
		logger.info("Deactivating java compiler");
		try {
			customJavaFileManager.close();
		} catch (IOException e) {
			logger.error("Error closing custom file manager",e);
		}
	}
	
	public byte[] compile(String className, InputStream source) throws IOException {
		 JavaFileObject javaSource = getJavaSourceFileObject(className, source);
		 Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(javaSource);

		CompilationTask task = compiler.getTask(null, customJavaFileManager, compilerOutputListener,new ArrayList<String>(), null, fileObjects);
		task.call();
		CustomJavaFileObject jfo = (CustomJavaFileObject) customJavaFileManager.getJavaFileForInput(StandardLocation.CLASS_OUTPUT, className, Kind.CLASS);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(jfo.openInputStream(),baos);
		return baos.toByteArray();
	}
	
	private void test() throws IOException {
		byte[] jfo = compile("mathtest/Calculator",getExampleCode());
		if (jfo==null) {
			logger.error("compilation failed.");
		} else {
			logger.info("compilation ok: "+jfo.length);
		}
	}

	private InputStream getExampleCode() {
        String example = 									
        		"package mathtest;\n"+
                "public class Calculator { \n"
               + "  public void testAdd() { "
               + "    System.out.println(200+300); \n"
               + "    org.apache.commons.io.IOUtils aaaa; \n"
//               + "   testcompiler.Activator a = new testcompiler.Activator();} \n"
             + "   } \n"
               + "  public static void main(String[] args) { \n"
               + "    Calculator cal = new Calculator(); \n"
               + "    cal.testAdd(); \n"
               + "  } " + "} ";	
        return new ByteArrayInputStream(example.getBytes());
	}
	
    private  JavaFileObject getJavaSourceFileObject(String className, InputStream contents) throws IOException
    {
        JavaFileObject so = null;
            so = new CustomJavaFileObject(className+ Kind.SOURCE.extension, URI.create("file:///" + className.replace('.', '/')
                    + Kind.SOURCE.extension), contents, Kind.SOURCE);
        return so;
    }
}
