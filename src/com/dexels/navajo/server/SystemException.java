

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.server;


public class SystemException extends Exception {

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

    public int code;
    public String message = "";
    public Throwable t;

    public SystemException() {
      super();
    }

    public SystemException(int code, String message) {
      this.code = code;
      this.message = message;
    }

    public SystemException(int code, String message, Throwable t) {
      this.code = code;
      this.message = message;
      this.t = t;

      // Util.debugLog(0, this.message);
      // Maybe write something to LOG.
      // Send an e-mail if the error code is in the trigger list.
    }

    public String getMessage() {
      return message;
    }
}
