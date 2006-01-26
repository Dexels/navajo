/*
 * Created on 26-jan-2006
 *
 * @author Dexels developer
 * @version $Id$
 */
package com.dexels.navajo.scheduler;

/**
 * @author Arjen
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class IllegalTrigger extends Exception {

	private static String msg = "Illegal Trigger: ";
	String message = "";
	
	public IllegalTrigger(String s) {
		message = msg + s;
	}
	
	public String getMessage() {
		return message;
	}
	
}
