package com.dexels.navajo.authentication.api;


public interface AuthenticationFactory {
	 public AAAInterface getAuthenticationModule();
	 public void addAuthenticationModule(AAAInterface a);

}
