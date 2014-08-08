package com.dexels.oauth.api;

import java.util.Map;

public interface ClientRegistration {
	public String getClientId();
	public String getRedirectUriPrefix();
	public String getClientDescription();
	public String getAccessToken();
	public Map<String,String> getAttributes();
	public String getUsername();
}
