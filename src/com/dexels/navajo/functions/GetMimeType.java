package com.dexels.navajo.functions;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class GetMimeType extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		System.err.println("O = " + getOperand(0));
		
		Binary b = (Binary) getOperand(0);
		if ( b != null ) {
			System.err.println("mime = " + b.guessContentType());
			return b.guessContentType();
		} else {
			return null;
		}
	}

	@Override
	public String remarks() {
		return "Guesses mime/type of a binary object";
	}

	@Override
	public String usage() {
		return "GetMimeType([binary])";
	}

}
