package org.dexels.servlet.smtp;

/**
 * Title:        Toolbox
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class Configuration {

  protected String className = "";
  protected String host = "";
  protected String username = "";
  protected String password = "";
  protected String folder = "";
  protected String subject = "";
  protected String sender = "";
  protected String identification = "";
  protected String recipient = "";
  protected int port = -1;
  protected boolean deleteMail = true;
}