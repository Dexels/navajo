package com.dexels.navajo.compiler.tsl.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.util.tracker.ServiceTracker;
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

	
	/**
	 * The script compiler to clear
	 * @param scriptCompiler  
	 */
	public void clearScriptCompiler(ScriptCompiler scriptCompiler) {
		this.scriptCompiler = null;
	}

	/**
	 * The java compiler to clear
	 * @param javaCompiler
	 */
	public void clearJavaCompiler(JavaCompiler javaCompiler) {
		this.javaCompiler = null;
	}

	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}

	/**
	 * 
	 * @param eventAdmin the eventadmin to clear
	 */
	public void clearEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = null;
	}

	
	@Override
	public String formatCompilationDate(Date d) {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String formatted = df.format(d);
		return formatted;
	}
	
	@Override
	public void createBundle(String scriptName, Date compilationDate, String scriptExtension, List<String> failures, List<String> success, List<String> skipped, boolean force, boolean keepIntermediate) throws Exception {
		
		String script = scriptName.replaceAll("\\.", "/");
		
		
		File scriptFolder = new File(navajoIOConfig.getScriptPath());
		File f = new File(scriptFolder,script);
//		boolean isInDefaultPackage = script.indexOf('/')==-1;
		final String formatCompilationDate = formatCompilationDate(compilationDate);

		if(f.isDirectory()) {
			compileAllIn(f,compilationDate, failures,  success,skipped,force,keepIntermediate);
		} else {
			File scriptFile = new File(scriptFolder,script+"."+scriptExtension);
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
				scriptCompiler.compileTsl(script,formatCompilationDate);
				javaCompiler.compileJava(script);
				javaCompiler.compileJava(script+"Factory");
				createBundleJar(script,keepIntermediate);
				success.add(script);
			}

		}
	}
	
	
	private void compileAllIn(File baseDir, Date compileDate, List<String> failures, List<String> success, List<String> skipped, boolean force, boolean keepIntermediate) throws Exception {
		final String extension = "xml";
		File scriptPath = new File(navajoIOConfig.getScriptPath());

		Iterator<File> it = FileUtils.iterateFiles(baseDir, new String[]{extension}, true);
		while (it.hasNext()) {
			File file = it.next();
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
//		final String extension = "jar";
		File outputFolder = new File(navajoIOConfig.getCompiledScriptPath());
		File f = new File(outputFolder,scriptPath);
		File jarFile = getScriptBundleJar(scriptPath);
//		new File(outputFolder,scriptPath+"."+extension);
		
		if(jarFile.exists()) {
			installBundle(scriptPath, failures, success,skipped, force);
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
			File file = it.next();
			String relative = getRelative(file, compiledScriptPath);
			if(!relative.endsWith(extension)) {
				logger.warn("Odd: "+relative);
			}
//			logger.info("File: "+relative);
			String withoutEx = relative.substring(0,relative.lastIndexOf('.'));
			installBundle(withoutEx, failures, success,skipped, force);

		}
	}

	@Override
	public void installBundle( String scriptPath,
			List<String> failures, List<String> success, List<String> skipped, boolean force) {
		try {
			Bundle b = doInstall(scriptPath, force);
			if(b==null) {
				skipped.add(scriptPath);
			} else {
				success.add(b.getSymbolicName());
			}
		} catch (BundleException e) {
			failures.add(e.getLocalizedMessage());
			reportInstallationError(scriptPath, e);
		} catch (FileNotFoundException e1) {
			failures.add(e1.getLocalizedMessage());
			reportInstallationError(scriptPath, e1);
		}
	}

	private Bundle doInstall(String scriptPath, boolean force) throws BundleException,FileNotFoundException {
		File bundleFile = getScriptBundleJar(scriptPath);
		final String uri = SCRIPTPROTOCOL+scriptPath;
		Bundle previous = bundleContext.getBundle(uri);
		if(previous!=null) {
			if (force) {
				previous.uninstall();
				logger.debug("uninstalling bundle with URI: "+uri);
			} else {
				logger.info("Skipping bundle at: "+uri+" as it is already installed. Lastmod: "+new Date(previous.getLastModified())+" status: "+previous.getState());
				return null;
			}
		}
		logger.info("Installing script: "+bundleFile.getName());
		FileInputStream fis = new FileInputStream(bundleFile);
		Bundle b;
		b = this.bundleContext.installBundle(uri, fis);
		b.start();
		return b;
	}
	
	private String getRelative(File path, File base) {
		String relative = base.toURI().relativize(path.toURI()).getPath();
		return relative;
	}
	

	private File createBundleJar(String scriptPath,boolean keepIntermediateFiles) throws IOException {
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
		return jarFile;
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
	
	/**
	 * 
	 * @param config the navajoioconfig to clear
	 */
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
		String scrpt = scriptPath.replaceAll("/", ".");
		final String bundleURI = SCRIPTPROTOCOL+scrpt;
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
	public Date getCompiledModificationDate(String script) {
		String scriptPath = script.replaceAll("\\.", "/");
		File jarFile = getScriptBundleJar(scriptPath);
		if(!jarFile.exists()) {
			return null;
		}
		return new Date(jarFile.lastModified());
	}

	private File getScriptBundleJar(String script) {
		String scriptPath = script.replaceAll("\\.", "/");
		final String extension = "jar";
		File compiledScriptPath = new File(navajoIOConfig.getCompiledScriptPath());
		File jarFile = new File(compiledScriptPath,scriptPath+"."+extension);
		return jarFile;
	}
	
	private boolean needsCompilation(String scriptPath) throws FileNotFoundException {
		Date compiled = getCompiledModificationDate(scriptPath);
		Date script = getScriptModificationDate(scriptPath);
		if(script==null) {
			throw new FileNotFoundException("Script "+scriptPath+" is missing!");
		}
		if(compiled==null) {
			return true;
		}
		return compiled.compareTo(script) < 0;
		
		
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
	
	@Override
	public CompiledScript getOnDemandScriptService(String rpcName) throws Exception {
		CompiledScript sc = getCompiledScript(rpcName);
		
		if(sc!=null) {
			boolean needsRecompile = checkForRecompile(rpcName);
			if(!needsRecompile) {
				return sc;
			}
		}
		List<String> failures = new ArrayList<String>();
		List<String> success = new ArrayList<String>();
		List<String> skipped = new ArrayList<String>();
		boolean force = false;
		// so no resolution
		if(needsCompilation(rpcName)) {
			createBundle(rpcName, new Date(), "xml", failures, success, skipped, false, false);
			force = true;
//			createBundleJar(rpcName, formatCompilationDate(new Date()), false);
		}
		
		
//		File bundleJar = getScriptBundleJar(rpcName);
		installBundle(rpcName, failures, success, skipped, force);
		
		logger.info("On demand installation finished, waiting for service...");
		CompiledScript cs = waitForService(rpcName);
		
		return cs;
		//		
//		List<String> failures = new ArrayList<String>();
//		List<String> success = new ArrayList<String>();
//		List<String> skipped = new ArrayList<String>();
//		bundleContext.installBundles(rpcName, failures, success, skipped, false);
//		
	}

	private boolean checkForRecompile(String rpcName) {
		Date mod = getScriptModificationDate(rpcName);
		if(mod==null) {
			logger.error("No modification date for script: "+rpcName+" this is weird.");
			return false;
		}
		Date install = getBundleInstallationDate(rpcName);
		if(install!=null) {
			if(install.before(mod)) {
				logger.debug("Install: "+install);
				logger.debug("mod: "+mod);
				logger.debug("comp: ", getCompiledModificationDate(rpcName));
				logger.debug("Obsolete script found. Needs recompile.");
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	
	public CompiledScript getCompiledScript(String rpcName)
			throws ClassNotFoundException {
		String filter = "(navajo.scriptName="+rpcName+")";
		
		ServiceReference[] sr;
			try {
				sr = bundleContext.getServiceReferences(CompiledScriptFactory.class.getName(), filter);
			if(sr!=null && sr.length!=0) {
				CompiledScriptFactory csf = bundleContext.getService(sr[0]);
				if(csf==null) {
					logger.warn("Script with filter: "+filter+" found, but could not be resolved.");
					return null;
				}
				CompiledScript cs = csf.getCompiledScript();
				return cs;
			}
		} catch (InvalidSyntaxException e) {
			throw new ClassNotFoundException("Error resolving script service for: "+rpcName,e);
		} catch (InstantiationException e) {
			throw new ClassNotFoundException("Error resolving script service for: "+rpcName,e);
		} catch (IllegalAccessException e) {
			throw new ClassNotFoundException("Error resolving script service for: "+rpcName,e);
		}
	
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private CompiledScript waitForService(String rpcPath) throws Exception {
		String rpcName = rpcPath.replaceAll("/", ".");
		String filterString = "(navajo.scriptName="+rpcName+")";
		logger.info("waiting for service...: "+rpcName);
		Filter filter = bundleContext.createFilter(filterString);
		ServiceTracker tr = new ServiceTracker(bundleContext,filter,null);
//		ServiceReference<CompiledScriptFactory>[] ss = (ServiceReference<CompiledScriptFactory>[]) bundleContext.getServiceReferences(CompiledScriptFactory.class.getName(), filterString);
//		if(ss!=null && ss.length>0) {
//			logger.info("Service present: "+ss.length);
//		} else {
//			logger.info("Service missing");
//		}
		tr.open();
		CompiledScriptFactory result = (CompiledScriptFactory) tr.waitForService(12000);
		if(result==null) {
			logger.error("Service resolution failed!");
		}
		CompiledScript cc = result.getCompiledScript();
		tr.close();
		return cc;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void verifySingleScript(String scriptPath, List<String> failed,
			List<String> success) {
		scriptPath = scriptPath.replace('/', '.');
		String filter = "(navajo.scriptName="+scriptPath+")";
		try {
			ServiceReference[] ssr = bundleContext.getServiceReferences(CompiledScriptFactory.class.getName(), filter);
//			
//			Collection<ServiceReference<CompiledScriptFactory>> sr = bundleContext.getServiceReferences(CompiledScriptFactory.class, filter);
			if(ssr.length==0) {
				logger.warn("Can not locate service for script: "+scriptPath+" filter: "+filter);
				failed.add(scriptPath);
				return;
			}
			if(ssr.length>1) {
				logger.warn("MULTIPLE scripts with the same name detected, marking as failed. Filter: "+scriptPath);
				return;
			}
			logger.info("# of services: "+ssr.length);
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
			File file = it.next();
			String relative = getRelative(file, compiledScriptPath);
			if(!relative.endsWith(extension)) {
				logger.warn("Odd: "+relative);
			}
			String withoutEx = relative.substring(0,relative.lastIndexOf('.'));
			verifySingleScript(withoutEx, failed,success);

		}
	}

}
