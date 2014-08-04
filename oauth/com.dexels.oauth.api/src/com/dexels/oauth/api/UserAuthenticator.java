package com.dexels.oauth.api;

import java.util.Map;

public interface UserAuthenticator {
	/**
	 * 
	 * @param username
	 * @param password
	 * @param clientId
	 * @return Map of user attributes, or null if auth failed
	 */
	public Map<String,String> authenticateUser(String username, String password, String clientId);
}
