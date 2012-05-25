package com.dexels.navajo.pdf.functions;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import com.dexels.navajo.version.ExtensionDefinition;


public class PDFFunctionDefinitions implements ExtensionDefinition {

	private static final long serialVersionUID = 2973100174588822113L;
	private ClassLoader extensionClassLoader = null;

	public InputStream getDefinitionAsStream() {
		return getClass().getClassLoader().getResourceAsStream("com/dexels/navajo/pdf/functions/functions.xml");
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
		
		return "Standard navajo function library";
	}

	public String getId() {
		return "navajofunction";
	}

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

	public String getProjectName() {
		return "NavajoFunctions";
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
