package com.dexels.navajo.tipi.components.echoimpl;

import java.util.*;

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

public class TipiServlet
    extends EchoServer {

  static {
    echopoint.ui.Installer.register();
    System.setProperty("com.dexels.navajo.DocumentImplementation",
                       "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");

  }

  public void destroy() {
  }

  public EchoInstance newInstance() {
    TipiEchoInstance tp = null;
    try {
      tp = new TipiEchoInstance(getServletConfig());
    }
    catch (Throwable ex) {
      ex.printStackTrace();
      throw new RuntimeException(ex);
    }

     return tp;
  }

}
