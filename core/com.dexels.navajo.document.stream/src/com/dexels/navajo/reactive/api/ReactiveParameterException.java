/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive.api;

public class ReactiveParameterException extends RuntimeException {

	private static final long serialVersionUID = 6115559675419129641L;

	public ReactiveParameterException() {
	}

	public ReactiveParameterException(String message) {
		super(message);
	}

	public ReactiveParameterException(Throwable cause) {
		super(cause);
	}

	public ReactiveParameterException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReactiveParameterException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
