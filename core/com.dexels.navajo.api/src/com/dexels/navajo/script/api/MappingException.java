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


public class MappingException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7107286296359045746L;

	public MappingException(String s) {
        super(s);
    }

	public MappingException(String s, Throwable t) {
        super(s,t);
    }

}
