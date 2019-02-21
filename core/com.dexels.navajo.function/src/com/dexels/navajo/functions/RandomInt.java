package com.dexels.navajo.functions;

import java.util.List;

import com.dexels.navajo.expression.api.FunctionInterface;


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
  @Override
public String remarks() {
    return "Returns a random integer, between the given bounds";
  }
  @Override
public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {

    List<?> operands = this.getOperands();

//    java.util.Random r = new java.util.Random(System.currentTimeMillis());
    Integer min = (Integer)operands.get(0);
    Integer max = (Integer)operands.get(1);
    int range = max.intValue() - min.intValue();
    int result = Math.abs(r.nextInt()+1) % range;
    return Integer.valueOf(result+min.intValue());
//    return Integer.valueOf(range);
  }
  @Override
public String usage() {
    return "Random(min,max) (integers, will return an integer";
  }
public static void main(String[] args) throws Throwable {
  RandomInt r = new RandomInt();
  r.reset();
  r.insertIntegerOperand(Integer.valueOf(2));
  r.insertIntegerOperand(Integer.valueOf(26));
  System.err.println("Aap: "+r.evaluate());
}
}
