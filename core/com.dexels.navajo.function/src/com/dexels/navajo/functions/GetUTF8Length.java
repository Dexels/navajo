package com.dexels.navajo.functions;


import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Marte Koning
 * @version $Id$
 */

public final class GetUTF8Length extends FunctionInterface {

	
	private final static Logger logger = LoggerFactory
			.getLogger(GetUTF8Length.class);
	
    public GetUTF8Length() {}

    @Override
	public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    	if(this.getOperands().size()==0) {
    		return new Integer(0);
    	}
    	if(this.getOperands().size()==1 && this.getOperands().get(0)==null) {
    		return new Integer(0);
    	}    	
    	if(this.getOperands().size() > 1) {
            throw new TMLExpressionException("Only a single argument supported for GetUTF8Length() function.");
    	}
    	
    	Object arg = this.getOperands().get(0);

        //System.out.println("IN SIZE(), ARG = " + arg);
        if (arg == null) {
            throw new TMLExpressionException("Argument expected for GetUTF8Length() function.");
        }
        else if (!(arg instanceof java.lang.String)) {
            throw new TMLExpressionException("String argument expected for GetUTF8Length() function.");
        }
        String str = (String) arg;
	    try {
			return str.getBytes("UTF-8").length;
		} catch (UnsupportedEncodingException e) {
			logger.error("Error: ", e);
		}
	    return new Integer(0);
    }

    @Override
	public String usage() {
        return "CodePointCount(String)";
    }

    @Override
	public String remarks() {
        return "This function return the size of a string in UTF-8 bytes.";
    }

    public static void main(String [] args) throws Exception {
        String simple = "Marte is gek";
       // String diacritic = "Mart� is gek";
        GetUTF8Length e = new GetUTF8Length();
        e.reset();
        e.insertOperand(simple);
        //e.insertOperand(diacritic);
        try {
            System.out.println(e.evaluate());
        } catch (TMLExpressionException e1) {
            e1.printStackTrace();
        }
    }
}
