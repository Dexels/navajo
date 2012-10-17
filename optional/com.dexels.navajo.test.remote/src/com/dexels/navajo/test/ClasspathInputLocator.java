package com.dexels.navajo.test;

import java.io.IOException;
import java.io.InputStream;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public class ClasspathInputLocator implements ScriptInputLocator {

	public ClasspathInputLocator() {
	}

	public Navajo getInput(String scriptName) throws IOException {

		System.err.println("Getting: "+scriptName+".xml");
		InputStream is = getClass().getClassLoader().getResourceAsStream(scriptName+".xml");
		if(is!=null) {
			System.err.println("Resource found!");
		}
		Navajo result=null;
		try {
			result = NavajoFactory.getInstance().createNavajo(is);
		} catch (Exception e) {
			throw new IOException("Error loading test input: "+scriptName);
		}
		if(is!=null) {
			is.close();
		}
		return result;
	}	

}
