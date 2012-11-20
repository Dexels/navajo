package com.dexels.navajo.client.sessiontoken;

public interface SessionTokenProvider {

	public abstract String getSessionToken();
	public void reset();
}