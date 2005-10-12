package com.dexels.navajo.functions;


import com.dexels.navajo.parser.*;

import java.util.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public final class DateSubstract extends FunctionInterface {

    public DateSubstract() {}

    public String remarks() {
        return "Substracts two date object";
    }

    public String usage() {
        return "DateSubstract(Date1, Date2)";
    }

    public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        if (this.getOperands().size() != 2)
            throw new TMLExpressionException("DateSubstract(Date1, Date2) expected. Wrong no. of args.");

        java.util.Date date1 = (java.util.Date) this.getOperands().get(0);
        java.util.Date date2 = (java.util.Date) this.getOperands().get(1);
        if (date1==null || date2==null) {
			return null;
		}
        if (!(date1 instanceof java.util.Date))
            throw new TMLExpressionException("DateSubstract(Date1, Date2) expected. Date1 has wrong type.");
        if (!(date2 instanceof java.util.Date))
            throw new TMLExpressionException("DateSubstract(Date1, Date2) expected. Date2 has wrong type.");
        
        long diff = date1.getTime()-date2.getTime();
        int hours = (int)(diff/3600000);
        int millis = (int)(diff % 36000000);
        
        java.util.Date dd = new java.util.Date(diff);
        Calendar cc = Calendar.getInstance();
        cc.clear();
        cc.add(Calendar.HOUR,hours);
        cc.add(Calendar.MILLISECOND,millis);
        return cc.getTime();
    }
}
