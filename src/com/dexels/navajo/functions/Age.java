package com.dexels.navajo.functions;


import com.dexels.navajo.parser.*;

/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version $Id$
 */
import java.util.*;


public class Age extends FunctionInterface {

    public Age() {}

    public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

        Object o = this.getOperands().get(0);
        if (o == null | !(o instanceof java.util.Date))
          throw new TMLExpressionException("Age: could not return value for input: " + o);

        java.util.Date datum = (java.util.Date) o;

        Calendar cal = Calendar.getInstance();

        cal.setTime(datum);
        Calendar today = Calendar.getInstance();

        today.setTime(new java.util.Date());
        int yToday = today.get(Calendar.YEAR);
        int dToday = cal.get(Calendar.YEAR);
        int result = yToday - dToday;

        if ((cal.get(Calendar.MONTH) > today.get(Calendar.MONTH))) {
          result--;
        }
        else if ((cal.get(Calendar.MONTH) == today.get(Calendar.MONTH)) &&
            (cal.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH))) {
          result--;
        }

        return new Integer(result);
    }

    public String usage() {
        return "";
    }

    public String remarks() {
        return "";
    }

    public static void main(String args[]) throws Exception {
        Age a = new Age();
        Calendar c = Calendar.getInstance();

        c.set(1966, 11, 20);
        System.err.println("c =" + c.getTime());
        a.reset();
        a.insertOperand(c.getTime());
        Integer age = (Integer) a.evaluate();

        System.out.println("age = " + age.intValue());

        System.out.println("MAX INT = " + Integer.MAX_VALUE);
        System.out.println("MAX LONG = " + Long.MAX_VALUE);
    }
}
