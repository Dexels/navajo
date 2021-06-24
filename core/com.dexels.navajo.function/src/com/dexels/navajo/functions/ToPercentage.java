/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.types.Percentage;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.parser.Expression;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class ToPercentage extends FunctionInterface {
  public ToPercentage() {
  }

  @Override
public String remarks() {
    return "Cast a string/double/integer to a percentage object";
  }

  @Override
public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
    Object o = getOperand(0);
   if (o == null) {
     return new Percentage( (Double)null);
   }
   else {
     return new Percentage(o);
   }
  }
  @Override
	public boolean isPure() {
  		return false;
  }

  @Override
public String usage() {
    return "ToPercentage(String/Integer/Double): Percentage";
  }

  public static void main(String [] args) throws Exception {

    java.util.Locale.setDefault(new java.util.Locale("nl", "NL"));
    // Tests.
    ToPercentage tm = new ToPercentage();
    tm.reset();
    tm.insertFloatOperand(Double.valueOf(1.5));
    System.out.println("result = " + ((Percentage) tm.evaluate()).formattedString());

    // Using expressions.
    String expression = "ToPercentage(1024.50) + 500 - 5.7 + ToPercentage(1)/2";
    Operand o = Expression.evaluate(expression, null);
    System.out.println("o = " + o.value);
    System.out.println("type = " + o.type);


  }

}
