
/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 */

package com.dexels.navajo.mapping;

/**
 * This class is used for throwing Exception from a Mappable object's load() and store() methods.
 */

public class MappableException extends Exception {

  public MappableException() {
    super();
  }

  public MappableException(String s) {
    super(s);
  }
}