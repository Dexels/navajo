package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;

/**
 * <p>Title: <h3>SportLink Services</h3><br></p>
 * <p>Description: Web Services for the SportLink Project<br><br></p>
 * <p>Copyright: Copyright (c) 2002<br></p>
 * <p>Company: Dexels.com<br></p>
 * @author unascribed
 * @version $Id$
 */

public final class Random extends FunctionInterface {

  private static java.util.Random random = null;

  public Random() {
    if (random == null)
      random = new java.util.Random(System.currentTimeMillis());
  }

  @Override
public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
    double result = random.nextInt();
    if (result < 0)
      result *= -1;
    int i = (int) (result/1000.0);
    return new Integer(i);
  }
  @Override
public String usage() {
    return "Random()";
  }
  @Override
public String remarks() {
    return "Returns random integer";
  }

  public static void main (String [] args) throws Exception {

      Random r = new Random();
      r.reset();
      Integer i = (Integer) r.evaluate();
      System.err.println("i = " +  i.intValue());
  }
}