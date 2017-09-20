package com.dexels.navajo.parser.compiled.api;

import java.io.StringReader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.parser.compiled.CompiledParser;
import com.dexels.navajo.parser.compiled.ContextExpression;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.tipilink.TipiLink;

public class CachedExpression {

	private static CachedExpression instance;

	private final ConcurrentMap<String, ContextExpression> expressionCache = new ConcurrentHashMap<>();
	private final ConcurrentMap<String, Object> expressionValueCache = new ConcurrentHashMap<>();
	CachedExpression() {
		
	}

	public Object evaluate(String expression,Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel, String option,
			String selectionOption, MappableTreeNode mapNode, TipiLink tipiLink) {
		Object cachedValue = expressionValueCache.get(expression);
		if(cachedValue!=null) {
			System.err.println("Found cached expression value: "+cachedValue);
			return cachedValue;
		}
		return parse(expression).apply(doc, parentMsg, parentParamMsg, parentSel, option, selectionOption, mapNode, tipiLink);
		
	}
	
	public ContextExpression parse(String expression) {
		ContextExpression cachedParsedExpression = expressionCache.get(expression);
		if(cachedParsedExpression!=null) {
			System.err.println("Found cached expression: "+expression);
			return cachedParsedExpression;
		}
		CompiledParser cp;
		try {
			
			StringReader sr = new StringReader(expression);
			cp = new CompiledParser(sr);
			cp.Expression();
	        ContextExpression parsed = cp.getJJTree().rootNode().interpretToLambda();
	        if(parsed.isLiteral()) {
	        		Object result = parsed.apply(null, null, null, null, null, null, null, null);
	        		expressionValueCache.put(expression, result);
	        		expressionCache.put(expression,parsed);
	        		return new ContextExpression() {
						
						@Override
						public boolean isLiteral() {
							return true;
						}
						
						@Override
						public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel, String option,
								String selectionOption, MappableTreeNode mapNode, TipiLink tipiLink) throws TMLExpressionException {
							return result;
						}
					};
	        } else {
	        		expressionCache.put(expression,parsed);
	        		return parsed;
	        }
	        
		} catch (ParseException e) {
			throw new TMLExpressionException("Error parsing expression: "+expression, e);
		}

	}
	
	public static CachedExpression getInstance() {
		if(instance==null) {
			instance = new CachedExpression();
		}
		return instance;
	}
	
}
