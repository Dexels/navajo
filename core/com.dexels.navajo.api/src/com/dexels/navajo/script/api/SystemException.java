/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/


/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.script.api;


public class SystemException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7429135215734290433L;
	
	public static final int NAVAJO_UNAVAILABLE = 0x00;
    public static final int INIT_ERROR = 0x02;
    public static final int PARSE_ERROR = 0x0F;
    public static final int IO_ERROR = 0x10;
    public static final int NOT_AUTHENTICATED = 0x09;
    public static final int NOT_AUTHORISED = 0x0A;
    public static final int UNKNOWN_RPC_NAME = 0x01;
    public static final int MISSING_MESSAGE = 0x03;
    public static final int MISSING_PROPERTY = 0x04;
    public static final int MISSING_ATTRIBUTE = 0x05;
    public static final int MISSING_ATTRIBUTE_VALUE = 0x06;	
	public static final int SYSTEM_ERROR = 0xAA;
	public static final int LOCKS_EXCEEDED = 0xFF;
    

    public final int code;


    public SystemException() {
      super();
      code = -1;
    }

    public SystemException(int code, String message) {
    	super(message);
    	this.code = code;
    }
    public SystemException(String message, Throwable t) {
      super(message,t);
      this.code = -1;
    }

    
	public SystemException(int code, String message, Throwable t) {
		super(message,t);
		this.code = code;
	}
}
