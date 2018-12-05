package com.dexels.navajo.expression.compiled;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class ParameterNamesFunction extends FunctionInterface {

	@Override
	public String remarks() {
		return "";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		return this.getNamedParameters().keySet().stream().sorted().collect(Collectors.joining(","));
	}

}
