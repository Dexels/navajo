package com.dexels.navajo.functions;


import com.dexels.navajo.parser.*;
import java.util.*;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 */

public final class DateField extends FunctionInterface {

    public DateField() {}

    public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        java.util.Date date = (java.util.Date) this.getOperands().get(0);
        String field = (String) this.getOperands().get(1);
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        //System.out.println("date  = " + date.toString());
        //System.out.println("field = " + field);
        //System.out.println("WEEK  = " + cal.get(Calendar.WEEK_OF_YEAR));

        if (field.equals("WEEK"))
            return new Integer(cal.get(Calendar.WEEK_OF_YEAR));
        else if (field.equals("YEAR"))
            return new Integer(cal.get(Calendar.YEAR));
        else if (field.equals("DAY"))
            return new Integer(cal.get(Calendar.DAY_OF_MONTH));
        else
            return new Integer(-1);
    }

    public String usage() {
        return "DateField(date, field)";
    }

    public String remarks() {
        return "";
    }

    public static void main(String args[]) {

        Calendar cal = Calendar.getInstance();

        cal.set(2001, 1, 1);
        System.out.println(cal.getTime().toString());
        System.out.println(cal.get(Calendar.WEEK_OF_YEAR));

    }
}
