package com.dexels.navajo.authentication.api;

import java.util.Map;


public interface AuthenticationFactory {
	 public AAAInterface getAuthenticationModule();
	 public void addAuthenticationModule(AAAInterface a, Map<String,Object> settings);

}
