/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.compiler.tsl.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.BundleCreator;
import com.dexels.navajo.compiler.tsl.BundleQueue;
import com.dexels.navajo.dependency.Dependency;
import com.dexels.navajo.dependency.DependencyAnalyzer;
import com.dexels.navajo.repository.api.RepositoryInstance;
import com.dexels.navajo.repository.api.util.RepositoryEventParser;
import com.dexels.navajo.script.api.CompilationException;

public class BundleQueueComponent implements EventHandler, BundleQueue {
    private static final String SCALA_FOLDER = "scala" + File.separator;
    private static final String SCRIPTS_FOLDER = "scripts" + File.separator;
    private static final List<String> SUPPORTED_EXTENSIONS = Arrays.asList(".xml", ".scala", ".ns");
    private BundleCreator bundleCreator = null;
    private ExecutorService executor;
    private DependencyAnalyzer depanalyzer;
    
    private boolean keepIntermediateFiles = true;

    // For testing purposes we need to be able to force synchronisation
    private boolean forceSync = false;

    private static final Logger logger = LoggerFactory.getLogger(BundleQueueComponent.class);

    public BundleQueueComponent()
    {
    }

    // Only the test(s) should be able to access this
    protected BundleQueueComponent( boolean forceSync )
    {
        this.forceSync = forceSync;
    }

    public void setBundleCreator(BundleCreator bundleCreator) {
        this.bundleCreator = bundleCreator;
    }

    public void activate() {
        this.executor = Executors.newFixedThreadPool(1);
    }

    public void deactivate() {
        executor.shutdown();
        executor = null;
    }

    public void setDependencyAnalyzer(DependencyAnalyzer depa) {
        depanalyzer = depa;
    }

    public void clearDependencyAnalyzer(DependencyAnalyzer depa) {
        depanalyzer = null;
    }

    /* (non-Javadoc)
     * @see com.dexels.navajo.compiler.tsl.internal.BundleQueue#enqueueScript(java .lang.String) */
    @Override
    public void enqueueScript(final String script, final String path) {
        if( forceSync )
        {
            compileScript( script, path );
        }
        else
        {
            executor.execute(() -> {
                compileScript( script, path );
            });
        }
    }
    
    /* (non-Javadoc)
     * @see com.dexels.navajo.compiler.tsl.internal.BundleQueue#compileScript(java .lang.String) */
    @Override
    public synchronized boolean compileScript(final String script, final String path) {
        boolean compilationSuccess = true;
        List<String> failures = new ArrayList<>();
        List<String> success = new ArrayList<>();
        List<String> skipped = new ArrayList<>();
        logger.info("Eagerly compiling: {}", script);
        try {
            bundleCreator.createBundle(script, failures, success, skipped, true, keepIntermediateFiles);
            bundleCreator.installBundle(script, failures, success, skipped, true);
            if (!skipped.isEmpty()) {
                compilationSuccess = false;
                logger.info("Script compilation skipped: {}", script);
            }
            if (!failures.isEmpty()) {
                compilationSuccess = false;
                logger.info("Script compilation failed: {}", script);
            }
            if (compilationSuccess || !skipped.isEmpty()) {
                ensureScriptDependencies(script);
                enqueueDependentScripts(script);
            }

        } catch (Throwable e) {
            compilationSuccess = false;
            bundleCreator.uninstallBundle( script );
            logger.error("Error: ", e);
        }
        return compilationSuccess;
    }

    public void enqueueDeleteScript(final String script) {
        if( forceSync )
        {
            uninstallScript( script );
        }
        else
        {
            executor.execute(() -> {
                uninstallScript( script );
            });
        }
    }

    private void uninstallScript(final String script) {
        // String tenant = script.
        logger.info("Uninstalling: {}", script);
        try {
            bundleCreator.uninstallBundle(script);
        } catch (Throwable e) {
            logger.error("Error: ", e);
        }
    }

    /**
     * 
     * @param bundleCreator
     *            the bundlecreator to clear
     */
    public void clearBundleCreator(BundleCreator bundleCreator) {
        this.bundleCreator = null;
    }

    @Override
    public void handleEvent(Event e) {
        try {

            checkForChangedScripts(e, SCRIPTS_FOLDER);
            checkForRemovedScripts(e, SCRIPTS_FOLDER);
            checkForRemovedScripts(e, SCALA_FOLDER);
            checkForChangedScripts(e, SCALA_FOLDER);
        } catch (Exception e1) {
            logger.error("Exception on handling event: {}", e);
        }

    }

    private void checkForRemovedScripts(Event e, String folder) {
        List<String> deletedScripts = RepositoryEventParser.filterDeleted(e, folder);
        for (String deletedScript : deletedScripts) {
            // Replace windows backslashes with normal ones
            deletedScript = deletedScript.replace("\\", "/");
            // Uninstall bundle
            String stripped = deletedScript.substring(folder.length());
            int dotIndex = stripped.lastIndexOf(".");
            if (dotIndex < 0) {
                logger.info("Scripts need an extension, and {} has none. Ignoring.",stripped);
                continue;
            }

            String extension = stripped.substring(dotIndex, stripped.length());
            String scriptName = stripped.substring(0, dotIndex);
            if (!SUPPORTED_EXTENSIONS.contains(extension)) {
                logger.info("Ignoring file delete {} due to non-matching extension: {} ", deletedScript, extension);
                return;
            }
            if (scriptName.endsWith("entitymapping")) {
            	continue;
            }
            enqueueDeleteScript(scriptName);
            enqueueDependentScripts( scriptName );
        }
    }

    private void checkForChangedScripts(Event e, String folder) {
        RepositoryInstance ri = (RepositoryInstance) e.getProperty("repository");
        Set<String> changedScripts = new HashSet<>(RepositoryEventParser.filterChanged(e, folder));
        for (String changedScript : changedScripts) {
            // Replace windows backslashes with normal ones
            changedScript = changedScript.replace("\\", "/");
            try {
                File location = new File(ri.getRepositoryFolder(), changedScript);
                if (location.isFile()) {
                    String stripped = changedScript.substring(folder.length());
                    int dotIndex = stripped.lastIndexOf('.');
                    if (dotIndex < 0) {
                        logger.info("Scripts need an extension, and {} has none. Ignoring update.", stripped);
                        continue;
                    }
                    String scriptName = stripped.substring(0, dotIndex);
                    String extension = stripped.substring(dotIndex, stripped.length());
                    if (!SUPPORTED_EXTENSIONS.contains(extension)) {
                        logger.info("Ignoring file update {} due to non-matching extension: {} ", scriptName, extension);
                        continue;
                    }
                    if (scriptName.endsWith("entitymapping")) {
                    	continue;
                    }
                    enqueueScript(scriptName, changedScript);
                }
            } catch (IllegalArgumentException e1) {
                logger.warn("Error in handling changed script {}: {}", changedScript, e1);
            }
        }
    }

    // ensure that dependencies of the current script are satisfied. If dependencies
    // are not satisfied, create them
    private void ensureScriptDependencies(String script) {
        String rpcName = script;
        String bareScript = script.substring(script.lastIndexOf('/') + 1);
        if (bareScript.indexOf('_') >= 0) {
            rpcName = script.substring(0, script.lastIndexOf('_'));
        }

        // For now, only entity dependencies are relevant script dependencies.
        // For instance, in the case the server runs in DEVELOP_MODE where
        // entities are lazily loaded, all bundles for super entities also need
        // to be installed
        List<Dependency> dependencies = depanalyzer.getDependencies(rpcName, Dependency.ENTITY_DEPENDENCY);

        for (Dependency dependency : dependencies) {
            String depScript = dependency.getDependee();
            try {
                // do an on demand call to the bundle creator, we only need the script to be
                // compiled, if it wasn't there yet
                bundleCreator.getOnDemandScriptService(depScript, null);
                ensureScriptDependencies(depScript);
            } catch (CompilationException e) {
                logger.info("Failed to compile {} after a change in {}: {}", depScript, script, e);
            }
        }
    }

    private void enqueueDependentScripts(String script) {
        String rpcName = script;
        String bareScript = script.substring(script.lastIndexOf('/') + 1);
        if (bareScript.indexOf('_') >= 0) {
            rpcName = script.substring(0, script.lastIndexOf('_'));
        }
        List<Dependency> dependencies = depanalyzer.getReverseDependencies(rpcName);
        
        // Going to pretend all the scripts that include us, also changed. This triggers their
        // re-compile, so that they have the correct version. This goes recursive, to allow
        // handling includes within includes within includes etc. Use a History set to prevent
        // a loop somewhere.
        // Use a set to prevent duplicates due to tenant-specific dependencies
        Set<String> dependentScripts = new HashSet<>();
        for (Dependency dep : dependencies) {
            if (dep.needsRecompile()) {
                dependentScripts.add(dep.getScript());
            }

        }
        for (String depScript : dependentScripts) {
            logger.info("Going to recompile {} after a change in {}", depScript, script);
            // recursion happens in enqueuescript after installing the bundle.
            // This is important, because dependencies only exist after installing
            enqueueScript(depScript, null);
        }
    }

}
