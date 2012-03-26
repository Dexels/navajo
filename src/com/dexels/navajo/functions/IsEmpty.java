package com.dexels.navajo.functions;

import java.util.List;

import com.dexels.navajo.document.types.NavajoType;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class IsEmpty extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {

		Object arg = this.getOperands().get(0);
		
		if (arg == null) {
			return new Boolean(true);
		}
		
		if (arg instanceof NavajoType) {
			NavajoType n = (NavajoType)arg;
			return new Boolean(n.isEmpty());
		}
		
		if ( arg instanceof String) {
			return (((String) arg).trim().equals(""));
		}
		
		if ( arg instanceof List ) {
			return (((List<?>) arg).size() == 0);
		}
		
		return new Boolean(false);

	}

	@Override
	public String remarks() {
		return "Determines whether a given Navajo Object is empty or null";
	}

}
