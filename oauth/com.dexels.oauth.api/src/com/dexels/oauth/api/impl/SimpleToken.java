package com.dexels.oauth.api.impl;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.dexels.oauth.api.Token;

public class SimpleToken implements Token {

	//(in sec:)
	private final String client_id;
	private final Set<String> scopes = new HashSet<String>();
	private final String username;
	private final String token;
	private final Map<String,Object> userAttributes;
	private final Date expire;

	public SimpleToken(String client_id, String[] scopes, String username, Map<String,Object> userAttributes,
			String redirect_uri,Date expireDate) {
		this.client_id = client_id;
		for (String s : scopes) {
			this.scopes.add(s);
		}
//		this.scopes = scopes;
		this.userAttributes = userAttributes;
		this.username = username;
		this.token = generateRandom();
		this.expire = expireDate;
	}

	@Override
	public String clientId() {
		return client_id;
	}

	@Override
	public Set<String> scopes() {
		return Collections.unmodifiableSet(scopes);
	}

	@Override
	public boolean isExpired() {
		return System.currentTimeMillis() > expire.getTime();
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public Map<String,Object> getUserAttributes() {
		return userAttributes;
	}
	
	private String generateRandom() {
		 SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

	@Override
	public String toString() {
		return this.token;
	}

	@Override
	public int getExpirySeconds() {
		return (int) ((expire.getTime() - System.currentTimeMillis()) / 1000);
	}

	@Override
	public Date getExpiryDate() {
		return expire;
	}

	
}
