package com.dexels.navajo.adapter.functions;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import navajo.ExtensionDefinition;



/**
 * Reference to the XML definition file for this set of adapters defined in this project.
 * 
 * @author arjen
 *
 */
public class StandardAdapterFunctionLibrary implements ExtensionDefinition {
	private static final long serialVersionUID = -2167328743333229662L;
	private transient ClassLoader extensionClassLoader = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(StandardAdapterFunctionLibrary.class);
	
	public InputStream getDefinitionAsStream() {
		logger.info("Processing extension: StandardAdapterFunctionLibrary");
		return getClass().getClassLoader().getResourceAsStream("com/dexels/navajo/adapter/functions/adapterfunctions.xml");
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
		return "The Standard Navajo Function Library";
	}

	public String getId() {
		return "NavajoFunctions";
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
