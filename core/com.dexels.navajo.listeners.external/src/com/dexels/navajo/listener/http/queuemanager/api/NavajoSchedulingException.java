/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.listener.http.queuemanager.api;


public class NavajoSchedulingException extends Exception {
	
	private static final long serialVersionUID = -4663637851802409047L;
	private int reason = UNKNOWN;
	public static final int UNKNOWN = -1;
	public static final int SCRIPT_PROBLEM = 0;
	public static final int REQUEST_REFUSED = 1;
	
	public int getReason() {
		return reason;
	}
	

	public NavajoSchedulingException(int reason, String message, Exception e) {
		super(message,e);
		this.reason = reason;
	}

	public NavajoSchedulingException(int reason, String message) {
		super(message);
		this.reason = reason;
	}	
}
