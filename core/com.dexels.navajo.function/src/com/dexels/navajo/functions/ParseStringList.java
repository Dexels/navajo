package com.dexels.navajo.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.parser.Expression;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class ParseStringList extends FunctionInterface {

  public ParseStringList() {
  }

  @Override
public String remarks() {
    return "Creates a list out of a string representation, e.g. {1,2,3,8,9}";
  }

  @Override
public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
    Object o = getOperand(0);
    if (o == null || (!(o instanceof String))) {
      throw new TMLExpressionException("Expected string");
    }
    Object delim = getOperand(1);
    if (delim == null || (!(delim instanceof String))) {
      throw new TMLExpressionException("Expected valid delimeter");
    }
   StringTokenizer tokens = new StringTokenizer((String) o, (String) delim);
   List<String> result = new ArrayList<String>();
   while (tokens.hasMoreTokens()) {
     result.add(tokens.nextToken());
   }
   return result;
  }

  @Override
public String usage() {
   return "ParseStringList(string, delimeter)";
  }

  public static void main(String [] args) throws Exception {
    String expression = "Contains(ParseStringList('CLUBFUNCTION,UNIONFUNCTION,2',','), 'FUNCTION'))";
    Operand o = Expression.evaluate(expression, null);
    System.err.println("o = " + o.value);
  }
}