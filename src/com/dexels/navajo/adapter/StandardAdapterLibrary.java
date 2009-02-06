package com.dexels.navajo.adapter;

import java.io.InputStream;

import navajo.ExtensionDefinition;

public class StandardAdapterLibrary implements ExtensionDefinition {

	public InputStream getDefinitionAsStream() {
		return getClass().getClassLoader().getResourceAsStream("com/dexels/navajo/adapter/adapters.xml");
	}

}
