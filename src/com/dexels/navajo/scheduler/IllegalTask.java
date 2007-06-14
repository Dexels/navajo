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
 */
public class IllegalTask extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1597289698912784893L;
	
	private static String msg = "Illegal Task: ";
	String message = "";
	
	public IllegalTask(String s) {
		message = msg + s;
	}
	
	public String getMessage() {
		return message;
	}
}
