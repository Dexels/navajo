package com.dexels.navajo.dependency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.NavajoIOConfig;

public class DependencyAnalyzer {
    private final static Logger logger = LoggerFactory.getLogger(DependencyAnalyzer.class);

    protected TslPreCompiler precompiler;
    private NavajoIOConfig navajoIOConfig = null;

    protected Map<String, List<Dependency>> dependencies;;
    protected Map<String, List<Dependency>> reverseDependencies;
    protected String scriptFolder;

    public void activate() {
        logger.debug("Activating DependencyAnalyzer");
        precompiler = new TslPreCompiler();
        precompiler.setIOConfig(navajoIOConfig);
        scriptFolder = navajoIOConfig.getScriptPath();
        initialize();

    }

    protected void initialize() {
        dependencies = new HashMap<String, List<Dependency>>();
        reverseDependencies = new HashMap<String, List<Dependency>>();
    }

    public void addDependencies(String script, String scriptExtension) {

        List<Dependency> myDependencies = new ArrayList<Dependency>();
        String scriptTenant = tenantFromScriptPath(script);

        try {
            precompiler.getAllDependencies(script, scriptExtension, scriptFolder, myDependencies, scriptTenant);
            // codeSearch.getAllWorkflowDependencies(scriptFile, scriptPath,
            // scriptFolder, myDependencies);
        } catch (Exception e) {
            logger.error(" Exception on getting depencencies for {}: {}", script, e);
            return;
        }
        dependencies.put(script, myDependencies);

        updateReverseDependencies(myDependencies);
    }

    public List<Dependency> getDependencies(String scriptName) {
        return dependencies.get(scriptName);
    }
    
    public List<Dependency> getDependencies(String scriptPath, int dependencyType) {
        List<Dependency> allDeps = dependencies.get(scriptPath);
        List<Dependency> result = new ArrayList<Dependency>();
        
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

        if (scriptPath.indexOf('_') > 0) {
            // Remove tenant-specific part
            script = scriptPath.substring(0, scriptPath.indexOf('_'));
        }
        if (reverseDependencies.containsKey(script)) {
            return reverseDependencies.get(script);
        }
        return new ArrayList<>();

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


    protected void updateReverseDependencies(List<Dependency> dependencies) {

        for (Dependency dep : dependencies) {

            if (!reverseDependencies.containsKey(dep.getDependee())) {
                reverseDependencies.put(dep.getDependee(), new ArrayList<Dependency>());
            }
            reverseDependencies.get(dep.getDependee()).add(dep);
        }
    }

    public void setIOConfig(NavajoIOConfig config) {
        this.navajoIOConfig = config;
    }

    public void clearIOConfig(NavajoIOConfig config) {
        this.navajoIOConfig = null;
    }

}
