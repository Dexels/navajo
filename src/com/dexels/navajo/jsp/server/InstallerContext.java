package com.dexels.navajo.jsp.server;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;

import com.dexels.navajo.server.listener.NavajoContextListener;
public class InstallerContext {
	private PageContext pageContext;
//	private Map<String,String> systemContexts = new HashMap<String, String>();
//	private String currentContext;
	
	private NavajoContextListener contextStarter = new NavajoContextListener();
	
	/**
	 * Returns if the location dictated by this context is plausible
	 */
	public boolean isValidInstallation() {
		return NavajoContextListener.isValidInstallationForContext(getServletContext());
	}
	
	public String getNavajoRoot(String serverContext) throws IOException {
		return  NavajoContextListener.getInstallationPath(getServletContext(),null);
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
		contextStarter.initializeContext(getPageContext().getServletContext(),null);
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
	



}
