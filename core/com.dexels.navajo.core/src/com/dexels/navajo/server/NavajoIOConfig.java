package com.dexels.navajo.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.dexels.navajo.mapping.compiler.TslCompiler;

public interface NavajoIOConfig {
	public InputStream getScript(String name) throws IOException;
	public InputStream getConfig(String name) throws IOException;
    public InputStream getResourceBundle(String name) throws IOException;

    public String getConfigPath();
	public String getRootPath();
	public String getScriptPath();
	public String getCompiledScriptPath();
	public String getAdapterPath();

	void writeOutput(String scriptName, String suffix, InputStream is)
			throws IOException;

	
    @Deprecated
    public String getClassPath();

	@Deprecated
	public File getJarFolder();

}
