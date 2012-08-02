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
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.BundleCreator;
import com.dexels.navajo.compiler.JavaCompiler;
import com.dexels.navajo.compiler.ScriptCompiler;
import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.server.CompiledScriptFactory;
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
	public void createBundle(String script, String compileDate, String extension, List<String> failures, List<String> success, List<String> skipped, boolean force, boolean keepIntermediate) throws Exception {
		File scriptFolder = new File(navajoIOConfig.getScriptPath());
		File f = new File(scriptFolder,script);
//		boolean isInDefaultPackage = script.indexOf('/')==-1;
		if(f.isDirectory()) {
			compileAllIn(f,compileDate, failures,  success,skipped,force,keepIntermediate);
		} else {
			File scriptFile = new File(scriptFolder,script+"."+extension);
			if(!scriptFile.exists()) {
				logger.error("Script or folder not found: "+script+" full path: "+scriptFile.getAbsolutePath());
				return;
			}
			Date compiled = getCompiledModificationDate(script);
			Date modified = getScriptModificationDate(script);
			if(!force && compiled!=null && compiled.after(modified)) {
//				logger.debug("Skipping up-to-date script: "+scriptFile.getAbsolutePath());
				skipped.add(script);
			} else {
				scriptCompiler.compileTsl(script,compileDate);
				javaCompiler.compileJava(script);
				javaCompiler.compileJava(script+"Factory");
				createBundleJar(script,compileDate,keepIntermediate);
				success.add(script);
			}

		}
	}
	
	
	private void compileAllIn(File baseDir, String compileDate, List<String> failures, List<String> success, List<String> skipped, boolean force, boolean keepIntermediate) throws Exception {
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
				createBundle(withoutEx, compileDate,extension,failures,success,skipped, force,keepIntermediate);
			} catch (Exception e) {
				logger.error("Error compiling script: "+relative,e);
				failures.add("Error compiling script: "+relative);
				reportCompilationError(relative, e);
			}
		}
	}
	
	@Override
	public void installBundles(String scriptPath, List<String> failures, List<String> success, List<String> skipped, boolean force) throws Exception {
		final String extension = "jar";
		File outputFolder = new File(navajoIOConfig.getCompiledScriptPath());
		File f = new File(outputFolder,scriptPath);
		File jarFile = new File(outputFolder,scriptPath+"."+extension);
		
		if(jarFile.exists()) {
			installBundle(jarFile, scriptPath, failures, success,skipped, force);
		} else {
			if(f.isDirectory()) {
				installBundles(f,failures,success,skipped, force);
			} else {
				logger.error("Jar not found: "+scriptPath+" full path: "+jarFile.getAbsolutePath());
				return;
			}
		}

	}
	
	private void installBundles(File baseDir, List<String> failures, List<String> success, List<String> skipped, boolean force) throws Exception {
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
			installBundle(file, withoutEx, failures, success,skipped, force);

		}
	}

	@Override
	public void installBundle(File bundleFile, String scriptPath,
			List<String> failures, List<String> success, List<String> skipped, boolean force) {
		try {
			final String uri = SCRIPTPROTOCOL+scriptPath;
			Bundle previous = bundleContext.getBundle(uri);
			if(previous!=null) {
				if (force) {
					previous.uninstall();
				} else {
					logger.info("Skipping bundle at: "+uri+" as it is already installed. Lastmod: "+new Date(previous.getLastModified())+" status: "+previous.getState());
					skipped.add(scriptPath);
					return;
				}
			}
//			System.err.println("file: "+bundleFile.getName());
			logger.info("Installing script: "+bundleFile.getName());
			FileInputStream fis = new FileInputStream(bundleFile);
			Bundle b;
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
	

	private void createBundleJar(String scriptPath, String compileDate, boolean keepIntermediateFiles) throws IOException {
		String packagePath = null;
		String script = null;
		if(scriptPath.indexOf('/')>=0) {
			packagePath = scriptPath.substring(0,scriptPath.lastIndexOf('/'));
			script = scriptPath.substring(scriptPath.lastIndexOf('/')+1);
		} else {
			packagePath ="";
			script=scriptPath;
		}
		String fixOffset = packagePath.equals("")?"defaultPackage":"";
		File outB = new File(navajoIOConfig.getCompiledScriptPath());
		File out = new File(outB,fixOffset);
		
		File java = new File(outB,scriptPath+".java");
		File factoryJavaFile = new File(outB,scriptPath+"Factory.java");
		File classFile = new File(out,scriptPath+".class");
		File factoryClassFile = new File(out,scriptPath+"Factory.class");
		File manifestFile = new File(outB,scriptPath+".MF");
		File dsFile = new File(outB,scriptPath+".xml");
		File bundleDir = new File(outB,scriptPath);
		if(!bundleDir.exists()) {
			bundleDir.mkdirs();
		}
		File bundlePackageFix = new File(bundleDir,fixOffset);
		File bundlePackageDir = new File(bundlePackageFix,packagePath);
		if(!bundlePackageDir.exists()) {
			bundlePackageDir.mkdirs();
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
		
		File classFileInPlace = new File(bundlePackageDir,script+".class");
		File factoryClassFileInPlace = new File(bundlePackageDir,script+"Factory.class");
		FileUtils.copyFile(classFile, classFileInPlace);
		FileUtils.copyFile(factoryClassFile, factoryClassFileInPlace);
		FileUtils.copyFile(manifestFile, metainfManifest);
		FileUtils.copyFile(dsFile, osgiinfScript);
		final File jarFile = new File(outB,scriptPath+".jar");
		addFolderToZip(bundleDir, null,jarFile, bundleDir.getAbsolutePath()+"/");
		if(!keepIntermediateFiles) {
			classFile.delete();
			manifestFile.delete();
			dsFile.delete();
			java.delete();
			factoryJavaFile.delete();
			factoryClassFile.delete();
			FileUtils.deleteDirectory(bundleDir);
		}
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

	@Override
	public void verifyScript(String scriptPath, List<String> failed,
			List<String> success) {
		File outputFolder = new File(navajoIOConfig.getScriptPath());
		File f = new File(outputFolder,scriptPath);

		if(f.isDirectory()) {
			verifyScripts(f,failed,success);
		} else {
			verifySingleScript(scriptPath,failed,success);
		}
		
	}

	private void verifySingleScript(String scriptPath, List<String> failed,
			List<String> success) {
		scriptPath = scriptPath.replace('/', '.');
		String filter = "(navajo.scriptName="+scriptPath+")";
		try {
			ServiceReference[] ssr = bundleContext.getServiceReferences(CompiledScriptFactory.class.getName(), filter);
//			
//			Collection<ServiceReference<CompiledScriptFactory>> sr = bundleContext.getServiceReferences(CompiledScriptFactory.class, filter);
//			if(sr.isEmpty()) {
//				logger.warn("Can not locate service for script: "+scriptPath+" filter: "+filter);
//				failed.add(scriptPath);
//				return;
//			}
//			if(sr.size()>1) {
//				logger.warn("MULTIPLE scripts with the same name detected, marking as failed. Filter: "+scriptPath);
//				return;
//			}
//			logger.info("# of services: "+sr.size());
//			final ServiceReference<CompiledScriptFactory> ref = sr.iterator().next();
			


			CompiledScriptFactory csf = bundleContext.getService(ssr[0]);
			if(csf==null) {
				logger.warn("Script with filter: "+filter+" found, but could not be resolved.");
				return;
			}
			CompiledScript cs = csf.getCompiledScript();
			logger.debug("Compiled script seems ok. "+cs.toString());
			bundleContext.ungetService(ssr[0]);
			success.add(scriptPath);
		} catch (InvalidSyntaxException e) {
			logger.error("Error parsing filter: "+filter,e);
			failed.add(scriptPath);
		} catch (Exception e) {
			logger.error("Error instantiating script: "+scriptPath+" instantiation seems to have failed.",e);
			failed.add(scriptPath);
		}
		
	}

	private void verifyScripts(File baseDir, List<String> failed, List<String> success) {
		final String extension = "xml";
		File compiledScriptPath = new File(navajoIOConfig.getScriptPath());
		Iterator<File> it = FileUtils.iterateFiles(baseDir, new String[]{extension}, true);
		while (it.hasNext()) {
			File file = (File) it.next();
			String relative = getRelative(file, compiledScriptPath);
			if(!relative.endsWith(extension)) {
				logger.warn("Odd: "+relative);
			}
//			logger.info("File: "+relative);
			String withoutEx = relative.substring(0,relative.lastIndexOf('.'));
			verifySingleScript(withoutEx, failed,success);

		}
	}

}
