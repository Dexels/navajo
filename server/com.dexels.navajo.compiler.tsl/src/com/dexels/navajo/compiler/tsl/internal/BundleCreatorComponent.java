package com.dexels.navajo.compiler.tsl.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.BundleCreator;
import com.dexels.navajo.compiler.JavaCompiler;
import com.dexels.navajo.compiler.ScriptCompiler;
import com.dexels.navajo.server.NavajoIOConfig;

public class BundleCreatorComponent implements BundleCreator {

	private NavajoIOConfig navajoIOConfig = null;
	private EventAdmin eventAdmin = null;
	private final static Logger logger = LoggerFactory.getLogger(BundleCreatorComponent.class);

	private ScriptCompiler scriptCompiler;
	private JavaCompiler javaCompiler;
	/* (non-Javadoc)
	 * @see com.dexels.navajo.compiler.tsl.ScriptCompiler#compileTsl(java.lang.String)
	 */
	private BundleContext bundleContext;

	
	public void setScriptCompiler(ScriptCompiler scriptCompiler) {
		this.scriptCompiler = scriptCompiler;
	}

	public void setJavaCompiler(JavaCompiler javaCompiler) {
		this.javaCompiler = javaCompiler;
	}

	
	public void clearScriptCompiler(ScriptCompiler scriptCompiler) {
		this.scriptCompiler = null;
	}

	public void clearJavaCompiler(JavaCompiler javaCompiler) {
		this.javaCompiler = null;
	}

	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}

	public void clearEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = null;
	}

	
	@Override
	public void createBundle(String script, String compileDate, String extension, List<String> failures, List<String> success) throws Exception {
		File scriptFolder = new File(navajoIOConfig.getScriptPath());
		File f = new File(scriptFolder,script);

		if(f.isDirectory()) {
			compileAllIn(f,compileDate, failures,  success);
		} else {
			File scriptFile = new File(scriptFolder,script+"."+extension);
			if(!scriptFile.exists()) {
				logger.error("Script or folder not found: "+script+" full path: "+f.getAbsolutePath());
				return;
			}
			Date compiled = getCompiledModificationDate(script);
			Date modified = getScriptModificationDate(script);
			if(compiled!=null && compiled.after(modified)) {
				logger.debug("Skipping up-to-date script: "+scriptFile.getAbsolutePath());
			} else {
				scriptCompiler.compileTsl(script,compileDate);
				javaCompiler.compileJava(script);
				createBundleJar(script,compileDate);
			}

		}
	}
	
	
	private void compileAllIn(File baseDir, String compileDate, List<String> failures, List<String> success) throws Exception {
		final String extension = "xml";
		File scriptPath = new File(navajoIOConfig.getScriptPath());
		Iterator<File> it = FileUtils.iterateFiles(baseDir, new String[]{extension}, true);
		while (it.hasNext()) {
			File file = (File) it.next();
			String relative = getRelative(file, scriptPath);
			if(!relative.endsWith(extension)) {
				logger.warn("Odd: "+relative);
			}
//			logger.info("File: "+relative);
			String withoutEx = relative.substring(0,relative.lastIndexOf('.'));
			try {
				createBundle(withoutEx, compileDate,extension,failures,success);
				success.add(relative);
			} catch (Exception e) {
				logger.error("Error compiling script: "+relative,e);
				failures.add("Error compiling script: "+relative);
				reportCompilationError(relative, e);
			}
		}
	}
	
	@Override
	public void installBundles(String scriptPath, List<String> failures, List<String> success) throws Exception {
		final String extension = "jar";
		File outputFolder = new File(navajoIOConfig.getCompiledScriptPath());
		File f = new File(outputFolder,scriptPath);

		if(f.isDirectory()) {
			installBundles(f,failures,success);
		} else {
			File jarFile = new File(outputFolder,scriptPath+"."+extension);
			if(!jarFile.exists()) {
				logger.error("Jar not found: "+scriptPath+" full path: "+jarFile.getAbsolutePath());
				return;
			}
			installBundle(jarFile, scriptPath, failures, success);
		}

	}
	
	@Override
	public void installBundles(File baseDir, List<String> failures, List<String> success) throws Exception {
		final String extension = "jar";
		File compiledScriptPath = new File(navajoIOConfig.getCompiledScriptPath());
		Iterator<File> it = FileUtils.iterateFiles(baseDir, new String[]{extension}, true);
		while (it.hasNext()) {
			File file = (File) it.next();
			String relative = getRelative(file, compiledScriptPath);
			if(!relative.endsWith(extension)) {
				logger.warn("Odd: "+relative);
			}
//			logger.info("File: "+relative);
			String withoutEx = relative.substring(0,relative.lastIndexOf('.'));
			installBundle(file, withoutEx, failures, success);

		}
	}

	@Override
	public void installBundle(File bundleFile, String scriptPath,
			List<String> failures, List<String> success) {
		try {
			final String uri = SCRIPTPROTOCOL+scriptPath;
			Bundle previous = bundleContext.getBundle(uri);
			if(previous!=null) {
				logger.info("Skipping bundle at: "+uri+" as it is already installed. Lastmod: "+new Date(previous.getLastModified())+" status: "+previous.getState());
				return;
			}
			System.err.println("file: "+bundleFile.getName());
			FileInputStream fis = new FileInputStream(bundleFile);
			Bundle b;
			System.err.println("uri: "+uri);
			b = this.bundleContext.installBundle(uri, fis);
			b.start();
			success.add(b.getSymbolicName());
		} catch (BundleException e) {
			failures.add(e.getLocalizedMessage());
			reportInstallationError(scriptPath, e);
		} catch (FileNotFoundException e1) {
			failures.add(e1.getLocalizedMessage());
			reportInstallationError(scriptPath, e1);
		}
	}
	
	private String getRelative(File path, File base) {
		String relative = base.toURI().relativize(path.toURI()).getPath();
		return relative;
	}
	

	private void createBundleJar(String scriptPath, String compileDate) throws IOException {
		String packagePath = null;
		String script = null;
		if(scriptPath.indexOf('/')>=0) {
			packagePath = scriptPath.substring(0,scriptPath.lastIndexOf('/'));
			script = scriptPath.substring(scriptPath.lastIndexOf('/')+1);
		} else {
			packagePath ="";
			script=scriptPath;
		}
		File out = new File(navajoIOConfig.getCompiledScriptPath());
		File java = new File(out,scriptPath+".java");
		File classFile = new File(out,scriptPath+".class");
		File manifestFile = new File(out,scriptPath+".MF");
		File dsFile = new File(out,scriptPath+".xml");
		File bundleDir = new File(out,scriptPath);
		if(!bundleDir.exists()) {
			bundleDir.mkdirs();
		}
		File packagePathFile = new File(bundleDir,packagePath);
		if(!packagePathFile.exists()) {
			packagePathFile.mkdirs();
		}
		File metainf = new File(bundleDir,"META-INF");
		if(!metainf.exists()) {
			metainf.mkdirs();
		}
		File osgiinf = new File(bundleDir,"OSGI-INF");
		if(!osgiinf.exists()) {
			osgiinf.mkdirs();
		}
		File osgiinfScript = new File(osgiinf,"script.xml");
		File metainfManifest = new File(metainf,"MANIFEST.MF");
		
		File classFileInPlace = new File(packagePathFile,script+".class");
		FileUtils.copyFile(classFile, classFileInPlace);
		FileUtils.copyFile(manifestFile, metainfManifest);
		FileUtils.copyFile(dsFile, osgiinfScript);
		final File jarFile = new File(out,scriptPath+".jar");
		classFile.delete();
		manifestFile.delete();
		dsFile.delete();
		java.delete();
		//		bundleDir.delete();
		addFolderToZip(bundleDir, null,jarFile, bundleDir.getAbsolutePath()+"/");
		FileUtils.deleteDirectory(bundleDir);
	}


	
	private void addFolderToZip(File folder, ZipOutputStream zip, File zipFile, String baseName) throws IOException {
		boolean toplevel = false;
		if(zip==null) {
			zip = new ZipOutputStream(new FileOutputStream(zipFile));
			toplevel = true;
		}			
		File[] files = folder.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				addFolderToZip(file, zip, zipFile, baseName);
			} else {
				String name = file.getAbsolutePath().substring(baseName.length());
				ZipEntry zipEntry = new ZipEntry(name);
				zip.putNextEntry(zipEntry);
				IOUtils.copy(new FileInputStream(file), zip);
				zip.closeEntry();
			}
		}
		if(toplevel) {
			zip.flush();
			zip.close();
		}
	}

	public void setIOConfig(NavajoIOConfig config) {
		this.navajoIOConfig = config;
	}
	
	public void clearIOConfig(NavajoIOConfig config) {
		this.navajoIOConfig = null;
	}
	
	public void activate(ComponentContext cc) {
		logger.debug("Activating Bundle creator");
		this.bundleContext = cc.getBundleContext();
	}
	
	public void deactivate() {
		logger.debug("Deactivating Bundle creator");
		this.bundleContext = null;
	}

	@Override
	public Date getBundleInstallationDate(String scriptPath) {
		final String bundleURI = SCRIPTPROTOCOL+scriptPath;
		Bundle b = this.bundleContext.getBundle(bundleURI);
		if(b==null) {
			logger.warn("Can not determine age of bundle: "+bundleURI+" as it can not be found.");
			return null;
		}
		return new Date(b.getLastModified());
	}
	
	@Override
	public Date getScriptModificationDate(String scriptPath) {
		return navajoIOConfig.getScriptModificationDate(scriptPath);
	}
	
	@Override
	public Date getCompiledModificationDate(String scriptPath) {
		final String extension = "jar";
		File compiledScriptPath = new File(navajoIOConfig.getCompiledScriptPath());
		File jarFile = new File(compiledScriptPath,scriptPath+"."+extension);
		if(!jarFile.exists()) {
			return null;
		}
		return new Date(jarFile.lastModified());
	}

	private void reportInstallationError(String path,	Throwable e) {
		Dictionary<String,Object> params = new Hashtable<String,Object>();
		params.put("scriptPath", path);
		params.put("throwable", e);
		if(eventAdmin!=null) {
			Event ee = new Event("installation",params);
			eventAdmin.postEvent(ee);
			
		} else {
			logger.warn("No eventAdmin found to report installation error");
		}
	}

	private void reportCompilationError(String path, Throwable e) {
		Dictionary<String,Object> params = new Hashtable<String,Object>();
		params.put("scriptPath", path);
		params.put("throwable", e);
		if(eventAdmin!=null) {
			Event ee = new Event("compilation",params);
			eventAdmin.postEvent(ee);
			
		} else {
			logger.warn("No eventAdmin found to report installation error");
		}
	}

}
