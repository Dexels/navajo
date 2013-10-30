package com.dexels.navajo.adapter.core;

import java.io.InputStream;
import java.util.List;

import navajo.ExtensionDefinition;



public class NavajoCoreAdapterLibrary implements ExtensionDefinition {

	
	private static final long serialVersionUID = 3364296653069922647L;
	private transient ClassLoader extensionClassLoader = null;
	
	@Override
	public InputStream getDefinitionAsStream() {
		return getClass().getResourceAsStream("coreadapters.xml");
	}

	@Override
	public String getConnectorId() {
		return null;
	}

	@Override
	public List<String> getDependingProjectUrls() {
		return null;
	}

	public String getDeploymentDescriptor() {
		return null;
	}

	@Override
	public String getDescription() {
		return "The Enterprise Navajo Adapter Library";
	}

	@Override
	public String getId() {
		return "Navajo";
	}

	@Override
	public String[] getIncludes() {
		return null;
	}

	public List<String> getLibraryJars() {
		return null;
	}

	public List<String> getMainJars() {
		return null;
	}

	@Override
	public String getProjectName() {
		return "Navajo";
	}

	@Override
	public List<String> getRequiredExtensions() {
		return null;
	}

	@Override
	public boolean isMainImplementation() {
		return false;
	}

	@Override
	public String requiresMainImplementation() {
		return null;
	}

	@Override
	public ClassLoader getExtensionClassloader() {
		return extensionClassLoader;
	}

	@Override
	public void setExtensionClassloader(ClassLoader extClassloader) {
		extensionClassLoader =  extClassloader;
	}

}
