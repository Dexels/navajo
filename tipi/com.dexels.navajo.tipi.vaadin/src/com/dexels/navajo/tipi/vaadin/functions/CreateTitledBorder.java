package com.dexels.navajo.tipi.vaadin.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class CreateTitledBorder extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		Object pp = operand(0).value;
		if (!(pp instanceof String)) {
			throw new TMLExpressionException(this, "Invalid operand: " + pp.getClass().getName());
		}
		return (String) pp;
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
