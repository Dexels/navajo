package com.dexels.navajo.adapter.core;

import java.io.InputStream;
import java.util.List;

import navajo.ExtensionDefinition;



public class NavajoCoreAdapterLibrary implements ExtensionDefinition {

	
	private static final long serialVersionUID = 3364296653069922647L;
	private transient ClassLoader extensionClassLoader = null;
	
	public InputStream getDefinitionAsStream() {
		return getClass().getClassLoader().getResourceAsStream("com/dexels/navajo/adapter/core/coreadapters.xml");
	}

	public String getConnectorId() {
		return null;
	}

	public List<String> getDependingProjectUrls() {
		return null;
	}

	public String getDeploymentDescriptor() {
		return null;
	}

	public String getDescription() {
		return "The Enterprise Navajo Adapter Library";
	}

	public String getId() {
		return "Navajo";
	}

	public String[] getIncludes() {
		return null;
	}

	public List<String> getLibraryJars() {
		return null;
	}

	public List<String> getMainJars() {
		return null;
	}

	public String getProjectName() {
		return "Navajo";
	}

	public List<String> getRequiredExtensions() {
		return null;
	}

	public boolean isMainImplementation() {
		return false;
	}

	public String requiresMainImplementation() {
		return null;
	}

	public ClassLoader getExtensionClassloader() {
		return extensionClassLoader;
	}

	public void setExtensionClassloader(ClassLoader extClassloader) {
		extensionClassLoader =  extClassloader;
	}

}
