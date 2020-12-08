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

public class BundleQueueComponent implements EventHandler, BundleQueue {
	private static final String SCALA_FOLDER = "scala" + File.separator;
    private static final String SCRIPTS_FOLDER = "scripts" + File.separator;
    private static final List<String> SUPPORTED_EXTENSIONS = Arrays.asList(".xml", ".scala");
    private BundleCreator bundleCreator = null;
    private ExecutorService executor;
    private DependencyAnalyzer depanalyzer;
    
    private boolean keepIntermediateFiles = true;

    private static final Logger logger = LoggerFactory.getLogger(BundleQueueComponent.class);

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
        executor.execute(() -> {
		    List<String> failures = new ArrayList<>();
		    List<String> success = new ArrayList<>();
		    List<String> skipped = new ArrayList<>();
		    logger.info("Eagerly compiling: {}", script);
		    try {
		        bundleCreator.createBundle(script, failures, success, skipped, true, keepIntermediateFiles);
		        bundleCreator.installBundle(script, failures, success, skipped, true);
		        if (!skipped.isEmpty()) {
		            logger.info("Script compilation skipped: {}", script);
		        }
		        if (!failures.isEmpty()) {
		            logger.info("Script compilation failed: {}", script);
		        }

		    } catch (Throwable e) {
		        logger.error("Error: ", e);
		    }
		});
    }

    public void enqueueDeleteScript(final String script) {
        executor.execute(() -> {
		    // String tenant = script.
		    logger.info("Uninstalling: {}", script);
		    try {
		        bundleCreator.uninstallBundle(script);
		    } catch (Throwable e) {
		        logger.error("Error: ", e);
		    }
		});
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
                    enqueueDependentScripts(scriptName, new HashSet<String>());
                }
            } catch (IllegalArgumentException e1) {
                logger.warn("Error in handling changed script {}: {}", changedScript, e1);
            }
        }
    }


    private void enqueueDependentScripts(String script, Set<String> history) {
        history.add(script);
        
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
        // Use a set to prevent duplicates due to tenent-specific dependencies
        Set<String> dependentScripts = new HashSet<>();
        for (Dependency dep : dependencies) {
            if (dep.getType() == Dependency.INCLUDE_DEPENDENCY) {
                dependentScripts.add(dep.getScript());
            }

        }
        for (String depScript : dependentScripts) {
            if (history.contains(depScript)) {
                logger.warn("Circular include dependency found! history: {} new: {}", history, depScript);
                return;
            }
            logger.info("Going to recompile {} after a change in {}", depScript, script);
            enqueueScript(depScript, null);
            enqueueDependentScripts(depScript, history);
        }
    }

}
