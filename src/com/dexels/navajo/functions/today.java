package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public final class today extends FunctionInterface {

  public String remarks() {
    return "today() returns the current date, with time at 00:00";
  }
  public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    Calendar c = Calendar.getInstance();
    c.set(Calendar.HOUR, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
    return c.getTime();
  }
  public String usage() {
    return "today()";
  }

  public static void main(String [] args) throws Exception {
    today n = new today();
    n.reset();
    Date result = (Date) n.evaluate();
    System.err.println("result = " + result);
  }
}