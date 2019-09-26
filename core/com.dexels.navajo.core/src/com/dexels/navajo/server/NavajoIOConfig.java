package com.dexels.navajo.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Date;

import com.dexels.navajo.document.Navajo;

public interface NavajoIOConfig {
	
	public File getContextRoot();

	@Deprecated
	public InputStream getScript(String name) throws IOException;
	
	@Deprecated
	public InputStream getScript(String name, String tenant,String extension) throws IOException;
	
	public InputStream getConfig(String name) throws IOException;
    public InputStream getResourceBundle(String name) throws IOException;
    public Writer getOutputWriter(String outputPath, String scriptPackage, String scriptName, String extension) throws IOException;
    public Reader getOutputReader(String outputPath, String scriptPackage, String scriptName, String extension) throws IOException;
    public String getConfigPath();
	public String getRootPath();
	public String getScriptPath();
	public String getCompiledScriptPath();
	public String getAdapterPath();
	public String getResourcePath();
	public String getDeployment();

	public Navajo readConfig(String s) throws IOException;
	public void writeConfig(String name, Navajo conf) throws IOException;

	
	void writeOutput(String scriptName, String suffix, InputStream is)
			throws IOException;
	

	/**
	 * Name does not include tenant suffix
	 */
	public boolean hasTenantScriptFile(String rpcName, String tenant, String scriptPath);
    

}
