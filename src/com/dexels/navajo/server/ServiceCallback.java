package com.dexels.navajo.server;

import javax.security.auth.callback.Callback;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class ServiceCallback implements Callback {

  private String name;
  private String service;

  public ServiceCallback(String name) {
    this.name = name;
  }

  public void setService(String s) {
    this.service = s;
  }

  public String getService() {
    return this.service;
  }

}