
/**
 * Title:        Navajo (version 2)<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version 1.0
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