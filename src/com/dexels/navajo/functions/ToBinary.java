package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.document.types.Binary;
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

public class ToBinary extends FunctionInterface {
  public ToBinary() {
  }

  public String remarks() {
    return "Cast a string to a binary object";
  }

  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
   Object o = getOperand(0);
  
   if (o == null) {
     return new Binary((byte[]) null);
   } else if ( o instanceof Binary ) { 	 
   	 return o;
   } else {
     return new Binary(o.toString().getBytes());
   }
  }

  public String usage() {
    return "ToBinary(String): Binary";
  }


}
