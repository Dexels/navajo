package org.dexels.servlet.smtp;

import javax.servlet.*;
import java.util.Enumeration;

/**
 * Title:        Toolbox
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class SmtpServletConfig implements ServletConfig {

  protected String name = "";
  protected String identification = "";
  protected String host = "";
  protected String username = "";
  protected String password = "";
  protected String folder = "";
  protected String sender = "";
  protected String recipient = "";
  protected ServletContext context = null;

  public SmtpServletConfig() {
  }

  public ServletContext getServletContext() {
    return this.context;
  }

  public String getInitParameter(String name) {
    return "";
  }

  public Enumeration getInitParameterNames() {
    return null;
  }

  public String getServletName() {
    return this.name;
  }

  public String getIdentification() {
    return this.identification;
  }

  public String getFolder() {
    return this.folder;
  }

  public String getPassword() {
    return this.password;
  }

  public String getHost() {
    return this.host;
  }

  public String getSender() {
    return this.sender;
  }

  public String getRecipient() {
    return this.recipient;
  }
}