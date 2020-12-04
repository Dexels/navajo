/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 */

package com.dexels.navajo.script.api;


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

    public MappableException(String s, Throwable cause) {
        super(s,cause);
    }

}
