package com.dexels.navajo.client.sessiontoken;

public class SessionTokenFactory  {

	private volatile static SessionTokenProvider instance = null;

	private SessionTokenFactory() {
		
	}
	
	
	public synchronized static SessionTokenProvider getSessionTokenProvider() {

		if ( instance == null ) {
				instance = new DefaultSessionTokenProvider();
		
		}
		return instance;
	}

	public synchronized static void setSessionTokenProvider(SessionTokenProvider provider) {
		instance = provider;
	}
	
	public static void main(String [] args) {
		SessionTokenProvider info = SessionTokenFactory.getSessionTokenProvider();
		System.err.println(info);
	}
	
	public static void clearInstance() {
		instance = null;
	}
}
