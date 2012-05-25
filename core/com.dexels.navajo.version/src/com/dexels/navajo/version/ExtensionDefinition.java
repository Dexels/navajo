package com.dexels.navajo.version;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

public interface ExtensionDefinition extends Serializable {

	public InputStream getDefinitionAsStream();
	/**
	 * Returns an array of all the include strings. '/' as path separator
	 * 
	 * @return
	 */
	public String[] getIncludes();

	
//	public String[] getFunctionIncludes();

	
	/**
	 * Returns the description of this extension
	 * 
	 * @return
	 */
	public String getDescription();

	/**
	 * Returns the id of this extension
	 * 
	 * @return
	 */
	public String getId();

	public boolean isMainImplementation();

	/**
	 * Returns the id for the required main application. '*' for any null for
	 * core components
	 * 
	 * @return
	 */
	public String requiresMainImplementation();
	public List<String> getRequiredExtensions();
	public String getConnectorId();
	public String getProjectName();
//	public String getDeploymentDescriptor();
	public List<String> getDependingProjectUrls();
	public ClassLoader getExtensionClassloader();
	public void setExtensionClassloader(ClassLoader extClassloader);
}
