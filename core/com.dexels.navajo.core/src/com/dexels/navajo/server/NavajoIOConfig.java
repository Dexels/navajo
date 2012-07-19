package com.dexels.navajo.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface NavajoIOConfig {
	public InputStream getScript(String name) throws IOException;
	public InputStream getConfig(String name) throws IOException;
    public InputStream getResourceBundle(String name) throws IOException;

    public String getConfigPath();
	public String getRootPath();
	public String getScriptPath();
	public String getCompiledScriptPath();
	public String getAdapterPath();

    @Deprecated
    public String getClassPath();

	@Deprecated
	public File getJarFolder();

}
