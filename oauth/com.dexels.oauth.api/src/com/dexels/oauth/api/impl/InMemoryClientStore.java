package com.dexels.oauth.api.impl;

import java.util.HashMap;
import java.util.Map;

import com.dexels.oauth.api.ClientRegistration;
import com.dexels.oauth.api.ClientStore;

public class InMemoryClientStore implements ClientStore {

	private final Map<String, ClientRegistration> clientRegistration = new HashMap<String, ClientRegistration>();
		
	@Override
	public void registerClient(String clientId, ClientRegistration registration) {
		clientRegistration.put(clientId, registration);
//		http://localhost:8080/oauth?redirect_uri=http://localhost:8080/ui/resource.html&response_type=token&client_id=123&scope=aapje,olifantje&state=456
	}

	@Override
	public boolean verifyRedirectURL(String clientId, String redirect_uri) {
		ClientRegistration cl = clientRegistration.get(clientId);
		if(cl==null) {
			throw new IllegalArgumentException("ClientId missing: "+clientId);
		}
		String redir = cl.getRedirectUriPrefix();
		if(redirect_uri.startsWith(redir)) {
			return true;
		}
		return false;
	}

	@Override
	public ClientRegistration getClient(String clientId) {
		return clientRegistration.get(clientId);
	}

}
