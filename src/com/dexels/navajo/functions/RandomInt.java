package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import java.util.*;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author not attributable
 * @version 1.0
 */

public class RandomInt extends FunctionInterface {
  private static  java.util.Random r = new java.util.Random(System.currentTimeMillis());
  public RandomInt() {
  }
  public String remarks() {
    return "Returns a random integer, between the given bounds";
  }
  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

    ArrayList operands = this.getOperands();

//    java.util.Random r = new java.util.Random(System.currentTimeMillis());
    Integer min = (Integer)operands.get(0);
    Integer max = (Integer)operands.get(1);
    int range = max.intValue() - min.intValue();
    int result = Math.abs(r.nextInt()) % range;
    return new Integer(result+min.intValue());
//    return new Integer(range);
  }
  public String usage() {
    return "Random(min,max) (integers, will return an integer";
  }
public static void main(String[] args) throws Throwable {
  RandomInt r = new RandomInt();
  r.reset();
  r.insertOperand(new Integer(2));
  r.insertOperand(new Integer(26));
  System.err.println("Aap: "+r.evaluate());
}
}
