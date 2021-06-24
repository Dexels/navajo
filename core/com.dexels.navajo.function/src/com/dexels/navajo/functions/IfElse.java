/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class IfElse extends FunctionInterface {

	@Override
	public String remarks() {
		return "Evaluates a boolean expression and returns second argument if it evaluates to true, third argument otherwise";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		Object o = getOperand(0);
		
		if ( getOperands().size() != 3 ) {
			throw new TMLExpressionException(this, "Invalid number of parameters.");
		}
		if (!(o instanceof Boolean)) {
			throw new TMLExpressionException(this, "Invalid parameter: " + o);
		}
		Boolean b = (Boolean) o;
		if ( b ) {
			return getOperand(1);
		} else {
			return getOperand(2);
		}
	}

}
