

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.server;


public class UserException extends Exception {

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

    public int code;
    public String message = "";
    // public int level = 1;

    public UserException() {
        super();
    }

    public UserException(int code, String message) {

        this.code = code;
        this.message = message;

        // Util.debugLog(0, this.message);
        // Maybe write something to LOG.
        // Send an e-mail if the error code is in the trigger list.
    }

    public String getMessage() {
        return message;
    }
}
