package com.dexels.navajo.tipi.vaadin.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstallationPathResolver {

	private static final Logger logger = LoggerFactory.getLogger(InstallationPathResolver.class); 
	
	public static List<String> getInstallationPath(ServletContext context) throws ServletException {
		String force = context.getInitParameter("forcedTipiPath");
		if(force!=null) {
			return parseContext(force);
		} else {
			try {
				
				String fullContext = context.getContextPath();

				return getInstallationFromPath(fullContext);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}



	public static List<String> getInstallationFromPath(String fullContext) throws IOException, ServletException {
		Map<String,String> systemContexts = loadSystemContexts();
		String contextPath = fullContext.startsWith("/")?fullContext.substring(1):fullContext;
		if(contextPath.isEmpty()) {
			contextPath="~";
		}
		return getInstallationPath(systemContexts, contextPath);
	}
	
	private static Map<String,String> loadSystemContexts() throws IOException {
		File home = new File(System.getProperty("user.home"));
		File navajo = new File(home,"tipi.properties");
		Map<String,String> systemContexts = new HashMap<String, String>();
		if(!navajo.exists()) {
			logger.warn("No tipi.properties found at: "+navajo.getAbsolutePath());
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
	 * @throws ServletException 
	 */
	private static List<String> getInstallationPath(Map<String,String> systemContexts,String contextPath) throws ServletException {
		String engineInstance = System.getProperty("com.dexels.tipi.EngineInstance");
		String key = contextPath;
		// Check WITH engine instance first
		if(engineInstance!=null) {
			key = contextPath+"@"+engineInstance;
		}
		String result = systemContexts.get(key);
		if(result!=null) {
			System.err.println("Path "+contextPath+" resolved: "+result);
			return parseContext(result);
		}
		result = systemContexts.get(contextPath);
		if(result==null) {
			if(engineInstance!=null) {
				throw new ServletException("No context found at: "+contextPath+"@"+engineInstance);
			} else {
				throw new ServletException("No context found at: "+contextPath);
				
			}

		}
		return parseContext(result);
	}
	
	private static List<String> parseContext(String context) {
		StringTokenizer st = new StringTokenizer(context, "|");
		List<String> result = new LinkedList<String>();
		while (st.hasMoreTokens()) {
			String part =st.nextToken();
			result.add(part);
			
		}
		return result;
	}
}
