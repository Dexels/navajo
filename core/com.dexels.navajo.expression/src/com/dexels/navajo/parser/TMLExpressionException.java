package com.dexels.navajo.parser;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class TMLExpressionException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 447452587961773391L;
	private String message = "";

    public TMLExpressionException(String s) {
        this.message = "Invalid expression. " + s;
    }

    public TMLExpressionException(String s, Throwable cause) {
    	super(cause);
    	this.message = "Invalid expression. " + s;
    }
    
    public TMLExpressionException(FunctionInterface function,String s, Throwable cause) {
    	super(cause);
        String usage = function.usage();
        String remarks = function.remarks();
        this.message = s + "\nUsage: " + usage + "\nRemarks: " + remarks;
    }

    
    public TMLExpressionException(FunctionInterface function, String s) {
        String usage = function.usage();
        String remarks = function.remarks();
        this.message = s + "\nUsage: " + usage + "\nRemarks: " + remarks;
    }

    public String getMessage() {
        return this.message;
    }
}
