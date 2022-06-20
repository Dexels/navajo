/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class IsNumeric extends FunctionInterface {
    public IsNumeric() {}
    
    @Override
	public String usage() {
        return "IsNumeric(Object)";
    }

    @Override
	public String remarks() {
        return "Checks if the given object contains only numeric values. NULL is considered to be ok.";
    }

    @Override
	@SuppressWarnings("unused")
    public Object evaluate() throws TMLExpressionException {
        Object o = this.getOperands().get(0);
        if (o == null) {
            return Boolean.TRUE;
        } else {
            try {
                if (o instanceof String) {
                    double nr = Double.parseDouble((String)o);
                    return Boolean.TRUE;
                } else if (o instanceof Long || o instanceof Integer || o instanceof Double || o instanceof Float) {
                    return Boolean.TRUE;
                } else {
                    // Implementation please
                    return Boolean.FALSE;
                }
            } catch (Exception e) {
                return Boolean.FALSE;
            }
        }
    }

    public static void main(String [] args) throws TMLExpressionException {
        IsNumeric in = new IsNumeric();
        in.reset();
        in.insertStringOperand("1234112534534534 A");
        System.err.println("Value is numeric = " + in.evaluate());
    }
}
