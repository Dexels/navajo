package com.dexels.navajo.server.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.server.Dispatcher;
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
		Dispatcher.killMe();
	}

	@Override
	public void contextInitialized(ServletContextEvent sc) {

		System.err.println("==========================================================");
		System.err.println("INITIALIZING NAVAJO INSTANCE: " +  sc.getServletContext().getContextPath());
		System.err.println("==========================================================");
		String configurationPath = null;
		String rootPath = null;
				String path;
				try {
					path = setupConfigurationPath(sc.getServletContext());
				} catch (IOException e) {
					path = null;
					e.printStackTrace();
				}
				if(path!=null) {
					System.err.println("Path found. Using: "+path);
					File f = new File(path);
					configurationPath = path;
				}
			// Check whether defined bootstrap webservice is present.
			System.setProperty(DOC_IMPL,QDSAX);
			System.err.println("Configuration path: "+configurationPath);

			boolean verified = false;

			URL configUrl;
			InputStream is = null;
			try {
				configUrl = new URL(configurationPath);
				is = configUrl.openStream();
				verified = true;
			} catch (MalformedURLException e) {
				//e.printStackTrace(System.err);
			} catch (IOException e) {
			} finally {
				if(is!=null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if( configurationPath==null || "".equals(configurationPath)|| !verified) {
				rootPath = sc.getServletContext().getRealPath("");
			}
			System.err.println("Resolved Configuration path: "+configurationPath);
			System.err.println("Resolved Root path: "+rootPath);

			// Startup Navajo instance.
			try {
				DispatcherInterface d = initDispatcher(sc.getServletContext(),rootPath,configurationPath);
//				Navajo n = NavajoFactory.getInstance().createNavajo();

			} catch (Exception e) {
				e.printStackTrace(System.err);
			}

		
		}
	
	protected final DispatcherInterface initDispatcher(ServletContext sc, String rootPath, String configurationPath) throws NavajoException {

		String servletContextRootPath = sc.getRealPath("");
		System.err.println("Context root path: "+servletContextRootPath);
		if (configurationPath!=null) {
			// Old SKOOL. Path provided, notify the dispatcher by passing a null DEFAULT_SERVER_XML
				return DispatcherFactory.getInstance(new File(configurationPath), DEFAULT_SERVER_XML, new com.dexels.navajo.server.FileInputStreamReader(),servletContextRootPath);
		} else {
			return DispatcherFactory.getInstance(rootPath, DEFAULT_SERVER_XML, new com.dexels.navajo.server.FileInputStreamReader(),servletContextRootPath);
		}

	}
		
	private String setupConfigurationPath(ServletContext context) throws IOException {
		String contextName =  context.getContextPath().substring(1);
		String navajoPath = getSystemPath(context,contextName);
		return navajoPath;
	}
		
	private String getSystemPath(ServletContext context, String name) throws IOException {

		String force = context.getInitParameter("forcedNavajoPath");
		System.err.println("Force: "+force);
		if(force!=null) {
//			System.err.println("Using the force! navajo.properties will be ignored!");
			return force;
		}
		Map<String,String> systemContexts = new HashMap<String, String>();
		File home = new File(System.getProperty("user.home"));
		File navajo = new File(home,"navajo.properties");
		System.err.println("Assuming navajo path: "+navajo.getAbsolutePath());
		if(!navajo.exists()) {
			return null;
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
		System.err.println("Maps: "+systemContexts);
		return systemContexts.get(name);
	}
		
		

}
