package com.dexels.navajo.server.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.api.NavajoServerContext;
import com.dexels.navajo.server.api.impl.NavajoServerInstance;
import com.dexels.navajo.version.AbstractVersion;

public class NavajoContextListener implements ServletContextListener {

	public static final String DOC_IMPL = "com.dexels.navajo.DocumentImplementation";
	public static final String NANO = "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl";
	public static final String JAXP = "com.dexels.navajo.document.jaxpimpl.NavajoFactoryImpl";
	public static final String QDSAX = "com.dexels.navajo.document.base.BaseNavajoFactoryImpl";
	public static final String DEFAULT_SERVER_XML = "config/server.xml";
	private static final Logger logger = LoggerFactory.getLogger(NavajoContextListener.class);
	private static ServiceRegistration<NavajoServerContext> navajoServerInstance;

	
	@Override
	public void contextDestroyed(ServletContextEvent sc) {
		destroyContext(sc.getServletContext());
	}

	@Override
	public void contextInitialized(ServletContextEvent sc) {

		init(sc.getServletContext());
	}

	public void init(ServletContext sc) {
		logger.info("==========================================================");
		logger.info("INITIALIZING NAVAJO INSTANCE: "+ sc.getContextPath());
		logger.info("==========================================================");

		if (!isValidInstallationForContext(sc)) {
			logger.info("No valid installation found, abandoning further Context initialization.");
			return;
		}

		initializeContext(sc, null);
	}

	public static void destroyContext(ServletContext sc) {
		logger.info("Destroying Navajo instance");
		unregisterInstanceOSGi();
		logger.warn("Destroying Navajo extensions. I'm not sure if this is wise in OSGi.");
		AbstractVersion.shutdownNavajoExtension("navajo");
		AbstractVersion.shutdownNavajoExtension("navajodocument");
		AbstractVersion.shutdownNavajoExtension("navajoclient");
		AbstractVersion.shutdownNavajoExtension("navajoadapters");
		AbstractVersion.shutdownNavajoExtension("navajolisteners");
		AbstractVersion.shutdownNavajoExtension("navajoenterpriseadapters");
		AbstractVersion.shutdownNavajoExtension("navajoenterprise");
		AbstractVersion.shutdownNavajoExtension("navajoenterpriselisteners");
		AbstractVersion.shutdownNavajoExtension("navajoqueuemanager");

	}

	/**
	 * Initialized a Navajo context.
	 * 
	 * @param sc
	 * @param force
	 *            Use this to force the path
	 * @return 
	 */

	// Should be called after installation, so the context will still be
	// initialized.
	public static DispatcherInterface initializeContext(ServletContext sc, String force) {
		String configurationPath = null;
		String rootPath = null;

		String path;
		path = getInstallationPath(sc, force);
		if (path != null) {
			configurationPath = path;
		}
		// Check whether defined bootstrap webservice is present.
		System.setProperty(DOC_IMPL, QDSAX);
		// System.err.println("Configuration path: " + configurationPath);

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
					logger.error("IO Error",e);
				}
			}
		}
		if (configurationPath == null || "".equals(configurationPath)
				|| !verified) {
			rootPath = sc.getRealPath("");
		}
		String servletContextRootPath = sc.getRealPath("");

		try {
			DispatcherInterface dispatcher = initDispatcher(servletContextRootPath, rootPath, configurationPath);
			NavajoServerInstance nsi = new NavajoServerInstance(path, dispatcher, sc);
			registerInstanceOSGi(nsi);
			return dispatcher;
		} catch (Exception e) {
			logger.error("Error initializing dispatcher", e);
		}
		return null;
	}

	private static void registerInstanceOSGi(NavajoServerInstance nsi) {
		BundleContext bc = navajolisteners.Version.getDefaultBundleContext();
		if(bc==null) {
			logger.warn("No OSGi environment found. Are we in J2EE mode?");
			return;
		}
        Dictionary<String, Object> properties = new Hashtable<String, Object>();
        properties.put("navajoContextPath", nsi.getServletContext().getContextPath());
        properties.put("installationPath", nsi.getInstallationPath());
        properties.put("serverId", nsi.getDispatcher().getServerId());
        navajoServerInstance = bc.registerService(NavajoServerContext.class, nsi,properties);
		logger.info("Service registration complete!");
	}

	private static void unregisterInstanceOSGi() {
		BundleContext bc = navajolisteners.Version.getDefaultBundleContext();
		if(bc==null) {
			logger.warn("No OSGi environment found. Are we in J2EE mode?");
			return;
		}
		if(navajoServerInstance!=null) {
			navajoServerInstance.unregister();
		}
	}
		
	public static DispatcherInterface initializeContext(String rootPath,
			String servletPath) throws NavajoException {
		return DispatcherFactory.getInstance(rootPath, DEFAULT_SERVER_XML,
				new com.dexels.navajo.server.FileInputStreamReader(),
				servletPath);
	}

	protected final static DispatcherInterface initDispatcher(
			String servletContextRootPath, String rootPath, String configurationPath)
			throws NavajoException {

		if (configurationPath != null) {
			// Old SKOOL. Path provided, notify the dispatcher by passing a null
			// DEFAULT_SERVER_XML
			return DispatcherFactory.getInstance(new File(configurationPath),
					DEFAULT_SERVER_XML,
					new com.dexels.navajo.server.FileInputStreamReader(),
					servletContextRootPath);
		} else {
			return initializeContext(rootPath, servletContextRootPath);
		}

	}

	public static boolean isValidInstallationForContext(ServletContext context) {
		String installPath = getInstallationPath(context, null);

		if (installPath == null) {
			return false;
		}
		File f = new File(installPath);
		if (!f.exists()) {
			return false;
		}
		File scripts = new File(f, "scripts");
		if (!scripts.exists()) {
			return false;
		}
		File config = new File(f, "config");
		if (!config.exists()) {
			return false;
		}
		return config != null;
	}

	public static String getInstallationPath(ServletContext context,
			String force) {
		if (force == null) {
			force = context.getInitParameter("forcedNavajoPath");
		}
		if (force != null) {
			return force;
		} else {
			try {
				String cp = context.getContextPath();
				String contextPath;
				if(cp == null || cp.isEmpty()) {
					contextPath="/";
				} else {
					if(cp.startsWith("/")) {
						contextPath = cp.substring(1);
					} else {
						contextPath = cp;
					}
				}
				Map<String, String> systemContexts = loadSystemContexts();
				return getInstallationPath(systemContexts, contextPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	// private String initialize(String contextPath) throws IOException {
	// Map<String,String> systemContexts = loadSystemContexts();
	// return getInstallationPath(systemContexts,contextPath);
	// }

	/**
	 * Only used when the path is not forced.
	 */
	private static String getInstallationPath(
			Map<String, String> systemContexts, String contextPath) {
		String engineInstance = System
				.getProperty("com.dexels.navajo.server.EngineInstance");
		String key = contextPath;
		if (engineInstance != null) {
			key = contextPath + "@" + engineInstance;
		}
		String result = systemContexts.get(key);
		if (result != null) {
			return result;
		}
		return systemContexts.get(contextPath);
	}

	private static Map<String, String> loadSystemContexts() throws IOException {
		File home = new File(System.getProperty("user.home"));
		File navajo = new File(home, "navajo.properties");
		Map<String, String> systemContexts = new HashMap<String, String>();
		if (!navajo.exists()) {
			return systemContexts;
		}
		BufferedReader br = new BufferedReader(new FileReader(navajo));

		while (true) {
			String line = br.readLine();
			if (line == null) {
				break;
			}
			String[] r = line.split("=");
			systemContexts.put(r[0], r[1]);
		}

		br.close();
		return systemContexts;
	}

}
