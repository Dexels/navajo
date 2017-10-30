package com.dexels.navajo.parser.compiled.api;

import java.io.StringReader;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.parser.compiled.CompiledParser;
import com.dexels.navajo.parser.compiled.ParseException;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.tipilink.TipiLink;
import com.dexels.replication.api.ReplicationMessage;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import io.reactivex.Observable;

public class ExpressionCache {
	private final static Logger logger = LoggerFactory.getLogger(ExpressionCache.class);
    private static final String DEFAULT_CACHE_SPEC = "maximumSize=20000";

	private static ExpressionCache instance;

    private final LoadingCache<String, Optional<ContextExpression>> expressionCache;
    private final LoadingCache<String, Optional<Object>> expressionValueCache;

	private final AtomicLong hitCount = new AtomicLong();
	private final AtomicLong pureHitCount = new AtomicLong();
	private final AtomicLong parsedCount = new AtomicLong();
	
	public ExpressionCache() {
		expressionCache = CacheBuilder.from(DEFAULT_CACHE_SPEC).build(new CacheLoader<String, Optional<ContextExpression>>() {
			public Optional<ContextExpression> load(String key) {
				// Return empty optional and let the application handle it.
				return Optional.empty();
			}
		});
		
		expressionValueCache = CacheBuilder.from(DEFAULT_CACHE_SPEC).build(new CacheLoader<String, Optional<Object>>() {
			public Optional<Object> load(String key) {
				// Return empty optional and let the application handle it.
				return Optional.empty();
			}
		});
		
		try {
			Observable.interval(1, TimeUnit.MINUTES)
				.doOnError(e->logger.error("Error printing stats: ", e))
				.forEach(l->printStats());
		} catch (Throwable e) {
			logger.error("Error: ", e);
		}
	}

	public Object evaluate(String expression,Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
			 MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ReplicationMessage> immutableMessage, Optional<ReplicationMessage> paramMessage) {
		Optional<Object> cachedValue = expressionValueCache.getUnchecked(expression);
		if(cachedValue.isPresent()) {
			pureHitCount.incrementAndGet();
//			printStats();
			return cachedValue.get();
		}
		return parse(expression).apply(doc, parentMsg, parentParamMsg, parentSel, mapNode, tipiLink,access,immutableMessage,paramMessage);
		
	}
	
	public ContextExpression parse(String expression) {
		Optional<ContextExpression> cachedParsedExpression = expressionCache.getUnchecked(expression);
		if(cachedParsedExpression.isPresent()) {
//			System.err.println("Found cached expression: "+expression);
			hitCount.incrementAndGet();
			return cachedParsedExpression.get();
		}
		CompiledParser cp;
		try {
			
			StringReader sr = new StringReader(expression);
			cp = new CompiledParser(sr);
			cp.Expression();
	        ContextExpression parsed = cp.getJJTree().rootNode().interpretToLambda();
	        parsedCount.incrementAndGet();
	        if(parsed.isLiteral()) {
	        		Object result = parsed.apply(null, null, null, null, null, null, null,null,null);
	        		expressionCache.put(expression, Optional.ofNullable(parsed));
	        		if(result!=null) {
		        		expressionValueCache.put(expression,  Optional.of(result));
	        		}
	        		return new ContextExpression() {
						
						@Override
						public boolean isLiteral() {
							return true;
						}
						
						@Override
						public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
								 MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ReplicationMessage> immutableMessage, Optional<ReplicationMessage> paramMessage) throws TMLExpressionException {
							return result;
						}
					};
	        } else {
	        		expressionCache.put(expression, Optional.ofNullable(parsed));
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
			createInstance();
		}
		return instance;
	}
	
	private synchronized static void createInstance() {
		if(instance==null) {
			instance = new ExpressionCache();
		}
		
	}
	
}
