package com.dexels.navajo.client.sessiontoken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionTokenFactory  {

	private volatile static SessionTokenProvider instance = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(SessionTokenFactory.class);
	
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
		logger.info(""+info);
	}
	
	public static void clearInstance() {
		instance = null;
	}
}
