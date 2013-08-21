package com.dexels.navajo.entity;

import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.server.UserException;

public class EntityException extends UserException {

	private static final long serialVersionUID = -6023245834578811854L;

	// HTTP
	public final static int OK = 200;
	public final static int ERROR = 500;
	public final static int BAD_REQUEST = 400;
	public final static int ENTITY_NOT_FOUND = 404;
	public final static int OPERATION_NOT_SUPPORTED = 405;
	public final static int CONFLICT = 409;
	
	// SPECIFIC
	public final static int ENTITY_LOOP = 600;
	public final static int UNKNOWN_PARENT_TYPE = 601;
	public final static int MISSING_ID = 602;
	
	private static Map<Integer,String> errorCodes = new HashMap<Integer, String>();
	
	static {
		errorCodes.put(CONFLICT, "Update conflict");
		errorCodes.put(ERROR, "General Error");
		errorCodes.put(ENTITY_LOOP, "Cannot add myself as subentity");
		errorCodes.put(UNKNOWN_PARENT_TYPE, "Extension type not implemented");
		errorCodes.put(ENTITY_NOT_FOUND, "Entity not found");
		errorCodes.put(OPERATION_NOT_SUPPORTED, "Operation not supported");
		errorCodes.put(MISSING_ID, "Missing entity id");
		errorCodes.put(BAD_REQUEST, "Invalid entity request");
	}
	
	public EntityException() {
	}

	public EntityException(int code, String msg) {
		super(code, errorCodes.get(code) + " : " + msg);
	}
	
	public EntityException(int code) {
		super(code, errorCodes.get(code));
	}

	public EntityException(Throwable arg0) {
		super(ERROR, errorCodes.get(ERROR));
	}

	public EntityException(int code, String msg, Throwable arg1) {
		super(code, errorCodes.get(code) + " : " + msg, arg1);
	}
	
	public EntityException(int code, Throwable arg1) {
		super(code, errorCodes.get(code), arg1);
	}

}
