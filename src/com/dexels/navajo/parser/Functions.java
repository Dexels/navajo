
/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version 1.0
 */
package com.dexels.navajo.parser;

import com.dexels.navajo.document.*;
import java.util.*;
import com.dexels.navajo.util.*;

abstract class FunctionConstructor {

  private Vector operatorList = null;

  public FunctionConstructor() {
  }

  public void reset() {
    operatorList = new Vector();
  }

  public void insertOperator(Object o) {
    operatorList.add(o);
  }

  public abstract Object evaluate();

  protected Vector getOperators() {
    return operatorList;
  }
}

class MinFunction extends FunctionConstructor {

  public MinFunction() {
    super();
  }

  public Object evaluate() {
    Vector opList = getOperators();
    int a = ((Integer) opList.get(0)).intValue();
    int b = ((Integer) opList.get(1)).intValue();
    int c = (a <= b) ? a : b;
    return new Integer(c);
  }
}

class MaxFunction extends FunctionConstructor {

  public MaxFunction() {
    super();
  }

  public Object evaluate() {
    Vector opList = getOperators();
    int a = ((Integer) opList.get(0)).intValue();
    int b = ((Integer) opList.get(1)).intValue();
    int c = (a >= b) ? a : b;
    return new Integer(c);
  }
}

public class Functions {

  private static Object [][] functionList = {{new String("MIN"), new MinFunction()},
                                             {new String("MAX"), new MaxFunction()}};

  private static Vector getOperators(String parameterList) {
    Vector result = new Vector();
    int start = parameterList.indexOf("(");
    int end = parameterList.indexOf(")");
    String stripped = parameterList.substring(start+1, end);

    String parm = "";
    for(int i = 0; i < stripped.length(); i++) {
      if (Character.isDigit(stripped.charAt(i))) {
        parm += stripped.charAt(i);
      } else
      if (Character.isLetter(stripped.charAt(i))) {
        // We have found a function as an operator.
        // Add until closing ) found.
        boolean found = false;
        int closed = 0;
        int j = i+1;
        parm += stripped.charAt(j);
        while (!found) {
          char c = stripped.charAt(j);
          if (Character.isLetter(c))
            parm += c;
          else if (c == '(') {
            parm += c;
            closed++;
          }
          else if (c == ')') {
            parm += c;
            closed--;
          }
          else if ((c == ',') && (closed == 0))
            found = true;
          j++;
        }
      }
    }
    System.out.println("Stripped parameter list: " + stripped);
    StringTokenizer st = new StringTokenizer(stripped, ",");
    while (st.hasMoreElements()) {
      String param = ((String) st.nextToken()).trim();
      System.out.println("param: " + param);
      result.add(param);
    }

    return result;
  }

  public static String evaluate(String clause, Navajo inMessage) {

    boolean finished = true;
    StringBuffer result = new StringBuffer();

    for (int i = 0; i < functionList.length; i++) {
      String functionName = (String) functionList[i][0];
      int functionOffset = clause.indexOf(functionName);
      System.out.println("functionName: " + functionName);
      if (functionOffset != -1) {
        // function exists.
        FunctionConstructor fc = (FunctionConstructor) functionList[i][1];
        fc.reset();
        String stripped = clause.substring(functionOffset + functionName.length(), clause.length());
        System.out.println("stripped before: " + stripped);
        int end = stripped.indexOf(")")+1;
        System.out.println("end: " + end);
        stripped = stripped.substring(0, end);
        System.out.println("stripped: " + stripped);
        Vector params = getOperators(stripped);
        for (int j = 0; j < params.size(); j++) {
          Integer a = Integer.valueOf((String) params.get(j));
          fc.insertOperator(a);
        }
        Integer c = (Integer) fc.evaluate();
        System.out.println("Function evaluation: " + c.intValue());
        System.out.println("functionOffset: " + functionOffset + ", end: " + end);
        String beginS = clause.substring(0, functionOffset);
        String endS = clause.substring(functionOffset + functionName.length() + end, clause.length());
        String evaluation = c.intValue()+"";
        System.out.println("beginS: " + beginS);
        System.out.println("evaulation: " + evaluation);
        System.out.println("endS: " + endS);
        result.append(beginS);
        result.append(evaluation);
        result.append(endS);
        finished = false;
      }
    }

    if (!finished) {
      return evaluate(result.toString(), inMessage);
    }
    else {
      return clause;
    }
  }

  public static void main(String args[]) {

    String clause = "MIN(5, 6)";
    String result = evaluate(clause, null);
    System.out.println("->" + result);
  }

}