package com.dexels.navajo.authorization;

public interface TokenManager {
	public ClientApplication getClient(String clientId);
	public String getToken(String code);

}
