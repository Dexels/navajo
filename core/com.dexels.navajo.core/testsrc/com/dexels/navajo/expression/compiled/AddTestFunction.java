package com.dexels.navajo.expression.compiled;

import java.util.Map;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class AddTestFunction extends FunctionInterface {

	@Override
	public String remarks() {
		return "";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		int op = getOperands().size();
		System.err.println("Operands: "+op+" named: "+this.getNamedParameters());
		return "monkey";
	}
	
	public Map<String,Operand> namedOperands() {
		return this.getNamedParameters();
	}

}
