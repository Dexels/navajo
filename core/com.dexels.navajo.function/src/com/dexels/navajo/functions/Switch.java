package com.dexels.navajo.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

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
