/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Created on May 15, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.dexels.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Base64DecodingFinishedException extends IOException {

	private static final long serialVersionUID = -2693614501104506907L;
	
	private static final Logger logger = LoggerFactory
			.getLogger(Base64DecodingFinishedException.class);
	
	public Base64DecodingFinishedException(char[] data, int offset, int len) {
		if (data==null) {
	        logger.info("Base64 finished at: "+offset+" len: "+len+" data array null ");
		} else {
	        logger.info("Base64 finished at: "+offset+" len: "+len+" data array len: "+data.length);
		}
    }


}
