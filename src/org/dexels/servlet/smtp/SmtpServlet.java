package org.dexels.servlet.smtp;

import javax.servlet.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

/**
 * Title:        Toolbox
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public abstract class SmtpServlet implements Servlet {

  protected String name = "";
  private ServletConfig config = null;
  protected ServletContext context = null;

  public SmtpServlet() {
  }

  public void init(ServletConfig config) throws ServletException {
    this.config = config;
  }

  public ServletConfig getServletConfig() {
    return this.config;
  }

  public ServletContext getServletContext() {
    return this.context;
  }

  public abstract void doSend(SmtpServletRequest req, SmtpServletResponse res) throws ServletException, IOException;

  public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
    System.out.println("in service() method");
    doSend((SmtpServletRequest) req, (SmtpServletResponse) res);
  }

  public String getServletInfo() {
    return this.name;
  }

  public void destroy() {
  }

}