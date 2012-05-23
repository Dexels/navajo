package com.dexels.navajo.jsp.server;

import java.io.File;

public interface ScriptStatus extends Comparable<ScriptStatus> {
	public boolean isCompiled();
	public boolean isDocumented();
	public File getSource();
	public File getCompiledPath();
	public boolean isByteCodeCompiled();
	public String getName();
	public String getLanguage();
	public boolean isLoaded();
	
}
