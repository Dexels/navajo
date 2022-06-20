/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class NavajoException extends RuntimeException {

	private static final long serialVersionUID = 8965495998372888144L;


	public NavajoException(Throwable e) {
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
     * Construct a NavajoException with a given message (text) AND a root cause
     * @param message String
     */
    public NavajoException(String message, Throwable root) {
      super(message,root);
    }

    
    /**
     * Gets the wrapped exception.
     *
     * @return the wrapped exception.
     */
   
}
