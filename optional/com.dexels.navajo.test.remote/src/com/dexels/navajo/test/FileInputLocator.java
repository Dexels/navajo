package com.dexels.navajo.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public class FileInputLocator implements ScriptInputLocator {

	private final File baseDir;
	public FileInputLocator(File baseDir) {
		this.baseDir = baseDir;
	}

	@Override
	public Navajo getInput(String scriptName) throws IOException {
		System.err.println("Getting: "+scriptName);
		File inputFile = new File(baseDir,scriptName+".xml");
		System.err.println("Resolved: "+inputFile);
		FileInputStream is = new FileInputStream(inputFile);
		Navajo result = NavajoFactory.getInstance().createNavajo(is);
		is.close();
		return result;
	}	

}
