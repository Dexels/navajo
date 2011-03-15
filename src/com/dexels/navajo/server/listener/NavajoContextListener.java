package com.dexels.navajo.server.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.DispatcherInterface;

public class NavajoContextListener implements ServletContextListener {

	
	
	public static final String DOC_IMPL = "com.dexels.navajo.DocumentImplementation";
	public static final String NANO = "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl";
	public static final String JAXP = "com.dexels.navajo.document.jaxpimpl.NavajoFactoryImpl";
	public static final String QDSAX = "com.dexels.navajo.document.base.BaseNavajoFactoryImpl";

	public static final String DEFAULT_SERVER_XML = "config/server.xml";

	
	@Override
	public void contextDestroyed(ServletContextEvent sc) {
		dexels.Version.shutdownNavajoExtension("navajo");
		dexels.Version.shutdownNavajoExtension("navajodocument");
		dexels.Version.shutdownNavajoExtension("navajoclient");
		dexels.Version.shutdownNavajoExtension("navajoadapters");
		dexels.Version.shutdownNavajoExtension("navajolisteners");
		dexels.Version.shutdownNavajoExtension("navajoenterpriseadapters");
		dexels.Version.shutdownNavajoExtension("navajoenterprise");
		dexels.Version.shutdownNavajoExtension("navajoenterpriselisteners");
		dexels.Version.shutdownNavajoExtension("navajoqueuemanager");
	}

	@Override
	public void contextInitialized(ServletContextEvent sc) {

		init(sc.getServletContext());
	}

	public void init(ServletContext sc) {
		System.err.println("==========================================================");
		System.err.println("INITIALIZING NAVAJO INSTANCE: " +  sc.getContextPath());
		System.err.println("==========================================================");
		
		if(!isValidInstallationForContext(sc)) {
			System.err.println("No valid installation found, abandoning Context initialization.");
			return;
		}

		initializeContext(sc,null);
	}

	/**
	 * Initialized a Navajo context.
	 * @param sc
	 * @param force Use this to force the path
	 */
	
	// Should be called after installation, so the context will still be initialized.
	public static void initializeContext(ServletContext sc, String force) {
		String configurationPath = null;
		String rootPath = null;

		String path;
		path = getInstallationPath(sc,force);
		if (path != null) {
			configurationPath = path;
		}
		// Check whether defined bootstrap webservice is present.
		System.setProperty(DOC_IMPL, QDSAX);
//		System.err.println("Configuration path: " + configurationPath);

		boolean verified = false;

		URL configUrl;
		InputStream is = null;
		try {
			configUrl = new URL(configurationPath);
			is = configUrl.openStream();
			verified = true;
		} catch (MalformedURLException e) {
			// e.printStackTrace(System.err);
		} catch (IOException e) {
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (configurationPath == null || "".equals(configurationPath)
				|| !verified) {
			rootPath = sc.getRealPath("");
		}
//		System.err.println("Resolved Configuration path: " + configurationPath);
//		System.err.println("Resolved Root path: " + rootPath);

		// Startup Navajo instance.
		try {
			initDispatcher(sc, rootPath, configurationPath);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	protected final static DispatcherInterface initDispatcher(ServletContext sc, String rootPath, String configurationPath) throws NavajoException {

		String servletContextRootPath = sc.getRealPath("");
		System.err.println("Context root path: "+servletContextRootPath);
		if (configurationPath!=null) {
			// Old SKOOL. Path provided, notify the dispatcher by passing a null DEFAULT_SERVER_XML
				return DispatcherFactory.getInstance(new File(configurationPath), DEFAULT_SERVER_XML, new com.dexels.navajo.server.FileInputStreamReader(),servletContextRootPath);
		} else {
			return DispatcherFactory.getInstance(rootPath, DEFAULT_SERVER_XML, new com.dexels.navajo.server.FileInputStreamReader(),servletContextRootPath);
		}

	}
		
//	private String setupConfigurationPath(ServletContext context) throws IOException {
//		String navajoPath = getSystemPath(context);
//		return navajoPath;
//	}
		
//	private String getSystemPath(ServletContext context) throws IOException {
//		String name =  context.getContextPath().substring(1);
//
//		String force = context.getInitParameter("forcedNavajoPath");
//		System.err.println("Force: "+force);
//		if(force!=null) {
////			System.err.println("Using the force! navajo.properties will be ignored!");
//			return force;
//		}
//		Map<String,String> systemContexts = new HashMap<String, String>();
//		File home = new File(System.getProperty("user.home"));
//		File navajo = new File(home,"navajo.properties");
//		System.err.println("Assuming navajo path: "+navajo.getAbsolutePath());
//		if(!navajo.exists()) {
//			return null;
//		}
//		
//		BufferedReader br = new BufferedReader(new FileReader(navajo));
//		while(true) {
//			String line = br.readLine();
//			if(line==null) {
//				break;
//			}
//			String[] r = line.split("=");
//			systemContexts.put(r[0], r[1]);
//		}
//		br.close();
//		System.err.println("Maps: "+systemContexts);
//		// So the path is not forced, and the navajo.properties file exists.
//		// check the com.dexels.navajo.server.EngineInstance property
//		
//		String engineInstance = System.getProperty("com.dexels.navajo.server.EngineInstance");
//		if(engineInstance!=null) {
//			String engineQualifiedContext = systemContexts.get(engineInstance+"/"+name);
//			if(engineQualifiedContext==null) {
//				System.err.println("Warning: com.dexels.navajo.server.EngineInstance set (to: "+engineInstance+"), but no such context found.");
//				System.err.println("Available contexts: "+systemContexts);
//				// ignore engineInstance
//			} else {
//				return engineQualifiedContext;
//			}
//			
//		}
//		return systemContexts.get(name);
//	}
//
//	
	
	public static boolean isValidInstallationForContext(ServletContext context) {
		String installPath = getInstallationPath(context,null);

		if(installPath==null) {
			return false;
		}
		File f = new File(installPath);
		if(!f.exists()) {
			return false;
		}
		File scripts = new File(f,"scripts");
		if(!scripts.exists()) {
			return false;
		}
		File config = new File(f,"config");
		if(!config.exists()) {
			return false;
		}
		return config!=null;
	}

	
	public static String getInstallationPath(ServletContext context, String force) {
		if(force==null) {
			force = context.getInitParameter("forcedNavajoPath");
		}
		System.err.println("Force: "+force);
		if(force!=null) {
			return force;
		} else {
			try {
				String contextPath = context.getContextPath().substring(1);
				Map<String,String> systemContexts = loadSystemContexts();
				return getInstallationPath(systemContexts, contextPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
//	private String initialize(String contextPath) throws IOException {
//		Map<String,String> systemContexts = loadSystemContexts();
//		return getInstallationPath(systemContexts,contextPath);
//	}

	/**
	 * Only used when the path is not forced.
	 */
	private static String getInstallationPath(Map<String,String> systemContexts,String contextPath) {
		String engineInstance = System.getProperty("com.dexels.navajo.server.EngineInstance");
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
	
	private static Map<String,String> loadSystemContexts() throws IOException {
		File home = new File(System.getProperty("user.home"));
		File navajo = new File(home,"navajo.properties");
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
	

}
