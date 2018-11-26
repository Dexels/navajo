package com.dexels.navajo.parser.compiled;

import java.util.List;
import java.util.Map;

import com.dexels.navajo.parser.compiled.api.ContextExpression;

public class ParsedReactiveElement {
	public final Map<String,ContextExpression> namedExpressions;
	public final List<ContextExpression> unnamedExpressions;
	public final int type;
	public final String name;
	
	public ParsedReactiveElement(String name, int type, Map<String,ContextExpression> namedExpressions, List<ContextExpression> unnamedExpressions) {
		this.name = name;
		this.type = type;
		this.namedExpressions = namedExpressions;
		this.unnamedExpressions = unnamedExpressions;
	}

}
