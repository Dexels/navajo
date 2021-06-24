/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.expression.api.FunctionInterface;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

public class ToBinary extends FunctionInterface {
  public ToBinary() {
  }

  @Override
public String remarks() {
    return "Cast a string to a binary object";
  }

  @Override
public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
   Object o = getOperand(0);
  
   if (o == null) {
     return new Binary();
   } else if ( o instanceof Binary ) { 	 
   	 return o;
   } else {
   	if( o instanceof byte[]) {
   		byte[] b = (byte[])o;
   		return new Binary(b);
   	}
     return new Binary(o.toString().getBytes());
   }
  }

  @Override
public String usage() {
    return "ToBinary(String): Binary";
  }


}
