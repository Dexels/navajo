

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

    private int code;
    private int level;
    private String message;

    public ClientException(int level, int code, String message) {

        this.code = code;
        this.level = level;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public int getLevel() {
        return this.level;
    }

    public String getMessage() {
        return this.message;
    }
}
