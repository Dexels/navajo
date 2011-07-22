package com.dexels.navajo.adapter;

import java.io.InputStream;
import java.util.List;

import navajo.ExtensionDefinition;

/**
 * Reference to the XML definition file for this set of adapters defined in this project.
 * 
 * @author arjen
 *
 */
public class StandardAdapterLibrary implements ExtensionDefinition {

	
	private static final long serialVersionUID = 5195100848450458590L;
	private transient ClassLoader extensionClassLoader = null;
	
	public InputStream getDefinitionAsStream() {
		return getClass().getClassLoader().getResourceAsStream("com/dexels/navajo/adapter/adapters.xml");
	}

	public String getConnectorId() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getDependingProjectUrls() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDeploymentDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDescription() {
		return "The Standard Navajo Adapter Library";
	}

	public String getId() {
		return "NavajoAdapters";
	}

	public String[] getIncludes() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getLibraryJars() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getMainJars() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProjectName() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getRequiredExtensions() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isMainImplementation() {
		// TODO Auto-generated method stub
		return false;
	}

	public String requiresMainImplementation() {
		// TODO Auto-generated method stub
		return null;
	}

	public ClassLoader getExtensionClassloader() {
		// TODO Auto-generated method stub
		return extensionClassLoader;
	}

	public void setExtensionClassloader(ClassLoader extClassloader) {
		// TODO Auto-generated method stub
		extensionClassLoader =  extClassloader;
	}

}
