package com.dexels.navajo.document;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public abstract class NavajoException extends Exception {

	public NavajoException(Exception e) {
		super(e);
	}
	
  /**
   * Default constructor, calls superclass
   */
  public NavajoException() {
      super();
    }

    /**
     * Construct a NavajoException with a given message (text)
     * @param message String
     */
    public NavajoException(String message) {
      super(message);
    }

    /**
     * Gets the wrapped exception.
     *
     * @return the wrapped exception.
     */
   
}
