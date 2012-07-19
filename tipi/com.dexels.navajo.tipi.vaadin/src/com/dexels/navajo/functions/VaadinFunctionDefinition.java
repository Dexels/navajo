package com.dexels.navajo.functions;

import java.io.InputStream;
import java.util.List;

import navajo.ExtensionDefinition;



public class VaadinFunctionDefinition implements ExtensionDefinition {

	private static final long serialVersionUID = -3429274998043371128L;
	private transient ClassLoader extensionClassLoader = null;

	public InputStream getDefinitionAsStream() {
		return getClass().getClassLoader().getResourceAsStream("com/dexels/navajo/functions/vaadinfunctions.xml");
	}

	public String getConnectorId() {
		return null;
	}

	public List<String> getDependingProjectUrls() {
		// list urls to open source projects here
		return null;
	}

	public String getDeploymentDescriptor() {
		return null;
	}

	public String getDescription() {
		
		return "Vaadin navajo function library";
	}

	public String getId() {
		return "tipivaadin";
	}

	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/functions/vaadinfunctions.xml"};
	}

	public List<String> getLibraryJars() {
		return null;
	}


	public String getProjectName() {
		return "com.dexels.navajo.tipi.vaadin";
	}

	public List<String> getRequiredExtensions() {
		return null;
	}

	public boolean isMainImplementation() {
		return false;
	}

	public String requiresMainImplementation() {
		// any will do
		return null;
	}
	public ClassLoader getExtensionClassloader() {
		return extensionClassLoader;
	}

	public void setExtensionClassloader(ClassLoader extClassloader) {
		extensionClassLoader =  extClassloader;
	}



}
