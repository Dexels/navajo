package com.dexels.navajo.article.ui;

import org.ops4j.pax.web.service.WebContainer;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JspComponent {
	private WebContainer webContainer;
//	private ClientContext clientContext;
	private BundleContext bundleContext;

	private final static Logger logger = LoggerFactory
			.getLogger(JspComponent.class);
	private HttpContext httpContext;

	public WebContainer getWebContainer() {
		return webContainer;
	}

	public void setWebContainer(WebContainer webcontainer) {
		this.webContainer = webcontainer;
	}

	public void clearWebContainer(WebContainer webcontainer) {
		this.webContainer = null;
	}


	public String getVersion() {
		return bundleContext.getBundle().getVersion().toString();
	}

	public void activate(BundleContext bc) {
		this.bundleContext = bc;
		try {
			httpContext = webContainer.getDefaultSharedHttpContext();
//			webContainer.registerJsps(new String[] { "/index.jsp" },httpContext);
			webContainer.registerResources("/article/ui", "/article/ui", httpContext);
//			webContainer.registerWelcomeFiles(new String[] { "index.html" },
//					false, httpContext);
		} catch (NamespaceException e) {
			logger.error("Error: ", e);
		}

	}

	public void deactivate() {
		if (webContainer == null) {
			return;
		}
		if (httpContext == null) {
			return;
		}
		// HttpContext context = webContainer.
		webContainer.unregisterJsps(httpContext);
	}

}
