package com.dexels.navajo.compiler;


/**
 * Interface for TSL compiling service
 * @author frank
 *
 */
public interface ScriptCompiler {

	void compileTsl(String scriptPath, String compileDate) throws Exception;

}