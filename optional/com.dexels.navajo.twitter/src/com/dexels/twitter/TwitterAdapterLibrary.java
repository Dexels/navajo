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

	private static final long serialVersionUID = 3696843876065384135L;

	@Override
	public InputStream getDefinitionAsStream() {
		return getClass().getClassLoader().getResourceAsStream("com/dexels/twitter/adapter.xml");
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
		return "The Twitter Adapter Library";
	}

	@Override
	public String getId() {
		return "TwitterAdapter";
	}

	@Override
	public String[] getIncludes() {
		// TODO Auto-generated method stub
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
		return null;
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
		return null;
	}

	@Override
	public void setExtensionClassloader(ClassLoader extClassloader) {
		
	}

}
