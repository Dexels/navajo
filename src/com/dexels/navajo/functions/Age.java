package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;


/**
 * Title:        Navajo Product Project
 * Description:  This is the official source for the Navajo server
 * Copyright:    Copyright (c) 2002
 * Company:      Dexels BV
 * @author Arjen Schoneveld
 * @version 1.0
 */
import java.util.*;

public class Age extends FunctionInterface {

  public Age() {
  }

  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    java.util.Date datum = (java.util.Date) this.getOperands().get(0);
    Calendar cal = Calendar.getInstance();
    cal.setTime(datum);
    Calendar today = Calendar.getInstance();
    today.setTime(new java.util.Date());
    int yToday = today.get(Calendar.YEAR);
    int dToday = cal.get(Calendar.YEAR);
    int result = yToday - dToday;
    if ((cal.get(Calendar.MONTH) >= today.get(Calendar.MONTH)) &&
        (cal.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH)))
        result--;
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
    c.set(1971, 5, 13);
    a.reset();
    a.insertOperand(c.getTime());
    Integer age  = (Integer) a.evaluate();
    System.out.println("age = " + age.intValue());

    System.out.println("MAX INT = " + Integer.MAX_VALUE);
    System.out.println("MAX LONG = " + Long.MAX_VALUE);
  }
}