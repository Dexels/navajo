package com.dexels.navajo.server.listener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import navajolisteners.Version;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.DispatcherInterface;
import com.dexels.navajo.server.LocalClientDispatcherWrapper;
import com.dexels.navajo.server.api.impl.NavajoServerInstance;
import com.dexels.navajo.server.listener.http.SchedulerTools;
import com.dexels.navajo.version.AbstractVersion;

public class NavajoContextListener implements ServletContextListener {

	public static final String DOC_IMPL = "com.dexels.navajo.DocumentImplementation";
	public static final String NANO = "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl";
	public static final String JAXP = "com.dexels.navajo.document.jaxpimpl.NavajoFactoryImpl";
	public static final String QDSAX = "com.dexels.navajo.document.base.BaseNavajoFactoryImpl";
	public static final String DEFAULT_SERVER_XML = "config/server.xml";
	private static final Logger logger = LoggerFactory.getLogger(NavajoContextListener.class);
	@SuppressWarnings("rawtypes")
	private static ServiceRegistration navajoServerInstance;
	@SuppressWarnings("rawtypes")
	private static ServiceRegistration localClientInstance;

	
	@Override
	public void contextDestroyed(ServletContextEvent sc) {
		destroyContext(sc.getServletContext());
	}

	@Override
	public void contextInitialized(ServletContextEvent sc) {
		String contextPath = sc.getServletContext().getContextPath();

		String installPath = getInstallationPath(contextPath);
		String servletContextPath = sc.getServletContext().getRealPath("");

		init(contextPath,servletContextPath,installPath,sc.getServletContext());
	}

	public void init(String contextPath, String servletContextPath, String installPath, ServletContext servletContext) {
		logger.info("=================	=========================================");
		logger.info("INITIALIZING NAVAJO INSTANCE: "+contextPath);
		logger.info("==========================================================");
		if (!isValidInstallationForContext(installPath)) {
			logger.info("No valid installation found, abandoning further Context initialization.");
			return;
		}
		initializeServletContext(contextPath,servletContextPath,installPath);
		DispatcherInterface dispatcher = initDispatcher(servletContextPath, servletContextPath, installPath);
		NavajoServerInstance nsi = new NavajoServerInstance(installPath, dispatcher);
		servletContext.setAttribute("navajoServerInstance", nsi);
		SchedulerTools.createScheduler(servletContext);
		if(!Version.hasOSGiBundleContext()) {
			logger.info("No OSGi detected. Manually inserting LocalClientWrapper into ServletContext");
			LocalClientDispatcherWrapper lcdw = new LocalClientDispatcherWrapper();
			lcdw.setContext(nsi);
			try {
				lcdw.activate(nsi.getClientSettingMap());
			} catch (IOException e) {
				logger.error("Error reading client settings: ",e);
			}
		}
	}

	public static void destroyContext(ServletContext sc) {
		logger.info("Destroying Navajo instance");
		NavajoServerInstance nsi = (NavajoServerInstance) sc.getAttribute("navajoServerInstance");
		if(nsi==null) {
			logger.warn("No navajo server instance found when shutting down context. Did the startup fail?");
		}
		unregisterInstanceOSGi();
		sc.removeAttribute("navajoServerInstance");
		
		if(Version.getDefaultBundleContext()!=null) {
			logger.info("Preventing extension shutdown. OSGi detected, they can fend for themselves.");
			return;
		}
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

	// Should also be called after installation, so the context will still be
	// initialized.
	public static NavajoServerInstance initializeServletContext(String contextPath, String servletContextPath, String installationPath) {
		System.setProperty(DOC_IMPL, QDSAX);

		try {
			DispatcherInterface dispatcher = initDispatcher(servletContextPath, servletContextPath, installationPath);
			NavajoServerInstance nsi = new NavajoServerInstance(installationPath, dispatcher);
			return nsi;
		} catch (Exception e) {
			logger.error("Error initializing dispatcher", e);
		}
		return null;
	}



	private static void unregisterInstanceOSGi() {
		BundleContext bc = navajolisteners.Version.getDefaultBundleContext();
		if(bc==null) {
			logger.warn("No OSGi environment found. Are we in J2EE mode?");
			return;
		}
		if(localClientInstance!=null) {
			localClientInstance.unregister();			
		}
		
	}
		
	public static DispatcherInterface initializeContext(String rootPath,
			String servletPath) throws NavajoException {
		return DispatcherFactory.getInstance(rootPath, DEFAULT_SERVER_XML,
				new com.dexels.navajo.server.FileInputStreamReader(),
				servletPath);
	}

	public final static DispatcherInterface initDispatcher(
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

	public static boolean isValidInstallationForContext(String installPath) {

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

	public static String getInstallationPath(String contextPath) {
			try {
				String canonicalContextPath;
				if(contextPath == null || contextPath.isEmpty()) {
					canonicalContextPath="/";
				} else {
					if(contextPath.startsWith("/")) {
						canonicalContextPath = contextPath.substring(1);
					} else {
						canonicalContextPath = contextPath;
					}
				}
				Map<String, String> systemContexts = loadSystemContexts();
				return getInstallationPath(systemContexts, canonicalContextPath);
			} catch (IOException e) {
				logger.error("Error: ", e);
				return null;
			}
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
