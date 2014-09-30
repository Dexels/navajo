package com.dexels.oauth.api;

import java.util.Date;
import java.util.Map;

public interface TokenStore {
	public Token generateToken(String client_id, String[] scopes,String username, Map<String,Object> userAttributes, String redirect_uri, Date expireDate) throws TokenException;
	public Token getTokenByString(String token) throws TokenException;
}
