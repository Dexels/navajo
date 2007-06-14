/*
 * Created on 26-jan-2006
 *
 * @author Dexels developer
 * @version $Id$
 */
package com.dexels.navajo.scheduler;

import java.io.Serializable;

/**
 * @author Arjen
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class IllegalTrigger extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7430392408033718411L;
	
	private static String msg = "Illegal Trigger: ";
	String message = "";
	
	public IllegalTrigger(String s) {
		message = msg + s;
	}
	
	public String getMessage() {
		return message;
	}
	
}
