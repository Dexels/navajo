package com.dexels.navajo.adapter.functions;

import java.io.InputStream;
import java.util.List;

import navajo.ExtensionDefinition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



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
	
	@Override
	public InputStream getDefinitionAsStream() {
		logger.info("Processing extension: StandardAdapterFunctionLibrary");
		return getClass().getClassLoader().getResourceAsStream("com/dexels/navajo/adapter/functions/adapterfunctions.xml");
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
		return "The Standard Navajo Function Library";
	}

	@Override
	public String getId() {
		return "NavajoFunctions";
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
		return extensionClassLoader;
	}

	@Override
	public void setExtensionClassloader(ClassLoader extClassloader) {
		extensionClassLoader =  extClassloader;
	}

}
