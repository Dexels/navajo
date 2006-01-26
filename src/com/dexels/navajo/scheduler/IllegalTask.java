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
 */
public class IllegalTask extends Exception {

	private static String msg = "Illegal Task: ";
	String message = "";
	
	public IllegalTask(String s) {
		message = msg + s;
	}
	
	public String getMessage() {
		return message;
	}
}
