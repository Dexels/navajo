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
    String expression = (String) getOperand(0);
    Navajo currentNavajo = this.getNavajo();
    Message currentMessage = this.getCurrentMessage();
    Operand result = null;
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
    return result.value;
  }

  public String usage() {
    return "EvaluateExpression(String)";
  }

}
