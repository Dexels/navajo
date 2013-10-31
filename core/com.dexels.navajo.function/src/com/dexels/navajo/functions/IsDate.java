package com.dexels.navajo.functions;

import java.util.ArrayList;
import java.util.List;

import com.dexels.navajo.parser.FunctionInterface;

/**
 * Title:        Navajo
 * Description:  Checks if the given Object is a java.util.Date or can be converted to it
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Erik Versteeg
 * @version $Id$
 */
public class IsDate extends FunctionInterface {
    public IsDate() {}

    @Override
	public String remarks() {
        return "Checks if a (string) object can be transformed to a date.";
    }

    @Override
	public String usage() {
        return "IsDate(String s|Date d)";
    }

    @Override
	public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Object o = this.getOperand(0);
        if (o instanceof java.util.Date) {
            return Boolean.TRUE;
        } else if (o instanceof String) {
            // Check if a format is given
            java.util.Date date = null;
            // Going to guess some formats now by using the navajo function ToDate()
            ParseDate td = new ParseDate();
            td.reset();
            td.insertOperand(o);
            date = (java.util.Date)td.evaluate();
            
            if (date != null) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } else {
            // Feel free to implement here
            return Boolean.FALSE;
        }
    }

    public static void main(String args[]) throws Exception {
        List<String> dates = new ArrayList<String>();
        dates.add("1997-02-05");
        dates.add("01-01-2001");
        dates.add("01-02-2007");
        dates.add("11/03/1985");
        dates.add("12/06/84");
        dates.add("06/14/1982");
        dates.add("06-05-87");
        dates.add("06-05-187");
        IsDate id = new IsDate();
        for (String date : dates) {
            id.reset();
            id.insertOperand(date);
            System.err.println("Date (" + date + ") ok = " + id.evaluate());
        }
    }
}
