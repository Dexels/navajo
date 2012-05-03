package com.dexels.navajo.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

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
