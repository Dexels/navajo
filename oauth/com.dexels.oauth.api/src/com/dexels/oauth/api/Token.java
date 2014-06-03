package com.dexels.oauth.api;

import java.util.Set;

public interface Token {
	public String clientId();
	public Set<String> scopes();
	public boolean isExpired();
	public String toString();
	public String getUsername();
	public int getExpirySeconds();
}
