package com.dexels.navajo.compiler.tsl.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
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
import com.dexels.navajo.compiler.BundleCreatorFactory;
import com.dexels.navajo.compiler.JavaCompiler;
import com.dexels.navajo.compiler.ScriptCompiler;
import com.dexels.navajo.dependency.Dependency;
import com.dexels.navajo.dependency.DependencyAnalyzer;
import com.dexels.navajo.mapping.compiler.SkipCompilationException;
import com.dexels.navajo.script.api.CompiledScriptFactory;
import com.dexels.navajo.script.api.CompiledScriptInterface;
import com.dexels.navajo.server.NavajoIOConfig;

public class BundleCreatorComponent implements BundleCreator {
    private NavajoIOConfig navajoIOConfig = null;
    private EventAdmin eventAdmin = null;
    private final static Logger logger = LoggerFactory.getLogger(BundleCreatorComponent.class);

    private Map<String, ScriptCompiler> compilers = new HashMap<String, ScriptCompiler>();
    private JavaCompiler javaCompiler;

    private Map<String, ReentrantLock> lockmap;
    
    private Map<String, Map<String,CompiledScriptFactory>> scriptsMap = new HashMap<>();

    /* (non-Javadoc)
     * @see com.dexels.navajo.compiler.tsl.ScriptCompiler#compileTsl(java.lang.String ) */
    private BundleContext bundleContext;
    private DependencyAnalyzer depanalyzer;

    public void setTslScriptCompiler(ScriptCompiler sc) {
        compilers.put(sc.getScriptExtension(), sc);
    }
    
    public void removeTslScriptCompiler(ScriptCompiler sc) {
        compilers.remove(sc.getScriptExtension());
    }
    
    public void setScalaScriptCompiler(ScriptCompiler sc) {
        compilers.put(sc.getScriptExtension(), sc);
    }
    
    public void removeScalaScriptCompiler(ScriptCompiler sc) {
        compilers.remove(sc.getScriptExtension());
    }
    public void setJavaCompiler(JavaCompiler javaCompiler) {
        this.javaCompiler = javaCompiler;
    }



    public void setDependencyAnalyzer(DependencyAnalyzer depa) {
        depanalyzer = depa;
    }

    public void clearDependencyAnalyzer(DependencyAnalyzer depa) {
        depanalyzer = null;
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
    
    private ScriptCompiler getScriptCompiler(File file) throws Exception {
        return compilers.get( getFileExtension(file));
    }
    
	private static String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") > 0)
			return fileName.substring(fileName.lastIndexOf("."));
		return "";
	}

	/**
	 * scriptName can include the _TENANT part
	 */
	@Override
	public void createBundle(String scriptName, List<String> failures, List<String> success, List<String> skipped,
			boolean force, boolean keepIntermediate) throws Exception {

		File scriptPath = getApplicableScriptFile(scriptName, null);
		logger.info("No known path for {} - determined {} as script path!", scriptName, scriptPath);
		

		String bareScript = scriptName.substring(scriptName.lastIndexOf("/") + 1);
		String rpcName = scriptName;

		if (bareScript.indexOf("_") > 0) {
			rpcName = scriptName.substring(0, rpcName.lastIndexOf("_"));
		}

		// Use ReentrantLock to ensure we don't compile same script twice at the same
		// time
		ReentrantLock lockObject = getLock(rpcName, "compile");
		try {
			if (lockObject.tryLock()) {
				createBundleNoLocking(rpcName, failures, success, skipped, force, keepIntermediate);
			} else {
				// Someone else is already compiling this script. Wait for it to release the
				// lock, and then we can return immediately since they compiled the script for us
				logger.info("Simultaneous compiling of {} - going to wait it out...", rpcName);
				lockObject.lock();
				return;
			}
		} finally {
			releaseLock(rpcName, "compile", lockObject);
		}

	}

    private void createBundleNoLocking(String rpcName, List<String> failures, List<String> success, List<String> skipped,
            boolean force, boolean keepIntermediate) throws Exception {

    	boolean matchedScript = false;
    	removeOldCompiledScriptFiles(rpcName);
    	for (ScriptCompiler compiler : compilers.values()) {
    		 File scriptFolder = new File(navajoIOConfig.getRootPath(), compiler.getRelativeScriptPath());
    	     File f = new File(scriptFolder, rpcName + compiler.getScriptExtension());
    	     
    	  // Look for other tenant-specific files
             AbstractFileFilter fileFilter = new WildcardFileFilter(FilenameUtils.getBaseName(rpcName) + "_*" + compiler.getScriptExtension());
             File dir = new File(scriptFolder, FilenameUtils.getPath(rpcName)); 
             Collection<File> files = Collections.<File>emptySet();
             if (dir.exists()) {
            	 files = FileUtils.listFiles(dir, fileFilter, null);
             }
             Map<String, File> tenantSpecificFiles = new HashMap<String, File>();
             Collection<String> tenantsToIgnore = new ArrayList<String>();
             for (File ascript : files) {
            	 matchedScript = true;
                 String pathRelative = getRelative(scriptFolder, ascript );
                 String[] splitted =  pathRelative.split("\\.");
                 String tenantScriptName = splitted[0].replace('\\', '/');
//                 String extension = "." + splitted[1];
                 tenantSpecificFiles.put(tenantScriptName, ascript);
                 // Get the tenant out of the name and put it in the tenantsToIgnore collection
                 tenantsToIgnore.add(tenantScriptName.split("_")[1]); 
             }
             
    	     if (f.exists()) {
    	    	 matchedScript = true;
        	     createBundleForScript(rpcName, rpcName,  f, tenantsToIgnore, true, failures, success, skipped, keepIntermediate);
    	     }
    	     for (Map.Entry<String, File> entry : tenantSpecificFiles.entrySet())
    	     {
    	    	 createBundleForScript(entry.getKey(), rpcName, entry.getValue(),  tenantsToIgnore, false, failures, success, skipped, keepIntermediate);
    	     }

             
    	}
    	
    	if (!matchedScript) {
    		throw new FileNotFoundException("Unable to find script for " + rpcName);
    	}
    }

	private void createBundleForScript(String script, String rpcName, File scriptFile, Collection<String> ignoreTenants, boolean isGenericVersion,
			List<String> failures, List<String> success, List<String> skipped, boolean keepIntermediate)
			throws Exception {
      
        String scriptTenant = tenantFromScriptPath(script);

        if (!scriptFile.exists()) {
            logger.error("Script or  folder not found: " + script + " full path: " + scriptFile.getAbsolutePath());
            return;
        }

        // Only do the include dependency check if we're at the generic version of the script
        if (isGenericVersion && getScriptCompiler(scriptFile).supportTslDependencies()) {
        	 Set<String> newTenants = new HashSet<>();
        	 depanalyzer.addDependencies(script, scriptFile);
             List<Dependency> dependencies = depanalyzer.getDependencies(script, Dependency.INCLUDE_DEPENDENCY);

             if (dependencies != null) {
                 // We are at the generic version, but check whether we include any tenant-specific files.
                 // If so, compile all versions as if we are tenant-specific (forceTenant), but ignore those tenants that have a tenant-specific version of the original script
                 for (Dependency d : dependencies) {
                     if (d.isTentantSpecificDependee() && ! ignoreTenants.contains(d.getTentantDependee())) {
                         newTenants.add(d.getTentantDependee());
                     }
                 }
                 for(String newTenant : newTenants) {
                     compileAndCreateBundle(script, scriptFile, newTenant, false,
                             true, keepIntermediate, success, skipped, failures);
                 }
                 
                 
             }

             if (isGenericVersion) {
                 // We are not tenant-specific, but check whether we used to have any includes that
                 // were tenant-specific, that furthermore no longer exist in the new version.
                 // If so, those must be removed.
                 uninstallObsoleteTenantScript(rpcName, newTenants);
             }
        }
       
        compileAndCreateBundle(script, scriptFile, scriptTenant, !isGenericVersion, false, keepIntermediate,
                success, skipped, failures);
    }
    


    private void removeOldCompiledScriptFiles(String rpcName) {
        AbstractFileFilter fileFilter = new WildcardFileFilter(FilenameUtils.getBaseName(rpcName) + "_*.jar");
        File compiledPath = new File(navajoIOConfig.getCompiledScriptPath(), FilenameUtils.getPath(rpcName));
        if (compiledPath.isDirectory()) {
            // Removing jar itself
            File jarFile = new File(compiledPath, FilenameUtils.getBaseName(rpcName) + ".jar");
            if (jarFile.exists()) {
                jarFile.delete();
            }
            Collection<File> files = FileUtils.listFiles(compiledPath, fileFilter, null);

            for (File bundleFile : files) {
                if (bundleFile.isFile() && bundleFile.exists()) {
                    bundleFile.delete();
                }

            }
        }

    }

    @SuppressWarnings("unchecked")
    private void uninstallObsoleteTenantScript(String rpcName, Set<String> newTenants) {
        String osgiScriptName = rpcName.replaceAll("/", ".");
        String filter = "(navajo.scriptName=" + osgiScriptName + ")";

        try {
            ServiceReference<CompiledScriptFactory>[] sr;
            sr = (ServiceReference<CompiledScriptFactory>[]) bundleContext
                    .getServiceReferences(CompiledScriptFactory.class.getName(), filter);
            if (sr == null) {
                return;
            }

            for (int i = 0; i < sr.length; i++) {
                String tenant = (String) sr[i].getProperty("navajo.tenant");
                if (tenant.equals("default")) {
                    continue;
                }
                if (!newTenants.contains(tenant)) {
                    (sr[i]).getBundle().uninstall();
                }
            }
        } catch (InvalidSyntaxException e) {
            logger.error("Invalid syntax in querying Navajo service: {}", e);
        } catch (BundleException e) {
            logger.error("Bundle exception in attempting to stop bundle for non-existing script {}", e);
        }
    }

    private void compileAndCreateBundle(String script, File scriptPath, final String scriptTenant,
            boolean hasTenantSpecificFile, boolean forceTenant, boolean keepIntermediate, List<String> success,
            List<String> skipped, List<String> failures) throws Exception {
        String myScript = script;
        if (forceTenant) {
            myScript = script + "_" + scriptTenant;
        }
        
        try{
            getScriptCompiler(scriptPath).compile(scriptPath, scriptTenant, hasTenantSpecificFile, forceTenant);
            if (getScriptCompiler(scriptPath).scriptNeedsCompilation()){
                javaCompiler.compileJava(myScript);
            }
            javaCompiler.compileJava(myScript + "Factory");
            createBundleJar(myScript,scriptPath, scriptTenant, keepIntermediate, hasTenantSpecificFile);
            success.add(myScript);
        } catch (SkipCompilationException e) {
            logger.debug("Script fragment: {} ignored: {}", script, e);
            skipped.add(script);
        } catch (Exception e) {
            failures.add(script);
            throw e;
        }
        if (forceTenant) {
            logger.info("Finished compiling and bundling {} for {}", script, scriptTenant);
        } else {
            logger.info("Finished compiling and bundling {}", script);
        }
       
    }

    private synchronized ReentrantLock getLock(String script, String context) {
        String key = script + context;
        if (!lockmap.containsKey(key)) {
            lockmap.put(key, new ReentrantLock());
        }

        return lockmap.get(key);
    }

    private synchronized void releaseLock(String script, String context, ReentrantLock lock) {
        lock.unlock();
        lockmap.remove(script + context);
    }


  
    @SuppressWarnings("unchecked")
    @Override
    public void uninstallBundle(String scriptName) {
        String scriptTenant = tenantFromScriptPath(scriptName);
        String rpcName = rpcNameFromScriptPath(scriptName);

        String osgiScriptName = rpcName.replaceAll("/", ".");
        String filter = null;
        ServiceReference<CompiledScriptFactory>[] sr;

        if (scriptTenant != null) {
            filter = "(&(navajo.scriptName=" + osgiScriptName + ")(navajo.tenant=" + scriptTenant + "))";
        } else {
            filter = "(&(navajo.scriptName=" + osgiScriptName + ") (navajo.tenant=default))";
        }

        try {
            sr = (ServiceReference<CompiledScriptFactory>[]) bundleContext
                    .getServiceReferences(CompiledScriptFactory.class.getName(), filter);
            if (sr == null) {
                return;
            }

            for (int i = 0; i < sr.length; i++) {
                (sr[i]).getBundle().uninstall();
            }
            scriptsMap.remove(rpcName);
        } catch (InvalidSyntaxException e) {
            logger.error("Invalid syntax in querying Navajo service: {}", e);
        } catch (BundleException e) {
            logger.error("Bundle exception in attempting to stop bundle for non-existing script {}", e);
        }

    }

    private String tenantFromScriptPath(String scriptPath) {
        int scoreIndex = scriptPath.lastIndexOf("_");
        int slashIndex = scriptPath.lastIndexOf("/");
        if (scoreIndex >= 0 && slashIndex < scoreIndex) {
            return scriptPath.substring(scoreIndex + 1, scriptPath.length());
        } else {
            return null;
        }
    }

    private String rpcNameFromScriptPath(String scriptPath) {
        int scoreIndex = scriptPath.lastIndexOf("_");
        int slashIndex = scriptPath.lastIndexOf("/");
        if (scoreIndex >= 0 && slashIndex < scoreIndex) {
            return scriptPath.substring(0, scoreIndex);
        } else {
            return scriptPath;
        }
    }

 
    @Override
    public void installBundle(String scriptPath, List<String> failures, List<String> success, List<String> skipped, boolean force) {
        
        ReentrantLock lockObject = getLock(scriptPath, "install");
        try {
            if (lockObject.tryLock()) {
                installBundleNoLocking(scriptPath, failures, success, skipped, force);
            } else {
                // Someone else is already compiling this script. Wait for it to release the lock,
                // and then we can return immediately since they compiled the script for us
                logger.info("Simultaneous installing of {} - going to wait it out...", scriptPath);
                lockObject.lock();
                return;
            }
        } finally {
            releaseLock(scriptPath, "install", lockObject);
        }
    }

    private void installBundleNoLocking(String scriptPath, List<String> failures, List<String> success, List<String> skipped, boolean force) {
        try {
            Bundle b = doInstall(scriptPath, force);
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

    private Bundle doInstall(String scriptPath, boolean force)
            throws BundleException, FileNotFoundException, MalformedURLException {
        String rpcName = rpcNameFromScriptPath(scriptPath);
        Bundle b = null;

        // Non-tenant specific jar file
        File compiledPath = new File(navajoIOConfig.getCompiledScriptPath(), FilenameUtils.getPath(rpcName));
        
        File jarFile = new File(compiledPath, FilenameUtils.getBaseName(rpcName) + ".jar");
        
        // Look for other tenant-specific jar files
        AbstractFileFilter fileFilter = new WildcardFileFilter(FilenameUtils.getBaseName(rpcName) + "_*.jar");
        if (!compiledPath.exists() || !compiledPath.isDirectory()) {
        	logger.warn("CompiledPath is not a directory? {} This is going to crash...", compiledPath);
        }
        
        Collection<File> files = FileUtils.listFiles(compiledPath, fileFilter, null);
        if (jarFile.exists()) {
            files.add(jarFile);
        }

        for (File bundleFile : files) {
            
            final String uri = bundleFile.toURI().toURL().toString();
            Bundle previous = bundleContext.getBundle(uri);
            if (previous != null) {
                if (force) {
                    logger.debug("uninstalling bundle with URI {} with file: {}: ", uri, bundleFile);
                    previous.uninstall();
                } else {
                    logger.debug("Skipping bundle at: " + uri + " as it is already installed. Lastmod: "
                            + new Date(previous.getLastModified()) + " status: " + previous.getState());
                    continue;
                }
            }
            logger.debug("Installing script: " + bundleFile.getName());
            FileInputStream fis = new FileInputStream(bundleFile);
            b = this.bundleContext.installBundle(uri, fis);
            b.start();
           
        }

        scriptsMap.remove(rpcName);
        return b;

    }

    private String getRelative(File base, File path) {
        String relative = base.toURI().relativize(path.toURI()).getPath();
        return relative;
    }

    private File createBundleJar(String scriptName, File scriptPath, String tenant, boolean keepIntermediateFiles, boolean useTenantSpecificFile) throws IOException {
        String packagePath = null;
        String script = null;
        if (scriptName.indexOf('/') >= 0) {
            packagePath = scriptName.substring(0, scriptName.lastIndexOf('/'));
            script = scriptName.substring(scriptName.lastIndexOf('/') + 1);
        } else {
            packagePath = "";
            script = scriptName;
        }

        String fixOffset = packagePath.equals("") ? "defaultPackage" : "";
        File compiledScriptPath = new File(navajoIOConfig.getCompiledScriptPath());
        File outPath = new File(compiledScriptPath, fixOffset);

        File java = new File(compiledScriptPath, scriptName + ".java");
        File factoryJavaFile = new File(compiledScriptPath, scriptName + "Factory.java");
        File classFile = new File(outPath, scriptName + ".class");
        File factoryClassFile = new File(outPath, scriptName + "Factory.class");
        File manifestFile = new File(compiledScriptPath, scriptName + ".MF");
        File dsFile = new File(compiledScriptPath, scriptName + ".xml");
        File entityFile = new File(compiledScriptPath, packagePath + File.separator + "entity.xml");

        File bundleDir = new File(compiledScriptPath, scriptName);
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
        File entityOsgiiScript = new File(osgiinf, "entity.xml");
        File metainfManifest = new File(metainf, "MANIFEST.MF");

        File classFileInPlace = new File(bundlePackageDir, script + ".class");
        File factoryClassFileInPlace = new File(bundlePackageDir, script + "Factory.class");

        // Scala compiled files are already in the right location
        if (!scriptPath.getPath().endsWith(".scala")) {
            FileUtils.copyFile(classFile, classFileInPlace);
        }

        FileUtils.copyFile(factoryClassFile, factoryClassFileInPlace);
        FileUtils.copyFile(manifestFile, metainfManifest);
        FileUtils.copyFile(dsFile, osgiinfScript);
        if (entityFile.exists()) {
            FileUtils.copyFile(entityFile, entityOsgiiScript);
        }

        FileUtils.copyFile(dsFile, osgiinfScript);
        File jarFile = new File(navajoIOConfig.getCompiledScriptPath(), scriptName + ".jar");

        addFolderToJar(bundleDir, null, jarFile, bundleDir.getAbsolutePath() + "/");
        if (!keepIntermediateFiles) {
            classFile.delete();
            manifestFile.delete();
            dsFile.delete();
            java.delete();
            factoryJavaFile.delete();
            factoryClassFile.delete();
            if (entityFile.exists()) {
                entityFile.delete();
            }
            FileUtils.deleteQuietly(bundleDir);
        }
        return jarFile;
    }

    private void addFolderToJar(File folder, ZipArchiveOutputStream jarOutputStream, File jarFile, String baseName)
            throws IOException {
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
                String name = file.getAbsolutePath().substring(baseName.length());
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
        BundleCreatorFactory.setInstance(this);
        this.bundleContext = bundleContext;
        lockmap = new HashMap<String, ReentrantLock>();
    }

    public void deactivate() {
        logger.debug("Deactivating Bundle creator");
        this.bundleContext = null;
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


    /**
     * rpcName does not include tenant suffix
     * @throws Exception 
     */
    @Override 
    public CompiledScriptInterface getOnDemandScriptService(String rpcName, String tenant) throws Exception {

        if (rpcName.indexOf("/") == -1) {
            if (rpcName.indexOf('_') != -1) {
                throw new IllegalArgumentException("rpcName should not have a tenant suffix: " + rpcName + " scriptName: " + rpcName);
            }
        }
        CompiledScriptInterface sc = getCompiledScript(rpcName, tenant);

        if (sc != null) {
            return sc;
        } else {
            ReentrantLock lockObject = getLock(rpcName, "compileinstall");
            try {
                if (lockObject.tryLock()) {
                    List<String> failures = new ArrayList<String>();
                    List<String> success = new ArrayList<String>();
                    List<String> skipped = new ArrayList<String>();
    
                    boolean keepIntermediateFiles = false;
                    boolean force = false;
                    createBundleNoLocking(rpcName, failures, success, skipped, force, keepIntermediateFiles);
                    installBundleNoLocking(rpcName, failures, success, skipped, force);
                } else {
                    // Someone else is already compiling this script. Wait for it to release the lock,
                    // and then we can return immediately since they compiled the script for us
                    logger.info("Simultaneous compiling and installing of {} - going to wait it out...", rpcName);
                    lockObject.lock();

                }
            } finally {
                releaseLock(rpcName, "compileinstall", lockObject);
            }
        }

        logger.debug("Finished on demand compiling of: {}", rpcName);
        return getCompiledScript(rpcName, tenant);
    }
    
    private File getApplicableScriptFile(String rpcName, String tenant)  {
    	for (ScriptCompiler compiler : compilers.values()) {
    		 File scriptFolder = new File(navajoIOConfig.getRootPath(), compiler.getRelativeScriptPath());
    		 if (tenant != null) {
    			 String tenantFilename = rpcName + "_" + tenant + compiler.getScriptExtension();
        	     File f = new File(scriptFolder, tenantFilename);
        	     if (f.exists()) {
        	    	 return f;
        	     } 
    		 }
    		
    	     String filename = rpcName +compiler.getScriptExtension();
    	     File f = new File(scriptFolder, filename);
    	     if (f.exists()) {
    	    	 return f;
    	     }
    	}
		return null;
	}

    @SuppressWarnings("unchecked")
    private CompiledScriptInterface getCompiledScript(String rpcName, String tenant)
            throws ClassNotFoundException {
        String scriptName = rpcName.replaceAll("/", ".");
        
        String realTenant = "default";
        if (tenant != null) {
            realTenant = tenant;
        }
        
        if (scriptsMap.containsKey(rpcName)) {
            Map<String, CompiledScriptFactory> myScripts = scriptsMap.get(rpcName);
            
            CompiledScriptFactory csf = myScripts.get(realTenant);
            if (csf != null) {
                try {
                    return csf.getCompiledScript();
                } catch (InstantiationException | IllegalAccessException e) {
                    logger.error("Exception on retrieving cached CompiledScriptFactory for {} {} - going to try non-cached one", rpcName, realTenant);
                }
            }
        }
        

       String filter = "(&(navajo.scriptName=" + scriptName + ") (|(navajo.tenant=" + tenant + ") (navajo.tenant=default)))";
        
        ServiceReference<CompiledScriptFactory>[] servicereferences;
        try {
            servicereferences = (ServiceReference<CompiledScriptFactory>[]) bundleContext
                    .getServiceReferences(CompiledScriptFactory.class.getName(), filter);
            if (servicereferences != null) {
             // First try to find one that matches our tenant
                for (ServiceReference<CompiledScriptFactory> srinstance : servicereferences) {
                    if (srinstance.getProperty("navajo.tenant").equals(tenant)) {
                        CompiledScriptFactory csf = bundleContext.getService(srinstance);
                        if (csf == null) {
                            logger.warn("Script with filter: " + filter + " found, but could not be resolved.");
                            return null;
                        }
                        updateCachedCompiledScript(rpcName, realTenant, csf);
                        return csf.getCompiledScript();
                    }
                }
              
                // if that fails, simply return first one (probably "default")
                if (servicereferences.length > 0) {
                    ServiceReference<CompiledScriptFactory> srinstance = servicereferences[0];
                    CompiledScriptFactory csf = bundleContext.getService(srinstance);
                    if (csf == null) {
                        logger.warn("Script with filter: " + filter + " found, but could not be resolved.");
                        return null;
                    }
                    updateCachedCompiledScript(rpcName, realTenant, csf);
                    return csf.getCompiledScript();
                }
            }
            
        } catch (InvalidSyntaxException e) {
            throw new ClassNotFoundException("Error resolving script service for: " + rpcName, e);
        } catch (InstantiationException e) {
            throw new ClassNotFoundException("Error resolving script service for: " + rpcName, e);
        } catch (IllegalAccessException e) {
            throw new ClassNotFoundException("Error resolving script service for: " + rpcName, e);
        }

        return null;
    }
    
    private synchronized void updateCachedCompiledScript(String rpcName, String realTenant, CompiledScriptFactory csf) {
        Map<String,CompiledScriptFactory> subMap = scriptsMap.get(rpcName);
        if (subMap == null) {
            subMap = new HashMap<>();
        }
        subMap.put(realTenant, csf);
        scriptsMap.put(rpcName,  subMap);
        
    }

   public static void main(String[] args) {
        // aap_noot/mies_wim/InitUpdateClub
        BundleCreatorComponent bcc = new BundleCreatorComponent();
        String s = bcc.rpcNameFromScriptPath("aap_noot/mies_wim/InitUpdateClub");
        System.out.println(s);
    }
}
