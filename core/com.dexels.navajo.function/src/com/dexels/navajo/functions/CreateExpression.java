package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;


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

  @Override
public String remarks() {
    return "Create a Navajo expression from a string that only contains text and property references";
  }

  /**
   *
   * @return
   * @throws com.dexels.navajo.expression.api.TMLExpressionException
   */
  @Override
public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {

    String input = getStringOperand(0);
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

  @Override
public String usage() {
    return "CreateExpression(String)";
  }
}
