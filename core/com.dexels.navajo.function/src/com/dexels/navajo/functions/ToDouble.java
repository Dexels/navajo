/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;


import com.dexels.navajo.document.types.Money;
import com.dexels.navajo.expression.api.FunctionInterface;


public final class ToDouble extends FunctionInterface {

    public ToDouble() {}

    @Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
		Object o = this.getOperands().get(0);
        if (o==null) {
          return Double.valueOf(0);
        }
        if(o instanceof Money) {
      	  return ((Money)o).doubleValue();
        }
        return Double.valueOf(o+"");
    }

    @Override
	public String usage() {
        return "ToDouble(Object)";
    }

    @Override
	public String remarks() {
        return "Get a Double version of supplied object. Returns 0.0 if object is null.";
    }
    


}
