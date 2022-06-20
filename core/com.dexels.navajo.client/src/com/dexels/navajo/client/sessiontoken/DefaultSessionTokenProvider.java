/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.client.sessiontoken;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSessionTokenProvider implements SessionTokenProvider {

	
	private static final Logger logger = LoggerFactory.getLogger(DefaultSessionTokenProvider.class);
	private String token  = null;
	
	DefaultSessionTokenProvider() {
		
	}
	


	/* (non-Javadoc)
	 * @see com.dexels.navajo.client.SystemInfoProvider#toString()
	 */
	@Override
	public String toString() {
		return getSessionToken();
	}
	

	// Ultra defensive for app engines.
	  
	@Override
	public String getSessionToken() {
		if(token!=null) {
			return token;
		}
		String userName = null;
		try {
			userName = System.getProperty("user.name");
		} catch (SecurityException e) {
			userName = "UnknownUser";
		}

		
		try {
			token = userName + "|" + (InetAddress.getLocalHost().getHostAddress())
					+ "|" + (InetAddress.getLocalHost().getHostName()) + "|"
					+ (System.currentTimeMillis());
		} catch (Throwable e) {
			logger.error("Error: ", e);
			logger.info("Session failed!");
			token="unknown session";
		}
		return token;	
	}



	@Override
	public void reset() {
		token = null;
	}

}
