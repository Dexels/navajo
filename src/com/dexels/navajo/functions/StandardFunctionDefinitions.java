package com.dexels.navajo.functions;

import java.io.InputStream;
import java.util.List;

import navajo.ExtensionDefinition;

public class StandardFunctionDefinitions implements ExtensionDefinition {

	public InputStream getDefinitionAsStream() {
		return getClass().getClassLoader().getResourceAsStream("com/dexels/navajo/functions/functions.xml");
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
		// TODO Auto-generated method stub
		return null;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return null;
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

}
