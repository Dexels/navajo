package com.dexels.navajo.dependency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.mapping.compiler.meta.IncludeDependency;
import com.dexels.navajo.mapping.compiler.meta.NavajoDependency;
import com.dexels.navajo.script.api.Dependency;
import com.dexels.navajo.server.NavajoIOConfig;

public class DependencyAnalyzer {
    private TslPreCompiler precompiler;
    private NavajoIOConfig navajoIOConfig = null;
    
    private Map<String, List<Dependency>> dependencies = new HashMap<String, List<Dependency>>();
    private Map<String, Set<String>> reverseIncludeDependencies = new HashMap<String, Set<String>>();

    private Map<String, Set<String>> reverseNavajoDependencies = new HashMap<String, Set<String>>();

    private final static Logger logger = LoggerFactory.getLogger(DependencyAnalyzer.class);

    public void activate() {
        logger.debug("Activating DependencyAnalyzer");
        precompiler = new TslPreCompiler(navajoIOConfig);
    }

    public List<Dependency> addDependencies(String scriptPath) {

        List<Dependency> myDependencies = new ArrayList<Dependency>();
        String script;
        if (scriptPath.indexOf('/') >= 0) {
            script = scriptPath.substring(scriptPath.lastIndexOf('/') + 1);
        } else {
            script = scriptPath;
        }

        String scriptTenant = tenantFromScriptPath(script);

        try {
            precompiler.getAllDependencies(scriptPath, navajoIOConfig.getScriptPath(),
                    navajoIOConfig.getCompiledScriptPath(), myDependencies, scriptTenant);
        } catch (Exception e) {
            logger.error("Exception in attempting to get dependencies for {}: {}", scriptPath, e);
        }

        for (Dependency dep : myDependencies) {
            if (dep instanceof NavajoDependency) {
                dependencies.put(scriptPath, myDependencies);

                NavajoDependency navajoDep = (NavajoDependency) dep;
                String navajoScript = navajoDep.getScriptPath();

                if (!reverseNavajoDependencies.containsKey(navajoScript)) {
                    reverseNavajoDependencies.put(navajoScript, new HashSet<String>());
                }
                reverseNavajoDependencies.get(navajoScript).add(scriptPath);
            } else if (dep instanceof IncludeDependency) {
                dependencies.put(scriptPath, myDependencies);

                IncludeDependency includeDep = (IncludeDependency) dep;
                String includeScript = includeDep.getScriptPath();

                if (!reverseIncludeDependencies.containsKey(includeScript)) {
                    reverseIncludeDependencies.put(includeScript, new HashSet<String>());
                }
                reverseIncludeDependencies.get(includeScript).add(scriptPath);

            }
        }
        return dependencies.get(scriptPath);

    }

    public Set<String> getDependentScripts(String scriptPath) {
        if (!reverseIncludeDependencies.containsKey(scriptPath)) {
            return new HashSet<String>();
        }
        return reverseIncludeDependencies.get(scriptPath);
    }

    public Set<String> getDependentNavajo(String scriptPath) {
        if (!reverseNavajoDependencies.containsKey(scriptPath)) {
            return new HashSet<String>();
        }
        return reverseNavajoDependencies.get(scriptPath);
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

    public void setIOConfig(NavajoIOConfig config) {
        this.navajoIOConfig = config;
    }

    public void clearIOConfig(NavajoIOConfig config) {
        this.navajoIOConfig = null;
    }

}
