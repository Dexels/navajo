package com.dexels.navajo.functions;

import java.util.*;
import java.net.*;
import java.io.*;

import com.dexels.navajo.parser.FunctionInterface;

public class GenerateAPI {

	public GenerateAPI() {
	}

	private static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<java.io.File> dirs = new ArrayList<java.io.File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new java.io.File(resource.getFile()));
		}
		ArrayList<Class> classes = new ArrayList<Class>();
		for (java.io.File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}

	/**
	 * Recursive method used to find all classes in a given directory and subdirs.
	 * 
	 * @param directory
	 *          The base directory
	 * @param packageName
	 *          The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	private static List<Class> findClasses(java.io.File directory, String packageName) throws ClassNotFoundException {
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		java.io.File[] files = directory.listFiles();
		for (java.io.File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}
	
	public void readClasses() {
		try{
			Class[] classes = getClasses("com.dexels.navajo.functions");
			
			// Generate WIKI list
			for(int j=0;j<classes.length;j++){
				Class a = classes[j];
				Object instance = null;
				try{
					instance = a.newInstance();
				}catch(InstantiationException ie){
//					System.out.println("Could not instantiate: " + a.getName());
				}
				if(instance != null && FunctionInterface.class.isInstance(instance)){
					
		     	FunctionInterface fie = (FunctionInterface)instance;
				  String name = a.getName();
				  if(name.indexOf(".") > 0){
				  	name = name.substring(name.lastIndexOf(".")+1);
				  }
				  
				  System.err.println("  * [[doc:functions#"+name+"|"+name+"]] " + fie.remarks());
				}
			}
			System.err.println("\n\n");
			
			// Generate WIKI details
			
			for(int i=0;i<classes.length;i++){
				Class a = classes[i];
				Object instance = null;
				try{
					instance = a.newInstance();
				}catch(InstantiationException ie){
//					System.out.println("Could not instantiate: " + a.getName());
				}
				if(instance != null && FunctionInterface.class.isInstance(instance)){
					
		     	FunctionInterface fie = (FunctionInterface)instance;
				  String name = a.getName();
				  if(name.indexOf(".") > 0){
				  	name = name.substring(name.lastIndexOf(".")+1);
				  }
				 
					System.err.println("===== " + name + " =====");
					System.err.println("**Description:** " + fie.remarks() + "\n");
					System.err.println("**Usage:** " + fie.usage() + "\n");
					System.err.println("**Example:**\n<code>[example]</code>\n");
					
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
	}

	public static void main(String[] args) {
		GenerateAPI api = new GenerateAPI();
		try{
			api.readClasses();	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
