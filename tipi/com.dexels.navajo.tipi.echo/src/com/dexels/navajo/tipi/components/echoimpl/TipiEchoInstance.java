/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import navajo.ExtensionDefinition;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Style;
import nextapp.echo2.app.Window;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.webcontainer.ContainerContext;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.Connection;
import nextapp.echo2.webrender.WebRenderServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.TipiApplicationInstance;
import tipi.TipiExtension;
import tipiecho.TipiEchoExtension;

import com.dexels.navajo.echoclient.components.Styles;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiContextListener;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.connectors.TipiConnector;
import com.dexels.navajo.tipi.echo.functions.EchoFunctionDefinition;
import com.dexels.navajo.tipi.locale.LocaleListener;

@SuppressWarnings("unused")
public class TipiEchoInstance extends ApplicationInstance implements TipiApplicationInstance {

	private static final long serialVersionUID = 4880562953035112989L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiEchoInstance.class);
	private TipiContext context;

	private String tipiDef = null;

//	private String tipiDir = null;

	private ServletConfig myServletConfig;

//	private String resourceDir;
	private final Set<TipiContextListener> tipiContextListeners = new HashSet<TipiContextListener>();

	private ServletContext myServletContext;

private TipiConnector defaultConnector;

private String language;

private String region;

private List<TipiExtension> extensionList = null;

	public TipiEchoInstance(ServletConfig sc, ServletContext c) throws Exception {
		myServletConfig = sc;
		myServletContext = c;

	}

	public URL getLogoutUrl() throws MalformedURLException {
		Connection con = WebRenderServlet.getActiveConnection();
		HttpServletRequest req = con.getRequest();
		String url = req.getRequestURL().toString();
		Enumeration<String> en = req.getHeaderNames();
		while (en.hasMoreElements()) {
			String header = en.nextElement();
//			System.err.println("HEADER: "+header+" : "+req.getHeader(header));
		}
		;

		return extractURL(req,"/logout");
	}

	private URL extractURL(HttpServletRequest request,String logoutPath) throws MalformedURLException {
		URL actual = new URL( request.getRequestURL().toString());
		String host = request.getHeader("x-forwarded-host");
		if(host==null) {
			host = actual.getHost();
		}
		String proto = request.getHeader("x-forwarded-proto");
		if(proto==null) {
			proto = actual.getProtocol();
		}
		String url = request.getHeader("x-forwarded-url");
		if(url==null) {
			url = actual.getPath();
		}
		if(logoutPath!=null) {
			url = logoutPath;
		}
		
		
		if(proto==null) {
			proto = "http";
		}
		logger.info("Extracting URL. Proto: "+proto+" : "+host+" : "+url);

		return new URL(proto,host,url);
	}
	
	public void exitToUrl() throws MalformedURLException {
		String logout = getLogoutUrl().toString();
		logger.info("Exiting to URL: "+logout);
		enqueueCommand(new BrowserRedirectCommand(logout));
	}

	public final void startup() throws IOException {
			this.extensionList  = TipiEchoExtension.getInstance().getTipiExtensionRegistry().getExtensionList();
		setCurrentContext(createContext());
	}



//	public void loadTipi(URL tipidef) throws IOException, TipiException {
//		context.parseURL(tipidef, false);
//	}

	public void loadTipi(TipiContext newContext, String fileName, ExtensionDefinition ed) throws IOException, TipiException {
		logger.info("Context: "+newContext+" filename: "+fileName);
		InputStream in = newContext.getTipiResourceStream(fileName);

		if(in!=null) {
			newContext.parseStream(in,ed);
			newContext.switchToDefinition("startup");
			in.close();
			 
		} else {
			throw new TipiException("Error loading tipi: "+fileName);
		}
	}

	public Window init() {
		try {
			startup();
		} catch (IOException e) {
			logger.error("Error: ",e);
		}
		TipiScreen echo = (TipiScreen) context.getDefaultTopLevel();
 
		TipiFrame w = (TipiFrame) echo.getTipiComponent("init", false);
		if (w == null) {
			throw new RuntimeException("No toplevel found!");
		}
		echo.setWindow(w.getWindow());
		return w.getWindow();
	}

	private void initServlet(TipiContext newContext, Enumeration args, ExtensionDefinition ed) throws Exception {
		checkForProperties(newContext, args);
		loadTipi(newContext, tipiDef,ed);
	}

	private Map checkForProperties(TipiContext context, Enumeration e) {
		Map result = new HashMap();
		while (e.hasMoreElements()) {
			String current = (String) e.nextElement();
			if (current.startsWith("-D")) {
				String prop = current.substring(2);
				try {
					StringTokenizer st = new StringTokenizer(prop, "=");
					String name = st.nextToken();
					String value = st.nextToken();
					System.setProperty(name, value);
					result.put(name, value);
				} catch (NoSuchElementException ex) {
					logger.info("Error parsing system property");
				}
			}
			if ("tipidef".equals(current)) {
				tipiDef = myServletConfig.getInitParameter(current);
				logger.info("Startup def: "+tipiDef);
				continue;
			}
			context.setSystemProperty(current, myServletConfig.getInitParameter(current));
		}
		return result;
	}

	public void finalize() {
		if (context != null) {
			context.exit();
		}
	}
	
	public TipiContext getTipiContext() {
		return context;
	}

	@Override
	public TipiContext createContext() throws IOException {

		String stylePath = myServletContext.getRealPath("Default.stylesheet");
		logger.info("StylePath: "+stylePath);
		try {
			FileInputStream fis = new FileInputStream(stylePath);
			Styles.loadStyleSheet(fis);
			fis.close();
		} catch (IOException e) {
			logger.error("Error: ",e);
		}

		if (Styles.DEFAULT_STYLE_SHEET != null) {
			setStyleSheet(Styles.DEFAULT_STYLE_SHEET);
			Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(WindowPane.class, "Default");
			logger.info(">>> " + ss);
		}
		logger.info("REAL PATH: " + myServletContext.getRealPath("/"));
		
		// Title.Sub
		logger.info("Extension list: "+this.extensionList);
		EchoTipiContext newContext = new EchoTipiContext(this,null,this.extensionList);
		newContext.setDefaultConnector(defaultConnector);
		ServletContextResourceLoader servletContextTipiLoader = new ServletContextResourceLoader(myServletContext,"tipi");
		newContext.setTipiResourceLoader(servletContextTipiLoader);
		ServletContextResourceLoader servletContextResourceLoader = new ServletContextResourceLoader(myServletContext,"resource");
		newContext.setGenericResourceLoader(servletContextResourceLoader);
		
	//	context.setResourceBaseDirectory(new File(myServletContext.getRealPath("/") + "resource/tipi/"));
		
		TipiEchoExtension ed = new TipiEchoExtension();
		ed.initialize(newContext);

		EchoFunctionDefinition efd = new EchoFunctionDefinition();
		
		getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
		TipiScreen es = new TipiScreen();
//		newContext.processRequiredIncludes();
		es.setContext(newContext);
		es.createContainer();
		newContext.setDefaultTopLevel(es);
		try {
			logger.info("Context created: "+newContext.hashCode());
			initServlet(newContext, myServletConfig.getInitParameterNames(),ed);
		} catch (Throwable ex) {
			logger.error("Error: ", ex);
		}
		return newContext;
	}

	public void dispose(TipiContext t) {
		
	}

	public TipiContext getCurrentContext() {
		return context;
	}

	public void reboot() throws IOException {
		
	}

	public void setCurrentContext(TipiContext currentContext) {
		context = currentContext;
		
	}

	@Override
	public String getDefinition() {
		logger.info("WARNING UNCLEAR FUNCTION");
		return "startup";
	}

	@Override
	public void setEvalUrl(URL context, String relativeUri) {
		throw new UnsupportedOperationException("setEvalUrl (vaadin version) not implemented in echo"); 
	}

	@Override
	public void setContextUrl(URL contextUrl) {
	}

	@Override
	public URL getContextUrl() {
		return null;
	}

	@Override
	public void close() {
		
	}

	@Override
	public void setDefaultConnector(TipiConnector tipiConnector) {
		this.defaultConnector = tipiConnector;
		
	}

	@Override
	public void addTipiContextListener(TipiContextListener t) {
		tipiContextListeners.add(t);
	}

	@Override
	public void setLocaleCode(String locale) {
		this.language = locale;
	}
	@Override
	public String getLocaleCode() {
		return language;
	}
	@Override
	public void setSubLocaleCode(String region) {
		this.region = region;
	}
	@Override
	public String getSubLocaleCode() {
		return region;
	}

	public void addLocaleListener(LocaleListener l) {
		logger.warn("Locale listeners not supported in Echo");
	}

	public void removeLocaleListener(LocaleListener l) {
		logger.warn("Locale listeners not supported in Echo");
	}

}
