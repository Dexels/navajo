package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.document.Operand;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public final class ToMoney extends FunctionInterface {
  public ToMoney() {
  }

  public String remarks() {
    return "Cast a string/double/integer to a money object";
  }

  public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    Object o = getOperand(0);
   if (o == null) {
     return new Money( (Money)null);
   }
   else {
     return new Money(o);
   }
  }

  public String usage() {
    return "ToMoney(String/Integer/Double): Money";
  }

  public static void main(String [] args) throws Exception {

    java.util.Locale.setDefault(new java.util.Locale("nl", "NL"));
    // Tests.
   ToMoney tm = new ToMoney();
    tm.reset();
    tm.insertOperand(new Double(1024.4990));
    System.out.println("result = " + ((Money) tm.evaluate()).formattedString());

    // Using expressions.
    String expression = "ToMoney(1024.50) + 500 - 5.7 + ToMoney(1)/2";
    Operand o = Expression.evaluate(expression, null);
    System.out.println("o = " + o.value);
    System.out.println("type = " + o.type);

    System.err.println("ToMoney('') = " + Expression.evaluate("ToMoney('')", null).value);

  }

}
