package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class CheckInteger extends FunctionInterface {

  public String remarks() {
   return "CheckInteger checks whether argument is valid integer";
  }

  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    Object o = getOperand(0);
    try {
      Integer.parseInt(o+"");
      return new Boolean(true);
    } catch (Exception e) {
      return new Boolean(false);
    }
  }

  public String usage() {
    return "CheckInteger(argument)";
  }

  public static void main(String [] args) throws Exception {
    CheckInteger ci = new CheckInteger();
    ci.reset();
    ci.insertOperand(new String("aap"));
    Object result = ci.evaluate();
    System.err.println("result = " + result);

    ci.reset();
    ci.insertOperand(new String("3432"));
    result = ci.evaluate();
    System.err.println("result = " + result);

  }
}