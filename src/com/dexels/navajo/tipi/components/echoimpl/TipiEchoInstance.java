package com.dexels.navajo.tipi.components.echoimpl;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sound.midi.SysexMessage;

import tipi.TipiApplicationInstance;

import nextapp.echo2.app.*;
import nextapp.echo2.webcontainer.ContainerContext;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;
import nextapp.echo2.webrender.Connection;
import nextapp.echo2.webrender.WebRenderServlet;

import com.dexels.navajo.echoclient.components.Styles;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.echoimpl.impl.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiEchoInstance extends ApplicationInstance implements TipiApplicationInstance {
	private TipiContext context;

	private String tipiDef = null;

	private String tipiDir = null;

	private ServletConfig myServletConfig;

	private String resourceDir;

	private ServletContext myServletContext;

	public TipiEchoInstance(ServletConfig sc, ServletContext c) throws Exception {
		myServletConfig = sc;
		myServletContext = c;

	}

	public URL getLogoutUrl() throws MalformedURLException {
		Connection con = WebRenderServlet.getActiveConnection();
		HttpServletRequest req = con.getRequest();
		String url = req.getRequestURL().toString();

		URL u = new URL(url);
		String contextname = con.getRequest().getContextPath();
		String host = con.getServlet().getInitParameter("host");

		if(host==null) {
			host = u.getHost();
		}

		URL rootURL = null;
		rootURL = new URL(u.getProtocol(),host, u.getPort(), contextname + "/logout?destination=" + u.getPath());
		return rootURL;

	}

	public void exitToUrl() throws MalformedURLException {
		enqueueCommand(new BrowserRedirectCommand(getLogoutUrl().toString()));
		ContainerContext containerContext = (ContainerContext) getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
		final HttpSession session = containerContext.getSession();
	}

	public final void startup() throws IOException {
		setCurrentContext(createContext());
	}



//	public void loadTipi(URL tipidef) throws IOException, TipiException {
//		context.parseURL(tipidef, false);
//	}

	public void loadTipi(TipiContext newContext, String fileName) throws IOException, TipiException {
		System.err.println("Context: "+newContext+" filename: "+fileName);
		InputStream in = newContext.getTipiResourceStream(fileName);

		if(in!=null) {
			newContext.parseStream(in, "startup", false);
			in.close();
			 
		} else {
			throw new TipiException("Error loading tipi: "+fileName);
		}
	}

	public Window init() {
		try {
			startup();
		} catch (IOException e) {
			e.printStackTrace();
		}
		TipiScreen echo = (TipiScreen) context.getDefaultTopLevel();
 
		TipiFrame w = (TipiFrame) echo.getTipiComponent("init");
		if (w == null) {
			throw new RuntimeException("No toplevel found!");
		}
		echo.setWindow(w.getWindow());
		return w.getWindow();
	}

	private void initServlet(TipiContext newContext, Enumeration args) throws Exception {
		checkForProperties(newContext, args);
		loadTipi(newContext, tipiDef);
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
					System.err.println("Error parsing system property");
				}
			}
			if ("tipidef".equals(current)) {
				tipiDef = myServletConfig.getInitParameter(current);
				System.err.println("Startup def: "+tipiDef);
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

	public TipiContext createContext() throws IOException {

		String stylePath = myServletContext.getRealPath("Default.stylesheet");
		System.err.println("StylePath: "+stylePath);
		try {
			FileInputStream fis = new FileInputStream(stylePath);
			Styles.loadStyleSheet(fis);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (Styles.DEFAULT_STYLE_SHEET != null) {
			setStyleSheet(Styles.DEFAULT_STYLE_SHEET);
			Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(WindowPane.class, "Default");
			System.err.println(">>> " + ss);
		}
		System.err.println("REAL PATH: " + myServletContext.getRealPath("/"));
		
		// Title.Sub
		EchoTipiContext newContext = new EchoTipiContext(this,null);
		ServletContextResourceLoader servletContextTipiLoader = new ServletContextResourceLoader(myServletContext,"tipi");
		newContext.setTipiResourceLoader(servletContextTipiLoader);
		ServletContextResourceLoader servletContextResourceLoader = new ServletContextResourceLoader(myServletContext,"resource");
		newContext.setGenericResourceLoader(servletContextResourceLoader);
	//	context.setResourceBaseDirectory(new File(myServletContext.getRealPath("/") + "resource/tipi/"));
		
		getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
		TipiScreen es = new TipiScreen();
		newContext.parseRequiredIncludes();
//		newContext.processRequiredIncludes();
		es.setContext(newContext);
		es.createContainer();
		newContext.setDefaultTopLevel(es);
		try {
			System.err.println("Context created: "+newContext.hashCode());
			initServlet(newContext, myServletConfig.getInitParameterNames());
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return newContext;
	}

	public void dispose(TipiContext t) {
		// TODO Auto-generated method stub
		
	}

	public TipiContext getCurrentContext() {
		// TODO Auto-generated method stub
		return context;
	}

	public void reboot() throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void setCurrentContext(TipiContext currentContext) {
		context = currentContext;
		
	}

}
