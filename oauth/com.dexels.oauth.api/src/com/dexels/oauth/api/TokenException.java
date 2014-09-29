package com.dexels.oauth.api;

public class TokenException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6064179740025144388L;

	public TokenException() {
	}

	public TokenException(String message) {
		super(message);
	}

	public TokenException(Throwable cause) {
		super(cause);
	}

	public TokenException(String message, Throwable cause) {
		super(message, cause);
	}

	public TokenException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
