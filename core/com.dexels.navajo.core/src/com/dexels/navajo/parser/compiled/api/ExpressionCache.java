/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.parser.compiled.api;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.FunctionClassification;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.expression.api.TipiLink;
import com.dexels.navajo.parser.compiled.CompiledParser;
import com.dexels.navajo.parser.compiled.Node;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;


public class ExpressionCache {
	private static final Logger logger = LoggerFactory.getLogger(ExpressionCache.class);
    private static final String DEFAULT_CACHE_SPEC = "maximumSize=20000";

	private static ExpressionCache instance;

    private final LoadingCache<String, Optional<ContextExpression>> exprCache;
    private final LoadingCache<String, Optional<Operand>> expressionValueCache;

	private final AtomicLong hitCount = new AtomicLong();
	private final AtomicLong pureHitCount = new AtomicLong();
	private final AtomicLong parsedCount = new AtomicLong();
	
	public ExpressionCache() {
		exprCache = CacheBuilder.from(DEFAULT_CACHE_SPEC).build(new CacheLoader<String, Optional<ContextExpression>>() {
			public Optional<ContextExpression> load(String key) {
				// Return empty optional and let the application handle it.
				return Optional.empty();
			}
		});
		
		expressionValueCache = CacheBuilder.from(DEFAULT_CACHE_SPEC).build(new CacheLoader<String, Optional<Operand>>() {
			public Optional<Operand> load(String key) {
				// Return empty optional and let the application handle it.
				return Optional.empty();
			}
		});
		
		try {
	        Timer time = new Timer(); // Instantiate Timer Object

	        time.schedule(new TimerTask() {

				@Override
				public void run() {
					printStats();
				}}, new Date(), TimeUnit.MINUTES.toMillis(5));
		

		} catch (Throwable e) {
			logger.error("Error: ", e);
		}
	}

	public Operand evaluate(String expression,Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
			 MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage) {
		Optional<Operand> cachedValue = expressionValueCache.getUnchecked(expression);
		if(cachedValue.isPresent()) {
			pureHitCount.incrementAndGet();
			return cachedValue.get();
		}
		List<String> problems = new ArrayList<>();
		ContextExpression parse = parse(problems,expression,functionName->FunctionClassification.DEFAULT,name->Optional.empty());
		if(!problems.isEmpty()) {
			problems.forEach(problem->
				logger.warn("Compile-time type error when compiling expression: {} -> {}",expression,problem)
			);
		}
		return parse.apply(doc, parentMsg, parentParamMsg, parentSel, mapNode, tipiLink,access,immutableMessage,paramMessage);
		
	}
	
//	public ContextExpression parse(List<String> problems, String expression,Function<String,FunctionClassification> functionClassifier) {
//		return parse(problems, expression,functionClassifier,true);
//	}
	public ContextExpression parse(List<String> problems, String expression,Function<String,FunctionClassification> functionClassifier) {
		return parse(problems, expression,functionClassifier,name->Optional.empty());
	}
	
	public ContextExpression parse(List<String> problems, String expression,Function<String,FunctionClassification> functionClassifier, Function<String,Optional<Node>> mapResolver) {
		Optional<ContextExpression> cachedParsedExpression = exprCache.getUnchecked(expression);
		if(cachedParsedExpression.isPresent()) {
			hitCount.incrementAndGet();
			return cachedParsedExpression.get();
		}
		CompiledParser cp;
		try {
			StringReader sr = new StringReader(expression);
			cp = new CompiledParser(sr);
			cp.Expression();
	        ContextExpression parsed = cp.getJJTree().rootNode().interpretToLambda(problems,expression,functionClassifier,mapResolver);
	        parsedCount.incrementAndGet();
	        if(parsed.isLiteral()) {
	        		Operand result = parsed.apply();
	        		exprCache.put(expression, Optional.ofNullable(parsed));
	        		if(result!=null) {
		        		expressionValueCache.put(expression,  Optional.of(result));
	        		}
	        		return new ContextExpression() {
						
						@Override
						public boolean isLiteral() {
							return true;
						}
						
						@Override
						public Operand apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
								 MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage) {
							return result;
						}

						@Override
						public Optional<String> returnType() {
							return Optional.ofNullable(result.type);
						}
						
						@Override
						public String expression() {
							return expression;
						}
					};
	        } else {
	        		exprCache.put(expression, Optional.ofNullable(parsed));
	        		return parsed;
	        }
	        
		} catch (ParseException e) {
			throw new TMLExpressionException("Error parsing expression: "+expression, e);
		} catch(Throwable e) {
			throw new TMLExpressionException("Unexpected error parsing expression: "+expression, e);
		}

	}
	
	public void printStats() {
		logger.info("Function cache stats. Value hit: {} expression hit: {} parse count: {} cached expression size: {} cached value size: {}",pureHitCount.get(),hitCount.get(), parsedCount.get(),this.exprCache.size(),this.expressionValueCache.size());
	}
	
	public static ExpressionCache getInstance() {
		if(instance==null) {
			createInstance();
		}
		return instance;
	}
	
	private static synchronized void createInstance() {
		if(instance==null) {
			instance = new ExpressionCache();
		}
		
	}
	
}
