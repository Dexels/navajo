package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public final class sysdate extends FunctionInterface {

  public String remarks() {
    return "sysdate() returns the current date, with time";
  }
  public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {	 
    return new Date();
  }
  public String usage() {
    return "sysdate()";
  }

  public static void main(String [] args) throws Exception {
    sysdate n = new sysdate();
    n.reset();
    Date result = (Date) n.evaluate();
    System.err.println("result = " + result);
  }
}