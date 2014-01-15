package com.dexels.navajo.authorization;

public interface ClientApplication {
	public String getClientId();
	public String getClientSecret();
	public String getRedirectionUri();
}
