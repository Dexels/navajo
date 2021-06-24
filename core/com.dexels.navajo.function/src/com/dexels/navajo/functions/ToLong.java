/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;


import com.dexels.navajo.expression.api.FunctionInterface;


public final class ToLong extends FunctionInterface {

    public ToLong() {}

    @Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
        Object o = this.getOperands().get(0);

        if (o == null || "".equals(o))
          return null;

        if(o instanceof Long) {
      	  return o;
        }
        if(o instanceof java.util.Date) {
        	  return Long.valueOf(((java.util.Date)o).getTime());
          }
        if (o instanceof Double)
          return Long.valueOf( (int) ((Double) o).doubleValue() );


        return Long.valueOf(Long.parseLong(o + ""));

    }

    @Override
	public String usage() {
        return "ToLong(Object)";
    }

    @Override
	public String remarks() {
        return "Get an long representation of given object.";
    }
    @Override
	public boolean isPure() {
    		return false;
    }

    public static void main(String [] args) {
    }

}
