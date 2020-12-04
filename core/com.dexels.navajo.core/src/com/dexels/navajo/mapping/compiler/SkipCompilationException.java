/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping.compiler;

/**
 * Indicated that a script shouldn't be compiled, it is not an error and probably
 * does not need to be logged extensively. Used for script fragments, that only
 * should be used as includes.
 */

public class SkipCompilationException extends Exception {

	public SkipCompilationException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 4069280458449005971L;

}
