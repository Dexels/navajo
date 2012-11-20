package com.dexels.navajo.functions;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class GetHeader extends FunctionInterface {

	public GetHeader() {

	}

	@Override
	public String remarks() {
		return "Returns a header attribute from the request";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {

		String result;

		if ( inMessage != null ) {
			Header h = inMessage.getHeader();
			result = h.getHeaderAttribute((String) getOperand(0));
		} else {
			return "empty header";
		}
		
		return result;
	}
}
