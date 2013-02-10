package com.dexels.navajo.server;

public class UserException extends com.dexels.navajo.script.api.UserException {

	private static final long serialVersionUID = -8650710088920995622L;


	public UserException() {
        super();
    }

    public UserException(int code, String message, Throwable t) {
    	super(code,message,t);
    }


    public UserException(int code, String message) {
    	super(code, message);
    }
}
