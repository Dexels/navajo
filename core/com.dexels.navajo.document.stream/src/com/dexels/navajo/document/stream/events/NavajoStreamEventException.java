/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.stream.events;

public class NavajoStreamEventException extends RuntimeException {

	private static final long serialVersionUID = -6524813949471907785L;

	public NavajoStreamEventException() {
	}

	public NavajoStreamEventException(String message) {
		super(message);
	}

	public NavajoStreamEventException(Throwable cause) {
		super(cause);
	}

	public NavajoStreamEventException(String message, Throwable cause) {
		super(message, cause);
	}

	public NavajoStreamEventException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
