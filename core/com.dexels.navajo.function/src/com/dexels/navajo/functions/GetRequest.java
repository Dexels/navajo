package com.dexels.navajo.functions;

import java.io.StringWriter;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class GetRequest extends FunctionInterface {

	@Override
	public String remarks() {
		return "Returns the current request as a string";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		StringWriter sb = new StringWriter();
		inMessage.write(sb);
		return sb.toString();
	}

}
