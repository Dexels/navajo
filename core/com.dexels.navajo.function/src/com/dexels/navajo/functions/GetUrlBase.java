/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.expression.api.FunctionInterface;

/*
 * Some utility functions for URLs
 */

public abstract class GetUrlBase extends FunctionInterface {

	
	private final static Logger logger = LoggerFactory
			.getLogger(GetUrlBase.class);
	
    protected static Date getUrlDate(URL u) {
        try {
        	HttpURLConnection uc = (HttpURLConnection)u.openConnection();
        	uc.setConnectTimeout(1000);
        	uc.setRequestMethod("HEAD");
            Date d = new Date(uc.getLastModified());
           return d;
        } catch (IOException e) {
        	logger.warn("getUrlTime failed.", e);
           return null;
        }
    }

    protected static Date getUrlTime(URL u) {
        try {
        	HttpURLConnection uc = (HttpURLConnection)u.openConnection();
        	uc.setConnectTimeout(1000);
        	uc.setRequestMethod("HEAD");
            Date d = new Date(uc.getDate());
            return d;
        } catch (IOException e) {
        	logger.warn("getUrlTime failed.", e);
        	return null;
        }
    }
	
    protected static int getUrlLength(URL u) {
        try {
        	HttpURLConnection uc = (HttpURLConnection)u.openConnection();
        	uc.setConnectTimeout(1000);
        	uc.setRequestMethod("HEAD");
            int d = uc.getContentLength();
           return d;
        } catch (IOException e) {
        	logger.warn("getUrlLength failed.", e);
           return 0;
        }
    }
    protected static String getUrlType(URL u) {
		try {
        	HttpURLConnection uc = (HttpURLConnection)u.openConnection();
        	uc.setConnectTimeout(1000);
        	uc.setRequestMethod("HEAD");
			String type = uc.getContentType();
			return type;
		} catch (IOException e) {
        	logger.warn("getUrlType failed.", e);
			return null;
		}
	}


}
