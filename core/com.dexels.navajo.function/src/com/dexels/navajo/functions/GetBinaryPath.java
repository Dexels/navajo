package com.dexels.navajo.functions;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class GetBinaryPath extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		
		Binary b = (Binary) getOperand(0);
		if ( b != null ) {
			return b.getFile().getAbsolutePath();
		} else {
			return null;
		}
	}
 
	@Override
	public String remarks() {
		return "Returns the path to a binary";
	}

	@Override
	public String usage() {
		return "GetBinaryPath([binary])";
	}

}
