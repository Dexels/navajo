package com.dexels.oauth.api;

public interface TokenStore {
	public Token generateToken(String client_id, String[] scopes,String username, String redirect_uri);
	public Token getTokenByString(String token);
}
