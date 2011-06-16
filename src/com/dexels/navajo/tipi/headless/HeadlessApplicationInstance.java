package com.dexels.navajo.tipi.headless;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import tipi.TipiCoreExtension;
import tipi.TipiExtension;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiScreen;
import com.dexels.navajo.tipi.internal.FileResourceLoader;


public class HeadlessApplicationInstance {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
//		Map<String, String> properties = parseProperties(args);
//		initialize("init", "init.xml", properties);
		initialize("init",new File("."), new TipiCoreExtension());
		Thread.sleep(2000);
	}
	
	public static  TipiContext initialize(String definition, File tipiDir, TipiExtension ed) throws Exception {
		return initialize(definition, definition+".xml", tipiDir, parseProperties(new String[]{}),ed);
	}
	
	public static  TipiContext initialize(String definition, File tipiDir,String[] args, TipiExtension ed) throws Exception {
		return initialize(definition, definition+".xml", tipiDir, parseProperties(args),ed);
	}
	
	public static  TipiContext initialize(String definition,String definitionPath, File tipiDir,Map<String, String> properties, TipiExtension ed) throws Exception {
		if(definitionPath==null) {
			definitionPath = definition;
		}
		TipiContext context = null;
//		System.setProperty("com.dexels.navajo.tipi.maxthreads","0");
		context = new HeadlessTipiContext();
		FileResourceLoader frl = new FileResourceLoader(tipiDir);
		context.setTipiResourceLoader(frl);
		context.setDefaultTopLevel(new TipiScreen(context));
		context.processProperties(properties);
		ed.initialize(context);
		InputStream tipiResourceStream = context.getTipiResourceStream(definitionPath);
		if(tipiResourceStream==null) {
			System.err.println("Error starting up: Can not load tipi");
		} else {
			context.parseStream(tipiResourceStream, definition, false,ed);
			context.switchToDefinition(definition);
		}
		return context;
	}

	public static Map<String,String> parseProperties(String gsargs) {
		StringTokenizer st = new StringTokenizer(gsargs);
		ArrayList<String> a = new ArrayList<String>();
		while(st.hasMoreTokens()) {
			String next = st.nextToken();
			a.add(next);
		}
		return parseProperties(a);
	}

	public static Map<String,String> parseProperties(String[] args) {
		List<String> st = new ArrayList<String>();
		for (int i = 0; i < args.length; i++) {
			st.add(args[i]);
		}
		return parseProperties(st);
	}	
	
	public static Map<String,String> parseProperties(List<String> args) {
		Map<String,String> result = new HashMap<String,String>();
		for (String current : args) {
			if (current.startsWith("-D")) {
				String prop = current.substring(2);
				try {
					StringTokenizer st = new StringTokenizer(prop, "=");
					String name = st.nextToken();
					String value = st.nextToken();
					result.put(name, value);
				} catch (NoSuchElementException ex) {
					System.err.println("Error parsing system property");
				} catch (SecurityException se) {
					System.err.println("Security exception: " + se.getMessage());
					se.printStackTrace();
				}
			}			
		}

		return result;
	}
}
