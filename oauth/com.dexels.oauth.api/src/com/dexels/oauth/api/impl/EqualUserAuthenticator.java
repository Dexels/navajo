package com.dexels.oauth.api.impl;

import com.dexels.oauth.api.UserAuthenticator;

public class EqualUserAuthenticator implements UserAuthenticator {

	@Override
	public boolean authenticateUser(String username, String password) {
		return username.equals(password);
	}

}
