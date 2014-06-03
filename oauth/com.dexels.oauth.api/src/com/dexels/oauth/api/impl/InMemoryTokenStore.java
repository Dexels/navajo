package com.dexels.oauth.api.impl;

import java.util.HashMap;
import java.util.Map;

import com.dexels.oauth.api.Token;
import com.dexels.oauth.api.TokenStore;

public class InMemoryTokenStore implements TokenStore {

	private final Map<String,Token> tokenMap = new HashMap<String,Token>();
	
	@Override
	public Token generateToken(String client_id, String[] scopes,
			String username, String redirect_uri) {
		SimpleToken t = new SimpleToken(client_id,scopes,username,redirect_uri);
		tokenMap.put(t.toString(), t);
		return t;
	}

	@Override
	public Token getTokenByString(String token) {
		return tokenMap.get(token);
	}

	

}
