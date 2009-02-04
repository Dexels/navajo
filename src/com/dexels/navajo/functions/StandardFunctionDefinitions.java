package com.dexels.navajo.functions;

import java.io.InputStream;

import navajo.ExtensionDefinition;

public class StandardFunctionDefinitions implements ExtensionDefinition {

	public InputStream getDefinitionAsStream() {
		return getClass().getClassLoader().getResourceAsStream("com/dexels/navajo/functions/functions.xml");
	}

}
