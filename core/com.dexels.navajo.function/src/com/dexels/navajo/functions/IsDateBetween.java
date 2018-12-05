package com.dexels.navajo.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

/**
 * Title:        Navajo
 * Description:  Checks if the given Objects are java.util.Date and is between the given dates (inclusive)
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Erik Versteeg
 * @version $Id$
 */
public class IsDateBetween extends FunctionInterface {
    private final static Logger logger = LoggerFactory.getLogger(IsDateBetween.class);

    public IsDateBetween() {}

    @Override
	public String remarks() {
        return "Checks if (string|date) object is between given dates.";
    }

    @Override
	public String usage() {
        return "IsDateBetween(String sCheck|Date dCheck, String sFrom|Date dFrom, String sTo|Date dTo)";
    }

    @Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
	    if (getOperands().size() != 3) {
            throw new TMLExpressionException(this, "Three operands expected. ");
        }
	    
        java.util.Date dCheck = convertToDate(this.getOperand(0));
        java.util.Date dFrom = convertToDate(this.getOperand(1));
        java.util.Date dTo = convertToDate(this.getOperand(2));
        
        if ( ( dCheck != null && dTo != null && dFrom != null ) && ( ( dCheck.after(dFrom) || dCheck.equals(dFrom) ) && ( dCheck.before(dTo) || dCheck.equals(dTo) ) ) ) {
        	return Boolean.TRUE;
        } else {
        	return Boolean.FALSE;
        }
    }
    
    private java.util.Date convertToDate(Object o) {
    	if (o instanceof java.util.Date) {
            return (java.util.Date) o;
        } else if (o instanceof String) {
            try
            {
                // Going to guess some formats now by using the navajo function ParseDate()
                ParseDate td = new ParseDate();
                td.reset();
                td.insertOperand(o);
                return (java.util.Date)td.evaluate();
            }
            catch (TMLExpressionException tee)
            {
                logger.debug("Not a date", tee);
            }
        } else {
            // Feel free to implement here
        }
    	return null;
    }

}
