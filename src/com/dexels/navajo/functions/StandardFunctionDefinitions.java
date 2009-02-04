package com.dexels.navajo.functions;

import java.io.InputStream;

import navajo.functions.FunctionDefinitions;

public class StandardFunctionDefinitions implements FunctionDefinitions {

	public InputStream getFunctionDefinitions() {
		return getClass().getClassLoader().getResourceAsStream("com/dexels/navajo/functions/functions.xml");
	}

}
