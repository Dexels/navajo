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
import com.dexels.navajo.script.api.Dependency;
import com.dexels.navajo.server.NavajoIOConfig;

public class DependencyAnalyzer {
	private TslPreCompiler precompiler;
	private NavajoIOConfig navajoIOConfig = null;
	private Map<String, List<Dependency>> scriptDependencies = new HashMap<String, List<Dependency>>();
	private Map<String, Set<String>> reverseIncludeDependencies =  new HashMap<String, Set<String>>();

	private final static Logger logger = LoggerFactory.getLogger(DependencyAnalyzer.class);

	public void activate() {
		logger.debug("Activating DependencyAnalyzer");
		precompiler = new TslPreCompiler(navajoIOConfig);
	}

	public List<Dependency> getIncludeDependencies(String scriptPath) {
		List<Dependency> dependencies = new ArrayList<Dependency>();
		String script;
		if (scriptPath.indexOf('/') >= 0) {
			script = scriptPath.substring(scriptPath.lastIndexOf('/') + 1);
		} else {
			script = scriptPath;
		}

		String scriptTenant = tenantFromScriptPath(script);

		try {
			precompiler.getIncludeDependencies(scriptPath, navajoIOConfig.getScriptPath(),
					navajoIOConfig.getCompiledScriptPath(), dependencies, scriptTenant);
		} catch (Exception e) {
			logger.error("Exception in attempting to get dependencies for {}: {}", scriptPath, e);
		}
		scriptDependencies.put(scriptPath, dependencies);
		
		

		//List<String> currentDeps = reverseIncludeDependencies.get(scriptPath);
		for (Dependency dep : dependencies) {
			if (dep instanceof IncludeDependency) {
				IncludeDependency includeDep = (IncludeDependency) dep;
				String includeScript = includeDep.getScriptPath();
				
				if (!reverseIncludeDependencies.containsKey(includeScript)) {
					reverseIncludeDependencies.put(includeScript, new HashSet<String>());
				}
				reverseIncludeDependencies.get(includeScript).add(scriptPath);
			}
		}

		return dependencies;
	}
	
	public Set<String> getDependentScripts(String scriptPath) {
		if (!reverseIncludeDependencies.containsKey(scriptPath)) {
			return new HashSet<String>();
		}
		return reverseIncludeDependencies.get(scriptPath);
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
