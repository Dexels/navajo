package com.dexels.navajo.server;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

import com.dexels.navajo.document.Navajo;

public class ConditionErrorException extends Exception {

  Navajo errors = null;

  public ConditionErrorException(Navajo n) {
    errors = n;
  }

  public Navajo getNavajo() {
    return errors;
  }

  public String getMessage() {
    return "Found condition errors";
  }
}