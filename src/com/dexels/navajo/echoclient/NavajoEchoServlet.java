package com.dexels.navajo.echoclient;

import nextapp.echo.*;
import nextapp.echoservlet.*;

public class NavajoEchoServlet extends EchoServer {

  static {
    echopoint.ui.Installer.register();
    System.setProperty("com.dexels.navajo.DocumentImplementation","com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");

  }
  public void destroy() {
  }
  public EchoInstance newInstance() {
    return new NavajoEchoInstance();
  }
}
