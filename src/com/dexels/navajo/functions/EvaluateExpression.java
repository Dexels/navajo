package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.*;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Operand;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class EvaluateExpression extends FunctionInterface {

  public String remarks() {
    return "Evaluate a Navajo expression";
  }

  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

    if (getOperands().size() != 1 && getOperands().size() != 3) {
      throw new TMLExpressionException("Wrong number of arguments");
    }

    Navajo currentNavajo = this.getNavajo();
    Message currentMessage = this.getCurrentMessage();
    Operand result = null;

    boolean conditional = (this.getOperands().size() == 3);

    if (!conditional) {
      String expression = (String) getOperand(0);

      try {
        //System.err.println("Evaluating " + expression + " against Navajo " + currentNavajo + ", and message " + currentMessage);
        result = Expression.evaluate(expression, currentNavajo, null, currentMessage);
      }
      catch (SystemException ex) {
        ex.printStackTrace(System.err);
      }
      catch (TMLExpressionException ex) {
        ex.printStackTrace(System.err);
      }
    } else {
       String condition = (String) getOperand(0);
       String exp1 = (String) getOperand(1);
       String exp2 = (String) getOperand(2);

      try {
        if (Condition.evaluate(condition, currentNavajo, null, currentMessage)) {
          result =  Expression.evaluate(exp1, currentNavajo, null, currentMessage);
        } else {
          result =  Expression.evaluate(exp2, currentNavajo, null, currentMessage);
        }
      }
      catch (SystemException ex1) {
        ex1.printStackTrace(System.err);
        throw new TMLExpressionException(this, ex1.getMessage());
      }
      catch (TMLExpressionException ex1) {
        ex1.printStackTrace(System.err);
        throw new TMLExpressionException(this, ex1.getMessage());
      }
    }

    if (result != null) {
      return result.value;
    } else {
      return null;
    }
  }

  public String usage() {
    return "EvaluateExpression(expression);EvaluateExpression(condition, expression1, expression2)";
  }

  public static void main(String [] args) throws Exception {
    Operand o = Expression.evaluate("EvaluateExpression('5 > 4', '6')", null);
    System.err.println("o.value = " + o.value);
  }
}
