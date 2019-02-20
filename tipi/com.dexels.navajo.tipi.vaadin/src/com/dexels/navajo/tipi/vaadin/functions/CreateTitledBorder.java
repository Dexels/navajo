package com.dexels.navajo.tipi.vaadin.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class CreateTitledBorder extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		String expression = getStringOperand(0);
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
