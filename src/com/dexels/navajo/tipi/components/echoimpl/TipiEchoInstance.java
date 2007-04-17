package com.dexels.navajo.tipi.components.echoimpl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Style;
import nextapp.echo2.app.Window;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.webcontainer.ContainerContext;
import nextapp.echo2.webcontainer.command.BrowserRedirectCommand;

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

    public void exitToUrl(String name) {
        enqueueCommand(new BrowserRedirectCommand(name));
        ContainerContext containerContext = (ContainerContext) getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
        final HttpSession session = containerContext.getSession();
        // Invalidate session in a different thread
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.currentThread().sleep(500);
                    if (session != null)
                        session.invalidate();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void startup() {
    	if(Styles.DEFAULT_STYLE_SHEET!=null) {
	       	setStyleSheet(Styles.DEFAULT_STYLE_SHEET); 
			Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(WindowPane.class, "Default");
	        System.err.println(">>> "+ss);
    	}
    	System.err.println("REAL PATH: "+myServletContext.getRealPath("/"));
        //        Title.Sub
        context = new EchoTipiContext(this);
        context.setResourceBaseDirectory(new File(myServletContext.getRealPath("/")+"resource/tipi/"));
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
        context.setStudioMode(false);

    }

    public void loadTipi(URL tipidef) throws IOException, TipiException {
        context.parseURL(tipidef, false);
    }

    public void loadTipi(String fileName) throws IOException, TipiException {
        File dir = new File(tipiDir);
        File f = new File(dir, fileName);
        context.parseFile(f, false, tipiDir);
    }

    public Window init() {
    	
    	
        startup();
        
        TipiScreen echo = (TipiScreen) context.getDefaultTopLevel();

        ContainerContext containerContext = (ContainerContext)getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
//        containerContext.setServerDelayMessage(new NavajoServerDelayMessage(containerContext,"Moment.."));
        
        TipiFrame w = (TipiFrame) echo.getTipiComponent("init");
        if(w==null) {
        	throw new RuntimeException("No toplevel found!");
        }
        return w.getWindow();
    }

    private void initServlet(Enumeration args) throws Exception {
        System.setProperty("com.dexels.navajo.propertyMap", "tipi.propertymap");
        checkForProperties(args);
        context.setStudioMode(false);
        loadTipi(tipiDef);
        }

    private void checkForProperties(Enumeration e) {
        while (e.hasMoreElements()) {
            String current = (String) e.nextElement();
            if (current.startsWith("-D")) {
                String prop = current.substring(2);
                try {
                    StringTokenizer st = new StringTokenizer(prop, "=");
                    String name = st.nextToken();
                    String value = st.nextToken();
                    System.setProperty(name, value);
                } catch (NoSuchElementException ex) {
                    System.err.println("Error parsing system property");
                }
            }
            if ("tipidef".equals(current)) {
                tipiDef = myServletConfig.getInitParameter(current);
                continue;
            }
            if ("tipidir".equals(current)) {
            	System.err.println("");
                context.setTipiBaseDirectory(new File(myServletConfig.getInitParameter(current)));
                tipiDir = myServletConfig.getInitParameter(current);
                continue;
            }
            if ("resourcedir".equals(current)) {
//                context.setResourceBaseDirectory(new File(myServletConfig.getInitParameter(current)));
                continue;
            }
            System.setProperty(current, myServletConfig.getInitParameter(current));
        }
    }

	public void finalize() {
		if (context!=null) {
			context.exit();
		}
	}
}
