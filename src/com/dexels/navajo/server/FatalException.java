package com.dexels.navajo.server;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class FatalException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6962064660769014280L;

	public FatalException(String s) {
        super(s);
    }
}
