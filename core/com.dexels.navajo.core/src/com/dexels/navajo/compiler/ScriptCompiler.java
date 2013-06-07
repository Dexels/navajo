package com.dexels.navajo.compiler;

import java.util.List;

import com.dexels.navajo.mapping.compiler.meta.Dependency;


/**
 * Interface for TSL compiling service
 * @author frank
 *
 */
public interface ScriptCompiler {

	public void compileTsl(String scriptPath, String compileDate, List<Dependency> dependencies, String tenant, boolean hasTenantSpecificFile) throws Exception;

}