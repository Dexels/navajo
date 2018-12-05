package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class GetCurrentMessage extends FunctionInterface {

	@Override
	public String remarks() {
		return "This function will return the current message";
	}

	@Override
	public String usage() {
		
		return "Used to check if an URL is responding.";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		return getCurrentMessage();
	}
}
