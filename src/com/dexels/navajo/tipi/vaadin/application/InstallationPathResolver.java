package com.dexels.navajo.tipi.vaadin.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

public class InstallationPathResolver {

	public static String getInstallationPath(ServletContext context) {
		String force = context.getInitParameter("forcedTipiPath");
		if(force!=null) {
			return force;
		} else {
			try {
				Map<String,String> systemContexts = loadSystemContexts();
				String fullContext = context.getContextPath();
				System.err.println("Context path: "+fullContext.length());
				String contextPath = fullContext.startsWith("/")?fullContext.substring(1):fullContext;
				if(contextPath.isEmpty()) {
					contextPath="~";
				}
				return getInstallationPath(systemContexts, contextPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private static Map<String,String> loadSystemContexts() throws IOException {
		File home = new File(System.getProperty("user.home"));
		File navajo = new File(home,"tipi.properties");
		Map<String,String> systemContexts = new HashMap<String, String>();
		if(!navajo.exists()) {
			return systemContexts;
		}
		BufferedReader br = new BufferedReader(new FileReader(navajo));
		
		while(true) {
			String line = br.readLine();
			if(line==null) {
				break;
			}
			String[] r = line.split("=");
			systemContexts.put(r[0], r[1]);
		}
		
		br.close();
		return systemContexts;
	}
	
	/**
	 * Only used when the path is not forced.
	 */
	private static String getInstallationPath(Map<String,String> systemContexts,String contextPath) {
		String engineInstance = System.getProperty("com.dexels.tipi.EngineInstance");
		String key = contextPath;
		if(engineInstance!=null) {
			key = contextPath+"@"+engineInstance;
		}
		String result = systemContexts.get(key);
		if(result!=null) {
			return result;
		}
		return systemContexts.get(contextPath);
	}
	
}
