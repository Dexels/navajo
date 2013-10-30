package com.dexels.navajo.functions;

import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.parser.FunctionInterface;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public final class ToMoneyInternal extends FunctionInterface {
  public ToMoneyInternal() {
  }

  @Override
public String remarks() {
    return "Cast a money to period separated double (as a string)";
  }

  @Override
public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    Object o = getOperand(0);
   if (o == null) {
     return null;
   } else {
   	Money m = (Money)o;
   	return m.toTmlString();
   }
  }

  @Override
public String usage() {
    return "ToMoneyInternal(money): String";
  }

  public static void main(String [] args) throws Exception {

    java.util.Locale.setDefault(new java.util.Locale("nl", "NL"));
    // Tests.
   ToMoneyInternal tm = new ToMoneyInternal();
    tm.reset();
    tm.insertOperand(new Money(3.4));
    
    System.out.println("result = " + tm.evaluate());

    // Using expressions.
//    String expression = "ToMoney(1024.50) + 500 - 5.7 + ToMoney(1)/2";
//    Operand o = Expression.evaluate(expression, null);
//    System.out.println("o = " + o.value);
//    System.out.println("type = " + o.type);
//
//    System.err.println("ToMoney('') = " + Expression.evaluate("ToMoney('')", null).value);

  }

}
