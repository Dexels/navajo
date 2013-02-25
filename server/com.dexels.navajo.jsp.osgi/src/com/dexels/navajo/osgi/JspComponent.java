package com.dexels.navajo.osgi;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.ops4j.pax.web.service.WebContainer;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.context.ClientContext;
import com.dexels.navajo.server.NavajoConfigInterface;

public class JspComponent {
	private WebContainer webContainer;
	private NavajoConfigInterface navajoConfig;
	private ClientContext clientContext;
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

	public NavajoConfigInterface getNavajoConfig() {
		return navajoConfig;
	}

	public void setNavajoConfig(NavajoConfigInterface navajoConfig) {
		this.navajoConfig = navajoConfig;
	}

	public void clearNavajoConfig(NavajoConfigInterface navajoConfig) {
		this.navajoConfig = null;
	}

	public ClientContext getClientContext() {
		return clientContext;
	}

	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
	}

	public void clearClientContext(ClientContext clientContext) {
		this.clientContext = null;
	}

	public static JspComponent getInstance() {
		return instance;
	}

	public String getVersion() {
		return bundleContext.getBundle().getVersion().toString();
	}

	public void activate(ComponentContext cc) {
		this.bundleContext = cc.getBundleContext();
		instance = this;
		try {
			httpContext = webContainer.createDefaultHttpContext();
			Dictionary<String, Object> contextProperties = new Hashtable<String, Object>();
			logger.info("Injecting forced Navajo path: "
					+ navajoConfig.getRootPath());
			contextProperties.put("forcedNavajoPath",
					navajoConfig.getRootPath());
			webContainer.setContextParam(contextProperties, httpContext);
			webContainer.registerEventListener(new ServletContextListener() {

				@Override
				public void contextInitialized(ServletContextEvent sce) {
					sce.getServletContext().setAttribute("navajoContext",
							clientContext);
				}

				@Override
				public void contextDestroyed(ServletContextEvent sce) {
					logger.debug("Context destroyed.");
				}
			}, httpContext);

			// PageContext pc = new Page

			webContainer.registerJsps(new String[] { "/index.jsp" },
					httpContext);
			webContainer
					.registerJsps(new String[] { "/main.jsp" }, httpContext);
			webContainer.registerJsps(new String[] { "/navajotester.jsp" },
					httpContext);
			webContainer.registerJsps(new String[] { "/tml/*" }, httpContext);
			webContainer.registerJsps(new String[] { "/tml/manager/*" },
					httpContext);
			webContainer.registerJsps(new String[] { "/tml/installer/*" },
					httpContext);
			webContainer.registerResources("/index.html", "/index.html",
					httpContext);
			webContainer.registerResources("/images", "/images", httpContext);
			webContainer.registerResources("/css", "/css", httpContext);
			webContainer.registerResources("/js", "/js", httpContext);
			webContainer.registerResources("/resources", "/resources",
					httpContext);
			webContainer.registerResources("/yaml", "/yaml", httpContext);
			webContainer.registerResources("/WEB-INF", "/WEB-INF", httpContext);
			webContainer.registerWelcomeFiles(new String[] { "index.html" },
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
