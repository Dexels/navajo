package com.dexels.navajo.entity;

import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.script.api.UserException;

public class EntityException extends UserException {

	private static final long serialVersionUID = -6023245834578811854L;

	// HTTP
	public final static int OK = 200;
	
	public final static int NOT_MODIFIED = 304;

	public final static int BAD_REQUEST = 400;
	public final static int UNAUTHORIZED = 401;
	public final static int ENTITY_NOT_FOUND = 404;
	public final static int OPERATION_NOT_SUPPORTED = 405;
	public final static int OUTPUT_NOT_ACCEPTABLE = 406;
	public final static int CONFLICT = 409;
	public final static int ETAG_ERROR = 412;
	public final static int VALIDATION_ERROR = 420;
	
	public final static int SERVER_ERROR = 500;

	// 304: Not yet implemented: could be used for GET operations. Instead of returning entire result
	// return 304 to indicate the "client" that data has not changed. It can use its current data.
	
	// SPECIFIC
	public final static int ENTITY_LOOP = 600;
	public final static int UNKNOWN_PARENT_TYPE = 601;
	public final static int MISSING_ID = 602;
	
	private static Map<Integer,String> errorCodes = new HashMap<Integer, String>();
	
	static {
	    errorCodes.put(NOT_MODIFIED, "Not Modified");
		errorCodes.put(CONFLICT, "Update conflict");
		errorCodes.put(SERVER_ERROR, "Server Error");
		errorCodes.put(ENTITY_LOOP, "Cannot add myself as subentity");
		errorCodes.put(UNKNOWN_PARENT_TYPE, "Extension type not implemented");
		errorCodes.put(ENTITY_NOT_FOUND, "Entity not found");
		errorCodes.put(OPERATION_NOT_SUPPORTED, "Operation not supported");
		errorCodes.put(MISSING_ID, "Missing entity id");
		errorCodes.put(BAD_REQUEST, "Invalid entity request");
		errorCodes.put(VALIDATION_ERROR, "Validation exception");
		errorCodes.put(OUTPUT_NOT_ACCEPTABLE, "Requested output not available");
		errorCodes.put(ETAG_ERROR, "ETag mismatch");
		errorCodes.put(UNAUTHORIZED, "Unauthorized");

	}

    private Navajo navajo = null;
	
	public EntityException() {
	}

	public EntityException(int code, String msg) {
		super(code, errorCodes.get(code) + " : " + msg);
	}
	
	public EntityException(int code, String msg, Navajo navajo) {
        super(code, errorCodes.get(code) + " : " + msg);
        this.navajo = navajo;
    }
	
	public EntityException(int code) {
		super(code, errorCodes.get(code));
	}

	public EntityException(Throwable arg0) {
		super(SERVER_ERROR, errorCodes.get(SERVER_ERROR));
	}

	public EntityException(int code, String msg, Throwable arg1) {
		super(code, errorCodes.get(code) + " : " + msg, arg1);
	}
	
	public EntityException(int code, Throwable arg1) {
		super(code, errorCodes.get(code), arg1);
	}

    public Navajo getNavajo() {
        return navajo;
    }

	
}
