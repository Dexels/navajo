package com.dexels.navajo.jsp.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class InstallerContext {
	private PageContext pageContext;
	
	private final static Logger logger = LoggerFactory
			.getLogger(InstallerContext.class);
	/**
	 * Returns if the location dictated by this context is plausible
	 */
	public boolean isValidInstallation() {
		return isValidInstallationForContext(getServletContext());
	}
	
	public String getNavajoRoot()  {
		return  getInstallationPath(getServletContext(),null);
	}
	
	public String getSuggestedPath() {
		File home = new File(System.getProperty("user.home"));
		String contextPath = getContextName().substring(1);
		File path = new File(home,contextPath);
		return path.getAbsolutePath();
	}
	

	public ServletContext getServletContext() {
		return getPageContext().getServletContext();
	}

	
	public String getContextName() {
		return getPageContext().getServletContext().getContextPath();
	}

	/*
	 * Call this after a fresh install (because in that case the context initialization has been skipped)
	 */
	public void initialize() {
		//TODO fix!
//		NavajoContextListener.initializeContext(getPageContext().getServletContext(),null);
	}
	
	public String getContextPath() {
		return getPageContext().getServletConfig().getServletContext().getRealPath("");
	}

	public String getServerInfo() {
		return "Server: "+getPageContext().getServletConfig().getServletContext().getServerInfo();
	}

	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	public PageContext getPageContext() {
		return pageContext;
	}
	
	/**
	 * Only used when the path is not forced.
	 */
	private static String getInstallationPath(
			Map<String, String> systemContexts, String contextPath) {
		String engineInstance = getEngineInstance();
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

	private static String getEngineInstance() {
		String engineInstance = System
				.getProperty("com.dexels.navajo.server.EngineInstance");
		return engineInstance;
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
		return true;
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
				String contextPath = context.getContextPath().substring(1);
				Map<String, String> systemContexts = loadSystemContexts();
				return getInstallationPath(systemContexts, contextPath);
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
		}
		return null;
	}


}
