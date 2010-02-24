package com.dexels.navajo.jsp.server;

import java.io.File;

public interface ScriptStatus {
	public boolean isCompiled();
	public boolean isDocumented();
	public File getSource();
	public File getCompiled();
	public boolean isByteCodeCompiled();
	public String getName();
	public String getLanguage();
	public boolean isLoaded();
	
}
