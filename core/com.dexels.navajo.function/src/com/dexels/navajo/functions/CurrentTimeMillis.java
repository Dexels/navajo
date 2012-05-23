package com.dexels.navajo.functions;

import com.dexels.navajo.parser.FunctionInterface;


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


}
