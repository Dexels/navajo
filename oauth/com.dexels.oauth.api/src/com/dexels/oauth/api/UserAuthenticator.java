package com.dexels.oauth.api;

public interface UserAuthenticator {
	public boolean authenticateUser(String username, String password);
}
