package com.dexels.navajo.compiler;

import java.util.List;

import com.dexels.navajo.script.api.Dependency;


/**
 * Interface for TSL compiling service
 * @author frank
 *
 */
public interface ScriptCompiler {

	public void compileTsl(String scriptPath, List<Dependency> dependencies, String tenant,
			boolean hasTenantSpecificFile, boolean forceTenant) throws Exception;

}