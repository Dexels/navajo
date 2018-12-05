package com.dexels.navajo.functions.util;

import com.dexels.navajo.expression.api.FunctionDefinition;

public interface FunctionResolver {
	public FunctionDefinition getFunction(String name);
}
