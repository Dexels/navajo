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
import java.lang.reflect.*;
import java.util.ArrayList;

public class StringFunction extends FunctionInterface {

  public String remarks() {
    return "";
  }

  /**
   * NOTE: THIS FUNCTION DOES NOT SUPPORT STRING METHODS THAT CONTAIN PRIMITIVE TYPE ARGUMENTS LIKE int, boolean float, etc.
   * EXAMPLE OF A NOT SUPPORTED METHOD IS: substring(int).
   *
   * @return
   * @throws TMLExpressionException
   */
  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    String methodName = (String) getOperand(0);
    String object = (String) getOperand(1);
    ArrayList parameters = new ArrayList();
    for (int i = 2; i < getOperands().size(); i++) {
      parameters.add(getOperand(i));
    }
    Class [] classTypes = null;
    if (parameters.size() > 0) {
      classTypes = new Class[parameters.size()];
      for (int i = 0; i < parameters.size(); i++) {
        Class c = parameters.get(i).getClass();
        classTypes[i] = c;
      }
    }

    Object returnValue = null;
    try {
      Method m = java.lang.String.class.getMethod(methodName, classTypes);

      if (parameters.size() > 0)
        returnValue = m.invoke(object, (Object []) parameters.toArray());
      else
        returnValue = m.invoke(object, null);

    } catch (Exception e) {
      e.printStackTrace();
      throw new TMLExpressionException(e.getMessage());
    }

    return returnValue;
  }

  public String usage() {
    return "StringFunction(Method, Object, Arguments)";
  }

  public static void main(String [] args) throws Exception {
    StringFunction f = new StringFunction();
    f.reset();
    String aap = "newaap";
    f.insertOperand("indexOf");
    f.insertOperand(aap);
    f.insertOperand("aaap");
    //f.insertOperand(new Integer(4));
    Object o = f.evaluate();
    System.out.println("o = " + o + ", type = " + o.getClass().getName());
  }

}