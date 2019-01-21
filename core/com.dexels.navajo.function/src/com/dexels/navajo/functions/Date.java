package com.dexels.navajo.functions;


import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version $Id$
 */

public final class Date extends FunctionInterface {

    public Date() {}

    @Override
	public String remarks() {
        return "Create a date object from a given string. String is expected to be of format: yyyy-MM-dd.";
    }

    @Override
	public String usage() {
        return "Date(String s)";
    }
    @Override
	public boolean isPure() {
    		return true;
    }

    @Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
        String arg = (String) this.getOperands().get(0);
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;

        try {
            date = format.parse(arg);
        } catch (Exception e) {
            throw new TMLExpressionException("Invalid date format: " + arg);
        }
        return date;
    }
}
