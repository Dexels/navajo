package com.dexels.navajo.client.sessiontoken;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSessionTokenProvider implements SessionTokenProvider {

	
	private final static Logger logger = LoggerFactory.getLogger(DefaultSessionTokenProvider.class);
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
