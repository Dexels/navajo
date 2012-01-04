/*
 * Created on May 15, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.dexels.utils;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base64DecodingFinishedException extends IOException {

	private static final long serialVersionUID = -2693614501104506907L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(Base64DecodingFinishedException.class);
	
	public Base64DecodingFinishedException(char[] data, int offset, int len) {
        logger.info("Base64 finished at: "+offset+" len: "+len);
    }


}
