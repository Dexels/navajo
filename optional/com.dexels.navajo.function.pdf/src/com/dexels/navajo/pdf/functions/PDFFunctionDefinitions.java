package com.dexels.navajo.pdf.functions;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import navajo.ExtensionDefinition;



public class PDFFunctionDefinitions implements ExtensionDefinition {

	private static final long serialVersionUID = 2973100174588822113L;
	private ClassLoader extensionClassLoader = null;

	@Override
	public InputStream getDefinitionAsStream() {
		return getClass().getClassLoader().getResourceAsStream("com/dexels/navajo/pdf/functions/functions.xml");
	}

	@Override
	public String getConnectorId() {
		return null;
	}

	@Override
	public List<String> getDependingProjectUrls() {
		// list urls to open source projects here
		return null;
	}

	public String getDeploymentDescriptor() {
		return null;
	}

	@Override
	public String getDescription() {
		
		return "Standard navajo function library";
	}

	@Override
	public String getId() {
		return "navajofunction";
	}

	@Override
	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/functions/functions.xml"};
	}

	public List<String> getLibraryJars() {
		return null;
	}

	public List<String> getMainJars() {
		List<String> jars = new LinkedList<String>();
		jars.add("NavajoFunctions.jar");
		return jars;
	}

	@Override
	public String getProjectName() {
		return "NavajoFunctions";
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
		// any will do
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
