package com.dexels.navajo.util;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version 1.0
 */

import com.dexels.navajo.document.*;
import com.dexels.navajo.server.SystemException;
public final class NavajoUtils {

  public static String getPropertyValue(Message msg, String propertyName, boolean required) throws NavajoException {

    Property prop = msg.getProperty(propertyName);
    if (required && (prop == null))
      throw new NavajoException("Could not find property: " + propertyName);
    else if (prop == null)
      return "";
    else {
      return prop.getValue();
    }
  }
}