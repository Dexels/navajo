package com.dexels.navajo.tipi.components.echoimpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sound.midi.SysexMessage;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Style;
import nextapp.echo2.app.Window;
import nextapp.echo2.app.WindowPane;
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

public class TipiEchoInstance extends ApplicationInstance {
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
		// deprecated the init. The context path should work
		String base = (String) con.getServlet().getInitParameter("baseURL");
		if (base == null) {
			base = contextname;
		}
		URL rootURL = new URL(u.getProtocol(), u.getHost(), u.getPort(), base + "/logout?destination=" + u.getPath());
		return rootURL;

	}

	public void exitToUrl() throws MalformedURLException {
		enqueueCommand(new BrowserRedirectCommand(getLogoutUrl().toString()));
		ContainerContext containerContext = (ContainerContext) getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
		final HttpSession session = containerContext.getSession();
		// Invalidate session in a different thread
		// Thread thread = new Thread(new Runnable() {
		// public void run() {
		// try {
		// Thread.currentThread().sleep(200);
		// if (session != null)
		// session.invalidate();
		// } catch (Throwable t) {
		// t.printStackTrace();
		// }
		// }
		// });
		// thread.start();
	}

	private void startup() {
		if (Styles.DEFAULT_STYLE_SHEET != null) {
			setStyleSheet(Styles.DEFAULT_STYLE_SHEET);
			Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(WindowPane.class, "Default");
			System.err.println(">>> " + ss);
		}
		System.err.println("REAL PATH: " + myServletContext.getRealPath("/"));
		// Title.Sub
		context = new EchoTipiContext(this);
		ServletContextResourceLoader servletContextResourceLoader = new ServletContextResourceLoader(myServletContext,"WEB-INF/classes/tipi");
		context.setTipiResourceLoader(servletContextResourceLoader);
		context.setGenericResourceLoader(servletContextResourceLoader);
	//	context.setResourceBaseDirectory(new File(myServletContext.getRealPath("/") + "resource/tipi/"));
		getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
		TipiScreen es = new TipiScreen();
		context.parseRequiredIncludes();
		context.processRequiredIncludes();
		es.setContext(context);
		es.createContainer();
		context.setDefaultTopLevel(es);
		try {
			initServlet(myServletConfig.getInitParameterNames());
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

	public void loadTipi(URL tipidef) throws IOException, TipiException {
		context.parseURL(tipidef, false);
	}

	public void loadTipi(String fileName) throws IOException, TipiException {
		InputStream in = context.getTipiResourceStream(fileName);

		if(in!=null) {
			context.parseStream(in, "startup", false);
			in.close();
			 
		} else {
			throw new TipiException("Error loading tipi: "+fileName);
		}
//		File rootDir = new File(myServletContext.getRealPath("/"));
//		File dir = null;
//		if (tipiDir == null) {
//			dir = rootDir;
//		} else {
//			dir = new File(rootDir, tipiDir);
//		}
//		File f = new File(dir, fileName);
//		URL resourceURL = getClass().getClassLoader().getResource(fileName);
//		// context.parseFile(f, false, tipiDir);
//		if(resourceURL!=null) {
//		InputStream in = resourceURL.openStream();
//		} else {
//			System.err.println("Error loading: tipi: "+fileName);
//		}
//		
		}

	public Window init() {
		startup();
//		System.err.println("Startup finished");
		TipiScreen echo = (TipiScreen) context.getDefaultTopLevel();
//		System.err.println("echo: " + echo.store());
 
		TipiFrame w = (TipiFrame) echo.getTipiComponent("init");
		if (w == null) {
			throw new RuntimeException("No toplevel found!");
		}
		echo.setWindow(w.getWindow());
		return w.getWindow();
	}

	private void initServlet(Enumeration args) throws Exception {
		checkForProperties(args);
		loadTipi(tipiDef);
	}

	private Map checkForProperties(Enumeration e) {
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
				continue;
			}
			System.setProperty(current, myServletConfig.getInitParameter(current));
		}
		return result;
	}

	public void finalize() {
		if (context != null) {
			context.exit();
		}
	}
}
