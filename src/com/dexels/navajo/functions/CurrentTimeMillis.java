package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.document.Operand;


public final class CurrentTimeMillis extends FunctionInterface {

  public String remarks() {
   return "CurrentTimeMillis(), returns the time in millis since 1/1/1970";
  }
  public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    return System.currentTimeMillis()+"";
  }
  public String usage() {
   return "CurrentTimeMillis()";
  }

  public static void main(String [] args) throws Exception {
    com.dexels.navajo.parser.Expression expr = new com.dexels.navajo.parser.Expression();
    Operand o = expr.evaluate("CurrentTimeMillis()", null);
    System.err.println("o.type = " + o.type);
    System.err.println("o.value = " + o.value);
  }
}
