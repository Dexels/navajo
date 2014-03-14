package com.dexels.navajo.osgi;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.ops4j.pax.web.service.WebContainer;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.context.ClientContext;
import com.dexels.navajo.script.api.LocalClient;
import com.dexels.navajo.server.NavajoConfigInterface;

public class JspComponent implements ServletContextListener {
	private WebContainer webContainer;
	private NavajoConfigInterface navajoConfig;
	private ClientContext clientContext;
	private BundleContext bundleContext;
	private static JspComponent instance;

	private final static Logger logger = LoggerFactory
			.getLogger(JspComponent.class);
	private HttpContext httpContext;
	private LocalClient localClient = null;

	private final Map<String,LocalClient> localClients = new HashMap<String, LocalClient>();
	private ServletContext servletContext;
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
	
//	   <reference bind="setLocalClient" cardinality="1..1" interface="com.dexels.navajo.script.api.LocalClient" name="LocalClient" policy="static" unbind="clearLocalClient"/>

	public void addLocalClient(LocalClient localClient, Map<String,Object> settings) {
		String name = (String) settings.get("instance");
		if (name==null) {
			this.localClient  = localClient;
		} else {
			this.localClients.put(name, localClient);
		}
		
	}

	public void removeLocalClient(LocalClient localClient, Map<String,Object> settings) {
		String name = (String) settings.get("instance");
		if (name==null) {
			this.localClient  = null;
		} else {
			this.localClients.remove(name);
		}
//		this.localClient = null;
	}

	public static JspComponent getInstance() {
		return instance;
	}

	public String getVersion() {
		return bundleContext.getBundle().getVersion().toString();
	}


	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.debug("Context destroyed.");
		this.servletContext = null;
		}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		this.servletContext = sce.getServletContext();
		sce.getServletContext().setAttribute("navajoContext",
				clientContext);
		sce.getServletContext().setAttribute("localClient", localClient);
		sce.getServletContext().setAttribute("localClients", this.localClients);
		
	}
	
	public void activate(BundleContext cc) {
		this.bundleContext = cc;
		instance = this;
		try {
			httpContext = webContainer.createDefaultHttpContext();
			Dictionary<String, Object> contextProperties = new Hashtable<String, Object>();
			logger.info("Injecting forced Navajo path: "
					+ navajoConfig.getRootPath());
			contextProperties.put("forcedNavajoPath",
					navajoConfig.getRootPath());
			webContainer.setContextParam(contextProperties, httpContext);
			webContainer.registerEventListener(this, httpContext);

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
