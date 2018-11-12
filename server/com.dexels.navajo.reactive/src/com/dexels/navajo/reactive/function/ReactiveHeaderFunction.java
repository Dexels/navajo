package com.dexels.navajo.reactive.function;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class ReactiveHeaderFunction extends FunctionInterface {

	@Override
	public String remarks() {
		return "Reactive Headers";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		String mime = (String) super.getNamedParameters().get("mimeType");
		
		
		return null;
	}

}
