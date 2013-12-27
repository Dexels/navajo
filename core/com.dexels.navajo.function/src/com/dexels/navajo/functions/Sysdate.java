package com.dexels.navajo.functions;

import java.util.Date;

import com.dexels.navajo.parser.FunctionInterface;

public final class Sysdate extends FunctionInterface {

  @Override
public String remarks() {
    return "Sysdate() returns the current date, with time";
  }
  @Override
public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {	 
    return new Date();
  }
  @Override
public String usage() {
    return "Sysdate()";
  }

  public static void main(String [] args) throws Exception {
    Sysdate n = new Sysdate();
    n.reset();
    Date result = (Date) n.evaluate();
    System.err.println("result = " + result);
  }
}