package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.document.types.Memo;
import com.dexels.navajo.document.types.Percentage;
import com.dexels.navajo.document.Operand;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class ToMemo extends FunctionInterface {
  public ToMemo() {
  }

  public String remarks() {
    return "Cast a string to a memo object";
  }

  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    Object o = getOperand(0);
   if (o == null) {
     return new Memo("");
   }
   else {
     return new Memo(""+o);
   }
  }

  public String usage() {
    return "ToMemo(String): Memo";
  }


}
