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


public class UserException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3312656813477111502L;
	
	public static final int MISSING_MESSAGE = 0x03;
    public static final int MISSING_PROPERTY = 0x04;
    public static final int MISSING_ATTRIBUTE = 0x05;
    public static final int MISSING_ATTRIBUTE_VALUE = 0x06;
    public static final int DATE_FORMAT_ERROR = 0x07;
    public static final int NUMBER_FORMAT_ERROR = 0x08;
    public static final int PARSE_ERROR = 0x0F;
    public static final int IO_ERROR = 0x10;
    public static final int CONDITION_ERROR = 0x12;
    public static final int OPERATION_NOT_PERMITTED = 0x13;
    public static final int INVALID_INPUT = 0xFB;
    public static final int USER_EXISTS = 0xFA;
    public static final int INVALID_ACCOUNT_NR = 0xF0;
    public static final int BREAK_EXCEPTION = 0x14;
    
    public final int code;

    public UserException() {
        super();
        code = -1;
    }
    
    public UserException(String message) {
    	super(message);
    	code = -1;
    }

    public UserException(int code, String message, Throwable t) {
    	super(message,t);
        this.code = code;
    }

    public UserException(String message, Throwable t) {
    	super(message,t);
        this.code = -1;
    }

    public UserException(int code, String message) {
    	super(message);
        this.code = code;
    }

    public int getCode() {
       return code;
     }
}
