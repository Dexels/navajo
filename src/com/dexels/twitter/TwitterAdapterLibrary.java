package com.dexels.twitter;

import java.io.InputStream;
import java.util.List;

import navajo.ExtensionDefinition;

/**
 * Reference to the XML definition file for this set of adapters defined in this project.
 * 
 * @author arjen
 *
 */
public class TwitterAdapterLibrary implements ExtensionDefinition {

	public InputStream getDefinitionAsStream() {
		return getClass().getClassLoader().getResourceAsStream("com/dexels/twitter/adapter.xml");
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
		return "The Twitter Adapter Library";
	}

	public String getId() {
		return "TwitterAdapter";
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
