package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public final class WeekDay extends FunctionInterface {

  private final static Locale locale = new Locale("en_US");

  public WeekDay() {
  }

  public String remarks() {
    return "Return a three letter string with the weekday; If no argument is "
        +  "provided return the weekday for today; Only english locale is "
        +  "supported now; The result is return in upper case";
  }

  public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    Object o = getOperand(0);

    java.util.Date day = null;

    if (o == null ) {
      // take today
      day = Calendar.getInstance(locale).getTime();
    }
    else if (o instanceof java.util.Date) {
      day = (java.util.Date) o;
    } else if (o instanceof String) {
      java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
      java.util.Date date = null;

      try {
          day = format.parse((String) o);
      } catch (Exception e) {
          throw new TMLExpressionException("Invalid date format: " + (String) o);
      }
    } else {
      throw new TMLExpressionException("Invalid date: " + o);
    }

    return new SimpleDateFormat("EEE", locale).format(day).toUpperCase();
  }

  public String usage() {
    return "WeekDay([ Date | Date string ]): String";
  }

  public static void main(String [] args) throws Exception {

    // Tests.
    WeekDay wd = new WeekDay();

    wd.reset();
    wd.insertOperand(null);
    System.out.println("result = " + wd.evaluate().toString());

    wd.reset();
    wd.insertOperand("2003-07-16");
    System.out.println("result = " + wd.evaluate().toString());

    wd.reset();
    wd.insertOperand(new java.util.Date(System.currentTimeMillis()));
    System.out.println("result = " + wd.evaluate().toString());

  }

}
