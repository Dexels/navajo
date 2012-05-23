package com.dexels.navajo.client.sessiontoken;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSessionTokenProvider implements SessionTokenProvider {

	
	private final static Logger logger = LoggerFactory.getLogger(DefaultSessionTokenProvider.class);
	
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
		String userName = null;
		try {
			userName = System.getProperty("user.name");
		} catch (SecurityException e) {
			userName = "UnknownUser";
		}

		String fabricatedToken = null;
		
		try {
			fabricatedToken = userName + "|" + (InetAddress.getLocalHost().getHostAddress())
					+ "|" + (InetAddress.getLocalHost().getHostName()) + "|"
					+ (System.currentTimeMillis());
		} catch (Throwable e) {
			logger.error("Error: ", e);
			logger.info("Session failed!");
			fabricatedToken="unknown session";
		}
		return fabricatedToken;	
	}

}
