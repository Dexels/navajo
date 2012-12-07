package com.dexels.navajo.jsp.server;

import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.api.NavajoServerContext;

public class NavajoServerContextProxy implements NavajoServerContext {

//	private PageContext pageContext;
	private ServletContext servletContext;
	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoServerContextProxy.class);
	private BundleContext bundleContext;
	
	public void setupOsgiLink() {
//		com.dexels.navajo.server.api.NavajoServerContext navajoServerContext;
		logger.info("Hash of context: "+servletContext.hashCode());
		bundleContext = (BundleContext) servletContext.getAttribute(BundleContext.class.getName());
	}

	@Override
	public String getInstallationPath() {
		ServiceReference<NavajoServerContext> sr = bundleContext.getServiceReference(NavajoServerContext.class);
		if(sr!=null) {
			NavajoServerContext nsc= bundleContext.getService(sr);
			logger.info("ServerCONTEXT serverref found!");
			return nsc.getInstallationPath();
		}
		return null;
	}

	public void setPageContext(PageContext pageContext) {
//		this.pageContext = pageContext;
		this.servletContext = pageContext.getServletContext();
		setupOsgiLink();

	}
}
