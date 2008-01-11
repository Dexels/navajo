

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

    /**
	 * 
	 */
	private static final long serialVersionUID = -437908771360471541L;

	public MappableException() {
        super();
    }

    public MappableException(String s) {
        super(s);
    }
}
