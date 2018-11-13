package com.dexels.navajo.expression.compiled;

import java.util.Map;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

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
	
	public Map<String,Object> namedOperands() {
		return this.getNamedParameters();
	}

}