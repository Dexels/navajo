package com.dexels.navajo.osgi;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.ops4j.pax.web.service.WebContainer;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JspComponent {
	private WebContainer webContainer;
	private BundleContext bundleContext;
	private static JspComponent instance;

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


	public static JspComponent getInstance() {
		return instance;
	}

	public String getVersion() {
		return bundleContext.getBundle().getVersion().toString();
	}

	public void activate(BundleContext cc) {
		this.bundleContext = cc;
		instance = this;
		try {
			httpContext = webContainer.createDefaultHttpContext();
			Dictionary<String, Object> contextProperties = new Hashtable<String, Object>();
			webContainer.setContextParam(contextProperties, httpContext);
			webContainer.registerEventListener(new ServletContextListener() {

				@Override
				public void contextInitialized(ServletContextEvent sce) {
					sce.getServletContext().setAttribute("navajoContext",
							null);
				}

				@Override
				public void contextDestroyed(ServletContextEvent sce) {
					logger.debug("Context destroyed.");
				}
			}, httpContext);

			// PageContext pc = new Page

			webContainer.registerJsps(new String[] { "/auth/index.jsp" },
					httpContext);
			webContainer.registerResources("/auth/css", "/auth/css", httpContext);
			webContainer.registerResources("/auth/js", "/auth/js", httpContext);
			webContainer.registerResources("/auth/img", "/auth/img", httpContext);
			webContainer.registerWelcomeFiles(new String[] { "auth/index.jsp" },
					false, httpContext);
		} catch (NamespaceException e) {
			logger.error("Error: ", e);
		}

	}

	public void deactivate() {
		instance = null;
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
