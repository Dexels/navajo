package com.dexels.navajo.util;

import java.io.File;

import com.dexels.navajo.server.DispatcherFactory;

public class Crawler {

	public static void main(String [] args) throws Exception {
		
		String basePath = args[0];
		String packageName = args[1];
		
		ScriptIntrospection.initializeDispatcher(basePath);
		
		System.err.println("Checking: " + DispatcherFactory.getInstance().getNavajoConfig().getScriptPath() + "/" + packageName);
		File parent = new File(DispatcherFactory.getInstance().getNavajoConfig().getScriptPath() + "/" + packageName);
		String [] files = parent.list();
		for ( int i = 0; i < files.length; i++ ) {
			if ( files[i].endsWith(".xml") ) {
				System.err.println("File: " + packageName + "/" + files[i]);
				ScriptIntrospection si = new ScriptIntrospection(basePath, packageName + "/" + files[i].replaceAll("\\.xml", ""));
				System.err.println("author     : " + si.getCompiledScript().getAuthor());
				System.err.println("description: " + si.getCompiledScript().getDescription());
				System.err.println("version    : " + si.getCvs().getVersion());
			}
		}
		
	}
}
