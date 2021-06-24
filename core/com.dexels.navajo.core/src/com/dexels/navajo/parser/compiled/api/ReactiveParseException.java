/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.parser.compiled.api;

public class ReactiveParseException extends RuntimeException {

	private static final long serialVersionUID = 1347861954879038546L;
	public ReactiveParseException(String message) {
		super(message);
	}

	public ReactiveParseException(String message, Throwable root) {
		super(message,root);
	}
	public ReactiveParseException(Throwable root) {
		super(root);
	}

}
