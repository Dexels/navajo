package com.dexels.navajo.functions;

import java.util.Calendar;
import java.util.Date;

import com.dexels.navajo.parser.FunctionInterface;

public final class Today extends FunctionInterface {

  @Override
public String remarks() {
    return "Today() returns the current date, with time at 00:00";
  }
  @Override
public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    Calendar c = Calendar.getInstance();
    c.set(Calendar.HOUR_OF_DAY, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
    return c.getTime();
  }
  @Override
public String usage() {
    return "Today()";
  }

  public static void main(String [] args) throws Exception {
    Today n = new Today();
    n.reset();
    Date result = (Date) n.evaluate();
    System.err.println("result = " + result);
  }
}