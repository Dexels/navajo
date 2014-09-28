package com.dexels.oauth.api;

public interface ClientStore {
	public void registerClient(String clientId, ClientRegistration registration) throws ClientStoreException;
	public boolean verifyRedirectURL(String clientId, String redirect_uri) throws ClientStoreException;
	public ClientRegistration getClient(String clientId) throws ClientStoreException;
}
