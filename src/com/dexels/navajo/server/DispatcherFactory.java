package com.dexels.navajo.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.jmx.JMXHelper;

public class DispatcherFactory {

	private static volatile DispatcherInterface instance;
	private static Object semaphore = new Object();
	
	public DispatcherFactory() {	
	}
	
	public DispatcherFactory(DispatcherInterface injectedDispatcher) {	
		instance = injectedDispatcher;
	}
	
	public static DispatcherInterface getInstance() {
		return instance;
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
	public static DispatcherInterface getInstance(String rootPath, String serverXmlPath, InputStreamReader fileInputStreamReader) throws NavajoException {

		if (instance != null) {
			return instance;
		}
		if(!rootPath.endsWith("/")) {
			rootPath = rootPath+"/";
		}

		URL configurationUrl;
		if(serverXmlPath==null) {
			System.err.println("Old skool configuration detected.");
			// old skool, the passed url is the configurationUrl (from web.xml):
			try {
				configurationUrl = new URL(rootPath);
				rootPath = null;
			} catch (MalformedURLException e) {
				throw NavajoFactory.getInstance().createNavajoException(e);
			}
		} else {
			// new-style: rootUrl is the root of the installation, serverXmlPath is the relative path to the server.xml file
			System.err.println("Newskool configuration detected.");
			try {
				File f = new File(rootPath);
				URL rootUrl = f.toURI().toURL();
				configurationUrl = new URL(rootUrl, serverXmlPath);
			} catch (MalformedURLException e) {
				throw NavajoFactory.getInstance().createNavajoException(e);
			}

		}

		synchronized (semaphore) {
			if (instance == null) {
				
				// Create NavajoConfig object.
				 InputStream is = null; 
				 NavajoConfig nc = null;
				  try {
					  // Read configuration file.
					  is = configurationUrl.openStream();
					  nc = new NavajoConfig(fileInputStreamReader, null, is, rootPath); 
				  }
				  catch (Exception se) {
					  se.printStackTrace(System.err);
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
				((Dispatcher) instance).init();
				JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, "Dispatcher");
				NavajoFactory.getInstance().setTempDir(instance.getTempDir());
				
			}
		}

		return instance;
	}
	
}
