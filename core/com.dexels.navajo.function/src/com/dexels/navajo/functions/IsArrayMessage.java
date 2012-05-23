package com.dexels.navajo.functions;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class IsArrayMessage extends FunctionInterface {

	public String remarks() {
		return "Function to check whether the input message is an array message or not. Returns a boolean.";
	}

	public String usage() {
		return "IsArrayMessage( Message )";
	}

	public Object evaluate() throws TMLExpressionException {
		if (this.getOperands().size() != 1)
			throw new TMLExpressionException("IsArrayMessage( Message name ) expected");

		Object a = this.getOperands().get(0);

		if (!(a instanceof String)) {
			throw new TMLExpressionException("IsArrayMessage( Message name ) expected");
		}

		String messageName = (String) a;

	    Navajo doc      = getNavajo();
	    Message message = doc.getMessage( messageName );

		return Boolean.valueOf(message.isArrayMessage());
	}

}
