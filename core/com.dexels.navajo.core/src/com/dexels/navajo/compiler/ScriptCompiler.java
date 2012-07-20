package com.dexels.navajo.compiler;

/**
 * Interface for TSL compiling service
 * @author frank
 *
 */
public interface ScriptCompiler {

	public abstract void compileTsl(String script) throws Exception;

}