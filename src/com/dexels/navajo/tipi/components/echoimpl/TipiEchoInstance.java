package com.dexels.navajo.tipi.components.echoimpl;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;

import com.dexels.navajo.tipi.*;
import nextapp.echo.*;
import nextapp.echoservlet.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiEchoInstance
    extends EchoInstance {
  private final TipiContext context;
  private String tipiDef = null;
  private String tipiDir = null;
  private ServletConfig myServletConfig;

  public TipiEchoInstance(ServletConfig sc) throws Exception {
    myServletConfig = sc;

    context = new EchoTipiContext();
    TipiScreen es = new TipiScreen();
    es.setContext(context);
    es.createContainer();
    context.setDefaultTopLevel(es);
    try {
      initServlet(myServletConfig.getInitParameterNames());
    }
    catch (Throwable ex) {
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

  protected Window init() {
    TipiScreen echo = (TipiScreen) context.getDefaultTopLevel();
    TipiFrame w = (TipiFrame) echo.getTipiComponent("init");
    return (Window) w.getContainer();
  }

  public void initServlet(Enumeration args) throws Exception {
    System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");
    System.setProperty("com.dexels.navajo.propertyMap", "tipi.propertymap");
    checkForProperties(args);
    context.setStudioMode(false);
    loadTipi(tipiDef);
    ServerContext scont = (ServerContext) getAttribute(ServerContext.ATTRIBUTE_NAME);
    ( (EchoTipiContext) context).setServerContext(scont);
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
        }
        catch (NoSuchElementException ex) {
          System.err.println("Error parsing system property");
        }
      }
      if ("tipidef".equals(current)) {
        tipiDef = myServletConfig.getInitParameter(current);
      }
      if ("tipidir".equals(current)) {
        tipiDir = myServletConfig.getInitParameter(current);
      }
    }
  }

}
