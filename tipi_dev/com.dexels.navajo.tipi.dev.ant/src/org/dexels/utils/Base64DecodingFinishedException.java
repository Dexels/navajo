/*
 * Created on May 15, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.dexels.utils;

import java.io.IOException;

public class Base64DecodingFinishedException extends IOException {
	
	private static final long serialVersionUID = -2693614501104506907L;

	/**
	 * @param data  
	 */
	public Base64DecodingFinishedException(char[] data, int offset, int len) {
        System.err.println("Base64 finished at: "+offset+" len: "+len);
    }


}
