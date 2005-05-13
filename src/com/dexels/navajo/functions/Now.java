package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public final class Now extends FunctionInterface {

  public String remarks() {
    return "Now() returns the current timestamp as a string in the following format: yyyy/MM/dd HH:uu:mm";
  }
  public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    Date today = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    return format.format(today);
  }
  public String usage() {
    return "Now()";
  }

  public static void main(String [] args) throws Exception {
    Now n = new Now();
    n.reset();
    String result = (String) n.evaluate();
    System.err.println("result = " + result);
  }
}