package com.dexels.navajo.authentication.api;

import java.util.Map;


public interface AAAFactoryInterface {
	 public AAAInterface getAuthenticationModule(String instance);
	 public void addAuthenticationModule(AAAInterface a, Map<String,Object> settings);

}
