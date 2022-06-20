/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;


import com.dexels.navajo.expression.api.FunctionInterface;


public final class ToInteger extends FunctionInterface {

    public ToInteger() {}

    @Override
	public final Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
        Object o = this.getOperands().get(0);

        if (o == null || "".equals(o))
          return null;

        if (o instanceof Double)
          return Integer.valueOf( (int) ((Double) o).doubleValue() );


        return Integer.valueOf(Integer.parseInt(o + ""));

    }

    @Override
	public String usage() {
        return "ToInteger(Object)";
    }

    @Override
	public String remarks() {
        return "Get an integer representation of given object.";
    }

    public static void main(String [] args) {
      System.err.println(new java.util.Date().getTime()+"");
    }

}
