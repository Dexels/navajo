package com.dexels.osgicompiler.internal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;

import org.apache.commons.io.IOUtils;
import org.eclipse.jdt.internal.compiler.tool.EclipseCompiler;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.tsl.custom.CustomClassLoader;
import com.dexels.navajo.compiler.tsl.custom.CustomClassloaderJavaFileManager;
import com.dexels.navajo.compiler.tsl.custom.CustomJavaFileObject;
import com.dexels.navajo.script.api.CompilationException;
import com.dexels.osgicompiler.OSGiJavaCompiler;

public class OSGiJavaCompilerImplementation implements OSGiJavaCompiler {

	private static final Logger logger = LoggerFactory
			.getLogger(OSGiJavaCompilerImplementation.class);
	private BundleContext context;
	private StandardJavaFileManager fileManager;
	private JavaFileManager customJavaFileManager;
	private JavaCompiler compiler;
	private ServiceRegistration<JavaFileManager> fileManagerRegistration;
	private CustomClassLoader customClassLoader;
	private ServiceRegistration<ClassLoader> customClassLoaderRegistration;

	public OSGiJavaCompilerImplementation() {

	}

	public void activateCompiler(Map<String, Object> settings,
			BundleContext context) {
		logger.info("Activating java compiler.");
		this.context = context;
		modified(settings, context);

	}

	public void modified(Map<String, Object> settings, BundleContext context) {
		logger.info("Update settings");

		if (fileManagerRegistration != null) {
			fileManagerRegistration.unregister();
		}
		if (customClassLoaderRegistration != null) {
			customClassLoaderRegistration.unregister();
		}
		if (customJavaFileManager != null) {
			try {
				customJavaFileManager.close();
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
		}
		compiler = getEclipseCompiler();
		DiagnosticListener<JavaFileObject> compilerOutputListener = diagnostic -> {
			//
		};
		fileManager = compiler.getStandardFileManager(compilerOutputListener,
				null, null);
		customJavaFileManager = new CustomClassloaderJavaFileManager(Optional.ofNullable(context),
				getClass().getClassLoader(), fileManager);
		this.customClassLoader = new CustomClassLoader(customJavaFileManager);


		if(context!=null) { 		// support unit tests:
			this.fileManagerRegistration = this.context.registerService(
					JavaFileManager.class, customJavaFileManager, null);

			// (type=navajoScriptClassLoader)
			Dictionary<String, String> nsc = new Hashtable<>();
			nsc.put("type", "navajoScriptClassLoader");
				this.customClassLoaderRegistration = this.context.registerService(
						ClassLoader.class, customClassLoader, nsc);
		}

	}

	protected JavaCompiler getEclipseCompiler() {
		try {
			return new EclipseCompiler();
		} catch (Exception e) {
			logger.warn("Error retrieving Eclipse compiler", e);
		}
		return null;
	}

	public void deactivate() {
		logger.info("Deactivating java compiler");
		try {
			customJavaFileManager.close();
		} catch (IOException e) {
			logger.error("Error closing custom file manager", e);
		}
		if (fileManagerRegistration != null) {
			fileManagerRegistration.unregister();
		}
		if (customClassLoaderRegistration != null) {
			customClassLoaderRegistration.unregister();
		}
		this.compiler = null;
		this.fileManager = null;
		this.customClassLoader = null;
		this.customClassLoaderRegistration = null;
	}

	@Override
	public byte[] compile(String className, InputStream source)
			throws IOException, CompilationException {
		JavaFileObject javaSource = getJavaSourceFileObject(className, source);
		Iterable<? extends JavaFileObject> fileObjects = Arrays
				.asList(javaSource);
		final Writer sw = new StringWriter();
		DiagnosticListener<JavaFileObject> compilerOutputListener = jfo -> {
			try {
				sw.write("Compilation problem. Line in .java file: " + jfo.getLineNumber() + ", error: " 
						+ jfo.getMessage(Locale.ENGLISH) + "\n");
			} catch (IOException e) {
				logger.error("Compilation problem: ", e);
			}
		};
		StringWriter swe = new StringWriter();
		List<String> options = new ArrayList<>();
		options.add("-nowarn");
		options.add("-target");
		options.add("1.8");
		 
		CompilationTask task = compiler.getTask(swe, customJavaFileManager,
				compilerOutputListener, options, null,
				fileObjects);
		boolean success = task.call();
		if(!success) {
			throw new CompilationException(sw.toString()+ "\n" + swe.toString());
		}
		
		CustomJavaFileObject jfo = (CustomJavaFileObject) customJavaFileManager
				.getJavaFileForInput(StandardLocation.CLASS_OUTPUT, className,
						Kind.CLASS);
		if (jfo == null) {
			logger.error("Compilation failed: {} \n {}", sw, swe);
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOUtils.copy(jfo.openInputStream(), baos);

		return baos.toByteArray();
	}

	private JavaFileObject getJavaSourceFileObject(String className,
			InputStream contents) throws IOException {
		JavaFileObject so = null;
		className = className.replace("\\", "/");
		so = new CustomJavaFileObject(className + Kind.SOURCE.extension,
				URI.create("file:///" + className.replace('.', '/')
						+ Kind.SOURCE.extension), contents, Kind.SOURCE);
		return so;
	}
}
