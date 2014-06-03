package com.dexels.oauth.api.impl;

import com.dexels.oauth.api.ScopeValidator;

public class SimpleScopeValidator implements ScopeValidator {

	@Override
	public String getClientName(String clientId) {
		return "Simple Scope for client: "+clientId;
	}

	@Override
	public String getScopeDescription(String s) {
		return "Description of: "+s;
	}

	@Override
	public boolean isValidScope(String s) {
		return true;
	}

}
