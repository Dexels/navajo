package com.dexels.navajo.tipi.vaadin.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class GetScreenWidth extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		return 1024;
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
