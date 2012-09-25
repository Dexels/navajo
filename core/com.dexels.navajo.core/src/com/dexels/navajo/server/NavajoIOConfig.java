package com.dexels.navajo.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Date;

import com.dexels.navajo.document.Navajo;

public interface NavajoIOConfig {
	
	public File getContextRoot();

	public InputStream getScript(String name) throws IOException;
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

	public Navajo readConfig(String s) throws IOException;
	public void writeConfig(String name, Navajo conf) throws IOException;

	
	void writeOutput(String scriptName, String suffix, InputStream is)
			throws IOException;
	
	/**
	 * Modification date of a script, returns null when missing
	 * @param scriptPath
	 * @return
	 */
	public Date getScriptModificationDate(String scriptPath);

//	public Date getBundleModificationDate(String scriptPath) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
    @Deprecated
    public String getClassPath();

	@Deprecated
	public File getJarFolder();

}
