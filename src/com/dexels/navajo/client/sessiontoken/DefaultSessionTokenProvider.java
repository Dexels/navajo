package com.dexels.navajo.client.sessiontoken;

import java.net.InetAddress;

public class DefaultSessionTokenProvider implements SessionTokenProvider {

	
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
//			e.printStackTrace();
			System.err.println("Session failed!");
			fabricatedToken="unknown session";
		}
		return fabricatedToken;	
	}

}
