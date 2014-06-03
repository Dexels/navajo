package com.dexels.oauth.api;

public interface ScopeValidator {
	public String getClientName(String clientId);
	public String getScopeDescription(String s);
	public boolean isValidScope(String s);
}
