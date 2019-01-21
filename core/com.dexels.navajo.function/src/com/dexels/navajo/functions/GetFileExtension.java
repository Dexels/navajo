package com.dexels.navajo.functions;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class GetFileExtension extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		Binary b = (Binary) getOperand(0);
		if ( b != null ) {
			return b.getExtension();
		} else {
			return null;
		}
	}

	@Override
	public String remarks() {
		return "Guesses mime/type of a binary object and returns default file extension";
	}

	@Override
	public String usage() {
		return "GetFileExtension([binary])";
	}

}
