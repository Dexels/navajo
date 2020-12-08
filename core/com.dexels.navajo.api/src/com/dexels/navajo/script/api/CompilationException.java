/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.script.api;

public class CompilationException extends Exception {

	private static final long serialVersionUID = 7448856689278900523L;


	public CompilationException(String message) {
		super(message);
	}

	public CompilationException(String message, Throwable cause) {
		super(message,cause);
	}
	

}
