/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.article;

public class APIException extends Exception {
	private static final long serialVersionUID = -5492265925114738046L;
	
	private APIErrorCode errorCode;
	
	public APIException (String message, Throwable cause, APIErrorCode errorCode) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public APIErrorCode getErrorCode() {
		return errorCode;
	}
}
