
/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.server;

import java.util.Date;

public class LogData {

  public Date entered;
  public String user;
  public String rpc;
  public String level;
  public String comment;
  public String timestamp;
  public String userAgent;
  public String ipAddress;
  public String hostName;

  public LogData() {
  }
}