package com.dexels.navajo.functions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dexels.navajo.parser.FunctionInterface;

/**
 * Title:        Navajo
 * Description:  Checks if the given Object is a java.util.Date and is in the future
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Erik Versteeg
 * @version $Id$
 */
public class IsFutureDate extends FunctionInterface {
    public IsFutureDate() {}

    public String remarks() {
        return "Checks if a (string) object can be transformed to a date.";
    }

    public String usage() {
        return "IsFutureDate(String s|Date d)";
    }

    public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Object o = this.getOperand(0);
        java.util.Date date = null;
        if (o instanceof java.util.Date) {
            date = (java.util.Date) o;
        } else if (o instanceof String) {
            // Check if a format is given
            // Going to guess some formats now by using the navajo function ToDate()
            ParseDate td = new ParseDate();
            td.reset();
            td.insertOperand(o);
            date = (java.util.Date)td.evaluate();
        } else {
            // Feel free to implement here
        }
        
        if (date != null && date.after(new java.util.Date())) {
        	return Boolean.TRUE;
        } else {
        	return Boolean.FALSE;
        }
    }

    public static void main(String args[]) throws Exception {
        List<String> dates = new ArrayList<String>();
        dates.add("1997-02-05");
        dates.add("01-01-2001");
        dates.add("01-01-2021");
        dates.add("");
        dates.add(null);
        dates.add("01-02-2007");
        dates.add("11/03/1985");
        dates.add("12/06/84");
        dates.add("06/14/1982");
        dates.add("06-05-87");
        dates.add("06-05-187");
        IsFutureDate id = new IsFutureDate();
        for (String date : dates) {
            id.reset();
            id.insertOperand(date);
            System.err.println("Date (" + date + ") ok = " + id.evaluate());
        }
    }
}
