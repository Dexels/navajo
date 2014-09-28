package com.dexels.oauth.api.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.oauth.api.ClientRegistration;
import com.dexels.oauth.api.ClientStore;

public class InMemoryClientStore implements ClientStore {

	private final Map<String, ClientRegistration> clientRegistration = new HashMap<String, ClientRegistration>();
	private final Map<String, ClientRegistration> clientRegistrationByToken = new HashMap<String, ClientRegistration>();
	private final Map<ClientRegistration, Long> regTTL = new HashMap<ClientRegistration, Long>();
	private static final long TTL = 60000;

	
	private final static Logger logger = LoggerFactory
			.getLogger(InMemoryClientStore.class);
	
	@Override
	public void registerClient(String clientId, ClientRegistration registration) {
		clientRegistration.put(clientId, registration);
		String token = registration.getPassword();
		clientRegistrationByToken.put(token, registration);
		
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


	private ClientRegistration verifyClientRegistration(ClientRegistration cr) {
		Long registered = this.regTTL.get(cr);
		if(registered==null) {
			logger.warn("Missing TTL entry for client registration: "+cr.getClientId());
			return null;
		}
		boolean expired = (registered + TTL) < System.currentTimeMillis();  
		if(expired) {
			regTTL.remove(cr);
			clientRegistration.remove(cr.getClientId());
			clientRegistrationByToken.remove(cr.getPassword());
			return null;
		}
		return cr;
	}


}
