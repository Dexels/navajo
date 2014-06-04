package com.dexels.oauth.api.impl;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.dexels.oauth.api.Token;

public class SimpleToken implements Token {

	//(in sec:)
	private static final int TIMEOUT = 30;
	private final String client_id;
	private final Set<String> scopes = new HashSet<String>();
	private final String username;
	private final String token;

	private final long expires = System.currentTimeMillis() + 1000 * TIMEOUT;
	public SimpleToken(String client_id, String[] scopes, String username,
			String redirect_uri) {
		this.client_id = client_id;
		for (String s : scopes) {
			this.scopes.add(s);
		}
//		this.scopes = scopes;
		this.username = username;
		this.token = generateRandom();
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
		return System.currentTimeMillis() > expires;
	}

	@Override
	public String getUsername() {
		return username;
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
		return (int) ((expires - System.currentTimeMillis()) / 1000);
	}


	
}
