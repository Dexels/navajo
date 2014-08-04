package com.dexels.oauth.api.impl;

import java.util.HashMap;
import java.util.Map;

import com.dexels.oauth.api.UserAuthenticator;

public class EqualUserAuthenticator implements UserAuthenticator {

	@Override
	public Map<String,String> authenticateUser(String username, String password, String clientId) {
		Map<String,String> result = new HashMap<String,String>();
		result.put("username", username);
		result.put("clientId", clientId);
		return username.equals(password)?result:null;
	}

}
