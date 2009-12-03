package com.dexels.navajo.functions;

import java.io.StringWriter;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class NavajoRequestToString extends FunctionInterface {

	@Override
	public Object evaluate() throws TMLExpressionException {
		Navajo in = getNavajo();
		StringWriter ws = new StringWriter();
		try {
			in.write(ws);
		} catch (NavajoException e) {
			throw new TMLExpressionException(this, e.getMessage(), e);
		}
		return ws.toString();
	}

	@Override
	public String remarks() {
		return "Serializes a Navajo request to a string";
	}

}
