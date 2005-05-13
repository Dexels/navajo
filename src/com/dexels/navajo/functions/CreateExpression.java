package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.document.Operand;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 *
 * Example:
 *
 * Hello [/Input/LastName],
 * How are you today?
 */

public class CreateExpression extends FunctionInterface {

  public String remarks() {
    return "Create a Navajo expression from a string that only contains text and property references";
  }

  /**
   *
   * @return
   * @throws com.dexels.navajo.parser.TMLExpressionException
   */
  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

    String input = (String) getOperand(0);
    StringBuffer expression = new StringBuffer(input.length());

    boolean startExpression = false;
    expression.append("'");
    for (int i = 0; i < input.length(); i++) {
      char c = input.charAt(i);
      if (startExpression && c != ']') {
        expression.append(c);
      } else if (c == '[' && !startExpression) {
        startExpression = true;
        expression.append("' + [");
      } else if (c == '[' && startExpression) {
        throw new TMLExpressionException("Invalid expression: " + input);
      } else if (c == ']' && startExpression) {
        startExpression = false;
        expression.append("] + '");
      } else if (c == ']' && !startExpression) {
         throw new TMLExpressionException("Invalid expression: " + input);
      } else if (c == '\n') {
        expression.append("\n");
      } else {
        expression.append(c);
      }
    }
    if (!startExpression)
      expression.append("'");

    return expression.toString();
  }

  public String usage() {
    return "CreateExpression(String)";
  }

  public static void main(String [] args) throws Exception {
    String expression = "Hallo \n Hoe is het nou?";
    CreateExpression ce = new CreateExpression();
    ce.reset();
    ce.insertOperand(expression);
    String result = (String) ce.evaluate();
    System.err.println("result:");
    System.err.println(result);
    Operand o = Expression.evaluate(result, null);
    System.err.println("Evaluated to: ");
    System.err.println(o.value);
  }

}
