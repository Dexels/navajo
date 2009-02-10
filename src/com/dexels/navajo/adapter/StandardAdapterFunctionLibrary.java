package com.dexels.navajo.adapter;

import java.io.InputStream;

import navajo.ExtensionDefinition;

/**
 * Reference to the XML definition file for this set of adapters defined in this project.
 * 
 * @author arjen
 *
 */
public class StandardAdapterFunctionLibrary implements ExtensionDefinition {

	public InputStream getDefinitionAsStream() {
		return getClass().getClassLoader().getResourceAsStream("com/dexels/navajo/adapter/adapterfunctions.xml");
	}

}
