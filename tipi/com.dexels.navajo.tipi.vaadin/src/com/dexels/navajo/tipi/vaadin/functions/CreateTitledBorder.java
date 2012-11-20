package com.dexels.navajo.tipi.vaadin.functions;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class CreateTitledBorder extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		String expression = (String)getOperand(0);
		return expression;
	}

	@Override
	public String remarks() {
		return null;
	}

	@Override
	public String usage() {
		return(" ");
	}

}
