package com.dexels.navajo.dependency;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.NavajoIOConfig;

public class DependencyAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(DependencyAnalyzer.class);

    protected TslPreCompiler precompiler;
    private NavajoIOConfig navajoIOConfig = null;

    protected Map<String, List<Dependency>> dependencies;
    protected Map<String, List<Dependency>> reverseDependencies;
    protected String scriptFolder;
    private Object sync = new Object();

    public void activate() {
        logger.info("Activating DependencyAnalyzer");
        precompiler = new TslPreCompiler();
        precompiler.setIOConfig(navajoIOConfig);
        scriptFolder = navajoIOConfig.getScriptPath();
        initialize();
    }
    
    public void deactivate() {
        logger.info("DeActivating DependencyAnalyzer");
        precompiler = null;
        dependencies.clear();
        reverseDependencies.clear();
    }


    protected void initialize() {
        dependencies = new HashMap<>();
        reverseDependencies = new HashMap<>();
    }

    public void addDependencies(String script, File scriptFile) {

        Thread t = Thread.currentThread();
        ClassLoader cl = t.getContextClassLoader();
        t.setContextClassLoader(getClass().getClassLoader());
        try {
            List<Dependency> myDependencies = new ArrayList<>();
            String scriptTenant = tenantFromScriptPath(scriptFile.getAbsolutePath());

            synchronized (sync) {
                try {
                    precompiler.getAllDependencies(scriptFile, scriptFolder, myDependencies, scriptTenant);
                } catch (Exception e) {
                    logger.error(" Exception on getting depencencies for: " + scriptFile.getAbsolutePath(), e);
                    return;
                }
                dependencies.put(script, myDependencies);

                updateReverseDependencies(myDependencies);
            }
            // Also ensure any includes I depend on, have their dependencies
            // set correct
            for (Dependency dep : myDependencies) {
                if (dep.getType() == Dependency.INCLUDE_DEPENDENCY) {
                    addDependencies(dep.getDependee(), new File(dep.getDependeeFile()));
                }
            }
        } finally {
            t.setContextClassLoader(cl);
        }

    }

    public List<Dependency> getDependencies(String scriptName) {
        return dependencies.get(scriptName);
    }
    
    public List<Dependency> getDependencies(String scriptPath, int dependencyType) {
        List<Dependency> allDeps = dependencies.get(scriptPath);
        List<Dependency> result = new ArrayList<>();
        
        if (allDeps == null) {
            return result;
        }
        
        for (Dependency dep : allDeps) {
            if (dep.getType() == dependencyType) {
                result.add(dep);
            }
        }
        return result;
    }

    public List<Dependency> getReverseDependencies(String scriptPath) {
        String script = scriptPath;

        if (scriptPath.indexOf('_') >= 0) {
            int slashIndex = scriptPath.lastIndexOf('/');
            
            if (slashIndex != -1) {
                // Check if the last '_' is after the / part, since a _ might occur in a directory name
                String bareScript = scriptPath.substring(slashIndex + 1);
                if (bareScript.indexOf('_') != -1) {
                    script = scriptPath.substring(0, scriptPath.lastIndexOf('_'));
                }
            } else {
                script = scriptPath.substring(0, scriptPath.lastIndexOf('_'));
            }
        }
        if (reverseDependencies.containsKey(script)) {
            return reverseDependencies.get(script);
        }
        return new ArrayList<>();

    }

    private String tenantFromScriptPath(String scriptPath) {
        int scoreIndex = scriptPath.lastIndexOf('_');
        int slashIndex = scriptPath.lastIndexOf('/');
        
        if (slashIndex != -1) {
            String bareScript = scriptPath.substring(slashIndex + 1);
            scoreIndex = bareScript.lastIndexOf('_');
            if (scoreIndex != -1) {
                return bareScript.substring(scoreIndex+1, bareScript.length());
            } 
        } else if (scoreIndex > -1)  {
            return scriptPath.substring(scoreIndex+1, scriptPath.length());
        }
        return null;
        
    }


    protected void updateReverseDependencies(List<Dependency> dependencies) {

        for (Dependency dep : dependencies) {

            if (!reverseDependencies.containsKey(dep.getDependee())) {
                reverseDependencies.put(dep.getDependee(), new ArrayList<Dependency>());
            }
            List<Dependency> reverse = reverseDependencies.get(dep.getDependee());
            if (!reverse.contains(dep)) {
                reverseDependencies.get(dep.getDependee()).add(dep);
            }
        }
    }

    public void setIOConfig(NavajoIOConfig config) {
        this.navajoIOConfig = config;
    }

    public void clearIOConfig(NavajoIOConfig config) {
        this.navajoIOConfig = null;
    }

}
