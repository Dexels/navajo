package com.dexels.navajo.parser.compiled.api;

import java.io.StringReader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.parser.compiled.CompiledParser;
import com.dexels.navajo.parser.compiled.ContextExpression;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.tipilink.TipiLink;

import io.reactivex.Observable;

public class ExpressionCache {

	private static ExpressionCache instance;

	private final ConcurrentMap<String, ContextExpression> expressionCache = new ConcurrentHashMap<>();
	private final ConcurrentMap<String, Object> expressionValueCache = new ConcurrentHashMap<>();
	
	private final AtomicLong hitCount = new AtomicLong();
	private final AtomicLong pureHitCount = new AtomicLong();
	private final AtomicLong parsedCount = new AtomicLong();
	private final static Logger logger = LoggerFactory.getLogger(ExpressionCache.class);

	ExpressionCache() {
		try {
			Observable.interval(1, TimeUnit.MINUTES)
				.doOnError(e->logger.error("Error printing stats: ", e))
				.forEach(l->printStats());
		} catch (Throwable e) {
			logger.error("Error: ", e);
		}
	}

	public Object evaluate(String expression,Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
			String selectionOption, MappableTreeNode mapNode, TipiLink tipiLink, Access access) {
		Object cachedValue = expressionValueCache.get(expression);
		if(cachedValue!=null) {
			pureHitCount.incrementAndGet();
			printStats();
			return cachedValue;
		}
		return parse(expression).apply(doc, parentMsg, parentParamMsg, parentSel, selectionOption, mapNode, tipiLink,access);
		
	}
	
	public ContextExpression parse(String expression) {
		ContextExpression cachedParsedExpression = expressionCache.get(expression);
		if(cachedParsedExpression!=null) {
//			System.err.println("Found cached expression: "+expression);
			hitCount.incrementAndGet();
			return cachedParsedExpression;
		}
		CompiledParser cp;
		try {
			
			StringReader sr = new StringReader(expression);
			cp = new CompiledParser(sr);
			cp.Expression();
	        ContextExpression parsed = cp.getJJTree().rootNode().interpretToLambda();
	        parsedCount.incrementAndGet();
	        if(parsed.isLiteral()) {
	        		Object result = parsed.apply(null, null, null, null, null, null, null,null);
	        		expressionCache.put(expression,parsed);
	        		if(result!=null) {
		        		expressionValueCache.put(expression, result);
	        		}
	        		return new ContextExpression() {
						
						@Override
						public boolean isLiteral() {
							return true;
						}
						
						@Override
						public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
								String selectionOption, MappableTreeNode mapNode, TipiLink tipiLink, Access access) throws TMLExpressionException {
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
	
	public void printStats() {
		logger.info("Function cache stats. Value hit: {} expression hit: {} parse count: {} cached expression size: {} cached value size: {}",pureHitCount.get(),hitCount.get(), parsedCount.get(),this.expressionCache.size(),this.expressionValueCache.size());
	}
	
	public static ExpressionCache getInstance() {
		if(instance==null) {
			instance = new ExpressionCache();
		}
		return instance;
	}
	
}
