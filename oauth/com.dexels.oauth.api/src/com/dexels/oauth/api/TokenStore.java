package com.dexels.oauth.api;

import java.util.Map;

public interface TokenStore {
	public Token generateToken(String client_id, String[] scopes,String username, Map<String,String> userAttributes, String redirect_uri);
	public Token getTokenByString(String token);
}
