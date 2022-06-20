/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class Switch extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		
		if ( getOperands().size() == 0) {
			throw new TMLExpressionException(this, "Not enough parameters for decode.");
		}
		
		Object oValue = getOperand(0);
		
		int i = 1;
		try {
			for ( i = 1; i < getOperands().size() - 1; i += 2 ) {
				Object matchValue = getOperand(i);
				Object newValue   = getOperand(i+1);
				if ( oValue.equals(matchValue) ) {
					return newValue;
				}
			}
		} catch (Throwable t) {
			throw new TMLExpressionException(this, "Not enough parameters for decode.");
		}
		
		if ( getOperands().size() -1  == i ) {
			return getOperands().get(i);
		} else {
			throw new TMLExpressionException(this, "Not enough parameters for decode.");
		}
	}

	@Override
	public String remarks() {
		return "Decode can be used to decode the value of a property to another value.";
	}

}
