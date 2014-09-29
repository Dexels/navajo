package com.dexels.oauth.api;

import java.util.Map;

public interface ClientRegistration {
	public String getClientId();
	public String getRedirectUriPrefix();
	public String getClientDescription();
	public Map<String, Object> getUserAttributes();
	public String getUsername();
	public String getPassword();
}
