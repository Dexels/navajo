package com.dexels.navajo.compiler.tsl.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.BundleCreator;
import com.dexels.navajo.compiler.JavaCompiler;
import com.dexels.navajo.compiler.ScriptCompiler;
import com.dexels.navajo.mapping.compiler.SkipCompilationException;
import com.dexels.navajo.script.api.CompiledScriptFactory;
import com.dexels.navajo.script.api.CompiledScriptInterface;
import com.dexels.navajo.script.api.Dependency;
import com.dexels.navajo.server.NavajoIOConfig;

public class BundleCreatorComponent implements BundleCreator {

	private NavajoIOConfig navajoIOConfig = null;
	private EventAdmin eventAdmin = null;
	private final static Logger logger = LoggerFactory
			.getLogger(BundleCreatorComponent.class);

	private ScriptCompiler scriptCompiler;
	private JavaCompiler javaCompiler;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dexels.navajo.compiler.tsl.ScriptCompiler#compileTsl(java.lang.String
	 * )
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
	 * 
	 * @param scriptCompiler
	 */
	public void clearScriptCompiler(ScriptCompiler scriptCompiler) {
		this.scriptCompiler = null;
	}

	/**
	 * The java compiler to clear
	 * 
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
	 * @param eventAdmin
	 *            the eventadmin to clear
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
	public void createBundle(String scriptName, Date compilationDate,
			String scriptExtension, List<String> failures,
			List<String> success, List<String> skipped, boolean force,
			boolean keepIntermediate, String tenant) throws Exception {
		// TODO does scriptExtension always include the dot?
		if(!scriptExtension.startsWith(".")) {
			throw new IllegalAccessError("SCript extension did not start with a dot!");
		}
		String script = scriptName.replaceAll("\\.", "/");

		File scriptFolder = new File(navajoIOConfig.getScriptPath());
		File f = new File(scriptFolder, script);
		// boolean isInDefaultPackage = script.indexOf('/')==-1;
		final String formatCompilationDate = formatCompilationDate(compilationDate);
		if (isDirectory(script, scriptFolder, f) ) {
			compileAllIn(f, compilationDate, failures, success, skipped, force,
					keepIntermediate,tenant);
		} else {
			File scriptFile = navajoIOConfig.getApplicableScriptFile(script, tenant,scriptExtension);
			boolean hasTenantSpecificFile = navajoIOConfig.hasTenantScriptFile(script, tenant,scriptExtension);
//			File scriptFile = new File(scriptFolder, script + "."
//					+ scriptExtension);
			if (!scriptFile.exists()) {
				logger.error("Script or folder not found: " + script
						+ " full path: " + scriptFile.getAbsolutePath());
				return;
			}
			Date compiled = getCompiledModificationDate(script,tenant,scriptExtension);
			Date modified = getScriptModificationDate(script,tenant,scriptExtension);
			if (!force && compiled != null && compiled.after(modified)) {
				// logger.debug("Skipping up-to-date script: "+scriptFile.getAbsolutePath());
				skipped.add(script);
			} else {
				try {
					List<Dependency> dependencies = new ArrayList<Dependency>();
					scriptCompiler.compileTsl(script, formatCompilationDate,
					dependencies, tenant,hasTenantSpecificFile);
					javaCompiler.compileJava(script);
					javaCompiler.compileJava(script + "Factory");
					createBundleJar(script, tenant,keepIntermediate,hasTenantSpecificFile,scriptExtension);
					success.add(script);
				} catch (SkipCompilationException e) {
					logger.debug("Script fragment: {} ignored.",script);
					skipped.add(script);
				}
			}

		}
	}

	private boolean isDirectory(String script, File scriptFolder, File f)
			throws IOException {
		if("".equals(script)) {
			return true;
		}
		final boolean equalsCanonical = f.getCanonicalPath().equals(scriptFolder.getCanonicalFile() + "/" + script);
		final boolean isDir = f.isDirectory();
		return isDir && equalsCanonical;
	}

	private void compileAllIn(File baseDir, Date compileDate,
			List<String> failures, List<String> success, List<String> skipped,
			boolean force, boolean keepIntermediate, String tenant) throws Exception {
		final String extension = "xml";
		File scriptPath = new File(navajoIOConfig.getScriptPath());

		Iterator<File> it = FileUtils.iterateFiles(baseDir,
				new String[] { extension }, true);
		while (it.hasNext()) {
			File file = it.next();
			String relative = getRelative(file, scriptPath);
			if (!relative.endsWith(extension)) {
				logger.warn("Odd: " + relative);
			}
			// logger.info("File: "+relative);
			String withoutEx = relative.substring(0, relative.lastIndexOf('.'));
			try {
				createBundle(withoutEx, compileDate, extension, failures,
						success, skipped, force, keepIntermediate,tenant);
			} catch (Exception e) {
				logger.error("Error compiling script: " + relative, e);
				failures.add("Error compiling script: " + relative);
				reportCompilationError(relative, e);
			}
		}
	}

	@Override
	public void installBundles(String scriptPath,String tenant, List<String> failures,
			List<String> success, List<String> skipped, boolean force, String extension)
			throws Exception {
		// final String extension = "jar";
		File outputFolder = new File(navajoIOConfig.getCompiledScriptPath());
		File f = new File(outputFolder, scriptPath);
		File jarFile = getScriptBundleJar(scriptPath,tenant,".xml");
		// new File(outputFolder,scriptPath+"."+extension);

		if (jarFile!=null && jarFile.exists()) {
			installBundle(scriptPath,tenant, failures, success, skipped, force,extension);
		} else {
			if (f.isDirectory()) {
				installBundles(tenant,f, failures, success, skipped, force,extension);
			} else {
				logger.error("Jar not found: " + scriptPath + " full path: "
						+ jarFile);
				return;
			}
		}

	}

	private void installBundles(String tenant, File baseDir, List<String> failures,
			List<String> success, List<String> skipped, boolean force,String scriptExtension)
			throws Exception {
		final String extension = "jar";
		File compiledScriptPath = new File(
				navajoIOConfig.getCompiledScriptPath());
		Iterator<File> it = FileUtils.iterateFiles(baseDir,
				new String[] { extension }, true);
		while (it.hasNext()) {
			File file = it.next();
			String relative = getRelative(file, compiledScriptPath);
			if (!relative.endsWith(extension)) {
				logger.warn("Odd: " + relative);
			}
			// logger.info("File: "+relative);
			String withoutEx = relative.substring(0, relative.lastIndexOf('.'));
			installBundle(withoutEx, tenant,failures, success, skipped, force,scriptExtension);

		}
	}

	@Override
	public void installBundle(String scriptPath, String tenant, List<String> failures,
			List<String> success, List<String> skipped, boolean force,String extension) {
		try {
			Bundle b = doInstall(scriptPath,tenant, force,extension);
			if (b == null) {
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
		} catch (MalformedURLException e) {
			failures.add(e.getLocalizedMessage());
			reportInstallationError(scriptPath, e);
		}
	}

	private Bundle doInstall(String scriptPath, String tenant, boolean force,String extension)
			throws BundleException, FileNotFoundException, MalformedURLException {
		File bundleFile = getScriptBundleJar(scriptPath,tenant,extension);
		final String uri = bundleFile.toURI().toURL().toString(); // SCRIPTPROTOCOL + scriptPath;
		Bundle previous = bundleContext.getBundle(uri);
		if (previous != null) {
			if (force) {
				previous.uninstall();
				logger.debug("uninstalling bundle with URI: " + uri);
			} else {
				logger.debug("Skipping bundle at: " + uri
						+ " as it is already installed. Lastmod: "
						+ new Date(previous.getLastModified()) + " status: "
						+ previous.getState());
				return null;
			}
		}
		logger.debug("Installing script: " + bundleFile.getName());
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

	private File createBundleJar(String scriptPath, String tenant,
			boolean keepIntermediateFiles, boolean useTenantSpecificFile, String extension) throws IOException {
		String packagePath = null;
		String script = null;
		if (scriptPath.indexOf('/') >= 0) {
			packagePath = scriptPath.substring(0, scriptPath.lastIndexOf('/'));
			script = scriptPath.substring(scriptPath.lastIndexOf('/') + 1);
		} else {
			packagePath = "";
			script = scriptPath;
		}
		String fixOffset = packagePath.equals("") ? "defaultPackage" : "";
		File outB = new File(navajoIOConfig.getCompiledScriptPath());
		File out = new File(outB, fixOffset);

		File java = new File(outB, scriptPath + ".java");
		File factoryJavaFile = new File(outB, scriptPath + "Factory.java");
		File classFile = new File(out, scriptPath + ".class");
		File factoryClassFile = new File(out, scriptPath + "Factory.class");
		File manifestFile = new File(outB, scriptPath + ".MF");
		File dsFile = new File(outB, scriptPath + ".xml");
		File bundleDir = new File(outB, scriptPath);
		if (!bundleDir.exists()) {
			bundleDir.mkdirs();
		}
		File bundlePackageFix = new File(bundleDir, fixOffset);
		File bundlePackageDir = new File(bundlePackageFix, packagePath);
		if (!bundlePackageDir.exists()) {
			bundlePackageDir.mkdirs();
		}
		File packagePathFile = new File(bundleDir, packagePath);
		if (!packagePathFile.exists()) {
			packagePathFile.mkdirs();
		}
		File metainf = new File(bundleDir, "META-INF");
		if (!metainf.exists()) {
			metainf.mkdirs();
		}
		File osgiinf = new File(bundleDir, "OSGI-INF");
		if (!osgiinf.exists()) {
			osgiinf.mkdirs();
		}
		File osgiinfScript = new File(osgiinf, "script.xml");
		File metainfManifest = new File(metainf, "MANIFEST.MF");

		File classFileInPlace = new File(bundlePackageDir, script + ".class");
		File factoryClassFileInPlace = new File(bundlePackageDir, script
				+ "Factory.class");
//		File parent = classFile.getParentFile();
//		if(!parent.exists()) {
//			parent.mkdirs();
//		}
		FileUtils.copyFile(classFile, classFileInPlace);
		FileUtils.copyFile(factoryClassFile, factoryClassFileInPlace);
		FileUtils.copyFile(manifestFile, metainfManifest);
		FileUtils.copyFile(dsFile, osgiinfScript);
//		final File jarFile = new File(outB, scriptPath + ".jar");
		final File jarFile = navajoIOConfig.getApplicableBundleForScript(scriptPath, tenant,extension);
		
		
		addFolderToJar(bundleDir, null, jarFile, bundleDir.getAbsolutePath()+ "/");
		if (!keepIntermediateFiles) {
			classFile.delete();
			manifestFile.delete();
			dsFile.delete();
			java.delete();
			factoryJavaFile.delete();
			factoryClassFile.delete();
			FileUtils.deleteQuietly(bundleDir);
		}
		return jarFile;
	}

	private void addFolderToJar(File folder,
			ZipArchiveOutputStream jarOutputStream, File jarFile,
			String baseName) throws IOException {
		boolean toplevel = false;
		if (jarOutputStream == null) {
			jarOutputStream = new ZipArchiveOutputStream(jarFile);
			toplevel = true;
		}
		File[] files = folder.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				addFolderToJar(file, jarOutputStream, jarFile, baseName);
			} else {
				String name = file.getAbsolutePath().substring(
						baseName.length());
				ZipArchiveEntry zipEntry = new ZipArchiveEntry(name);
				jarOutputStream.putArchiveEntry(zipEntry);
				FileInputStream input = new FileInputStream(file);
				IOUtils.copy(input, jarOutputStream);
				input.close();
				jarOutputStream.closeArchiveEntry();
			}
		}
		if (toplevel) {
			jarOutputStream.flush();
			jarOutputStream.close();
		}
	}

	public void setIOConfig(NavajoIOConfig config) {
		this.navajoIOConfig = config;
	}

	/**
	 * 
	 * @param config
	 *            the navajoioconfig to clear
	 */
	public void clearIOConfig(NavajoIOConfig config) {
		this.navajoIOConfig = null;
	}

	public void activate(BundleContext bundleContext) {
		logger.debug("Activating Bundle creator");
		this.bundleContext = bundleContext;

	}

	public void deactivate() {
		logger.debug("Deactivating Bundle creator");
		this.bundleContext = null;
	}

	@Override
	public Date getBundleInstallationDate(String scriptPath,String tenant, String extension) {
		File bundleFile = navajoIOConfig.getApplicableBundleForScript(scriptPath, tenant, extension);
		URL u;
		try {
			u = bundleFile.toURI().toURL();
			final String bundleURI = u.toString();
			
			Bundle b = this.bundleContext.getBundle(bundleURI);
			if (b == null) {
				logger.warn("Can not determine age of bundle: " + bundleURI
						+ " as it can not be found.");
				return null;
			}
			return new Date(b.getLastModified());
		} catch (MalformedURLException e) {
			logger.error("Bad bundle URI for file: "+bundleFile.toString(),e);
		}
		return null;
	}

	@Override
	public Date getScriptModificationDate(String scriptPath, String tenant,String extension) throws FileNotFoundException {
		return navajoIOConfig.getScriptModificationDate(scriptPath, tenant,extension);
	}

	
	@Override
	public Date getCompiledModificationDate(String scriptPath, String tenant,String extension) {
		String scriptName = scriptPath.replaceAll("\\.", "/");
		File jarFile = navajoIOConfig.getApplicableBundleForScript(scriptName,tenant,extension);
//		public File getApplicableBundleForScript(String rpcName, String tenant) throws FileNotFoundException;

		if (jarFile==null || !jarFile.exists()) {
			return null;
		}
		return new Date(jarFile.lastModified());
	}

	private File getScriptBundleJar(String script, String tenant,String extension) {
		return navajoIOConfig.getApplicableBundleForScript(script, tenant,extension);
	}

	private boolean checkForRecompile(String rpcName, String tenant, boolean tenantQualified,String extension) throws FileNotFoundException {
		Date mod = getScriptModificationDate(rpcName,tenant,extension);
		if (mod == null) {
			logger.error("No modification date for script: " + rpcName
					+ " this is weird.");
			return false;
		}
		Date install = getBundleInstallationDate(rpcName,tenant,extension);
		if (install != null) {
			if (install.before(mod)) {
				logger.debug("Install: " + install);
				logger.debug("mod: " + mod);
				logger.debug("comp: ", getCompiledModificationDate(rpcName,tenant,extension));
				logger.debug("Obsolete script found. Needs recompile.");
				return true;
			}
		}
		return false;
	}

	private boolean needsCompilation(String scriptPath,String tenant,String extension)
			throws FileNotFoundException {
		Date compiled = getCompiledModificationDate(scriptPath,tenant,extension);
		Date script = getScriptModificationDate(scriptPath,tenant,extension);
		if (script == null) {
			throw new FileNotFoundException("Script " + scriptPath
					+ " is missing!");
		}
		if (compiled == null) {
			return true;
		}
		return compiled.compareTo(script) < 0;

	}

	private void reportInstallationError(String path, Throwable e) {
		Dictionary<String, Object> params = new Hashtable<String, Object>();
		params.put("scriptPath", path);
		params.put("throwable", e);
		if (eventAdmin != null) {
			Event ee = new Event("installation", params);
			eventAdmin.postEvent(ee);

		} else {
			logger.warn("No eventAdmin found to report installation error");
		}
	}

	private void reportCompilationError(String path, Throwable e) {
		Dictionary<String, Object> params = new Hashtable<String, Object>();
		params.put("scriptPath", path);
		params.put("throwable", e);
		if (eventAdmin != null) {
			Event ee = new Event("compilation", params);
			eventAdmin.postEvent(ee);

		} else {
			logger.warn("No eventAdmin found to report installation error");
		}
	}

	@Override
	public void verifyScript(String scriptPath, List<String> failed,
			List<String> success) {
		File outputFolder = new File(navajoIOConfig.getScriptPath());
		File f = new File(outputFolder, scriptPath);

		if (f.isDirectory()) {
			verifyScripts(f, failed, success);
		} else {
			verifySingleScript(scriptPath, failed, success);
		}

	}

	@Override
	public CompiledScriptInterface getOnDemandScriptService(String rpcName, String tenant, boolean tenantQualified, boolean force, String extension)
			throws Exception {
		CompiledScriptInterface sc = getCompiledScript(rpcName,tenant,extension);

		boolean forceReinstall = false;
		if (sc != null) {
			boolean needsRecompile = checkForRecompile(rpcName,tenant,tenantQualified,extension);
			if (!force && !needsRecompile) {
				return sc;
			}
		}
		List<String> failures = new ArrayList<String>();
		List<String> success = new ArrayList<String>();
		List<String> skipped = new ArrayList<String>();
		// so no resolution
		if (needsCompilation(rpcName,tenant,extension) || force) {
			createBundle(rpcName, new Date(), ".xml", failures, success,
					skipped, force, false,tenant);
			forceReinstall = true;
			// createBundleJar(rpcName, formatCompilationDate(new Date()),
			// false);
		}

		// File bundleJar = getScriptBundleJar(rpcName);
		installBundle(rpcName, tenant,failures, success, skipped, forceReinstall,extension);

		logger.debug("On demand installation finished, waiting for service...");
//		CompiledScript cs = waitForService(rpcName,tenant);
		return getCompiledScript(rpcName, tenant,extension);

//	/	return cs;
		//
		// List<String> failures = new ArrayList<String>();
		// List<String> success = new ArrayList<String>();
		// List<String> skipped = new ArrayList<String>();
		// bundleContext.installBundles(rpcName, failures, success, skipped,
		// false);
		//
	}


	@Override
	public CompiledScriptInterface getCompiledScript(String rpcName, String tenant,String extension)
			throws ClassNotFoundException {
		String scriptName = rpcName.replaceAll("/", ".");
		String filter = null;
//		boolean tenantQualified;
		
		if(navajoIOConfig.hasTenantScriptFile(rpcName, tenant,extension)) {
//			tenantQualified = true;
			filter = "(&(navajo.scriptName=" + scriptName + ")(navajo.tenant="+tenant+"))";
		} else {
//			tenantQualified = false;
			filter = "(&(navajo.scriptName=" + scriptName + ")(!(navajo.tenant=*)))";
		}
			
		
		ServiceReference<CompiledScriptFactory>[] sr;
		try {
			sr = (ServiceReference<CompiledScriptFactory>[]) bundleContext.getServiceReferences(
					CompiledScriptFactory.class.getName(), filter);
			if (sr != null && sr.length != 0) {
				CompiledScriptFactory csf = bundleContext.getService(sr[0]);
				if (csf == null) {
					logger.warn("Script with filter: " + filter
							+ " found, but could not be resolved.");
					return null;
				}
				CompiledScriptInterface cs = csf.getCompiledScript();
				return cs;
			}
		} catch (InvalidSyntaxException e) {
			throw new ClassNotFoundException(
					"Error resolving script service for: " + rpcName, e);
		} catch (InstantiationException e) {
			throw new ClassNotFoundException(
					"Error resolving script service for: " + rpcName, e);
		} catch (IllegalAccessException e) {
			throw new ClassNotFoundException(
					"Error resolving script service for: " + rpcName, e);
		}

		return null;
	}

//	private CompiledScript waitForService(String rpcPath, String tenant) throws Exception {
//		String rpcName = rpcPath.replaceAll("/", ".");
//		// script://person/InitSearchPersons
//		File cscripts = new File(navajoIOConfig.getCompiledScriptPath());
//		File scriptFile = new File(cscripts,rpcPath+".jar");
//		System.err.println("Path: "+scriptFile.getAbsolutePath());
//		URL u = scriptFile.toURI().toURL();
//		
//		final String bundleURI = u.toString();
//		Bundle scriptBundle = bundleContext.getBundle(bundleURI);
//		if (scriptBundle == null) {
//			throw new UserException(-1, "Can not resolve bundle for service: "
//					+ rpcName + " failed to find bundle with URI: " + bundleURI);
//		}
//		if (scriptBundle.getState() != Bundle.ACTIVE) {
//			throw new UserException(-1, "Can not resolve bundle for service: "
//					+ rpcName + " bundle with URI: " + bundleURI
//					+ " is not active. State: " + scriptBundle.getState());
//		}
//		return getCompiledScript(rpcName, tenant);
//	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void verifySingleScript(String scriptPath, List<String> failed,
			List<String> success) {
		scriptPath = scriptPath.replace('/', '.');
		String filter = "(navajo.scriptName=" + scriptPath + ")";
		try {
			ServiceReference[] ssr = bundleContext.getServiceReferences(
					CompiledScriptFactory.class.getName(), filter);
			//
			// Collection<ServiceReference<CompiledScriptFactory>> sr =
			// bundleContext.getServiceReferences(CompiledScriptFactory.class,
			// filter);
			if (ssr==null || ssr.length == 0) {
				logger.warn("Can not locate service for script: " + scriptPath
						+ " filter: " + filter);
				failed.add(scriptPath);
				return;
			}
			if (ssr.length > 1) {
				logger.warn("MULTIPLE scripts with the same name detected, marking as failed. Filter: "
						+ scriptPath);
				return;
			}
			logger.info("# of services: " + ssr.length);
			// final ServiceReference<CompiledScriptFactory> ref =
			// sr.iterator().next();

			CompiledScriptFactory csf = bundleContext.getService(ssr[0]);
			if (csf == null) {
				logger.warn("Script with filter: " + filter
						+ " found, but could not be resolved.");
				return;
			}
			CompiledScriptInterface cs = csf.getCompiledScript();
			logger.debug("Compiled script seems ok. " + cs.toString());
			bundleContext.ungetService(ssr[0]);
			success.add(scriptPath);
		} catch (InvalidSyntaxException e) {
			logger.error("Error parsing filter: " + filter, e);
			failed.add(scriptPath);
		} catch (Exception e) {
			logger.error("Error instantiating script: " + scriptPath
					+ " instantiation seems to have failed.", e);
			failed.add(scriptPath);
		}

	}

	private void verifyScripts(File baseDir, List<String> failed,
			List<String> success) {
		final String extension = "xml";
		File compiledScriptPath = new File(navajoIOConfig.getScriptPath());
		Iterator<File> it = FileUtils.iterateFiles(baseDir,
				new String[] { extension }, true);
		while (it.hasNext()) {
			File file = it.next();
			String relative = getRelative(file, compiledScriptPath);
			if (!relative.endsWith(extension)) {
				logger.warn("Odd: " + relative);
			}
			String withoutEx = relative.substring(0, relative.lastIndexOf('.'));
			verifySingleScript(withoutEx, failed, success);

		}
	}

}
