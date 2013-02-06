package com.dexels.navajo.adapters.svg;

import java.io.InputStream;
import java.util.List;

import navajo.ExtensionDefinition;



/**
 * Reference to the XML definition file for this set of adapters defined in this project.
 * 
 * @author arjen
 *
 */
public class SvgAdapterLibrary implements ExtensionDefinition {

	
	private static final long serialVersionUID = 5195100848450458590L;
	private transient ClassLoader extensionClassLoader = null;
	
	public InputStream getDefinitionAsStream() {
		return getClass().getResourceAsStream("svgadapterdef.xml");
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
		return "The Svg emitter Adapter Library";
	}

	public String getId() {
		return "SvgEmitterAdapters";
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
		return null;
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
