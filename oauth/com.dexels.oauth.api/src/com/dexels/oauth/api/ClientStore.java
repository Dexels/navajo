package com.dexels.oauth.api;

public interface ClientStore {
	public void registerClient(String clientId, ClientRegistration registration);
	public boolean verifyRedirectURL(String clientId, String redirect_uri);
	public ClientRegistration getClient(String clientId);
}
