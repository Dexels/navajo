package navajotsl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.tsl.custom.CustomClassloaderJavaFileManager;
import com.dexels.navajo.compiler.tsl.custom.CustomJavaFileObject;


public class Activator implements BundleActivator {

	private static BundleContext context;
	private StandardJavaFileManager fileManager;
	private CustomClassloaderJavaFileManager customJavaFileManager;
	private JavaCompiler compiler;
	private DiagnosticListener<JavaFileObject> compilerOutputListener;
	
	private final static Logger logger = LoggerFactory
			.getLogger(Activator.class);
	
	static BundleContext getContext() {
		return context;
	}


	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		compiler = ToolProvider.getSystemJavaCompiler();
		compilerOutputListener = new DiagnosticListener<JavaFileObject>() {

			@Override
			public void report(Diagnostic<? extends JavaFileObject> jfo) {
				if(jfo.getKind()==javax.tools.Diagnostic.Kind.ERROR || jfo.getKind()==javax.tools.Diagnostic.Kind.MANDATORY_WARNING) {
					logger.warn("Compilation problem: "+jfo.getMessage(Locale.ENGLISH));
				}
				
			}
			
		};
		fileManager = compiler.getStandardFileManager(compilerOutputListener, null, null);
		customJavaFileManager = new CustomClassloaderJavaFileManager(context, getClass().getClassLoader(), fileManager);

		// test the example, it shouldn't really be here, actually
//		JavaFileObject jfo = compile(getJavaSourceFileObject("mathtest/Calculator", getExampleCode()));
//		if (jfo==null) {
//			logger.error("compilation failed.");
//		} else {
//			logger.info("compilation ok.");
//		}
	}

	private JavaFileObject compile(JavaFileObject javaSource) throws IOException {
		Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(javaSource);

		CompilationTask task = compiler.getTask(null, customJavaFileManager, compilerOutputListener,new ArrayList<String>(), null, fileObjects);
		task.call();
		CustomJavaFileObject jfo = (CustomJavaFileObject) customJavaFileManager.getJavaFileForInput(StandardLocation.CLASS_OUTPUT, "mathtest.Calculator", Kind.CLASS);
		return jfo;
	}
	

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		customJavaFileManager.close();
		Activator.context = null;
	}

	
	private InputStream getExampleCode() {
        String example = 									
        		"package mathtest;\n"+
                "public class Calculator { \n"
               + "  public void testAdd() { "
               + "    System.out.println(200+300); \n"
               + "    org.apache.commons.io.IOUtils aaaa; \n"
               + "    com.dexels.navajo.adapter.NavajoAccess nao; \n"
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
