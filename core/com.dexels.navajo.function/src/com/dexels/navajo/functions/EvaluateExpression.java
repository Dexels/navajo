package com.dexels.navajo.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.parser.Condition;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.script.api.SystemException;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public final class EvaluateExpression extends FunctionInterface {

	
	private final static Logger logger = LoggerFactory
			.getLogger(EvaluateExpression.class);
	
  @Override
public String remarks() {
    return "Evaluate a Navajo expression";
  }

  @Override
public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {

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
        result = Expression.evaluate(expression, currentNavajo, null, currentMessage,null,null,null,null);
      }
      catch (TMLExpressionException ex) {
    	  logger.error("Error: ",ex);
      }
    } else {
       String condition = (String) getOperand(0);
       String exp1 = (String) getOperand(1);
       String exp2 = (String) getOperand(2);

      try {
        if (Condition.evaluate(condition, currentNavajo, null, currentMessage,getAccess())) {
          result =  Expression.evaluate(exp1, currentNavajo, null, currentMessage,null,null,null,null);
        } else {
          result =  Expression.evaluate(exp2, currentNavajo, null, currentMessage,null,null,null,null);
        }
      }
      catch (SystemException ex1) {
    	  logger.error("Error: ", ex1);
    	  throw new TMLExpressionException(this, ex1.getMessage());
      }
      catch (TMLExpressionException ex1) {
    	  logger.error("Error: ", ex1);
    	  throw new TMLExpressionException(this, ex1.getMessage());
      }
    }

    if (result != null) {
      return result.value;
    } else {
      return null;
    }
  }

  @Override
public String usage() {
    return "EvaluateExpression(expression);EvaluateExpression(condition, expression1, expression2)";
  }

  public static void main(String [] args) throws Exception {
    Operand o = Expression.evaluate("EvaluateExpression('5 < 4', '\\'Aap\\'', '\\'Noot\\'')", null);
    System.err.println("o.value = " + o.value);
  }
}
