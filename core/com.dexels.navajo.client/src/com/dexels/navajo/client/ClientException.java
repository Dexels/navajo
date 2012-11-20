

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.client;

public class ClientException extends Exception {

   private static final long serialVersionUID = 75302782005830988L;
	private int code;
    private int level;

    public ClientException(int level, int code, String message) {
    	super(message);
        this.code = code;
        this.level = level;
    }

    public ClientException(int level, int code, String message, Throwable cause) {
    	super(message,cause);
        this.code = code;
        this.level = level;
    }

    
    public int getCode() {
        return this.code;
    }

    public int getLevel() {
        return this.level;
    }
}
