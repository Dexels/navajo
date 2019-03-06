package com.dexels.navajo.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.script.ScriptEngineManager;

import navajocore.Version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.jmx.JMXHelper;

public class DispatcherFactory {

	private static DispatcherInterface instance;
	private static Object semaphore = new Object();
	private static ScriptEngineManager scriptEngineFactory = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(DispatcherFactory.class);
	
  
	public DispatcherFactory(DispatcherInterface injectedDispatcher) {	
		instance = injectedDispatcher;
	}
	
	public static DispatcherInterface getInstance() {
		return instance;
	}
	public static synchronized  void setInstance(DispatcherInterface dispatcher) {
		instance = dispatcher;
	}
	
	/**
	 * Only use this getInstance to get a reference to the real Dispatcher.
	 * 
	 * @param rootPath
	 * @param serverXmlPath
	 * @param fileInputStreamReader
	 * @return
	 * @throws NavajoException
	 */
	public static DispatcherInterface getInstance(File rootPath, String serverXmlPath, String servletContextRootPath) throws NavajoException {
		if (instance != null) {
			return instance;
		}
		URL configurationUrl;
		String absRootPath = rootPath.getAbsolutePath();
		if(!absRootPath.endsWith("/")) {
			absRootPath = absRootPath+ "/";
		}
		try {
			File serverXMLFile = new File(rootPath,serverXmlPath);
			configurationUrl = serverXMLFile.toURI().toURL();
		} catch (MalformedURLException e) {
			throw NavajoFactory.getInstance().createNavajoException(e);
		}

		
		createInstance(absRootPath, configurationUrl,servletContextRootPath);
		return instance;
	}
	
	public static void shutdown() {
		instance = null;
		scriptEngineFactory = null;
//		NavajoConfig.terminate();
	}
	
	public static synchronized ScriptEngineManager getScriptEngineManager() {
		synchronized (DispatcherFactory.class) {
			if(scriptEngineFactory==null) {
				scriptEngineFactory = new javax.script.ScriptEngineManager();
			}
			return scriptEngineFactory;
		}
	}
	
	/**
	 * Only use this getInstance to get a reference to the real Dispatcher.
	 * 
	 * @param rootPath
	 * @param serverXmlPath
	 * @param fileInputStreamReader
	 * @return
	 * @throws NavajoException
	 */
	public static DispatcherInterface getInstance(String rootPath, String serverXmlPath, String servletContextRootPath) throws NavajoException {
		logger.warn("GETINSTANCE1");
		if (instance != null) {
			return instance;
		}
		if(!rootPath.endsWith("/")) {
			rootPath = rootPath+"/";
		}

		URL configurationUrl;
		if(serverXmlPath==null) {
			// old skool, the passed url is the configurationUrl (from web.xml):
			try {
				configurationUrl = new URL(rootPath);
				rootPath = null;
			} catch (MalformedURLException e) {
				throw NavajoFactory.getInstance().createNavajoException(e);
			}
		} else {
			// new-style: rootUrl is the root of the installation, serverXmlPath is the relative path to the server.xml file
			try {
				File f = new File(rootPath);
				URL rootUrl = f.toURI().toURL();
				configurationUrl = new URL(rootUrl, serverXmlPath);
			} catch (MalformedURLException e) {
				throw NavajoFactory.getInstance().createNavajoException(e);
			}

		}
		
		if(rootPath != null && !rootPath.endsWith("/")) {
			rootPath = rootPath+ "/";
		}

		createInstance(rootPath, configurationUrl,servletContextRootPath);

		return instance;
	}

	private static void createInstance(String rootPath, URL configurationUrl,String servletContextRootPath)
			throws NavajoException {
		logger.warn("CREATE INSTANCE2");
		synchronized (semaphore) {
			if(Version.osgiActive()) {
				logger.info("Using osgi");
			}
			if (instance == null) {
				
				// Create NavajoConfig object.
				 InputStream is = null; 
				 NavajoConfigInterface nc = null;
				  try {
					  // Read configuration file.
					  is = configurationUrl.openStream();
					  nc = new NavajoConfig( null, is, rootPath,servletContextRootPath); 
//					  navajocore.Version.registerNavajoConfig(nc);
//					  createNavajoConfigConfiguration(configurationUrl);
				  }
				  catch (Exception se) {
					  throw NavajoFactory.getInstance().createNavajoException(se);
				  } finally {
					  if ( is != null ) {
						  try {
							is.close();
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					  }
				  }
				  
				instance = new Dispatcher(nc);
//				navajocore.Version.registerDispatcher(instance);
				((Dispatcher) instance).init();
				JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, "Dispatcher");
				NavajoFactory.getInstance().setTempDir(instance.getTempDir());
				
			}
		}
	}


}
