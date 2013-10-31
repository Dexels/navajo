

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
    public SystemException(String message, Throwable t) {
        super(t);
      this.code = -1;
      this.message = message;
      this.t = t;
    }

    
    public SystemException(int code, String message, Throwable t) {
        super(t);
      this.code = code;
      this.message = message;
      this.t = t;
    }

    @Override
	public String getMessage() {
      return message;
    }
}
