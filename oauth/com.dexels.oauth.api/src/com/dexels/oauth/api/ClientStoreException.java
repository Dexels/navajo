package com.dexels.oauth.api;

public class ClientStoreException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6064179740025144388L;

	public ClientStoreException() {
	}

	public ClientStoreException(String message) {
		super(message);
	}

	public ClientStoreException(Throwable cause) {
		super(cause);
	}

	public ClientStoreException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientStoreException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
